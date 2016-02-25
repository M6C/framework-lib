// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSIfStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IExpressionNode, IStatementNode, IllegalTypeException, 
//            VerifyingException, AbruptCompletionException, SimpleNode, EvalMethods, 
//            IEnv

public class JNSIfStatement extends JevaNode
    implements IStatementNode
{

    JNSIfStatement(int id)
    {
        super(id);
    }

    public IExpressionNode getTestExpressionNode()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    public IStatementNode getThenStatementNode()
    {
        return (IStatementNode)jjtGetChild(1);
    }

    public IStatementNode getElseStatementNode()
    {
        return (IStatementNode)(jjtGetNumChildren() != 3 ? null : jjtGetChild(2));
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        if(Boolean.TYPE != EvalMethods.getType(getTestExpressionNode(), env))
            throw new IllegalTypeException(this, env, "if testExpr must be boolean");
        EvalMethods.verifyTypes(getThenStatementNode(), env);
        if(3 == jjtGetNumChildren())
            EvalMethods.verifyTypes(getElseStatementNode(), env);
        return env;
    }

    public IEnv eval(IEnv env)
        throws AbruptCompletionException
    {
        Boolean testVal = (Boolean)EvalMethods.eval(getTestExpressionNode(), env);
        if(testVal.booleanValue())
            EvalMethods.eval(getThenStatementNode(), env);
        else
        if(3 == jjtGetNumChildren())
            EvalMethods.eval(getElseStatementNode(), env);
        return env;
    }
}
