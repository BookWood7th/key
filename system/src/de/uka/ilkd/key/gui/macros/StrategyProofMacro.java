package de.uka.ilkd.key.gui.macros;

import de.uka.ilkd.key.collection.ImmutableList;
import de.uka.ilkd.key.gui.InteractiveProver;
import de.uka.ilkd.key.gui.KeYMediator;
import de.uka.ilkd.key.gui.ProverTaskListener;
import de.uka.ilkd.key.gui.TaskFinishedInfo;
import de.uka.ilkd.key.logic.PosInOccurrence;
import de.uka.ilkd.key.proof.Goal;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.strategy.AutomatedRuleApplicationManager;
import de.uka.ilkd.key.strategy.FocussedRuleApplicationManager;
import de.uka.ilkd.key.strategy.Strategy;

/**
 * The abstract class StrategyProofMacro can be used to define proof macros
 * which use their own strategy.
 * 
 * In order to implement a {@link StrategyProofMacro}, override
 * {@link #createStrategy(KeYMediator, PosInOccurrence)}.
 * 
 * This class is aware of Position in occurrences and can also be applied to
 * inner nodes. Both {@link AutomatedRuleApplicationManager} and
 * {@link Strategy} are changed for the course of the macro but are restored
 * afterwards using a {@link ProverTaskListener}.
 * 
 * @see ProverTaskListener
 * @see Strategy
 */
public abstract class StrategyProofMacro implements ProofMacro {

    protected abstract Strategy createStrategy(KeYMediator mediator, PosInOccurrence posInOcc);
    
    /**
     * When a prove run is finished, we need to reset the goals' rule
     * application managers using this listener.
     */
    private static class StopListener implements ProverTaskListener {

        private final InteractiveProver interactiveProver;
        private final Strategy oldStrategy;

        public StopListener(InteractiveProver interactiveProver, Strategy oldStrategy) {
            this.interactiveProver = interactiveProver;
            this.oldStrategy = oldStrategy;
        }

        @Override
        public void taskStarted(String message, int size) {
        }

        @Override 
        public void taskProgress(int position) {
        }

        @Override 
        public void taskFinished(TaskFinishedInfo info) {
            Proof proof = interactiveProver.getProof();
            for (final Goal goal : proof.openGoals()) {
                AutomatedRuleApplicationManager manager = goal.getRuleAppManager();
                // touch the manager only if necessary
                if(manager.getDelegate() != null) {
                    while(manager.getDelegate() != null) {
                        manager = manager.getDelegate();
                    }
                    manager.clearCache();
                    goal.setRuleAppManager(manager);
                }
            }

            proof.setActiveStrategy(oldStrategy);
            interactiveProver.removeProverTaskListener(this);
        }
    }

    /** 
     * {@inheritDoc}
     * 
     * This macro can always be applied (does not change anything perhaps)
     * 
     * TODO make this only applicable if it has an impact.
     * 
     */
    @Override 
    public boolean canApplyTo(KeYMediator mediator, PosInOccurrence posInOcc) {
        return true;
    }
    
    /*
     * Set a new rule app manager similar to the focussed mode.
     * Set a new strategy which only allows for the named admitted rules.
     * Then run automation mode and in the end reset the managers.
     * and the strategy
     */
    @Override 
    public void applyTo(KeYMediator mediator, PosInOccurrence posInOcc) {
        InteractiveProver interactiveProver = mediator.getInteractiveProver();
        Goal goal = mediator.getSelectedGoal();

        // add a focus manager if there is a focus
        if(posInOcc != null) {
            AutomatedRuleApplicationManager realManager = goal.getRuleAppManager();
            realManager.clearCache();
            FocussedRuleApplicationManager manager = 
                    new FocussedRuleApplicationManager(realManager, goal, posInOcc);
            goal.setRuleAppManager(manager);
        }

        // set a new strategy.
        Proof proof = interactiveProver.getProof();
        Strategy oldStrategy = proof.getActiveStrategy();
        proof.setActiveStrategy(createStrategy(mediator, posInOcc));

        // this resets the proof strategy and the managers after the automation
        // has run
        interactiveProver.addProverTaskListener(
                new StopListener(interactiveProver, oldStrategy));

        // find the relevant goals
        // and start
        ImmutableList<Goal> goals = proof.getSubtreeEnabledGoals(mediator.getSelectedNode());
        interactiveProver.startAutoMode(goals);
    }

}