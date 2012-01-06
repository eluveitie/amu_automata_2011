package pl.edu.amu.wmi.daut.base;

import junit.framework.TestCase;

/**
 * Test klasy Determinizer.
 */
public class TestDeterminizer extends TestCase {

    /**
     * Zestaw testow jakimi bedziemy poddawac zdeterminizowany automat.
     */
    public final void setOfTests(AutomatonSpecification nfa, int numberOfStates,
                                 int numberOfTransitions, String acceptedWord,
                                 String notAcceptedWord) {

        Determinizer determinizer =  new Determinizer();
        DeterministicAutomatonSpecification ndfa = new NaiveDeterministicAutomatonSpecification();

        determinizer.determinize(nfa, ndfa);

        if (ndfa.isEmpty()) {
            //assertFalse(ndfa.isNotEmpty()); //Uzycie tej metody powoduje
                                              //"zwieche" mvn przy testowaniu
            assertTrue(ndfa.isEmpty());
        } else {
            assertTrue(ndfa.isDeterministic());
            //assertTrue(ndfa.isNotEmpty()); //J.w.
            assertFalse(ndfa.isEmpty());
        }

        DeterministicAutomaton dfa = new DeterministicAutomaton(ndfa);

        if (acceptedWord != null & notAcceptedWord != null) {
            assertTrue(dfa.accepts(acceptedWord));
            assertFalse(dfa.accepts(notAcceptedWord));
        }

        //assertFalse(ndfa.uselessStates());  //Metoda ma bledy -> listOutOfBound

        assertEquals(ndfa.countTransitions(), numberOfTransitions);
        assertEquals(ndfa.countStates(), numberOfStates);
    }


    /**
     * Na poczatek pusty automat.
     */
    public final void test1() {
        AutomatonSpecification nfa = new NaiveAutomatonSpecification();

        setOfTests(nfa, 0, 0, null, null);
    }
    /**
     * Deterministyczny automat do determinizacji.
     */
    public final void test2() {
        AutomatonSpecification nfa = new NaiveAutomatonSpecification();

        State q0 = nfa.addState();
        State q1 = nfa.addState();

        nfa.markAsInitial(q0);
        nfa.markAsFinal(q1);

        nfa.addTransition(q0, q1, new CharTransitionLabel('a'));
        nfa.addLoop(q1, new CharTransitionLabel('b'));

        setOfTests(nfa, 2, 2, "abbb", "aba");
    }
    /**
     * Przykladowy nfa.
     */
    public final void test3() {
        AutomatonSpecification nfa = new NaiveAutomatonSpecification();

        State q0 = nfa.addState();
        State q1 = nfa.addState();
        State q2 = nfa.addState();

        nfa.markAsInitial(q0);
        nfa.markAsFinal(q2);

        nfa.addLoop(q0, new CharTransitionLabel('0'));
        nfa.addLoop(q0, new CharTransitionLabel('1'));
        nfa.addTransition(q0, q1, new CharTransitionLabel('1'));
        nfa.addTransition(q1, q2, new CharTransitionLabel('0'));
        nfa.addTransition(q1, q2, new CharTransitionLabel('1'));

        setOfTests(nfa, 4, 8, "111010", "111101");
    }
    /**
     * Przykladowy nfa.
     */
    public final void test4() {
        AutomatonSpecification nfa = new NaiveAutomatonSpecification();

        State q0 = nfa.addState();
        State q1 = nfa.addState();
        State q2 = nfa.addState();

        nfa.markAsInitial(q0);
        nfa.markAsFinal(q2);

        nfa.addLoop(q0, new CharTransitionLabel('b'));
        nfa.addLoop(q0, new CharTransitionLabel('c'));
        nfa.addTransition(q0, q1, new CharTransitionLabel('a'));
        nfa.addTransition(q1, q2, new CharTransitionLabel('a'));
        nfa.addTransition(q2, q1, new CharTransitionLabel('b'));
        nfa.addTransition(q2, q1, new CharTransitionLabel('c'));
        nfa.addLoop(q2, new CharTransitionLabel('b'));
        nfa.addLoop(q2, new CharTransitionLabel('c'));

        setOfTests(nfa, 4, 9, "bccbbcaabccccbcbcbcbcccbcabc", "bcbcbcabcbcbcbcaaccbcbc");
    }
    /**
     * Przykladowy  nfa.
     */
    public final void test5() {
        AutomatonSpecification nfa = new NaiveAutomatonSpecification();

        State q0 = nfa.addState();
        State q1 = nfa.addState();
        State q2 = nfa.addState();
        State q3 = nfa.addState();

        nfa.markAsInitial(q0);
        nfa.markAsFinal(q3);

        nfa.addTransition(q0, q1, new CharTransitionLabel('a'));
        nfa.addTransition(q0, q3, new CharTransitionLabel('a'));
        nfa.addLoop(q1, new CharTransitionLabel('a'));
        nfa.addTransition(q1, q2, new CharTransitionLabel('b'));
        nfa.addTransition(q2, q1, new CharTransitionLabel('a'));
        nfa.addTransition(q2, q3, new CharTransitionLabel('c'));
        nfa.addLoop(q3, new CharTransitionLabel('c'));

        setOfTests(nfa, 5, 9, "aabaaaabc", "abbcc");
    }
    /**
     * Test metody determinize.
     */
    public final void testDeterminize() {

        test1();
        test2();
        test3();
        test4();
        test5();
    }
}

