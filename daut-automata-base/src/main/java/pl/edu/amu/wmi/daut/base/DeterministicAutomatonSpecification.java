package pl.edu.amu.wmi.daut.base;

import java.util.ArrayList;
import java.util.HashMap;
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

    public  List<State> findPreviousState(State nextState,
            DeterministicAutomatonSpecification automaton) {

        List<State> previousStates = new ArrayList<State>();

        for (State state : automaton.allStates()) {
            for (OutgoingTransition transition : automaton.allOutgoingTransitions(state)) {
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
            State previousState, State nextState,
            DeterministicAutomatonSpecification automaton) {

        List<OutgoingTransition> needTransitions = new ArrayList<OutgoingTransition>();

        for (OutgoingTransition transition : automaton.allOutgoingTransitions(previousState)) {
            if (transition.getTargetState() == nextState)
                needTransitions.add(transition);
        }
        return needTransitions;
    }
    /**
     * Usuwa zbędne stany. To znaczy takie, do których nie można,
     * dojść ze stanu początkowego.
     */
    public List<State> returnUselessStates() {

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

        return uselessStates;

    }

    private void buildMinimal(DeterministicAutomatonSpecification automatonToBeMinimized,
            HashMap<State, State> similarStates) {

        List<State> states = automatonToBeMinimized.allStates();
        List<State> prevStates;
        List<OutgoingTransition> prevTransitions;

        HashMap<State, State> getOldStates = new HashMap<State, State>();
        HashMap<State, State> getStates = new HashMap<State, State>();

        for (State state : states) {
            if (!similarStates.containsKey(state) && !similarStates.containsValue(state)) {
                State newState = this.addState();
                getOldStates.put(newState, state);
                getStates.put(state, newState);
                if (state == automatonToBeMinimized.getInitialState())
                    this.markAsInitial(newState);
                if (automatonToBeMinimized.isFinal(state))
                    this.markAsFinal(newState);
            }
        }

        for (State state : allStates()) {
            List<OutgoingTransition> transitions = automatonToBeMinimized
                    .allOutgoingTransitions(getOldStates.get(state));
            for (OutgoingTransition transition : transitions) {
                this.addTransition(state, getStates
                        .get(transition.getTargetState()),
                        transition.getTransitionLabel());
            }
        }


        HashMap<State, State> simStates = new HashMap<State, State>(similarStates);


        for (State state : simStates.keySet()) {
            for (State state1 : simStates.keySet()) {
                if ((similarStates.get(state) == similarStates.get(state1)) && !(state == state1)) {
                    if ((automatonToBeMinimized.getInitialState() == state1)
                            || (automatonToBeMinimized
                            .getInitialState() == similarStates.get(state1))) {
                        similarStates.remove(state);
                        break;
                    } else {
                        similarStates.remove(state1);
                        continue;
                    }
                }
            }
        }


        for (State state : similarStates.keySet()) {
            State newState = addState();
            getStates.put(state, newState);
            getStates.put(similarStates.get(state), newState);
            if (state == automatonToBeMinimized.getInitialState())
                this.markAsInitial(newState);
            if (automatonToBeMinimized.isFinal(state))
                this.markAsFinal(newState);
            prevStates = automatonToBeMinimized.findPreviousState(state,
                    automatonToBeMinimized);
            for (State prevState : prevStates) {
                prevTransitions = findPreviousStateTransitions(prevState,
                        state, automatonToBeMinimized);
                for (OutgoingTransition transition : prevTransitions)
                    addTransition(getStates.get(prevState), newState,
                            transition.getTransitionLabel());
                prevTransitions = findPreviousStateTransitions(prevState,
                        similarStates.get(state), automatonToBeMinimized);
                for (OutgoingTransition transition : prevTransitions)
                    addTransition(getStates.get(prevState), newState,
                                    transition.getTransitionLabel());

            }
            for (OutgoingTransition transition : automatonToBeMinimized
                    .allOutgoingTransitions(state))
                addTransition(newState, getStates.get(transition.getTargetState()),
                        transition.getTransitionLabel());
            for (OutgoingTransition transition : automatonToBeMinimized
                    .allOutgoingTransitions(similarStates.get(state)))
                addTransition(newState, getStates.get(transition.getTargetState()),
                        transition.getTransitionLabel());
        }

    }

    /**
     * Pobiera automat na wejsciu.
     * Zwraca zminimalizowany automat
     */
    public void makeMinimal(
            DeterministicAutomatonSpecification automatonToBeMinimized,
            String alphabet) {

        List<State> states = automatonToBeMinimized.allStates();
        List<State> uselessStates =  automatonToBeMinimized.returnUselessStates();
        HashMap<State, Integer> indexStates = new HashMap<State, Integer>();
        HashMap<State, State> similarStates = new HashMap<State, State>();


        if (!uselessStates.isEmpty()) {
            states.removeAll(uselessStates);
        }


        int index = 0;
        boolean changed;


        for (State state : states) {
            indexStates.put(state, index);
            index++;
        }


        int size = states.size();
        boolean[][] mark = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
            mark[i][j] = true;
        }

        for (int i = 0; i < size - 1; i++) {
            for (int j = (i + 1); j < size; j++) {
                if (((automatonToBeMinimized.isFinal(states.get(i))
                        && !automatonToBeMinimized.isFinal(states.get(j))))
                        || ((!automatonToBeMinimized.isFinal(states.get(i))
                        && automatonToBeMinimized.isFinal(states.get(j))))) {
                    mark[i][j] = false;
                    mark[j][i] = false;
                }
            }
        }

        do {
            changed = false;
            for (int c = 0; c < alphabet.length(); c++) {
                for (int i = 0; i < (size - 1); i++) {
                    for (int j = (i + 1); j < size; j++) {
                        for (OutgoingTransition itransition : automatonToBeMinimized
                                .allOutgoingTransitions(states.get(i))) {
                            for (OutgoingTransition atransition : automatonToBeMinimized
                                    .allOutgoingTransitions(states.get(j))) {
                                if (!((itransition.getTransitionLabel()
                                        .canAcceptCharacter(alphabet.charAt(c)))
                                && (atransition.getTransitionLabel()
                                        .canAcceptCharacter(alphabet
                                        .charAt(c))))) {
                                    continue;
                                }
                                State state1 = itransition.getTargetState();
                                State state2 = atransition.getTargetState();
                                if (state1 == state2)
                                    break;
                                if (!mark[indexStates.get(state1)]
                                        [indexStates.get(state2)] && mark[i][j]) {
                                    mark[i][j] = false;
                                    mark[j][i] = false;
                                    changed = true;
                                }
                            }
                        }
                    }
                }
            }
        } while(changed);

        for (int i = 0; i < (size - 1); i++) {
            for (int j = (i + 1); j < size; j++) {
                if (mark[i][j]) {
                    similarStates.put(states.get(i), states.get(j));
                }
            }
        }

        buildMinimal(automatonToBeMinimized, similarStates);

    }
}
