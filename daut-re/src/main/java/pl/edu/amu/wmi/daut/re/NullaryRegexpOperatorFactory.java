package pl.edu.amu.wmi.daut.re;

/**
 * Fabryka operatorów zeroargumentowych.
 */
public abstract class NullaryRegexpOperatorFactory extends RegexpOperatorFactory {

    @Override
    public int arity() {
        return 0;
    }

}
