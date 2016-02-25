// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSEmptyStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IStatementNode, IEnv

public class JNSEmptyStatement extends JevaNode
    implements IStatementNode
{

    JNSEmptyStatement(int id)
    {
        super(id);
    }

    public IEnv eval(IEnv env)
    {
        return env;
    }

    public IEnv verifyTypes(IEnv env)
    {
        return env;
    }
}
