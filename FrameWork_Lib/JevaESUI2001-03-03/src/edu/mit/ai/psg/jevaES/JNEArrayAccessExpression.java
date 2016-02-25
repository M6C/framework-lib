// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEArrayAccessExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionNode, IllegalTypeException, ArrayStore, 
//            IExpressionStoreNode, VerifyingException, ThrowException, SimpleNode, 
//            EvalMethods, IJevaNode, TypeMethods, IStore, 
//            PrimitiveOperations, IEnv

public class JNEArrayAccessExpression extends ExpressionNode
    implements IExpressionStoreNode
{

    JNEArrayAccessExpression(int id)
    {
        super(id);
    }

    public IExpressionNode getArrayExpressionNode()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    public IExpressionNode getIndexExpressionNode()
    {
        return (IExpressionNode)jjtGetChild(1);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        IExpressionNode arrayExpr = getArrayExpressionNode();
        Class arrayClass = EvalMethods.getType(arrayExpr, env);
        if(!arrayClass.isArray())
            throw new IllegalTypeException(this, env, "Not an array: " + getArrayExpressionNode().printToString());
        if(!TypeMethods.isIntExpression(getIndexExpressionNode(), env))
            throw new IllegalTypeException(this, env, "Array index not an int:" + getIndexExpressionNode().printToString());
        else
            return arrayClass.getComponentType();
    }

    public boolean computeIsConstant(IEnv env)
        throws VerifyingException
    {
        return EvalMethods.isConstant(getArrayExpressionNode(), env) && EvalMethods.isConstant(getIndexExpressionNode(), env);
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        return evalToStore(env).getValue();
    }

    public IStore evalToStore(IEnv env)
        throws ThrowException
    {
        Object array = EvalMethods.eval(getArrayExpressionNode(), env);
        Object indexWrapper = EvalMethods.eval(getIndexExpressionNode(), env);
        return new ArrayStore(array, PrimitiveOperations.unaryPromoteToInt(indexWrapper));
    }
}
