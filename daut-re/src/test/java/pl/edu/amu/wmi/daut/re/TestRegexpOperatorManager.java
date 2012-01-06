/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.wmi.daut.re;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * Test klasy RegexpOperatorManager.
 */
public class TestRegexpOperatorManager extends TestCase {

    /**
     * Test metody getFactory i addOperator bez piorytetów.
     */
    public final void testAddOperatorWithoutPiority() {

        RegexpOperatorManager manager = new RegexpOperatorManager();

        manager.addOperator("*", new KleeneStarOperator.Factory(), Arrays.<String>asList("", "*"));
        manager.addOperator("{n,m}", new RangeNumberOfOccurrencesOperator.Factory(),
                Arrays.<String>asList("", "{", ",", "}"));
        manager.addOperator("s", new SingleCharacterOperator.Factory(),
                Arrays.<String>asList("", "s"));
        manager.addOperator("()", new DoNothingOperator.Factory(), Arrays.<String>asList("(", ")"));
        manager.addOperator(".", new AnyCharOperator.Factory(), Arrays.<String>asList("", "."));
        manager.addOperator("?", new AlternativeOperator.Factory(), Arrays.<String>asList("", "?"));

        assertNotNull(manager);
        assertNotNull(manager.getFactory("*"));
        assertNotNull(manager.getFactory("{n,m}"));
        assertNotNull(manager.getFactory("s"));
        assertNull(manager.getFactory("{n}"));
        assertNotNull(manager.getFactory("()"));
        assertNotNull(manager.getFactory("."));
        assertNotNull(manager.getFactory("?"));
    }

    /**
     * Test metody addOperator z piorytetem oraz getPriority.
     */
    public final void testAddOperatorWithPiority() {

        RegexpOperatorManager manager = new RegexpOperatorManager();

        manager.addOperator("*", new KleeneStarOperator.Factory(),
                Arrays.<String>asList("", "*"), 1);
        manager.addOperator("{n,m}", new RangeNumberOfOccurrencesOperator.Factory(),
                Arrays.<String>asList("", "{", ",", "}"), 2);
        manager.addOperator("s", new SingleCharacterOperator.Factory(),
                Arrays.<String>asList("", "s"), 3);
        manager.addOperator("()", new DoNothingOperator.Factory(),
                Arrays.<String>asList("(", ")"), 4);
        manager.addOperator(".", new AnyCharOperator.Factory(), Arrays.<String>asList("", "."), 5);
        manager.addOperator("?", new AlternativeOperator.Factory(),
                Arrays.<String>asList("", "?"), 6);

        assertEquals(1, manager.getPriority("*"));
        assertEquals(2, manager.getPriority("{n,m}"));
        assertEquals(3, manager.getPriority("s"));
        assertEquals(-1, manager.getPriority("{n}"));
        assertEquals(4, manager.getPriority("()"));
        assertEquals(5, manager.getPriority("."));
        assertEquals(6, manager.getPriority("?"));
    }

    /**
     * Test metody getSeparators.
     */
    public final void testGetSeparators() {

        RegexpOperatorManager manager = new RegexpOperatorManager();

        manager.addOperator("*", new KleeneStarOperator.Factory(), Arrays.<String>asList("", "*"));
        manager.addOperator("{n,m}", new RangeNumberOfOccurrencesOperator.Factory(),
                Arrays.<String>asList("", "{", ",", "}"));
        manager.addOperator("s", new SingleCharacterOperator.Factory(),
                Arrays.<String>asList("", "s"));

        List<String> kleene = new ArrayList<String>();
        kleene.add("");
        kleene.add("*");

        List<String> range = new ArrayList<String>();
        range.add("");
        range.add("{");
        range.add(",");
        range.add("}");

        List<String> single = new ArrayList<String>();
        single.add("");
        single.add("s");

        assertEquals(kleene, manager.getSeparators("*"));
        assertEquals(range, manager.getSeparators("{n,m}"));
        assertEquals(single, manager.getSeparators("s"));
        assertNull(manager.getSeparators("z"));
    }

    /**
     * Test metody getOperatorsForStringPrefix.
     */
    public final void testGetOperatorsForStringPrefix() {

        RegexpOperatorManager manager = new RegexpOperatorManager();

        manager.addOperator("*", new KleeneStarOperator.Factory(), Arrays.<String>asList("", "*"));
        manager.addOperator("{n,m}", new RangeNumberOfOccurrencesOperator.Factory(),
                Arrays.<String>asList("", "{", ",", "}"));
        manager.addOperator("s", new SingleCharacterOperator.Factory(),
                Arrays.<String>asList("", "s"));
        manager.addOperator("()", new DoNothingOperator.Factory(), Arrays.<String>asList("(", ")"));
        manager.addOperator(".", new AnyCharOperator.Factory(), Arrays.<String>asList("", "."));
        manager.addOperator("?", new AlternativeOperator.Factory(), Arrays.<String>asList("", "?"));

        List<String> string = new ArrayList<String>();

        string = manager.getOperatorsForStringPrefix("");
        assertEquals(Arrays.<String>asList("*", "{n,m}", "s", ".", "?"), string);

        string = manager.getOperatorsForStringPrefix("()");
        assertEquals(Arrays.<String>asList("()"), string);

        string = manager.getOperatorsForStringPrefix("ss*");
        assertEquals(Arrays.<String>asList("*", "{n,m}", "s", ".", "?"), string);

        string = manager.getOperatorsForStringPrefix(".");
        assertEquals(Arrays.<String>asList("*", "{n,m}", "s", ".", "?"), string);
    }

    /**
     * Test z getOperatorsForStringPrefix separatorem o długości dwa znaki.
     */
    public final void testGetOperatorsForStringPrefixWithTwoChars() {

        RegexpOperatorManager manager = new RegexpOperatorManager();

        manager.addOperator("!", new SingleCharacterOperator.Factory(),
                Arrays.<String>asList("!"));
        manager.addOperator("!!", new SingleCharacterOperator.Factory(),
                Arrays.<String>asList("!!"));

        List<String> string = new ArrayList<String>();

        string = manager.getOperatorsForStringPrefix("!!bla!bla");
        assertEquals(Arrays.<String>asList("!!"), string);
    }
}
