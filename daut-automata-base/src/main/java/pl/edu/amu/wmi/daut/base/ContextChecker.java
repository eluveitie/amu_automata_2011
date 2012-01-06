package pl.edu.amu.wmi.daut.base;

/**
 * Interfejs wykorzystany do uwspólnienia kodu w metodach
 * wyszukujących domknięcia epsilon z kontekstem i bez.
 */
public interface ContextChecker {

    /**
     * Metoda odpowiedzialna za sprawdzanie kontekstu. Zwraca true lub false.
     */
    boolean check(TransitionLabel label);
}
