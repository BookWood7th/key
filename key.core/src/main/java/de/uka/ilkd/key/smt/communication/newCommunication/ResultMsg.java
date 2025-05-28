package de.uka.ilkd.key.smt.communication.newCommunication;

import de.uka.ilkd.key.smt.SMTSolverResult;

public record ResultMsg(SMTSolverResult.ThreeValuedTruth result) implements SocketMessage {
}
