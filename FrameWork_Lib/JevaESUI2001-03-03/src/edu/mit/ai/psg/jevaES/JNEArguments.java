// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEArguments.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IExpressionNode, VerifyingException, ThrowException, 
//            SimpleNode, EvalMethods, IEnv

public class JNEArguments extends JevaNode
{

    JNEArguments(int id)
    {
        super(id);
    }

    public Class[] getTypes(IEnv env)
        throws VerifyingException
    {
        Class results[] = new Class[jjtGetNumChildren()];
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            IExpressionNode child = (IExpressionNode)jjtGetChild(i);
            results[i] = EvalMethods.getType(child, env);
        }

        return results;
    }

    public boolean computeIsConstant(IEnv env)
        throws VerifyingException
    {
        for(int i = 0; i < jjtGetNumChildren(); i++)
            if(!EvalMethods.isConstant((IExpressionNode)jjtGetChild(i), env))
                return false;

        return true;
    }

    public Object[] evalArguments(IEnv env)
        throws ThrowException
    {
        Object results[] = new Object[jjtGetNumChildren()];
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            IExpressionNode child = (IExpressionNode)jjtGetChild(i);
            results[i] = EvalMethods.eval(child, env);
        }

        return results;
    }
}
