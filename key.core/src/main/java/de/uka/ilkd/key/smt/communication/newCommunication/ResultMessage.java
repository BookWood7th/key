package de.uka.ilkd.key.smt.communication.newCommunication;

import de.uka.ilkd.key.smt.SMTSolverResult;

public record ResultMessage(SMTSolverResult.ThreeValuedTruth result) implements SocketMessage {
}
