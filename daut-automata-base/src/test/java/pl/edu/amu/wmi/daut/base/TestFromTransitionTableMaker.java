package pl.edu.amu.wmi.daut.base;

import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;

/**
 * Klasa testujaca tworzenie AutomatonSpecification z tablicy przejsc.
 */
public class TestFromTransitionTableMaker extends TestCase {

    /**
     * Automat akceptujacy "ab" lub "ba".
     */
    public final void testMakeAutomatonTwoDiffStates() {
        AutomatonSpecification spec = new NaiveAutomatonSpecification();
        int[][] transitionTable = {
            {1, 3},
            {4, 2},
            {4, 4},
            {2, 4},
            {4, 4}
        };
        Set<Integer> finalStates = new HashSet<Integer>();
        finalStates.add(new Integer(2));
        FromTransitionTableMaker.makeAutomaton(spec, "ab", transitionTable, finalStates);

        AutomatonByRecursion automaton = new AutomatonByRecursion(spec);

        assertTrue(automaton.accepts("ab"));
        assertTrue(automaton.accepts("ba"));
        assertFalse(automaton.accepts("aa"));
        assertFalse(automaton.accepts("bb"));
    }

    /**
     * Automat akceptujacy *a^2.
     */
    public final void testMakeAutomatonEndsgWithASquared() {
        AutomatonSpecification spec = new NaiveAutomatonSpecification();
        int[][] transitionTable = {
            {1, 0},
            {2, 0},
            {2, 0}
        };
        Set<Integer> finalStates = new HashSet<Integer>();
        finalStates.add(new Integer(2));
        FromTransitionTableMaker.makeAutomaton(spec, "ab", transitionTable, finalStates);

        AutomatonByRecursion automaton = new AutomatonByRecursion(spec);

        assertFalse(automaton.accepts("ab"));
        assertFalse(automaton.accepts("ba"));
        assertTrue(automaton.accepts("aa"));
        assertFalse(automaton.accepts("bb"));
        assertFalse(automaton.accepts("aba"));
        assertTrue(automaton.accepts("baa"));
        assertTrue(automaton.accepts("aaa"));
        assertTrue(automaton.accepts("abaa"));

    }

    /**
     * Automat akceptujacy slowa zawierajace kazda litere z alfabetu przynajmniej raz.
     */
    public final void testMakeAutomatonContainsAllChars() {
        AutomatonSpecification spec = new NaiveAutomatonSpecification();
        int[][] transitionTable = {
            {1, 5, 7},
            {1, 2, 4},
            {2, 2, 3},
            {3, 3, 3},
            {4, 3, 4},
            {2, 5, 6},
            {3, 6, 6},
            {4, 6, 7}
        };
        Set<Integer> finalStates = new HashSet<Integer>();
        finalStates.add(new Integer(3));
        FromTransitionTableMaker.makeAutomaton(spec, "abc", transitionTable, finalStates);

        AutomatonByRecursion automaton = new AutomatonByRecursion(spec);

        assertFalse(automaton.accepts("aaa"));
        assertFalse(automaton.accepts("aab"));
        assertFalse(automaton.accepts("cca"));
        assertFalse(automaton.accepts("aac"));
        assertTrue(automaton.accepts("bac"));
        assertTrue(automaton.accepts("abc"));
        assertTrue(automaton.accepts("cba"));
        assertTrue(automaton.accepts("bca"));
        assertTrue(automaton.accepts("acb"));
        assertTrue(automaton.accepts("cab"));
        assertTrue(automaton.accepts("aabbaaabcc"));
        assertTrue(automaton.accepts("ccccbbcbbbcbab"));

    }
}
