package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomataOperations;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import java.util.List;

/**
 * Klasa reprezentująca operator '|' z wyrażeń regularnych (alternatywa).
 */
public class AlternativeOperator extends BinaryRegexpOperator {

    @Override
    public final AutomatonSpecification createAutomatonFromTwoAutomata(
            AutomatonSpecification leftSubautomaton,
            AutomatonSpecification rightSubautomaton) {
        return AutomataOperations.sum(leftSubautomaton, rightSubautomaton);
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
            return new AlternativeOperator();
        }
    }

}
