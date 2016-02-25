// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/VerifyingException.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            IExpressionNode, IStatementNode, IJevaNode, Token, 
//            IEnv

public abstract class VerifyingException extends Exception
{

    VerifyingException(IJevaNode n, IEnv env)
    {
        node = n;
        environment = env;
    }

    public String toString()
    {
        return "Terminated while verifying " + ((node instanceof IExpressionNode) ? "expression" : (node instanceof IStatementNode) ? "statement" : "") + " at line " + node.getBeginToken().beginLine + ", column " + node.getBeginToken().beginColumn + ":\n" + node.printToString().trim();
    }

    public IJevaNode node;
    public IEnv environment;
}
