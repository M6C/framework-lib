// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNPrimitiveType.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, Token, TypeMethods

public class JNPrimitiveType extends JevaNode
{

    JNPrimitiveType(int id)
    {
        super(id);
    }

    public Class getNamedType()
    {
        try
        {
            return TypeMethods.nameToPrimitiveType(getBeginToken().image);
        }
        catch(ClassNotFoundException e)
        {
            throw JevaNode.shouldNotHappen(e);
        }
    }
}
