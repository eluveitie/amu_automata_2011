package pl.edu.amu.wmi.daut.base;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Klasa zwierająca operacje na automatach.
 */
public class AutomataOperations {

    /**
     * Klasa reprezentuje stan C powstały poprzez połączenie stanów A i B w wyniku operacji
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
     *Metoda zwraca automat akceptujący odwrócenie języka,
     * akceptowanego przez dany automat "parent".
     */
    public static AutomatonSpecification reverseLanguageAutomaton(
            NaiveAutomatonSpecification parentAutomaton) {

        NaiveAutomatonSpecification childAutomaton = new NaiveAutomatonSpecification();

        if (parentAutomaton.isEmpty()) { return childAutomaton; }

        List<State> parentStates = new ArrayList<State>();
        List<State> childStates = new ArrayList<State>();
        parentStates.addAll(parentAutomaton.allStates());

        //utwórz sztucznie stan początkowy.
        //bedzie łączony przez epsilon ze stanami końcowymi automatu wejściowego.
        State initialChildState = childAutomaton.addState();
        childStates.add(initialChildState);
        childAutomaton.markAsInitial(initialChildState);

        //zadeklaruj tabelkę translacji stanów z automatu wejściowego
        //na stany z automatu wyjściowego.
        Map<State, State> parentToSonStates = new HashMap<State, State>();

        //krok 1. utwórz stany, oraz zaznacz je jako początkowe lub końcowe.
        for (State parentState : parentStates) {
            State childState = childAutomaton.addState();
            childStates.add(childState);
            //dodaj do tabelki translacji stanów
            parentToSonStates.put(parentState, childState);

            //jeśli stan jest początkowym, zaznacz go jako końcowy.
            if (parentState == parentAutomaton.getInitialState())
                childAutomaton.markAsFinal(childStates.get(childStates.size() - 1));
            //jeśli stan jest końcowym, utwórz połączenie z jedynym możliwym stanem początkowym.
            else if (parentAutomaton.isFinal(parentState)) {
                EpsilonTransitionLabel eps = new EpsilonTransitionLabel();
                childAutomaton.addTransition(initialChildState, childState, eps);
            }
        }

        //krok 2. utwórz krawędzie.
        //z każdego stanu w automacie wejściowym...
        for (State parentState : parentStates) {
            //pobierz każdą wychodzącą krawędź...
            for (OutgoingTransition parentTransition
                : parentAutomaton.allOutgoingTransitions(parentState)) {
                //pobierz stan wyjściowy z krawędzi
                State targetState = parentTransition.getTargetState();
                //pobierz z tabelki translacji stanów stany: wejściowy i początkowy
                State childStateFrom = parentToSonStates.get(parentState);
                State childStateTo = parentToSonStates.get(targetState);
                //dodaj do listy krawędzi krawędź między stanami w kierunku odwrotnym niż oryginalny
                childAutomaton.addTransition(childStateTo,
                    childStateFrom, parentTransition.getTransitionLabel());
            }
        }

        return childAutomaton;
    }

    /**
     * Metoda tworzy przejście od stanu stateC do nowego stanu utworzonego przez parę A i B w
     * combinedC po etykiecie transition. Dodanie nowo utworzonego stanu stateCn do listy newStates
     * wraz z wpisaniem jej oraz jej kombinacji stanów do HashMap.
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
     * Metoda zwracajaca Automat akceptujacy jezyk bedacy dopelnieniem jezyka
     * akceptowanego przez Automat otrzymywany "na wejsciu".
     */
    static AutomatonSpecification
            complementLanguageAutomaton(DeterministicAutomatonSpecification automaton,
            Set<Character> alfabet) {
        AutomatonSpecification returned = automaton.clone();
        returned.makeFull(alfabet.toString());
        for (State obecny : returned.allStates()) {
            if (returned.isFinal(obecny))
                returned.unmarkAsFinalState(obecny);
            else
                returned.markAsFinal(obecny);
        }
        return returned;
    }

    /**
     * Metoda zwracająca automat akceptujący przecięcie języków akceptowanych przez
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
         * combinedStatesC - zawiera łańcuch kontrolny odpowiadający kombinacji stanów A i B
         * statesC - zawiera stan C z łańcuchem kobminacji jego stanów A i B
         * statesCHandle - zawiera uchwyt do stanu C poprzez łańcuch kontrolny jego kombinacji
         * stanów A i B
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
                //Epsilon przejścia
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
    public static AutomatonSpecification getKleeneStar(AutomatonSpecification automaton) {
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
     * Metoda tworząca automat akceptujący sumę 2 jezyków.
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

  /**
  * Zwraca automat akceptujący język powstały w wyniku zastosowania homomorfizmu h na
  * języku akceptowanym przez automat automaton. Homomorfizm jest dany jako mapa, w której
  * kluczami są znaki, a wartościami - napisy.
  * @param alphabet alfabet w postaci String, np. abc
  * @param automaton automat wejściowy
  * @param h homomorfizm języka

  */
 AutomatonSpecification homomorphism(AutomatonSpecification automaton,
         Map<Character, String> h, String alphabet) {
     if (automaton.isEmpty()) {
         return automaton;
     }

     char[] tablica;
     tablica = alphabet.toCharArray();
     AutomatonSpecification homoautomaton = new NaiveDeterministicAutomatonSpecification();
     List<State> states = new ArrayList<State>();
     states.addAll(automaton.allStates());
     HashMap<State, State> connectedStates = new HashMap<State, State>();
      for (State current : states) {
          if (!connectedStates.containsKey(current))
              connectedStates.put(current, homoautomaton.addState());
        for (OutgoingTransition currenttrans : automaton.allOutgoingTransitions(current)) {
          TransitionLabel tl = currenttrans.getTransitionLabel();
          for (char znak : tablica) {
            if (tl.canAcceptCharacter(znak)) {
                 String napis = h.get(znak);
                 int dlugosc = napis.length();
                 char[] znaki = napis.toCharArray();
                 State docelowy = currenttrans.getTargetState();
                 State prev = current;
                 if (dlugosc == 0) {
                     homoautomaton.addTransition(prev, docelowy, new EpsilonTransitionLabel());
                 }
                 for (int i = 0; i < dlugosc - 1; i++) {
                     State next = homoautomaton.addState();
                     homoautomaton.addTransition(prev, next, new CharTransitionLabel(znaki[i]));
                     prev = next;
                 }
                 homoautomaton.addTransition(prev, docelowy,
                         new CharTransitionLabel(znaki[dlugosc]));
                 connectedStates.put(docelowy, homoautomaton.addState());
              }
          }
      }
     }
     return homoautomaton;
 }


    /**
     * Klasa pomocnicza do determinize2(). Rekuprezentuje "zbiór stanów" będący stanem automatu dfa.
     */
    private static class PowerSetElement {
        private Set<State> nfaStates;
        private State dfaState;
        private static int numberOfPowerSetElements = 0;

        public PowerSetElement(Iterable<State> nfaStatesRemote, State dfaStateRemote) {
            nfaStates = new HashSet<State>();
            for (State s : nfaStatesRemote) {
                nfaStates.add(s);
            }
            dfaState = dfaStateRemote;
            numberOfPowerSetElements++;
        }

        /**
         * Funkcja pomocnicza do determinize2(). Rekurencyjnie tworzy listę stanów
         * występujących w dfa. Przed użyciem tej funkcji trzeba jednak dodać do listOfStates
         * jeden element - odpowiadający zbioru pustemu.
         */
        public static void giveAllPowerSetElements(List<PowerSetElement> listOfStates,
            List<State> dfaStatesRemote, List<State> nfaStatesRemote) {
            Set<State> nfaStates = new HashSet<State>();
            giveAllPowerSetElementsRecursive(listOfStates, dfaStatesRemote, nfaStatesRemote,
                    nfaStates, 0);
        }

        private static void giveAllPowerSetElementsRecursive(List<PowerSetElement> listOfStates,
            List<State> dfaStatesRemote, List<State> nfaStatesRemote, Set<State> nfaStates,
            int depth) {
            if (depth < nfaStatesRemote.size()) {
                //Gałąź dla false(Obecnie rozpatrywany stan NFA nie jest brany)
                giveAllPowerSetElementsRecursive(listOfStates, dfaStatesRemote,
                        nfaStatesRemote, nfaStates, depth + 1);

                //Gałąź dla true(Obecnie rozpatrywany stan NFA jest brany)
                nfaStates.add(nfaStatesRemote.get(depth));
                giveAllPowerSetElementsRecursive(listOfStates, dfaStatesRemote,
                        nfaStatesRemote, nfaStates, depth + 1);
                nfaStates.remove(nfaStatesRemote.get(depth));
            } else {
                listOfStates.add(new PowerSetElement(nfaStates,
                        dfaStatesRemote.get(numberOfPowerSetElements)));
            }
        }

        public Set<State> getnfaStates() {
            return nfaStates;
        }

        public State getdfaState() {
            return dfaState;
        }

        public static void resetNumber() {
            numberOfPowerSetElements = 0;
        }
    };

    /*
     * Metoda pomocnicza dla determiinize2. Tworzy podstawowy zbiór etykiet przejścia używanych
     * przez oba automaty - niedeterministyczny i deterministyczny.
     */
    private static void putTransitionLabelInSet(HashSet<TransitionLabel> tSet,
            TransitionLabel transitionLabel) throws StructureException {
        if (!transitionLabel.isEmpty())
            if (transitionLabel instanceof AnyTransitionLabel) {
                putTransitionLabelInSet(tSet, new ComplementCharClassTransitionLabel(""));
            } else if (transitionLabel instanceof CharTransitionLabel) {
                tSet.add(transitionLabel);
            } else if (transitionLabel instanceof CharSetTransitionLabel) {
                for (char sign : ((CharSetTransitionLabel) transitionLabel).getCharSet()) {
                    tSet.add(new CharTransitionLabel(sign));
                    for (TransitionLabel t : tSet) {
                        if (t instanceof ComplementCharClassTransitionLabel) {
                            ((ComplementCharClassTransitionLabel) t).getSet().add(sign);
                        }
                    }
                }
            } else if (transitionLabel instanceof CharRangeTransitionLabel) {
                for (char k = ((CharRangeTransitionLabel) transitionLabel)
                    .getFirstChar(); k <= ((CharRangeTransitionLabel) transitionLabel)
                    .getSecondChar(); k++) {
                        tSet.add(new CharTransitionLabel(k));
                }
            } else if (transitionLabel instanceof ComplementCharClassTransitionLabel) {
                ComplementCharClassTransitionLabel newOne
                        = (ComplementCharClassTransitionLabel) transitionLabel;
                ComplementCharClassTransitionLabel oldOne = null;
                TransitionLabel resTL = null;
                Set<Character> newSet = newOne.getSet();
                for (TransitionLabel t : tSet) {
                    if (t instanceof ComplementCharClassTransitionLabel) {
                        String oldBuilder = t.toString();
                        oldOne = new ComplementCharClassTransitionLabel(
                                oldBuilder.substring(2, oldBuilder.length() - 1));
                        resTL = oldOne.intersect(newOne);
                        Set<Character> excluded = new HashSet<Character>();
                        excluded.addAll(oldOne.getSet());
                        excluded.removeAll(newOne.getSet());
                        for (Character sign : excluded)
                            tSet.add(new CharTransitionLabel(sign));
                        excluded.clear();
                        excluded.addAll(newOne.getSet());
                        excluded.removeAll(oldOne.getSet());
                        for (Character sign : excluded)
                            tSet.add(new CharTransitionLabel(sign));
                        oldOne = (ComplementCharClassTransitionLabel) t;
                        newOne = (ComplementCharClassTransitionLabel) resTL;
                        newSet = newOne.getSet();
                    } else {
                        newSet.add(t.toString().charAt(0));
                    }
                }
                tSet.add(newOne);
                if (oldOne != null)
                    tSet.remove(oldOne);
            } else
                throw new StructureException();
    }

    /**
     * Metoda determinizuje automat niedeterministyczny bez epsilon-przejść.
     * Determinizacja przebiega zgodnie z algorytmem przedstawionym na wykładzie.
     * Automat resultDfa na wejściu powinien być pusty!
     */
    public static void determinize2(AutomatonSpecification nfa,
            DeterministicAutomatonSpecification resultDfa) throws StructureException {

        //Sprawdzenie, czy resultDfa na pewno jest pusty.
        if (resultDfa.isEmpty()) {

            if (!nfa.prefixChecker(nfa.getInitialState())) {
                State one = resultDfa.addState();
                resultDfa.markAsInitial(one);
                return;
            }

            if (nfa.isDeterministic()) {
                resultDfa.fromString(nfa.toString());
                return;
            }

            //Utworzenie pustego zbioru przejść, oraz dodatkowego zbioru przejść
            //klasy ComplementCharClassTransitionLabel
            HashSet<TransitionLabel> tSet = new HashSet<TransitionLabel>();

            //Utworzenie listy stanów automatu NFA. Oznaczenie: K.
            List<State> kList = nfa.allStates();

            //Uzupełnienie zbioru przejść, aby był zbiorem przejść automatu NFA. Oznaczenie: T.
            for (State s : kList) {
                for (OutgoingTransition oT : nfa.allOutgoingTransitions(s)) {
                    if ((oT.getTransitionLabel() instanceof ComplementCharClassTransitionLabel)
                            || (oT.getTransitionLabel() instanceof AnyTransitionLabel)) {
                        putTransitionLabelInSet(tSet, oT.getTransitionLabel());
                    } else {
                        if (tSet.isEmpty()) {
                            putTransitionLabelInSet(tSet, oT.getTransitionLabel());
                        }
                        boolean isUnique = true;
                        for (TransitionLabel t : tSet) {
                            if (oT.getTransitionLabel().intersect(t).equals(oT
                                    .getTransitionLabel())) {
                                isUnique = false;
                                break;
                            }
                        }
                        if (isUnique)
                            putTransitionLabelInSet(tSet, oT.getTransitionLabel());
                    }
                }
            }

            //Obliczenie liczby stanów automatu DFA. Oznaczenie: |K'|.
            int nrOfdfaStates = (int) Math.pow((double) 2, (double) (nfa.countStates()));

            //Utworzenie stanów automatu DFA. Oznaczenie: K'.
            for (int i = 0; i < nrOfdfaStates; i++) {
                resultDfa.addState();
            }
            List<State> kPrimList = resultDfa.allStates();
            List<PowerSetElement> kPrimAdditionalList = new Vector<PowerSetElement>();
            PowerSetElement.giveAllPowerSetElements(kPrimAdditionalList, kPrimList, kList);

            //Odnajduje stany końcowe NFA.
            Vector<Integer> fList = new Vector<Integer>();
            for (State seeker : kList) {
                if (nfa.isFinal(seeker)) {
                    fList.add(kList.indexOf(seeker));
                }
            }

            //Na ich podstawie oznacza stany końcowe w DFA.
            for (Integer endState : fList) {
                for (PowerSetElement structure : kPrimAdditionalList) {
                    if ((!resultDfa.isFinal(structure.getdfaState()))
                            && (structure.getnfaStates().contains(kList.get(endState))))
                        resultDfa.markAsFinal(structure.getdfaState());
                }
            }

            //Odnajduje stan początkowy obu automatów.
            HashSet<State> initialStates = new HashSet<State>();
            initialStates.add(nfa.getInitialState());
            for (PowerSetElement structure : kPrimAdditionalList) {
                if (initialStates.equals(structure.getnfaStates())) {
                    resultDfa.markAsInitial(structure.getdfaState());
                    break;
                }
            }

            //Utworzenie przejść dla nowego automatu na podstawie przejść z nfa.
            //Dla każdego "nowego stanu w dfa będącego tak naprawdę zbiorem stanów z nfa"
            for (PowerSetElement structure : kPrimAdditionalList) {
                if (structure.getnfaStates().isEmpty()) {
                    for (PowerSetElement targetStructure : kPrimAdditionalList) {
                        if (targetStructure.getnfaStates().isEmpty())
                            resultDfa.addTransition(structure.getdfaState(),
                                targetStructure.getdfaState(), new AnyTransitionLabel());
                    }
                } else {
                    for (TransitionLabel t : tSet) {
                        HashSet<State> whereTo = new HashSet<State>();
                        if (t instanceof ComplementCharClassTransitionLabel) {
                            for (State s : structure.getnfaStates()) {
                                for (OutgoingTransition oT : nfa.allOutgoingTransitions(s)) {
                                    if (!(t.intersect(oT.getTransitionLabel()).isEmpty())) {
                                        whereTo.add(oT.getTargetState());
                                    }
                                }
                            }
                        } else {
                            for (State s : structure.getnfaStates()) {
                                for (OutgoingTransition oT : nfa.allOutgoingTransitions(s)) {
                                    if (oT.getTransitionLabel().canAcceptCharacter(t.toString()
                                            .charAt(0))) {
                                        whereTo.add(oT.getTargetState());
                                    }
                                }
                            }
                        }

                        PowerSetElement thereGoesNow = structure;
                        for (PowerSetElement targetStructure : kPrimAdditionalList) {
                            if (whereTo.equals(targetStructure.getnfaStates())) {
                                thereGoesNow = targetStructure;
                                break;
                            }
                        }
                        //Gdy skończyliśmy przecinanie wszystkich etykiet przejścia
                        //tworzymy OutgoingTransition.
                        resultDfa.addTransition(structure.getdfaState(),
                                thereGoesNow.getdfaState(), t);
                    }
                }
            }
            PowerSetElement.resetNumber();
            resultDfa.deleteUselessStates();
        } else {
            throw new StructureException();
        }
    }

}


