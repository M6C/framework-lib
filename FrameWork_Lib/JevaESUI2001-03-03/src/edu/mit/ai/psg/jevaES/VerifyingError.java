// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/VerifyingError.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            IExpressionNode, IStatementNode, IJevaNode, Token, 
//            IEnv

public class VerifyingError extends Error
{

    public VerifyingError(IJevaNode n, IEnv env, Throwable err)
    {
        node = n;
        environment = env;
        error = err;
    }

    public String toString()
    {
        return "While verifying" + ((node instanceof IExpressionNode) ? " expression" : (node instanceof IStatementNode) ? " statement" : "") + " at line " + node.getBeginToken().beginLine + ", column " + node.getBeginToken().beginColumn + ":\n" + node.printToString().trim() + "\n" + "Encountered error: \n" + "  " + error;
    }

    public IJevaNode node;
    public IEnv environment;
    public Throwable error;
}
