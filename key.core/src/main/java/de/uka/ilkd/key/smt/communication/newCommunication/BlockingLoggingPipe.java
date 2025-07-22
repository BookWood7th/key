package de.uka.ilkd.key.smt.communication.newCommunication;

import de.uka.ilkd.key.smt.communication.SolverCommunication;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class BlockingLoggingPipe implements AutoCloseable, Pipe {
    private final BufferedMessageReader reader;
    private final BufferedWriter writer;
    private boolean closed = false;

    private final SolverCommunication session;

    public BlockingLoggingPipe(InputStream in, OutputStream out, SolverCommunication session, String[] messageDelimiters) {
        this.reader = new BufferedMessageReader(new InputStreamReader(in, StandardCharsets.UTF_8), messageDelimiters);
        this.writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        this.session = session;
    }

    @Override
    public synchronized void sendMessage(@NonNull String message) throws IOException {
        throwIfClosed();
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
            session.addMessage(message, SolverCommunication.MessageType.OUTPUT);
        } catch (IOException e) {
            close();
            throw e;
        }
    }

    @Override
    public synchronized @Nullable String readMessage() throws IOException, InterruptedException {
        throwIfClosed();
        try {
            String message = reader.readMessage();
            if (message == null) {
                throw new IOException("End of stream reached");
            }
            session.addMessage(message, SolverCommunication.MessageType.INPUT);
            return message;
        } catch (IOException e) {
            close();
            throw e;
        }
    }

    @Override
    public @NonNull SolverCommunication getSolverCommunication() {
        return session;
    }

    @Override
    public synchronized void close() throws IOException {
        if (closed) return;
        closed = true;
        try {
            reader.close();
        } finally {
            writer.close();
        }
    }

    private void throwIfClosed() throws IOException {
        if (closed) {
            throw new IOException("Pipe is closed");
        }
    }
}
