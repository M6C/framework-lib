// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEInstanceOfExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionNode, JNType, IllegalTypeException, 
//            VerifyingException, EvaluatingError, ThrowException, SimpleNode, 
//            EvalMethods, TypeMethods, PrimitiveOperations, IEnv

public class JNEInstanceOfExpression extends ExpressionNode
{

    JNEInstanceOfExpression(int id)
    {
        super(id);
    }

    public IExpressionNode getExpressionNode()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    public JNType getTypeNode()
    {
        return (JNType)jjtGetChild(1);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        Class exprType = EvalMethods.getType(getExpressionNode(), env);
        Class refType = getTypeNode().toType(env);
        if(exprType != null && !TypeMethods.isReferenceType(exprType))
            throw new IllegalTypeException(this, env, "Expression is not of reference type: " + exprType);
        if(!TypeMethods.isReferenceType(refType))
            throw new IllegalTypeException(this, env, "Type is not a reference type: " + refType);
        else
            return Boolean.TYPE;
    }

    public boolean computeIsConstant(IEnv env)
        throws VerifyingException
    {
        return false;
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        Object val = EvalMethods.eval(getExpressionNode(), env);
        Class classObj;
        try
        {
            classObj = getTypeNode().toType(env);
        }
        catch(VerifyingException e)
        {
            throw new EvaluatingError(this, env, e);
        }
        if(null == val)
            return Boolean.FALSE;
        else
            return PrimitiveOperations.toBoolean(classObj.isInstance(val));
    }
}
