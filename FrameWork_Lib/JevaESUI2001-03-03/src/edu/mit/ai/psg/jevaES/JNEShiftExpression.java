// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEShiftExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            LeftAssociativeOperatorExpression, JevaESParserConstants, TypeMethods, Token, 
//            PrimitiveOperations, JevaNode

public class JNEShiftExpression extends LeftAssociativeOperatorExpression
    implements JevaESParserConstants
{

    JNEShiftExpression(int id)
    {
        super(id);
    }

    public Class binaryCombineTypes(Token operator, Class leftType, Class rightType)
    {
        return TypeMethods.binaryCombineShiftTypes(operator, leftType, rightType);
    }

    public Object binaryCombine(Token operator, Object leftResult, Object rightResult)
    {
        switch(operator.kind)
        {
        case 98: // 'b'
            return PrimitiveOperations.leftShift(leftResult, rightResult);

        case 99: // 'c'
            return PrimitiveOperations.rightSignedShift(leftResult, rightResult);

        case 100: // 'd'
            return PrimitiveOperations.rightUnsignedShift(leftResult, rightResult);
        }
        throw JevaNode.shouldNotHappen("Expected one of <<, >>, or >>> for operator: " + operator.image);
    }
}
