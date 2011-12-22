package pl.edu.amu.wmi.daut.re;

import java.util.List;

/**
 * Klasa reprezentujaca.
 */
public class RegexpOperatorTree {

    private static class ArityException extends Exception {

        public ArityException() {
        }
    }
    private RegexpOperator root;
    private List<RegexpOperatorTree> subtrees;

    /**
     * Konstruuje drzewo z korzeniem operator i poddrzewami subtrees.
     *
     * Jeśli liczba poddrzew nie zgadza się z arnością operatora, powinien być wyrzucany wyjątek.
     */
    RegexpOperatorTree(RegexpOperator operator, List<RegexpOperatorTree> subtrees)
            throws ArityException {

        if (operator.arity() == subtrees.size()) {
            this.root = operator;
            this.subtrees = subtrees;
        } else {
            throw new ArityException();
        }
    }

    /**
     * Zwraca korzeń.
     */
    RegexpOperator getRoot() {
        return root;
    }

    /**
     * Zwraca listę poddrzew.
     */
    List<RegexpOperatorTree> getSubtrees() {
        return subtrees;
    }
}
