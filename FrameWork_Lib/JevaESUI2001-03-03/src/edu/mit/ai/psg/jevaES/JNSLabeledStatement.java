// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSLabeledStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IStatementNode, EnvLabel, IllegalNameException, 
//            BreakException, VerifyingException, AbruptCompletionException, SimpleNode, 
//            Token, IEnv, EvalMethods

public class JNSLabeledStatement extends JevaNode
    implements IStatementNode
{

    JNSLabeledStatement(int id)
    {
        super(id);
    }

    public IStatementNode getStatement()
    {
        return (IStatementNode)jjtGetChild(0);
    }

    public String getLabel()
    {
        return getBeginToken().image;
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        String label = getLabel();
        if(null == env.lookupLabel(label, false))
            return EvalMethods.verifyTypes(getStatement(), new EnvLabel(label, getStatement(), env));
        else
            throw new IllegalNameException(this, env, "Label already in use: " + label);
    }

    public IEnv eval(IEnv env)
        throws AbruptCompletionException
    {
        String label = getLabel();
        try
        {
            return EvalMethods.eval(getStatement(), new EnvLabel(label, getStatement(), env));
        }
        catch(BreakException breakException)
        {
            if(label.equals(breakException.getLabel()))
                return env;
            else
                throw breakException;
        }
    }
}
