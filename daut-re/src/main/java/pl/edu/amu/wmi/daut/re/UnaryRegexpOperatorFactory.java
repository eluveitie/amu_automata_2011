package pl.edu.amu.wmi.daut.re;

/**
 * Fabryka operatorów unarnych.
 */
public abstract class UnaryRegexpOperatorFactory extends RegexpOperatorFactory {

    @Override
    public int arity() {
        return 1;
    }

}
