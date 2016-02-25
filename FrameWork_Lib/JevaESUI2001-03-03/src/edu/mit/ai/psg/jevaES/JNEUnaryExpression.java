// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEUnaryExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionNode, IllegalTypeException, JevaESParserConstants, 
//            VerifyingException, ThrowException, SimpleNode, JevaNode, 
//            EvalMethods, Token, TypeMethods, PrimitiveOperations, 
//            IEnv

public class JNEUnaryExpression extends ExpressionNode
    implements JevaESParserConstants
{

    JNEUnaryExpression(int id)
    {
        super(id);
    }

    public IExpressionNode getExpression()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        Token operator = getBeginToken();
        Class childType = EvalMethods.getType(getExpression(), env);
        switch(operator.kind)
        {
        case 78: // 'N'
            if(Boolean.TYPE == childType)
                return childType;
            else
                throw new IllegalTypeException(this, env, "! operand must be boolean: " + childType.getName());

        case 79: // 'O'
            if(TypeMethods.isIntegralType(childType))
                return TypeMethods.unaryPromoteType(childType);
            else
                throw new IllegalTypeException(this, env, "~ operand must be primitive integral number: " + childType.getName());

        case 90: // 'Z'
        case 91: // '['
            if(TypeMethods.isNumericType(childType))
                return TypeMethods.unaryPromoteType(childType);
            else
                throw new IllegalTypeException(this, env, operator.image + " operand must be primitive number: " + childType.getName());
        }
        throw JevaNode.shouldNotHappen("expected !, ~, -, or + operator: " + operator.image);
    }

    public boolean computeIsConstant(IEnv env)
        throws VerifyingException
    {
        return EvalMethods.isConstant(getExpression(), env);
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        Token operator = getBeginToken();
        Object value = EvalMethods.eval(getExpression(), env);
        switch(operator.kind)
        {
        case 78: // 'N'
            return PrimitiveOperations.toBoolean(!((Boolean)value).booleanValue());

        case 79: // 'O'
            return PrimitiveOperations.bitwiseComplement(value);

        case 91: // '['
            return PrimitiveOperations.negate(value);

        case 90: // 'Z'
            return PrimitiveOperations.unaryPlus(value);
        }
        throw JevaNode.shouldNotHappen("expected !,~,-, or +: " + operator.image);
    }
}
