// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSBreakStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IllegalConstructException, IllegalNameException, BreakException, 
//            IStatementNode, JevaESParserConstants, VerifyingException, Token, 
//            IEnv

public class JNSBreakStatement extends JevaNode
    implements IStatementNode, JevaESParserConstants
{

    JNSBreakStatement(int id)
    {
        super(id);
    }

    public String getLabel()
    {
        if(getBeginToken().next.kind == 72)
            return "";
        else
            return getBeginToken().next.image;
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        String label = getLabel();
        if(null != env.lookupLabel(label, false))
            return env;
        if("".equals(label))
            throw new IllegalConstructException(this, env, "Break with no label must be in switch, for, while, or do");
        else
            throw new IllegalNameException(this, env, "No such label: " + label);
    }

    public IEnv eval(IEnv env)
        throws BreakException
    {
        throw new BreakException(getLabel());
    }
}
