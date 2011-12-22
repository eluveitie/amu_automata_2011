package pl.edu.amu.wmi.daut.re;

import java.util.List;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;

/**
 * Klasa abstrakcyjna reprezentująca operator unarny (jednoargumentowy).
 */
abstract class UnaryRegexpOperator extends RegexpOperator {

    public int arity() {
        return 1;
    }

    @Override
    protected final AutomatonSpecification doCreateAutomaton(
        List<AutomatonSpecification> subautomata) {

        return createAutomatonFromOneAutomaton(subautomata.get(0));
    }

    /**
     * Właściwa metoda budująca automat na podstawie podautomatu.
     *
     * To właśnie ta metoda powinna być zdefiniowana w klasach
     * dziedziczących z niniejszej klasy.
     */
    public abstract AutomatonSpecification createAutomatonFromOneAutomaton(
        AutomatonSpecification subautomaton);

};
