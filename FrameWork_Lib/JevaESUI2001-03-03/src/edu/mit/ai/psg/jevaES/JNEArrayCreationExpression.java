// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEArrayCreationExpression.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.Array;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, JNEArrayDimensions, JNEArrayInitializer, JNPrimitiveType, 
//            JNName, IExpressionNode, IllegalTypeException, ThrowException, 
//            VerifyingError, IllegalConstructException, VerifyingException, SimpleNode, 
//            JevaNode, IJevaNode, Token, TypeMethods, 
//            EvalMethods, PrimitiveOperations, IEnv

public class JNEArrayCreationExpression extends ExpressionNode
{

    JNEArrayCreationExpression(int id)
    {
        super(id);
        ndims = 0;
        componentType = null;
    }

    public JNEArrayDimensions getArrayDimensionsNode()
    {
        return (JNEArrayDimensions)jjtGetChild(1);
    }

    public JNEArrayInitializer getArrayInitializerNode()
    {
        if(3 == jjtGetNumChildren())
            return (JNEArrayInitializer)jjtGetChild(2);
        else
            return null;
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        Node componentNode = jjtGetChild(0);
        componentType = (componentNode instanceof JNPrimitiveType) ? ((JNPrimitiveType)componentNode).getNamedType() : ((JNName)componentNode).getNamedClass(env);
        JNEArrayDimensions dimExprs = (JNEArrayDimensions)jjtGetChild(1);
        ndims = dimExprs.jjtGetNumChildren();
        Token token;
        if(0 == ndims)
        {
            token = dimExprs.getBeginToken();
        } else
        {
            IExpressionNode lastDimExpr = (IExpressionNode)dimExprs.jjtGetChild(ndims - 1);
            token = lastDimExpr.getEndToken().next;
        }
        Token endDims = dimExprs.getEndToken();
        for(; "[".equals(token.image) && "]".equals(token.next.image); token = token.next.next)
            ndims++;

        for(int i = 0; i < dimExprs.jjtGetNumChildren(); i++)
        {
            IExpressionNode dimExpr = (IExpressionNode)dimExprs.jjtGetChild(i);
            if(!TypeMethods.isIntExpression(dimExpr, env))
                throw new IllegalTypeException(this, env, "Array dimension not an int: " + dimExpr.printToString());
        }

        Class arrayType = Array.newInstance(componentType, new int[ndims]).getClass();
        if(3 == jjtGetNumChildren())
        {
            JNEArrayInitializer initNode = (JNEArrayInitializer)jjtGetChild(2);
            initializerDims = initNode.checkInitializerTypes(arrayType, env);
            for(int i = 0; i < dimExprs.jjtGetNumChildren(); i++)
            {
                IExpressionNode dimExpr = (IExpressionNode)dimExprs.jjtGetChild(i);
                if(EvalMethods.isConstant(dimExpr, env))
                {
                    Object dimWrapper;
                    try
                    {
                        dimWrapper = EvalMethods.eval(dimExpr, env);
                    }
                    catch(ThrowException e)
                    {
                        throw new VerifyingError(this, env, e);
                    }
                    if(initializerDims[i] > PrimitiveOperations.unaryPromoteToInt(dimWrapper))
                        throw new IllegalConstructException(this, env, "Array initializer has larger dimensions than specified dimensions.");
                }
            }

        }
        return arrayType;
    }

    public boolean computeIsConstant(IEnv env)
    {
        return false;
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        if(null == componentType || 0 == ndims)
            throw new Error("Unverified");
        int dimensions[] = new int[ndims];
        JNEArrayDimensions dimExprs = (JNEArrayDimensions)jjtGetChild(1);
        for(int i = 0; i < ndims; i++)
            if(i < dimExprs.jjtGetNumChildren())
            {
                IExpressionNode dimExpr = (IExpressionNode)dimExprs.jjtGetChild(i);
                Object dimWrapper = EvalMethods.eval(dimExpr, env);
                dimensions[i] = PrimitiveOperations.unaryPromoteToInt(dimWrapper);
            } else
            {
                dimensions[i] = initializerDims[i];
            }

        try
        {
            Object array = Array.newInstance(componentType, dimensions);
            if(3 == jjtGetNumChildren())
                ((JNEArrayInitializer)jjtGetChild(2)).evalInitializer(array, env);
            return array;
        }
        catch(NegativeArraySizeException e)
        {
            throw new ThrowException(e);
        }
    }

    protected int ndims;
    protected int initializerDims[];
    protected Class componentType;
}
