package pl.edu.amu.wmi.daut.base;

import java.util.List;
import java.util.LinkedList;

/**
 * Ukonkretnienie klasy abstrakcyjnej AutomatonSpecification.
 */
class NotNaiveAutomatonSpecification extends AutomatonSpecification {

    static class NotNaiveState implements State {

        /**
         * Konstruuje stan.
         */
        public NotNaiveState() {
        }

        public List<OutgoingTransition> returnOutgoingTrasitions() {
            return outgoingTransitions;
        }
        private LinkedList<OutgoingTransition> outgoingTransitions
                = new LinkedList<OutgoingTransition>();
    }

    public NotNaiveState addState() {
        NotNaiveState newState = new NotNaiveState();
        allStates.add(newState);
        return newState;
    }

    public void addTransition(State from, State to, TransitionLabel transitionLabel) {
        ((NotNaiveState) from).outgoingTransitions.add(new
                OutgoingTransition(transitionLabel, (State) to));
    }

    public void markAsInitial(State state) {
        initialState = (NotNaiveState) state;
    }

    public void markAsFinal(State state) {
        finalStates.add((NotNaiveState) state);
    }

    public void unmarkAsFinalState(State state) {
        finalStates.remove((NotNaiveState) state);
    }

    public List<State> allStates() {
        return allStates;
    }

    public List<OutgoingTransition> allOutgoingTransitions(State from) {
        return ((NotNaiveState) from).outgoingTransitions;
    }

    public State getInitialState() {
        return initialState;
    }

    public boolean isFinal(State state) {
        for (NotNaiveState someState : finalStates) {
            if (someState == state) {
                return true;
            }
        }

        return false;
    }

    public List<OutgoingTransition> getOutReturnOutgoingTransitions(State from) {
        return ((NotNaiveState) from).returnOutgoingTrasitions();
    }

    private LinkedList<State> allStates = new LinkedList<State>();
    private NotNaiveState initialState;
    private LinkedList<NotNaiveState> finalStates = new LinkedList<NotNaiveState>();
}
