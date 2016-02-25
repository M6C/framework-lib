// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNERelationalExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            LeftAssociativeOperatorExpression, JevaESParserConstants, TypeMethods, Token, 
//            PrimitiveOperations, JevaNode

public class JNERelationalExpression extends LeftAssociativeOperatorExpression
    implements JevaESParserConstants
{

    JNERelationalExpression(int id)
    {
        super(id);
    }

    public Class binaryCombineTypes(Token operator, Class leftType, Class rightType)
    {
        if(TypeMethods.isNumericType(leftType) && TypeMethods.isNumericType(rightType))
            return Boolean.TYPE;
        else
            throw new IllegalArgumentException("Operands to " + operator.image + " not of primitive numeric types: " + leftType.getName() + operator.image + rightType.getName());
    }

    public Object binaryCombine(Token operator, Object accumResult, Object nextResult)
    {
        switch(operator.kind)
        {
        case 77: // 'M'
            return PrimitiveOperations.lessThan(accumResult, nextResult);

        case 76: // 'L'
            return PrimitiveOperations.greaterThan(accumResult, nextResult);

        case 83: // 'S'
            return PrimitiveOperations.lessThanOrEqualTo(accumResult, nextResult);

        case 84: // 'T'
            return PrimitiveOperations.greaterThanOrEqualTo(accumResult, nextResult);

        case 78: // 'N'
        case 79: // 'O'
        case 80: // 'P'
        case 81: // 'Q'
        case 82: // 'R'
        default:
            throw JevaNode.shouldNotHappen("Expected <,>,<=, or >= token: " + operator.image);
        }
    }
}
