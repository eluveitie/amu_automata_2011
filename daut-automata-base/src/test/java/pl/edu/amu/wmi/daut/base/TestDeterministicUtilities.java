
package pl.edu.amu.wmi.daut.base;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import junit.framework.TestCase;


/**
 *
 * @author Adam
 * 
 * klasa testuje poprawność działania metody
 * public void createAutomatonForFiniteLanguage
 * (DeterministicAutomatonSpecification automaton, Set<String> language)
 * w klasie DeterministicUtilities.
 */
public class TestDeterministicUtilities extends TestCase {

    /**
     * Testuje automat dla języka pustego.
     */
    public final void testEmptyLanguage() {
    Set<String> language1 = new HashSet<String>();
    DeterministicAutomatonSpecification automaton1 =
    new NaiveDeterministicAutomatonSpecification();
    DeterministicUtilities detUtil = new DeterministicUtilities();
    detUtil.createAutomatonForFiniteLanguage(automaton1, language1);
    Generator words1 = new Generator();
    List<String> accepted1 = words1.wordsFromAutomatonWithoutCycles(automaton1, "abc");
    assertTrue(compare(language1, accepted1));
}

    /**
     * Testuje automat dla języka z tylko słowem pustym.
     */
    public final void testEmptyWord() {

        Set<String> language2 = new HashSet<String>();
        language2.add("");
        DeterministicAutomatonSpecification automaton2 =
        new NaiveDeterministicAutomatonSpecification();
        DeterministicUtilities detUtil = new DeterministicUtilities();
        detUtil.createAutomatonForFiniteLanguage(automaton2, language2);
        AutomatonByRecursion angle = new AutomatonByRecursion(automaton2);
        assertTrue(angle.accepts(""));
        assertFalse(angle.accepts("cokolwiek_niepustego"));
    /*
    Generator words2 = new Generator();
    List<String> accepted2 = words2.wordsFromAutomatonWithoutCycles(automaton2, "abc");
    assertTrue(compare(language2, accepted2));*/
}

    /**
     * Test Przykładowy.
     */
    public final void testNormal() {
        Set<String> language3 = new HashSet<String>();
        language3.add("ko");
        language3.add("kot");
        language3.add("kok");
        language3.add("kat");
        language3.add("");
        language3.add("ko");
        language3.add("k");
        language3.add("tak");
        language3.add("takmakptak");
        language3.add("ak");
        language3.add("bokot");
        language3.add("boko");
        language3.add("koty");
        language3.add("kok");
        language3.add("tyt");
        language3.add("kotokokoojhooo");
        language3.add("b");
        language3.add("bok");
        language3.add("bokot");
        DeterministicAutomatonSpecification automaton3 =
        new NaiveDeterministicAutomatonSpecification();
        DeterministicUtilities detUtil = new DeterministicUtilities();
        detUtil.createAutomatonForFiniteLanguage(automaton3, language3);
        Generator words3 = new Generator();
        List<String> accepted3 = words3.wordsFromAutomatonWithoutCycles(automaton3,
                "qwertyuiopasdfghjklzxcvbnm");

        assertTrue(compare(language3, accepted3));
        AutomatonByRecursion angle = new AutomatonByRecursion(automaton3);
        assertTrue(angle.accepts(""));
        assertFalse(angle.accepts("cokolwiek_niepustego_i_nie_z_language"));
    }

    /**
     * Test przykładowy ze słowem pustym.
     */
    public final void testNormalAndEmptyWord() {

        Set<String> language4 = new HashSet<String>();
        language4.add("");
        language4.add("kot");
        language4.add("koty");
        language4.add("kok");
        language4.add("kotokokoojhooo");
        language4.add("bok");
        language4.add("bokot");
        DeterministicAutomatonSpecification automaton4 =
        new NaiveDeterministicAutomatonSpecification();
        DeterministicUtilities detUtil = new DeterministicUtilities();
        detUtil.createAutomatonForFiniteLanguage(automaton4, language4);
        Generator words4 = new Generator();
        List<String> accepted4 = words4.wordsFromAutomatonWithoutCycles(automaton4, "kotyjhb");
        assertTrue(compare(language4, accepted4));

        AutomatonByRecursion angle = new AutomatonByRecursion(automaton4);
        assertTrue(angle.accepts(""));
        assertFalse(angle.accepts("cokolwiek_niepustego_i_nie_z_language"));
}

    /**
     * Metoda porównująca słowa akceptowane przez powstały automat ze słowami oczekiwanymi.
     */
    private boolean compare(Set<String> language, List<String> acceptedWords) {
        int number = acceptedWords.size();

        for (String i : language) {
            if (i.equals("")) {
                language.remove(i);
                break;
            }
        }

        if (language.size() == number) {
            for (String i : language) {
                for (String j : acceptedWords) {
                    if (i.equals(j)) {
                        number--;
                        //language.remove(i);
                        //acceptedWords.remove(j);
                        break;
                    }
                }
            }
            //if (language.isEmpty() && acceptedWords.isEmpty()) {
            return (number == 0);
        } else {
            return false;
        }
    }
}
