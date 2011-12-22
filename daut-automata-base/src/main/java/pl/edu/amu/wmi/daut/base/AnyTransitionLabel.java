package pl.edu.amu.wmi.daut.base;

/**
 * @author cole1911
 */

/**
 * Implementacja Transition Label reprezentujaca
 * przejscie po dowolnym znaku.
 */
public class AnyTransitionLabel extends TransitionLabel {

    /**
     * Konstruktor domyslny.
     */
    public AnyTransitionLabel() { };

    @Override
    public boolean canBeEpsilon() {
        return false;
    }

    @Override
    public boolean canAcceptCharacter(char c) {
        return true;
    }

    @Override
    public boolean isEmpty() {
         return false;
    }

    @Override
    public String toString() {
         return "ANY";
    }

    @Override
    protected TransitionLabel intersectWith(TransitionLabel label) {
        boolean isResultEmpty = (label.isEmpty() || label.canBeEpsilon());
        return isResultEmpty ? new EmptyTransitionLabel() : label;
    }
}
