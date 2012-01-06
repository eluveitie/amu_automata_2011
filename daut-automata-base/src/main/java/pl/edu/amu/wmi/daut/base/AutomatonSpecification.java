package pl.edu.amu.wmi.daut.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

/**
 * Klasa abstrakcyjna reprezentująca specyfikację (opis) automatu
 * (jakie są stany, przejścia, który stan jest stanem początkowym,
 * które stany są stanami akceptującymi).
 *
 * Uwaga: klasa ta nie reprezentuje działającego automatu (nie ma tu funkcji
 * odpowiadających na pytanie, czy automat akceptuje napis, czy nie),
 * tylko "zawartość" automatu.
 */
public abstract class AutomatonSpecification implements Cloneable  {

    // metody "budujące" automat
    /**
     * Dodaje nowy stan do automatu.
     *
     * Zwraca dodany stan.
     */
    public abstract State addState();

    /**
     * Dodaje przejście od stanu 'from' do stanu 'to' etykietowane etykietą transitionLabel.
     */
    public abstract void addTransition(State from, State to, TransitionLabel transitionLabel);
    /**
     * Dodaje przejście od stanu 'from' do nowo utworzonego stanu 'to' etykietowane etykietą
     * transitionLabel, a następnie zwraca utworzony stan.
     */
    public State addTransition(State from, TransitionLabel transitionLabel) {

        State to = addState();
        addTransition(from, to, transitionLabel);

        return to;
    }

    /**
     * Dla zadanego słowa dodaje stany i przejścia.
     *
     * Zwraca stan końcowy.
     */
    public State addTransitionSequence(State from, String text) {
        State prev = from;
        State next = prev;

        for (int i = 0; i < text.length(); ++i) {
            prev = addTransition(next,
                    new CharTransitionLabel(text.charAt(i)));
            next = prev;
        }
       return prev;
    }

    /**
     * Tworzy "gałąź" w automacie.
     * Metoda dodaje ciąg przejść od stanu początkowego automatu,
     * dla podanej listy etykiet przejść.
     * Metoda zwraca (nowo utworzony) stan docelowy ostatniego przejścia.
     */
    public State addBranch(State from, List<TransitionLabel> oTransition) {
        State prev = from;
        State next = prev;

        for (TransitionLabel transition : oTransition) {
            prev = addTransition(next, transition);
            next = prev;
        }
        return prev;
    }

    /**
     * Oznacza stan jako początkowy.
     */
    public abstract void markAsInitial(State state);

    /**
     * Oznacza stan jako końcowy (akceptujący).
     */
    public abstract void markAsFinal(State state);

    /**
     * Odznacza stan końcowy.
     */
    public abstract void unmarkAsFinalState(State state);

    // metody zwracające informacje o automacie
    /**
     * Zwraca listę wszystkich stanów.
     *
     * Stany niekoniecznie muszą być zwrócone w identycznej
     * kolejności jak były dodane.
     */
    public abstract List<State> allStates();

    /**
     * Zwraca listę wszystkich przejść wychodzących ze stanu 'from'.
     *
     * Przejścia niekoniecznie muszą być zwrócone w identycznej
     * kolejności jak były dodane.
     */
    public abstract List<OutgoingTransition> allOutgoingTransitions(State from);

    /**
     * Zwraca stan początkowy.
     */
    public abstract State getInitialState();

    /**
     * Zwraca true wgdy stan jest stanem końcowym.
     */
    public abstract boolean isFinal(State state);

    /**
     * Metoda sprawdza czy automat jest pusty.
     */
    public boolean isEmpty() {

        List<State> states = allStates();
        if (states.isEmpty())
            return true;
        return false;
    }

    /**
     * Zwraca zawartość automatu w czytelnej dla człowieka postaci String'a.
     * @return String
     */
    @Override
    public String toString() {
        StringBuffer retString = new StringBuffer("Automaton:\n-States: ");

        //Dopisanie stanów z automatu
        List<State> listOfStates = allStates();
        for (int i = 0; i < listOfStates.size(); i++) {
            retString.append("q" + i + " ");
        }

        //Dopisanie przejść z automatu
        retString.append("\n-Transitions:\n");
        for (int i = 0; i < listOfStates.size(); i++) {
            List<OutgoingTransition> listOfTrans = allOutgoingTransitions(listOfStates.get(i));
            for (int j = 0; j < listOfTrans.size(); j++) {
                retString.append("  q" + i + " -" + listOfTrans.get(j).getTransitionLabel()
                        + "-> q");
                State target = listOfTrans.get(j).getTargetState();
                for (int m = 0; m < listOfStates.size(); m++) {
                    if (target == listOfStates.get(m)) {
                        retString.append(m);
                        break;
                    }
                }
                retString.append("\n");
            }
        }

        //Dopisanie stanu początkowego automatu
        retString.append("-Initial state: ");
        for (int i = 0; i < listOfStates.size(); i++) {
            if (listOfStates.get(i) == getInitialState()) {
                retString.append("q" + i);
                break;
            }
        }

        //Dopisanie stanów końcowych automatu
        retString.append("\n-Final states: ");
        for (int i = 0; i < listOfStates.size(); i++) {
            if (isFinal(listOfStates.get(i))) {
                retString.append("q" + i + " ");
            }
        }

        //Zwrócenie wyniku
        return retString.toString();
    }


   /**
     * Sprawdza, czy język akceptowany przez automat jest niepusty.
     */
    public boolean isNotEmpty() {
        List<State> states = allStates();

        if (states.isEmpty())
            return false;
        else {
            int counter = 0;
            boolean isThereNoInitialState = false;
            for (State s : allStates()) {
                if (this.isFinal(s))
                    counter++;
            }
            if (this.getInitialState() == null)
                isThereNoInitialState = true;
            if (counter == 0 || isThereNoInitialState)
                return false;
            else {
                Stack<Integer> stateStack = new Stack<Integer>();
                Stack<Integer> transitionStack = new Stack<Integer>();
                Stack<Integer> branchStack = new Stack<Integer>();

                Integer state = states.indexOf(this.getInitialState());
                Integer transition = 0;
                Integer branches = this.allOutgoingTransitions(states.get(state)).size();

                stateStack.push(state);
                transitionStack.push(transition);

                do {
                    state = stateStack.pop();
                    transition = transitionStack.pop();

                    if (this.isFinal(states.get(state)))
                        return true;

                    if (transition < branches) {
                        stateStack.push(state);
                        transitionStack.push(transition++);
                        branchStack.push(branches);
                        State stateForWhile = this.allOutgoingTransitions(states.get(state))
                                .get(transition).getTargetState();
                        branchStack.push(this.allOutgoingTransitions(stateForWhile).size());
                        stateStack.push(states.indexOf(stateForWhile));
                        transitionStack.push(0);
                    }
                } while (!stateStack.empty());
            }
        }
        return false;
    }

   /**
     * Sprawdza, czy automat jest deterministyczny (to znaczy, czy ma
     * przynajmniej jeden stan, czy nie zawiera epsilon-przejść (za wyjątkiem
     * sytuacji, gdy epsilon-przejście jest jedynym sposobem wyjścia ze stanu)
     * oraz czy przejścia z danego stanu do innych stanów odbywają się po
     * różnych znakach).
     */
    public boolean isDeterministic() {
        List<State> states = allStates();

        if (states.isEmpty())
            return false;

        for (State state : states) {
            List<OutgoingTransition> transitions = allOutgoingTransitions(state);

            if (transitions.size() <= 1)
                continue;

            for (int i = 0; i < transitions.size(); ++i) {
                TransitionLabel label = transitions.get(i).getTransitionLabel();

                if (label.canBeEpsilon())
                    return false;

                for (int j = i + 1; j < transitions.size(); ++j) {
                    TransitionLabel label2 = transitions.get(j).getTransitionLabel();
                    if (!label2.intersect(label).isEmpty())
                        return false;
                }
            }
        }

        return true;
    }

    /**
     * Dodaje przejście od stanu state z powrotem do tego samego stanu
     * po etykiecie transitionLabel.
     */
    public void addLoop(State state, TransitionLabel transitionLabel) {

        addTransition(state, state, transitionLabel);
    }

    /**
     * Zwraca obiekt typu String, który zawiera gotowy kod w języku DOT służący do
     * przedstawienia automatu w formie graficznej, (w ubuntu pakiet
     * graphviz). Z konsoli wywołuje się przykładowo w następujący sposób: dot
     * -Tpng -O plik_zkodem.dot który tworzy plik-schemat zapisany w formacie
     * png. Więcej w: man dot.
     *
     * @return Kod źródłowy schematu w języku DOT.
     */
    public String getDotGraph() {

        class DotGraph {
            private StringBuffer dotCode;
            private List<State> states;

            public DotGraph() {
                dotCode = new StringBuffer();
                states = allStates();
            }

            private void getDotGraphIntro() {
                dotCode.append(
                        "digraph finite_state_machine {\n"
                         + "    rankdir=LR;\n"
                         + "    size=\"8,5\"\n"
                         + "    node [style=filled fillcolor=\"#00ff005f\" shape = ");
                if (isFinal(getInitialState())) dotCode.append("double");
                dotCode.append("circle];\n"
                               + "    \"State #" + states.indexOf(getInitialState()) + "\";\n"
                               + "    node [shape = doublecircle style=filled "
                               + "fillcolor=\"#00000000\"];\n    ");
            }

            private void getDotGraphFinalStates() {
                for (State it : states) {
                    if (isFinal(it)) {
                        dotCode.append("\"State #" + states.indexOf(it) + "\" ");
                    }
                }
            }

            private void getEdgeLabel(State state, int target, String label) {
                if (label.length() != 0) {
                    dotCode.append("    \"State #");
                    dotCode.append(states.indexOf(state) + "\"");
                    dotCode.append(" -> ");
                    dotCode.append("\"State #");
                    dotCode.append(target + "\"");
                    dotCode.append(" [ label = \"" + label
                            + "\" ]");
                    dotCode.append(";\n");
                }

            }

            private void getDotGraphEdges() {
                for (State it : states) {
                    final StringBuffer[] labelList = new StringBuffer[states.size()];
                    for (int i = 0; i < labelList.length; ++i) {
                        labelList[i] = new StringBuffer();
                    }

                    final List<OutgoingTransition> edges = allOutgoingTransitions(it);

                    for (OutgoingTransition edgeIt : edges) {
                        if (labelList[states.indexOf(edgeIt.getTargetState())].length() == 0) {
                            labelList[states.indexOf(edgeIt.getTargetState())]
                                    .append(edgeIt.getTransitionLabel());
                        } else {
                            labelList[states.indexOf(edgeIt.getTargetState())]
                                    .append(", " + edgeIt.getTransitionLabel());
                        }
                    }

                    for (int i = 0; i < labelList.length; ++i) {
                        getEdgeLabel(it, i, labelList[i].toString());
                    }
                }
            }

            public String  getDotGraph() {
                getDotGraphIntro();
                getDotGraphFinalStates();
                dotCode.append(";\n" + "    node [shape = circle];\n" + "");
                getDotGraphEdges();
                dotCode.append("\n}\n");
                return dotCode.toString();
            }
        }

        DotGraph tmp = new DotGraph();
        return tmp.getDotGraph();
    }

    /**
     * Zwraca liczbę stanów.
     */
    public int countStates() {
        return allStates().size();
    }

    /**
     * Zwraca liczbę przejść.
     */
    public int countTransitions() {
        int sum = 0;
        for (State state : allStates()) {
            sum += allOutgoingTransitions(state).size();
        }
        return sum;
    }

    /**
     * Wstawia począwszy od stanu state kopię automatu automaton.
     * Stan state będzie utożsamiony ze stanem
     * początkowym automatu automaton.
     */
    public void insert(State state, AutomatonSpecification automaton) {
        List<State> loadedStates = automaton.allStates();
        HashMap<State, State> connectedStates = new HashMap<State, State>();
        State automatonInitialState = automaton.getInitialState();
        for (State currentState : loadedStates) {
            if (currentState == automatonInitialState)
                connectedStates.put(currentState, state);
            else
                connectedStates.put(currentState, this.addState());
        }
        for (State currentState : loadedStates) {
            if (automaton.isFinal(currentState))
                markAsFinal(connectedStates.get(currentState));
            List<OutgoingTransition> list = automaton
                    .allOutgoingTransitions(currentState);
            for (OutgoingTransition transition : list) {
                this.addTransition(connectedStates.get(currentState),
                        connectedStates.get(transition.getTargetState()),
                        transition.getTransitionLabel());
            }
        }
    }

    /**
     * Funkcja zmieniająca pusty automat na automat akceptujący wyłącznie napis
     * pusty.
     */
    public AutomatonSpecification makeEmptyStringAutomaton() {
        State emptyState = this.addState();
        this.markAsInitial(emptyState);
        this.markAsFinal(emptyState);

        return this;
    }

    /**
     * Sprawdza czy dla danego stanu i znaku istnieje przejście.
     */
    private boolean doesTransitionExist(State state, char c) {

        for (OutgoingTransition transition1 : allOutgoingTransitions(state)) {

            if (transition1.getTransitionLabel().canAcceptCharacter(c))
                return true;
        }

        return false;
    }

    /**
     * Sprawdza, czy dla każdego stanu i dla każdego znaku z alfabetu
     * istnieje przejście.
     */
    public boolean isFull(String alphabet) {

        if (allStates().isEmpty())
            return false;

        for (State state : allStates()) {

            if (allOutgoingTransitions(state).isEmpty())
                    return false;

            for (int i = 0; i < alphabet.length(); i++) {
                if (!doesTransitionExist(state, alphabet.charAt(i)))
                    return false;
            }
        }

        return true;
    }

    /**
     * Dopełnia automat tak, aby isFull zwracało prawdę.
     */
    public void makeFull(String alphabet) {

        State trash = null;

        if (this.isEmpty()) {
            trash = addState();
            for (int i = 0; i < alphabet.length(); i++)
                addLoop(trash, new CharTransitionLabel(
                        alphabet.charAt(i)));
            return;
        }

        for (State state : new ArrayList<State>(allStates())) {

            for (int i = 0; i < alphabet.length(); i++) {

                if (allOutgoingTransitions(state).isEmpty()) {

                    if (trash == null)
                        trash = addState();

                    addTransition(state, trash, new CharTransitionLabel(
                            alphabet.charAt(i)));
                }

                if (!doesTransitionExist(state, alphabet.charAt(i))) {

                    if (trash == null)
                        trash = addState();

                    addTransition(state, trash, new CharTransitionLabel(
                            alphabet.charAt(i)));
                }
            }
        }

        if (trash != null) {

            for (int i = 0; i < alphabet.length(); i++)
                addLoop(trash, new CharTransitionLabel(
                        alphabet.charAt(i)));
        }
    }

    /**
     * Sprawdza, czy od stanu state można dojść do stanu końcowego.
     */
    public boolean prefixChecker(State state) {

        if (isFinal(state)) {
            return true;
        }

        List<State> checkedStates = new ArrayList<State>();
        List<OutgoingTransition> outgoing = new ArrayList<OutgoingTransition>();
        State currentState;

        checkedStates.add(state);
        int limit = checkedStates.size();

        for (int i = 0; i < limit; i++) {
            outgoing.clear();
            outgoing = allOutgoingTransitions(checkedStates.get(i));

            for (int j = 0; j < outgoing.size(); j++) {

                currentState = outgoing.get(j).getTargetState();

                if (isFinal(currentState)) {
                        return true;
                }

                if (!checkedStates.contains(currentState)) {
                    checkedStates.add(currentState);
                    limit++;
                }
            }
        }
        return false;
    }

    /**
     * Funkcja tworzaca zawartość automatu ze Stringa.
     */

    void fromString(String automatonDescription) throws StructureException {
        MakeAutomatonFromString graph = new MakeAutomatonFromString(this, automatonDescription);
        graph.make();
    }

    /**
     * Zwraca true, gdy automat akceptuje napis pusty.
     */

    public boolean acceptEmptyWord() {

        List<State> tocheck = new ArrayList<State>();
        List<OutgoingTransition> transitions = new ArrayList<OutgoingTransition>();
        TransitionLabel label;
        State state;

        if (isFinal(getInitialState())) {
            return true;
        }

        tocheck.add(getInitialState());
        int iterator = tocheck.size();

        for (int i = 0; i < iterator; ++i) {
            transitions.clear();
            transitions = allOutgoingTransitions(tocheck.get(i));

            for (OutgoingTransition j : transitions) {
                label = j.getTransitionLabel();
                state = j.getTargetState();

                if (label.canBeEpsilon() && !tocheck.contains(state)) {
                    tocheck.add(state);
                    ++iterator;

                    if (isFinal(state)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Sprawdza, czy w automacie istnieją zbędne stany.
     */
    public boolean uselessStates() {
        boolean flag1 = true;
        boolean flag2 = false;
        State q = getInitialState();
        List<State> stack = new ArrayList<State>();
        List<State> used;
        used = allStates();
        int x = 0;
        while (true) {
            if (flag1) {
                for (int i = 1; i <= allOutgoingTransitions(q).size(); i++) {
                    stack.add(allOutgoingTransitions(q).get(i).getTargetState());
                }
            }
            if (!stack.isEmpty()) {
                flag1 = true;
                q = stack.get(stack.size());
                for (int i = 1; i <= used.size(); i++) {
                    if (used.get(i) == q) {
                        flag2 = true;
                        x = i;
                        break;
                    }
                }
                if (flag2) {
                    used.remove(x);
                    flag2 = false;
                    continue;
                } else {
                    flag1 = false;
                }
            } else {
                break;
            }
        }
        for (int i = 1; i <= used.size(); i++) {
            if (used.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tworzy automat akceptujący napisy nad alfabetem.
     */
    public AutomatonSpecification makeAllStringsAutomaton(String alphabet) {
        State state = addState();
        markAsInitial(state);
        markAsFinal(state);
        for (int i = 0; i < alphabet.length(); i++)
            addLoop(state, new CharTransitionLabel(alphabet.charAt(i)));

        return this;
    }

    /**
     * Tworzy automat akceptujący wszystkie niepuste napisy nad alfabetem.
     */
    public AutomatonSpecification makeAllNonEmptyStringsAutomaton(String alphabet) {
        State s0 = addState();
        State s1 = addState();
        markAsInitial(s0);
        markAsFinal(s1);
        for (int i = 0; i < alphabet.length(); i++) {
            addTransition(s0, s1, new CharTransitionLabel(alphabet.charAt(i)));
            addLoop(s1, new CharTransitionLabel(alphabet.charAt(i)));
        }

        return this;
    }

    /**
     * Sprawdza, czy można przedłużyć word do słowa akceptowanego.
     */
    public boolean checkPrefix(String word) {

        List<State> finalStates = new ArrayList<State>();
        List<State> nowChecking = new ArrayList<State>();
        List<OutgoingTransition> outgoing = new ArrayList<OutgoingTransition>();
        TransitionLabel label;
        State state;

        finalStates.add(getInitialState());

        for (int i = 0; i < word.length(); i++) {
            nowChecking.clear();
            nowChecking.addAll(finalStates);
            finalStates.clear();
            int size = nowChecking.size();

            for (int j = 0; j < size; j++) {
                outgoing.clear();
                outgoing = allOutgoingTransitions(nowChecking.get(j));
                for (int k = 0; k < outgoing.size(); k++) {
                    label = outgoing.get(k).getTransitionLabel();
                    state = outgoing.get(k).getTargetState();

                    if (label.canBeEpsilon() && !nowChecking.contains(state)) {
                        nowChecking.add(state);
                        size++;
                    }
                    boolean term = label.canAcceptCharacter(word.charAt(i));
                    if (term && !finalStates.contains(state)) {
                        finalStates.add(state);
                    }
                }
            }
        }

        int size = finalStates.size();

        for (int j = 0; j < size; j++) {
            outgoing.clear();
            outgoing = allOutgoingTransitions(finalStates.get(j));
            for (int k = 0; k < outgoing.size(); k++) {
                label = outgoing.get(k).getTransitionLabel();
                state = outgoing.get(k).getTargetState();

                if (label.canBeEpsilon() && !finalStates.contains(state)) {
                    finalStates.add(state);
                    size++;
                }
            }
        }

        for (int i = 0; i < finalStates.size(); i++) {
            boolean istrue = prefixChecker(finalStates.get(i));

            if (istrue)
                return true;
        }
        return false;
    }

    /**
     * Klonowanie automatu.
     */
    @Override
    public AutomatonSpecification clone() {
        AutomatonSpecification mini = new NaiveAutomatonSpecification();
        State q = mini.addState();
        mini.markAsInitial(q);
        mini.insert(q, this);
        return mini;
    }

    /**
     * Tworzy automat z jednym przejściem.
     */
    public AutomatonSpecification makeOneLoopAutomaton(char c) {
        State q0 = addState();
        addLoop(q0, new CharTransitionLabel(c));
        markAsInitial(q0);
        markAsFinal(q0);

        return this;
    }

    /**
     * Metoda budująca 2-stanowy automat z jednym przejściem.
     */
    public AutomatonSpecification makeOneTransitionAutomaton(char c) {
        State q0 = addState();
        State q1 = addState();
        addTransition(q0, q1, new CharTransitionLabel(c));
        markAsInitial(q0);
        markAsFinal(q1);

        return this;
    }

    /**
     * Metoda zwracającą wszystkie napisy akceptowane przez automat.
     */
    public AllAcceptedWords returnAllAcceptedWords() {
        AllAcceptedWords words = new AllAcceptedWords(this);
        return words;
    }


    /**
     * Sprawdza, czy akceptowany język jest nieskończony.
     */
    public boolean isInfinite() {
        return checkForLoop(getInitialState(), new ArrayList<State>());
    }

    private boolean checkForLoop(State state, List<State> history) {

        for (State his : history) {
            if (his == state) {
                return findFinals(state, new ArrayList<State>());
            }
        }

        if (allOutgoingTransitions(state).isEmpty())
            return false;

        history.add(state);

        boolean result = false;
        for (OutgoingTransition child : allOutgoingTransitions(state)) {
            List<State> newHistory = new ArrayList<State>();
            for (State s : history)
                newHistory.add(s);
                result = result || checkForLoop(child.getTargetState(), newHistory);
            if (result)
                break;
        }
        return result;
    }

    private boolean findFinals(State state, List<State> history) {
        boolean result = false;

        if (isFinal(state))
            return true;

        if (allOutgoingTransitions(state).size() == 0)
            return false;

        for (State his : history)
            if (his == state)
                return false;

        history.add(state);

        for (OutgoingTransition child : allOutgoingTransitions(state)) {
            List<State> newHistory = new ArrayList<State>();
            for (State s : history)
                newHistory.add(s);
            result = result || findFinals(child.getTargetState(), newHistory);
            if (result)
                break;
        }
        return result;
    }

    /**
     * Metoda zwracająca pierwszy według kolejności alfabetycznej napis,
     * akceptowany przez automat.
     */
    public String firstAcceptedWord(String alphabet) {
        NondeterministicAutomatonByThompsonApproach a =
                new NondeterministicAutomatonByThompsonApproach(this);
        boolean found = false;
        char[] tmp = alphabet.toCharArray();
        java.util.Arrays.sort(tmp);
        String sorted = new String(tmp);
        int l = alphabet.length();
        int x = 1;
        if (this.isEmpty())
            throw new RuntimeException("empty automaton");
        if (this.acceptEmptyWord()) {
            found = true;
            return "";
        } else do {
            int flag = x;
            char[] searchWord = new char[x];
            while (flag > 0) {
                searchWord[flag - 1] = sorted.charAt(0);
                flag--;
            }
            for (int i = 0; i < l; i++) {
                if (x > 1 && searchWord[x - 1] == sorted.charAt(sorted.length() - 1)) {
                    while (flag > 0) {
                        if (searchWord[flag - 1] == sorted.charAt(sorted.length() - 1)) {
                            flag--;
                        } else {
                            int z = 0, y = 0;
                            while (z < sorted.length() - 1 && y == 0) {
                                if (searchWord[flag - 1] == sorted.charAt(z))
                                    y = z + 1;
                                else
                                    z++;
                            }
                            searchWord[flag - 1] = sorted.charAt(y);
                            if (flag - 1 == 0) {
                                flag = x;
                                while (flag > 1) {
                                    searchWord[flag - 1] = sorted.charAt(0);
                                    flag--;
                                }
                            }
                            flag = 0;
                        }
                    }
                }
                flag = x;
                searchWord[x - 1] = tmp[i % alphabet.length()];
                String acceptedWord = new String(searchWord);
                if (a.accepts(acceptedWord)) {
                    found = true;
                    return acceptedWord;
                }
            }
            l = l * l;
            x++;
        } while (!found);

        throw new RuntimeException("error");
    }
    /**
     *Metoda zwraca długość najdłuższego słowa akceptowanego.
     */
    public int maxWordLength() {
        AllAcceptedWords words = new AllAcceptedWords(this);
        String word;
        int tmp;
        final int infinitereturncode = -2;
        final int emptyreturncode = -1;
        int max = 0;
        if (isInfinite()) {
            return infinitereturncode;
        }
        if (words.hasNext()) {
            do {
                word = words.next();
                tmp = word.length();
                if (max < tmp) {
                    max = tmp;
                }
            } while (words.hasNext());
            return max;
        } else {
            return emptyreturncode;
        }
    }

    /**
     * Tworzy epsilon domknięcie zadanego stanu.
     */
    public Set<State> getEpsilonClosure(State initial) {
        AlwaysAcceptingContextChecker contextChecker = new AlwaysAcceptingContextChecker();
        return doGetEpsilonClosure(initial, contextChecker);
    }
    /**
     * Odznacza końcowy stan.
     */
    public void unmarkedAsFinalState(State state) {
        getFinalStates().remove(state);
    }
    /**
     * Dla podanego automatu tworzy równoważny automat z 1 stanem końcowym.
     */
    public AutomatonSpecification makeOneFinalStateAutomaton() {
        ArrayList<State> allFinalStates = new ArrayList<State>();
        ArrayList<State> allStates = new ArrayList<State>();

        allStates.addAll(allStates());

        for (State someState : allStates) {
            if (isFinal(someState)) {
                allFinalStates.add(someState);
            }
        }

        int size = allFinalStates.size();
        AutomatonSpecification spec = new NaiveAutomatonSpecification();

        switch (size) {
            case 0:
                spec.clone();
                spec.markAsFinal(spec.addState());
                return spec;
            case 1:
                spec.clone();
                return spec;
            default:
                spec.clone();
                State stateFinal = spec.addState();
                for (State someState : allFinalStates) {
                    spec.unmarkedAsFinalState(someState);
                    spec.addTransition(someState, stateFinal, new EpsilonTransitionLabel());
                    return spec;
                }
        }
        return null;
    }

    protected List<State> getFinalStates() {
        return finalStatess;
    }

    /**
     * Zwraca epsilon domknięcie zadanego stanu, z uwzględnieniem warunków kontekstowych.
     */
    public Set<State> getEpsilonClosureWithContext(State initial, String s, int position) {
        ReallyCheckingContextChecker contextChecker =
                new ReallyCheckingContextChecker(s, position);
        return doGetEpsilonClosure(initial, contextChecker);
    }

    /**
     * Metoda wyszukująca epsilon domknięcie.
     */
    private Set<State> doGetEpsilonClosure(State initial, ContextChecker contextChecker) {
        Set<State> epsilonClosure = new HashSet<State>();
        Set<State> visited = new HashSet<State>();
        Stack<State> stack = new Stack<State>();

        stack.push(initial);
        epsilonClosure.add(initial);

        while (!stack.empty()) {
            State from = stack.pop();
            if (visited.contains(from)) {
                continue;
            }
            visited.add(from);

            for (OutgoingTransition trans : allOutgoingTransitions(from)) {
                TransitionLabel label = trans.getTransitionLabel();
                State to = trans.getTargetState();
                if (label.canBeEpsilon() && contextChecker.check(label)) {
                    epsilonClosure.add(to);
                    stack.push(to);
                }
            }

        }
        return epsilonClosure;
    }

    private LinkedList<State> finalStatess = new LinkedList<State>();
};

class StructureException extends Exception {
}
