/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.wmi.daut.base;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * ComplementCharClassTransitionLabel jest implementacją TransitionLabel
 * reprezentującą przejście po znaku z dopełnienia podanej klasy znaków.
 * 
 */
public class ComplementCharClassTransitionLabel extends TransitionLabel {

    /**
     *
     * @param s Przyjmuje Stringa będącego wyrażeniem regularnym
     */
    public ComplementCharClassTransitionLabel(String s) {
        int l = s.length();
        se = new TreeSet<Character>();
        for (int i = 0; i < l; i++) {
            if (s.charAt(i) == '-') {
                if (i == 0 || i == l - 1) {
                    se.add('-');
                } else {
                    for (char k = (char) (s.charAt(i - 1) + 1); k < (s.charAt(i + 1)); k++) {
                        se.add(k);
                    }
                }
            } else {
                se.add(s.charAt(i));
            }
        }
    }

    /**
     *
     * @return Wynikiem jest wartość logiczna odpowiadająca na pytanie czy może
     * być epsilon przejście
     */
    @Override
    public boolean canBeEpsilon() {
        return false;
    }

    /**
     *
     * @param c Przyjmuje znak do sprawdzenia
     * @return Wynikiem jest wartość logiczna czy znak jest akceptowany
     */
    @Override
    public boolean canAcceptCharacter(char c) {
        return !(se.contains(c));
    }

    /**
     *
     * @return Zwraca wartość logiczną czy jest puste przejście
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     *
     * @param label Przyjmuje TransitionLabel
     * @return Zwraca etykietę przejścia będącą przecięciem label i danej
     * etykiety
     */
    @Override
    protected TransitionLabel intersectWith(TransitionLabel label) {
        if (label instanceof ComplementCharClassTransitionLabel) {
            return new ComplementCharClassTransitionLabel(
                    getIntersectionString((ComplementCharClassTransitionLabel) label));
        } else {
            throw new CannotDetermineIntersectionException();
        }
    }

    /**
     *
     * @return Zwraca set przechowujący spełniające podane wyrażenie regularne
     */
    protected SortedSet<Character> getSet() {
        return se;
    }

    /**
     * 
     * @return Zwraca String będący przecięciem dwóch obiektów
     * ComplementCharclassTransitionLabel
     */
    private String getIntersectionString(ComplementCharClassTransitionLabel label) {
        SortedSet<Character> set = new TreeSet<Character>(), set1;
        set1 = ((ComplementCharClassTransitionLabel) label).getSet();
        for (Character o : se) {
            set.add(o);
        }
        for (Character o : set1) {
            set.add(o);
        }
        StringBuilder buf = new StringBuilder();
        boolean f = false;
        for (Character o : set) {
            if (o.equals('-')) {
                f = true;
                continue;
            }
            buf.append(o);
        }
        if (f) {
            buf.append('-');
        }
        String str = buf.toString();
        char p = 0, k = 0;
        if (str.length() == 0) {
            return "";
        }
        StringBuilder n = new StringBuilder();
        p = str.charAt(0);
        k = p;
        for (int i = 1; i < str.length(); i++) {
            if (k + 1 != str.charAt(i)) {
                if (p != k) {
                    n.append(p);
                    n.append('-');
                    n.append(k);

                } else {
                    n.append(p);
                }
                p = str.charAt(i);
                k = p;
            } else {
                k++;
            }
        }
        if (p != k) {
            n.append(p);
            n.append('-');
            n.append(k);

        } else {
            n.append(p);
        }

        return n.toString();
    }

    /**
     *
     * @return Zwraca wyrażenie regularne
     */
    @Override
    public String toString() {
        String q = "";
        q += "[^";
        q += getIntersectionString(new ComplementCharClassTransitionLabel(""));
        q += "]";
        return q;

    }
    private SortedSet<Character> se;
}
