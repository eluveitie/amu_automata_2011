package pl.edu.amu.wmi.daut.base;

import junit.framework.TestCase;

/**
 * Test klasy AutomatonByRecursion.
 */
public class TestAutomatonByRecursion extends TestCase {
    /**
     * Symulacja testu.
     */
    public final void testLessComplicated() {

        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0a = spec.addState();
        State q1a = spec.addState();
        State q2a = spec.addState();

        spec.addTransition(q0a, q1a, new CharTransitionLabel('a'));
        spec.addTransition(q1a, q2a, new CharTransitionLabel('b'));
        spec.addTransition(q0a, q2a, new CharTransitionLabel('c'));

        spec.markAsInitial(q0a);
        spec.markAsFinal(q2a);

        final AutomatonByRecursion automaton = new AutomatonByRecursion(spec);

        assertFalse(automaton.accepts("a"));
        assertTrue(automaton.accepts("ab"));
        assertTrue(automaton.accepts("c"));
        assertFalse(automaton.accepts("a"));
    }
    /**
     * Trochę bardziej skomplikowany test.
     */
    public final void testComplicated() {

        final AutomatonSpecification specA = new NaiveAutomatonSpecification();

        State q0A = specA.addState();
        State q1A = specA.addState();
        State q2A = specA.addState();
        State q3A = specA.addState();

        specA.addTransition(q0A, q1A, new CharTransitionLabel('a'));
        specA.addTransition(q1A, q3A, new CharTransitionLabel('b'));
        specA.addTransition(q1A, q2A, new CharTransitionLabel('b'));
        specA.addTransition(q0A, q3A, new CharTransitionLabel('b'));

        specA.markAsInitial(q0A);
        specA.markAsFinal(q3A);

        AutomatonByRecursion automatonA = new AutomatonByRecursion(specA);

        assertTrue(automatonA.accepts("ab"));
        assertFalse(automatonA.accepts("a"));
        assertTrue(automatonA.accepts("b"));
        assertFalse(automatonA.accepts("a"));
    }
    /**
     * Najbardziej skomplikowany test.
     */
    public final void testVeryComplicated() {

        final AutomatonSpecification specB = new NaiveAutomatonSpecification();

        State q0B = specB.addState();
        State q1B = specB.addState();
        State q2B = specB.addState();
        State q3B = specB.addState();
        State q4B = specB.addState();
        State q5B = specB.addState();
        State q6B = specB.addState();
        State q7B = specB.addState();
        State q8B = specB.addState();
        State q9B = specB.addState();
        State q10B = specB.addState();
        State q11B = specB.addState();
        State q12B = specB.addState();

        specB.addTransition(q0B, q1B, new CharTransitionLabel('a'));
        specB.addTransition(q1B, q2B, new CharTransitionLabel('b'));
        specB.addTransition(q2B, q3B, new CharTransitionLabel('c'));
        specB.addTransition(q3B, q4B, new CharTransitionLabel('d'));
        specB.addTransition(q3B, q11B, new CharTransitionLabel('d'));
        specB.addTransition(q4B, q5B, new CharTransitionLabel('e'));
        specB.addTransition(q4B, q7B, new CharTransitionLabel('g'));
        specB.addTransition(q4B, q12B, new CharTransitionLabel('z'));
        specB.addLoop(q5B, new CharTransitionLabel('f'));
        specB.addTransition(q3B, q6B, new CharTransitionLabel('d'));
        specB.addTransition(q6B, q9B, new CharTransitionLabel('e'));
        specB.addTransition(q7B, q8B, new EpsilonTransitionLabel());
        specB.addTransition(q9B, q10B, new CharTransitionLabel('f'));
        specB.addLoop(q10B, new CharTransitionLabel('g'));
        specB.addTransition(q12B, q11B, new EpsilonTransitionLabel());
        specB.addTransition(q8B, q9B, new CharTransitionLabel('g'));
        specB.addTransition(q10B, q11B, new CharTransitionLabel('h'));

        specB.markAsInitial(q0B);
        specB.markAsFinal(q11B);

        AutomatonByRecursion automatonB = new AutomatonByRecursion(specB);

        assertTrue(automatonB.accepts("abcd"));
        assertTrue(automatonB.accepts("abcdefgh"));
        assertTrue(automatonB.accepts("abcdefgggh"));
        assertFalse(automatonB.accepts("abcdggfh"));
        assertTrue(automatonB.accepts("abcdefgh"));
        assertFalse(automatonB.accepts("abcdg"));
        assertTrue(automatonB.accepts("abcd"));
        assertFalse(automatonB.accepts("abcdz"));
        assertTrue(automatonB.accepts("abcd"));
        assertFalse(automatonB.accepts("abcdggfh"));
    }
}
