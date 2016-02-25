// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/EnvLabel.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            EnvBase, JNSSwitchStatement, IEnv, JevaESParserConstants, 
//            IJevaNode, Token, IStatementNode

class EnvLabel extends EnvBase
    implements IEnv, JevaESParserConstants
{

    public EnvLabel(IStatementNode node, IEnv nextEnv)
    {
        super(nextEnv);
        label = "";
        statement = node;
    }

    public EnvLabel(String label, IStatementNode node, IEnv nextEnv)
    {
        super(nextEnv);
        this.label = label;
        statement = node;
    }

    public String toString()
    {
        return "{Label: " + label + ":" + statement.getBeginToken().image + ", " + getNext() + "}";
    }

    public IStatementNode lookupLabel(String queryName, boolean isForContinue)
    {
        if(label.equals(queryName) && (!isForContinue || !"".equals(queryName) || !(statement instanceof JNSSwitchStatement)))
            return statement;
        else
            return super.lookupLabel(queryName, isForContinue);
    }

    String label;
    IStatementNode statement;
}
