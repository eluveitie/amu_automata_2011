package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import java.util.List;

/**
 *
 * @author Adam
 *
 * klasa reprezentująca operator jednego znaku z wyrażeń regularnych. Na przykład "a" albo "%"
 */
public class SingleCharacterOperator extends NullaryRegexpOperator {

    private char character;

    /**
     * Konstruktor jaki jest każdy widzi. Ten akurat pobiera chara :)
     */
    public SingleCharacterOperator(char a) {
        character = a;
    }

    /**
     * Funkcja zwracajaca znak akceptowany przez automat.
     */
    public char getCharacter() {
        return character;
    }

    /**
     * Generuje automat składający się tylko z jednego znaku.
     */
    @Override
    public AutomatonSpecification createFixedAutomaton() {

        return new NaiveAutomatonSpecification().makeOneTransitionAutomaton(character);
    }

     /**
      * Fabryka operatora.
      */
    public static class Factory extends NullaryRegexpOperatorFactory {

        @Override
        public int numberOfParams() {
            return 1;
        }

        protected RegexpOperator doCreateOperator(List<String> params) {
            return new SingleCharacterOperator(params.get(0).charAt(0));
        }
    }
}
