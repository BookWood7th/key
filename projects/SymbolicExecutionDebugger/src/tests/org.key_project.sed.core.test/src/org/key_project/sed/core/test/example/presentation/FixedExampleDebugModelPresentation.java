package org.key_project.sed.core.test.example.presentation;

import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.ui.IEditorInput;
import org.key_project.sed.ui.presentation.AbstractSEDDebugModelPresentation;

/**
 * {@link IDebugModelPresentation} for the fixed example.
 * @author Martin Hentschel
 */
public class FixedExampleDebugModelPresentation extends AbstractSEDDebugModelPresentation implements IDebugModelPresentation {
   /**
    * {@inheritDoc}
    */
   @Override
   public IEditorInput getEditorInput(Object element) {
      return null;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public String getEditorId(IEditorInput input, Object element) {
      return null;
   }
}