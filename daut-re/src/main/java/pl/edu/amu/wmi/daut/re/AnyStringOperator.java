package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.State;
import pl.edu.amu.wmi.daut.base.AnyTransitionLabel;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import java.util.List;

/**
 * Reprezentuje dowolny napis, dowolnej długości (także pusty).
 * Odpowiada to wyrażeniu regularnego '.*'.
 */
public class AnyStringOperator extends NullaryRegexpOperator {
    @Override
    public final AutomatonSpecification createFixedAutomaton() {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q = spec.addState();
        spec.markAsInitial(q);
        spec.markAsFinal(q);

        spec.addTransition(q, q, new AnyTransitionLabel());

        return spec;
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
            return new AnyStringOperator();
        }
    }
}
