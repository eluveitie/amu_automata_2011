package pl.edu.amu.wmi.daut.re;

import java.util.ArrayList;
import pl.edu.amu.wmi.daut.base.NondeterministicAutomatonByThompsonApproach;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import junit.framework.TestCase;

/**
 * Klasa, testuje klasę BellCharacterOperator.
 */
public class TestBellCharacterOperator extends TestCase {

    /**
     * Konstruuje przykładowy automat, a następnie go testuje.
     */
    public void testAutomaton() {

        BellCharacterOperator operator = new BellCharacterOperator();
        AutomatonSpecification aut = operator.createFixedAutomaton();
        NondeterministicAutomatonByThompsonApproach a
            = new NondeterministicAutomatonByThompsonApproach(aut);

        assertTrue(a.accepts(Character.toString('\7')));
        assertFalse(a.accepts(Character.toString('8')));
        assertFalse(a.accepts(Character.toString('c')));
        assertTrue(a.accepts(Character.toString((char) 7)));
        assertFalse(a.accepts(Character.toString(' ')));
        assertFalse(a.accepts(Character.toString('\6')));
    }

    /**
     * Test fabryki operatora.
     */
    public void testFactory() {
        RegexpOperatorFactory factory = new BellCharacterOperator.Factory();
        ArrayList<String> params = new ArrayList<String>();
        assertEquals(0, factory.arity());
        assertEquals(factory.createOperator(params).getClass(),
            new BellCharacterOperator().getClass());
    }
}
