package pl.edu.amu.wmi.daut.base;

import java.util.HashSet;
import java.util.Arrays;
import junit.framework.TestCase;

/**
 * Test klasy AutomatonStack.
 */
public class TestAutomatonByStack extends TestCase {
/**
 * Pierwszy test.
 */
    public final void test1() {
       final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0a = spec.addState();
        State q1a = spec.addState();
        State q2a = spec.addState();
        State q3a = spec.addState();
        State q4a = spec.addState();
        State q5a = spec.addState();

        spec.addTransition(q0a, q1a, new CharTransitionLabel('a'));
        spec.addLoop(q0a, new CharTransitionLabel('a'));
        spec.addLoop(q0a, new CharTransitionLabel('b'));
        spec.addLoop(q0a, new CharTransitionLabel('c'));
        spec.addTransition(q1a, q3a, new CharTransitionLabel('b'));
        spec.addLoop(q1a, new CharTransitionLabel('a'));
        spec.addLoop(q1a, new CharTransitionLabel('b'));
        spec.addLoop(q1a, new CharTransitionLabel('c'));
        spec.addTransition(q0a, q2a, new CharTransitionLabel('c'));
        spec.addTransition(q2a, q4a, new CharTransitionLabel('a'));
        spec.addTransition(q2a, q4a, new CharTransitionLabel('b'));
        spec.addTransition(q2a, q4a, new CharTransitionLabel('c'));
        spec.addTransition(q4a, q5a, new CharTransitionLabel('a'));
        spec.addLoop(q4a, new CharTransitionLabel('a'));
        spec.addLoop(q4a, new CharTransitionLabel('b'));
        spec.addLoop(q4a, new CharTransitionLabel('c'));

        spec.markAsInitial(q0a);
        spec.markAsFinal(q3a);
        spec.markAsFinal(q5a);

        final AutomatonByStack automaton = new AutomatonByStack(spec);

        assertTrue(automaton.accepts("aaaaaaaaaabbbbbbb"));
        assertTrue(automaton.accepts("ccccccabbbbb"));
        assertTrue(automaton.accepts("ccccccccccccccccccccccccccccaba"));
        assertTrue(automaton.accepts("bbbbbbbcabbbbbbba"));
        assertFalse(automaton.accepts("bbbbbbbbaaaaac"));
        assertFalse(automaton.accepts("cccccccccabbbbbbc"));
        assertFalse(automaton.accepts("aaaaaaacbaaaaabc"));
        assertFalse(automaton.accepts("bbbbbbccbbbbbbc"));
   }
    /**
     *Drugi test.
     */
     public final void test2() {
        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0a = spec.addState();
        State q1a = spec.addState();
        State q2a = spec.addState();
        State q3a = spec.addState();
        State q4a = spec.addState();

        spec.addTransition(q0a, q1a, new CharTransitionLabel('a'));
        spec.addTransition(q0a, q1a, new CharTransitionLabel('b'));
        spec.addTransition(q1a, q3a, new CharTransitionLabel('a'));
        spec.addTransition(q1a, q3a, new CharTransitionLabel('c'));
        spec.addTransition(q0a, q2a, new CharTransitionLabel('c'));
        spec.addTransition(q1a, q2a, new CharTransitionLabel('b'));
        spec.addTransition(q3a, q2a, new CharTransitionLabel('b'));
        spec.addTransition(q2a, q4a, new CharTransitionLabel('a'));
        spec.addTransition(q2a, q4a, new CharTransitionLabel('b'));
        spec.addLoop(q0a, new CharTransitionLabel('a'));
        spec.addLoop(q1a, new CharTransitionLabel('c'));
        spec.addLoop(q1a, new CharTransitionLabel('b'));
        spec.addLoop(q3a, new CharTransitionLabel('a'));
        spec.addLoop(q3a, new CharTransitionLabel('b'));
        spec.addLoop(q2a, new CharTransitionLabel('a'));
        spec.addLoop(q2a, new CharTransitionLabel('b'));
        spec.addLoop(q2a, new CharTransitionLabel('c'));

        spec.markAsInitial(q0a);
        spec.markAsFinal(q4a);

        final AutomatonByStack automaton = new AutomatonByStack(spec);

        assertTrue(automaton.accepts("aaaabcccbcbcbababababbcccca"));
        assertTrue(automaton.accepts("aaaaabccccbbbcbcbcbccbabababcbca"));
        assertTrue(automaton.accepts("aaaaaaaaacabcabcabcabcabccccccb"));
        assertTrue(automaton.accepts("aaaaaaaaaaaaaabbbbbbbbbbbbbb"));
        assertFalse(automaton.accepts("bbacbaccbbaabccabc"));
        assertFalse(automaton.accepts("aaabcbcbcbbccbabcabccc"));
        assertFalse(automaton.accepts("aaacacbcbaabc"));
        assertFalse(automaton.accepts("cccccccccccccccccccccccc"));
    }
   /**
    *Test trzeci.
    */
    public final void test3() {
        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0a = spec.addState();
        State q1a = spec.addState();
        State q2a = spec.addState();
        State q3a = spec.addState();
        State q4a = spec.addState();
        State q5a = spec.addState();
        State q6a = spec.addState();
        State q7a = spec.addState();
        State q8a = spec.addState();

        spec.addTransition(q0a, q1a, new CharTransitionLabel('a'));
        spec.addTransition(q0a, q1a, new CharTransitionLabel('e'));
        spec.addTransition(q0a, q2a, new CharTransitionLabel('b'));
        spec.addTransition(q0a, q2a, new CharTransitionLabel('c'));
        spec.addTransition(q0a, q3a, new CharTransitionLabel('c'));
        spec.addTransition(q0a, q3a, new CharTransitionLabel('d'));
        spec.addTransition(q1a, q4a, new CharTransitionLabel('e'));
        spec.addTransition(q3a, q4a, new CharTransitionLabel('c'));
        spec.addTransition(q4a, q5a, new CharTransitionLabel('e'));
        spec.addTransition(q4a, q6a, new CharTransitionLabel('d'));
        spec.addTransition(q4a, q7a, new CharTransitionLabel('c'));
        spec.addTransition(q4a, q7a, new CharTransitionLabel('a'));
        spec.addTransition(q6a, q7a, new CharTransitionLabel('e'));
        spec.addTransition(q7a, q6a, new CharTransitionLabel('b'));
        spec.addTransition(q7a, q8a, new CharTransitionLabel('a'));
        spec.addTransition(q7a, q8a, new CharTransitionLabel('d'));
        spec.addLoop(q0a, new CharTransitionLabel('c'));
        spec.addLoop(q0a, new CharTransitionLabel('d'));
        spec.addLoop(q0a, new CharTransitionLabel('e'));
        spec.addLoop(q1a, new CharTransitionLabel('b'));
        spec.addLoop(q3a, new CharTransitionLabel('d'));
        spec.addLoop(q3a, new CharTransitionLabel('a'));
        spec.addLoop(q4a, new CharTransitionLabel('a'));
        spec.addLoop(q4a, new CharTransitionLabel('b'));
        spec.addLoop(q4a, new CharTransitionLabel('e'));
        spec.addLoop(q6a, new CharTransitionLabel('a'));
        spec.addLoop(q6a, new CharTransitionLabel('e'));
        spec.addLoop(q6a, new CharTransitionLabel('c'));
        spec.addLoop(q7a, new CharTransitionLabel('b'));
        spec.addLoop(q7a, new CharTransitionLabel('a'));

        spec.markAsInitial(q0a);
        spec.markAsFinal(q2a);
        spec.markAsFinal(q5a);
        spec.markAsFinal(q8a);

        final AutomatonByStack automaton = new AutomatonByStack(spec);

        assertTrue(automaton.accepts("cdedceeddceabbbbbbbeabeebaebdaeeaecceceebababababbbbaad"));
        assertTrue(automaton.accepts("cdeeddceeeddcedecededececededcedecdecededceb"));
        assertTrue(automaton.accepts("edcecdedcedcedcedcedcedcecdddaaaadddaaadadadadacabbeaaaee"));
        assertTrue(automaton.accepts("edececdecececedceabbbbeaaaabbbeeaeaeacabbbababd"));
        assertFalse(automaton.accepts("cdecdecedececabbbbbeeaaac"));
        assertFalse(automaton.accepts("aaaabdcedbcadecbadecabdbbbdddaecbaecdbeacdbecaecb"));
        assertFalse(automaton.accepts("cdecdecedecccdadaadceebaababaeb"));
        assertFalse(automaton.accepts("dcddcececdabbbeababababdaeeeaccedbc"));
    }

  /**
    * Test czwarty.
    */
    public final void test4() {

        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0a = spec.addState();
        State q1a = spec.addState();
        State q2a = spec.addState();
        State q3a = spec.addState();
        State q4a = spec.addState();
        State q5a = spec.addState();
        State q6a = spec.addState();
        State q7a = spec.addState();

        spec.addTransition(q0a, q1a, new CharTransitionLabel('c'));
        spec.addTransition(q0a, q2a, new CharTransitionLabel('a'));
        spec.addTransition(q0a, q2a, new CharTransitionLabel('b'));
        spec.addTransition(q2a, q3a, new CharTransitionLabel('c'));
        spec.addTransition(q2a, q4a, new CharTransitionLabel('a'));
        spec.addTransition(q4a, q5a, new CharTransitionLabel('b'));
        spec.addTransition(q4a, q6a, new CharTransitionLabel('a'));
        spec.addTransition(q6a, q7a, new CharTransitionLabel('a'));
        spec.addLoop(q0a, new CharTransitionLabel('a'));
        spec.addLoop(q0a, new CharTransitionLabel('b'));
        spec.addLoop(q0a, new CharTransitionLabel('c'));
        spec.addLoop(q2a, new CharTransitionLabel('a'));
        spec.addLoop(q2a, new CharTransitionLabel('b'));
        spec.addLoop(q2a, new CharTransitionLabel('c'));
        spec.addLoop(q4a, new CharTransitionLabel('a'));
        spec.addLoop(q6a, new CharTransitionLabel('c'));

        spec.markAsInitial(q0a);
        spec.markAsFinal(q1a);
        spec.markAsFinal(q3a);
        spec.markAsFinal(q5a);
        spec.markAsFinal(q7a);

        final AutomatonByStack automaton = new AutomatonByStack(spec);

        assertTrue(automaton.accepts("ababababababababababbc"));
        assertTrue(automaton.accepts("abccbacabcbabacabbaacacbc"));
        assertTrue(automaton.accepts("aaaaaaaaaaaaaaaaaaca"));
        assertTrue(automaton.accepts("bbbbbababababababababaaca"));
        assertFalse(automaton.accepts("abcabcabcaabcccbacaacb"));
        assertFalse(automaton.accepts("abcaaabbbcccaacb"));
        assertFalse(automaton.accepts("aaabccbcaabccbaaaaaaaacb"));
        assertFalse(automaton.accepts("abababababababababababababababacb"));
    }
   /**
    *Test piaty.
    */
    public final void test5() {

        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0a = spec.addState();
        State q1a = spec.addState();
        State q2a = spec.addState();
        State q3a = spec.addState();
        State q4a = spec.addState();
        State q5a = spec.addState();
        State q6a = spec.addState();
        State q7a = spec.addState();
        State q8a = spec.addState();
        State q9a = spec.addState();
        State q10a = spec.addState();
        State q11a = spec.addState();
        State q12a = spec.addState();
        State q13a = spec.addState();
        State q14a = spec.addState();
        State q15a = spec.addState();

        spec.addTransition(q0a, q1a, new CharTransitionLabel('b'));
        spec.addTransition(q0a, q1a, new CharTransitionLabel('d'));
        spec.addLoop(q0a, new CharTransitionLabel('a'));
        spec.addLoop(q0a, new CharTransitionLabel('c'));
        spec.addTransition(q1a, q2a, new CharTransitionLabel('e'));
        spec.addTransition(q1a, q3a, new CharTransitionLabel('d'));
        spec.addLoop(q1a, new CharTransitionLabel('a'));
        spec.addLoop(q1a, new CharTransitionLabel('b'));
        spec.addLoop(q1a, new CharTransitionLabel('c'));
        spec.addTransition(q2a, q5a, new CharTransitionLabel('a'));
        spec.addTransition(q2a, q5a, new CharTransitionLabel('b'));
        spec.addLoop(q2a, new CharTransitionLabel('c'));
        spec.addTransition(q3a, q4a, new CharTransitionLabel('b'));
        spec.addTransition(q3a, q7a, new CharTransitionLabel('a'));
        spec.addLoop(q3a, new CharTransitionLabel('g'));
        spec.addLoop(q3a, new CharTransitionLabel('f'));
        spec.addTransition(q4a, q2a, new CharTransitionLabel('g'));
        spec.addLoop(q4a, new CharTransitionLabel('b'));
        spec.addLoop(q4a, new CharTransitionLabel('a'));
        spec.addTransition(q5a, q6a, new CharTransitionLabel('c'));
        spec.addTransition(q5a, q12a, new CharTransitionLabel('g'));
        spec.addLoop(q5a, new CharTransitionLabel('g'));
        spec.addLoop(q5a, new CharTransitionLabel('f'));
        spec.addTransition(q6a, q8a, new CharTransitionLabel('b'));
        spec.addTransition(q6a, q12a, new CharTransitionLabel('b'));
        spec.addTransition(q6a, q13a, new CharTransitionLabel('a'));
        spec.addLoop(q6a, new CharTransitionLabel('d'));
        spec.addLoop(q6a, new CharTransitionLabel('a'));
        spec.addTransition(q7a, q10a, new CharTransitionLabel('b'));
        spec.addTransition(q7a, q8a, new CharTransitionLabel('d'));
        spec.addLoop(q7a, new CharTransitionLabel('b'));
        spec.addTransition(q8a, q9a, new CharTransitionLabel('d'));
        spec.addLoop(q8a, new CharTransitionLabel('a'));
        spec.addLoop(q8a, new CharTransitionLabel('f'));
        spec.addTransition(q9a, q11a, new CharTransitionLabel('a'));
        spec.addTransition(q9a, q13a, new CharTransitionLabel('b'));
        spec.addLoop(q9a, new CharTransitionLabel('a'));
        spec.addLoop(q9a, new CharTransitionLabel('b'));
        spec.addTransition(q12a, q14a, new CharTransitionLabel('g'));
        spec.addLoop(q12a, new CharTransitionLabel('c'));
        spec.addLoop(q12a, new CharTransitionLabel('d'));
        spec.addTransition(q13a, q15a, new CharTransitionLabel('f'));
        spec.addLoop(q13a, new CharTransitionLabel('f'));
        spec.addLoop(q13a, new CharTransitionLabel('d'));
        spec.markAsInitial(q0a);
        spec.markAsFinal(q10a);
        spec.markAsFinal(q11a);
        spec.markAsFinal(q15a);
        spec.markAsFinal(q14a);
        final AutomatonByStack automaton = new AutomatonByStack(spec);
        assertTrue(automaton.accepts("ccaacababccbadgffgfgabbbbb"));
        assertTrue(automaton.accepts("aaaabaaaaecccafggfcdadadadaddddff"));
        assertFalse(automaton.accepts("accacaccdccccbfggfcdaaaddabaffafaadaabbababa"));
        assertFalse(automaton.accepts("ccccccdccccbggggccccccg"));
        assertFalse(automaton.accepts("cacacacbbbbdggggabbbbc"));
        assertFalse(automaton.accepts("cacacacbabccbadfggfabbbbdaaffadaaaabababc"));
        assertFalse(automaton.accepts("cacacadccccabbagfgcddaadadfffa"));
        assertFalse(automaton.accepts("ccacacdddcccccccfadgdcabbcccfggfbaca"));
    }
   /**
    * Szosty test.
    */
    public final void test6() {

        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0a = spec.addState();
        State q1a = spec.addState();
        State q2a = spec.addState();
        State q3a = spec.addState();
        spec.addTransition(q0a, q1a, new CharTransitionLabel('a'));
        spec.addTransition(q1a, q3a, new CharTransitionLabel('b'));
        spec.addTransition(q2a, q1a, new CharTransitionLabel('c'));
        spec.addTransition(q0a, q2a, new CharTransitionLabel('b'));
        spec.addTransition(q2a, q3a, new CharTransitionLabel('a'));
        spec.addLoop(q0a, new CharTransitionLabel('a'));
        spec.addLoop(q0a, new CharTransitionLabel('b'));
        spec.addLoop(q0a, new CharTransitionLabel('c'));
        spec.addLoop(q1a, new CharTransitionLabel('a'));
        spec.addLoop(q1a, new CharTransitionLabel('c'));
        spec.addLoop(q2a, new CharTransitionLabel('b'));
        spec.addLoop(q3a, new CharTransitionLabel('a'));
        spec.addLoop(q3a, new CharTransitionLabel('b'));
        spec.markAsInitial(q0a);
        spec.markAsFinal(q3a);
        final AutomatonByStack automaton = new AutomatonByStack(spec);
        assertTrue(automaton.accepts("ab"));
        assertTrue(automaton.accepts("ba"));
        assertTrue(automaton.accepts("bcb"));
        assertTrue(automaton.accepts("aabcbab"));
        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("aabcbabababc"));
        assertFalse(automaton.accepts("bcaca"));
        assertFalse(automaton.accepts("acca"));
    }

/**
 * Siodmy test #362.
 */
  public final void test7() {
    final AutomatonSpecification spec = new NaiveAutomatonSpecification();

    HashSet<Character> separator =
      new HashSet<Character>(Arrays.asList('.', ':'));
    HashSet<Character> digit0to3 =
      new HashSet<Character>(Arrays.asList('0', '1', '2', '3'));
    HashSet<Character> digit0to5 =
      new HashSet<Character>(Arrays.asList('0', '1', '2', '3', '4', '5'));
    HashSet<Character> digit0to9 =
      new HashSet<Character>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    HashSet<Character> digit3to9 =
      new HashSet<Character>(Arrays.asList('3', '4', '5', '6', '7', '8', '9'));



    State q0 = spec.addState();
    State q1 = spec.addState();
    State q2 = spec.addState();
    State q3 = spec.addState();
    State q4 = spec.addState();
    State q5 = spec.addState();
    State q6 = spec.addState();
    State q7 = spec.addState();
    State q8 = spec.addState();
    State q9 = spec.addState();

    spec.addTransition(q0, q1, new CharTransitionLabel('0'));
    spec.addTransition(q0, q3, new CharTransitionLabel('1'));
    spec.addTransition(q0, q5, new CharTransitionLabel('2'));
    spec.addTransition(q1, q2, new CharSetTransitionLabel(separator));
    spec.addTransition(q3, q2, new CharSetTransitionLabel(separator));
    spec.addTransition(q4, q2, new CharSetTransitionLabel(separator));
    spec.addTransition(q5, q2, new CharSetTransitionLabel(separator));
    spec.addTransition(q6, q2, new CharSetTransitionLabel(separator));
    spec.addTransition(q9, q2, new CharSetTransitionLabel(separator));
    spec.addTransition(q5, q6, new CharSetTransitionLabel(digit0to3));
    spec.addTransition(q2, q7, new CharSetTransitionLabel(digit0to5));
    spec.addTransition(q3, q4, new CharSetTransitionLabel(digit0to9));
    spec.addTransition(q7, q8, new CharSetTransitionLabel(digit0to9));
    spec.addTransition(q0, q9, new CharSetTransitionLabel(digit3to9));
    spec.markAsInitial(q0);
    spec.markAsFinal(q8);

    final AutomatonByStack automaton = new AutomatonByStack(spec);
    assertTrue(automaton.accepts("0:00"));
    assertTrue(automaton.accepts("5:01"));
    assertTrue(automaton.accepts("2:01"));
    assertTrue(automaton.accepts("1.11"));
    assertTrue(automaton.accepts("12:59"));
    assertTrue(automaton.accepts("23:08"));
    assertTrue(automaton.accepts("0.59"));
    assertFalse(automaton.accepts("03:22"));
    assertFalse(automaton.accepts("24:13"));
    assertFalse(automaton.accepts("1:69"));
    assertFalse(automaton.accepts("23:591"));
    assertFalse(automaton.accepts(""));
    assertFalse(automaton.accepts("1"));
    assertFalse(automaton.accepts("1:1"));
    assertFalse(automaton.accepts("00:00"));
  }
}
