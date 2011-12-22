package pl.edu.amu.wmi.daut.base;

/**
 * Interfejs reprezentujący etykietę przejścia (np. pojedynczy znak, zbiór znaków,
 * epsilon-przejście).
 */
abstract class TransitionLabel {

    /**
     * Zwraca true gdy przejście o danej etykiecie może nastąpić
     * bez "zjedzenia" znaku z wejścia.
     */
    public abstract boolean canBeEpsilon();

    /**
     * Zwraca true gdy przejście może nastąpić po znaku 'c'.
     */
    public abstract boolean canAcceptCharacter(char c);

    /**
     * Zwraca true gdy przejście jest puste.
     *
     * Puste przejście ma specjalny charakter (nie jest to epsilon-przejście!),
     * jest używane przez metody zwracające TransitionLabel do zaznaczenia, że
     * nie udało się wygenerować/znaleźć żądanego przejścia.
     */
    public abstract boolean isEmpty();

    /**
     * Zwraca etykietę przejścia będącą przecięciem (częścią wspólną)
     * danej etykiety i etykiety podanej jako argument label.
     */
    final TransitionLabel intersect(TransitionLabel label) {
        TransitionLabel result;

        // intersect odwołuje się do metody intersectWith - problem
        // polega na tym, że nie zawsze dla danej klasy
        // implementującej TransitionLabel i klasy podanej jako argument
        // da się łatwo wyznaczyć przecięcie...
        try {
            // ... dlatego najpierw próbujemy wywołać intersectWith
            // dla danego obiektu...
            result = intersectWith(label);
        } catch (CannotDetermineIntersectionException ex) {
            // ... a jeśli się nie uda (zostanie wyrzucony wyjątek),
            // próbujemy zamienić miejscami dany obiekt i obiekt
            // przekazany jako argument.
            result = label.intersectWith(this);
        }

        return result;
    }

    protected abstract TransitionLabel intersectWith(TransitionLabel label);

    static class CannotDetermineIntersectionException extends UnsupportedOperationException {
    }
}
