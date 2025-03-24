package org.key_project.jmlsurgeon;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.jml.NodeWithContracts;
import com.github.javaparser.ast.jml.clauses.JmlContract;

public class LoopContractInsertionVisitor extends AbstractLoopVisitor {
    private final NodeList<Node> contracts;
    private final String methodName;
    private final String marker;

    public LoopContractInsertionVisitor(String methodName , NodeList<Node> contracts, String marker) {
        this.contracts = contracts;
        this.methodName = methodName;
        this.marker = marker;
    }

    @Override
    protected <T extends Node & NodeWithContracts<T>> T visitLoop(T n, T result) {
        var loopComment = n.getComment();
        if (loopComment.isEmpty()) {
            return result;
        }
        var commentText = loopComment.get().getContent();
        if (n.getContracts().isEmpty() && commentText.contains(this.marker) && isDesiredMethod(n)) {
            result.setContracts(contracts.stream().filter((x) -> x instanceof JmlContract).map((x) -> (JmlContract) x).collect(NodeList::new, NodeList::add, NodeList::addAll));
            result.setComment(null);
        }
        return result;
    }

    private boolean isDesiredMethod(Node n) {
        Node parent = n;
        do {
            parent = parent.getParentNode().orElse(null);
        } while (!(parent instanceof MethodDeclaration) && parent != null);
        if (parent == null) {
            return false;
        } else {
            return ((MethodDeclaration) parent).getNameAsString().equals(methodName);
        }
    }

}
