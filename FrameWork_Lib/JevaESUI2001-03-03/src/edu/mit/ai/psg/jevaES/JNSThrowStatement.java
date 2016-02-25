// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSThrowStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IExpressionNode, IllegalTypeException, ThrowException, 
//            IStatementNode, VerifyingException, SimpleNode, EvalMethods, 
//            TypeMethods, IEnv

public class JNSThrowStatement extends JevaNode
    implements IStatementNode
{

    JNSThrowStatement(int id)
    {
        super(id);
    }

    public IExpressionNode getThrownExpressionNode()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        Class exceptionType = EvalMethods.getType(getThrownExpressionNode(), env);
        if(!(java.lang.Throwable.class).isAssignableFrom(exceptionType))
            throw new IllegalTypeException(this, env, "Not a subclass of Throwable: " + TypeMethods.toSourceString(exceptionType));
        if(!TypeMethods.isUncheckedException(exceptionType) && !TypeMethods.isCaughtOrDeclaredException(exceptionType, env.lookupThrows()))
            throw new IllegalTypeException(this, env, "Checked exception not caught or declared: " + exceptionType.getName());
        else
            return env;
    }

    public IEnv eval(IEnv env)
        throws ThrowException
    {
        throw new ThrowException((Throwable)EvalMethods.eval(getThrownExpressionNode(), env));
    }
}
