package pl.edu.amu.wmi.daut.re;

/**
 * Fabryka operatorów binarnych.
 */
public abstract class BinaryRegexpOperatorFactory extends RegexpOperatorFactory {

    @Override
    public int arity() {
        return 2;
    }

}
