// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEMultiplicativeExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            LeftAssociativeOperatorExpression, JevaESParserConstants, ThrowException, TypeMethods, 
//            Token, PrimitiveOperations, JevaNode

public class JNEMultiplicativeExpression extends LeftAssociativeOperatorExpression
    implements JevaESParserConstants
{

    JNEMultiplicativeExpression(int id)
    {
        super(id);
    }

    public Class binaryCombineTypes(Token operator, Class accumType, Class nextType)
    {
        return TypeMethods.binaryCombineNumericTypes(operator, accumType, nextType);
    }

    public Object binaryCombine(Token operator, Object accumResult, Object nextResult)
        throws ThrowException
    {
        switch(operator.kind)
        {
        case 92: // '\\'
            return PrimitiveOperations.multiply(accumResult, nextResult);

        case 93: // ']'
            return PrimitiveOperations.divide(accumResult, nextResult);

        case 97: // 'a'
            return PrimitiveOperations.remainder(accumResult, nextResult);
        }
        throw JevaNode.shouldNotHappen("Expected *,/, or % token: " + operator.image);
    }
}
