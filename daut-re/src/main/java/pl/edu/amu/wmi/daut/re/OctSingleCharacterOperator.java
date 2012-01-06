package pl.edu.amu.wmi.daut.re;

import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import java.util.List;

/**
 * Klasa reprezentująca pojedynczy konkretny znak o kodzie Unicode podany w zapisie ósemkowym.
 */
public class OctSingleCharacterOperator extends NullaryRegexpOperator {

    private int value;
    static final int BASE = 8;

    /**
     * Konstruktor.
     */
    public OctSingleCharacterOperator(int a) throws Exception {
       this.setValue(a);
    }

    /**
     * Metoda, ustawia nową wartość ( sprawdza czy jest w kodzie ósemkowym).
     */
    public void setValue(int i) throws Exception {
        if (isOctal(i))
            this.value = i;
        else throw new Exception();
    }

    /**
     * Metoda zwraca aktualną wartość (w kodzie ósemkowym).
     */
    public int getValue() {
        return value;
    }

    /**
     * Metoda sprawdza, czy wartość jest w kodzie ósemkowym.
     */
    public boolean isOctal(int number) {
        return Integer.toString(number).equals(Integer.toOctalString(number));
    }

    @Override
    public AutomatonSpecification createFixedAutomaton() {

        int octValue = Integer.parseInt(Integer.toString(value), BASE);

        return new NaiveAutomatonSpecification()
                .makeOneTransitionAutomaton((char) octValue);
    }

    /**
     * Fabryka operatora.
     */
    public static class Factory extends NullaryRegexpOperatorFactory {

        @Override
        public int numberOfParams() {
            return 1;
        }

        @Override
        protected RegexpOperator doCreateOperator(List<String> params) {
            OctSingleCharacterOperator oper = null;

            try {
                oper = new OctSingleCharacterOperator(Integer.parseInt(params.get(0)));
            } catch (Exception ex) {
                Logger.getLogger(OctSingleCharacterOperator.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            return oper;
        }
    }
}
