package de.uka.ilkd.key.smt.communication.newCommunication;

public interface SMTResponseDecoder {
    SocketMessage decode(String message);
}
