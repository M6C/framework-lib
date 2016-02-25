// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSBlock.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IStatementNode, VerifyingException, AbruptCompletionException, 
//            SimpleNode, EvalMethods, IEnv

public class JNSBlock extends JevaNode
    implements IStatementNode
{

    JNSBlock(int id)
    {
        super(id);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        IEnv envInBlock = env;
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            IStatementNode statement = (IStatementNode)jjtGetChild(i);
            envInBlock = EvalMethods.verifyTypes(statement, envInBlock);
        }

        return env;
    }

    public IEnv eval(IEnv env)
        throws AbruptCompletionException
    {
        IEnv envInBlock = env;
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            IStatementNode statement = (IStatementNode)jjtGetChild(i);
            envInBlock = EvalMethods.eval(statement, envInBlock);
        }

        return env;
    }
}
