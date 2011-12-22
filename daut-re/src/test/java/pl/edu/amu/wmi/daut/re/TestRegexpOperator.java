package pl.edu.amu.wmi.daut.re;

import java.util.List;
import java.util.Arrays;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;

import junit.framework.TestCase;

/**
 * Testy klasy RegexpOperator.
 */
public class TestRegexpOperator extends TestCase {

    /**
     * Sprawdzamy, czy zostanie rzucony wyjątek, jeśli
     * nie zgadza się arność.
     */
    public final void testArityException() {
        RegexpOperator operator = new NullaryRegexpOperator() {
                public AutomatonSpecification createFixedAutomaton() {
                    return new NaiveAutomatonSpecification();
                }
            };

        List<AutomatonSpecification> oneElementList
            = Arrays.<AutomatonSpecification>asList(
                new NaiveAutomatonSpecification());

        boolean thrown = false;
        try {
            operator.createAutomaton(oneElementList);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

}
