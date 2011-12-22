package pl.edu.amu.wmi.daut.base;

import java.util.List;
/**
 * Pomocnicza klasa
 * do obslugi epsilonow.
 */
public class EpsilonUtilities {

    protected EpsilonUtilities() {
        throw new UnsupportedOperationException();
    }
    /**
     * Metoda sprawdzajaca, czy dany automat
     * ma jakies epsilon-przejscie.
     */
    public static boolean isEpsilonTransition(AutomatonSpecification automat) {
        List<State> states = automat.allStates();

        if (states.isEmpty())
            return false;

        for (State state : states) {
            List<OutgoingTransition> transitions = automat.allOutgoingTransitions(state);

            for (OutgoingTransition transition : transitions) {
                TransitionLabel label = transition.getTransitionLabel();

                if (label.canBeEpsilon())
                    return true;
            }
        }
        return false;

    }
}
