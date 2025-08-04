package de.uka.ilkd.key.smt.communication.newCommunication;

import de.uka.ilkd.key.smt.communication.SolverCommunication;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class BlockingLoggingPipe implements AutoCloseable, Pipe {
    private final BufferedMessageReader reader;
    private final BufferedMessageReader errorReader;
    private final BufferedWriter writer;
    private boolean closed = false;

    private final SolverCommunication session;

    private final ExecutorService executor;

    private final BlockingQueue<String> processMessageQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<IOException> processMessageErrors = new LinkedBlockingQueue<>();

    private final BlockingQueue<String> processErrorQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<IOException> processErrorErrors = new LinkedBlockingQueue<>();


    public BlockingLoggingPipe(InputStream in, OutputStream out, InputStream error, SolverCommunication session, String[] messageDelimiters) {
        this.reader = new BufferedMessageReader(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)), messageDelimiters);
        this.writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        this.errorReader = new BufferedMessageReader(new BufferedReader(new InputStreamReader(error, StandardCharsets.UTF_8)), messageDelimiters);

        this.session = session;

        this.executor = Executors.newVirtualThreadPerTaskExecutor();

        executor.submit(() -> {
            try {
                String message;
                while ((message = reader.readMessage()) != null) {
                    processMessageQueue.add(message);
                    session.addMessage(message, SolverCommunication.MessageType.OUTPUT);
                }
            } catch (IOException e) {
                processMessageErrors.add(e);
            }
        });

        executor.submit(() -> {
            try {
                String errorMessage;
                while ((errorMessage = errorReader.readMessage()) != null) {
                    errorMessage = "ERR: " + errorMessage;
                    processErrorQueue.add(errorMessage);
                    session.addMessage(errorMessage, SolverCommunication.MessageType.ERROR);
                }
            } catch (IOException e) {
                processErrorErrors.add(e);
            }
        });
    }

    @Override
    public void sendMessage(@NonNull String message) throws IOException {
        throwIfClosed();
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
            session.addMessage(message, SolverCommunication.MessageType.INPUT);
        } catch (IOException e) {
            close();
            throw e;
        }
    }

    @Override
    public @Nullable String readMessage() throws IOException, InterruptedException {
        throwIfClosed();
        throwAnyErrors();


        CompletableFuture<String> futureMessage = CompletableFuture.supplyAsync(() -> {
            try {
                return processMessageQueue.take();
            } catch (InterruptedException e) {
                throw new CompletionException(e);
            }
        }, executor);
        CompletableFuture<String> futureErrorMessage = CompletableFuture.supplyAsync(() -> {
            try {
                return processErrorQueue.take();
            } catch (InterruptedException e) {
                throw new CompletionException(e);
            }
        }, executor);

        CompletableFuture<String> nextMessageFuture = CompletableFuture.anyOf(futureMessage, futureErrorMessage).thenApply(result -> (String) result);

        try {
            String message = nextMessageFuture.get();
            futureMessage.cancel(true);
            futureErrorMessage.cancel(true);

            return message;
        } catch (ExecutionException e) {
            if (e.getCause() instanceof CompletionException) {
                if (e.getCause().getCause() instanceof InterruptedException) {
                    throw new InterruptedException();
                }
            }
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

    private void throwAnyErrors() throws IOException {
        if (!processMessageErrors.isEmpty()) {
            throw processMessageErrors.poll();
        }
        if (!processErrorErrors.isEmpty()) {
            throw processErrorErrors.poll();
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
