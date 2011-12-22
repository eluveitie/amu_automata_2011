package pl.edu.amu.wmi.daut.re;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * Test klasy RegexpOperatorFactory.
 */
public class TestRegexpOperatorFactory extends TestCase {

    /**
     * Test metody createOperator().
     */
    public final void testCreateOperator() {

        RegexpOperatorFactory factory = new AlternativeOperator.Factory();
        List<String> strList = new ArrayList();

        assertNotNull(factory.createOperator(strList));

        strList.add("a");
        boolean exceptionThrow = false;
        try {
            factory.createOperator(strList);
        } catch (Exception e) {
            exceptionThrow = true;
        }

        assertTrue(exceptionThrow);
    }

}
