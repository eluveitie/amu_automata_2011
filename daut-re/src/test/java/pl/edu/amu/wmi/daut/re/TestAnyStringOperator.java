package pl.edu.amu.wmi.daut.re;

import java.util.ArrayList;
import pl.edu.amu.wmi.daut.base.NondeterministicAutomatonByThompsonApproach;
import junit.framework.TestCase;
import pl.edu.amu.wmi.daut.re.AnyStringOperator.Factory;

/**
 * Test klasy AnyStringOperator.
 */
public class TestAnyStringOperator extends TestCase {

    /**
     * Test konstruktora AnyStringOperator.
     */
    public void testAnyStringOperator() {
        AnyStringOperator operator = new AnyStringOperator();
    }

    /**
     * Testuje automat roznych znakow, takze pustego przejscia.
     */
    public final void testcreateFixedAutomaton() {

        AnyStringOperator spec2 = new AnyStringOperator();
        NondeterministicAutomatonByThompsonApproach automaton2 =
                new NondeterministicAutomatonByThompsonApproach(spec2.createFixedAutomaton());

        assertTrue(automaton2.accepts("qwer"));
        assertTrue(automaton2.accepts("qqqq"));
        assertTrue(automaton2.accepts("w"));
        assertTrue(automaton2.accepts("kakademona"));
        assertTrue(automaton2.accepts("qqrrqqrr"));
        assertTrue(automaton2.accepts(""));
        assertTrue(automaton2.accepts("  s  "));
    }

    /**
     * Test fabryki.
     */
    public final void testFactory() {
        Factory factory = new Factory();
        ArrayList<String> params = new ArrayList<String>();
        assertEquals(factory.createOperator(params).getClass(),
                new AnyStringOperator().getClass());
    }
}
