package pl.edu.amu.wmi.daut.base;

import java.util.Set;

/**
 * Klasa reprezentuje automat tworzony na podstawie podanej tablicy przejść.
 */
public final class FromTransitionTableMaker {

    private FromTransitionTableMaker() {
    }

    /**
     * Metoda dla pustego automatu podanego jako parametr 'spec' dodaje
     * odpowiednie stany i przejścia.
     * 
     */
    static void makeAutomaton(AutomatonSpecification spec, String alphabet,
            int[][] transitionTable, Set<Integer> finalStates) {

        int alphCount = alphabet.length();
        int stateCount = transitionTable.length;

        for (int i = 0; i < stateCount; i++) {
            spec.addState();
        }

        spec.markAsInitial(spec.allStates().get(0));

        for (int st : finalStates) {
            spec.markAsFinal(spec.allStates().get(st));
        }

        for (int i = 0; i < stateCount; ++i) {
            for (int j = 0; j < alphCount; ++j) {
                spec.addTransition(spec.allStates().get(i),
                        spec.allStates().get(transitionTable[i][j]),
                        new CharTransitionLabel(alphabet.charAt(j)));
            }
        }

    }
}
