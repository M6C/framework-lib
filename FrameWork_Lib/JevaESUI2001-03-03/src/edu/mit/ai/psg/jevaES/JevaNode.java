// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JevaNode.java

package edu.mit.ai.psg.jevaES;

import java.io.PrintWriter;
import java.io.StringWriter;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            SimpleNode, IJevaNode, Token, EvalMethods

public class JevaNode extends SimpleNode
    implements IJevaNode
{

    public JevaNode(int id)
    {
        super(id);
    }

    public Token getBeginToken()
    {
        return begin;
    }

    public Token getEndToken()
    {
        return end;
    }

    public void setBeginToken(Token t)
    {
        begin = t;
    }

    public void setEndToken(Token t)
    {
        end = t;
    }

    public void print(PrintWriter stream)
    {
        print(stream, true);
    }

    public void print(PrintWriter stream, boolean includeSpecialTokens)
    {
        Token t = begin;
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            IJevaNode child = (IJevaNode)jjtGetChild(i);
            for(Token childBegin = child.getBeginToken(); t != childBegin; t = t.next)
                printToken(t, stream, includeSpecialTokens);

            child.print(stream, includeSpecialTokens);
            t = child.getEndToken();
        }

        for(; t != end; t = t.next)
            printToken(t, stream, includeSpecialTokens);

    }

    protected final void printToken(Token t, PrintWriter stream, boolean includeSpecialTokens)
    {
        if(includeSpecialTokens && t.specialToken != null)
        {
            Token sp_t;
            for(sp_t = t.specialToken; sp_t.specialToken != null; sp_t = sp_t.specialToken);
            for(; sp_t != null; sp_t = sp_t.next)
                stream.print(sp_t.image);

        }
        stream.print(t.image);
        if(!includeSpecialTokens && t != null && t.next != null)
        {
            String tImage = t.image;
            String nImage = t.next.image;
            if(tImage.length() > 0 && nImage.length() > 0 && Character.isJavaIdentifierPart(tImage.charAt(tImage.length() - 1)) && Character.isJavaIdentifierPart(nImage.charAt(0)))
                stream.print(' ');
        }
    }

    public String printToString()
    {
        return printToString(true);
    }

    public String printToString(boolean includeSpecialTokens)
    {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        print(printWriter, includeSpecialTokens);
        printWriter.flush();
        return stringWriter.toString();
    }

    protected static Error shouldNotHappen(String explain, Throwable e)
    {
        return EvalMethods.shouldNotHappen(explain, e);
    }

    protected static Error shouldNotHappen(Throwable e)
    {
        return EvalMethods.shouldNotHappen(e);
    }

    protected static Error shouldNotHappen(String explain)
    {
        return EvalMethods.shouldNotHappen(explain);
    }

    protected static IllegalStateException notVerified()
    {
        return new IllegalStateException("Not verified.");
    }

    Token begin;
    Token end;
}
