package pl.edu.amu.wmi.daut.base;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa zwierająca operacje na automatach.
 */
public class AutomataOperations {

    /**
     * Klasa reprezentuje stan C powstały poprzez połączenie stan�lw A i B w wyniku operacji
     * intersection.
     */
    private static final class CombinedState {

        /**
         * Przypisuje stanowi C jego składowe stany A i B.
         */
        public void set(State a, State b) {
            qA = a;
            qB = b;
        }
        @Override
        public String toString() {
            return "A" + String.valueOf(qA.hashCode()) + "B" + String.valueOf(qB.hashCode());
        }
        public State getB() {
            return qB;
        }
        public State getA() {
            return qA;
        }
        private State qA;
        private State qB;
    }

    /**
     *Metoda zwraca automat akceptujący odwr�lcenie języka,
     * akceptowanego przez dany automat "parent".
     */
    public static AutomatonSpecification reverseLanguageAutomat(
            NaiveAutomatonSpecification parentAutomaton) {

        NaiveAutomatonSpecification childAutomaton = new NaiveAutomatonSpecification();
        
        if (parentAutomaton.isEmpty()) { return childAutomaton; }

        List<State> parentStates = new ArrayList<State>();
        List<State> childStates = new ArrayList<State>();
        parentStates.addAll(parentAutomaton.allStates());

        //utworz sztucznie stan poczatkowy. bedzie laczony przez epsilon ze stanami koncowymi automatu wejsciowego.
        State initialChildState = childAutomaton.addState(); 
        childStates.add(initialChildState);
        childAutomaton.markAsInitial(initialChildState);
        
        //zadeklaruj tabelke translacji stanow z automatu wejsciowego na stany z automatu wyjsciowego.
        Map<State, State> parentToSonStates = new HashMap<State, State>();

        //krok 1. utworz stany, oraz zaznacz je jako poczatkowe lub koncowe.
        for (State parentState : parentStates)
        {
        	State childState = childAutomaton.addState();
            childStates.add(childState);
            //dodaj do tabelki translacji stanow
            parentToSonStates.put(parentState, childState);
            
            //jesli stan jest poczatkowym, zaznacz go jako koncowy. 
            if (parentState == parentAutomaton.getInitialState())
            	childAutomaton.markAsFinal(childStates.get(childStates.size() - 1));
            //jesli stan jest koncowym, utworz polaczenie z jedynym mozliwym stanem poczatkowym przez epsilon.
            else if (parentAutomaton.isFinal(parentState)) {
                EpsilonTransitionLabel eps = new EpsilonTransitionLabel();
                childAutomaton.addTransition(childState, initialChildState, eps);
            }
        }
        
        //krok 2. utworz krawedzie.
        //z kazdego stanu w automacie wejsciowym...
        for (State parentState : parentStates)
        {
        	//pobierz kazda wychodzaca krawedz...
        	for (OutgoingTransition parentTransition : parentAutomaton.allOutgoingTransitions(parentState))
        	{
        		//pobierz stan wyjsciowy z krawedzi
        		State targetState = parentTransition.getTargetState();
        		//pobierz z tabelki translacji stanow stany: wejsciowy i poczatkowy
        		State childStateFrom = parentToSonStates.get(parentState);
        		State childStateTo = parentToSonStates.get(targetState);
        		//dodaj do listy krawedzi krawedz miedzy stanami w kierunku odwrotnym niz oryginalny
        		childAutomaton.addTransition(childStateTo, childStateFrom, parentTransition.getTransitionLabel());
        	}
        }
        
        return childAutomaton;
    }

    /**
     * Metoda tworzy przejscie od stanu stateC do nowego stanu utworzonego przez pare A i B w
     * combinedC po etykiecie transition. Dodanie nowo utworzonego stanu stateCn do listy newStates
     * wraz z wpisaniem jej oraz jej kombinacji stan�lw do HashMap.
     * hashMaps - 0 - statesC, 1 - statesCHandle, 2 - combinedStatesC
     */
    private static boolean makeTransition(CombinedState combinedC, List newStates,
            TransitionLabel transition, List<HashMap> hashMaps, State stateC,
            AutomatonSpecification automatonC, boolean isFinal) {
        State stateCn;
        boolean empty = true;
        if (hashMaps.get(0).containsValue(combinedC.toString()))
            stateCn = (State) hashMaps.get(1).get(
                    hashMaps.get(2).get(combinedC.toString()).toString());
        else {
            stateCn = automatonC.addState();
            hashMaps.get(2).put(combinedC.toString(), combinedC);
            hashMaps.get(0).put(stateCn, combinedC.toString());
            hashMaps.get(1).put(combinedC.toString(), stateCn);
            newStates.add(stateCn);
            empty = false;
        }
        automatonC.addTransition(stateC, stateCn, transition);
        if (isFinal)
                automatonC.markAsFinal(stateCn);
        return empty;
    }
    /**
     * Metoda zwracająca automat akceptujący przecięcie język�lw akceptowanych przez
     * dwa podane automaty.
     */
    public static AutomatonSpecification intersection(
            AutomatonSpecification automatonA, AutomatonSpecification automatonB) {

        boolean empty, isFinal = false;
        CombinedState combinedC = new CombinedState();
        AutomatonSpecification automatonC = new NaiveAutomatonSpecification();

        State qA = automatonA.getInitialState();
        State qB = automatonB.getInitialState();
        State qC = automatonC.addState();
        automatonC.markAsInitial(qC);
        if (automatonA.isFinal(qA) && automatonB.isFinal(qB))
            automatonC.markAsFinal(qC);

        List<OutgoingTransition> lA;
        List<OutgoingTransition> lB;
        List<State> lC = new java.util.LinkedList<State>();
        List<State> newStates = new java.util.LinkedList<State>();
        newStates.add(qC);

        /*
         * combinedStatesC - zawiera łańcuch kontrolny odpowiadający kombinacji stan�lw A i B
         * statesC - zawiera stan C z łańcuchem kobminacji jego stan�lw A i B
         * statesCHandle - zawiera uchwyt do stanu C poprzez łańcuch kontrolny jego kombinacji
         * stan�lw A i B
         */
        HashMap<String, CombinedState> combinedStatesC = new HashMap<String, CombinedState>();
        HashMap<State, String> statesC = new HashMap<State, String>();
        HashMap<String, State> statesCHandle = new HashMap<String, State>();
        List<HashMap> hashMaps = new LinkedList<HashMap>();
        hashMaps.add(statesC);
        hashMaps.add(statesCHandle);
        hashMaps.add(combinedStatesC);

        combinedC.set(qA, qB);
        combinedStatesC.put(combinedC.toString(), combinedC);
        statesC.put(qC, combinedC.toString());
        statesCHandle.put(combinedC.toString(), qC);

        do {
            lC.addAll(newStates);
            newStates.clear();
            empty = true;

            for (State stateC : lC) {
                combinedC = combinedStatesC.get(statesC.get(stateC));
                qA = combinedC.getA();
                qB = combinedC.getB();
                lA = automatonA.allOutgoingTransitions(qA);
                lB = automatonB.allOutgoingTransitions(qB);

                for (OutgoingTransition qAn : lA) {
                    for (OutgoingTransition qBn : lB) {

                        TransitionLabel tL = qAn.getTransitionLabel().intersect(
                                qBn.getTransitionLabel());

                        if (!tL.isEmpty() && !tL.canBeEpsilon()) {
                            combinedC = new CombinedState();
                            combinedC.set(qAn.getTargetState(), qBn.getTargetState());
                            if (automatonA.isFinal(qAn.getTargetState())
                                    && automatonB.isFinal(qBn.getTargetState()))
                                isFinal = true;
                            else
                                isFinal = false;
                            if (!makeTransition(combinedC, newStates, tL, hashMaps, stateC,
                                    automatonC, isFinal))
                                empty = false;
                        }
                    }
                }
                //Epsilon przejscia
                for (OutgoingTransition transitionToAn : lA) {
                    if (transitionToAn.getTransitionLabel().canBeEpsilon()) {
                        combinedC = new CombinedState();
                        combinedC.set(transitionToAn.getTargetState(), qB);
                        if (automatonA.isFinal(transitionToAn.getTargetState())
                                && automatonB.isFinal(qB))
                            isFinal = true;
                        else
                            isFinal = false;
                        if (!makeTransition(combinedC, newStates, new EpsilonTransitionLabel(),
                                hashMaps, stateC, automatonC, isFinal))
                            empty = false;
                    }
                }
                for (OutgoingTransition transitionToBn : lB) {
                    if (transitionToBn.getTransitionLabel().canBeEpsilon()) {
                        combinedC = new CombinedState();
                        combinedC.set(qA, transitionToBn.getTargetState());
                        if (automatonA.isFinal(qA)
                                && automatonB.isFinal(transitionToBn.getTargetState()))
                            isFinal = true;
                        else
                            isFinal = false;
                        if (!makeTransition(combinedC, newStates, new EpsilonTransitionLabel(),
                                hashMaps, stateC, automatonC, isFinal))
                                empty = false;
                    }
                }
            }
            lC.clear();
        } while (!empty);

        return automatonC;
    }
    /**
     * Zwraca automat akceptujący domknięcie Kleene'ego
     * języka akceptowanego przez dany automat.
     */
    public AutomatonSpecification getKleeneStar(AutomatonSpecification automaton) {
        AutomatonSpecification kleeneautomaton = new NaiveAutomatonSpecification();
        State state1 = kleeneautomaton.addState();
        kleeneautomaton.markAsInitial(state1);
        kleeneautomaton.markAsFinal(state1);
        if (!automaton.isEmpty()) {
            State state2 = kleeneautomaton.addState();
            kleeneautomaton.addTransition(state1, state2, new EpsilonTransitionLabel());
            kleeneautomaton.insert(state2, automaton);
            for (State state : automaton.allStates()) {
                if (automaton.isFinal(state)) {
                    kleeneautomaton.addTransition(state, state1, new EpsilonTransitionLabel());
                }
            }
        }
        return kleeneautomaton;
    }
     /**
     * Metoda tworzaca automat akceptujacy sume 2 jezykow.
     */
    public static AutomatonSpecification sum(
        AutomatonSpecification automatonA, AutomatonSpecification automatonB) {
        AutomatonSpecification automaton = new NaiveAutomatonSpecification();
        State q0 = automaton.addState();
        State q1 = automaton.addState();
        State q2 = automaton.addState();
        automaton.markAsInitial(q0);
        automaton.insert(q1, automatonA);
        automaton.insert(q2, automatonB);
        automaton.addTransition(q0, q1, new EpsilonTransitionLabel());
        automaton.addTransition(q0, q2, new EpsilonTransitionLabel());
        return automaton;
    }
}
