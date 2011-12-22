package pl.edu.amu.wmi.daut.base;

import junit.framework.TestCase;

/**
 * Klasa testujaca metodę isDeterministic.
 */
public class TestIsDeterministic extends TestCase {

    /**
    * Test metody isDeterministic na automacie z jednym przejściem.
    */
    public final void testOneTransition() {

        AutomatonSpecification aut = new NaiveAutomatonSpecification();
        aut.makeOneTransitionAutomaton('g');

        assertTrue(aut.isDeterministic());
    }

    /**
     * Test metody isDeterministic na automacie z czterema przejściami.
     */
    public final void testFourTransitions() {

        AutomatonSpecification aut = new NaiveAutomatonSpecification();

        State s0 = aut.addState();
        State s1 = aut.addState();
        State s2 = aut.addState();
        State s3 = aut.addState();

        aut.markAsInitial(s0);
        aut.markAsFinal(s1);
        aut.markAsFinal(s2);
        aut.markAsFinal(s3);

        aut.addTransition(s0, s1, new CharTransitionLabel('a'));
        aut.addTransition(s0, s2, new CharTransitionLabel('b'));
        aut.addTransition(s0, s3, new CharTransitionLabel('b'));
        aut.addTransition(s1, s3, new CharTransitionLabel('d'));


        assertFalse(aut.isDeterministic());
    }

    /**
     * Test metody isDeterministic na automacie sześcioma przejściami.
     */
    public final void testSixTransitions() {

        AutomatonSpecification aut = new NaiveAutomatonSpecification();

        State s0 = aut.addState();
        State s1 = aut.addState();
        State s2 = aut.addState();
        State s3 = aut.addState();
        State s4 = aut.addState();
        State s5 = aut.addState();

        aut.markAsInitial(s0);
        aut.markAsFinal(s3);
        aut.markAsFinal(s4);
        aut.markAsFinal(s5);

        aut.addTransition(s0, s1, new CharTransitionLabel('a'));
        aut.addTransition(s1, s2, new CharTransitionLabel('a'));
        aut.addTransition(s2, s3, new CharTransitionLabel('a'));
        aut.addTransition(s3, new CharTransitionLabel('a'));
        aut.addTransition(s2, s4, new CharTransitionLabel('c'));
        aut.addTransition(s4, new CharTransitionLabel('b'));
        aut.addTransition(s4, s5, new CharTransitionLabel('b'));
        aut.addTransition(s5, new CharTransitionLabel('a'));
        aut.addTransition(s4, s5, new ComplementCharClassTransitionLabel("ab-gh"));

        assertFalse(aut.isDeterministic());
    }

    /**
     * Test metody isDeterministic na pustym automacie.
     */
    public final void testEmptyAutomaton() {

        AutomatonSpecification aut = new NaiveAutomatonSpecification();

        assertFalse(aut.isDeterministic());
    }

    /**
     * Test metody isDeterministic na automacie z jednym epsilon przejściem.
     */
    public final void testDeterministicEpsilonTransition() {

        AutomatonSpecification aut = new NaiveAutomatonSpecification();

        State s0 = aut.addState();
        State s1 = aut.addState();

        aut.markAsInitial(s0);
        aut.markAsFinal(s1);

        aut.addTransition(s0, s1, new EpsilonTransitionLabel());
        aut.addTransition(s1, s0, new CharTransitionLabel('a'));

        assertTrue(aut.isDeterministic());
    }

    /**
     * Test metody isDeterministic z dwoma epsilon przejściami.
     */
    public final void testIndeterministicEpsilonTransition() {

        AutomatonSpecification aut = new NaiveAutomatonSpecification();

        State s0 = aut.addState();
        State s1 = aut.addState();
        State s2 = aut.addState();

        aut.markAsInitial(s0);
        aut.markAsFinal(s1);

        aut.addTransition(s0, s1, new EpsilonTransitionLabel());
        aut.addTransition(s0, s2, new EpsilonTransitionLabel());
        aut.addTransition(s1, s0, new CharTransitionLabel('a'));

        assertFalse(aut.isDeterministic());
    }

    /**
     * Test metody isDeterministic na automacie z mieszanymi przejściami.
     */
    public final void testIndeterministicEpsilonInepsilonTransition() {

        AutomatonSpecification aut = new NaiveAutomatonSpecification();

        State s0 = aut.addState();
        State s1 = aut.addState();
        State s2 = aut.addState();

        aut.markAsInitial(s0);
        aut.markAsFinal(s1);

        aut.addTransition(s0, s1, new EpsilonTransitionLabel());
        aut.addTransition(s0, s2, new CharTransitionLabel('b'));
        aut.addTransition(s1, s0, new CharTransitionLabel('a'));

        assertFalse(aut.isDeterministic());
    }
};
