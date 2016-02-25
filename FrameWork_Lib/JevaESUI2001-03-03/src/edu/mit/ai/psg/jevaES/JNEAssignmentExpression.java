// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEAssignmentExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionStoreNode, IJevaNode, JNEAssignmentOperator, 
//            IExpressionNode, IllegalConstructException, JNName, JNEArrayAccessExpression, 
//            IllegalTypeException, JevaESParserConstants, VerifyingException, ThrowException, 
//            SimpleNode, EvalMethods, JevaNode, Token, 
//            TypeMethods, IStore, PrimitiveOperations, IEnv

public class JNEAssignmentExpression extends ExpressionNode
    implements JevaESParserConstants
{

    JNEAssignmentExpression(int id)
    {
        super(id);
    }

    public IExpressionStoreNode getTargetStoreNode()
    {
        try
        {
            return (IExpressionStoreNode)jjtGetChild(0);
        }
        catch(ClassCastException classcastexception)
        {
            throw new IllegalStateException("Not a storable location: " + ((IJevaNode)jjtGetChild(0)).printToString());
        }
    }

    public JNEAssignmentOperator getAssignmentOperatorNode()
    {
        return (JNEAssignmentOperator)jjtGetChild(1);
    }

    public IExpressionNode getSourceExpressionNode()
    {
        return (IExpressionNode)jjtGetChild(2);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        IExpressionStoreNode target;
        try
        {
            target = getTargetStoreNode();
        }
        catch(IllegalStateException e)
        {
            throw new IllegalConstructException(this, env, e.getMessage());
        }
        if((target instanceof JNName) && ((JNName)target).isFinal(env))
            throw new IllegalConstructException(this, env, "Can't assign to final name: " + target.printToString());
        if((target instanceof JNName) || (target instanceof JNEArrayAccessExpression))
        {
            Class targetType = EvalMethods.getType(target, env);
            IExpressionNode expr = getSourceExpressionNode();
            Class sourceType = EvalMethods.getType(expr, env);
            Token opToken = getAssignmentOperatorNode().getBeginToken();
            int op = opToken.kind;
            if(75 == op)
                if(TypeMethods.isAssignable(targetType, expr, env))
                    return targetType;
                else
                    throw new IllegalTypeException(this, env, "Can't assign a " + EvalMethods.getType(expr, env) + " to variable of type " + targetType);
            if(101 == op && (java.lang.String.class) == targetType)
                return targetType;
            Class combinedType;
            try
            {
                switch(op)
                {
                case 101: // 'e'
                case 102: // 'f'
                case 103: // 'g'
                case 104: // 'h'
                case 108: // 'l'
                    combinedType = TypeMethods.binaryCombineNumericTypes(opToken, targetType, sourceType);
                    break;

                case 105: // 'i'
                case 106: // 'j'
                case 107: // 'k'
                    combinedType = TypeMethods.binaryCombineBitwiseTypes(opToken, targetType, sourceType);
                    break;

                case 109: // 'm'
                case 110: // 'n'
                case 111: // 'o'
                    combinedType = TypeMethods.binaryCombineShiftTypes(opToken, targetType, sourceType);
                    break;

                default:
                    throw JevaNode.shouldNotHappen("unrecognized op " + opToken.image);
                }
            }
            catch(IllegalArgumentException e)
            {
                throw new IllegalTypeException(this, env, e.getMessage());
            }
            try
            {
                TypeMethods.checkCastTypes(targetType, combinedType);
            }
            catch(ClassCastException e)
            {
                throw new IllegalTypeException(this, env, e.getMessage());
            }
            return targetType;
        } else
        {
            throw JevaNode.shouldNotHappen("unrecognized target " + target);
        }
    }

    public boolean computeIsConstant(IEnv env)
    {
        return false;
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        IExpressionStoreNode targetExpr = getTargetStoreNode();
        IStore store = EvalMethods.evalToStore(targetExpr, env);
        Class targetType = store.getType();
        int op = getAssignmentOperatorNode().getBeginToken().kind;
        Object storeValue = op != 75 ? store.getValue() : null;
        IExpressionNode rightExpr = getSourceExpressionNode();
        Object value = EvalMethods.eval(rightExpr, env);
        switch(op)
        {
        case 101: // 'e'
            value = PrimitiveOperations.add(storeValue, value);
            break;

        case 102: // 'f'
            value = PrimitiveOperations.subtract(storeValue, value);
            break;

        case 103: // 'g'
            value = PrimitiveOperations.multiply(storeValue, value);
            break;

        case 104: // 'h'
            value = PrimitiveOperations.divide(storeValue, value);
            break;

        case 108: // 'l'
            value = PrimitiveOperations.remainder(storeValue, value);
            break;

        case 105: // 'i'
            value = PrimitiveOperations.and(storeValue, value);
            break;

        case 106: // 'j'
            value = PrimitiveOperations.or(storeValue, value);
            break;

        case 107: // 'k'
            value = PrimitiveOperations.xor(storeValue, value);
            break;

        case 109: // 'm'
            value = PrimitiveOperations.leftShift(storeValue, value);
            break;

        case 110: // 'n'
            value = PrimitiveOperations.rightSignedShift(storeValue, value);
            break;

        case 111: // 'o'
            value = PrimitiveOperations.rightUnsignedShift(storeValue, value);
            break;

        case 76: // 'L'
        case 77: // 'M'
        case 78: // 'N'
        case 79: // 'O'
        case 80: // 'P'
        case 81: // 'Q'
        case 82: // 'R'
        case 83: // 'S'
        case 84: // 'T'
        case 85: // 'U'
        case 86: // 'V'
        case 87: // 'W'
        case 88: // 'X'
        case 89: // 'Y'
        case 90: // 'Z'
        case 91: // '['
        case 92: // '\\'
        case 93: // ']'
        case 94: // '^'
        case 95: // '_'
        case 96: // '`'
        case 97: // 'a'
        case 98: // 'b'
        case 99: // 'c'
        case 100: // 'd'
        default:
            throw JevaNode.shouldNotHappen("unrecognized op: " + getAssignmentOperatorNode().getBeginToken().image);

        case 75: // 'K'
            break;
        }
        if(!TypeMethods.isReferenceType(targetType))
            value = PrimitiveOperations.castPrimitive(targetType, value);
        store.setValue(value);
        return value;
    }
}
