// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   strings/IndentWriter.java

package edu.mit.ai.psg.strings;

import java.io.PrintWriter;

// Referenced classes of package edu.mit.ai.psg.strings:
//            Indent

public class IndentWriter extends PrintWriter
{

    boolean isAtLeftMargin()
    {
        return needsIndent;
    }

    public IndentWriter(PrintWriter str)
    {
        super(str, true);
        indent = "  ";
        needsIndent = false;
    }

    public IndentWriter(PrintWriter str, String indent)
    {
        super(str, true);
        this.indent = "  ";
        needsIndent = false;
        this.indent = indent;
    }

    public void println()
    {
        ((PrintWriter)super.out).println();
        needsIndent = true;
    }

    protected void printIndentIfNeeded()
    {
        if(needsIndent)
        {
            ((PrintWriter)super.out).print(indent);
            needsIndent = false;
        }
    }

    public void print(String string)
    {
        printIndentIfNeeded();
        String indentedString = Indent.string(indent, string);
        ((PrintWriter)super.out).print(indentedString);
        if(indentedString.length() > 0)
        {
            char lastChar = indentedString.charAt(indentedString.length() - 1);
            if(lastChar == '\n' || lastChar == '\r' || lastChar == '\f')
                needsIndent = true;
        }
    }

    public void println(String string)
    {
        print(string);
        println();
    }

    public String indent;
    boolean needsIndent;
}
