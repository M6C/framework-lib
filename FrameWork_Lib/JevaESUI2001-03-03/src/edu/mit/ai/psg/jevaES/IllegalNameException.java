// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/IllegalNameException.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            VerifyingException, IJevaNode, IEnv

public class IllegalNameException extends VerifyingException
{

    public IllegalNameException(IJevaNode n, IEnv env, String explain)
    {
        super(n, env);
        explanation = explain;
    }

    public String toString()
    {
        return super.toString() + "\n" + "Encountered illegal name: " + explanation;
    }

    public String explanation;
}
