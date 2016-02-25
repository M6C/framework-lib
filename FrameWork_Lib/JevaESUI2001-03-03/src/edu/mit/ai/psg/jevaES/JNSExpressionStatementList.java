// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSExpressionStatementList.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IExpressionNode, IStatementNode, VerifyingException, 
//            ThrowException, SimpleNode, EvalMethods, IEnv

public class JNSExpressionStatementList extends JevaNode
    implements IStatementNode
{

    JNSExpressionStatementList(int id)
    {
        super(id);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        for(int i = 0; i < jjtGetNumChildren(); i++)
            EvalMethods.getType((IExpressionNode)jjtGetChild(i), env);

        return env;
    }

    public IEnv eval(IEnv env)
        throws ThrowException
    {
        for(int i = 0; i < jjtGetNumChildren(); i++)
            EvalMethods.eval((IExpressionNode)jjtGetChild(i), env);

        return env;
    }
}
