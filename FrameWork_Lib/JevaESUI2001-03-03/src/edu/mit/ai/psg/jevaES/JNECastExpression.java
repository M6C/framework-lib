// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNECastExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, JNType, IExpressionNode, VerifyingException, 
//            EvaluatingError, ThrowException, SimpleNode, EvalMethods, 
//            TypeMethods, PrimitiveOperations, IEnv

public class JNECastExpression extends ExpressionNode
{

    JNECastExpression(int id)
    {
        super(id);
    }

    public final Class getCastType(IEnv env)
        throws VerifyingException
    {
        return ((JNType)jjtGetChild(0)).toType(env);
    }

    public final IExpressionNode getExpr()
    {
        return (IExpressionNode)jjtGetChild(1);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        Class exprType = EvalMethods.getType(getExpr(), env);
        Class castType = getCastType(env);
        TypeMethods.checkCastTypes(castType, exprType);
        return castType;
    }

    public boolean computeIsConstant(IEnv env)
        throws VerifyingException
    {
        return EvalMethods.isConstant(getExpr(), env);
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        Object value = EvalMethods.eval(getExpr(), env);
        Class castType;
        try
        {
            castType = EvalMethods.getType(this, env);
        }
        catch(VerifyingException e)
        {
            throw new EvaluatingError(this, env, e);
        }
        if(TypeMethods.isReferenceType(castType))
        {
            Class valueType = null != value ? value.getClass() : null;
            if(TypeMethods.canWidenReference(castType, valueType))
                return value;
            else
                throw new ThrowException(new ClassCastException("(" + TypeMethods.toSourceString(castType) + ")" + TypeMethods.toSourceString(valueType)));
        } else
        {
            return PrimitiveOperations.castPrimitive(castType, value);
        }
    }
}
