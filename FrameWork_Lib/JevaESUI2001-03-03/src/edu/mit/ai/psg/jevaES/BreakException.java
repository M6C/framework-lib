// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/BreakException.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            AbruptCompletionException

public class BreakException extends AbruptCompletionException
{

    public String getLabel()
    {
        return label;
    }

    public BreakException()
    {
        label = null;
    }

    public BreakException(String breakLabel)
    {
        label = null;
        label = breakLabel;
    }

    String label;
}
