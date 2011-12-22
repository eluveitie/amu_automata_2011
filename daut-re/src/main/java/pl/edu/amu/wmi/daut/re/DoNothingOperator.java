package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import java.util.List;

/**
 * Klasa dziedzicząca po UnaryRegexpOperator,
 * która nic nie robi.
 */
public class DoNothingOperator extends UnaryRegexpOperator {

    @Override
    public AutomatonSpecification createAutomatonFromOneAutomaton(
        AutomatonSpecification subautomaton) {
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
            return new DoNothingOperator();
        }
    }

}
