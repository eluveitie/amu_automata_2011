package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import pl.edu.amu.wmi.daut.base.EpsilonTransitionLabel;
import pl.edu.amu.wmi.daut.base.AutomataOperations;
import pl.edu.amu.wmi.daut.base.State;
import java.util.List;

/**
 * Klasa reprezentujaca operator oznaczajacy, ze dwa elementy moga wystapic jeden po drugim
 * w dowolnej kolejnosci.
 */
public class AnyOrderOperator extends BinaryRegexpOperator {

    @Override
    public AutomatonSpecification createAutomatonFromTwoAutomata(
            AutomatonSpecification leftSubautomaton, AutomatonSpecification rightSubautomaton) {

        AutomatonSpecification automatonLR = new NaiveAutomatonSpecification();
        AutomatonSpecification automatonRL = new NaiveAutomatonSpecification();

        State initialStateL = automatonLR.addState();
        State finalStateL = automatonLR.addState();
        automatonLR.markAsInitial(initialStateL);
        automatonLR.insert(initialStateL, leftSubautomaton);
        for (State q : automatonLR.allStates()) {
            if (automatonLR.isFinal(q)) {
               automatonLR.addTransition(q, finalStateL, new EpsilonTransitionLabel());
               automatonLR.unmarkAsFinalState(q);
            }
        }
        automatonLR.insert(finalStateL, rightSubautomaton);

        State initialStateR = automatonRL.addState();
        State finalStateR = automatonRL.addState();
        automatonRL.markAsInitial(initialStateR);
        automatonRL.insert(initialStateR, rightSubautomaton);
        for (State q : automatonRL.allStates()) {
            if (automatonRL.isFinal(q)) {
               automatonRL.addTransition(q, finalStateR, new EpsilonTransitionLabel());
               automatonRL.unmarkAsFinalState(q);
            }
        }
        automatonRL.insert(finalStateR, leftSubautomaton);

        return AutomataOperations.sum(automatonLR, automatonRL);
    }

     /**
      * Fabryka operatora.
      */
    public static class Factory extends BinaryRegexpOperatorFactory {

        @Override
        public int numberOfParams() {
            return 0;
        }

        protected RegexpOperator doCreateOperator(List<String> params) {
            return new AnyOrderOperator();
        }
    }
}
