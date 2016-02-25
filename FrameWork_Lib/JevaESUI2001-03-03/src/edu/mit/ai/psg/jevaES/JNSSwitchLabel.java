// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSSwitchLabel.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IExpressionNode, JevaESParserConstants, Token, 
//            SimpleNode

public class JNSSwitchLabel extends JevaNode
    implements JevaESParserConstants
{

    JNSSwitchLabel(int id)
    {
        super(id);
    }

    public boolean isCase()
    {
        return 9 == getBeginToken().kind;
    }

    public boolean isDefault()
    {
        return 15 == getBeginToken().kind;
    }

    public IExpressionNode getExpression()
    {
        return (IExpressionNode)jjtGetChild(0);
    }
}
