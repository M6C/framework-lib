// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNResultType.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, JNType, JevaESParserConstants, VerifyingException, 
//            Token, SimpleNode, IEnv

public class JNResultType extends JevaNode
    implements JevaESParserConstants
{

    JNResultType(int id)
    {
        super(id);
    }

    public Class toType(IEnv env)
        throws VerifyingException
    {
        if(getBeginToken().kind == 52)
            return Void.TYPE;
        else
            return ((JNType)jjtGetChild(0)).toType(env);
    }
}
