// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/ContinueException.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            AbruptCompletionException

public class ContinueException extends AbruptCompletionException
{

    public String getLabel()
    {
        return label;
    }

    public ContinueException()
    {
        label = null;
    }

    public ContinueException(String continueLabel)
    {
        label = null;
        label = continueLabel;
    }

    String label;
}
