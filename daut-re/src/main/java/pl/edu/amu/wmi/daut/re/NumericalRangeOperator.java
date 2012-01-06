package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.State;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import java.util.List;

class InvalidValueException extends RuntimeException { }

/**
 * Reprezentuje wszystkie napisy składające się z liczby
 * z podanego zakresu zapisane dziesiętnie (bez zer nieznaczących).
 * Np. dla parametrów 4 i 13 powinien zostać skonstruowany automat
 * akceptujący napisy: "4", "5", "6", "7", "8", "9", "10", "11", "12", "13".
 */
public class NumericalRangeOperator extends NullaryRegexpOperator {
    /**
     * Początek zakresu.
     */
    private int from;

    /**
     * Koniec zakresu.
     */
    private int to;

    /**
     * Konstruktor klasy. Ustala możliwy zakres liczb.
     */
    public NumericalRangeOperator(int from, int to) {
        if (from >= 0 && to >= 0 && from <= to) {
            this.from = from;
            this.to = to;
        } else {
            throw new InvalidValueException();
        }
    }

    @Override
    public final AutomatonSpecification createFixedAutomaton() {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();
        spec.markAsInitial(q0);

        State q;
        for (int i = this.from; i <= this.to; ++i) {
            q = spec.addTransitionSequence(q0, Integer.toString(i));
            spec.markAsFinal(q);
        }

        return spec;
    }

    /**
     * Fabryka operatora.
     */
    public static class Factory extends NullaryRegexpOperatorFactory {
        @Override
        public int numberOfParams() {
            return 2;
        }

        @Override
        protected RegexpOperator doCreateOperator(List<String> params) {
            return new NumericalRangeOperator(Integer.parseInt(params.get(0)),
                    Integer.parseInt(params.get(1)));
        }
    }
}
