package pl.edu.amu.wmi.daut.base;
import java.util.List;
import java.util.Arrays;

import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;

/**
 * Testy klasy AutomatonSpecification.
 */
public class TestAutomatonSpecification extends TestCase {

    /**
     * Test metody countStates.
     */
    public final void testCountStates() {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        //Test 1
        assertEquals(spec.countStates(), 0);

        //Test 2
        State q0 = spec.addState();
        assertEquals(spec.countStates(), 1);

        //Test 3
        for (int i = 1; i <= 123456; i++) {
            State q = spec.addState();
        }
        assertEquals(spec.countStates(), 123456 + 1);
    }

    /**
     * Test metody acceptEmptyWord dla automatu z tym samym stanem poczatkowym i koncowym.
     */
    public final void testAcceptEmptyWordFinalTheSameAsInitial() {

        //Test 1 - stan poczatkowy jest stanem koncowym
        NaiveAutomatonSpecification testSpec1 = new NaiveAutomatonSpecification();
        State state = testSpec1.addState();
        testSpec1.markAsInitial(state);
        testSpec1.markAsFinal(state);
        assertTrue(testSpec1.acceptEmptyWord());
    }

    /**
     * Test metody acceptEmptyWord dla automatu bez epsilon-przejsc.
     */
    public final void testAcceptEmptyWordNoEpsilonTransitions() {

        //Test 2 - automat ma wiecej stanow, bez epsilon-przejsc do stanu koncowego
        NaiveAutomatonSpecification testSpec2 = new NaiveAutomatonSpecification();
        State q0 = testSpec2.addState();
        State q1 = testSpec2.addState();
        State q2 = testSpec2.addState();

        testSpec2.addTransition(q0, q1, new CharTransitionLabel('a'));
        testSpec2.addTransition(q1, q2, new CharTransitionLabel('b'));

        testSpec2.markAsInitial(q0);
        testSpec2.markAsFinal(q2);

        assertFalse(testSpec2.acceptEmptyWord());
    }

    /**
     * Test metody acceptEmptyWord dla automatu z epsilon przejsciami
     * ze stanu poczatkowego do koncowego.
     */
    public final void testAcceptEmptyWordWithEpsilonTransitions() {

        //Test 3 - automat jak w poprzednim przypadku
        //ale zawiera epsilon-przejscia do stanu koncowego
        NaiveAutomatonSpecification testSpec2 = new NaiveAutomatonSpecification();
        State q0 = testSpec2.addState();
        State q1 = testSpec2.addState();
        State q2 = testSpec2.addState();
        State q3 = testSpec2.addState();

        testSpec2.markAsInitial(q0);
        testSpec2.markAsFinal(q2);

        testSpec2.addTransition(q0, q1, new CharTransitionLabel('a'));
        testSpec2.addTransition(q1, q2, new CharTransitionLabel('b'));

        testSpec2.addTransition(q0, q3, new EpsilonTransitionLabel());
        testSpec2.addTransition(q3, q2, new EpsilonTransitionLabel());

        assertTrue(testSpec2.acceptEmptyWord());
    }

    /**
     * Test metody acceptEmptyWord dla automatu z pętlą.
     */
    public final void testAcceptEmptyWordForAutomatonWithLoop() {

        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();
        State s0 = spec.addState();
        State s1 = spec.addState();
        spec.markAsInitial(s0);
        spec.markAsFinal(s1);
        spec.addLoop(s0, new CharTransitionLabel('a'));
        spec.addTransition(s1, s0, new CharTransitionLabel('b'));
        assertFalse(spec.acceptEmptyWord());

        spec.markAsFinal(s0);
        assertTrue(spec.acceptEmptyWord());
    }

    /**
     * Test metody countStates.
     */
    public final void testCountTransitions() {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        //Test 1
        assertEquals(spec.countTransitions(), 0);

        //Test 2
        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();

        spec.addTransition(q0, q1, new CharTransitionLabel('a'));
        spec.addTransition(q1, q2, new CharTransitionLabel('b'));

        spec.markAsInitial(q0);
        spec.markAsFinal(q2);

        assertEquals(spec.countTransitions(), 2);

        //Test 3
        spec.addTransition(q2, q0, new CharTransitionLabel('c'));

        assertEquals(spec.countTransitions(), 3);

        //Test 4
        spec.addTransition(q0, q2, new CharTransitionLabel('d'));

        assertEquals(spec.countTransitions(), 4);

        //Test 5
        spec.addTransition(q2, q2, new CharTransitionLabel('d'));

        assertEquals(spec.countTransitions(), 5);
    }

    /**
     * Test metody makeEmptyStringAutomaton.
     */
    public final void testmakeEmptyStringAutomaton() {
        AutomatonSpecification automaton1 = new NaiveAutomatonSpecification();
        automaton1.makeEmptyStringAutomaton();
        AutomatonByRecursion angle = new AutomatonByRecursion(automaton1);

        assertFalse(angle.accepts("qqqqqq"));
        assertTrue(angle.accepts(""));
        assertFalse(angle.accepts("x"));
        assertFalse(angle.accepts("qwertyuiopasdfghjklzxcvbnm1234567890"));
        assertFalse(angle.accepts(" "));
    }

    /**
     * Testy dla metody addTransitionSequence().
     */
    public final void testAddTransitionSequence() {
        // Tworzymy automat do testów
        AutomatonSpecification spec = new NaiveAutomatonSpecification();

        // Proste testy ilości stanów i przejść
        // Pusty ciąg przejść
        State s0 = spec.addState();
        spec.markAsInitial(s0);
        spec.addTransitionSequence(s0, "");
        assertEquals(0, spec.countTransitions());
        assertEquals(1, spec.countStates());

        // Ciąg przejść składający się z jednego znaku
        spec = new NaiveAutomatonSpecification();
        s0 = spec.addState();
        spec.markAsInitial(s0);
        spec.addTransitionSequence(s0, "a");
        assertEquals(1, spec.countTransitions());
        assertEquals(2, spec.countStates());

        // Ciąg przejść składający się z jednego znaku
        // i dodanie stanu na podstawie tego co zwróciła
        // metoda addTransitionSequence()
        spec = new NaiveAutomatonSpecification();
        s0 = spec.addState();
        spec.markAsInitial(s0);
        State s1 = spec.addTransitionSequence(s0, "a");
        spec.addTransition(s1, new CharTransitionLabel('b'));
        assertEquals(2, spec.countTransitions());
        assertEquals(3, spec.countStates());

        // Ciąg przejść składający się z takich samych znaków
        spec = new NaiveAutomatonSpecification();
        s0 = spec.addState();
        spec.markAsInitial(s0);
        spec.addTransitionSequence(s0, "aa");
        assertEquals(2, spec.countTransitions());
        assertEquals(3, spec.countStates());

        // Ciąg przejść składający się z różnych znaków
        spec = new NaiveAutomatonSpecification();
        s0 = spec.addState();
        spec.markAsInitial(s0);
        spec.addTransitionSequence(s0, "abc");
        assertEquals(3, spec.countTransitions());
        assertEquals(4, spec.countStates());

        // Sprawdzamy czy przejścia mają odpowiednie oznaczenia oraz
        // akceptują odpowiednie znaki, dla ciągu "abc"
        State s;
        List<OutgoingTransition> sOuts;

        // Dla jasności pobieramy stan początkowy automatu
        s = spec.getInitialState();

        // Sprawdzamy możliwe przejścia ze stanu początkowego,
        // sprawdzamy ich ilość, oznaczenia oraz jakie znaki akceptują
        // (oczekujemy a)
        sOuts = spec.allOutgoingTransitions(s);
        assertEquals(1, sOuts.size());
        assertEquals('a', ((CharTransitionLabel)
                sOuts.get(0).getTransitionLabel()).getChar());
        assertTrue(((CharTransitionLabel)
                sOuts.get(0).getTransitionLabel()).canAcceptCharacter('a'));
        assertFalse(((CharTransitionLabel)
                sOuts.get(0).getTransitionLabel()).canAcceptCharacter('c'));

        // Kolejne przejście (oczekujemy b)
        s = sOuts.get(0).getTargetState();
        sOuts = spec.allOutgoingTransitions(s);
        assertEquals(1, sOuts.size());
        assertEquals('b', ((CharTransitionLabel)
                sOuts.get(0).getTransitionLabel()).getChar());
        assertTrue(((CharTransitionLabel)
                sOuts.get(0).getTransitionLabel()).canAcceptCharacter('b'));
        assertFalse(((CharTransitionLabel)
                sOuts.get(0).getTransitionLabel()).canAcceptCharacter('a'));
        assertFalse(((CharTransitionLabel)
                sOuts.get(0).getTransitionLabel()).canAcceptCharacter('c'));

        // Kolejne przejście (oczekujemy c)
        s = sOuts.get(0).getTargetState();
        sOuts = spec.allOutgoingTransitions(s);
        assertEquals(1, sOuts.size());
        assertEquals('c', ((CharTransitionLabel)
                sOuts.get(0).getTransitionLabel()).getChar());
        assertTrue(((CharTransitionLabel)
                sOuts.get(0).getTransitionLabel()).canAcceptCharacter('c'));
        assertFalse(((CharTransitionLabel)
                sOuts.get(0).getTransitionLabel()).canAcceptCharacter('a'));
    }

    /**
     * Test metody addBranch(). Automat o 4 stanach.
     */
    public final void testAddBranchWithFourStates() {

        // Budowanie automatu o 4 stanach.
        AutomatonSpecification spec = new NaiveAutomatonSpecification();
        State s0 = spec.addState();
        spec.markAsInitial(s0);
        List<TransitionLabel> transitions =
                Arrays.<TransitionLabel>asList(
            new CharTransitionLabel('a'),
            new CharTransitionLabel('b'),
            new CharTransitionLabel('c')
            );
        State s3 = spec.addBranch(s0, transitions);
        spec.markAsFinal(s3);

        //testowanie

        State r0 = spec.getInitialState();

        List<OutgoingTransition> r0Outs = spec.allOutgoingTransitions(r0);
        assertEquals(r0Outs.size(), 1);   //sprawdza ze jest tylko jedno przejscie
        assertTrue(((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar() == 'a');
        assertFalse(((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar() == 'b');
        assertFalse(((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).getChar() == 'c');
        assertFalse(((CharTransitionLabel) r0Outs.get(0).getTransitionLabel()).canBeEpsilon());

        State r1 = r0Outs.get(0).getTargetState();
        List<OutgoingTransition> r1Outs = spec.allOutgoingTransitions(r1);
        assertEquals(r1Outs.size(), 1);   //sprawdza ze jest tylko jedno przejscie
        assertTrue(((CharTransitionLabel) r1Outs.get(0).getTransitionLabel()).getChar() == 'b');
        assertFalse(((CharTransitionLabel) r1Outs.get(0).getTransitionLabel()).getChar() == 'a');
        assertFalse(((CharTransitionLabel) r1Outs.get(0).getTransitionLabel()).getChar() == 'c');
        assertFalse(((CharTransitionLabel) r1Outs.get(0).getTransitionLabel()).canBeEpsilon());

        State r2 = r1Outs.get(0).getTargetState();
        List<OutgoingTransition> r2Outs = spec.allOutgoingTransitions(r2);
        assertEquals(r2Outs.size(), 1);   //sprawdza ze jest tylko jedno przejscie
        assertTrue(((CharTransitionLabel) r2Outs.get(0).getTransitionLabel()).getChar() == 'c');
        assertFalse(((CharTransitionLabel) r2Outs.get(0).getTransitionLabel()).getChar() == 'a');
        assertFalse(((CharTransitionLabel) r2Outs.get(0).getTransitionLabel()).getChar() == 'b');
        assertFalse(((CharTransitionLabel) r2Outs.get(0).getTransitionLabel()).canBeEpsilon());

        State r3 = r2Outs.get(0).getTargetState();

        assertFalse(spec.isFinal(r2));
        assertTrue(spec.isFinal(r3));
        assertSame(r0, spec.getInitialState());
        assertNotSame(r0, r1);
        assertNotSame(r1, r2);
        assertNotSame(r2, r3);

        List<State> states = spec.allStates();

        assertEquals(states.size(), 4);
    }
    /**
     * Test metody addBranch(). Automat o 1 stanie.
     */
    public final void testAddBranchWithOneStates() {

        // Budowanie automatu o 1 stanie.
        AutomatonSpecification spec2 = new NaiveAutomatonSpecification();
        State st0 = spec2.addState();
        spec2.markAsInitial(st0);
        List<TransitionLabel> transitions2 =
                Arrays.<TransitionLabel>asList(
            );
        State st1 = spec2.addBranch(st0, transitions2);
        spec2.markAsFinal(st0);

        //testowanie
        State rr0 = spec2.getInitialState();

        List<OutgoingTransition> r0Outs2 = spec2.allOutgoingTransitions(rr0);
        assertEquals(r0Outs2.size(), 0);

        assertTrue(spec2.isFinal(st1));
        assertSame(st1, spec2.getInitialState());

    }


    /**
     * Testuje działanie metody toString().
     */
    public final void testToString() {
        /**
         * Pozwala na wygenerowanie tekstu "oszukanego" automatu
         * na podstawie stanów i krawędzi wprowadzonych jako tekst.
         * Imitacja działania metody toString() dla automatu.
         */
        class AutomatonString {
            private String states, transitions, istates, fstates;

            /**
             * Ustala stany, przejścia, stan początkowy oraz stany końcowe
             */
            public AutomatonString(String states, String transitions,
                    String istates, String fstates) {
                this.states = states;
                this.transitions = transitions;
                this.istates = istates;
                this.fstates = fstates;
            }

            /**
             * Zwraca "oszukany" automat w postaci tekstowej.
             */
            @Override
            public String toString() {
                StringBuffer str = new StringBuffer();
                str.append("Automaton:");
                str.append("\n-States: " + states);
                if (states.length() > 0) {
                    str.append(" ");
                }
                str.append("\n-Transitions:\n");
                if (transitions.length() > 0) {
                    str.append("  " + transitions.replaceAll(",", "\n  "));
                    str.append("\n");
                }
                str.append("-Initial state: " + istates);
                str.append("\n-Final states: " + fstates);
                if (fstates.length() > 0) {
                    str.append(" ");
                }
                return str.toString();
            }
        }

        // Pierwszy testowy automat:
        // Jeden stan z krawędzią
        NaiveAutomatonSpecification ta1 = new NaiveAutomatonSpecification();

        State ta1s0 = ta1.addState();
        ta1.addTransition(ta1s0, ta1s0, new CharTransitionLabel('a'));
        ta1.markAsInitial(ta1s0);
        ta1.markAsFinal(ta1s0);

        AutomatonString str;

        str = new AutomatonString("q0", "q0 -a-> q0", "q0", "q0");
        assertEquals(str.toString(), ta1.toString());

        // Drugi testowy automat:
        // Dwa stany, krawędzie jak w przypadku NFA
        NaiveAutomatonSpecification ta2 = new NaiveAutomatonSpecification();

        State ta2s0 = ta2.addState();
        State ta2s1 = ta2.addState();
        ta2.addTransition(ta2s0, ta2s0, new CharTransitionLabel('a'));
        ta2.addTransition(ta2s0, ta2s1, new CharTransitionLabel('a'));
        ta2.markAsInitial(ta2s0);
        ta2.markAsFinal(ta2s0);
        ta2.markAsFinal(ta2s1);

        String transitions = "q0 -a-> q0,q0 -a-> q1";
        str = new AutomatonString("q0 q1", transitions, "q0", "q0 q1");
        assertEquals(str.toString(), ta2.toString());

        // Trzeci testowy automat:
        // Dwa stany, dwa różne rodzaje krawędzi
        NaiveAutomatonSpecification ta3 = new NaiveAutomatonSpecification();

        State ta3s0 = ta3.addState();
        State ta3s1 = ta3.addState();
        ta3.addTransition(ta3s0, ta3s0, new CharTransitionLabel('a'));
        ta3.addTransition(ta3s0, ta3s1, new CharTransitionLabel('b'));
        ta3.addTransition(ta3s1, ta3s1, new CharTransitionLabel('a'));
        ta3.addTransition(ta3s1, ta3s1, new CharTransitionLabel('b'));
        ta3.markAsInitial(ta3s0);
        ta3.markAsFinal(ta3s1);

        transitions = "q0 -a-> q0,q0 -b-> q1,q1 -a-> q1,q1 -b-> q1";
        str = new AutomatonString("q0 q1", transitions, "q0", "q1");
        assertEquals(str.toString(), ta3.toString());

        // Czwarty testowy automat:
        // sprawdzamy co jest zwracane dla "pustej" instancji klasy automatu
        NaiveAutomatonSpecification ta4 = new NaiveAutomatonSpecification();

        str = new AutomatonString("", "", "", "");
        assertEquals(str.toString(), ta4.toString());

        // Piąty testowy automat:
        // Brak stanów początkowych i końcowych
        NaiveAutomatonSpecification ta5 = new NaiveAutomatonSpecification();

        State ta5s0 = ta5.addState();
        ta5.addTransition(ta5s0, ta5s0, new CharTransitionLabel('a'));

        str = new AutomatonString("q0", "q0 -a-> q0", "", "");
        assertEquals(str.toString(), ta5.toString());
    }

    /**
     * Klasa pomocnicza do testów funkcji getDotGraph().
     */
    class FakeDotGraphGenerator {
        private String states, transitions, begin, ends;
        private boolean isBeginTheEnd;

        public FakeDotGraphGenerator(String states, String transitions,
                String begin, String ends, boolean isBeginTheEnd) {
            this.states = states;
            this.transitions = transitions;
            this.begin = begin;
            this.ends = ends;
            this.isBeginTheEnd = isBeginTheEnd;
        }

        /**
         * Zwraca żądany przez testera graf w postaci String'a.
         */
        @Override
        public String toString() {
            //Poczatek
            StringBuffer dotGraphString = new StringBuffer();
            dotGraphString.append("digraph finite_state_machine {\n    rankdir=LR;\n"
                    + "    size=\"8,5\"\n    node [style=filled fillcolor=\"#00ff005f\""
                    + " shape = ");
            if (isBeginTheEnd)
                dotGraphString.append("double");
            dotGraphString.append("circle];\n    \"State #" + begin + "\";\n"
                           + "    node [shape = doublecircle style=filled "
                           + "fillcolor=\"#00000000\"];\n    ");

            //Stany końcowe
            String[] endStates = ends.split(" ");
            for (String state : endStates) {
                dotGraphString.append("\"State #" + state + "\" ");
            }

            dotGraphString.append(";\n    node [shape = circle];\n");

            //Przejścia
            for (String transition : transitions.split(" ")) {
                String[] splitedTransition = transition.split("-");
                dotGraphString.append("    \"State #" + splitedTransition[0] + "\"");
                dotGraphString.append(" -> \"State #" + splitedTransition[2] + "\"");
                if ((splitedTransition[1].length() > 2) && (splitedTransition[1].contains(",")
                            && (!(splitedTransition[1].matches("[*,*]"))))) {
                    String[] transitionLabel = splitedTransition[1].split(",");
                    dotGraphString.append(" [ label = \"" + transitionLabel[0]);
                    for (int i = 1; i < transitionLabel.length; i++) {
                        dotGraphString.append(", " + transitionLabel[i]);
                    }
                    dotGraphString.append("\" ]");
                } else {
                    dotGraphString.append(" [ label = \"" + splitedTransition[1] + "\" ]");
                }
                dotGraphString.append(";\n");
            }

            //Koniec
            dotGraphString.append("\n}\n");
            return dotGraphString.toString();
        }

        boolean isItProper(String dotGraphFromAutomaton) {
            String fakeDotGraph = this.toString();

            //Pierwszy krok testu - porównanie ich długości
            if (dotGraphFromAutomaton.length() != fakeDotGraph.length())
                return false;

            //Podział obu stringów na linie
            String[] dotGraphTab = dotGraphFromAutomaton.split("\n");
            String[] exampleOfDotGraphTab = fakeDotGraph.split("\n");

            //Porównanie ilości linii w obu Stringach
            if (dotGraphTab.length != exampleOfDotGraphTab.length)
                return false;

            //Porównanie 1: dokładnie ten sam porządek linii
            for (int i = 0; i < 6; i++)
                if (!dotGraphTab[i].equals(exampleOfDotGraphTab[i]))
                    return false;

            //Porównanie 2: stany końcowe
            int numberOfEndStates = ends.split(" ").length;
            for (int i = 6; i < 6 + numberOfEndStates; i++) {
                boolean doThisLineExist = false;
                for (int j = 6; j < 6 + numberOfEndStates; j++) {
                    if (exampleOfDotGraphTab[i].equals(dotGraphTab[j])) {
                        doThisLineExist = true;
                        break;
                    }
                }
                if (!doThisLineExist)
                    return false;
            }

            //Porównanie 3: linie mogą różnić się kolejnością
            for (int i = 6 + numberOfEndStates; i < exampleOfDotGraphTab.length; i++) {
                boolean doThisLineExist = false;
                for (int j = 6 + numberOfEndStates; j < exampleOfDotGraphTab.length; j++) {
                    if (exampleOfDotGraphTab[i].equals(dotGraphTab[j])) {
                        doThisLineExist = true;
                        break;
                    }
                }
                if (!doThisLineExist)
                    return false;
            }

            return true;
        }
    }

    /**
     * Pierwszy test metody getDotGraph(). Prosty automat. Akceptuje tylko słowo puste
     * oraz słowo "one".
     */
    public final void testGetDotGraph0EasyAutomaton() {
        AutomatonSpecification testAutomaton = new NotNaiveAutomatonSpecification();
        State qBegin = testAutomaton.addState();
        State qEnd = testAutomaton.addTransitionSequence(qBegin, "one");
        State qMore = testAutomaton.addTransition(qEnd, new CharTransitionLabel('1'));
        testAutomaton.addTransition(qEnd, qMore, new CharTransitionLabel('2'));
        testAutomaton.markAsInitial(qBegin);
        testAutomaton.markAsFinal(qBegin);
        testAutomaton.markAsFinal(qEnd);

        //Tworzenie obu Stringów: przez toDotGraph() i "kłamliwą" funkcję
        String dotGraph = testAutomaton.getDotGraph();
        FakeDotGraphGenerator exampleOfDotGraph = new FakeDotGraphGenerator("0 1 2 3 4", "0-o-1"
                + " 1-n-2 2-e-3 3-1,2-4", "0", "0 3", true);
        assertTrue(exampleOfDotGraph.isItProper(dotGraph));
    }

    /**
     * Drugi test metody getDotGraph(). Ze wszystkich słów nad alfabetem {a,b,c} zawierających
     * parzystą liczbę wystąpień podciągu "ab" i w których liczba wystąpień litery c jest
     * podzielna przez trzy.
     */
    public final void testGetDotGraph1BigAutomaton() {
        AutomatonSpecification testAutomaton = new NotNaiveAutomatonSpecification();
        State qBegin = testAutomaton.addState();
        State qCMod3Is1 = testAutomaton.addTransition(qBegin, new CharTransitionLabel('c'));
        State qCMod3Is2 = testAutomaton.addTransition(qCMod3Is1, new CharTransitionLabel('c'));
        State qAC0 = testAutomaton.addTransition(qBegin, new CharTransitionLabel('a'));
        State qACMod3Is1 = testAutomaton.addTransition(qCMod3Is1, new CharTransitionLabel('a'));
        State qACMod3Is2 = testAutomaton.addTransition(qCMod3Is2, new CharTransitionLabel('a'));
        State qNpC0 = testAutomaton.addTransition(qAC0, new CharTransitionLabel('b'));
        State qNpCMod3Is1 = testAutomaton.addTransition(qNpC0, new CharTransitionLabel('c'));
        State qNpCMod3Is2 = testAutomaton.addTransition(qNpCMod3Is1, new CharTransitionLabel('c'));
        State qNpAC0 = testAutomaton.addTransition(qNpC0, new CharTransitionLabel('a'));
        State qNpACMod3Is1 = testAutomaton.addTransition(qNpCMod3Is1, new CharTransitionLabel('a'));
        State qNpACMod3Is2 = testAutomaton.addTransition(qNpCMod3Is2, new CharTransitionLabel('a'));
        testAutomaton.addLoop(qAC0, new CharTransitionLabel('a'));
        testAutomaton.addLoop(qACMod3Is1, new CharTransitionLabel('a'));
        testAutomaton.addLoop(qACMod3Is2, new CharTransitionLabel('a'));
        testAutomaton.addLoop(qNpAC0, new CharTransitionLabel('a'));
        testAutomaton.addLoop(qNpACMod3Is1, new CharTransitionLabel('a'));
        testAutomaton.addLoop(qNpACMod3Is2, new CharTransitionLabel('a'));
        testAutomaton.addLoop(qBegin, new CharTransitionLabel('b'));
        testAutomaton.addLoop(qCMod3Is1, new CharTransitionLabel('b'));
        testAutomaton.addLoop(qCMod3Is2, new CharTransitionLabel('b'));
        testAutomaton.addLoop(qNpC0, new CharTransitionLabel('b'));
        testAutomaton.addLoop(qNpCMod3Is1, new CharTransitionLabel('b'));
        testAutomaton.addLoop(qNpCMod3Is2, new CharTransitionLabel('b'));
        testAutomaton.addTransition(qACMod3Is1, qNpCMod3Is1, new CharTransitionLabel('b'));
        testAutomaton.addTransition(qACMod3Is2, qNpCMod3Is2, new CharTransitionLabel('b'));
        testAutomaton.addTransition(qNpAC0, qBegin, new CharTransitionLabel('b'));
        testAutomaton.addTransition(qNpACMod3Is1, qCMod3Is1, new CharTransitionLabel('b'));
        testAutomaton.addTransition(qNpACMod3Is2, qCMod3Is2, new CharTransitionLabel('b'));
        testAutomaton.addTransition(qCMod3Is2, qBegin, new CharTransitionLabel('c'));
        testAutomaton.addTransition(qAC0, qCMod3Is1, new CharTransitionLabel('c'));
        testAutomaton.addTransition(qACMod3Is1, qCMod3Is2, new CharTransitionLabel('c'));
        testAutomaton.addTransition(qACMod3Is2, qBegin, new CharTransitionLabel('c'));
        testAutomaton.addTransition(qNpCMod3Is2, qNpC0, new CharTransitionLabel('c'));
        testAutomaton.addTransition(qNpAC0, qNpCMod3Is1, new CharTransitionLabel('c'));
        testAutomaton.addTransition(qNpACMod3Is1, qNpCMod3Is2, new CharTransitionLabel('c'));
        testAutomaton.addTransition(qNpACMod3Is2, qNpC0, new CharTransitionLabel('c'));
        testAutomaton.markAsInitial(qBegin);
        testAutomaton.markAsFinal(qBegin);

        //Tworzenie obu Stringów: przez toDotGraph() i "kłamliwą" funkcję
        String dotGraph = testAutomaton.getDotGraph();
        FakeDotGraphGenerator exampleOfDotGraph = new FakeDotGraphGenerator("0 1 2 3 4 5 6 7 8 9"
                + " 10 11", "0-a-3 0-b-0 0-c-1 1-a-4 1-b-1 1-c-2 2-a-5 2-b-2 2-c-0 3-a-3 3-b-6"
                + " 3-c-1 4-a-4 4-b-7 4-c-2 5-a-5 5-b-8 5-c-0 6-a-9 6-b-6 6-c-7 7-a-10 7-b-7"
                + " 7-c-8 8-a-11 8-b-8 8-c-6 9-a-9 9-b-0 9-c-7 10-a-10 10-b-1 10-c-8 11-a-11"
                + " 11-b-2 11-c-6", "0", "0", true);
        assertTrue(exampleOfDotGraph.isItProper(dotGraph));
    }

    /**
     * Pierwszy test metody getDotGraph(). Automat sprawdzający poprawność działania funkcji
     * dla automatów zawierających przejścia po dowolnym znaku.
     */
    public final void testGetDotGraph2AutomatonWithAnyTransitionLabel() {
        AutomatonSpecification testAutomaton = new NaiveAutomatonSpecification();
        State qBegin = testAutomaton.addState();
        State qEnd = testAutomaton.addTransition(qBegin, new AnyTransitionLabel());
        testAutomaton.markAsInitial(qBegin);
        testAutomaton.markAsFinal(qEnd);

        //Tworzenie obu Stringów: przez toDotGraph() i "kłamliwą" funkcję
        String dotGraph = testAutomaton.getDotGraph();
        FakeDotGraphGenerator exampleOfDotGraph = new FakeDotGraphGenerator("0 1", "0-ANY-1", "0",
                "1", false);
        assertTrue(exampleOfDotGraph.isItProper(dotGraph));
    }

    /**
     * Testuje działanie metody testPrefixChecker().
     */
    public final void testPrefixChecker() {
        AutomatonSpecification spec = new NaiveAutomatonSpecification();
        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();
        State q3 = spec.addState();
        State q4 = spec.addState();
        State q5 = spec.addState();

        spec.markAsInitial(q0);
        spec.markAsFinal(q3);

        spec.addTransition(q0, q1, new CharTransitionLabel('a'));
        spec.addTransition(q0, q1, new CharTransitionLabel('b'));
        spec.addTransition(q0, q3, new CharTransitionLabel('c'));

        spec.addTransition(q1, q3, new CharTransitionLabel('a'));
        spec.addTransition(q1, q2, new CharTransitionLabel('b'));
        spec.addTransition(q1, q2, new CharTransitionLabel('c'));

        spec.addTransition(q2, q3, new CharTransitionLabel('a'));
        spec.addLoop(q2, new CharTransitionLabel('b'));
        spec.addTransition(q2, q5, new CharTransitionLabel('c'));

        spec.addTransition(q3, q0, new CharTransitionLabel('a'));
        spec.addTransition(q3, q0, new CharTransitionLabel('b'));
        spec.addTransition(q3, q4, new CharTransitionLabel('c'));

        // Stan 4 jest pułapką

        spec.addLoop(q4, new CharTransitionLabel('a'));
        spec.addLoop(q4, new CharTransitionLabel('b'));
        spec.addLoop(q4, new CharTransitionLabel('c'));

        // Stan 5 prowadzi tylko do stanu 4

        spec.addLoop(q5, new CharTransitionLabel('a'));
        spec.addTransition(q5, q4, new CharTransitionLabel('b'));
        spec.addLoop(q5, new CharTransitionLabel('c'));

        assertTrue(spec.prefixChecker(q0));
        assertTrue(spec.prefixChecker(q1));
        assertTrue(spec.prefixChecker(q2));
        assertTrue(spec.prefixChecker(q3));
        assertFalse(spec.prefixChecker(q4));
        assertFalse(spec.prefixChecker(q5));
    }
    /**
     * Testuje działanie metody checkPrefix().
     * Bazuje bezpośrednio na teście metody prefixChecker()
     */
    public final void testCheckPrefix() {
        AutomatonSpecification spec = new NaiveAutomatonSpecification();
        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();
        State q3 = spec.addState();
        State q4 = spec.addState();
        State q5 = spec.addState();
        State q6 = spec.addState();

        spec.markAsInitial(q0);
        spec.markAsFinal(q3);

        spec.addTransition(q0, q1, new CharTransitionLabel('a'));
        spec.addTransition(q0, q1, new CharTransitionLabel('b'));
        spec.addTransition(q0, q3, new CharTransitionLabel('c'));
        spec.addTransition(q0, q6, new EpsilonTransitionLabel());

        spec.addTransition(q1, q3, new CharTransitionLabel('a'));
        spec.addTransition(q1, q2, new CharTransitionLabel('b'));
        spec.addTransition(q1, q2, new CharTransitionLabel('c'));

        spec.addTransition(q2, q3, new CharTransitionLabel('a'));
        spec.addLoop(q2, new CharTransitionLabel('b'));
        spec.addTransition(q2, q5, new CharTransitionLabel('c'));

        spec.addTransition(q3, q0, new CharTransitionLabel('a'));
        spec.addTransition(q3, q0, new CharTransitionLabel('b'));
        spec.addTransition(q3, q4, new CharTransitionLabel('c'));

        // Stan 4 jest pułapką

        spec.addLoop(q4, new CharTransitionLabel('a'));
        spec.addLoop(q4, new CharTransitionLabel('b'));
        spec.addLoop(q4, new CharTransitionLabel('c'));

        // Stan 5 prowadzi tylko do stanu 4

        spec.addLoop(q5, new CharTransitionLabel('a'));
        spec.addTransition(q5, q4, new CharTransitionLabel('b'));
        spec.addLoop(q5, new CharTransitionLabel('c'));

        spec.addTransition(q6, q3, new CharTransitionLabel('c'));

        assertTrue(spec.checkPrefix("abbb"));
        assertTrue(spec.checkPrefix("cacbab"));
        assertTrue(spec.checkPrefix("bbaabcaa"));
        assertTrue(spec.checkPrefix("cababbc"));
        assertTrue(spec.checkPrefix("c"));
        assertTrue(spec.checkPrefix("cbcbcbcbcbacbbbbaab"));

        assertFalse(spec.checkPrefix("aacc"));
        assertFalse(spec.checkPrefix("sdfcs"));
        assertFalse(spec.checkPrefix("bcca"));
        assertFalse(spec.checkPrefix("cc"));
        assertFalse(spec.checkPrefix("abaabacac"));
        assertFalse(spec.checkPrefix("caccb"));
        assertFalse(spec.checkPrefix("bac"));
    }

    /**
     *test automatu pustego.
     */
    public final void testIsEmpty() {
        AutomatonSpecification automat = new NaiveAutomatonSpecification();
        assertTrue(automat.isEmpty());
    }
    /**
     *test automatu niepustego.
     */
    public final void testIsEmptyforNotEmpty() {
        AutomatonSpecification automat = new NaiveAutomatonSpecification();
        State s1 = automat.addState();
        assertFalse(automat.isEmpty());
    }

    /**
     * Test wariacji metody addTransition, w której tworzyony jest nowy stan.
     */
    public final void testAddTransitionWithAddingState() {
        AutomatonSpecification automat = new NaiveAutomatonSpecification();
        State s0 = automat.addState();

        //Sprawdzam ilość stanów oraz przejść w automacie przed zastosowaniem metody.
        int numberOfStates1 = automat.countStates();
        int numberOfTransitions1 = automat.countTransitions();

        //Dodaję nowy stan i przejście używając metody addTransition i sprawdzam
        //ponownie ilość stanów oraz przejść.
        State s1 = automat.addTransition(s0, new CharTransitionLabel('a'));
        int numberOfStates2 = automat.countStates();
        int numberOfTransitions2 = automat.countTransitions();

        //Sprawdzam, czy ilości stanów oraz przejsć nie są równe oraz czy ze stanu
        //s0 da sie przejść do stanu s1.
        assertFalse(numberOfStates1 == numberOfStates2);
        assertFalse(numberOfTransitions1 == numberOfTransitions2);
        assertTrue(automat.allOutgoingTransitions(s0).get(0).getTargetState() == s1);
    }

    /**
     * test metody insert: testuje zwykłe użycie.
     */
    public final void testInsertSimpleTest() {
        AutomatonSpecification firstAutomaton = new NaiveAutomatonSpecification();
        State firstState = firstAutomaton.addState();
        firstAutomaton.markAsInitial(firstState);
        AutomatonSpecification second = new NaiveAutomatonSpecification();
        String fromString = "Automaton:\n-States: q0 q1 q2 q3 q4 \n-Transitions:\n  q0 -a-> "
            + "q1\n  q1 -a-> q0\n  q2 -epsilon-> q4"
            + "\n  q3 -ANY-> q4\n-Initial state: q0\n-Final states: q1 ";
        try {
            second.fromString(fromString);
        } catch (Exception e) {
            fail("Nie udało się stworzyć automatu.");
        }

        firstAutomaton.insert(firstState, second);
        assertTrue(fromString.equals(firstAutomaton.toString()));
    }

    /**
     * test funkcji insert: dodaje do automatu pusty automat.
     */
    public final void testInsertEmptyAutomaton() {
        AutomatonSpecification base =  new NaiveAutomatonSpecification();
        State s1 = base.addState();
        State s2 = base.addState();
        State s3 = base.addState();
        base.markAsInitial(s1);
        base.markAsFinal(s1);
        base.markAsFinal(s3);
        base.addTransition(s1, s3, new CharTransitionLabel('a'));
        base.addTransition(s2, s1, new CharTransitionLabel('b'));
        base.addTransition(s2, s1, new CharTransitionLabel('c'));
        AutomatonSpecification empty = new NaiveAutomatonSpecification();
        String automatonString = base.toString();
        base.insert(s2, empty);
        assertEquals(automatonString, base.toString());
    }

    /**
     * testuje metodę insert, ale dodaje automat bez jakichkolwiek przejść.
     */
    public final void testInsertNoTransitions() {
        final int automatonSize = 300;
        AutomatonSpecification base = new NaiveAutomatonSpecification();
        for (int iter = 0; iter < 99; ++iter) {
            base.addState();
        }
        AutomatonSpecification secondAutomaton = new NaiveAutomatonSpecification();
        State initial = secondAutomaton.addState();
        secondAutomaton.markAsInitial(initial);
        for (int iter = 0; iter < 201; ++iter) {
            secondAutomaton.addState();
        }
        base.insert(initial, secondAutomaton);
        assertEquals(base.countTransitions(), 0);
        assertEquals(base.countStates(), automatonSize);
    }

    /**
     * Testuje działanie metody clone(). Test 1.
     */
    public final void testCloneMiniAutomaton() {

        AutomatonSpecification mini = new NaiveAutomatonSpecification();

        State q0 = mini.addState();
        State q1 = mini.addState();
        State q2 = mini.addState();

        mini.markAsInitial(q0);
        mini.markAsFinal(q2);

        mini.addTransition(q0, q1, new CharTransitionLabel('a'));
        mini.addTransition(q1, q2, new CharTransitionLabel('b'));

        AutomatonSpecification clon = mini.clone();

        assertEquals(mini.countStates(), clon.countStates());
        assertEquals(mini.countTransitions(), clon.countTransitions());

        AutomatonByStack mini2 = new AutomatonByStack(mini);
        AutomatonByStack clon2 = new AutomatonByStack(clon);

        assertEquals(clon2.accepts("ab"), mini2.accepts("ab"));
        assertEquals(clon2.accepts("aa"), mini2.accepts("aa"));
        assertEquals(clon2.accepts(""), mini2.accepts(""));
    }

    /**
     * Testuje działanie metody clone(). Test 2.
     */
    public final void testCloneMini2Automaton() {

        AutomatonSpecification mini = new NaiveAutomatonSpecification();

        State q0 = mini.addState();
        State q1 = mini.addState();

        mini.markAsInitial(q0);
        mini.markAsFinal(q1);

        mini.addTransition(q0, q1, new CharTransitionLabel('a'));
        mini.addLoop(q1, new CharTransitionLabel('a'));
        mini.addLoop(q1, new CharTransitionLabel('b'));

        AutomatonSpecification clon = mini.clone();

        assertEquals(mini.countStates(), clon.countStates());
        assertEquals(mini.countTransitions(), clon.countTransitions());

        AutomatonByStack mini2 = new AutomatonByStack(mini);
        AutomatonByStack clon2 = new AutomatonByStack(clon);

        assertEquals(clon2.accepts("a"), mini2.accepts("a"));
        assertEquals(clon2.accepts("aa"), mini2.accepts("aa"));
        assertEquals(clon2.accepts("aaa"), mini2.accepts("aaa"));
        assertEquals(clon2.accepts("ab"), mini2.accepts("ab"));
        assertEquals(clon2.accepts("aab"), mini2.accepts("aab"));
        assertEquals(clon2.accepts("aba"), mini2.accepts("aba"));
        assertEquals(clon2.accepts("ababa"), mini2.accepts("ababa"));

        assertEquals(clon2.accepts(""), mini2.accepts(""));
        assertEquals(clon2.accepts("b"), mini2.accepts("b"));
        assertEquals(clon2.accepts("bbba"), mini2.accepts("bbba"));
        assertEquals(clon2.accepts("cos"), mini2.accepts("cos"));
    }

    /**
     * Testuje działanie metody clone(). Test 3.
     */
    public final void testCloneMini3Automaton() {

        AutomatonSpecification mini = new NaiveAutomatonSpecification();

        State q0 = mini.addState();
        State q1 = mini.addState();
        State q2 = mini.addState();
        State q3 = mini.addState();

        mini.addTransition(q0, q1, new CharTransitionLabel('a'));
        mini.addTransition(q1, q2, new CharTransitionLabel('b'));
        mini.addTransition(q1, q3, new CharTransitionLabel('b'));

        mini.markAsInitial(q0);
        mini.markAsFinal(q2);

        AutomatonSpecification clon = mini.clone();

        assertEquals(mini.countStates(), clon.countStates());
        assertEquals(mini.countTransitions(), clon.countTransitions());

        AutomatonByStack mini2 = new AutomatonByStack(mini);
        AutomatonByStack clon2 = new AutomatonByStack(clon);

        assertEquals(clon2.accepts("ab"), mini2.accepts("ab"));
        assertEquals(clon2.accepts("bb"), mini2.accepts("bb"));
    }

    /**
     * Testuje działanie metody clone(). Test 4.
     */
    public final void testCloneMini4Automaton() {

    AutomatonSpecification mini = new NaiveAutomatonSpecification();

        State q0 = mini.addState();
        State q1 = mini.addState();
        State q2 = mini.addState();
        State q3 = mini.addState();

        mini.addTransition(q0, q1, new CharTransitionLabel('a'));
        mini.addTransition(q1, q0, new CharTransitionLabel('b'));
        mini.addTransition(q1, q2, new CharTransitionLabel('c'));
        mini.addTransition(q2, q3, new CharTransitionLabel('a'));
        mini.addTransition(q3, q2, new CharTransitionLabel('b'));

        mini.markAsInitial(q0);
        mini.markAsFinal(q3);

        AutomatonSpecification clon = mini.clone();

        assertEquals(mini.countStates(), clon.countStates());
        assertEquals(mini.countTransitions(), clon.countTransitions());

        AutomatonByStack mini2 = new AutomatonByStack(mini);
        AutomatonByStack clon2 = new AutomatonByStack(clon);

        assertEquals(clon2.accepts("aca"), mini2.accepts("aca"));
        assertEquals(clon2.accepts("bc"), mini2.accepts("bc"));
        assertEquals(clon2.accepts("bbc"), mini2.accepts("bbc"));
        assertEquals(clon2.accepts("acabababa"), mini2.accepts("acabababa"));
        assertEquals(clon2.accepts(""), mini2.accepts(""));
        assertEquals(clon2.accepts("cc"), mini2.accepts("cc"));
        assertEquals(clon2.accepts("bca"), mini2.accepts("bca"));
        assertEquals(clon2.accepts("acc"), mini2.accepts("acc"));
    }

    /**
     * Prosty test metody getEpsilonClosure(State).
     */
    public final void testSimpleGetEpsilonClosure() {
        // Automat z tylko jednym stanem (początkowy i końcowy).
        // Tylko jedne możliwe przejście (czytanie znaku pustego
        // i wracanie na ten sam stan).
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();
        // Nowy automat ma już stan początkowy i ustawiamy przejście.
        automat.addTransition(automat.getInitialState(),
                automat.getInitialState(),
                new EpsilonTransitionLabel());
        // Zaznaczamy stan początkowy jako końcowy.
        automat.markAsFinal(automat.getInitialState());
        Set<State> zbior = new HashSet<State>();
        zbior.add(automat.getInitialState());
        assertEquals(zbior,
                automat.getEpsilonClosure(automat.getInitialState()));
    }

    /**
     * Trudniejszy test metody getEpsilonClosure(State).
     */
    public final void testHardGetEpsilonClosure() {
        // Prosty automat z czterema stanami.
        NaiveAutomatonSpecification automat = new NaiveAutomatonSpecification();
        State s0, s1, s2, s3;
        s0 = automat.getInitialState();
        s1 = automat.addState();
        s2 = automat.addState();
        s3 = automat.addState();
        automat.markAsFinal(s3);

        // Dodajmy jakieś "normalne" przejścia.
        automat.addTransition(s0, s1, new CharTransitionLabel('a'));
        automat.addTransition(s0, s2, new CharTransitionLabel('b'));
        automat.addTransition(s1, s3, new CharTransitionLabel('a'));
        automat.addTransition(s1, s3, new CharTransitionLabel('b'));
        automat.addTransition(s2, s1, new CharTransitionLabel('b'));

        // Dodajemy epsilon przejścia.
        automat.addTransition(s2, s3, new EpsilonTransitionLabel());
        automat.addTransition(s3, s1, new EpsilonTransitionLabel());
        automat.addTransition(s3, s0, new EpsilonTransitionLabel());
        automat.addTransition(s0, s3, new EpsilonTransitionLabel());

        Set<State> zbior = new HashSet<State>();
        zbior.add(s1);
        assertEquals(zbior, automat.getEpsilonClosure(s1));

        zbior = automat.getEpsilonClosure(s1);
        zbior.add(s0);
        zbior.add(s1);
        zbior.add(s2);
        zbior.add(s3);
        assertEquals(zbior, automat.getEpsilonClosure(s2));

        // Tu jest pętla.
        zbior = new HashSet<State>();
        zbior.add(s0);
        zbior.add(s1);
        zbior.add(s3);
        assertEquals(zbior, automat.getEpsilonClosure(s3));
    }
}
