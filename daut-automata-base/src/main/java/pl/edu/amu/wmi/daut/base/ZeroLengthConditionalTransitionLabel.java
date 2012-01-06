package pl.edu.amu.wmi.daut.base;

/**
 * Klasa, z której powinny dziedziczyć wszystkie przejścia
 * z kontekstowym warunkiem.
 */
abstract class ZeroLengthConditionalTransitionLabel extends TransitionLabel {

    @Override
    public boolean canBeEpsilon() {
        return true;
    }

    @Override
    protected boolean doIsContextual() {
        return true;
    }

    protected abstract boolean doCheckContext(String s, int position);

    protected TransitionLabel intersectWith(TransitionLabel label) {
        if (label.canBeEpsilon()) {
            return this;
        } else {
            return new EmptyTransitionLabel();
        }
    };

}

