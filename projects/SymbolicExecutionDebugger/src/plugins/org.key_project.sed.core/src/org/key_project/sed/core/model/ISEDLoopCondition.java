package org.key_project.sed.core.model;

import org.eclipse.debug.core.model.IStackFrame;
import org.key_project.sed.core.model.impl.AbstractSEDLoopCondition;
import org.key_project.sed.core.model.memory.SEDMemoryLoopCondition;

/**
 * A node in the symbolic execution tree which represents a loop condition,
 * e.g. {@code x >= 0}.
 * <p>
 * A symbolic branch node is also a normal stack frame ({@link IStackFrame})
 * for compatibility reasons with the Eclipse debug API.
 * </p>
 * <p>
 * Clients may implement this interface. It is recommended to subclass
 * from {@link AbstractSEDLoopCondition} instead of implementing this
 * interface directly. {@link SEDMemoryLoopCondition} is also a default
 * implementation that stores all values in the memory.
 * </p>
 * @author Martin Hentschel
 * @see ISEDDebugNode
 */
public interface ISEDLoopCondition extends ISEDDebugNode, IStackFrame {

}