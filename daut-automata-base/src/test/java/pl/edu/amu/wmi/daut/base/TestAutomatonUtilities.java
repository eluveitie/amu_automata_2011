package pl.edu.amu.wmi.daut.base;

import junit.framework.TestCase;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Wojciech
 */
public class TestAutomatonUtilities extends TestCase {

    /**
     * Test metody getAlphabet, sprawdza czy zwraca dwuelementowy alfabet.
    */
    public final void testAlphabetForSmallSetFromSuperset() {

        final NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();

        spec.addTransition(q0, q1, new CharTransitionLabel('a'));
        spec.addTransition(q1, q2, new CharTransitionLabel('b'));

        spec.markAsInitial(q0);
        spec.markAsFinal(q2);

        Set<Character> superSet = new HashSet<Character>();
        superSet.add(('a'));
        superSet.add(('b'));
        superSet.add(('c'));
        superSet.add(('d'));
        superSet.add(('e'));
        superSet.add(('f'));
        superSet.add(('g'));
        superSet.add(('h'));
        superSet.add(('i'));

        Set<Character> result = AutomatonUtilities.getAlphabet(spec, superSet);

        Set<Character> resultExpected = new HashSet<Character>();
        resultExpected.add('a');
        resultExpected.add('b');
        assertTrue(result.equals(resultExpected));

        resultExpected = new HashSet<Character>();
        resultExpected.add('c');
        resultExpected.add('b');
        assertFalse(result.equals(resultExpected));
    }

    /**
     * Drugi test, sprawdza alfabet przy powtarzajacych sie.
    */
    public final void testAlphabetForSameLabels() {

        final NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();

        spec.addTransition(q0, q1, new CharTransitionLabel('a'));
        spec.addTransition(q0, q1, new CharTransitionLabel('a'));
        spec.addTransition(q1, q2, new CharTransitionLabel('b'));

        spec.markAsInitial(q0);
        spec.markAsFinal(q2);

        Set<Character> superSet = new HashSet<Character>();
        superSet.add(('a'));
        superSet.add(('b'));
        superSet.add(('c'));
        superSet.add(('d'));
        superSet.add(('e'));
        superSet.add(('f'));
        superSet.add(('g'));
        superSet.add(('h'));
        superSet.add(('i'));

        Set<Character> result = AutomatonUtilities.getAlphabet(spec, superSet);

        Set<Character> resultExpected = new HashSet<Character>();
        resultExpected.add('a');
        resultExpected.add('b');
        assertTrue(result.equals(resultExpected));
    }

     /**
      * Trzeci test, sprawdza alfabet pusty.
     */
     public final void testAlphabetEmpty() {

        final NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();

        spec.markAsInitial(q0);
        spec.markAsFinal(q2);

        Set<Character> superSet = new HashSet<Character>();
        superSet.add(('a'));
        superSet.add(('b'));
        superSet.add(('c'));
        superSet.add(('d'));
        superSet.add(('e'));
        superSet.add(('f'));
        superSet.add(('g'));
        superSet.add(('h'));
        superSet.add(('i'));

        Set<Character> result = AutomatonUtilities.getAlphabet(spec, superSet);

        Set<Character> resultExpected = new HashSet<Character>();
        assertTrue(result.equals(resultExpected));
    }

    /**
     * Czwarty test, sprawdza alfabet rowny calemu zbiorowi.
    */
    public final void testAlphabetFullSet() {

        final NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();

        spec.addTransition(q0, q0, new CharTransitionLabel('a'));
        spec.addTransition(q0, q1, new CharTransitionLabel('b'));
        spec.addTransition(q0, q2, new CharTransitionLabel('c'));
        spec.addTransition(q1, q0, new CharTransitionLabel('d'));
        spec.addTransition(q1, q1, new CharTransitionLabel('e'));
        spec.addTransition(q1, q2, new CharTransitionLabel('f'));
        spec.addTransition(q2, q0, new CharTransitionLabel('g'));
        spec.addTransition(q2, q1, new CharTransitionLabel('h'));
        spec.addTransition(q1, q2, new CharTransitionLabel('i'));

        spec.markAsInitial(q0);
        spec.markAsFinal(q2);

        Set<Character> superSet = new HashSet<Character>();
        superSet.add(('a'));
        superSet.add(('b'));
        superSet.add(('c'));
        superSet.add(('d'));
        superSet.add(('e'));
        superSet.add(('f'));
        superSet.add(('g'));
        superSet.add(('h'));
        superSet.add(('i'));

        Set<Character> result = AutomatonUtilities.getAlphabet(spec, superSet);

        Set<Character> resultExpected = new HashSet<Character>();
        resultExpected.add(('a'));
        resultExpected.add(('b'));
        resultExpected.add(('c'));
        resultExpected.add(('d'));
        resultExpected.add(('e'));
        resultExpected.add(('f'));
        resultExpected.add(('g'));
        resultExpected.add(('h'));
        resultExpected.add(('i'));

        assertTrue(result.equals(resultExpected));
    }

    /**
     * Piaty test, sprawdza uzycie elementu nie nalezacego do nadzbioru.
    */
    public final void testAlphabetElementFromOutsideSuperset() {

        final NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();

        spec.addTransition(q0, q0, new CharTransitionLabel('a'));
        spec.addTransition(q0, q1, new CharTransitionLabel('y'));

        spec.markAsInitial(q0);
        spec.markAsFinal(q2);

        Set<Character> superSet = new HashSet<Character>();
        superSet.add(('a'));
        superSet.add(('b'));
        superSet.add(('c'));
        superSet.add(('d'));
        superSet.add(('e'));
        superSet.add(('f'));
        superSet.add(('g'));
        superSet.add(('h'));
        superSet.add(('i'));

        Set<Character> result = AutomatonUtilities.getAlphabet(spec, superSet);

        Set<Character> resultExpected = new HashSet<Character>();
        resultExpected.add(('a'));
        resultExpected.add(('y'));

        assertFalse(result.equals(resultExpected));
    }
}
