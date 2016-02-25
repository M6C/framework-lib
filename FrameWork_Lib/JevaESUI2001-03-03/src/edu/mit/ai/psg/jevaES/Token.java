// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/Token.java

package edu.mit.ai.psg.jevaES;


public class Token
{

    public Token()
    {
    }

    public final String toString()
    {
        return image;
    }

    public static final Token newToken(int ofKind)
    {
        switch(ofKind)
        {
        default:
            return new Token();
        }
    }

    public int kind;
    public int beginLine;
    public int beginColumn;
    public int endLine;
    public int endColumn;
    public String image;
    public Token next;
    public Token specialToken;
}
