/* This file is part of KeY - https://key-project.org
 * KeY is licensed under the GNU General Public License Version 2
 * SPDX-License-Identifier: GPL-2.0-only */
package de.uka.ilkd.key.smt;

import java.util.*;
import java.util.concurrent.*;

import de.uka.ilkd.key.java.Services;
import de.uka.ilkd.key.smt.solvertypes.SolverType;

/**
 * IN ORDER TO START THE SOLVERS USE THIS CLASS.<br>
 * There are two cases how the solvers can be started:<br>
 * <br>
 * 1. Case: Starting the solvers synchronously<br>
 * <br>
 * First Step: Create the SMT problem:<br>
 * <code>SMTProblem problem = new SMTProblem(g); // g can be either a goal or term</code> <br>
 * <br>
 * Second Step: Create the launcher object:<br>
 * <code>SolverLauncher launcher = new SolverLauncher(new SMTSettings(){...});</code> <br>
 * <br>
 * Third Step: Launch the solvers you want to execute<br>
 * <code>launcher.launch(problem, services,SolverType.Z3_SOLVER,SolverType.YICES_SOLVER);</code>
 * <br>
 * <br>
 * Forth Step: Get the final result<br>
 * <code>return problem.getFinalResult();</code><br>
 * <br>
 * In case that you want to access the result of each solver:<br>
 *
 * <pre>
 * for (SMTSolver solver : problem.getSolvers()) {
 *     solver.getFinalResult();
 * }
 * </pre>
 *
 * <br>
 * <p>
 * 2. Case: Starting the solvers asynchronously:<br>
 * <br>
 *
 * <pre>
 * Thread thread = new Thread(new Runnable() {
 * public void run() {
 *   SMTProblem final problem = new SMTProblem(...);
 *   SolverLauncher launcher = new SolverLauncher(new SMTSettings(...));
 *   launcher.addListener(new SolverLauncherListener(){
 *          public void launcherStopped(SolverLauncher launcher, Collection<SMTSolver> problemSolvers){
 *          	// do something with the results here...
 *          	problem.getFinalResult();
 *          	// handling the problems that have occurred:
 *          	for(SMTSolver solver : problemSolvers){
 *          		solver.getException();
 *          		...
 *            }
 *          }
 *          public void launcherStarted(Collection<SMTProblem> problems,
 *                                      Collection<SolverType> solverTypes,
 *                                      SolverLauncher launcher);
 *             });
 *   launcher.launch(problem,services,SolverType.Z3_SOLVER,SolverType.YICES_SOLVER);
 *
 *            }
 *        });
 *    thread.start();
 * </pre>
 *
 * <br>
 * NOTE: In case that you add at least one listener to a launcher no exception is thrown when a
 * solver produces an error. The exceptions of the solvers are stored within the solver object and
 * can be accessed by <code>solver.getException</code>.
 */

public class SolverLauncher implements AutoCloseable {

    /* ############### Public Interface #################### */

    /**
     * Create for every solver execution a new object. Don't reuse the solver launcher object.
     *
     * @param settings settings for the execution of the SMT Solvers.
     */
    public SolverLauncher(SMTSettings settings) {
        this.settings = settings;
    }

    /**
     * Adds a listener to the launcher object. The listener can be used to observe the solver
     * execution. If at least one listener was added to the solver launcher, no exception is thrown
     * when a solver produces an error. The error can be read when the method
     * <code>launcherStopped</code> of the listener is called.
     */
    public void addListener(SolverLauncherListener listener) {
        this.listener.addListener(listener);
    }

    public void removeListener(SolverLauncherListener listener) {
        this.listener.removeListener(listener);
    }

    /**
     * Launches several solvers for the problem that is handed over.<br>
     * Note: Calling this methods does not create an extra thread, i.e. the calling thread is
     * blocked until the method returns. (Synchronous method call).
     *
     * @param problem The problem that should be translated and passed to the solvers
     * @param services The services object of the current proof.
     * @param solverTypes A list of solver types that should be used for the problem.
     */
    public void launch(SMTProblem problem, Services services, SolverType... solverTypes) {
        launch(List.of(problem), services, solverTypes);
    }

    /**
     * Launches several solvers for the problems that are handed over. Note: Calling this methods
     * does not create an extra thread, i.e. the calling thread is blocked until the method returns.
     * (Synchronous method call).
     *
     * @param problems The problems that should be translated and passed to the solvers
     * @param services The services object of the current proof.
     * @param solverTypes A list of solver types that should be used for the problem.
     */
    public void launch(Collection<SMTProblem> problems, Services services, SolverType... solverTypes) {
        launch(problems, services, List.of(solverTypes));
    }

    public void launch(Collection<SMTProblem> problems, Services services, Collection<SolverType> solverTypes) {
        start();
        launchIntern(problems, services, solverTypes);
    }

    private void start() {
        close();
        if (launcherHasBeenUsed) {
            throw new IllegalStateException("SolverLauncher has already been started");
        }
        launcherHasBeenUsed = true;
        threadPool = Executors.newFixedThreadPool(settings.getMaxConcurrentProcesses());
    }

    /**
     * Stops the execution of the launcher.
     */
    public void close() {
        if (threadPool != null) {
            threadPool.shutdownNow();
        }
        notifyListenersOfStop();
    }

    /* ################ Implementation ############################ */

    private ExecutorService threadPool;

    private final CompositeSolverLauncherListener listener = new CompositeSolverLauncherListener();

    /**
     * The SMT settings that should be used
     */
    private final SMTSettings settings;

    private final Collection<SMTSolver> solvers = new LinkedList<>();

    /**
     * Every launcher object should be used only once.
     */
    private boolean launcherHasBeenUsed = false;

    private void launchIntern(Collection<SMTProblem> problems, Services services, Collection<SolverType> solverTypes) {
        Collection<Callable<SMTSolverResult>> tasks = prepareTasks(problems, services, solverTypes);

        // Show progress dialog
        notifyListenersOfStart(problems, solverTypes);

        tasks.forEach(threadPool::submit);

        notifyListenersOfStop();
    }

    private Collection<Callable<SMTSolverResult>> prepareTasks(Collection<SMTProblem> problems, Services services, Collection<SolverType> solverTypes) {
        Collection<Callable<SMTSolverResult>> tasks = new ArrayList<>();

        //Only consider installed solver types.
        Collection<SolverType> installedSolverTypes = solverTypes.stream().filter((type) -> {
            if (settings.checkForSupport()) {
                //TODO this should display a warning of some kind. Implement in SolverListener
                return type.checkForSupport();
            } else {
                boolean forceRecheckInstallFlag = false;
                return type.isInstalled(forceRecheckInstallFlag);
            }
        }).toList();

        for (SMTProblem problem : problems) {
            for (SolverType solverType : installedSolverTypes) {
                SMTSolver solver = solverType.createSolver(problem, null, services, settings, solverType.getSolverTimeout());
                solvers.add(solver);
                //TODO change this when refactoring SMTProblem
                problem.addSolver(solver);

                Callable<SMTSolverResult> solverTask = () -> {
                    FutureTask<SMTSolverResult> solverComputationTask = new FutureTask<>(solver);
                    try {
                        solverComputationTask.run();
                        return solverComputationTask.get(solverType.getSolverTimeout(), TimeUnit.MILLISECONDS);
                    } catch (TimeoutException ex) {
                        solverComputationTask.cancel(true);
                        SMTSolverResult result = solver.getFinalResult();
                        return SMTSolverResult.getTimeoutResult(solverType, problem, result.getTimeTaken(), result.getSolverCommunication(), result.getTranslation().orElseThrow());
                    } finally {
                        System.out.println(solver.getFinalResult());
                    }
                };

                tasks.add(solverTask);
            }
        }
        return tasks;
    }

    private void notifyListenersOfStart(Collection<SMTProblem> problems,
            Collection<SolverType> solverTypes) {
        listener.launcherStarted(problems, solverTypes, this);
    }

    private void notifyListenersOfStop() {
        Collection<SMTSolver> finishedSolvers = new ArrayList<>(solvers.stream()
                .filter((solver) -> solver.getState() == SMTSolver.SolverState.Stopped).toList());

        for (SMTSolver solver : solvers) {
            if (!finishedSolvers.contains(solver)) {
                finishedSolvers.add(solver);
            }
        }

        listener.launcherStopped(this,  finishedSolvers);
    }

    static class CompositeSolverLauncherListener implements SolverLauncherListener {
        private final Collection<SolverLauncherListener> listeners = new ArrayList<>();

        public CompositeSolverLauncherListener() {}

        public boolean addListener(SolverLauncherListener listener) {
            return listeners.add(listener);
        }

        public boolean removeListener(SolverLauncherListener listener) {
            return listeners.remove(listener);
        }

        public boolean isEmpty() {
            return listeners.isEmpty();
        }

        @Override
        public void launcherStarted(Collection<SMTProblem> problems, Collection<SolverType> solverTypes, SolverLauncher launcher) {
            listeners.forEach(listener -> {
               try {
                   listener.launcherStarted(problems, solverTypes, launcher);
               } catch (Exception ignored) {}
            });
        }

        @Override
        public void launcherStopped(SolverLauncher launcher, Collection<SMTSolver> finishedSolvers) {
            listeners.forEach(listener -> {
                try {
                    listener.launcherStopped(launcher, finishedSolvers);
                } catch (Exception ignored) {}
            });
        }
    }
}
