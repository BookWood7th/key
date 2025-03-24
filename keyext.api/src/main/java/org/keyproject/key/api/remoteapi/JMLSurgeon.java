package org.keyproject.key.api.remoteapi;

import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;

import java.util.concurrent.CompletableFuture;

@JsonSegment("surgeon")
public interface JMLSurgeon {
    @JsonRequest
    CompletableFuture<String> insertInvariant(String file, String methodName, String marker, String invariant);
}
