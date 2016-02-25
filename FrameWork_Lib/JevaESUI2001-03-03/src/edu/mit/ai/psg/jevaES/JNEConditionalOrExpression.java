// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEConditionalOrExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionNode, IllegalTypeException, ThrowException, 
//            VerifyingError, VerifyingException, SimpleNode, EvalMethods, 
//            IJevaNode, IEnv

public class JNEConditionalOrExpression extends ExpressionNode
{

    JNEConditionalOrExpression(int id)
    {
        super(id);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            IExpressionNode child = (IExpressionNode)jjtGetChild(i);
            if(Boolean.TYPE != EvalMethods.getType(child, env))
                throw new IllegalTypeException(this, env, "Parameters to || must be boolean: " + child.printToString());
        }

        return Boolean.TYPE;
    }

    public boolean computeIsConstant(IEnv env)
        throws VerifyingException
    {
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            IExpressionNode child = (IExpressionNode)jjtGetChild(i);
            if(!EvalMethods.isConstant(child, env))
                return false;
            try
            {
                if(Boolean.TRUE.equals(EvalMethods.eval(child, env)))
                    return true;
            }
            catch(ThrowException e)
            {
                throw new VerifyingError(this, env, e);
            }
        }

        return true;
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            IExpressionNode child = (IExpressionNode)jjtGetChild(i);
            Boolean result = (Boolean)EvalMethods.eval(child, env);
            if(Boolean.TRUE.equals(result))
                return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
