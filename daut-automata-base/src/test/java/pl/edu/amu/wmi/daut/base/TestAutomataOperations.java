package pl.edu.amu.wmi.daut.base;

import java.util.HashSet;
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

<<<<<<< HEAD
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
=======
    /**
     * Test funkcji determinize2(). Automat akceptuje słowa postaci:
     * ((b)^2n)(a^m)(*), gdzie m,n >= 0, a (*) oznacza dowolny ciąg znaków.
     */
    public final void testDeterminize2SimpleAutomaton() {
        AutomatonSpecification nonDeterministicAutomat = new NaiveAutomatonSpecification();
        DeterministicAutomatonSpecification deterministicAutomat
                = new NaiveDeterministicAutomatonSpecification();

        State qPoczatkowy = nonDeterministicAutomat.addState();
        State qKoncowy = nonDeterministicAutomat.addState();
        nonDeterministicAutomat.addLoop(qPoczatkowy, new CharTransitionLabel('a'));
        nonDeterministicAutomat.addTransition(qPoczatkowy, qKoncowy, new CharTransitionLabel('a'));
        nonDeterministicAutomat.addTransition(qPoczatkowy, qKoncowy, new CharTransitionLabel('b'));
        nonDeterministicAutomat.addTransition(qKoncowy, qPoczatkowy, new CharTransitionLabel('b'));
        nonDeterministicAutomat.markAsFinal(qPoczatkowy);
        nonDeterministicAutomat.markAsInitial(qPoczatkowy);

        try {
            AutomataOperations.determinize2(nonDeterministicAutomat, deterministicAutomat);
        } catch (Exception e) {
            fail();
        }

        AutomatonByRecursion zdeterminizowany = new AutomatonByRecursion(deterministicAutomat);

        assertTrue(zdeterminizowany.accepts(""));
        assertTrue(zdeterminizowany.accepts("aaaaa"));
        assertTrue(zdeterminizowany.accepts("abbababbaa"));
        assertTrue(zdeterminizowany.accepts("bbaba"));
        assertFalse(zdeterminizowany.accepts("bababb"));
    }

    /**
     * Test funkcji determinize2(). Prosty automat niedeterministyczny z Wikipedii.
     */
    public final void testDeterminize2AutomatonFromWikipedia() {
        AutomatonSpecification nonDeterministicAutomat = new NaiveAutomatonSpecification();
        DeterministicAutomatonSpecification innaNazwa
                = new NaiveDeterministicAutomatonSpecification();

        State qA = nonDeterministicAutomat.addState();
        State qB = nonDeterministicAutomat.addTransition(qA, new CharTransitionLabel('1'));
        State qC = nonDeterministicAutomat.addTransition(qA, new CharTransitionLabel('1'));
        nonDeterministicAutomat.addTransition(qC, qB, new CharTransitionLabel('0'));
        nonDeterministicAutomat.addTransition(qB, qA, new CharTransitionLabel('0'));
        nonDeterministicAutomat.markAsInitial(qA);
        nonDeterministicAutomat.markAsFinal(qC);

        try {
            AutomataOperations.determinize2(nonDeterministicAutomat, innaNazwa);
        } catch (Exception e) {
            fail();
        }

        AutomatonByRecursion zdeterminizowany = new AutomatonByRecursion(innaNazwa);

        assertFalse(zdeterminizowany.accepts(""));
        assertTrue(zdeterminizowany.accepts("1"));
        assertTrue(zdeterminizowany.accepts("101"));
        assertTrue(zdeterminizowany.accepts("10101001"));
        assertFalse(zdeterminizowany.accepts("1000"));
        assertFalse(zdeterminizowany.accepts("0"));
    }

    /**
     * Test funkcji determinize2(). Automat !już! deterministyczny akceptuje tylko słowa:
     * Marcin, Maria, Marianna, Mariusz, Marek, Marzena.
     */
    public final void testDeterminize2DeterministicSixNames() {
        AutomatonSpecification nonDeterministicAutomat = new NaiveAutomatonSpecification();
        DeterministicAutomatonSpecification deterministicAutomat
                = new NaiveDeterministicAutomatonSpecification();

        State qPoczatkowy = nonDeterministicAutomat.addState();
        State qMar = nonDeterministicAutomat.addTransitionSequence(qPoczatkowy, "Mar");
        State qMarc = nonDeterministicAutomat.addTransition(qMar, new CharTransitionLabel('c'));
        State qMarcin = nonDeterministicAutomat.addTransitionSequence(qMarc, "in");
        State qMari = nonDeterministicAutomat.addTransition(qMar, new CharTransitionLabel('i'));
        State qMaria = nonDeterministicAutomat.addTransition(qMari, new CharTransitionLabel('a'));
        State qMarianna = nonDeterministicAutomat.addTransitionSequence(qMaria, "nna");
        State qMariusz = nonDeterministicAutomat.addTransitionSequence(qMari, "usz");
        State qMarek = nonDeterministicAutomat.addTransitionSequence(qMar, "ek");
        State qMarzena = nonDeterministicAutomat.addTransitionSequence(qMar, "zena");
        nonDeterministicAutomat.markAsInitial(qPoczatkowy);
        nonDeterministicAutomat.markAsFinal(qMarcin);
        nonDeterministicAutomat.markAsFinal(qMaria);
        nonDeterministicAutomat.markAsFinal(qMarianna);
        nonDeterministicAutomat.markAsFinal(qMariusz);
        nonDeterministicAutomat.markAsFinal(qMarek);
        nonDeterministicAutomat.markAsFinal(qMarzena);

        try {
            AutomataOperations.determinize2(nonDeterministicAutomat, deterministicAutomat);
        } catch (Exception e) {
            fail();
        }

        AutomatonByRecursion zdeterminizowany = new AutomatonByRecursion(deterministicAutomat);

        assertFalse(zdeterminizowany.accepts(""));
        assertTrue(zdeterminizowany.accepts("Marcin"));
        assertTrue(zdeterminizowany.accepts("Maria"));
        assertTrue(zdeterminizowany.accepts("Marianna"));
        assertTrue(zdeterminizowany.accepts("Mariusz"));
        assertTrue(zdeterminizowany.accepts("Marek"));
        assertTrue(zdeterminizowany.accepts("Marzena"));
        assertFalse(zdeterminizowany.accepts("mariusz"));
    }

    /**
     * Test funkcji determinize2(). Automaty są niepoprawne dla tej metody, powinna ona
     * zwracać wyjątki.
     */
    public final void testDeterminize2WrongAutomatons() {
        AutomatonSpecification nonDeterministicAutomat = new NaiveAutomatonSpecification();
        DeterministicAutomatonSpecification deterministicAutomat
                = new NaiveDeterministicAutomatonSpecification();

        try {
            AutomataOperations.determinize2(nonDeterministicAutomat, deterministicAutomat);
        } catch (Exception e) {
            assertTrue(true);
        }

        State first = nonDeterministicAutomat.addState();
        nonDeterministicAutomat.addTransition(first, new EpsilonTransitionLabel());

        try {
            AutomataOperations.determinize2(nonDeterministicAutomat, deterministicAutomat);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Test funkcji determinize2(). Automat zawiera ComplementCharClassTransitionLabel.
     */
    public final void testDeterminize2AutomatonWithComplementCharClass() {
        AutomatonSpecification nonDeterministicAutomat = new NaiveAutomatonSpecification();
        DeterministicAutomatonSpecification deterministicAutomat
                = new NaiveDeterministicAutomatonSpecification();

        State qBegin = nonDeterministicAutomat.addState();
        State qFirstEnd = nonDeterministicAutomat
                .addTransition(qBegin, new ComplementCharClassTransitionLabel("a-gt"));
        State qSecondEnd = nonDeterministicAutomat
                .addTransition(qBegin, new CharTransitionLabel('z'));
        State qThirdArm = nonDeterministicAutomat
                .addTransition(qBegin, new ComplementCharClassTransitionLabel("f-t"));
        State qThirdEnd = nonDeterministicAutomat
                .addTransition(qThirdArm, new CharTransitionLabel('z'));
        nonDeterministicAutomat.markAsInitial(qBegin);
        nonDeterministicAutomat.markAsFinal(qFirstEnd);
        nonDeterministicAutomat.markAsFinal(qSecondEnd);
        nonDeterministicAutomat.markAsFinal(qThirdEnd);

        try {
            AutomataOperations.determinize2(nonDeterministicAutomat, deterministicAutomat);
        } catch (Exception e) {
            fail();
        }

        AutomatonByRecursion zdeterminizowany = new AutomatonByRecursion(deterministicAutomat);

        assertFalse(zdeterminizowany.accepts(""));
        assertTrue(zdeterminizowany.accepts("z"));
        assertTrue(zdeterminizowany.accepts("ez"));
        assertTrue(zdeterminizowany.accepts("uz"));
        assertTrue(zdeterminizowany.accepts("h"));
        assertTrue(zdeterminizowany.accepts("k"));
        assertFalse(zdeterminizowany.accepts("f"));
    }

    /**
     * Test funkcji determinize2(). Automat zawiera CharSetTransitionLabel.
     */
    public final void testDeterminize2AutomatonWithCharSet() {
        AutomatonSpecification nonDeterministicAutomat = new NaiveAutomatonSpecification();
        DeterministicAutomatonSpecification deterministicAutomat
                = new NaiveDeterministicAutomatonSpecification();

        State qBegin = nonDeterministicAutomat.addState();
        State qFirstArm = nonDeterministicAutomat
                .addTransition(qBegin, new CharTransitionLabel('a'));
        State qFirstEnd = nonDeterministicAutomat
                .addTransition(qFirstArm, new AnyTransitionLabel());
        HashSet<Character> mySet = new HashSet<Character>();
        mySet.add('b');
        mySet.add('d');
        mySet.add('z');
        State qSecondEnd = nonDeterministicAutomat
                .addTransition(qFirstArm, new CharSetTransitionLabel(mySet));
        HashSet<Character> mySet2 = new HashSet<Character>();
        mySet2.add('b');
        mySet2.add('d');
        mySet2.add('z');
        mySet2.add('a');
        mySet2.add('f');
        mySet2.add('h');
        nonDeterministicAutomat.addTransition(qBegin, qSecondEnd,
                new CharSetTransitionLabel(mySet2));
        HashSet<Character> mySet3 = new HashSet<Character>();
        mySet3.add('a');
        mySet3.add('f');
        mySet3.add('h');
        nonDeterministicAutomat.addTransition(qSecondEnd, qFirstEnd,
                new CharSetTransitionLabel(mySet3));
        nonDeterministicAutomat.markAsInitial(qBegin);
        nonDeterministicAutomat.markAsFinal(qFirstEnd);
        nonDeterministicAutomat.markAsFinal(qSecondEnd);

        try {
            AutomataOperations.determinize2(nonDeterministicAutomat, deterministicAutomat);
        } catch (Exception e) {
            fail();
        }

        AutomatonByRecursion zdeterminizowany = new AutomatonByRecursion(deterministicAutomat);

        assertFalse(zdeterminizowany.accepts(""));
        assertTrue(zdeterminizowany.accepts("a"));
        assertTrue(zdeterminizowany.accepts("b"));
        assertTrue(zdeterminizowany.accepts("h"));
        assertTrue(zdeterminizowany.accepts("z"));
        assertTrue(zdeterminizowany.accepts("az"));
        assertTrue(zdeterminizowany.accepts("ak"));
        assertTrue(zdeterminizowany.accepts("bf"));
        assertTrue(zdeterminizowany.accepts("zh"));
        assertFalse(zdeterminizowany.accepts("g"));
        assertFalse(zdeterminizowany.accepts("A"));
        assertFalse(zdeterminizowany.accepts("fz"));
        assertFalse(zdeterminizowany.accepts("c"));
    }

    /**
     * Test funkcji determinize2(). Automat zawiera CharRangeTransitionLabel.
     */
    public final void testDeterminize2AutomatonWithCharRange() {
        AutomatonSpecification nonDeterministicAutomat = new NaiveAutomatonSpecification();
        DeterministicAutomatonSpecification deterministicAutomat
                = new NaiveDeterministicAutomatonSpecification();

        State qBegin = nonDeterministicAutomat.addState();
        State qFirstEnd = nonDeterministicAutomat
                .addTransition(qBegin, new CharRangeTransitionLabel('a', 'f'));
        State qSecondArm = nonDeterministicAutomat
                .addTransition(qBegin, new CharTransitionLabel('a'));
        State qSecondEnd = nonDeterministicAutomat
                .addTransition(qSecondArm, new CharRangeTransitionLabel('g', 'j'));
        nonDeterministicAutomat.addLoop(qFirstEnd, new CharTransitionLabel('a'));
        nonDeterministicAutomat.markAsInitial(qBegin);
        nonDeterministicAutomat.markAsFinal(qFirstEnd);
        nonDeterministicAutomat.markAsFinal(qSecondEnd);

        try {
            AutomataOperations.determinize2(nonDeterministicAutomat, deterministicAutomat);
        } catch (Exception e) {
            fail();
        }

        AutomatonByRecursion zdeterminizowany = new AutomatonByRecursion(deterministicAutomat);

        assertFalse(zdeterminizowany.accepts(""));
        assertTrue(zdeterminizowany.accepts("a"));
        assertTrue(zdeterminizowany.accepts("b"));
        assertTrue(zdeterminizowany.accepts("c"));
        assertTrue(zdeterminizowany.accepts("d"));
        assertTrue(zdeterminizowany.accepts("e"));
        assertTrue(zdeterminizowany.accepts("f"));
        assertTrue(zdeterminizowany.accepts("aa"));
        assertTrue(zdeterminizowany.accepts("fa"));
        assertTrue(zdeterminizowany.accepts("ba"));
        assertTrue(zdeterminizowany.accepts("ag"));
        assertTrue(zdeterminizowany.accepts("aj"));
        assertFalse(zdeterminizowany.accepts("g"));
        assertFalse(zdeterminizowany.accepts("bb"));
        assertFalse(zdeterminizowany.accepts("fat"));
    }

    /**
     * Test funkcji determinize2(). Automat zawiera CharRangeTransitionLabel.
     */
    public final void testDeterminize2BigAutomaton() {
        AutomatonSpecification nonDeterministicAutomat = new NaiveAutomatonSpecification();
        DeterministicAutomatonSpecification deterministicAutomat
                = new NaiveDeterministicAutomatonSpecification();

        State qBegin = nonDeterministicAutomat.addState();
        nonDeterministicAutomat
                .addTransition(qBegin, qBegin, new CharTransitionLabel('a'));
        State q1 = nonDeterministicAutomat
                .addTransition(qBegin, new CharTransitionLabel('a'));
        State q2 = nonDeterministicAutomat
                .addTransition(qBegin, new CharTransitionLabel('b'));
        State q4 = nonDeterministicAutomat
                .addTransition(qBegin, new CharTransitionLabel('a'));
        nonDeterministicAutomat
                .addTransition(q1, q2, new CharTransitionLabel('b'));
        nonDeterministicAutomat
                .addTransition(q2, q2, new CharTransitionLabel('b'));
        State q3 = nonDeterministicAutomat
                .addTransition(q2, new CharTransitionLabel('a'));
        nonDeterministicAutomat
                .addTransition(q3, q4, new CharTransitionLabel('b'));
        State q5 = nonDeterministicAutomat
                .addTransition(q4, new CharTransitionLabel('b'));
        nonDeterministicAutomat
                .addTransition(q5, q3, new CharTransitionLabel('a'));
        nonDeterministicAutomat.markAsInitial(qBegin);
        nonDeterministicAutomat.markAsFinal(q5);

        try {
            AutomataOperations.determinize2(nonDeterministicAutomat, deterministicAutomat);
        } catch (Exception e) {
            fail();
        }

        AutomatonByRecursion zdeterminizowany = new AutomatonByRecursion(deterministicAutomat);

        assertFalse(zdeterminizowany.accepts(""));
        assertTrue(zdeterminizowany.accepts("aaaab"));
        assertTrue(zdeterminizowany.accepts("babb"));
        assertTrue(zdeterminizowany.accepts("abbbbabb"));
        assertFalse(zdeterminizowany.accepts("bbb"));
        assertFalse(zdeterminizowany.accepts("aaa"));
        assertFalse(zdeterminizowany.accepts("inne"));
    }

    /**
    * Test metody Intersection z AutomataOperations na automatach z petlami.
    */
    public final void testIntersectionSimple() {

        AutomatonSpecification automatonA = new NaiveAutomatonSpecification();

>>>>>>> d78ffc372bbb4628f2d7ac9e0e43aeee66fa4914
        State q0 = automatonA.addState();
        State q1 = automatonA.addState();
        automatonA.addTransition(q0, q1, new CharTransitionLabel('a'));
        automatonA.addLoop(q1, new CharTransitionLabel('a'));
        automatonA.addLoop(q1, new CharTransitionLabel('b'));
        automatonA.markAsInitial(q0);
        automatonA.markAsFinal(q1);

<<<<<<< HEAD
        AutomatonSpecification automatonB = AutomataOperations.reverseLanguageAutomaton(automatonA);
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

        AutomatonSpecification automatonB = AutomataOperations.reverseLanguageAutomaton(automatonA);
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
=======
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


        AutomatonSpecification result = AutomataOperations.intersection(automatonA, automatonB);
        AutomatonByRecursion automaton = new AutomatonByRecursion(result);

        assertTrue(automaton.accepts("aa"));
        assertTrue(automaton.accepts("ab"));
        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("a"));
        assertFalse(automaton.accepts("Blablablabla bla"));
        assertFalse(automaton.accepts("abababab"));
        assertFalse(automaton.accepts("aba"));
        assertFalse(automaton.accepts("ba"));
    }

    /**
     * Test metody intersection z AutomataOperations na automacie A z soba samym.
     */
    public final void testIntersectionWithTheSameAutomaton() {
        AutomatonSpecification automatonA = new NaiveAutomatonSpecification();

        State q0 = automatonA.addState();
        State q1 = automatonA.addState();
        State q2 = automatonA.addState();
        automatonA.addTransition(q0, q2, new CharTransitionLabel('b'));
        automatonA.addTransition(q0, q1, new CharTransitionLabel('a'));
        automatonA.addTransition(q1, q2, new CharTransitionLabel('a'));
        automatonA.addLoop(q2, new CharTransitionLabel('b'));
        automatonA.markAsInitial(q0);
        automatonA.markAsFinal(q2);

        AutomatonSpecification result = AutomataOperations.intersection(automatonA, automatonA);
        AutomatonByRecursion automaton = new AutomatonByRecursion(result);

        assertTrue(automaton.accepts("aabb"));
        assertTrue(automaton.accepts("bb"));
        assertTrue(automaton.accepts("bbbbbbbbbbbbbbbbb"));
        assertFalse(automaton.accepts("aaaaaaaaaaaaaaaa"));
        assertFalse(automaton.accepts("xxxxaabbxxxxxx"));
        assertFalse(automaton.accepts(""));

    }

     /**
     * Test metody intersection z AutomataOperations na automatach z epsilon przejsciami.
     */
    public final void testIntersectionwithEpsilonTransition() {
        AutomatonSpecification automatonA = new NotNaiveAutomatonSpecification();

        State q0 = automatonA.addState();
        State q1 = automatonA.addState();
        State q2 = automatonA.addState();

        automatonA.addTransition(q0, q1, new EpsilonTransitionLabel());
        automatonA.addTransition(q0, q2, new EpsilonTransitionLabel());
        automatonA.addLoop(q1, new CharTransitionLabel('a'));
        automatonA.addLoop(q2, new CharTransitionLabel('b'));
        automatonA.markAsInitial(q0);
        automatonA.markAsFinal(q0);
        automatonA.markAsFinal(q1);
        automatonA.markAsFinal(q2);

        AutomatonSpecification automatonB = new NotNaiveAutomatonSpecification();

        State q10 = automatonB.addState();
        State q11 = automatonB.addState();
        State q12 = automatonB.addState();
        State q13 = automatonB.addState();
        State q14 = automatonB.addState();
        State q15 = automatonB.addState();
        State q16 = automatonB.addState();

        automatonB.addTransition(q10, q11, new EpsilonTransitionLabel());
        automatonB.addTransition(q10, q14, new EpsilonTransitionLabel());
        automatonB.addTransition(q11, q12, new CharTransitionLabel('a'));
        automatonB.addTransition(q12, q13, new CharTransitionLabel('b'));
        automatonB.addLoop(q13, new CharTransitionLabel('b'));
        automatonB.addTransition(q14, q15, new CharTransitionLabel('b'));
        automatonB.addTransition(q15, q16, new CharTransitionLabel('b'));
        automatonB.addLoop(q16, new CharTransitionLabel('b'));
        automatonB.markAsInitial(q10);
        automatonB.markAsFinal(q12);
        automatonB.markAsFinal(q13);
        automatonB.markAsFinal(q15);

        AutomatonSpecification result = AutomataOperations.intersection(automatonA, automatonB);

        NondeterministicAutomatonByThompsonApproach automaton = new
        NondeterministicAutomatonByThompsonApproach(result);

        assertTrue(automaton.accepts("b"));
        assertTrue(automaton.accepts("a"));
        assertFalse(automaton.accepts("aaaaaaaaaaaaaaaa"));
        assertFalse(automaton.accepts("bbbbbbbbbbbbbbbbbb"));
        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("abbb"));
        assertFalse(automaton.accepts("nieakceptowanedlugieslowo"));



    }

    /**
     * Test metody intersection z AutomataOperations gdzie automat A
     * ma epsilon przejscia i stan koncowy jest poczatkowym.
     */
    public final void testIntersectionwhereInitialIsFinalA() {
        AutomatonSpecification automatonA = new NaiveAutomatonSpecification();

        State q0 = automatonA.addState();
        automatonA.addLoop(q0, new CharTransitionLabel('b'));
        automatonA.markAsInitial(q0);
        automatonA.markAsFinal(q0);

        AutomatonSpecification automatonB = new NaiveAutomatonSpecification();

        State q10 = automatonB.addState();
        State q11 = automatonB.addState();
        automatonB.addTransition(q10, q11, new CharTransitionLabel('b'));
        automatonB.addTransition(q10, q11, new EpsilonTransitionLabel());
        automatonB.markAsInitial(q10);
        automatonB.markAsFinal(q10);
        automatonB.markAsFinal(q11);

        AutomatonSpecification result = AutomataOperations.intersection(automatonA, automatonB);

        NondeterministicAutomatonByThompsonApproach automaton = new
        NondeterministicAutomatonByThompsonApproach(result);

        assertTrue(automaton.accepts("b"));
        assertTrue(automaton.accepts(""));
        assertFalse(automaton.accepts("a"));
        assertFalse(automaton.accepts("bbbbbbbbbbbbbb"));
        assertFalse(automaton.accepts("bardzodlugieslowowbardzodlugieslowo"));

    }

    /**
     * Test metody intersection z AutomataOperations gdzie automat B
     * ma epsilon przejscia i stan koncowy jest poczatkowym.
     */
    public final void testIntersectionwhereInitialisFinalB() {


        AutomatonSpecification automatonA = new NaiveAutomatonSpecification();

        State q10 = automatonA.addState();
        State q11 = automatonA.addState();
        automatonA.addTransition(q10, q11, new CharTransitionLabel('b'));
        automatonA.addTransition(q10, q11, new EpsilonTransitionLabel());
        automatonA.markAsInitial(q10);
        automatonA.markAsFinal(q10);
        automatonA.markAsFinal(q11);

        AutomatonSpecification automatonB = new NaiveAutomatonSpecification();

        State q0 = automatonB.addState();
        automatonB.addLoop(q0, new CharTransitionLabel('b'));
        automatonB.markAsInitial(q0);
        automatonB.markAsFinal(q0);

        AutomatonSpecification result = AutomataOperations.intersection(automatonA, automatonB);

        NondeterministicAutomatonByThompsonApproach automaton = new
        NondeterministicAutomatonByThompsonApproach(result);

        assertTrue(automaton.accepts("b"));
        assertTrue(automaton.accepts(""));
        assertFalse(automaton.accepts("a"));
        assertFalse(automaton.accepts("bbbbbbbbbbbbbb"));
        assertFalse(automaton.accepts("bardzodlugieslowowbardzodlugieslowo"));

    }

    /**
     * Test metody intersection na automacie bez stanu końcowego.
     */
    public final void testIntersectionOnNoFiniteStateAutomaton() {
        AutomatonSpecification automatonA =
            new NaiveAutomatonSpecification().makeOneTransitionAutomaton('a');

        AutomatonSpecification automatonB = new NaiveAutomatonSpecification();
        State q0 = automatonB.addState();
        automatonB.markAsInitial(q0);
        automatonB.addTransition(q0, new CharTransitionLabel('a'));

        AutomatonSpecification intersectedAB = AutomataOperations.intersection(
            automatonA, automatonB);

        NondeterministicAutomatonByThompsonApproach automatonAB = new
            NondeterministicAutomatonByThompsonApproach(intersectedAB);
        assertFalse(automatonAB.accepts("a"));
        assertFalse(automatonAB.accepts(""));
    }

    /**
     * Test complementLanguageAutomaton() dla automatu "pustego".
     */
    public final void testComplementLanguageAutomaton0EmptyAutomaton() {
        DeterministicAutomatonSpecification pustyOjciec = new
                NaiveDeterministicAutomatonSpecification();

        State q0 = pustyOjciec.addState();
        pustyOjciec.addLoop(q0, new CharTransitionLabel('a'));
        pustyOjciec.markAsInitial(q0);
        HashSet<Character> zbior = new HashSet<Character>();
        zbior.add('a');
        zbior.add('b');
        zbior.add('c');

        AutomatonByRecursion pusteDziecko = new
         AutomatonByRecursion(AutomataOperations.complementLanguageAutomaton(pustyOjciec, zbior));

        assertTrue(pusteDziecko.accepts("a"));
        assertTrue(pusteDziecko.accepts("abba"));
        assertTrue(pusteDziecko.accepts(""));
        assertTrue(pusteDziecko.accepts("cc"));
        assertFalse(pusteDziecko.accepts("dd"));
    }

    /**
     * Test complementLanguageAutomaton() dla automatu akceptującego dziwne "a".
     */
    public final void testComplementLanguageAutomaton1StrangeAAutomaton() {
        DeterministicAutomatonSpecification autLucas = new
                NaiveDeterministicAutomatonSpecification();

        State q0 = autLucas.addState();
        State q1 = autLucas.addState();
        State q2 = autLucas.addState();
        State q3 = autLucas.addState();
        autLucas.addTransition(q0, q1, new CharTransitionLabel('a'));
        autLucas.addTransition(q1, q2, new CharTransitionLabel('a'));
        autLucas.addTransition(q2, q3, new CharTransitionLabel('a'));
        autLucas.addTransition(q3, q1, new CharTransitionLabel('a'));
        autLucas.markAsInitial(q0);
        autLucas.markAsFinal(q2);
        autLucas.markAsFinal(q3);
        HashSet<Character> zbior = new HashSet<Character>();
        zbior.add('a');

        AutomatonByRecursion autLucasBR = new
            AutomatonByRecursion(AutomataOperations.complementLanguageAutomaton(autLucas, zbior));

        assertTrue(autLucasBR.accepts(""));
        assertTrue(autLucasBR.accepts("a"));
        assertTrue(autLucasBR.accepts("aaaa"));
        assertFalse(autLucasBR.accepts("aa"));
        assertFalse(autLucasBR.accepts("aaa"));
    }

    /**
     * Test complementLanguageAutomaton() dla automatu akceptującego "ab" i "ba".
     */
    public final void testComplementLanguageAutomaton2AbBaAutomaton() {
        DeterministicAutomatonSpecification abba = new NaiveDeterministicAutomatonSpecification();

        State q0 = abba.addState();
        State qa = abba.addTransition(q0, new CharTransitionLabel('a'));
        State qb = abba.addTransition(q0, new CharTransitionLabel('b'));
        State qab = abba.addTransition(qa, new CharTransitionLabel('b'));
        State qba = abba.addTransition(qb, new CharTransitionLabel('a'));
        State smietnik = abba.addTransition(qa, new CharTransitionLabel('a'));
        abba.addTransition(qb, smietnik, new CharTransitionLabel('b'));
        abba.addTransition(qab, smietnik, new CharTransitionLabel('a'));
        abba.addTransition(qab, smietnik, new CharTransitionLabel('b'));
        abba.addTransition(qba, smietnik, new CharTransitionLabel('a'));
        abba.addTransition(qba, smietnik, new CharTransitionLabel('b'));
        abba.addLoop(smietnik, new CharTransitionLabel('a'));
        abba.addLoop(smietnik, new CharTransitionLabel('b'));
        abba.markAsInitial(q0);
        abba.markAsFinal(qab);
        abba.markAsFinal(qba);
        HashSet<Character> zbior = new HashSet<Character>();
        zbior.add('a');
        zbior.add('b');

        AutomatonByRecursion abbaBR = new
                AutomatonByRecursion(AutomataOperations.complementLanguageAutomaton(abba, zbior));

        assertTrue(abbaBR.accepts(""));
        assertTrue(abbaBR.accepts("a"));
        assertTrue(abbaBR.accepts("b"));
        assertFalse(abbaBR.accepts("ab"));
        assertFalse(abbaBR.accepts("ba"));
        assertTrue(abbaBR.accepts("aa"));
        assertTrue(abbaBR.accepts("bb"));
        assertTrue(abbaBR.accepts("aba"));
        assertTrue(abbaBR.accepts("bab"));
        assertTrue(abbaBR.accepts("abb"));
        assertTrue(abbaBR.accepts("baa"));
>>>>>>> d78ffc372bbb4628f2d7ac9e0e43aeee66fa4914
    }
}
