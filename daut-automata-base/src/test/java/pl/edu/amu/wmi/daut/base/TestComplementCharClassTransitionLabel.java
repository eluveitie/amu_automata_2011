package pl.edu.amu.wmi.daut.base;

import junit.framework.TestCase;
/**
 *
 * @author s362606
 * Klasa testująca ComplementCharClassTransitionLabel.
 */
public class TestComplementCharClassTransitionLabel extends TestCase {

    /**
     * Testujemy metody canAcceptCharacter, isEmpty,toString i canBeEpsilon.
     */
    public final void testcanAcceptCharacter() {

        //budujemy
        TransitionLabel test1 = new ComplementCharClassTransitionLabel("");

        //testujemy
        assertTrue(test1.canAcceptCharacter('j'));
        assertTrue(test1.canAcceptCharacter('8'));
        assertTrue(test1.canAcceptCharacter('W'));
        assertTrue(test1.canAcceptCharacter('*'));
        assertEquals(test1.toString(), "[^]");
        assertFalse(test1.canBeEpsilon());
        assertFalse(test1.isEmpty());

        //budujemy
        TransitionLabel test2 = new ComplementCharClassTransitionLabel("a");

        //testujemy
        assertTrue(test2.canAcceptCharacter('p'));
        assertTrue(test2.canAcceptCharacter('U'));
        assertTrue(test2.canAcceptCharacter('0'));
        assertTrue(test2.canAcceptCharacter('/'));
        assertFalse(test2.canAcceptCharacter('a'));
        assertEquals(test2.toString(), "[^a]");
        assertFalse(test2.canBeEpsilon());
        assertFalse(test2.isEmpty());

        //budujemy
        TransitionLabel test3 =
                        new ComplementCharClassTransitionLabel("a-jBDJS0-6A-G+*$k#/8P-Um-wd-");

        //testujemy
        assertTrue(test3.canAcceptCharacter('y'));
        assertTrue(test3.canAcceptCharacter('O'));
        assertTrue(test3.canAcceptCharacter('7'));
        assertTrue(test3.canAcceptCharacter('='));
        assertTrue(test3.canAcceptCharacter('W'));
        assertFalse(test3.canAcceptCharacter('a'));
        assertFalse(test3.canAcceptCharacter('g'));
        assertFalse(test3.canAcceptCharacter('j'));
        assertFalse(test3.canAcceptCharacter('5'));
        assertFalse(test3.canAcceptCharacter('-'));
        assertFalse(test3.canAcceptCharacter('0'));
        assertFalse(test3.canAcceptCharacter('8'));
        assertFalse(test3.canAcceptCharacter('n'));
        assertFalse(test3.canAcceptCharacter('#'));
        assertEquals(test3.toString(), "[^#-$*-+/-68A-GJP-Ua-km-w-]");
        assertFalse(test3.canBeEpsilon());
        assertFalse(test3.isEmpty());

        //budujemy
        TransitionLabel test4 = new ComplementCharClassTransitionLabel("-bbcbbabaaaccaaacccaaab");

        //testujemy
        assertTrue(test4.canAcceptCharacter('l'));
        assertTrue(test4.canAcceptCharacter('R'));
        assertTrue(test4.canAcceptCharacter('3'));
        assertTrue(test4.canAcceptCharacter('+'));
        assertFalse(test4.canAcceptCharacter('-'));
        assertFalse(test4.canAcceptCharacter('a'));
        assertFalse(test4.canAcceptCharacter('b'));
        assertFalse(test4.canAcceptCharacter('c'));
        assertEquals(test4.toString(), "[^a-c-]");
        assertFalse(test4.canBeEpsilon());
        assertFalse(test4.isEmpty());

    }

    /**
     * Testujemy intersectWith.
     */
    public final void testintersect() {

        //budujemy
        TransitionLabel test1 = new ComplementCharClassTransitionLabel("");
        TransitionLabel test2 = new ComplementCharClassTransitionLabel("a-c");

        //testujemy
        TransitionLabel inter = test1.intersect(test2);
        assertTrue(inter.canAcceptCharacter('j'));
        assertTrue(inter.canAcceptCharacter('-'));
        assertFalse(inter.canAcceptCharacter('a'));
        assertFalse(inter.canAcceptCharacter('b'));
        assertFalse(inter.canAcceptCharacter('c'));
        assertFalse(inter.canBeEpsilon());
        assertFalse(inter.isEmpty());
        assertEquals(inter.toString(), test2.toString());
        assertFalse(inter.canBeEpsilon());

        //budujemy
        TransitionLabel test3 = new ComplementCharClassTransitionLabel("a-jBDJSA-G+*$k#/8P-Um-wd-");
        TransitionLabel test4 = new ComplementCharClassTransitionLabel("0-6");

        //testujemy
        inter = test3.intersect(test4);
        assertTrue(inter.canAcceptCharacter('y'));
        assertTrue(inter.canAcceptCharacter('O'));
        assertTrue(inter.canAcceptCharacter('7'));
        assertTrue(inter.canAcceptCharacter('='));
        assertTrue(inter.canAcceptCharacter('W'));
        assertFalse(inter.canAcceptCharacter('a'));
        assertFalse(inter.canAcceptCharacter('g'));
        assertFalse(inter.canAcceptCharacter('j'));
        assertFalse(inter.canAcceptCharacter('5'));
        assertFalse(inter.canAcceptCharacter('-'));
        assertFalse(inter.canAcceptCharacter('0'));
        assertFalse(inter.canAcceptCharacter('8'));
        assertFalse(inter.canAcceptCharacter('n'));
        assertFalse(inter.canAcceptCharacter('#'));
        assertEquals(inter.toString(), "[^#-$*-+/-68A-GJP-Ua-km-w-]");
        assertFalse(inter.canBeEpsilon());
        assertFalse(inter.isEmpty());

        //budujemy
        TransitionLabel test5 = new ComplementCharClassTransitionLabel("a-j0-79");
        TransitionLabel charTest = new CharTransitionLabel('9');
        TransitionLabel empty = new  EmptyTransitionLabel();
        TransitionLabel epsilon = new EpsilonTransitionLabel();

        //testujemy
        inter = test5.intersect(new CharTransitionLabel('v'));
        assertEquals(inter.toString(), "v");
        assertFalse(inter.canBeEpsilon());
        assertFalse(inter.isEmpty());
        assertTrue(inter.canAcceptCharacter('v'));
        assertFalse(inter.canAcceptCharacter('7'));

        inter = test5.intersect(charTest);
        assertFalse(inter.canBeEpsilon());
        assertTrue(inter.isEmpty());

        inter = test5.intersect(empty);
        assertFalse(inter.canBeEpsilon());
        assertTrue(inter.isEmpty());

        inter = test5.intersect(epsilon);
        assertFalse(inter.canBeEpsilon());
        assertTrue(inter.isEmpty());

        //budujemy
        TransitionLabel any = new AnyTransitionLabel();

        //testujemy
        inter = test5.intersect(any);
        assertEquals("[^0-79a-j]", inter.toString());
        assertFalse(inter.canBeEpsilon());
        assertFalse(inter.isEmpty());
        assertFalse(inter.canAcceptCharacter('0'));
        assertTrue(inter.canAcceptCharacter('+'));
        assertFalse(inter.canAcceptCharacter('b'));
        assertTrue(inter.canAcceptCharacter('Y'));
    }
}

