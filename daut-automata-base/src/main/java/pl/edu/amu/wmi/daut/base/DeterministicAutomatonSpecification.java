package pl.edu.amu.wmi.daut.base;

import java.util.ArrayList;
import java.util.List;

abstract class DeterministicAutomatonSpecification extends AutomatonSpecification {
    /**
     * Zwraca stan, jaki zostanie osiągnięty przy przejściu ze stanu
     * from przez znak c. Zwraca null, jeśli nie istnieje przejście
     * ze stanu from przez znak c.
     */
    public abstract State targetState(State from, char c);
    /**
     * Zwraca liste stanów z których przejścia wychodzą do podanego stanu.
     */
    public  List<State> findPreviousState(State nextState) {

        List<State> previousStates = new ArrayList<State>();

        for (State state : allStates()) {
            for (OutgoingTransition transition : allOutgoingTransitions(state)) {
                if (transition.getTargetState() == nextState) {
                    previousStates.add(state);
                }
            }
        }
        return previousStates;
    }
    /**
     * Pobiera dwa stany, zwraca przejścia między nimi.
     */
    public List<OutgoingTransition> findPreviousStateTransitions(
            State previousState, State nextState) {

        List<OutgoingTransition> needTransitions = new ArrayList<OutgoingTransition>();

        for (OutgoingTransition transition : allOutgoingTransitions(previousState)) {
            if (transition.getTargetState() == nextState)
                needTransitions.add(transition);
        }
        return needTransitions;
    }
    /**
     * Usuwa zbędne stany. To znaczy takie, do których nie można,
     * dojść ze stanu początkowego.
     */
    public void deleteUselessStates() {

        State startState = getInitialState();

        List<State> startStates = new ArrayList<State>();
        List<State> uselessStates = new ArrayList<State>();

        uselessStates.addAll(allStates());
        uselessStates.remove(getInitialState());

        for (OutgoingTransition transition : allOutgoingTransitions(startState)) {
            startStates.add(transition.getTargetState());
            uselessStates.remove(transition.getTargetState());
        }

        while (!startStates.isEmpty()) {
            for (int i = 0; i < startStates.size(); i++) {
                if (i > startStates.size())
                    break;
                for (OutgoingTransition transition : allOutgoingTransitions(
                        startStates.get(i))) {
                    if (uselessStates.contains(transition.getTargetState())) {
                        startStates.add(transition.getTargetState());
                        uselessStates.remove(transition.getTargetState());
                    }
                }
                startStates.remove(startStates.get(i));
            }
        }

        if (!uselessStates.isEmpty())
            allStates().removeAll(uselessStates);

    }
    /**
     * Pobiera automat na wejsciu.
     * Zwraca zminimalizowany automat
     */
    public DeterministicAutomatonSpecification makeMinimal() {

        DeterministicAutomatonSpecification returnAutomaton = this;

        deleteUselessStates();

        int size = returnAutomaton.allStates().size() - 1;
        boolean[][] mark = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int a = 0; a < size; a++)
            mark[i][a] = true;
        }

        for (int i = 0; i < size; i++) {
            if (i + 1 < size) {
                for (int a = (i + 1); a < size; a++) {
                    if (returnAutomaton.isFinal(returnAutomaton.allStates().get(i))
                            && !returnAutomaton.isFinal(returnAutomaton.allStates().get(a))) {
                        mark[i][a] = false;
                        break;
                    }
                    if (!returnAutomaton.isFinal(returnAutomaton.allStates().get(i))
                            && returnAutomaton.isFinal(returnAutomaton.allStates().get(a))) {
                        mark[i][a] = false;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            if (i + 1 < size) {
                for (int a = (i + 1); a < size; a++) {
                    for (OutgoingTransition itransition : returnAutomaton
                            .allOutgoingTransitions(returnAutomaton.allStates().get(i))) {
                        for (OutgoingTransition atransition : returnAutomaton
                                .allOutgoingTransitions(returnAutomaton.allStates().get(a))) {
                            State state1 = itransition.getTargetState();
                            State state2 = atransition.getTargetState();
                            if (state1 == state2)
                                break;
                            if (!mark[returnAutomaton.allStates().indexOf(state1) - 1]
                                    [returnAutomaton.allStates().indexOf(state2) - 1])
                                mark[i][a] = false;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < size; i++)
            mark[i][i] = false;
        for (int i = 0; i < size; i++) {
            if (i + 1 < size) {
                for (int a = (i + 1); a < size; a++) {
                    if (mark[i][a]) {
                        List<State> prevStates;
                        List<OutgoingTransition> prevTransitions;
                        prevStates = returnAutomaton.findPreviousState(returnAutomaton
                                .allStates().get(a));
                        for (State state : prevStates) {
                            prevTransitions = findPreviousStateTransitions(state,
                                    returnAutomaton.allStates().get(a));
                            for (OutgoingTransition transition : prevTransitions)
                                returnAutomaton.addTransition(
                                        state, returnAutomaton.allStates().get(i),
                                        transition.getTransitionLabel());
                            }
                        returnAutomaton.allStates().remove(a);
                    }
                }
            }
        }

        return returnAutomaton;
    }
}
