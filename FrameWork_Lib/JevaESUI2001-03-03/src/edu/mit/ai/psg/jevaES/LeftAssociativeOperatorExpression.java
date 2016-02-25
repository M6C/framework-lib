// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/LeftAssociativeOperatorExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionNode, IllegalTypeException, VerifyingException, 
//            ThrowException, SimpleNode, EvalMethods, IJevaNode, 
//            IEnv, Token

public abstract class LeftAssociativeOperatorExpression extends ExpressionNode
{

    LeftAssociativeOperatorExpression(int id)
    {
        super(id);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        IExpressionNode previousChild = (IExpressionNode)jjtGetChild(0);
        Class accumType = EvalMethods.getType(previousChild, env);
        for(int i = 1; i < jjtGetNumChildren(); i++)
        {
            IExpressionNode nextChild = (IExpressionNode)jjtGetChild(i);
            Class nextType = EvalMethods.getType(nextChild, env);
            Token operator = previousChild.getEndToken();
            try
            {
                accumType = binaryCombineTypes(operator, accumType, nextType);
            }
            catch(IllegalArgumentException e)
            {
                throw new IllegalTypeException(this, env, e.getMessage());
            }
            previousChild = nextChild;
        }

        return accumType;
    }

    public abstract Class binaryCombineTypes(Token token, Class class1, Class class2)
        throws IllegalArgumentException;

    public boolean computeIsConstant(IEnv env)
        throws VerifyingException
    {
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            IExpressionNode child = (IExpressionNode)jjtGetChild(i);
            if(!EvalMethods.isConstant(child, env))
                return false;
        }

        return true;
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        IExpressionNode previousChild = (IExpressionNode)jjtGetChild(0);
        Object accumResult = EvalMethods.eval(previousChild, env);
        for(int i = 1; i < jjtGetNumChildren(); i++)
        {
            IExpressionNode nextChild = (IExpressionNode)jjtGetChild(i);
            Object nextResult = EvalMethods.eval(nextChild, env);
            Token operator = previousChild.getEndToken();
            accumResult = binaryCombine(operator, accumResult, nextResult);
            previousChild = nextChild;
        }

        return accumResult;
    }

    public abstract Object binaryCombine(Token token, Object obj, Object obj1)
        throws ThrowException;
}
