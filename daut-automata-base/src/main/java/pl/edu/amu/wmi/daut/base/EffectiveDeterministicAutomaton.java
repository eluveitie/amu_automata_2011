package pl.edu.amu.wmi.daut.base;
import java.util.HashSet;
import java.util.Vector;
import java.util.List;


class EffectiveDeterministicAutomaton extends DeterministicAutomatonSpecification
{
    static class MyState implements State {
        private static final int DEFAULT_ARRAY_LENGTH = 256;
        private MyState[] mCharacterTargetState;
        private int mCharacterTargetStateLength;
        private MyState mEpsilonTargetState;
        private boolean mHasCharacterTransition;
        private boolean mIsFinal;
        private Vector<OutgoingTransition> mOutgoingTransitions;
        private EffectiveDeterministicAutomaton mOwner;


        public MyState(EffectiveDeterministicAutomaton owner) {
            mCharacterTargetStateLength = DEFAULT_ARRAY_LENGTH;
            mCharacterTargetState = new MyState[mCharacterTargetStateLength];
            for (int i = 0; i < mCharacterTargetStateLength; ++i) {
                mCharacterTargetState[i] = null;
            }
            mEpsilonTargetState = null;
            mHasCharacterTransition = false;
            mIsFinal = false;
            mOutgoingTransitions = new Vector<OutgoingTransition>();
            mOwner = owner;
        }


        private void addOutgoingTransition(OutgoingTransition transition) {
            mOutgoingTransitions.addElement(transition);
        }


        public EffectiveDeterministicAutomaton getOwner() {
            return mOwner;
        }


        public MyState getEpsilonTargetState() {
            return mEpsilonTargetState;
        }


        public Vector<OutgoingTransition> getOutgoingTransitions() {
            return mOutgoingTransitions;
        }


        public MyState getTargetState(char c) {
            return (c < mCharacterTargetStateLength ? mCharacterTargetState[c] : null);
        }


        public boolean hasCharacterTransition() {
            return mHasCharacterTransition;
        }


        public boolean hasEpsilonTransition() {
            return (mEpsilonTargetState != null);
        }


        public boolean isFinal() {
            return mIsFinal;
        }


        public void setEpsilonTargetState(MyState state) {
            if (mHasCharacterTransition
               || (mEpsilonTargetState != null && mEpsilonTargetState != state))
                throw new UnsupportedOperationException();
            mEpsilonTargetState = state;
        }


        public void setFinal(boolean value) {
            mIsFinal = value;
        }


        public void setTargetState(char c, MyState state) {
            if (mEpsilonTargetState != null)
                throw new UnsupportedOperationException();

            if (c >= mCharacterTargetStateLength) {
                MyState[] oldArray = mCharacterTargetState;
                int oldArrayLength = mCharacterTargetStateLength;
                if (mCharacterTargetStateLength == 0)
                    mCharacterTargetStateLength = c + 1;
                else while (true) {
                    mCharacterTargetStateLength *= 2;
                    if (mCharacterTargetStateLength >= c + 1) {
                        if (mCharacterTargetStateLength > Character.MAX_VALUE)
                            mCharacterTargetStateLength = Character.MAX_VALUE + 1;
                        break;
                    }
                }
                mCharacterTargetState = new MyState[mCharacterTargetStateLength];
                for (int j = 0; j < oldArrayLength; ++j)
                    mCharacterTargetState[j] = oldArray[j];
                for (int j = oldArrayLength; j < mCharacterTargetStateLength; ++j)
                    mCharacterTargetState[j] = null;
            }

            if (mCharacterTargetState[c] != null && mCharacterTargetState[c] != state)
                throw new UnsupportedOperationException();

            mCharacterTargetState[c] = state;
            mHasCharacterTransition = true;
        }
    }


    private MyState mInitialState;
    private Vector<State> mStates;


    @Override
    public State addState() {
        MyState myState = new MyState(this);
        mStates.addElement(myState);
        return myState;
    }


    @Override
    public void addTransition(State from, State to, TransitionLabel label) {
        if (label.isEmpty())
            return;

        MyState myFrom = assertStateValid(from);
        MyState myTo = assertStateValid(to);

        if (label instanceof CharTransitionLabel) {
            CharTransitionLabel l = (CharTransitionLabel) label;
            myFrom.setTargetState(l.getChar(), myTo);
        } else if (label instanceof CharRangeTransitionLabel) {
            CharRangeTransitionLabel l = (CharRangeTransitionLabel) label;
            for (int i = l.getSecondChar(); i >= l.getFirstChar(); --i)
                myFrom.setTargetState((char) i, myTo);
        } else if (label instanceof CharSetTransitionLabel) {
            CharSetTransitionLabel l = (CharSetTransitionLabel) label;
            HashSet<Character> characters = l.getCharSet();
            for (Character c : characters)
                myFrom.setTargetState(c, myTo);
        } else for (int i = 0; i <= Character.MAX_VALUE; ++i) {
            char c = (char) i;
            if (label.canAcceptCharacter(c))
                    myFrom.setTargetState(c, myTo);
        }

        if (label.canBeEpsilon())
            myFrom.setEpsilonTargetState(myTo);

        myFrom.addOutgoingTransition(new OutgoingTransition(label, to));
    }


    @Override
    public List<OutgoingTransition> allOutgoingTransitions(State state) {
        MyState myState = assertStateValid(state);
        return myState.getOutgoingTransitions();
    }


    @Override
    public List<State> allStates() {
        return mStates;
    }


    private MyState assertStateValid(State state) {
        if (state != null) {
            if (state instanceof MyState) {
                MyState myState = (MyState) state;
                if (myState.getOwner() == this)
                    return myState;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }


    public EffectiveDeterministicAutomaton() {
        mInitialState = null;
        mStates = new Vector<State>();
    }


    @Override
    public State getInitialState() {
        return mInitialState;
    }


    @Override
    public boolean isFinal(State state) {
        MyState myState = assertStateValid(state);
        return myState.isFinal();
    }


    @Override
    public void markAsInitial(State state) {
        mInitialState = assertStateValid(state);
    }


    @Override
    public void markAsFinal(State state) {
        MyState myState = assertStateValid(state);
        myState.setFinal(true);
    }

    @Override
    public void unmarkAsFinalState(State state) {
        MyState myState = assertStateValid(state);
        myState.setFinal(false);
    }

    @Override
    public State targetState(State from, char c) {
        MyState myFrom = assertStateValid(from);
        return myFrom.getTargetState(c);
    }
}

