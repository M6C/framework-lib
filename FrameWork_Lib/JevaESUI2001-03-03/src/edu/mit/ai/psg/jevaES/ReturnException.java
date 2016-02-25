// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/ReturnException.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            AbruptCompletionException

public class ReturnException extends AbruptCompletionException
{

    public Object getValue()
    {
        return value;
    }

    public ReturnException()
    {
        value = null;
    }

    public ReturnException(Object returnValue)
    {
        value = null;
        value = returnValue;
    }

    Object value;
}
