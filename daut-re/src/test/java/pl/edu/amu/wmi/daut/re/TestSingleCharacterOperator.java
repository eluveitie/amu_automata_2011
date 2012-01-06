package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NondeterministicAutomatonByThompsonApproach;
import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;


/**
 * Testy klasy SingleCharacterOperator.
 */
public class TestSingleCharacterOperator extends TestCase {

    /**
     * Metoda testujaca konstruktor.
     */
    public void testSingleCharacterOperator() {

        SingleCharacterOperator operator = new SingleCharacterOperator('c');
        assertNotNull(operator);
        assertEquals(operator.getCharacter(), 'c');
    }

    /**
     * Test metody createFixedAutomaton.
     */
    public final void testCreateFixedAutomaton() {

        SingleCharacterOperator operator = new SingleCharacterOperator('c');
        AutomatonSpecification automaton = operator.createFixedAutomaton();
        NondeterministicAutomatonByThompsonApproach result =
          new NondeterministicAutomatonByThompsonApproach(automaton);

        assertFalse(automaton.isEmpty());

        assertTrue(result.accepts("c"));

        assertFalse(result.accepts("cc"));
        assertFalse(result.accepts("a"));

        assertEquals('c', operator.getCharacter());
    }

    /**
     * Test fabryki.
     */
    public final void testFactory() {

        SingleCharacterOperator.Factory factory = new SingleCharacterOperator.Factory();
        List<String> list = new ArrayList<String>();
        list.add("a");

        SingleCharacterOperator operator = (SingleCharacterOperator) factory.createOperator(list);

        assertNotNull(operator);

        assertEquals(1, factory.numberOfParams());
        assertEquals('a', operator.getCharacter());
    }
}
