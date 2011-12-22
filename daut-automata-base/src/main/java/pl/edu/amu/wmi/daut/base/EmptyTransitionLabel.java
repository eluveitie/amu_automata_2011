package pl.edu.amu.wmi.daut.base;

/**
 * Puste przejście (nie epsilon-przejście!).
 *
 * Używane do sygnalizowania braku przejścia.
 */
class EmptyTransitionLabel extends TransitionLabel {

    public boolean canBeEpsilon() {
        return false;
    }

    public boolean canAcceptCharacter(char c) {
        return false;
    }

    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return "EMPTY";
    }

    protected TransitionLabel intersectWith(TransitionLabel label) {
        // przecięcie pustego z czymkolwiek daje oczywiście pustą etykietę
        return this;
    }
}
