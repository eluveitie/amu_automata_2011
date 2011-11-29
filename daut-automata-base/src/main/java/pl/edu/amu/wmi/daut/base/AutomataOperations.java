package pl.edu.amu.wmi.daut.base;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa zwieraj�ca operacje na automatach.
 */
public class AutomataOperations {

    /**
     * Klasa reprezentuje stan C powsta�y poprzez po��czenie stan�w A i B w wyniku operacji
     * intersection.
     */
    private static final class CombinedState {

        /**
         * Przypisuje stanowi C jego sk�adowe stany A i B.
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
     *Metoda zwraca automat akceptuj�cy odwr�cenie jezyka,
     * akceptowanego przez dany automat "parent".
     */
    public static AutomatonSpecification reverseLanguageAutomat(
            NaiveAutomatonSpecification parentAutomaton) {

        NaiveAutomatonSpecification childAutomaton = new NaiveAutomatonSpecification();

        if (parentAutomaton.isEmpty()) { return childAutomaton; }

        List<State> parentStates = new ArrayList<State>();
        List<State> childStates = new ArrayList<State>();
        parentStates.addAll(parentAutomaton.allStates());

        //utw�rz sztucznie stan pocz�tkowy.
        //bedzie ��czony przez epsilon ze stanami ko�cowymi automatu wej�ciowego.
        State initialChildState = childAutomaton.addState();
        childStates.add(initialChildState);
        childAutomaton.markAsInitial(initialChildState);

        //zadeklaruj tabelk� translacji stan�w z automatu wej�ciowego
        //na stany z automatu wyj�ciowego.
        Map<State, State> parentToSonStates = new HashMap<State, State>();

        //krok 1. utw�rz stany, oraz zaznacz je jako pocz�tkowe lub ko�cowe.
        for (State parentState : parentStates) {
            State childState = childAutomaton.addState();
            childStates.add(childState);
            //dodaj do tabelki translacji stan�w
            parentToSonStates.put(parentState, childState);

            //jesli stan jest pocz�tkowym, zaznacz go jako ko�cowy.
            if (parentState == parentAutomaton.getInitialState())
                childAutomaton.markAsFinal(childStates.get(childStates.size() - 1));
            //jesli stan jest ko�cowym, utw�rz po��czenie z jedynym mo�liwym stanem pocz�tkowym.
            else if (parentAutomaton.isFinal(parentState)) {
                EpsilonTransitionLabel eps = new EpsilonTransitionLabel();
                childAutomaton.addTransition(initialChildState, childState, eps);
            }
        }

        //krok 2. utw�rz kraw�dzie.
        //z ka�dego stanu w automacie wej�ciowym...
        for (State parentState : parentStates) {
            //pobierz ka�d� wychodz�ca kraw�d�...
            for (OutgoingTransition parentTransition
                : parentAutomaton.allOutgoingTransitions(parentState)) {
                //pobierz stan wyj�ciowy z kraw�dzi
                State targetState = parentTransition.getTargetState();
                //pobierz z tabelki translacji stan�w stany: wej�ciowy i pocz�tkowy
                State childStateFrom = parentToSonStates.get(parentState);
                State childStateTo = parentToSonStates.get(targetState);
                //dodaj do listy kraw�dzi kraw�d� mi�dzy stanami w kierunku odwrotnym ni� oryginalny
                childAutomaton.addTransition(childStateTo,
                    childStateFrom, parentTransition.getTransitionLabel());
            }
        }

        return childAutomaton;
    }

    /**
     * Metoda tworzy przejscie od stanu stateC do nowego stanu utworzonego przez par� A i B w
     * combinedC po etykiecie transition. Dodanie nowo utworzonego stanu stateCn do listy newStates
     * wraz z wpisaniem jej oraz jej kombinacji stan�w do HashMap.
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
     * Metoda zwracaj�ca automat akceptuj�cy przeci�cie jezyk�w akceptowanych przez
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
         * combinedStatesC - zawiera �a�cuch kontrolny odpowiadaj�cy kombinacji stan�w A i B
         * statesC - zawiera stan C z �ancuchem kobminacji jego stan�w A i B
         * statesCHandle - zawiera uchwyt do stanu C poprzez �a�cuch kontrolny jego kombinacji
         * stan�w A i B
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
                //Epsilon przej�cia
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
     * Zwraca automat akceptuj�cy domkni�cie Kleene'ego
     * jezyka akceptowanego przez dany automat.
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
     * Metoda tworz�ca automat akceptuj�cy sum� 2 jezyk�w.
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
