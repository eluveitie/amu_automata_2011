package pl.edu.amu.wmi.daut.re;

import java.util.List;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;

/**
 * Klasa abstrakcyjna reprezentujący operator wyrażenia regularnego, np.
 * '*', '?', '|'.
 */
public abstract class RegexpOperator {

    /**
     * Zwraca arność operatora (oczekiwaną liczbę argumentów).
     */
    public abstract int arity();

    /**
     * Określa procedurę budowania automatu na podstawie
     * podautomatów.
     *
     * Poza sprawdzeniem zgodności liczby elementów listy
     * z arnością tylko deleguje do pomocniczej metody
     * doCreateAutomaton.
     */
    final AutomatonSpecification createAutomaton(
        List<AutomatonSpecification> subautomata) {

        if (subautomata.size() != arity())
            throw new IllegalArgumentException(
                "expected " + arity() + "-element list");

        return doCreateAutomaton(subautomata);
    }

    /**
     * Właściwe metoda odpowiedzialna za zbudowanie automatu
     * z podautomatów.
     *
     * To właśnie ta metoda będzie zdefiniowana w klasach
     * dziedziczących.
     */
    protected abstract AutomatonSpecification doCreateAutomaton(
        List<AutomatonSpecification> subautomata);

};
