package de.uka.ilkd.key.smt.communication.newCommunication;

import de.uka.ilkd.key.smt.communication.SolverCommunication;

import java.io.IOException;

public final class SMTSolverSocket implements AutoCloseable {
    private final String[] commands;
    private final ExternalProcessLauncher launcher;

    public SMTSolverSocket(String[] commands, String[] messageDelimiters, SolverCommunication solverCommunication) {
        this.launcher = new ExternalProcessLauncher(solverCommunication, messageDelimiters);
        this.commands = commands;
    }

    public void open() throws IOException {
        launcher.launch(commands);
    }

    public void sendMessage(String message) throws IOException {
        launcher.getPipe().sendMessage(message);
    }

    public String readMessage() throws IOException, InterruptedException {
        return launcher.getPipe().readMessage();
    }

    @Override
    public void close() {
        launcher.stop();
    }
}
