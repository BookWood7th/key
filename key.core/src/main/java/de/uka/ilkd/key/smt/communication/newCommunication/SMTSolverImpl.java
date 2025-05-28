package de.uka.ilkd.key.smt.communication.newCommunication;

import de.uka.ilkd.key.java.Services;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.logic.Sequent;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.mgt.SpecificationRepository;
import de.uka.ilkd.key.smt.*;
import de.uka.ilkd.key.smt.communication.newCommunication.commands.CheckSatCommand;
import de.uka.ilkd.key.smt.communication.newCommunication.commands.SMTSolverCommandSerializer;
import de.uka.ilkd.key.smt.solvertypes.SolverType;
import de.uka.ilkd.key.smt.solvertypes.SolverTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class SMTSolverImpl implements SMTSolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(SMTSolverImpl.class);

    private final SMTProblem problem;
    private final SolverType solverType;
    private final CompositeSolverListener solverListener;
    private final SolverCapabilities capabilities;
    private final SMTSolverSocket socket;
    private final SMTSettings smtSettings;
    private final Services services;
    private final SolverCommunicationLog solverCommunicationLog;

    private String problemString;
    private ModelExtractor query;

    private SMTSolverResult satisfiabilityResult;

    private IllegalFormulaException translationException;


    private boolean started = false;
    //TODO perhaps this should not be able to be started twice

    public SMTSolverImpl(final SolverType solverType, final SMTProblem problem, final SolverCapabilities capabilities, final Services services, final SMTSettings smtSettings) {
        this.problem = problem;
        this.solverType = solverType;
        this.capabilities = capabilities;
        this.solverListener = new CompositeSolverListener();
        this.services = services;
        this.smtSettings = smtSettings;

        this.solverCommunicationLog = new SolverCommunicationLog();
        this.socket = new SMTSolverSocket(getSolverStartCommands(), solverType.getDelimiters(), solverCommunicationLog);

        prepareProblem();
    }

    protected void prepareProblem() {
        try {
            this.problemString = translateProblem(problem);
        } catch (IllegalFormulaException ignored) {
        }
    }

    protected String translateProblem(SMTProblem problem) throws IllegalFormulaException {
        String problemString;
        Sequent sequent = problem.getSequent();
        if (getType() == SolverTypes.Z3_CE_SOLVER || getSolverCapabilities().supportsModelGeneration()) {
            Proof proof = problem.getGoal().proof();
            SpecificationRepository specrep = proof.getServices().getSpecificationRepository();

            Proof originalProof = null;
            for (Proof pr : specrep.getAllProofs()) {
                if (proof.name().toString().endsWith(pr.name().toString())) {
                    originalProof = pr;
                    break;
                }
            }

            KeYJavaType typeOfClassUnderTest =
                    specrep.getProofOblInput(originalProof).getContainerType();


            SMTObjTranslator objTrans =
                    new SMTObjTranslator(smtSettings, services, typeOfClassUnderTest);
            problemString = objTrans.translateProblem(sequent, services, smtSettings).toString();
            this.query = objTrans.getQuery();
        } else {
            SMTTranslator trans = getType().createTranslator();
            problemString = indent(trans.translateProblem(sequent, services, smtSettings).toString());
        }
        return problemString;
    }

    private static String indent(String string) {
        try {
            return SMTBeautifier.indent(string);
        } catch (Exception ex) {
            // fall back if pretty printing fails
            LOGGER.warn("Beautifier failed", ex);
            return string;
        }
    }

    protected String[] getSolverStartCommands() {
        String[] parameters = getType().getSolverParameters().split(" ");
        String[] result = new String[parameters.length + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = i == 0 ? getType().getSolverCommand() : parameters[i - 1];
        }
        return result;
    }

    @Override
    public synchronized void start() throws IOException {
        close();
        try {
            socket.open();
            socket.sendMessage(getType().modifyProblem(problemString));
        } catch (IOException e) {
            close();
            throw e;
        }
        started = true;
    }

    @Override
    public synchronized SMTSolverResult checkSatisfiability() throws IOException, InterruptedException {
        ensureStarted();
        SMTSolverCommandSerializer serializer = getType().getSerializer();
        SMTResponseDecoder decoder = getType().getResponseDecoder();

        long startTime = System.currentTimeMillis();
        socket.sendMessage(serializer.serialize(new CheckSatCommand()));

        String msg;
        while (true) {
            msg = socket.readMessage();
            SocketMessage socketMsg = decoder.decode(msg);
            if (socketMsg instanceof ResultMsg) {
                //TODO implement Result /change interfaces to match new architecture
                return switch (((ResultMsg) socketMsg).result()) {
                    case VALID -> null;
                    case FALSIFIABLE -> null;
                    case UNKNOWN -> null;
                };
            }
        }
    }

    @Override
    public synchronized ModelExtractor extractModel() throws IOException, InterruptedException {
        ensureStarted();
        if (!getSolverCapabilities().supportsModelGeneration()) {
            throw new IOException(problemString + " does not support model generation");
        }
        if (satisfiabilityResult == null) {
            satisfiabilityResult = checkSatisfiability();
        }
        if (satisfiabilityResult.isValid() != SMTSolverResult.ThreeValuedTruth.FALSIFIABLE) {
            throw new IllegalStateException(problemString + " is satisfiable");
        }

        //TODO implement querying

        return query;
    }

    private void ensureStarted() throws IOException {
        if (!started) {
            throw new IOException("SMT solver has not been started yet");
        }
    }

    @Override
    public SMTProblem getProblem() {
        return problem;
    }

    @Override
    public SolverCapabilities getSolverCapabilities() {
        return capabilities;
    }

    @Override
    public SolverType getType() {
        return solverType;
    }

    @Override
    public boolean addListener(SolverListener listener) {
        return solverListener.addListener(listener);
    }

    @Override
    public void close() {
        socket.close();
    }

    static class CompositeSolverListener implements SolverListener {
        private final Collection<SolverListener> listeners;

        CompositeSolverListener() {
            this.listeners = new ArrayList<>();
        }

        CompositeSolverListener(Collection<SolverListener> listeners) {
            this.listeners = listeners;
        }

        boolean addListener(SolverListener listener) {
            return listeners.add(listener);
        }

        @Override
        public void processStarted(de.uka.ilkd.key.smt.SMTSolver solver, SMTProblem problem) {
            listeners.forEach(listener -> {
                try {
                listener.processStarted(solver, problem); }
            catch (Exception ignored) {}});
        }

        @Override
        public void processInterrupted(de.uka.ilkd.key.smt.SMTSolver solver, SMTProblem problem, Throwable e) {
            listeners.forEach(listener -> {
                try {
                    listener.processInterrupted(solver, problem, e); }
                catch (Exception ignored) {}});
        }

        @Override
        public void processStopped(de.uka.ilkd.key.smt.SMTSolver solver, SMTProblem problem) {
            listeners.forEach(listener -> {
                try {
                    listener.processStopped(solver, problem); }
                catch (Exception ignored) {}});
        }

        @Override
        public void processTimeout(de.uka.ilkd.key.smt.SMTSolver solver, SMTProblem problem) {
            listeners.forEach(listener -> {
                try {
                    listener.processTimeout(solver, problem); }
                catch (Exception ignored) {}});
        }

        @Override
        public void processUser(de.uka.ilkd.key.smt.SMTSolver solver, SMTProblem problem) {
            listeners.forEach(listener -> {
                try {
                    listener.processUser(solver, problem); }
                catch (Exception ignored) {}});
        }
    }
}
