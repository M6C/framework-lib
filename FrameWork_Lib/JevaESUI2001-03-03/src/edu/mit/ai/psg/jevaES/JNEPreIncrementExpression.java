// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEPreIncrementExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionStoreNode, IllegalTypeException, VerifyingException, 
//            EvaluatingError, ThrowException, SimpleNode, EvalMethods, 
//            TypeMethods, IStore, PrimitiveOperations, IEnv

public class JNEPreIncrementExpression extends ExpressionNode
{

    JNEPreIncrementExpression(int id)
    {
        super(id);
    }

    public IExpressionStoreNode getStoreNode()
    {
        return (IExpressionStoreNode)jjtGetChild(0);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        IExpressionStoreNode target = getStoreNode();
        Class targetClass = EvalMethods.getType(target, env);
        if(TypeMethods.isNumericType(targetClass))
            return targetClass;
        else
            throw new IllegalTypeException(this, env, "++ Prefix requires numeric argument");
    }

    public boolean computeIsConstant(IEnv env)
    {
        return false;
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        IExpressionStoreNode target = getStoreNode();
        Class targetType;
        try
        {
            targetType = EvalMethods.getType(target, env);
        }
        catch(VerifyingException e)
        {
            throw new EvaluatingError(this, env, e);
        }
        IStore store = EvalMethods.evalToStore(target, env);
        Object previousValue = store.getValue();
        Object newValue = PrimitiveOperations.castPrimitive(targetType, PrimitiveOperations.add(previousValue, one));
        store.setValue(newValue);
        return newValue;
    }

    static final Integer one = new Integer(1);

}
