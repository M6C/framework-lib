// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEPostfixExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionStoreNode, IllegalTypeException, VerifyingException, 
//            EvaluatingError, JevaESParserConstants, ThrowException, SimpleNode, 
//            IJevaNode, Token, EvalMethods, TypeMethods, 
//            JevaNode, IStore, PrimitiveOperations, IEnv

public class JNEPostfixExpression extends ExpressionNode
    implements JevaESParserConstants
{

    JNEPostfixExpression(int id)
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
        Token op = target.getEndToken();
        if(op.kind == 88 || op.kind == 89)
        {
            Class targetClass = EvalMethods.getType(target, env);
            if(TypeMethods.isNumericType(targetClass))
                return targetClass;
            else
                throw new IllegalTypeException(this, env, "Postfix " + op.image + " requires numeric argument.");
        } else
        {
            throw JevaNode.shouldNotHappen("expected ++ or --: " + op.image);
        }
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
        Token op = target.getEndToken();
        IStore store = EvalMethods.evalToStore(target, env);
        Object previousValue = store.getValue();
        store.setValue(PrimitiveOperations.castPrimitive(targetType, op.kind != 88 ? PrimitiveOperations.subtract(previousValue, one) : PrimitiveOperations.add(previousValue, one)));
        return previousValue;
    }

    static final Integer one = new Integer(1);

}
