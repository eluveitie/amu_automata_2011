package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NondeterministicAutomatonByThompsonApproach;
import pl.edu.amu.wmi.daut.base.State;
import pl.edu.amu.wmi.daut.base.CharTransitionLabel;
import pl.edu.amu.wmi.daut.re.DoNothingOperator.Factory;

import junit.framework.TestCase;
import java.util.ArrayList;


/**
 * Testy klasy DoNothingOperator.
 */
public class TestDoNothingOperator extends TestCase {

    /**
     * Test metody createAutomatonFromOneAutomaton
     * z automatem z jednym stanem.
     */
    public final void testCreateAutomatonFromOneAutomaton1() {

        AutomatonSpecification subautomaton = new NaiveAutomatonSpecification();
        State q0 = subautomaton.addState();
        subautomaton.markAsInitial(q0);
        subautomaton.markAsFinal(q0);

        DoNothingOperator operator = new DoNothingOperator();
        NondeterministicAutomatonByThompsonApproach automaton =
                new NondeterministicAutomatonByThompsonApproach(
                operator.createAutomatonFromOneAutomaton(subautomaton));

        assertTrue(automaton.accepts(""));
        assertFalse(automaton.accepts("word"));
    }

    /**
     * Test metody createAutomatonFromOneAutomaton
     * z automatem z kilkoma stanami i przejściami.
     */
    public final void testCreateAutomatonFromOneAutomaton2() {

        AutomatonSpecification subautomaton = new NaiveAutomatonSpecification();
        State q0 = subautomaton.addState();
        State q1 = subautomaton.addState();
        State q2 = subautomaton.addState();
        subautomaton.addTransition(q0, q1, new CharTransitionLabel('a'));
        subautomaton.addLoop(q1, new CharTransitionLabel('b'));
        subautomaton.addTransition(q1, q2, new CharTransitionLabel('a'));
        subautomaton.markAsInitial(q0);
        subautomaton.markAsFinal(q2);

        DoNothingOperator operator = new DoNothingOperator();
        NondeterministicAutomatonByThompsonApproach automaton =
                new NondeterministicAutomatonByThompsonApproach(
                operator.createAutomatonFromOneAutomaton(subautomaton));

        assertTrue(automaton.accepts("aa"));
        assertTrue(automaton.accepts("aba"));
        assertTrue(automaton.accepts("abbbbbbbbbbbbbba"));
        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("abb"));

    }

    /**
     * Test fabryki.
     */
    public final void testFactory() {

        Factory factory = new Factory();
        ArrayList<String> params = new ArrayList<String>();
        assertEquals(factory.createOperator(params).getClass(),
            new DoNothingOperator().getClass());

    }

}
