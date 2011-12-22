package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import java.util.List;

/**
 * Klasa reprezentująca operator '.' z wyrażeń regularnych (dowolny znak).
 */
public class AnyCharOperator extends NullaryRegexpOperator {
    private char character;

    /**
     * Metoda, ustawia pożądany znak.
     */
    public void setCharacter(char c) {
        this.character = c;
    }
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
            return 0;
        }

        protected RegexpOperator doCreateOperator(List<String> params) {
            return new AnyCharOperator();
        }
    }
}
