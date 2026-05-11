/* This file is part of KeY - https://key-project.org
 * KeY is licensed under the GNU General Public License Version 2
 * SPDX-License-Identifier: GPL-2.0-only */
package org.keyproject.key.api.remoteapi;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;

@JsonSegment("surgeon")
public interface JMLSurgeon {
    @JsonRequest
    CompletableFuture<String> insertInvariant(String file, String methodName, String marker,
            String invariant);

    @JsonRequest
    CompletableFuture<List<String>> getIllegallyChangedContracts(String program,
            String annotatedProgram);

    @JsonRequest
    CompletableFuture<Boolean> checkNoAssumesInAnnotations(String program, String annotatedProgram);

    @JsonRequest
    CompletableFuture<String> getFirstChangedCodeSnippet(String program, String annotatedProgram);

    @JsonRequest
    CompletableFuture<List<String>> getLoopsWithoutInvariants(String program);

    @JsonRequest
    CompletableFuture<List<String>> getLoopsWithoutDecreases(String program);
}
