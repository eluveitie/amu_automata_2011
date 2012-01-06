package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import pl.edu.amu.wmi.daut.base.State;
import pl.edu.amu.wmi.daut.base.CharTransitionLabel;
import pl.edu.amu.wmi.daut.base.NondeterministicAutomatonByThompsonApproach;
import pl.edu.amu.wmi.daut.re.OptionalityOperator.Factory;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 * Test klasy OptionalityOperator.
 */
public class TestOptionalityOperator extends TestCase {

    /**
     * Test metody createAutomatonFromOneAutomaton.
     */
    public final void testCreateAutomatonFromOneAutomaton() {

        AutomatonSpecification automaton = new NaiveAutomatonSpecification();

        State q0 = automaton.addState();
        State q1 = automaton.addState();
        State q2 = automaton.addState();
        automaton.addTransition(q0, q1, new CharTransitionLabel('a'));
        automaton.addTransition(q1, q2, new CharTransitionLabel('b'));
        automaton.addLoop(q1, new CharTransitionLabel('a'));
        automaton.addLoop(q2, new CharTransitionLabel('b'));

        automaton.markAsInitial(q0);
        automaton.markAsFinal(q2);

        OptionalityOperator operator = new OptionalityOperator();
        NondeterministicAutomatonByThompsonApproach result =
            new NondeterministicAutomatonByThompsonApproach(
                operator.createAutomatonFromOneAutomaton(automaton));

        assertTrue(result.accepts("aaabbb"));
        assertTrue(result.accepts("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbb"));
        assertTrue(result.accepts("aabbbbbbbbbbbbb"));
        assertTrue(result.accepts("ab"));
        assertTrue(automaton.acceptEmptyWord());

        assertFalse(result.accepts("aaabbbbaabbbbab"));
        assertFalse(result.accepts("aaaaaaaaaaa"));
        assertFalse(result.accepts("cojapacze"));
        assertFalse(result.accepts("macrimnatueshzanc"));
        assertFalse(result.accepts("katrayzniaedc"));
        assertFalse(result.accepts("jestemglupimstringieminicnieznacze"));
        assertFalse(result.accepts("jestemautomateminieakceptuje"));
    }

    /**
     * Test fabryki.
     */
    public final void testFactory() {

        Factory factory = new Factory();
        ArrayList<String> params = new ArrayList<String>();
        assertEquals(factory.createOperator(params).getClass(),
            new OptionalityOperator().getClass());
    }
}
