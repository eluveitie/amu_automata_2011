package pl.edu.amu.wmi.daut.re;

import junit.framework.TestCase;

/**
 *
 * Test klasy NullaryRegexpOperatorFactory.
 */
public class TestNullaryRegexpOperatorFactory extends TestCase {

    /**
     * Test metody arity().
     * SingleCharacterOperator posiada fabrykę dziedziczącą po NullaryRegexpOperatorFactory.
     */
    public final void testArity() {

        RegexpOperatorFactory factory = new SingleCharacterOperator.Factory();
        assertEquals(0, factory.arity());

    }

}
