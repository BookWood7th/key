package de.uka.ilkd.key.smt.communication.newCommunication.commands;

public sealed interface StandardCommand extends SolverCommand permits CheckSatCommand, ExitCommand {

}
