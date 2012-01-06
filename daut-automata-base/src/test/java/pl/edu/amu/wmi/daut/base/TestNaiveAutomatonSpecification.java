package pl.edu.amu.wmi.daut.base;

import junit.framework.TestCase;
import java.util.List;

/**
 * Przykładowe testy przykładowej klasy NaiveAutomatonSpecification.
 */
public class TestNaiveAutomatonSpecification extends TestCase {

    /**
     * Test prostego automatu o trzech stanach.
     */
    public final void testSimpleAutomaton() {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        // budowanie

        State s0 = spec.addState();
        State s1 = spec.addState();
        spec.addTransition(s0, s1, new CharTransitionLabel('a'));
        State s2 = spec.addState();
        spec.addTransition(s0, s2, new CharTransitionLabel('b'));
        spec.addTransition(s1, s2, new CharTransitionLabel('c'));

        spec.markAsInitial(s0);
        spec.markAsFinal(s2);

        // testowanie

        State r0 = spec.getInitialState();

        List<OutgoingTransition> r0Outs = spec.allOutgoingTransitions(r0);

        // w ten sposób w JUnicie wyrażamy oczekiwanie, że liczba
        // przejść wychodzących z początkowego stanu powinna być równa 2
        assertEquals(r0Outs.size(), 2);
        assertFalse(spec.isFinal(r0));

        State r1;
        State r2;

        if (((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar() == 'a') {
            r1 = r0Outs.get(0).getTargetState();
            r2 = r0Outs.get(1).getTargetState();
            assertEquals(((CharTransitionLabel) r0Outs.get(1).getTransitionLabel()).getChar(), 'b');
            assertTrue(
                ((CharTransitionLabel) r0Outs.get(1).getTransitionLabel()).canAcceptCharacter('b'));
            assertFalse(
                ((CharTransitionLabel) r0Outs.get(1).getTransitionLabel()).canAcceptCharacter('c'));
            assertFalse(((CharTransitionLabel) r0Outs.get(1).getTransitionLabel()).canBeEpsilon());
        } else {
            // kolejność może być odwrócona
            r1 = r0Outs.get(1).getTargetState();
            r2 = r0Outs.get(0).getTargetState();
            assertEquals(((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar(), 'b');
        }

        assertFalse(spec.isFinal(r1));
        assertTrue(spec.isFinal(r2));
        assertSame(r0, spec.getInitialState());
        assertNotSame(r0, r1);
        assertNotSame(r0, r2);
        assertNotSame(r1, r2);

        List<State> states = spec.allStates();

        assertEquals(states.size(), 3);
    }

    /**
     * Prosty test wyznaczania przecięcia.
     */
    public final void testIntersections() {
        CharTransitionLabel tA1 = new CharTransitionLabel('a');
        CharTransitionLabel tA2 = new CharTransitionLabel('a');
        CharTransitionLabel tB = new CharTransitionLabel('b');
        EmptyTransitionLabel emptyTransition = new EmptyTransitionLabel();

        TransitionLabel intersectedA = tA1.intersect(tA2);
        assertFalse(intersectedA.isEmpty());
        assertTrue(intersectedA.canAcceptCharacter('a'));
        assertFalse(intersectedA.canAcceptCharacter('b'));

        assertTrue(tA1.intersect(tB).isEmpty());
        assertTrue(tB.intersect(tA1).isEmpty());
        assertTrue(emptyTransition.intersect(tA1).isEmpty());
        assertTrue(tA1.intersect(emptyTransition).isEmpty());
        assertTrue(emptyTransition.intersect(emptyTransition).isEmpty());
    }

    /**
     * Test metody dodającej pętlę.
     */
    public final void testAddLoop() {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        //budowanie

        State s0 = spec.addState();
        spec.addLoop(s0, new CharTransitionLabel('a'));
        spec.markAsInitial(s0);
        spec.markAsFinal(s0);

        //testowanie

        State r0 = spec.getInitialState();

        List<OutgoingTransition> r0Outs = spec.allOutgoingTransitions(r0);

        assertEquals(r0Outs.size(), 1);
        assertTrue(spec.isFinal(r0));

        if (((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar() == 'a') {
            assertEquals(
                ((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar(), 'a');
            assertTrue(
                ((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).canAcceptCharacter('a'));
            assertFalse(
                ((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).canBeEpsilon());
        }

        assertTrue(spec.isFinal(r0));
        assertSame(r0, spec.getInitialState());

        List<State> states = spec.allStates();

        assertEquals(states.size(), 1);

    }

    /**
     * Test metody tworzącej prosty automat.
     */
    public final void testmakeOneLoopAutomaton(char c) {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        //budowanie

        State s0 = spec.addState();
        spec.addLoop(s0, new CharTransitionLabel('c'));
        spec.markAsInitial(s0);
        spec.markAsFinal(s0);

        //testowanie

        State r0 = spec.getInitialState();

        List<OutgoingTransition> r0Outs = spec.allOutgoingTransitions(r0);

        assertEquals(r0Outs.size(), 1);
        assertTrue(spec.isFinal(r0));

        State r1;

        if (((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar() == 'c') {
            r1 = r0Outs.get(0).getTargetState();
            assertEquals(
                ((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar(), 'c');
            assertTrue(
                ((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).canAcceptCharacter('c'));
            assertFalse(
                ((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).canBeEpsilon());
        }

        assertTrue(spec.isFinal(r0));
        assertSame(r0, spec.getInitialState());

        List<State> states = spec.allStates();

        assertEquals(states.size(), 1);

    }

    /**
     * Test metody dopełniającej automat.
     */
    public final void testMakeFull() {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();
        NaiveAutomatonSpecification spec2 = new NaiveAutomatonSpecification();
        NaiveAutomatonSpecification spec3 = new NaiveAutomatonSpecification();

        assertFalse(spec.isFull("abc"));

        spec.makeFull("abc");

        assertTrue(spec.isFull("abc"));
        assertEquals(spec.countStates(), 1);

        State s = spec2.addState();

        assertFalse(spec2.isFull("abc"));

        spec2.makeFull("abc");
        assertTrue(spec2.isFull("abc"));
        assertEquals(spec2.countStates(), 2);

        State s0 = spec3.addState();
        State s1 = spec3.addState();
        State s2 = spec3.addState();

        spec3.addTransition(s0, s1, new CharTransitionLabel('a'));

        assertFalse(spec3.isFull("abc"));

        spec3.makeFull("abc");
        assertTrue(spec3.isFull("abc"));
        assertEquals(spec3.countStates(), 4);
    }

    /**
     * Test metody dopełniającej automat na automacie, który jest już pełny.
     */
    public final void testMakeFullAlreadyFull() {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        State s0 = spec.addState();
        State s1 = spec.addState();
        State s2 = spec.addState();

        spec.addTransition(s0, s1, new CharTransitionLabel('b'));
        spec.addTransition(s0, s1, new CharTransitionLabel('a'));
        spec.addLoop(s1, new CharTransitionLabel('a'));
        spec.addLoop(s1, new CharTransitionLabel('b'));
        spec.addLoop(s2, new CharTransitionLabel('a'));
        spec.addLoop(s2, new CharTransitionLabel('b'));

        spec.makeFull("ab");
        assertTrue(spec.isFull("ab"));
        assertEquals(spec.countStates(), 3);
    }

    /**
     * Test metody dopełniającej automat na automacie, któremu brakuje jednego przejścia.
     */
    public final void testMakeFullAlmostFull() {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        State s0 = spec.addState();
        State s1 = spec.addState();
        State s2 = spec.addState();

        spec.addTransition(s0, s1, new CharTransitionLabel('a'));
        spec.addLoop(s1, new CharTransitionLabel('a'));
        spec.addLoop(s1, new CharTransitionLabel('b'));
        spec.addLoop(s2, new CharTransitionLabel('a'));
        spec.addLoop(s2, new CharTransitionLabel('b'));

        spec.makeFull("ab");
        assertEquals(spec.countStates(), 4);
        assertTrue(spec.isFull("ab"));
    }

    /**
     * Test metody tworzącej prosty automat.
     */
    public final void testmakeOneTransitionAutomaton(char c) {
        AutomatonSpecification spec = new NaiveAutomatonSpecification();

        spec.makeOneTransitionAutomaton(c);

        //testowanie

        State r0 = spec.getInitialState();

        List<OutgoingTransition> r0Outs = spec.allOutgoingTransitions(r0);

        assertEquals(r0Outs.size(), 1);
        assertFalse(spec.isFinal(r0));

        State r1 = r0Outs.get(0).getTargetState();

        if (((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar() == 'c') {
            assertEquals(
                ((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar(), 'c');
            assertTrue(
                ((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).canAcceptCharacter('c'));
            assertFalse(
                ((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).canBeEpsilon());
        }

        assertTrue(spec.isFinal(r1));
        assertSame(r0, spec.getInitialState());

        List<State> states = spec.allStates();

        assertEquals(states.size(), 2);
    }

    /**
     * Test metody tworzącej automat akceptujący wszystkie napisy nad zadanym językiem.
     */
    public final void testMakeAllStringsAutomaton() {
        final AutomatonSpecification spec = new NaiveAutomatonSpecification();
        spec.makeAllStringsAutomaton("abc");
        assertTrue(spec.acceptEmptyWord());
        AutomatonByRecursion automaton = new AutomatonByRecursion(spec);
        assertFalse(automaton.accepts("defffadegbc"));
        assertFalse(automaton.accepts("abecadlo"));
        assertTrue(automaton.accepts("abcbcabbbaaa"));
        assertTrue(automaton.accepts("bbccaabcbabab"));
        assertTrue(automaton.accepts("cacacacbbccccc"));
    }

    /**
     * Testuje działanie metody makeAllNonEmptyStringsAutomaton().
     */
    public final void testMakeAllNonEmptyStringsAutomaton() {

        //Buduję automat na 2 stanach korzystając z testowanej metody

        final AutomatonSpecification spec = new NaiveAutomatonSpecification();
        spec.makeAllNonEmptyStringsAutomaton("ab");
        AutomatonByRecursion automaton = new AutomatonByRecursion(spec);

        //Sprawdzam czy automat akceptuje losowe słowa i czy odrzuca słowo puste

        assertFalse(spec.acceptEmptyWord());
        assertTrue(automaton.accepts("abbabbabbabbaaa"));
        assertFalse(automaton.accepts("caba"));
        assertTrue(automaton.accepts("a"));
        assertTrue(automaton.accepts("b"));
        assertTrue(automaton.accepts("aaaa"));
        assertTrue(automaton.accepts("bbbb"));
    }

    /**
     * Test metody sprawdzającej, czy akceptowany język jest nieskończony dla
     * automatu z kilkoma pętlami zarówno w stanach pojedyńczych, jak i z wieloma
     * stanami.
     */
    public final void testInfiniteForManyLoops() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();
        State s1 = automat.addState();
        State s2 = automat.addState();
        State s3 = automat.addState();
        State s4 = automat.addState();

        automat.addTransition(s0, s1, new CharTransitionLabel('a'));
        automat.addLoop(s1, new CharTransitionLabel('b'));
        automat.addLoop(s2, new CharTransitionLabel('b'));
        automat.addTransition(s1, s2, new CharTransitionLabel('a'));
        automat.addTransition(s2, s1, new CharTransitionLabel('a'));
        automat.addTransition(s2, s0, new CharTransitionLabel('c'));
        automat.addTransition(s2, s3, new CharTransitionLabel('c'));
        automat.addTransition(s3, s4, new CharTransitionLabel('a'));
        automat.addLoop(s4, new CharTransitionLabel('b'));

        automat.markAsFinal(s2);
        automat.markAsFinal(s1);
        automat.markAsInitial(s0);
        automat.markAsFinal(s4);

        assertTrue(automat.isInfinite());
    }

    /**
     * Test metody sprawdzającej, czy akceptowany język jest nieskończony przy
     * automacie z pętlą rozpoczynającą się zaraz po przejściu ze stanu początkowego.
     */
    public final void testInfiniteForLoopStartingAfterState0() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();
        State s1 = automat.addState();
        State s2 = automat.addState();
        State s3 = automat.addState();

        automat.addTransition(s0, s1, new CharTransitionLabel('a'));
        automat.addTransition(s1, s2, new CharTransitionLabel('a'));
        automat.addTransition(s2, s3, new CharTransitionLabel('a'));
        automat.addTransition(s3, s1, new CharTransitionLabel('a'));

        automat.markAsInitial(s0);
        automat.markAsFinal(s2);

        assertTrue(automat.isInfinite());
    }

    /**
     * Test metody sprawdzającej, czy akceptowany język jest nieskończony dla
     * automatu, w którym z pętli zawierającej stan końcowy da się wyjść do
     * innego stanu końcowego.
     */
    public final void testInfiniteForStateAfterLoop() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();
        State s1 = automat.addState();
        State s2 = automat.addState();
        State s3 = automat.addState();
        State s4 = automat.addState();

        automat.addTransition(s0, s1, new CharTransitionLabel('a'));
        automat.addTransition(s1, s2, new CharTransitionLabel('a'));
        automat.addTransition(s2, s3, new CharTransitionLabel('a'));
        automat.addTransition(s3, s1, new CharTransitionLabel('a'));
        automat.addTransition(s3, s4, new CharTransitionLabel('b'));

        automat.markAsInitial(s0);
        automat.markAsFinal(s2);
        automat.markAsFinal(s1);
        automat.markAsFinal(s4);

        assertTrue(automat.isInfinite());
    }

    /**
     * Test metody sprawdzającej, czy akceptowany język jest nieskończony dla
     * automatu, w którym z pętli zawierającej stan końcowy da sie wyjść do innej
     * pętli.
     */
    public final void testInfiniteForLoopAfterLoop() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();
        State s1 = automat.addState();
        State s2 = automat.addState();
        State s3 = automat.addState();
        State s4 = automat.addState();
        State s5 = automat.addState();

        automat.addTransition(s0, s1, new CharTransitionLabel('a'));
        automat.addTransition(s1, s2, new CharTransitionLabel('a'));
        automat.addTransition(s2, s3, new CharTransitionLabel('a'));
        automat.addTransition(s3, s2, new CharTransitionLabel('b'));
        automat.addTransition(s3, s1, new CharTransitionLabel('a'));
        automat.addTransition(s3, s4, new CharTransitionLabel('b'));
        automat.addTransition(s4, s5, new CharTransitionLabel('a'));
        automat.addTransition(s5, s4, new CharTransitionLabel('a'));

        automat.markAsInitial(s0);
        automat.markAsFinal(s1);

        assertTrue(automat.isInfinite());
    }

    /**
     * Test metody sprawdzającej, czy akceptowany język jest nieskończony dla
     * automatu, w którym stan końcowy jest zawarty po pętli.
     */
    public final void testInfiniteForFinalAfterLoop() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();
        State s1 = automat.addState();
        State s2 = automat.addState();
        State s3 = automat.addState();
        State s4 = automat.addState();

        automat.addTransition(s0, s1, new CharTransitionLabel('a'));
        automat.addTransition(s1, s2, new CharTransitionLabel('a'));
        automat.addTransition(s2, s3, new CharTransitionLabel('a'));
        automat.addTransition(s3, s1, new CharTransitionLabel('a'));
        automat.addTransition(s3, s4, new CharTransitionLabel('b'));

        automat.markAsInitial(s0);
        automat.markAsFinal(s4);

        assertTrue(automat.isInfinite());
    }

    /**
     * Test metody sprawdzającej, czy akceptowany język jest nieskończony dla
     * automatu, w którym mamy zwykłą ścieżkę ze stanu s0 do stanu s4 bez żadnych
     * pętli.
     */
    public final void testInfiniteForSimpleRoute() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();
        State s1 = automat.addState();
        State s2 = automat.addState();
        State s3 = automat.addState();
        State s4 = automat.addState();

        automat.addTransition(s0, s1, new CharTransitionLabel('a'));
        automat.addTransition(s1, s2, new CharTransitionLabel('a'));
        automat.addTransition(s2, s3, new CharTransitionLabel('a'));
        automat.addTransition(s3, s4, new CharTransitionLabel('a'));

        automat.markAsInitial(s0);
        automat.markAsFinal(s3);

        assertFalse(automat.isInfinite());
    }

    /**
     * Test metody sprawdzającej, czy akceptowany język jest nieskończony dla
     * automatu z jedną pętlą i dwoma stanami końcowymi w niej.
     */
    public final void testInfiniteForOneLoopAndTwoFinalStates() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();
        State s1 = automat.addState();
        State s2 = automat.addState();
        State s3 = automat.addState();

        automat.addTransition(s1, s2, new CharTransitionLabel('a'));
        automat.addTransition(s2, s3, new CharTransitionLabel('a'));
        automat.addTransition(s3, s1, new CharTransitionLabel('a'));

        automat.markAsInitial(s0);
        automat.markAsFinal(s2);
        automat.markAsFinal(s1);

        assertFalse(automat.isInfinite());
    }

    /**
     * Test metody sprawdzającej, czy akceptowany język jest nieskończony dla
     * automatu zawierającego jeden stan zawierający pętlę do samego siebie.
     */
    public final void testInfiniteForOneState() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();

        automat.addLoop(s0, new CharTransitionLabel('a'));

        automat.markAsInitial(s0);
        automat.markAsFinal(s0);

        assertTrue(automat.isInfinite());
    }

    /**
     * Test metody sprawdzającej, czy akceptowany język jest nieskończony z pętlą
     * złożoną z 2 stanów dla sytuacji, kiedy stanem końcowym jest stan początkowy.
     */
    public final void testInfiniteFinalState0() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();
        State s1 = automat.addState();

        automat.addTransition(s0, s1, new CharTransitionLabel('a'));
        automat.addTransition(s1, s0, new CharTransitionLabel('a'));

        automat.markAsInitial(s0);
        automat.markAsFinal(s0);

        assertTrue(automat.isInfinite());
    }

    /**
     * Test metody sprawdzającej, czy akceptowany język jest nieskończony dla
     * automatu, w którym jeden ze stanów końcowych jest zawarty przed pętlą.
     */
    public final void testInfiniteForFinalUntilLoop() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();
        State s1 = automat.addState();
        State s2 = automat.addState();
        State s3 = automat.addState();

        automat.addTransition(s0, s1, new CharTransitionLabel('a'));
        automat.addTransition(s1, s2, new CharTransitionLabel('b'));
        automat.addTransition(s2, s3, new CharTransitionLabel('b'));
        automat.addTransition(s3, s2, new CharTransitionLabel('c'));

        automat.markAsFinal(s1);
        automat.markAsFinal(s3);
        automat.markAsInitial(s0);

        assertTrue(automat.isInfinite());
    }

    /**
     * Test ze "ślepą uliczką".
     */
    public final void testInfiniteDeadEnd() {
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();

        State s0 = automat.addState();
        State sA1 = automat.addState();
        State sA2 = automat.addState();
        State sA3 = automat.addState();
        State sB1 = automat.addState();
        State sB2 = automat.addState();
        State sB3 = automat.addState();

        automat.addTransition(s0, sA1, new CharTransitionLabel('a'));
        automat.addTransition(s0, sB1, new CharTransitionLabel('b'));

        automat.markAsInitial(s0);
        automat.markAsFinal(sA1);
        automat.markAsFinal(sB1);

        assertFalse(automat.isInfinite());

        automat.addTransition(sA1, sA2, new AnyTransitionLabel());
        automat.addTransition(sA2, sA3, new CharTransitionLabel('a'));
        automat.addTransition(sA3, sA2, new CharTransitionLabel('b'));

        automat.addTransition(sB1, sB2, new AnyTransitionLabel());
        automat.addTransition(sB2, sB3, new CharTransitionLabel('a'));
        automat.addTransition(sB3, sB2, new CharTransitionLabel('x'));

        assertFalse(automat.isInfinite());

        automat.markAsFinal(sB2);
        assertTrue(automat.isInfinite());
    }


}
