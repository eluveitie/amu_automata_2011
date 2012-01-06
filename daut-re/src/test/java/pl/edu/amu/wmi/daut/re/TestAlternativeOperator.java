package pl.edu.amu.wmi.daut.re;

import java.util.ArrayList;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NondeterministicAutomatonByThompsonApproach;
import pl.edu.amu.wmi.daut.base.State;
import pl.edu.amu.wmi.daut.base.CharTransitionLabel;
import junit.framework.TestCase;
import pl.edu.amu.wmi.daut.re.AlternativeOperator.Factory;

/**
 * Test klasy AlternativeOperator.
 */
public class TestAlternativeOperator extends TestCase {

    /**
     * Test automatów:
     * automaton1 - akceptującego wyraz składający się z liter "a" i "b",
     *     który zawiera przynajmniej jedno "a"
     * automaton2 - akceptującego wyraz składający się z liter "a" i "b",
     *     który zawiera nieparzystą ilość "b".
     */
    public final void testCreateAutomatonFromTwoAutomata1() {

        AutomatonSpecification automaton1 = new NaiveAutomatonSpecification();

        State q0 = automaton1.addState();
        State q1 = automaton1.addState();
        automaton1.addTransition(q0, q1, new CharTransitionLabel('a'));
        automaton1.addLoop(q0, new CharTransitionLabel('b'));
        automaton1.addLoop(q1, new CharTransitionLabel('a'));
        automaton1.addLoop(q1, new CharTransitionLabel('b'));

        automaton1.markAsInitial(q0);
        automaton1.markAsFinal(q1);

        AutomatonSpecification automaton2 = new NaiveAutomatonSpecification();

        State q2 = automaton2.addState();
        State q3 = automaton2.addState();
        automaton2.addTransition(q2, q3, new CharTransitionLabel('b'));
        automaton2.addTransition(q3, q2, new CharTransitionLabel('b'));
        automaton2.addLoop(q2, new CharTransitionLabel('a'));
        automaton2.addLoop(q3, new CharTransitionLabel('a'));

        automaton2.markAsInitial(q2);
        automaton2.markAsFinal(q3);

        AlternativeOperator oper = new AlternativeOperator();
        NondeterministicAutomatonByThompsonApproach result =
        new NondeterministicAutomatonByThompsonApproach(
                oper.createAutomatonFromTwoAutomata(automaton1, automaton2));

        assertTrue(result.accepts("aaaa"));
        assertTrue(result.accepts("bbaaaa"));
        assertTrue(result.accepts("aaaaaaaaaaaabaaaaaa"));
        assertTrue(result.accepts("bbb"));
        assertTrue(result.accepts("baba"));
        assertTrue(result.accepts("bababab"));
        assertFalse(result.accepts(""));
        assertFalse(result.accepts("jakasbzdura"));
        assertFalse(result.accepts("bb"));
        assertFalse(result.accepts("kabaczek"));
    }

    /**
     * Test automatów:
     * automaton1 - akceptującego wyraz składający się z liter "a" i "b",
     *     postaci {(ab)^n : n>0}
     * automaton2 - automatu pustego.
     */
    public final void testCreateAutomatonFromTwoAutomata2() {

        AutomatonSpecification automaton1 = new NaiveAutomatonSpecification();

        State q0 = automaton1.addState();
        State q1 = automaton1.addState();
        State q2 = automaton1.addState();
        automaton1.addTransition(q0, q1, new CharTransitionLabel('a'));
        automaton1.addTransition(q1, q2, new CharTransitionLabel('b'));
        automaton1.addTransition(q2, q1, new CharTransitionLabel('a'));

        automaton1.markAsInitial(q0);
        automaton1.markAsFinal(q2);

        AutomatonSpecification automaton2 = new NaiveAutomatonSpecification();

        AlternativeOperator oper = new AlternativeOperator();
        NondeterministicAutomatonByThompsonApproach result =
        new NondeterministicAutomatonByThompsonApproach(
                oper.createAutomatonFromTwoAutomata(automaton1, automaton2));

        assertTrue(result.accepts("abab"));
        assertTrue(result.accepts("ababababababab"));
        assertFalse(result.accepts(""));
        assertFalse(result.accepts("cosdziwnego"));
        assertFalse(result.accepts("baaaaaaaaaa"));
        assertFalse(result.accepts("abababababb"));
        assertFalse(result.accepts("bababa"));
    }

    /**
     * Test fabryki operatora.
     */
    public final void testFactory() {
        Factory factory = new Factory();
        ArrayList<String> params = new ArrayList<String>();

        assertEquals(factory.createOperator(params).getClass(),
            new AlternativeOperator().getClass());
    }
}
