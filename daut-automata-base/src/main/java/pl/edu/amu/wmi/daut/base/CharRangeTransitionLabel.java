package pl.edu.amu.wmi.daut.base;

/**
 * Klasa reprezentująca przejście po dowolnym znaku z podanego zakresu UTF-8.
 */
public class CharRangeTransitionLabel extends TransitionLabel {

    private char firstChar;
    private char secondChar;

    /**
     * Konstruktor klasy.
     */
    public CharRangeTransitionLabel(char a, char z) {
        firstChar = a;
        secondChar = z;
    }

    @Override
    public boolean canBeEpsilon() {
        return false;
    }

    @Override
    public boolean canAcceptCharacter(char c) {
        return (c >= firstChar & c <= secondChar);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Zwraca pierwszy znak.
     */
    public char getFirstChar() {
        return firstChar;
    }

    /**
     * Zwraca drugi znak.
     */
    public char getSecondChar() {
        return secondChar;
    }

    @Override
    protected TransitionLabel intersectWith(TransitionLabel label) {

        if (label instanceof CharRangeTransitionLabel) {

            char a1 = this.getFirstChar();
            char a2 = this.getSecondChar();

            char b1 = ((CharRangeTransitionLabel) label).getFirstChar();
            char b2 = ((CharRangeTransitionLabel) label).getSecondChar();

            if ((a1 <= b1 && a2 <= b2 && b1 < a2)) {
                return new CharRangeTransitionLabel(b1, a2);

            } else if (a1 >= b1 && a2 >= b2 && a1 < b2) {
                return new CharRangeTransitionLabel(a1, b2);

            } else if (a1 < b1 && a2 > b2) {
                return new CharRangeTransitionLabel(b1, b2);

            } else if (a1 > b1 && a2 < b2) {
                return new CharRangeTransitionLabel(a1, a2);

            } else if (a1 == b2) {
                return new CharTransitionLabel(a1);

            } else if (b1 == a2) {
                return new CharTransitionLabel(b1);

            } else {
                return new EmptyTransitionLabel();
            }

        } else {
            throw new CannotDetermineIntersectionException();
        }
    }

    /**
    *
    * @return Zwraca zakres aktualnego przejścia/przecięcia.
    */
    @Override
    public String toString() {
        if (this.getFirstChar() != 0 && this.getSecondChar() != 0) {
            String s = this.getFirstChar() + "-" + this.getSecondChar();
            return s;
        } else if (this.getFirstChar() == 0 && this.getSecondChar() == 0) {
            String s = "";
            return s;
        } else if (this.getFirstChar() == 0 && this.getSecondChar() != 0) {
            String s = Character.toString(this.getSecondChar());
            return s;
        } else {
            String s = Character.toString(this.getFirstChar());
            return s;
        }
    }
};
