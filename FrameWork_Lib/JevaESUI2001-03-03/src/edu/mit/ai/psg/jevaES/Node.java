// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/Node.java

package edu.mit.ai.psg.jevaES;


public interface Node
{

    public abstract void jjtOpen();

    public abstract void jjtClose();

    public abstract void jjtSetParent(Node node);

    public abstract Node jjtGetParent();

    public abstract void jjtAddChild(Node node, int i);

    public abstract Node jjtGetChild(int i);

    public abstract int jjtGetNumChildren();
}
