// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/IExpressionStoreNode.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            IExpressionNode, ThrowException, IEnv, IStore

public interface IExpressionStoreNode
    extends IExpressionNode
{

    public abstract IStore evalToStore(IEnv ienv)
        throws ThrowException;
}
