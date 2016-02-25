// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSExpressionStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IExpressionNode, IStatementNode, VerifyingException, 
//            ThrowException, SimpleNode, EvalMethods, IEnv

public class JNSExpressionStatement extends JevaNode
    implements IStatementNode
{

    JNSExpressionStatement(int id)
    {
        super(id);
    }

    public IExpressionNode getExpression()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        EvalMethods.getType(getExpression(), env);
        return env;
    }

    public IEnv eval(IEnv env)
        throws ThrowException
    {
        EvalMethods.eval(getExpression(), env);
        return env;
    }
}
