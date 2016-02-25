// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEAdditiveExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            LeftAssociativeOperatorExpression, JevaESParserConstants, ThrowException, Token, 
//            TypeMethods, PrimitiveOperations, JevaNode

public class JNEAdditiveExpression extends LeftAssociativeOperatorExpression
    implements JevaESParserConstants
{

    JNEAdditiveExpression(int id)
    {
        super(id);
    }

    public Class binaryCombineTypes(Token operator, Class accumType, Class nextType)
    {
        if(90 == operator.kind && ((java.lang.String.class) == accumType || (java.lang.String.class) == nextType))
            return java.lang.String.class;
        else
            return TypeMethods.binaryCombineNumericTypes(operator, accumType, nextType);
    }

    public Object binaryCombine(Token operator, Object accumResult, Object nextResult)
        throws ThrowException
    {
        if(90 == operator.kind)
            return PrimitiveOperations.add(accumResult, nextResult);
        if(91 == operator.kind)
            return PrimitiveOperations.subtract(accumResult, nextResult);
        else
            throw JevaNode.shouldNotHappen("Expected + or - token: " + operator.image);
    }
}
