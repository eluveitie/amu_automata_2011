package pl.edu.amu.wmi.daut.base;

import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;

/**
 * Testy różnych operacji na automatach.
 */
public class TestAutomataOperations extends TestCase {

    /**
     * Test prostego automatu.
     */
    public final void testSimpleAutomaton() {

        AutomatonSpecification automatonA = new NaiveAutomatonSpecification();

        State q0 = automatonA.addState();
        State q1 = automatonA.addState();
        automatonA.addTransition(q0, q1, new CharTransitionLabel('a'));
        automatonA.addLoop(q1, new CharTransitionLabel('a'));
        automatonA.addLoop(q1, new CharTransitionLabel('b'));
        automatonA.markAsInitial(q0);
        automatonA.markAsFinal(q1);

        AutomatonSpecification automatonB = new NaiveAutomatonSpecification();
        State q10 = automatonB.addState();
        State q11 = automatonB.addState();
        State q12 = automatonB.addState();
        automatonB.addTransition(q10, q11, new CharTransitionLabel('a'));
        automatonB.addTransition(q10, q11, new CharTransitionLabel('b'));
        automatonB.addTransition(q11, q12, new CharTransitionLabel('a'));
        automatonB.addTransition(q11, q12, new CharTransitionLabel('b'));
        automatonB.markAsInitial(q10);
        automatonB.markAsFinal(q12);


        // proszę odkomentować, kiedy AutomataOperations.intersection
        // będzie gotowe!!!
        // AutomatonSpecification Result = AutomataOperations.intersection(automatonA, automatonB);
        // AutomatonByRecursion automaton = AutomatonByRecursion(Result);

        // assertTrue(automaton.accepts("aa"));
        // assertTrue(automaton.accepts("ab"));
        // assertFalse(automaton.accepts(""));
        // assertFalse(automaton.accepts("a"));

    }

    /**
     * Test sprawdza metode Sum w AutomataOperations A.
     */
    public final void testSumA() {

        AutomatonSpecification automatonA = new NaiveAutomatonSpecification();

        State q0 = automatonA.addState();
        State q1 = automatonA.addState();
        automatonA.addTransition(q0, q1, new CharTransitionLabel('a'));
        automatonA.addLoop(q1, new CharTransitionLabel('a'));
        automatonA.addLoop(q1, new CharTransitionLabel('b'));
        automatonA.markAsInitial(q0);
        automatonA.markAsFinal(q1);

        AutomatonSpecification automatonB = new NaiveAutomatonSpecification();

        State q0B = automatonB.addState();
        State q1B = automatonB.addState();
        State q2B = automatonB.addState();
        automatonB.addTransition(q0B, q1B, new CharTransitionLabel('a'));
        automatonB.addTransition(q0B, q1B, new CharTransitionLabel('b'));
        automatonB.addTransition(q1B, q2B, new CharTransitionLabel('a'));
        automatonB.addTransition(q1B, q2B, new CharTransitionLabel('b'));
        automatonB.markAsInitial(q0B);
        automatonB.markAsFinal(q2B);

        AutomatonSpecification result = AutomataOperations.sum(automatonA, automatonB);

        NondeterministicAutomatonByThompsonApproach automaton = new
        NondeterministicAutomatonByThompsonApproach(result);

        assertTrue(automaton.accepts("aa"));
        assertTrue(automaton.accepts("ba"));
        assertTrue(automaton.accepts("aaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaa"));
        assertTrue(automaton.accepts("bb"));
        assertTrue(automaton.accepts("abbbbabbbabbb"));
        assertFalse(automaton.accepts("bbb"));
        assertFalse(automaton.accepts("tegomaniezakceptowac"));
        assertFalse(automaton.accepts("baaaaaaaaaa"));
        assertFalse(automaton.accepts("aaaaaaaaaaaaaaaxaaaaaa"));
        assertFalse(automaton.accepts("bab"));
    }

    /**
     * Test sprawdza metode Sum w AutomataOperations B.
     */
    public final void testSumB() {

        AutomatonSpecification automatonB = new NaiveAutomatonSpecification();

        State q0B = automatonB.addState();
        State q1B = automatonB.addState();
        State q2B = automatonB.addState();
        automatonB.addTransition(q0B, q1B, new CharTransitionLabel('a'));
        automatonB.addTransition(q0B, q1B, new CharTransitionLabel('b'));
        automatonB.addTransition(q1B, q2B, new CharTransitionLabel('a'));
        automatonB.addTransition(q1B, q2B, new CharTransitionLabel('b'));
        automatonB.markAsInitial(q0B);
        automatonB.markAsFinal(q2B);

        AutomatonSpecification automatonD = new NaiveAutomatonSpecification();

        State q0D = automatonD.addState();
        State q1D = automatonD.addState();
        State q2D = automatonD.addState();
        State q3D = automatonD.addState();
        automatonD.addTransition(q0D, q1D, new CharTransitionLabel('a'));
        automatonD.addTransition(q0D, q2D, new CharTransitionLabel('b'));
        automatonD.addTransition(q1D, q3D, new CharTransitionLabel('a'));
        automatonD.addTransition(q1D, q2D, new CharTransitionLabel('b'));
        automatonD.addTransition(q2D, q0D, new CharTransitionLabel('c'));
        automatonD.addTransition(q2D, q1D, new CharTransitionLabel('b'));
        automatonD.addTransition(q2D, q3D, new CharTransitionLabel('a'));
        automatonD.addTransition(q3D, q2D, new CharTransitionLabel('c'));
        automatonD.addTransition(q3D, q0D, new CharTransitionLabel('b'));
        automatonD.markAsInitial(q0D);
        automatonD.markAsFinal(q3D);

        AutomatonSpecification result = AutomataOperations.sum(automatonB, automatonD);

        NondeterministicAutomatonByThompsonApproach automaton = new
        NondeterministicAutomatonByThompsonApproach(result);

        assertTrue(automaton.accepts("ab"));
        assertTrue(automaton.accepts("abbabba"));
        assertTrue(automaton.accepts("bbbcaacba"));
        assertTrue(automaton.accepts("aacacaca"));
        assertTrue(automaton.accepts("aa"));
        assertFalse(automaton.accepts("zle"));
        assertFalse(automaton.accepts("b"));
        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("aac"));
    }

    /**
     * Test sprawdza metode Sum w AutomataOperations C.
     */
    public final void testSumC() {

        AutomatonSpecification automatonB = new NaiveAutomatonSpecification();

        State q0B = automatonB.addState();
        State q1B = automatonB.addState();
        State q2B = automatonB.addState();
        automatonB.addTransition(q0B, q1B, new CharTransitionLabel('a'));
        automatonB.addTransition(q0B, q1B, new CharTransitionLabel('b'));
        automatonB.addTransition(q1B, q2B, new CharTransitionLabel('a'));
        automatonB.addTransition(q1B, q2B, new CharTransitionLabel('b'));
        automatonB.markAsInitial(q0B);
        automatonB.markAsFinal(q2B);

        AutomatonSpecification automatonC = new NaiveAutomatonSpecification();

        State q0C = automatonC.addState();
        automatonC.addLoop(q0C, new CharTransitionLabel('a'));
        automatonC.addLoop(q0C, new CharTransitionLabel('b'));
        automatonC.addLoop(q0C, new CharTransitionLabel('c'));
        automatonC.addLoop(q0C, new CharTransitionLabel('d'));
        automatonC.markAsInitial(q0C);
        automatonC.markAsFinal(q0C);

        AutomatonSpecification result = AutomataOperations.sum(automatonB, automatonC);

        NondeterministicAutomatonByThompsonApproach automaton = new
        NondeterministicAutomatonByThompsonApproach(result);

        assertTrue(automaton.accepts("babbaccddcaaccb"));
        assertTrue(automaton.accepts("bbaccddbaba"));
        assertTrue(automaton.accepts("bbbcaacba"));
        assertTrue(automaton.accepts("aaaaaaaaaaaaaaaa"));
        assertTrue(automaton.accepts(""));
        assertFalse(automaton.accepts("bbaccddxbaba"));
        assertFalse(automaton.accepts("czytwojprogrammackutozaakceptuje"));
        assertFalse(automaton.accepts("zielonosmutnaniebieskowesolapomaranczowa"));
    }

    /**
     * Test sprawdza metode Sum w AutomataOperations D.
     */
    public final void testSumD() {

        AutomatonSpecification automatonB = new NaiveAutomatonSpecification();

        State q0B = automatonB.addState();
        State q1B = automatonB.addState();
        State q2B = automatonB.addState();
        automatonB.addTransition(q0B, q1B, new CharTransitionLabel('a'));
        automatonB.addTransition(q0B, q1B, new CharTransitionLabel('b'));
        automatonB.addTransition(q1B, q2B, new CharTransitionLabel('a'));
        automatonB.addTransition(q1B, q2B, new CharTransitionLabel('b'));
        automatonB.markAsInitial(q0B);
        automatonB.markAsFinal(q2B);

        AutomatonSpecification automatonE = new NaiveAutomatonSpecification();

        State q0E = automatonE.addState();
        automatonE.addTransition(q0E, q0E, new EpsilonTransitionLabel());
        automatonE.markAsInitial(q0E);
        automatonE.markAsFinal(q0E);

        AutomatonSpecification result = AutomataOperations.sum(automatonB, automatonE);

        NondeterministicAutomatonByThompsonApproach automaton = new
        NondeterministicAutomatonByThompsonApproach(result);

        assertTrue(automaton.accepts(""));
        assertTrue(automaton.accepts("aa"));
        assertFalse(automaton.accepts("bbaccddxbaba"));
        assertFalse(automaton.accepts("aabbbaaaa"));
    }

    /**
     * Test sprawdza metode Sum w AutomataOperations E.
     */
    public final void testSumE() {

        AutomatonSpecification automatonB = new NaiveAutomatonSpecification();

        State q0B = automatonB.addState();
        State q1B = automatonB.addState();
        State q2B = automatonB.addState();
        automatonB.addTransition(q0B, q1B, new CharTransitionLabel('a'));
        automatonB.addTransition(q0B, q1B, new CharTransitionLabel('b'));
        automatonB.addTransition(q1B, q2B, new CharTransitionLabel('a'));
        automatonB.addTransition(q1B, q2B, new CharTransitionLabel('b'));
        automatonB.markAsInitial(q0B);
        automatonB.markAsFinal(q2B);

        AutomatonSpecification automatonF = new NaiveAutomatonSpecification();

        State q0F = automatonF.addState();
        State q1F = automatonF.addState();
        State q2F = automatonF.addState();
        State q3F = automatonF.addState();
        State q7F = automatonF.addState();
        State q5F = automatonF.addState();
        State q6F = automatonF.addState();
        automatonF.addTransition(q0F, q1F, new CharTransitionLabel('a'));
        automatonF.addTransition(q0F, q3F, new EpsilonTransitionLabel());
        automatonF.addTransition(q0F, q2F, new EpsilonTransitionLabel());
        automatonF.addTransition(q3F, q7F, new CharTransitionLabel('a'));
        automatonF.addTransition(q2F, q5F, new EpsilonTransitionLabel());
        automatonF.addTransition(q5F, q6F, new CharTransitionLabel('b'));
        automatonF.markAsInitial(q0F);
        automatonF.markAsFinal(q1F);
        automatonF.markAsFinal(q7F);
        automatonF.markAsFinal(q6F);

        AutomatonSpecification result = AutomataOperations.sum(automatonB, automatonF);

        NondeterministicAutomatonByThompsonApproach automaton = new
        NondeterministicAutomatonByThompsonApproach(result);

        assertTrue(automaton.accepts("aa"));
        assertTrue(automaton.accepts("b"));
        assertTrue(automaton.accepts("a"));
        assertFalse(automaton.accepts("aaabbbb"));
        assertFalse(automaton.accepts(""));
    }

     /**
     * Test sprawdza, czy odwracanie automatu działa.
     */
    public final void testInversionA() {
        System.out.println("testInversionA start");
        List<String> words = new ArrayList<String>();
        words.add("ab");
        words.add("ba");
        words.add("caa");
        words.add("bbba");
        words.add("bbb");
        words.add("bab");
        words.add("abb");
        words.add("aaa");
        words.add("a");
        words.add("b");
        words.add("");

        NaiveAutomatonSpecification automatonA = new NaiveAutomatonSpecification();
        State q0 = automatonA.addState();
        State q1 = automatonA.addState();
        automatonA.addTransition(q0, q1, new CharTransitionLabel('a'));
        automatonA.addLoop(q1, new CharTransitionLabel('a'));
        automatonA.addLoop(q1, new CharTransitionLabel('b'));
        automatonA.markAsInitial(q0);
        automatonA.markAsFinal(q1);

        AutomatonSpecification automatonB = AutomataOperations.reverseLanguageAutomat(automatonA);
        System.out.println("Automat wyglada tak: ");
        System.out.println(automatonB.getDotGraph());

        NondeterministicAutomatonByThompsonApproach originalAutomaton = new
                NondeterministicAutomatonByThompsonApproach(automatonA);

        NondeterministicAutomatonByThompsonApproach reversedAutomaton = new
                NondeterministicAutomatonByThompsonApproach(automatonB);

        List<String> wordsToAccept = new ArrayList<String>();
        List<String> wordsToReject = new ArrayList<String>();

        for (String word : words) {
            String reversedWord = new StringBuffer(word).reverse().toString();
            if (originalAutomaton.accepts(word))
                wordsToAccept.add(reversedWord);
            else
                wordsToReject.add(reversedWord);
        }

        for (String word : wordsToAccept) {
            System.out.println("Testuje " + word + " (powinno przejsc)");
            assertTrue(reversedAutomaton.accepts(word));
        }
        for (String word : wordsToReject) {
            System.out.println("Testuje " + word + " (nie powinno przejsc)");
            assertFalse(reversedAutomaton.accepts(word));
        }
        System.out.println("testInversionA koniec");
    }

    /**
     * Test sprawdza, czy odwracanie automatu działa (B).
     */
    public final void testInversionB() {
        System.out.println("testInversionB start");
        List<String> words = new ArrayList<String>();
        words.add("cb");
        words.add("bc");
        words.add("bab");
        words.add("bac");
        words.add("cba");
        words.add("cbb");
        words.add("aaa");
        words.add("aab");
        words.add("aac");
        words.add("aba");
        words.add("abb");
        words.add("abc");
        words.add("aca");
        words.add("acb");
        words.add("acc");
        words.add("aa");
        words.add("ab");
        words.add("ac");
        words.add("ba");
        words.add("bb");
        words.add("bc");
        words.add("ca");
        words.add("cb");
        words.add("cc");
        words.add("a");
        words.add("b");
        words.add("c");
        words.add("");
        words.add("cb");
        words.add("cab");
        words.add("caab");
        words.add("bc");
        words.add("bac");
        words.add("baac");
        words.add("aac");
        words.add("aab");
        words.add("caa");
        words.add("baa");

        NaiveAutomatonSpecification automatonA = new NaiveAutomatonSpecification();
        State q0 = automatonA.addState();
        State q1 = automatonA.addState();
        State q2 = automatonA.addState();
        State q3 = automatonA.addState();
        automatonA.addTransition(q0, q1, new CharTransitionLabel('c'));
        automatonA.addTransition(q1, q2, new CharTransitionLabel('a'));
        automatonA.addLoop(q2, new CharTransitionLabel('a'));
        automatonA.addTransition(q2, q3, new CharTransitionLabel('b'));
        automatonA.markAsInitial(q0);
        automatonA.markAsFinal(q3);

        AutomatonSpecification automatonB = AutomataOperations.reverseLanguageAutomat(automatonA);
        System.out.println("Automat wyglada tak: ");
        System.out.println(automatonB.getDotGraph());

        NondeterministicAutomatonByThompsonApproach originalAutomaton = new
                NondeterministicAutomatonByThompsonApproach(automatonA);

        NondeterministicAutomatonByThompsonApproach reversedAutomaton = new
                NondeterministicAutomatonByThompsonApproach(automatonB);

        List<String> wordsToAccept = new ArrayList<String>();
        List<String> wordsToReject = new ArrayList<String>();

        for (String word : words) {
            String reversedWord = new StringBuffer(word).reverse().toString();
            if (originalAutomaton.accepts(word))
                wordsToAccept.add(reversedWord);
            else
                wordsToReject.add(reversedWord);
        }

        for (String word : wordsToAccept) {
            System.out.println("Testuje " + word + " (powinno przejsc)");
            assertTrue(reversedAutomaton.accepts(word));
        }
        for (String word : wordsToReject) {
            System.out.println("Testuje " + word + " (nie powinno przejsc)");
            assertFalse(reversedAutomaton.accepts(word));
        }
        System.out.println("testInversionB koniec");
    }
}
