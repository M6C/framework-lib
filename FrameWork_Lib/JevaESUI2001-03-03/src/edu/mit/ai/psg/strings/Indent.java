// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   strings/Indent.java

package edu.mit.ai.psg.strings;


public final class Indent
{

    private Indent()
    {
    }

    public static String string(int spaces, String stringToIndent)
    {
        return string(makeSpaces(spaces).toString(), stringToIndent);
    }

    public static String string(String indent, String stringToIndent)
    {
        return string(indent, stringToIndent, false);
    }

    public static String string(String indent, String stringToIndent, boolean addToStart)
    {
        int start = 0;
        StringBuffer buf = new StringBuffer();
        if(addToStart)
        {
            buf.append(indent);
            start = indent.length();
        }
        buf.append(stringToIndent);
        for(int i = start; i < buf.length(); i++)
            if(buf.charAt(i) == '\r' && i + 1 < buf.length() && buf.charAt(i + 1) == '\n')
            {
                if(i + 2 < buf.length())
                    buf.insert(i + 2, indent);
                i += indent.length() + 1;
            } else
            if(buf.charAt(i) == '\n' || buf.charAt(i) == '\r')
            {
                if(i + 1 < buf.length())
                    buf.insert(i + 1, indent);
                i += indent.length();
            }

        return buf.toString();
    }

    public static String makeSpaces(int length)
    {
        for(int i = spaceBuffer.length(); i < length; i++)
            spaceBuffer.append(' ');

        return spaceBuffer.substring(0, length);
    }

    public static String trimNewlines(String untrimmedString)
    {
        StringBuffer buf = new StringBuffer(untrimmedString);
        int end = 0;
        for(int i = 0; i < buf.length(); i++)
        {
            char c = buf.charAt(i);
            if(c == '\r' && i + 1 < buf.length() && buf.charAt(i + 1) == '\n')
            {
                end = i + 2;
                i++;
                continue;
            }
            if(c == '\n' || c == '\r' || c == '\f')
            {
                end = i + 1;
                continue;
            }
            if(c != ' ' && c != '\t')
                break;
        }

        buf.delete(0, end);
        end = buf.length();
        for(int i = end - 1; i >= 0; i--)
        {
            char c = buf.charAt(i);
            if(c == '\n' && i - 1 >= 0 && buf.charAt(i - 1) == '\r')
            {
                end = i - 1;
                i--;
                continue;
            }
            if(c == '\n' || c == '\r' || c == '\f')
            {
                end = i;
                continue;
            }
            if(c != ' ' && c != '\t')
                break;
        }

        buf.delete(end, buf.length());
        return buf.toString();
    }

    public static final String unindent(int spaces, String stringToUnindent)
    {
        return unindent(makeSpaces(spaces), stringToUnindent);
    }

    public static final String unindent(String indent, String stringToUnindent)
    {
        StringBuffer buf = new StringBuffer(stringToUnindent);
        for(int i = 0; i < buf.length() - indent.length(); i++)
        {
            if(indent.equals(buf.substring(i, i + indent.length())))
                buf.delete(i, i + indent.length());
            for(; i < buf.length() - indent.length(); i++)
            {
                char c = buf.charAt(i);
                if(c == '\r' && i + 1 < buf.length() && buf.charAt(i + 1) == '\n')
                {
                    i++;
                    break;
                }
                if(c == '\n' || c == '\r' || c == '\f')
                    break;
            }

        }

        return buf.toString();
    }

    public static final String leadingWhitespace(String string)
    {
        for(int i = 0; i < string.length(); i++)
            if(!Character.isWhitespace(string.charAt(i)))
                return string.substring(0, i);

        return string;
    }

    private static final StringBuffer spaceBuffer = new StringBuffer();

}
