// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/ThrowException.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            AbruptCompletionException

public class ThrowException extends AbruptCompletionException
{

    public Throwable getThrown()
    {
        return thrown;
    }

    public ThrowException(Throwable thrownValue)
    {
        thrown = thrownValue;
    }

    public String toString()
    {
        return "ThrowException(" + thrown + ")";
    }

    Throwable thrown;
}
