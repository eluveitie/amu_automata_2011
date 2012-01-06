package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import pl.edu.amu.wmi.daut.base.State;
import pl.edu.amu.wmi.daut.base.ComplementCharClassTransitionLabel;
import java.util.List;

/**
 * Klasa reprezentującą dowolny znak niebędący cyfrą (\D).
 */
public class NoDigitOperator extends NullaryRegexpOperator {
    /**
     * Główna metoda klasy.
     */
    public AutomatonSpecification createFixedAutomaton() {
        AutomatonSpecification noDigitAutomaton = new NaiveAutomatonSpecification();
        State state1 = noDigitAutomaton.addState();
        State state2 = noDigitAutomaton.addState();
        noDigitAutomaton.addTransition(state1, state2,
                new ComplementCharClassTransitionLabel("0-9"));
        noDigitAutomaton.markAsInitial(state1);
        noDigitAutomaton.markAsFinal(state2);
        return noDigitAutomaton;
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
            return new NoDigitOperator();
        }
    }
}
