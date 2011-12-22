
package pl.edu.amu.wmi.daut.base;
import java.util.Set;

/**
* Różne pomocnicze funkcje
* związane z automatami deterministycznymi.
*/
public class DeterministicUtilities {

     /**
     * Tworzy automat deterministyczny, który akceptuje napisy ze zbioru `language`
     * i nie akceptuje żadnych innych napisów. Automat powstaje przez
     * rozbudowanie pustego automatu deterministycznego przekazanego jako argument `automaton`.
     */
    public void createAutomatonForFiniteLanguage(
        DeterministicAutomatonSpecification automaton, Set<String> language) {

        State q0 = automaton.addState();
        automaton.markAsInitial(q0);

        for (String s : language) {
            if (s.equals("")) {
                automaton.markAsFinal(q0);

            } else {
                State activeState = q0;
                int letter = 0;

                for ( ; letter < s.length(); letter++) {

                    if (automaton.targetState(activeState, s.charAt(letter)) != null) {
                        activeState = automaton.targetState(activeState, s.charAt(letter));

                    } else {
                        State newState = automaton.addState();
                        automaton.addTransition(activeState, newState,
                            new CharTransitionLabel(s.charAt(letter)));
                        activeState = newState;
                    }

                }
                automaton.markAsFinal(activeState);
            }
        }
    }

}
