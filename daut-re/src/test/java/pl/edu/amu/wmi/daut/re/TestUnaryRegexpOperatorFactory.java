package pl.edu.amu.wmi.daut.re;

import junit.framework.TestCase;

/**
 *
 * Test klasy UnaryRegexpOperatorFactory.
 */
public class TestUnaryRegexpOperatorFactory extends TestCase {

    /**
     * Test metody arity().
     */
    public final void testArity() {

        UnaryRegexpOperatorFactory factory = new DoNothingOperator.Factory();
        assertEquals(1, factory.arity());

    }

}
