package pl.edu.amu.wmi.daut.re;
import junit.framework.TestCase;

/**
 *
 * Test klasy BinaryRegexpOperatorFactory.
 */
public class TestBinaryRegexpOperatorFactory extends TestCase {

    /**
     * Test metody arity().
     * Klasa AlternativeOperator posiada w sobie
     * fabrykę dziedziczącą po BinaryRegexpOperatorFactory.
     */
    public final void testarity() {

        RegexpOperatorFactory factory = new AlternativeOperator.Factory();
        assertEquals(2, factory.arity());
    }

}
