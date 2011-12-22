package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import pl.edu.amu.wmi.daut.base.State;
import pl.edu.amu.wmi.daut.base.CharRangeTransitionLabel;
import java.util.List;

/**
 * Klasa reprezentującą dowolną cyfrę (\d w wyrażeniach regularnych języka Perl).
 */
public class DigitOperator extends NullaryRegexpOperator {

    /**
     * Główna metoda klasy.
     */
    @Override
    public AutomatonSpecification createFixedAutomaton() {

        AutomatonSpecification automaton = new NaiveAutomatonSpecification();
        char a = '0';
        char b = '9';
        State q0 = automaton.addState();
        State q1 = automaton.addState();
        automaton.addTransition(q0, q1, new CharRangeTransitionLabel(a, b));
        automaton.markAsInitial(q0);
        automaton.markAsFinal(q1);

        return automaton;
    }

    /**
     * Fabryka operatora.
     */
    public static class Factory extends NullaryRegexpOperatorFactory {

        @Override
        public int numberOfParams() {
            return 0;
        }

        protected RegexpOperator doCreateOperator(List<String> params) {
            return new DigitOperator();
        }
    }
}
