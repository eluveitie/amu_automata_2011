package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import pl.edu.amu.wmi.daut.base.State;
import java.util.List;
import pl.edu.amu.wmi.daut.base.CharClassTransitionLabel;


/**
 *
 * @author cole1911
 *
 * klasa reprezentująca dowolny pusty znak.
 * */
public class WhitespaceOperator extends NullaryRegexpOperator {
    private static final String WHITESPACE_CHARS = "\t\n\f\r \u000B";

    @Override
    public AutomatonSpecification createFixedAutomaton() {
        AutomatonSpecification automaton = new NaiveAutomatonSpecification();
        State q0 = automaton.addState();
        State q1 = automaton.addState();
        automaton.markAsInitial(q0);
        automaton.markAsFinal(q1);
        automaton.addTransition(q0, q1, new CharClassTransitionLabel(WHITESPACE_CHARS));
        return automaton;
    }

    /**
      * Fabryka operatora.
      */
    public static class Factory extends BinaryRegexpOperatorFactory {

        @Override
        public int numberOfParams() {
            return 0;
        }

        @Override
        protected RegexpOperator doCreateOperator(List<String> params) {
            return new WhitespaceOperator();
        }
    }
}
