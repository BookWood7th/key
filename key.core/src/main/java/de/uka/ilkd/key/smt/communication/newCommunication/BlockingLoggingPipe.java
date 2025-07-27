package de.uka.ilkd.key.smt.communication.newCommunication;

import de.uka.ilkd.key.smt.communication.SolverCommunication;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class BlockingLoggingPipe implements AutoCloseable, Pipe {
    private final BufferedMessageReader reader;
    private final BufferedWriter writer;
    private boolean closed = false;

    private final SolverCommunication session;

    private final ExecutorService executor;

    public BlockingLoggingPipe(InputStream in, OutputStream out, SolverCommunication session, String[] messageDelimiters) {
        this.reader = new BufferedMessageReader(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)), messageDelimiters);
        this.writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        this.session = session;

        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public void sendMessage(@NonNull String message) throws IOException {
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
    public @Nullable String readMessage() throws IOException, InterruptedException {
        throwIfClosed();
        Future<String> futureMessage = executor.submit(reader::readMessage);

        try {
            return futureMessage.get();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new RuntimeException("Unexpected exception during reading", e.getCause());
        } catch (CancellationException e) {
            throw new IOException("Cancellation during reading", e);
        } catch (InterruptedException e) {
            futureMessage.cancel(true);
            throw e;
        }
    }

    @Override
    public @NonNull SolverCommunication getSolverCommunication() {
        return session;
    }

    @Override
    public void close() throws IOException {
        if (closed) return;
        executor.shutdownNow();
        try {
            reader.close();
        } finally {
            writer.close();
        }
        closed = true;
    }

    private void throwIfClosed() throws IOException {
        if (closed) {
            throw new IOException("Pipe is closed");
        }
    }
}
