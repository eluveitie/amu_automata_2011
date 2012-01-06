package pl.edu.amu.wmi.daut.base;

/**
 * Klasa odpowiedzialna za sprawdzanie kontekstu.
 */
public class ReallyCheckingContextChecker implements ContextChecker {
    private final String s;
    private final int position;

    /**
     * Konstruktor klasy.
     */
    public ReallyCheckingContextChecker(String str, int pos) {
        s = str;
        position = pos;
    }

    /**
     * Metoda sprawdzająca kontekst.
     */
    @Override
    public boolean check(TransitionLabel label) {
        return label.checkContext(s, position);
    }



}
