package pl.edu.amu.wmi.daut.base;

import java.util.HashSet;
import java.util.Set;

/**
 * Klasa reprezentujaca zbior stanow wykorzystywana przy determinizacji.
 */
class StateSet {

    private Set<State> stateSet;
    private State thatState;

    /**
     * Zwroci stan w DFA odpowiadajacy zbiorowi stanow w NFA.
     */
    public State getThatState() {
        return thatState;

    }

    /**
     * Ustawi stan w DFA odpowiadajacy zbiorowi stanow w NFA.
     */
    public void setThatState(State s) {
        thatState = s;
    }

    /**
     * Konstruktor tworzacy zbior pusty.
     */
    public StateSet() {
        stateSet = new HashSet<State>();
    }

    /**
     * Konstruktor tworzacy zbior z poczatkowym elementem.
     */
    public StateSet(State s) {
        stateSet = new HashSet<State>();
        stateSet.add(s);
    }

    /**
     * Zwraca zbior stanow.
     */
    public Set<State> getStateSet() {
        return stateSet;
    }

    /**
     * Dodaje stan z NFA do zbioru.
     */
    public boolean add(State s) {
        return stateSet.add(s);
    }

    /**
     * Sprawdza, czy obiekt podany jako argument zawiera taki sam zbior stanow.
     */
    public boolean isEqual(StateSet sS) {
        if (sS.stateSet.isEmpty() && this.stateSet.isEmpty()) {
            return true;
        }
        return stateSet.equals(sS.stateSet);
    }
}
