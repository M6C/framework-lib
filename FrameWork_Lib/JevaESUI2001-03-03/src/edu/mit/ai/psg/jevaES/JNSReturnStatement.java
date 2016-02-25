// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSReturnStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IExpressionNode, IllegalConstructException, IllegalTypeException, 
//            ReturnException, IStatementNode, VerifyingException, ThrowException, 
//            SimpleNode, IEnv, TypeMethods, EvalMethods

public class JNSReturnStatement extends JevaNode
    implements IStatementNode
{

    JNSReturnStatement(int id)
    {
        super(id);
    }

    public IExpressionNode getExpression()
    {
        return (IExpressionNode)(1 != jjtGetNumChildren() ? null : jjtGetChild(0));
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        Class returnType = env.lookupReturnType();
        if(returnType == null)
            throw new IllegalConstructException(this, env, "'return' may not appear in static initializers");
        if(returnType == Void.TYPE)
        {
            if(0 != jjtGetNumChildren())
                throw new IllegalConstructException(this, env, "Cannot return a value");
        } else
        {
            if(0 == jjtGetNumChildren())
                throw new IllegalTypeException(this, env, "Must return " + returnType.getName() + " value");
            if(!TypeMethods.isAssignable(returnType, getExpression(), env))
            {
                Class exprType = EvalMethods.getType(getExpression(), env);
                throw new IllegalTypeException(this, env, "Must return " + returnType + " value; " + "can't return " + (exprType != null ? exprType.getName() : "" + exprType));
            }
        }
        return env;
    }

    public IEnv eval(IEnv env)
        throws ReturnException, ThrowException
    {
        if(0 == jjtGetNumChildren())
            throw new ReturnException();
        else
            throw new ReturnException(EvalMethods.eval(getExpression(), env));
    }
}
