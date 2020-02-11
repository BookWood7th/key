package org.key_project.proofmanagement.check.dependency;

import de.uka.ilkd.key.proof.io.intermediate.NodeIntermediate;

public abstract class NodeIntermediateWalker {
    /** the root the walker starts */
    private NodeIntermediate root;

    /** create the Walker
     * @param root the NodeIntermediate where to begin
     */
    public NodeIntermediateWalker(NodeIntermediate root) {
        this.root = root;
    }

    /** starts the walker*/
    public void start() {
        walk(root);
    }

    /** walks through the AST. While keeping track of the current node
     * @param node the JavaProgramElement the walker is at 
     */
    protected void walk(NodeIntermediate node) {
        doAction(node);
        
        for (NodeIntermediate child : node.getChildren()) {
            walk(child);
        }
    }

    /** the action that is performed just before leaving the node the
     * last time 
     */
    protected abstract void doAction(NodeIntermediate node);
}
