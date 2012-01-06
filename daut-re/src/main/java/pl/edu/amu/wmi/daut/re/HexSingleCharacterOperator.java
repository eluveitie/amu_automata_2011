package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import java.util.List;

/**
 * Wyjatki.
 */
class InvalidHexSingleCharacterOperatorException extends RuntimeException {
    public InvalidHexSingleCharacterOperatorException(String message) {
        super(message);
    }
}

/**
 * Klasa reprezentujaca pojedynczy konkretny znak o kodzie Unicode podanym w zapisie szesnastkowym.
 */
public class HexSingleCharacterOperator extends NullaryRegexpOperator {

    private char character;

    /**
     * Jako parametr przyjmuje char, jaki ma zaakceptowac.
     */
    public HexSingleCharacterOperator(char a) {
        character = a;
    }

    /**
     * Funkcja zwracajaca znak akceptowany przez automat.
     */
    public char getCharacter() {
        return character;
    }

    @Override
    public AutomatonSpecification createFixedAutomaton() {
        return new NaiveAutomatonSpecification().makeOneTransitionAutomaton(character);
    }

    /**
     * Fabryka.
     */
    public static class Factory extends NullaryRegexpOperatorFactory {

        static final int BASE16 = 16;
        static final int MAXNUMBERLENGTH = 8;

        @Override
        public int numberOfParams() {
            return 1;
        }

        /**
         * Jako parametr przyjmuje ciag zapisany w systemie szesnastkowym (A0AB).
         */
        protected RegexpOperator doCreateOperator(List<String> params) {
            String s = params.get(0);
            int length = s.length();
            if (length <= 0)
                throw new
                InvalidHexSingleCharacterOperatorException("Argument was too short.");
            if (length > MAXNUMBERLENGTH)
                throw new
                InvalidHexSingleCharacterOperatorException("Value was too long.");
            for (char c : s.toCharArray())
                if ((c < '0' || c > '9') && (c < 'a' || c > 'f') && (c < 'A' || c > 'F'))
                    throw new
                    InvalidHexSingleCharacterOperatorException("Wrong hexadecimal value.");
            if (length == MAXNUMBERLENGTH && (s.charAt(0) < '0' || s.charAt(0) > '7'))
                throw new
                InvalidHexSingleCharacterOperatorException("Value was too high.");
            int i = Integer.parseInt(s, BASE16);
            char c = (char) i;
            return new HexSingleCharacterOperator(c);
        }
    }
}
