// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSSynchronizedStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IExpressionNode, JNSBlock, IllegalTypeException, 
//            IStatementNode, VerifyingException, AbruptCompletionException, SimpleNode, 
//            EvalMethods, TypeMethods, IJevaNode, IEnv

public class JNSSynchronizedStatement extends JevaNode
    implements IStatementNode
{

    JNSSynchronizedStatement(int id)
    {
        super(id);
    }

    public IExpressionNode getTargetExpressionNode()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    public JNSBlock getBodyNode()
    {
        return (JNSBlock)jjtGetChild(1);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        IExpressionNode expr = getTargetExpressionNode();
        if(!TypeMethods.isReferenceType(EvalMethods.getType(expr, env)))
        {
            throw new IllegalTypeException(this, env, "Lockable object of Synchronized is not a reference:  (" + TypeMethods.toSourceString(EvalMethods.getType(expr, env)) + ") " + expr.printToString());
        } else
        {
            EvalMethods.verifyTypes(getBodyNode(), env);
            return env;
        }
    }

    public IEnv eval(IEnv env)
        throws AbruptCompletionException
    {
        synchronized(EvalMethods.eval(getTargetExpressionNode(), env))
        {
            EvalMethods.eval(getBodyNode(), env);
        }
        return env;
    }
}
