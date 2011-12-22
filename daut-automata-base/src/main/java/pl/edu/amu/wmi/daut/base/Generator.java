package pl.edu.amu.wmi.daut.base;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * klasa zawierająca metodę zwracającą listę wszystkich słów z automatu bez cykli.
 */
public class Generator {

    private List<String> acceptedWords = new ArrayList<String>();
    private TransitionLabel currentLabel;

    /**
     * Metoda zwracająca listę wszystkich słów akceptowanych przez automat bez cykli.
     */
    void acceptWords(AutomatonSpecification automaton, String alphabet, String word, State state) {
        StringBuffer buf = new StringBuffer(word);
        List<OutgoingTransition> allOutTransitions;
        allOutTransitions = automaton.allOutgoingTransitions(state);
            if (!allOutTransitions.isEmpty()) {
                String word2 = "";
                for (OutgoingTransition transition : allOutTransitions) {
                    currentLabel = transition.getTransitionLabel();
                    if (currentLabel instanceof EmptyTransitionLabel
                        || currentLabel instanceof EpsilonTransitionLabel) {
                         if (!automaton.isFinal(state)) {
                                 acceptedWords.remove(word);
                            }
                        buf.append("");
                        word2 = buf.toString();
                        acceptedWords.add(word2);
                        acceptWords(automaton, alphabet, word, transition.getTargetState());
                        break;
                 }
                    for (int i = 0; i < alphabet.length(); i++) {
                        if (currentLabel.canAcceptCharacter(alphabet.charAt(i))) {
                            if (!automaton.isFinal(state)) {
                                 acceptedWords.remove(word);
                            }
                        buf.append(alphabet.charAt(i));
                        word2 = buf.toString();
                        acceptedWords.add(word2);
                        acceptWords(automaton, alphabet, word2, transition.getTargetState());

                        break;
                        }
                    }
            buf.deleteCharAt(word.length());
            word2 = buf.toString();
                }
            } else {
                    if (!automaton.isFinal(state))
                        acceptedWords.remove(word);
            }
    }
    /**
     * Metoda zwracająca listę słów akceptowanych przez automat bez cykli.
     *
     */
    public List wordsFromAutomatonWithoutCycles(AutomatonSpecification automaton, String alphabet) {
        String word = "";
        acceptWords(automaton, alphabet, word, automaton.getInitialState());
        return acceptedWords;
    }

    /**
     * Metoda zwracająca losowy napis akceptowany przez automat.
     */
    String randomWord(AutomatonSpecification automaton, String alphabet, State state) {
        String word = "";
        Random rand = new Random();
        List<OutgoingTransition> allOutTransitions = automaton.allOutgoingTransitions(state);
        while (!(automaton.isFinal(state) && allOutTransitions.isEmpty())) {
            int r = rand.nextInt(allOutTransitions.size());
            currentLabel = allOutTransitions.get(r).getTransitionLabel();
            for (int i = 0; i < alphabet.length(); i++) {
                char addChar = alphabet.charAt(i);
                if (currentLabel.canAcceptCharacter(addChar)) {
                    state = allOutTransitions.get(r).getTargetState();
                    allOutTransitions = automaton.allOutgoingTransitions(state);
                    word = new StringBuffer(word).insert(word.length(), addChar).toString();
                    break;
                }
            }
            boolean finish = rand.nextBoolean();
            if (automaton.isFinal(state) && !allOutTransitions.isEmpty() && finish) {
                break;
            }
        }
        return word;
    }
}

