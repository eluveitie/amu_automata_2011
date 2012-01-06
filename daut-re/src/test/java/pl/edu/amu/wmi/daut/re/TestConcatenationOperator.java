package pl.edu.amu.wmi.daut.re;

import pl.edu.amu.wmi.daut.base.AutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NaiveAutomatonSpecification;
import pl.edu.amu.wmi.daut.base.NondeterministicAutomatonByThompsonApproach;
import pl.edu.amu.wmi.daut.base.State;
import pl.edu.amu.wmi.daut.base.CharTransitionLabel;
import junit.framework.TestCase;

    /**
     * Test klasy ConcatenationOperator.
     */
public class TestConcatenationOperator extends TestCase {
    /**
     * Pierwszy automat akceptuje wyrażenia mające co najmniej jedno "b".
     * Drugi akceptuje słowa z dokładnie dwoma "c" i dowolną ilością "a".
     * Powstały ma akceptować ich konkatenację.
     */
    public final void testCreateAutomatonFromTwoAutomata() {

        AutomatonSpecification a1 = new NaiveAutomatonSpecification();

        State q0 = a1.addState();
        State q1 = a1.addState();
        a1.addTransition(q0, q1, new CharTransitionLabel('b'));
        a1.addLoop(q0, new CharTransitionLabel('a'));
        a1.addLoop(q1, new CharTransitionLabel('b'));
        a1.addLoop(q1, new CharTransitionLabel('a'));

        a1.markAsInitial(q0);
        a1.markAsFinal(q1);

        AutomatonSpecification a2 = new NaiveAutomatonSpecification();

        State q0c = a2.addState();
        State q1c = a2.addState();
        State q2c = a2.addState();
        a2.addTransition(q0c, q1c, new CharTransitionLabel('c'));
        a2.addTransition(q1c, q2c, new CharTransitionLabel('c'));
        a2.addLoop(q0c, new CharTransitionLabel('a'));
        a2.addLoop(q1c, new CharTransitionLabel('a'));
        a2.addLoop(q2c, new CharTransitionLabel('a'));

        a2.markAsInitial(q0c);
        a2.markAsFinal(q2c);

        ConcatenationOperator con = new ConcatenationOperator();
        NondeterministicAutomatonByThompsonApproach result =
        new NondeterministicAutomatonByThompsonApproach(
                con.createAutomatonFromTwoAutomata(a1, a2));

        assertTrue(result.accepts("abbbaaaacc"));
        assertTrue(result.accepts("ababababcaaaaaac"));
        assertTrue(result.accepts("aaaaaaaaaaaabaaaaaacaaaaaacaaaaa"));
        assertTrue(result.accepts("bcc"));
        assertFalse(result.accepts("aabaaba"));
        assertFalse(result.accepts("abbca"));
        assertFalse(result.accepts("aaaacc"));
        assertFalse(result.accepts(""));
        assertFalse(result.accepts("cokolwiek"));
        assertFalse(result.accepts("bbaccab"));
        assertFalse(result.accepts("ccabba"));
    }
}
