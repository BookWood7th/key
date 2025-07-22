package de.uka.ilkd.key.smt.communication.newCommunication;

import de.uka.ilkd.key.smt.SMTSolverResult;
import de.uka.ilkd.key.smt.communication.newCommunication.commands.*;

public class Z3Serializer implements SMTSerializer {
    @Override
    public String serialize(StandardCommand standardCommand) {
        return switch (standardCommand) {
            case CheckSatCommand checkSatCommand -> "(check-sat)";
            case ExitCommand exitCommand -> "(exit)";
        };
    }

    @Override
    public boolean canSerialize(SolverCommand command) {
        return switch (command) {
            case StandardCommand standardCommand -> true;
            case GetModelCommand getModelCommand -> true;
            default -> false;
        };
    }

    @Override
    public String serialize(SolverCommand command) throws CommandNotSupportedException {
        return switch (command) {
            case StandardCommand standardCommand -> serialize(standardCommand);
            case GetModelCommand getModelCommand -> "(get-model)";
            default -> throw new CommandNotSupportedException("Unsupported command: " + command);
        };
    }

    @Override
    public SocketMessage decode(String message) {
        return switch (message) {
            case "sat" -> new ResultMessage(SMTSolverResult.ThreeValuedTruth.VALID);
            case "unsat" -> new ResultMessage(SMTSolverResult.ThreeValuedTruth.FALSIFIABLE);
            case "unknown" -> new ResultMessage(SMTSolverResult.ThreeValuedTruth.UNKNOWN);
            default -> new EventMsg(message);
        };
    }
}
