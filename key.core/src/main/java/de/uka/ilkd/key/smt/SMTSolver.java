package de.uka.ilkd.key.smt;

import de.uka.ilkd.key.smt.communication.SolverCommunication;
import de.uka.ilkd.key.smt.solvertypes.SolverType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Callable;

public interface SMTSolver extends Callable<SMTSolverResult>, AutoCloseable {
    Logger LOGGER = LoggerFactory.getLogger(SMTSolver.class);

    enum SolverState {
        Waiting, Running, Stopped
    }

    SolverState getState();

    void start() throws IOException;
    SMTSolverResult checkSatisfiability();
    ModelExtractor extractModel() throws IOException, InterruptedException;

    SolverCommunication getSolverCommunication();
    SMTProblem getProblem();
    SolverCapabilities getSolverCapabilities();
    SolverType getType();

    default SMTSolverResult call() {
        try {
            start();
        } catch (IOException e) {
            LOGGER.error("SMT Solver could not start{}", e.getMessage());
            //TODO better handling needed
            throw new RuntimeException(e);
        }
        try {
            SMTSolverResult result = checkSatisfiability();
            if (result.isValid() == SMTSolverResult.ThreeValuedTruth.FALSIFIABLE
                    && getSolverCapabilities().supportsModelGeneration()) {
                try {
                    extractModel();
                } catch (IOException | InterruptedException ignored) {
                    LOGGER.error("SMTSolver encountered an error: {}", ignored.getMessage());
                    //TODO implement better handling for this case
                }
            }
            return result;
        } finally {
            try {
                close();
            } catch (Exception ignored) {
            }
        }
    }

    boolean addListener(SolverListener listener);

    default String name() {
        return getType().getName();
    }

    //TODO Replace this when refactoring SMTProblem
    @Deprecated
    SMTSolverResult getFinalResult();

    @Deprecated
    ModelExtractor getQuery();

    //TODO this used to record the scheduled start time of the timeout task. Investigate side effects of making this return start time of solver
    @Deprecated
    long getStartTime();
}
