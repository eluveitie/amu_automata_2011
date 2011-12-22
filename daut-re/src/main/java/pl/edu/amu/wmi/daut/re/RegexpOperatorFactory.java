package pl.edu.amu.wmi.daut.re;

import java.util.List;

/**
 * Klasa abstrakcyjna fabryki operatorów, czyli czegoś, co tworzy
 * operatory.
 */
public abstract class RegexpOperatorFactory {

    /**
     * Główna metoda tworząca operator. Argumentem jest lista
     * parametrów
     */
    public RegexpOperator createOperator(List<String> params) {
        if (params.size() != numberOfParams())
            throw new IllegalArgumentException("unexpected number of parameters");

        return doCreateOperator(params);
    }

    /**
     * Zwraca arność tworzonego operatora (jego liczbę argumentów).
     */
    public abstract int arity();

    /**
     * Zwraca liczbę parametrów operatora.
     *
     * Należy odróżnić argumenty i parametry: argumenty są (pod)wyrażeniami
     * regularnymi, natomiast parametry to dodatkowe wartości
     * modyfikujące działanie operatora. Np. operator (abc){4,10}
     * ma jeden argument "(abc)" i dwa parametry (4 i 10).
     */
    public abstract int numberOfParams();

    /**
     * Właściwa metoda tworząca operator, nie musi
     * już sprawdzać, czy zgadza się liczba parametrów
     * (robi to createOperator).
     */
    protected abstract RegexpOperator doCreateOperator(List<String> params);

};
