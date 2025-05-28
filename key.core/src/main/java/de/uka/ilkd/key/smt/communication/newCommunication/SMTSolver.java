package de.uka.ilkd.key.smt.communication.newCommunication;

import de.uka.ilkd.key.smt.ModelExtractor;
import de.uka.ilkd.key.smt.SMTProblem;
import de.uka.ilkd.key.smt.SMTSolverResult;
import de.uka.ilkd.key.smt.SolverListener;
import de.uka.ilkd.key.smt.solvertypes.SolverType;

import java.io.IOException;
import java.util.concurrent.Callable;

public interface SMTSolver extends Callable<SMTSolverResult>, AutoCloseable {
    void start() throws IOException;
    SMTSolverResult checkSatisfiability() throws IOException, InterruptedException;
    ModelExtractor extractModel() throws IOException, InterruptedException;

    SMTProblem getProblem();
    SolverCapabilities getSolverCapabilities();
    SolverType getType();

    default SMTSolverResult call() throws InterruptedException, IOException {
        start();
        try {
            SMTSolverResult result = checkSatisfiability();
            if (result.isValid() == SMTSolverResult.ThreeValuedTruth.FALSIFIABLE && getSolverCapabilities().supportsModelGeneration()) {
                extractModel();
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
}
