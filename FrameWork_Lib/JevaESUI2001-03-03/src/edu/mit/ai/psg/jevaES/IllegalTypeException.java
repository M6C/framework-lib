// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/IllegalTypeException.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            VerifyingException, IJevaNode, IEnv

public class IllegalTypeException extends VerifyingException
{

    public IllegalTypeException(IJevaNode n, IEnv env, String explain)
    {
        super(n, env);
        explanation = explain;
    }

    public String toString()
    {
        return super.toString() + "\n" + "Encountered illegal type: " + explanation;
    }

    public String explanation;
}
