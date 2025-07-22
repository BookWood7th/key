package de.uka.ilkd.key.smt.communication.newCommunication;

import de.uka.ilkd.key.smt.communication.newCommunication.commands.SolverCommand;
import de.uka.ilkd.key.smt.communication.newCommunication.commands.StandardCommand;

public interface SMTSerializer {
    String serialize(final StandardCommand standardCommand);

    boolean canSerialize(final SolverCommand command);

    String serialize(final SolverCommand command) throws CommandNotSupportedException;

    SocketMessage decode(String message);
}
