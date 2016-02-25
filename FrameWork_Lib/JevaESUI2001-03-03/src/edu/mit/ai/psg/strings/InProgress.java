// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   strings/Stringify.java

package edu.mit.ai.psg.strings;

import java.util.Stack;
import java.util.Vector;

// Referenced classes of package edu.mit.ai.psg.strings:
//            Stringify

class InProgress
{
    static class State
    {

        Stack stack;
        Vector cyclicElements;

        State()
        {
            stack = new Stack();
            cyclicElements = new Vector();
        }
    }


    InProgress()
    {
    }

    static boolean add(Object obj)
    {
        State state = (State)localInProgressState.get();
        if(!containsIdentical(state.stack, obj))
        {
            state.stack.push(obj);
            return true;
        }
        if(!containsIdentical(state.cyclicElements, obj))
            state.cyclicElements.add(obj);
        return false;
    }

    static final boolean containsIdentical(Vector vec, Object obj)
    {
        for(int i = 0; i < vec.size(); i++)
            if(obj == vec.get(i))
                return true;

        return false;
    }

    static boolean isCyclic(Object obj)
    {
        State state = (State)localInProgressState.get();
        if(containsIdentical(state.stack, obj))
            return containsIdentical(state.cyclicElements, obj);
        else
            throw notInProgress(obj);
    }

    static void remove(Object obj)
    {
        State state = (State)localInProgressState.get();
        if(state.stack.peek() == obj)
        {
            state.stack.pop();
            for(int i = state.cyclicElements.size() - 1; i >= 0; i--)
            {
                if(state.cyclicElements.get(i) != obj)
                    continue;
                state.cyclicElements.remove(i);
                break;
            }

        } else
        {
            throw notInProgress(obj);
        }
    }

    static IllegalArgumentException notInProgress(Object obj)
    {
        return new IllegalArgumentException("Not in progress: " + Stringify.makeShortString(obj));
    }

    static final ThreadLocal localInProgressState = new ThreadLocal() {

        public Object initialValue()
        {
            return new State();
        }

    }
;

}
