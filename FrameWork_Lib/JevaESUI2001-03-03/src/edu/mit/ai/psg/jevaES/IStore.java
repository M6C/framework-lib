// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/IStore.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ThrowException

public interface IStore
{

    public abstract Class getType();

    public abstract boolean isFinal();

    public abstract boolean isConstant();

    public abstract Object getValue()
        throws ThrowException;

    public abstract void setValue(Object obj)
        throws ThrowException;
}
