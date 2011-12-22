package pl.edu.amu.wmi.daut.base;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Klasa służy obsłudze prostych klas wyrażeń regularnych,
 * pozwala na sprawdzenie czy dany znak je spełnia.
 */
public class CharClassTransitionLabel extends TransitionLabel {

    private SortedSet<Character> ssc = new TreeSet<Character>();
    private String charClass;

    /**
     * poniższy konstruktor przekształca dany łańcuch na zbiór charów.
     * @param s jest zbiorem klas wyrażeń regularnych do których należy dany char
     */
    public CharClassTransitionLabel(String s) {
        charClass = s;
        if (s.charAt(0) == '-' || s.charAt(s.length() - 1) == '-') {
            addChar(s.charAt(0));
        }
        for (int i = 0; i < s.length(); ++i) {
            if (i + 2 < s.length() && s.charAt(i + 1) == '-') {
                addChar(s.charAt(i), s.charAt(i + 2));
                i += 2;
            } else {
                addChar(s.charAt(i));
            }
        }
    }

    private void addChar(Character c1, Character c2) {
        for (Character d = c1; d <= c2; ++d) {
            ssc.add(d);
        }
    }
    private void addChar(Character c) {
        ssc.add(c);
    }

    @Override
    public boolean canBeEpsilon() {
        return false;
    }

    @Override
    public boolean canAcceptCharacter(final char c) {
        return ssc.contains((Character) c);

    }

    @Override
    public boolean isEmpty() {
        return charClass.isEmpty();
    }

    @Override
    public String toString() {
        return "[" + charClass + "]";
    }

    @Override
    protected TransitionLabel intersectWith(TransitionLabel label) {
        SortedSet<Character> tmp = new TreeSet<Character>();
        StringBuilder sb = new StringBuilder();
        for (Character c : ssc) {
            if (label.canAcceptCharacter(c)) {
                tmp.add(c);
            }
        }
        for (Character c : tmp) {
            sb.append(c);
        }
        return new CharClassTransitionLabel(sb.toString());

    }


}
