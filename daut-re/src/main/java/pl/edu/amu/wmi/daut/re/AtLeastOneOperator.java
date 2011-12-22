package pl.edu.amu.wmi.daut.re;

import java.util.List;

import pl.edu.amu.wmi.daut.base.EpsilonTransitionLabel;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.State;

/**
 * Klasa reprezentująca operator '+' z wyrażeń regularnych
 * (przynajmniej jedno wystąpienie).
 */
public class AtLeastOneOperator extends UnaryRegexpOperator {

    @Override
    public AutomatonSpecification createAutomatonFromOneAutomaton(
            AutomatonSpecification subautomaton) {
        State q = subautomaton.addState();
        List<State> listOfStates = subautomaton.allStates();
        subautomaton.markAsFinal(q);
        for (int i = 0; i < listOfStates.size(); i++) {
            if (subautomaton.isFinal(listOfStates.get(i))) {
                subautomaton.addTransition(listOfStates.get(i), q, new EpsilonTransitionLabel());
            }
        }
        subautomaton.addTransition(q, subautomaton.getInitialState(),
                new EpsilonTransitionLabel());
        return subautomaton;
    }

     /**
      * Fabryka operatora.
      */
    public static class Factory extends UnaryRegexpOperatorFactory {

        @Override
        public int numberOfParams() {
            return 0;
        }

        protected RegexpOperator doCreateOperator(List<String> params) {
            return new AtLeastOneOperator();
        }
    }
}
