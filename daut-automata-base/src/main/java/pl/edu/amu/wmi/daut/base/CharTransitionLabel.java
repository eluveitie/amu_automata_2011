package pl.edu.amu.wmi.daut.base;
/**
 * Implementacja TransitionLabel reprezentujaca przejscie po podanym znaku .
 */
public class CharTransitionLabel extends TransitionLabel {
    /**
    * Konstruuje etykietę oznaczoną znakiem 'c'.
    */
    public CharTransitionLabel(char c) {
        ch = c;
    }

    @Override
    public boolean canBeEpsilon() {
        return false;
    }

    @Override
    public boolean canAcceptCharacter(char c) {
        return c == ch;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
    * Zwraca znak po którym następuje przejście.
    */
    public char getChar() {
        return ch;
    }

    @Override
    public String toString() {
        return Character.toString(ch);
    }

    @Override
    protected TransitionLabel intersectWith(TransitionLabel label) {
        return label.canAcceptCharacter(ch) ? this : new EmptyTransitionLabel();
    }

    private char ch;
}
