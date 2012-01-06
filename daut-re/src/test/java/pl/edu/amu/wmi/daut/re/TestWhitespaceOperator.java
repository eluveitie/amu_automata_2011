package pl.edu.amu.wmi.daut.re;

import junit.framework.TestCase;
import static org.junit.Assert.*;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.AutomatonByRecursion;
import pl.edu.amu.wmi.daut.re.WhitespaceOperator.Factory;
import java.util.ArrayList;

/**
 * Test klasy WhitespaceOperator.
 */
public class TestWhitespaceOperator extends TestCase {

    /**
     * Test metody createFixedAutomaton.
     */
    public void testCreateFixedAutomaton() {

        WhitespaceOperator operator = new WhitespaceOperator();
        AutomatonSpecification spec = operator.createFixedAutomaton();
        assertFalse(spec.isEmpty());
        assertFalse(spec.acceptEmptyWord());
        AutomatonByRecursion automaton = new AutomatonByRecursion(spec);
        assertTrue(automaton.accepts("\t"));
        assertTrue(automaton.accepts("\n"));
        assertTrue(automaton.accepts("\f"));
        assertTrue(automaton.accepts("\r"));
        assertTrue(automaton.accepts("\u000B"));
        assertFalse(automaton.accepts("'"));
        assertFalse(automaton.accepts("%"));
        assertFalse(automaton.accepts("4"));
        assertFalse(automaton.accepts("0"));
    }

    /**
     * Test fabryki.
     */
    public void testFactory() {

        Factory factory = new Factory();
        RegexpOperator operator2 = factory.createOperator(new ArrayList<String>());
        int arity = operator2.arity();
        assertEquals(arity, 0);

    }
}
