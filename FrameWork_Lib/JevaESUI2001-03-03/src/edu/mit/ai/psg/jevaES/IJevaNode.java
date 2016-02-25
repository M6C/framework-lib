// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/IJevaNode.java

package edu.mit.ai.psg.jevaES;

import java.io.PrintWriter;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            Node, Token

public interface IJevaNode
    extends Node
{

    public abstract Token getBeginToken();

    public abstract Token getEndToken();

    public abstract void setBeginToken(Token token);

    public abstract void setEndToken(Token token);

    public abstract void print(PrintWriter printwriter);

    public abstract void print(PrintWriter printwriter, boolean flag);

    public abstract String printToString();

    public abstract String printToString(boolean flag);
}
