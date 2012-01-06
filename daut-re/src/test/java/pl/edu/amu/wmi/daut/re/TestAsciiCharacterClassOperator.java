package pl.edu.amu.wmi.daut.re;

import java.util.ArrayList;
import pl.edu.amu.wmi.daut.base.NondeterministicAutomatonByThompsonApproach;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import junit.framework.TestCase;

/**
 * Test klasy AsciiCharacterClassOperator.
 */
public class TestAsciiCharacterClassOperator extends TestCase {
    /**
     * Test konstruktora AsciiCharacterClassOperator dla złych wartości parametru.
     */
    public void testAsciiCharacterClassOperatorWrongParams() {

        try {
            ArrayList<String> params = new ArrayList<String>();
            params.add("zlyTest1");
            (new AsciiCharacterClassOperator.Factory()).createOperator(params);
            fail();
        } catch (UnknownAsciiCharacterClassException e) {
            assertTrue(true);
        }
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia [:alnum:].
     */
    public final void testAsciiCharacterClassOperatorAlnum() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("alnum");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("w"));
        assertTrue(automaton.accepts("9"));
        assertTrue(automaton.accepts("W"));
        assertTrue(automaton.accepts("a"));
        assertTrue(automaton.accepts("6"));

        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts(" "));
        assertFalse(automaton.accepts("g9"));
        assertFalse(automaton.accepts("bedziedobrze"));
        assertFalse(automaton.accepts("7dwa7dwa"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia alpha.
     */
    public final void testAsciiCharacterClassOperatorAlpha() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("alpha");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("A"));
        assertTrue(automaton.accepts("s"));
        assertTrue(automaton.accepts("I"));
        assertTrue(automaton.accepts("a"));

        assertFalse(automaton.accepts("7"));
        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("wrotka"));
        assertFalse(automaton.accepts("siedem"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia blank.
     */
    public final void testAsciiCharacterClassOperatorBlank() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("blank");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("\t"));
        assertTrue(automaton.accepts(" "));

        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("M"));
        assertFalse(automaton.accepts("a"));
        assertFalse(automaton.accepts("456"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia cntrl.
     */
    public final void testAsciiCharacterClassOperatorCntrl() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("cntrl");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("\u0000"));
        assertTrue(automaton.accepts("\u0002"));
        assertTrue(automaton.accepts("\u001F"));
        assertTrue(automaton.accepts("\u000F"));

        assertFalse(automaton.accepts("\u1044"));
        assertFalse(automaton.accepts(" t67"));
        assertFalse(automaton.accepts("g9"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia digit.
     */
    public final void testAsciiCharacterClassOperatorDigit() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("digit");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("1"));
        assertTrue(automaton.accepts("9"));
        assertTrue(automaton.accepts("7"));
        assertTrue(automaton.accepts("3"));

        assertFalse(automaton.accepts("bakteryja"));
        assertFalse(automaton.accepts(" "));
        assertFalse(automaton.accepts("digit"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia graph.
     */
    public final void testAsciiCharacterClassOperatorGraph() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("graph");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("~"));
        assertTrue(automaton.accepts("!"));
        assertTrue(automaton.accepts("-"));

        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts(" "));
        assertFalse(automaton.accepts("^^"));
        assertFalse(automaton.accepts("o/\\o"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia lower.
     */
    public final void testAsciiCharacterClassOperatorLower() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("lower");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("p"));
        assertTrue(automaton.accepts("i"));
        assertTrue(automaton.accepts("w"));
        assertTrue(automaton.accepts("o"));

        assertFalse(automaton.accepts("W"));
        assertFalse(automaton.accepts("I"));
        assertFalse(automaton.accepts("N"));
        assertFalse(automaton.accepts("O"));
        assertFalse(automaton.accepts("!"));
        assertFalse(automaton.accepts("sobota"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia print.
     */
    public final void testAsciiCharacterClassOperatorPrint() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("print");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("-"));
        assertTrue(automaton.accepts("a"));
        assertTrue(automaton.accepts("B"));
        assertTrue(automaton.accepts("$"));
        assertTrue(automaton.accepts("~"));
        assertTrue(automaton.accepts(" "));

        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("Lech"));
        assertFalse(automaton.accepts("+*+"));
        assertFalse(automaton.accepts("<>"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia punct.
     */
    public final void testAsciiCharacterClassOperatorPunct() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("punct");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("!"));
        assertTrue(automaton.accepts(":"));
        assertTrue(automaton.accepts("@"));
        assertTrue(automaton.accepts("~"));

        assertFalse(automaton.accepts(":("));
        assertFalse(automaton.accepts(":-("));
        assertFalse(automaton.accepts(":-@"));
        assertFalse(automaton.accepts("~-~"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia space.
     */
    public final void testAsciiCharacterClassOperatorSpace() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("space");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("\t"));
        assertTrue(automaton.accepts("\n"));
        assertTrue(automaton.accepts("\u000B"));

        assertFalse(automaton.accepts("aqq"));
        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("ola"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia upper.
     */
    public final void testAsciiCharacterClassOperatorUpper() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("upper");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("A"));
        assertTrue(automaton.accepts("L"));
        assertTrue(automaton.accepts("I"));

        assertFalse(automaton.accepts("w"));
        assertFalse(automaton.accepts("i"));
        assertFalse(automaton.accepts("a"));
        assertFalse(automaton.accepts("t"));
        assertFalse(automaton.accepts("r"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia word.
     */
    public final void testAsciiCharacterClassOperatorWord() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("word");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("_"));
        assertTrue(automaton.accepts("7"));
        assertTrue(automaton.accepts("w"));
        assertTrue(automaton.accepts("Z"));
        assertTrue(automaton.accepts("1"));

        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("112"));
        assertFalse(automaton.accepts(" "));
        assertFalse(automaton.accepts("cotojestkakademona"));
    }

    /**
     * Testuje automat AsciiCharacterClassOperator dla przejscia xdigit.
     */
    public final void testAsciiCharacterClassOperatorXdigit() {

        ArrayList<String> params = new ArrayList<String>();
        params.add("xdigit");
        RegexpOperator spec = (new AsciiCharacterClassOperator.Factory()).createOperator(params);
        NondeterministicAutomatonByThompsonApproach automaton =
            new NondeterministicAutomatonByThompsonApproach(spec.createAutomaton(
                new ArrayList<AutomatonSpecification>()));

        assertTrue(automaton.accepts("F"));
        assertTrue(automaton.accepts("9"));
        assertTrue(automaton.accepts("f"));
        assertTrue(automaton.accepts("C"));
        assertTrue(automaton.accepts("1"));

        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("g"));
        assertFalse(automaton.accepts("G"));
        assertFalse(automaton.accepts("12"));
    }
}

