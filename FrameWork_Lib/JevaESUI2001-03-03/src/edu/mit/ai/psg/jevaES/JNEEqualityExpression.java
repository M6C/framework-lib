// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEEqualityExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            LeftAssociativeOperatorExpression, JevaESParserConstants, ThrowException, TypeMethods, 
//            Token, PrimitiveOperations, JevaNode

public class JNEEqualityExpression extends LeftAssociativeOperatorExpression
    implements JevaESParserConstants
{

    JNEEqualityExpression(int id)
    {
        super(id);
    }

    public Class binaryCombineTypes(Token operator, Class leftType, Class rightType)
    {
        if(TypeMethods.isNumericType(leftType) == TypeMethods.isNumericType(rightType) && (leftType == Boolean.TYPE) == (rightType == Boolean.TYPE))
            return Boolean.TYPE;
        else
            throw new IllegalArgumentException("Incompatible operands to " + operator.image + " : " + leftType.getName() + operator.image + rightType.getName());
    }

    public Object binaryCombine(Token operator, Object accumResult, Object nextResult)
        throws ThrowException
    {
        Boolean value;
        if(PrimitiveOperations.isNumericValue(accumResult) && PrimitiveOperations.isNumericValue(nextResult))
            value = PrimitiveOperations.equals(accumResult, nextResult);
        else
        if((accumResult instanceof Boolean) && (nextResult instanceof Boolean))
            value = PrimitiveOperations.toBoolean(((Boolean)accumResult).equals(nextResult));
        else
            value = PrimitiveOperations.toBoolean(accumResult == nextResult);
        if(82 == operator.kind)
            return value;
        if(85 == operator.kind)
            return PrimitiveOperations.toBoolean(!value.booleanValue());
        else
            throw JevaNode.shouldNotHappen("Expected == or != token: " + operator.image);
    }
}
