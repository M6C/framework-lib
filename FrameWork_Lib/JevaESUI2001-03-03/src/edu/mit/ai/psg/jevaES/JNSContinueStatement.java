// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSContinueStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IllegalConstructException, IllegalNameException, ContinueException, 
//            IStatementNode, JevaESParserConstants, VerifyingException, Token, 
//            IEnv, IJevaNode

public class JNSContinueStatement extends JevaNode
    implements IStatementNode, JevaESParserConstants
{

    JNSContinueStatement(int id)
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
        IStatementNode node = env.lookupLabel(label, true);
        if(node != null)
            switch(node.getBeginToken().kind)
            {
            case 16: // '\020'
            case 24: // '\030'
            case 54: // '6'
                return env;
            }
        if("".equals(label) || node != null)
            throw new IllegalConstructException(this, env, "May only continue a while, for, or do loop.");
        else
            throw new IllegalNameException(this, env, "No such label: " + label);
    }

    public IEnv eval(IEnv env)
        throws ContinueException
    {
        throw new ContinueException(getLabel());
    }
}
