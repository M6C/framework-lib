// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/IStatementNode.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            IJevaNode, VerifyingException, AbruptCompletionException, IEnv

public interface IStatementNode
    extends IJevaNode
{

    public abstract IEnv verifyTypes(IEnv ienv)
        throws VerifyingException;

    public abstract IEnv eval(IEnv ienv)
        throws AbruptCompletionException;
}
