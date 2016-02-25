// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/ExpressionNode.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, ThrowException, IllegalConstructException, VerifyingException, 
//            EvaluatingError, IExpressionNode, IEnv

public abstract class ExpressionNode extends JevaNode
    implements IExpressionNode
{

    public ExpressionNode(int id)
    {
        super(id);
        typeIsComputed = false;
        isConstantIsComputed = false;
    }

    public final Class getType(IEnv env)
        throws VerifyingException
    {
        if(!typeIsComputed)
        {
            type = computeType(env);
            typeIsComputed = true;
            isConstant(env);
        }
        return type;
    }

    public abstract Class computeType(IEnv ienv)
        throws VerifyingException;

    public final boolean isConstant(IEnv env)
        throws VerifyingException
    {
        if(!isConstantIsComputed)
        {
            isConstant = computeIsConstant(env);
            isConstantIsComputed = true;
            if(isConstant)
                try
                {
                    constantValue = eval(env);
                }
                catch(ThrowException e)
                {
                    throw new IllegalConstructException(this, env, "" + e.getThrown());
                }
        }
        return isConstant;
    }

    public abstract boolean computeIsConstant(IEnv ienv)
        throws VerifyingException;

    public final Object getValue(IEnv env)
        throws ThrowException
    {
        boolean isConstant;
        try
        {
            isConstant = isConstant(env);
        }
        catch(VerifyingException e)
        {
            throw new EvaluatingError(this, env, e);
        }
        if(isConstant)
            return constantValue;
        else
            return eval(env);
    }

    public abstract Object eval(IEnv ienv)
        throws ThrowException;

    Class type;
    boolean typeIsComputed;
    Object constantValue;
    boolean isConstant;
    boolean isConstantIsComputed;
}
