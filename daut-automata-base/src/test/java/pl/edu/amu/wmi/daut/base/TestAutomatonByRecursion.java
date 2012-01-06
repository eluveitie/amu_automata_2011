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

    /**
     * Automat rozpoznający godziny I.
     * Automat: automat deterministyczny (bądź niedeterministyczny)
     * (ale bez epsilon-przejść) akceptujący napisy reprezentujące zapis
     * godziny, gdzie dwukropek jest separatorem godziny i minuty, na
     * początku napisu może występować '0', przykładowe napisy, które
     * powinny być akceptowane: 11:11, 23:59, 0:00, 3:45, 03:45, 15:45, 12:00
     */
      public final void testAutomatClock1() {

        final AutomatonSpecification specClock1 = new NaiveAutomatonSpecification();

        State q0 = specClock1.addState();
        State q1A = specClock1.addState();
        State q1B = specClock1.addState();
        State q1C = specClock1.addState();
        State q2 = specClock1.addState();
        State q3 = specClock1.addState();
        State q4 = specClock1.addState();
        State q5 = specClock1.addState();
        State qZ = specClock1.addState();

        specClock1.addTransition(q0, q1A, new CharTransitionLabel('0'));
        specClock1.addTransition(q0, q1B, new CharTransitionLabel('1'));
        specClock1.addTransition(q0, q1C, new CharTransitionLabel('2'));
        specClock1.addTransition(q0, q2, new CharTransitionLabel('3'));
        specClock1.addTransition(q0, q2, new CharTransitionLabel('4'));
        specClock1.addTransition(q0, q2, new CharTransitionLabel('5'));
        specClock1.addTransition(q0, q2, new CharTransitionLabel('6'));
        specClock1.addTransition(q0, q2, new CharTransitionLabel('7'));
        specClock1.addTransition(q0, q2, new CharTransitionLabel('8'));
        specClock1.addTransition(q0, q2, new CharTransitionLabel('9'));

        specClock1.addTransition(q1A, q2, new CharTransitionLabel('0'));
        specClock1.addTransition(q1A, q2, new CharTransitionLabel('1'));
        specClock1.addTransition(q1A, q2, new CharTransitionLabel('2'));
        specClock1.addTransition(q1A, q2, new CharTransitionLabel('3'));
        specClock1.addTransition(q1A, q2, new CharTransitionLabel('4'));
        specClock1.addTransition(q1A, q2, new CharTransitionLabel('5'));
        specClock1.addTransition(q1A, q2, new CharTransitionLabel('6'));
        specClock1.addTransition(q1A, q2, new CharTransitionLabel('7'));
        specClock1.addTransition(q1A, q2, new CharTransitionLabel('8'));
        specClock1.addTransition(q1A, q2, new CharTransitionLabel('9'));


        specClock1.addTransition(q1B, q2, new CharTransitionLabel('0'));
        specClock1.addTransition(q1B, q2, new CharTransitionLabel('1'));
        specClock1.addTransition(q1B, q2, new CharTransitionLabel('2'));
        specClock1.addTransition(q1B, q2, new CharTransitionLabel('3'));
        specClock1.addTransition(q1B, q2, new CharTransitionLabel('4'));
        specClock1.addTransition(q1B, q2, new CharTransitionLabel('5'));
        specClock1.addTransition(q1B, q2, new CharTransitionLabel('6'));
        specClock1.addTransition(q1B, q2, new CharTransitionLabel('7'));
        specClock1.addTransition(q1B, q2, new CharTransitionLabel('8'));
        specClock1.addTransition(q1B, q2, new CharTransitionLabel('9'));

        specClock1.addTransition(q1C, q2, new CharTransitionLabel('0'));
        specClock1.addTransition(q1C, q2, new CharTransitionLabel('1'));
        specClock1.addTransition(q1C, q2, new CharTransitionLabel('2'));
        specClock1.addTransition(q1C, q2, new CharTransitionLabel('3'));

        specClock1.addTransition(q2, q3, new CharTransitionLabel(':'));
        specClock1.addTransition(q1A, q3, new CharTransitionLabel(':'));
        specClock1.addTransition(q1B, q3, new CharTransitionLabel(':'));
        specClock1.addTransition(q1C, q3, new CharTransitionLabel(':'));

        specClock1.addTransition(q3, q4, new CharTransitionLabel('0'));
        specClock1.addTransition(q3, q4, new CharTransitionLabel('1'));
        specClock1.addTransition(q3, q4, new CharTransitionLabel('2'));
        specClock1.addTransition(q3, q4, new CharTransitionLabel('3'));
        specClock1.addTransition(q3, q4, new CharTransitionLabel('4'));
        specClock1.addTransition(q3, q4, new CharTransitionLabel('5'));

        specClock1.addTransition(q4, q5, new CharTransitionLabel('0'));
        specClock1.addTransition(q4, q5, new CharTransitionLabel('1'));
        specClock1.addTransition(q4, q5, new CharTransitionLabel('2'));
        specClock1.addTransition(q4, q5, new CharTransitionLabel('3'));
        specClock1.addTransition(q4, q5, new CharTransitionLabel('4'));
        specClock1.addTransition(q4, q5, new CharTransitionLabel('5'));
        specClock1.addTransition(q4, q5, new CharTransitionLabel('6'));
        specClock1.addTransition(q4, q5, new CharTransitionLabel('7'));
        specClock1.addTransition(q4, q5, new CharTransitionLabel('8'));
        specClock1.addTransition(q4, q5, new CharTransitionLabel('9'));

        specClock1.markAsInitial(q0);
        specClock1.markAsFinal(q5);

        AutomatonByRecursion automatonClock = new AutomatonByRecursion(specClock1);

        assertTrue(automatonClock.accepts("11:11"));
        assertTrue(automatonClock.accepts("23:59"));
        assertTrue(automatonClock.accepts("0:00"));
        assertTrue(automatonClock.accepts("3:45"));
        assertTrue(automatonClock.accepts("03:45"));
        assertTrue(automatonClock.accepts("15:45"));
        assertTrue(automatonClock.accepts("12:00"));
        assertTrue(automatonClock.accepts("00:00"));
        assertTrue(automatonClock.accepts("19:45"));
        assertTrue(automatonClock.accepts("1:00"));
        assertTrue(automatonClock.accepts("20:59"));


        assertFalse(automatonClock.accepts("40:10"));
        assertFalse(automatonClock.accepts("10:60"));
        assertFalse(automatonClock.accepts("24:10"));
        assertFalse(automatonClock.accepts("0:1:0"));
        assertFalse(automatonClock.accepts(":3:10"));
        assertFalse(automatonClock.accepts("1:10-"));
        assertFalse(automatonClock.accepts("2,10"));
        assertFalse(automatonClock.accepts("25:10"));
        assertFalse(automatonClock.accepts(""));
        assertFalse(automatonClock.accepts("\n"));
        assertFalse(automatonClock.accepts(":::::"));
        assertFalse(automatonClock.accepts("666666"));
        assertFalse(automatonClock.accepts("true"));
    }
}
