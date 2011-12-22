package pl.edu.amu.wmi.daut.re;

import java.util.List;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;

/**
 * Klasa abstrakcyjna reprezentująca operator binarny (dwuargumentowy).
 */
abstract class BinaryRegexpOperator extends RegexpOperator {

    public int arity() {
        return 2;
    }

    protected final AutomatonSpecification doCreateAutomaton(
        List<AutomatonSpecification> subautomata) {

        return createAutomatonFromTwoAutomata(
            subautomata.get(0), subautomata.get(1));
    }

    /**
     * Właściwa metoda budująca automat na podstawie podautomatu.
     */
    public abstract AutomatonSpecification createAutomatonFromTwoAutomata(
        AutomatonSpecification leftSubautomaton,
        AutomatonSpecification rightSubautomaton);

};
