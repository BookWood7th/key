/* This file is part of KeY - https://key-project.org
 * KeY is licensed under the GNU General Public License Version 2
 * SPDX-License-Identifier: GPL-2.0-only */
package de.uka.ilkd.key.smt;

import de.uka.ilkd.key.smt.communication.SolverCommunication;
import de.uka.ilkd.key.smt.solvertypes.SolverType;

import java.util.Optional;

/**
 * Encapsulates the result of a single solver.
 */
public abstract class SMTSolverResult {

    private final SolverType solverType;
    private final SMTProblem problem;
    private final long timeTaken;
    private final SolverCommunication solverCommunication;
    private final Optional<String> translation;

    /**
     * In the context of proving nodes/sequents these values mean the following: TRUE iff negation
     * of the sequent is unsatisfiable, FALSIFIABLE iff negation of the sequent is satisfiable (i.e.
     * it has a counterexample), UNKNOWN otherwise (I'm not sure if this holds if an error occurs)
     * Note: Currently (1.12.'09) the SMT Solvers do not check if a node is FALSE.
     */
    public enum ThreeValuedTruth {
        VALID("valid"),
        FALSIFIABLE("there is a counter example"),
        UNKNOWN("unknown");

        private final String description;

        ThreeValuedTruth(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public static SMTValidResult getValidResult(SolverType solver, SMTProblem problem,
            long timeTaken, SolverCommunication solverCommunication, String translation) {
        return new SMTValidResult(solver, problem, timeTaken, solverCommunication, translation);
    }

    public static SMTFalsifiableResult getFalsifiableResult(SolverType solver, SMTProblem problem,
            long timeTaken, SolverCommunication solverCommunication, String translation) {
        return new SMTFalsifiableResult(solver, problem, timeTaken, solverCommunication, translation);
    }

    public static SMTCEResult getCEResult(SolverType solver, SMTProblem problem, long timeTaken,
            SolverCommunication solverCommunication, String translation, ModelExtractor query) {
        return new SMTCEResult(solver, problem, timeTaken, solverCommunication, translation, query);
    }

    public static SMTUnknownResult getUnknownResult(SolverType solver, SMTProblem problem,
            long timeTaken, SolverCommunication solverCommunication, String translation) {
        return new SMTUnknownResult(solver, problem, timeTaken, solverCommunication, translation);
    }

    public static SMTTimeoutResult getTimeoutResult(SolverType solver, SMTProblem problem,
            long timeTaken, SolverCommunication solverCommunication, String translation) {
        return new SMTTimeoutResult(solver, problem, timeTaken, solverCommunication, translation);
    }

    public static SMTExceptionResult getExceptionResult(SolverType solver, SMTProblem problem,
            long timeTaken, SolverCommunication solverCommunication, String translation, Throwable exception) {
        return new SMTExceptionResult(solver, problem, timeTaken, solverCommunication, translation, exception);
    }

    private SMTSolverResult(SolverType solverType, SMTProblem problem, long timeTaken,
                            SolverCommunication solverCommunication, String translation) {
        this.solverType = solverType;
        this.problem = problem;
        this.timeTaken = timeTaken;
        this.solverCommunication = solverCommunication;

        if (translation != null) {
            this.translation = Optional.of(translation);
        } else {
            //Translation likely failed
            //TODO should there be different handling of this case?
            this.translation = Optional.empty();
        }
    }

    public abstract ThreeValuedTruth isValid();

    public String getRawSolverOutput() {
        StringBuilder output = new StringBuilder();
        for (SolverCommunication.Message m : solverCommunication.getOutMessages()) {
            String s = m.content();
            output.append(s).append("\n");
        }
        return output.toString();
    }

    public String getRawSolverInput() {
        StringBuilder input = new StringBuilder();

        for (SolverCommunication.Message m : solverCommunication
                .getMessages(SolverCommunication.MessageType.INPUT)) {
            String s = m.content();
            input.append(s).append("\n");
        }
        return input.toString();
    }

    public SMTProblem getProblem() {
        return problem;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public String getName() {
        return solverType.getName();
    }

    public SolverCommunication getSolverCommunication() {
        return solverCommunication;
    }

    public Optional<String> getTranslation() {
        return translation;
    }


    @Override
    public String toString() {
        return isValid().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SMTSolverResult ssr)) {
            return false;
        }
        return isValid() == ssr.isValid();
    }


    public static class SMTValidResult extends SMTSolverResult {
        private SMTValidResult(SolverType solverType, SMTProblem problem, long timeTaken,
                SolverCommunication solverCommunication, String translation) {
            super(solverType, problem, timeTaken, solverCommunication, translation);
        }

        @Override
        public ThreeValuedTruth isValid() {
            return ThreeValuedTruth.VALID;
        }
    }

    public static class SMTFalsifiableResult extends SMTSolverResult {
        private SMTFalsifiableResult(SolverType solverType, SMTProblem problem, long timeTaken,
                SolverCommunication solverCommunication, String translation) {
            super(solverType, problem, timeTaken, solverCommunication, translation);
        }

        @Override
        public ThreeValuedTruth isValid() {
            return ThreeValuedTruth.FALSIFIABLE;
        }
    }

    public static class SMTCEResult extends SMTFalsifiableResult {
        private final ModelExtractor query;

        private SMTCEResult(SolverType solverType, SMTProblem problem, long timeTaken,
                SolverCommunication solverCommunication, String translation, ModelExtractor query) {
            super(solverType, problem, timeTaken, solverCommunication, translation);
            this.query = query;
        }

        public ModelExtractor getQuery() {
            return query;
        }
    }

    public static class SMTUnknownResult extends SMTSolverResult {
        private SMTUnknownResult(SolverType solverType, SMTProblem problem, long timeTaken,
                SolverCommunication solverCommunication, String translation) {
            super(solverType, problem, timeTaken, solverCommunication, translation);
        }

        @Override
        public ThreeValuedTruth isValid() {
            return ThreeValuedTruth.UNKNOWN;
        }
    }

    public static class SMTExceptionResult extends SMTUnknownResult {
        private final Throwable exception;

        private SMTExceptionResult(SolverType solver, SMTProblem problem, long timeTaken,
                SolverCommunication solverCommunication, String translation, Throwable exception) {
            super(solver, problem, timeTaken, solverCommunication, translation);
            this.exception = exception;
        }

        public Throwable getException() {
            return exception;
        }
    }

    public static class SMTTimeoutResult extends SMTUnknownResult {
        private SMTTimeoutResult(SolverType solver, SMTProblem problem, long timeTaken,
                SolverCommunication solverCommunication, String translation) {
            super(solver, problem, timeTaken, solverCommunication, translation);
        }
    }
}
