package org.key_project.sed.key.evaluation.wizard.manager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.key_project.sed.key.evaluation.model.definition.AbstractButtonsQuestion;
import org.key_project.sed.key.evaluation.model.definition.Choice;
import org.key_project.sed.key.evaluation.model.input.QuestionInput;
import org.key_project.sed.key.evaluation.wizard.page.QuestionWizardPage;
import org.key_project.sed.key.evaluation.wizard.page.QuestionWizardPage.ICreateControlCallback;
import org.key_project.util.java.ArrayUtil;
import org.key_project.util.java.ObjectUtil;
import org.key_project.util.java.StringUtil;

public abstract class AbstractButtonsManager<Q extends AbstractButtonsQuestion> extends AbstractQuestionInputManager {
   private final QuestionInput questionInput;
   
   private final List<Button> buttons = new LinkedList<Button>();
   
   private final PropertyChangeListener questionListener = new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
         handleQuestionValueChanged(evt);
      }
   };
   
   private final Map<Choice, List<IQuestionInputManager>> choiceManagers = new HashMap<Choice, List<IQuestionInputManager>>();
   
   private Composite composite;
   
   private Section questionSection;
   
   private TrustManager trustManager;
   
   public AbstractButtonsManager(FormToolkit toolkit, 
                                 Composite parent, 
                                 QuestionInput questionInput, 
                                 Q question,
                                 ICreateControlCallback callback) {
      questionInput.addPropertyChangeListener(QuestionInput.PROP_VALUE, questionListener);
      this.questionInput = questionInput;
      composite = toolkit.createComposite(parent);
      if (questionInput.hasChoiceInputs()) {
         createwithChildQuestionControls(toolkit, question, callback);
      }
      else {
         createNoChildQuestionControls(toolkit, question);
      }
   }
   
   protected void createwithChildQuestionControls(FormToolkit toolkit, Q question, ICreateControlCallback callback) {
      composite.setLayout(new GridLayout(1, false));
      createSection(toolkit, composite, question);
      for (final Choice choice : question.getChoices()) {
         createChoiceControl(toolkit, choice, question);
         QuestionInput[] choiceInputs = questionInput.getChoiceInputs(choice);
         if (!ArrayUtil.isEmpty(choiceInputs)) {
            Composite choiceComposite = toolkit.createComposite(composite);
            GridLayout layout = new GridLayout(1, false);
            layout.marginWidth = 0;
            layout.marginHeight = 0;
            layout.marginLeft = 30;
            layout.horizontalSpacing = 0;
            layout.verticalSpacing = 0;
            choiceComposite.setLayout(layout);
            List<IQuestionInputManager> managers = QuestionWizardPage.createQuestionControls(toolkit, choiceComposite, choiceInputs, callback);
            choiceManagers.put(choice, managers);
         }
      }
      updateChoiceChildrenEnabled();
   }
   
   protected void createSection(FormToolkit toolkit, Composite parent, Q question) {
      Composite sectionComposite = toolkit.createComposite(parent);
      sectionComposite.setLayout(new GridLayout(3, false));
      questionSection = toolkit.createSection(sectionComposite, SWT.NONE);
      questionSection.setText(question.getLabel());
      if (question.isAskForTrust()) {
         trustManager = new TrustManager(sectionComposite, questionInput);
      }
   }
   
   protected void createNoChildQuestionControls(FormToolkit toolkit, Q question) {
      if (question.getLabel() != null) {
         if (question.isVertical()) {
            composite.setLayout(new GridLayout(1, false));
         }
         else {
            composite.setLayout(new GridLayout(question.countChoices() + 1, false));
         }
         createSection(toolkit, composite, question);
      }
      else {
         if (question.isVertical()) {
            composite.setLayout(new GridLayout(1, false));
         }
         else {
            composite.setLayout(new GridLayout(question.countChoices(), false));
         }
      }
      for (final Choice choice : question.getChoices()) {
         createChoiceControl(toolkit, choice, question);
      }
   }
   
   protected void createChoiceControl(final FormToolkit toolkit, final Choice choice, final Q question) {
      final Button button = toolkit.createButton(composite, choice.getText(), getButtonStyle());
      button.setData(choice.getValue());
      if (!StringUtil.isEmpty(choice.getTooltip())) {
         button.setToolTipText(choice.getTooltip() + StringUtil.NEW_LINE + question.getLabel());
      }
      else {
         button.setToolTipText(question.getLabel());
      }
      if (isChoiceSelected(questionInput.getValue(), choice.getValue())) {
         button.setSelection(true);
      }
      button.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            handleButtonSelected(questionInput, button, choice);
         }
      });
      buttons.add(button);
   }
   
   protected abstract boolean isChoiceSelected(String inputValue, String choiceValue);

   protected abstract int getButtonStyle();

   protected abstract void handleButtonSelected(QuestionInput questionInput, Button button, Choice choice);

   protected void handleQuestionValueChanged(PropertyChangeEvent evt) {
      for (Button button : buttons) {
         if (isChoiceSelected(ObjectUtil.toString(evt.getNewValue()),
                              ObjectUtil.toString(button.getData()))) {
            button.setSelection(true);
         }
         else {
            button.setSelection(false);
         }
      }
      updateChoiceChildrenEnabled();
   }
   
   @Override
   public void dispose() {
      questionInput.removePropertyChangeListener(QuestionInput.PROP_VALUE, questionListener);
      for (List<IQuestionInputManager> managers : choiceManagers.values()) {
         for (IQuestionInputManager manager : managers) {
            manager.dispose();
         }
      }
      if (trustManager != null) {
         trustManager.dispose();
      }
   }

   public Composite getComposite() {
      return composite;
   }

   @Override
   protected void enableControls(boolean enabled) {
      if (questionSection != null) {
         questionSection.setEnabled(enabled);
      }
      for (Button button : buttons) {
         button.setEnabled(enabled);
      }
      if (trustManager != null) {
         trustManager.setEnabled(enabled);
      }
      updateChoiceChildrenEnabled();
   }
   
   protected void updateChoiceChildrenEnabled() {
      for (Entry<Choice, List<IQuestionInputManager>> entry : choiceManagers.entrySet()) {
         boolean enabled = isEnabled() && isChoiceSelected(questionInput.getValue(), entry.getKey().getValue());
         for (IQuestionInputManager manager : entry.getValue()) {
            manager.setEnabled(enabled);
         }
      }
   }
}