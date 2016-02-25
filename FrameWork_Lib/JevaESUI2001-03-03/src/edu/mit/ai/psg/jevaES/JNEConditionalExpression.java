// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEConditionalExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionNode, IllegalTypeException, ThrowException, 
//            VerifyingError, VerifyingException, SimpleNode, EvalMethods, 
//            IJevaNode, TypeMethods, JevaNode, IEnv

public class JNEConditionalExpression extends ExpressionNode
{

    JNEConditionalExpression(int id)
    {
        super(id);
    }

    public IExpressionNode getTestExpression()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    public IExpressionNode getThenExpression()
    {
        return (IExpressionNode)jjtGetChild(1);
    }

    public IExpressionNode getElseExpression()
    {
        return (IExpressionNode)jjtGetChild(2);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        IExpressionNode testExpr = getTestExpression();
        if(Boolean.TYPE != EvalMethods.getType(testExpr, env))
            throw new IllegalTypeException(this, env, "Test expression must be boolean: " + testExpr.printToString());
        IExpressionNode expr1 = getThenExpression();
        IExpressionNode expr2 = getElseExpression();
        Class type1 = EvalMethods.getType(expr1, env);
        Class type2 = EvalMethods.getType(expr2, env);
        if(type1 == type2)
            return type1;
        if(TypeMethods.isNumericType(type1) && TypeMethods.isNumericType(type2))
        {
            if(TypeMethods.canNarrowPrimitiveConstant(type1, type2, expr2, env))
                return type1;
            if(TypeMethods.canNarrowPrimitiveConstant(type1, type2, expr2, env))
                return type2;
            else
                return TypeMethods.binaryPromoteType(type1, type2);
        }
        if(type1 == null && TypeMethods.isReferenceType(type2))
            return type2;
        if(type2 == null && TypeMethods.isReferenceType(type1))
            return type1;
        if(TypeMethods.isAssignable(type1, expr2, env))
            return type1;
        if(TypeMethods.isAssignable(type2, expr1, env))
            return type2;
        else
            throw new IllegalTypeException(this, env, " ? : operator can't reconcile types of then and else expressions: " + TypeMethods.toSourceString(type1) + ", " + TypeMethods.toSourceString(type2));
    }

    public boolean computeIsConstant(IEnv env)
        throws VerifyingException
    {
        IExpressionNode testExpr = getTestExpression();
        IExpressionNode thenExpr = getThenExpression();
        IExpressionNode elseExpr = getElseExpression();
        try
        {
            if(EvalMethods.isConstant(testExpr, env))
                if(Boolean.TRUE.equals(EvalMethods.eval(testExpr, env)))
                    return EvalMethods.isConstant(thenExpr, env);
                else
                    return EvalMethods.isConstant(elseExpr, env);
            if(EvalMethods.isConstant(thenExpr, env) && EvalMethods.isConstant(elseExpr, env))
            {
                Object thenValue = EvalMethods.eval(thenExpr, env);
                Object elseValue = EvalMethods.eval(elseExpr, env);
                return thenValue.equals(elseValue);
            } else
            {
                return false;
            }
        }
        catch(ThrowException e)
        {
            throw new VerifyingError(this, env, e);
        }
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        Object testValue = EvalMethods.eval(getTestExpression(), env);
        if(Boolean.TRUE.equals(testValue))
            return EvalMethods.eval(getThenExpression(), env);
        if(Boolean.FALSE.equals(testValue))
            return EvalMethods.eval(getElseExpression(), env);
        else
            throw JevaNode.shouldNotHappen("expected Boolean: " + testValue);
    }
}
