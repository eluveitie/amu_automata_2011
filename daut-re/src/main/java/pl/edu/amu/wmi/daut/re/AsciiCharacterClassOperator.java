package pl.edu.amu.wmi.daut.re;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.CharClassTransitionLabel;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import pl.edu.amu.wmi.daut.base.State;


class UnknownAsciiCharacterClassException extends RuntimeException {
}

/**
 * ASCII character classes.
 */
public class AsciiCharacterClassOperator extends NullaryRegexpOperator {
    private String str;


    private static final Map<String, String> MAP_OF_ASCII_CHARACTER_CLASS = createMap();


    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("alnum", "0-9A-Za-z");
        result.put("alpha", "A-Za-z");
        result.put("blank", "\t ");
        result.put("cntrl", "\u0000-\u001F\u007F");
        result.put("digit", "0-9");
        result.put("graph", "!~-");
        result.put("lower", "a-z");
        result.put("print", " -~");
        result.put("punct", "!-/:-@[-`{-~");
        result.put("space", "\t\n\f\r \u000B");
        result.put("upper", "A-Z");
        result.put("word", "0-9A-Za-z_");
        result.put("xdigit", "0-9A-Fa-f");
        return Collections.unmodifiableMap(result);
    }


    /**
     * Konstruktor ASCII character classes.
     */
    public AsciiCharacterClassOperator(String classString) {
        transformToClassString(classString);
    }


    private void transformToClassString(String a) {
        str = MAP_OF_ASCII_CHARACTER_CLASS.get(a);
        if (str == null)
            throw new UnknownAsciiCharacterClassException();
    }

    /**
     * Generuje automat.
     */
    @Override
    public AutomatonSpecification createFixedAutomaton() {

        AutomatonSpecification automaton = new NaiveAutomatonSpecification();
        State q0 = automaton.addState();
        State q1 = automaton.addState();
        automaton.markAsInitial(q0);
        automaton.markAsFinal(q1);

        automaton.addTransition(q0, q1, new CharClassTransitionLabel(str));

        return automaton;
    }

    /**
     * Fabryka operatora.
     */
    public static class Factory extends NullaryRegexpOperatorFactory {

        @Override
        protected RegexpOperator doCreateOperator(List<String> params) {
            return new AsciiCharacterClassOperator(params.get(0));
        }

        @Override
        public int numberOfParams() {
            return 1;
        }
    }
}
