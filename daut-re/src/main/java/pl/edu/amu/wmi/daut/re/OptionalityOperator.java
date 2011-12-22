package pl.edu.amu.wmi.daut.re;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.EpsilonTransitionLabel;
import pl.edu.amu.wmi.daut.base.State;
import java.util.List;

/**
 * Klasa reprezentujaca operator ?
 */
public class OptionalityOperator extends UnaryRegexpOperator {

    /**
     * Główna metoda klasy.
     */
    public AutomatonSpecification createAutomatonFromOneAutomaton(
            AutomatonSpecification subautomaton) {
        State q0 = subautomaton.addState();
        subautomaton.markAsInitial(q0);
        subautomaton.markAsFinal(q0);
        subautomaton.addTransition(q0, subautomaton.getInitialState(),
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
            return new OptionalityOperator();
        }
    }
}
