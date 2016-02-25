// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/IExpressionNode.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            IJevaNode, VerifyingException, ThrowException, IEnv

public interface IExpressionNode
    extends IJevaNode
{

    public abstract Class getType(IEnv ienv)
        throws VerifyingException;

    public abstract boolean isConstant(IEnv ienv)
        throws VerifyingException;

    public abstract Object getValue(IEnv ienv)
        throws ThrowException;
}
