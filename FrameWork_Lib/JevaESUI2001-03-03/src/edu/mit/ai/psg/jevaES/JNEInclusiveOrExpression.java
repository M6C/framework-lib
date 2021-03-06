// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEInclusiveOrExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            LeftAssociativeOperatorExpression, ThrowException, TypeMethods, PrimitiveOperations, 
//            Token

public class JNEInclusiveOrExpression extends LeftAssociativeOperatorExpression
{

    JNEInclusiveOrExpression(int id)
    {
        super(id);
    }

    public Class binaryCombineTypes(Token operator, Class accumType, Class nextType)
    {
        return TypeMethods.binaryCombineBitwiseTypes(operator, accumType, nextType);
    }

    public Object binaryCombine(Token operator, Object accumResult, Object nextResult)
        throws ThrowException
    {
        return PrimitiveOperations.or(accumResult, nextResult);
    }
}
