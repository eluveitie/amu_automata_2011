package pl.edu.amu.wmi.daut.base;

import java.util.List;

/**
 * Nieefektywne, przykładowe ukonkretnienie klasy abstrakcyjnej
 * DeterministicAutomatonSpecification.
 *
 *
 */
public class NaiveDeterministicAutomatonSpecification
           extends DeterministicAutomatonSpecification {

    private NaiveAutomatonSpecification automatonSpec = new NaiveAutomatonSpecification();

    @Override
    public List<State> allStates() {
        return automatonSpec.allStates();
    }

    /**
     *
     * @param from
     * @return
     */
    @Override
    public List<OutgoingTransition> allOutgoingTransitions(State from) {
        return automatonSpec.allOutgoingTransitions(from);
    }

    @Override
    public State targetState(State from, char c) {
           for (OutgoingTransition outgoingTransition : allOutgoingTransitions(from)) {
            if (outgoingTransition.getTransitionLabel().canAcceptCharacter(c)) {
                return outgoingTransition.getTargetState();
            }
        }
        return null;
    }

    @Override
    public State addState() {
        return this.automatonSpec.addState();
    }

    @Override
    public void addTransition(State from, State to, TransitionLabel transitionLabel) {
        this.automatonSpec.addTransition(from, to, transitionLabel);
    }

    @Override
    public void markAsInitial(State state) {
        this.automatonSpec.markAsInitial(state);
    }

    @Override
    public void markAsFinal(State state) {
        this.automatonSpec.markAsFinal(state);
    }

    @Override
    public void unmarkAsFinalState(State state) {
        this.automatonSpec.unmarkAsFinalState(state);
    }

    @Override
    public State getInitialState() {
        return this.automatonSpec.getInitialState();
    }

    @Override
    public boolean isFinal(State state) {
        return this.automatonSpec.isFinal(state);
    }
}
