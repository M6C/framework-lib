// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEParenthesizedExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionNode, VerifyingException, ThrowException, 
//            SimpleNode, EvalMethods, IEnv

public class JNEParenthesizedExpression extends ExpressionNode
{

    JNEParenthesizedExpression(int id)
    {
        super(id);
    }

    public IExpressionNode getExpression()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        return EvalMethods.getType(getExpression(), env);
    }

    public boolean computeIsConstant(IEnv env)
        throws VerifyingException
    {
        return EvalMethods.isConstant(getExpression(), env);
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        return EvalMethods.eval(getExpression(), env);
    }
}
