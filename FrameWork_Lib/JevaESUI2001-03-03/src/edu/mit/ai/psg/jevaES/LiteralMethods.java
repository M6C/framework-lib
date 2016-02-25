// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/LiteralMethods.java

package edu.mit.ai.psg.jevaES;


public class LiteralMethods
{

    private LiteralMethods()
    {
    }

    public static final Number createIntegerFromLiteral(String digits)
    {
        char lastChar = digits.charAt(digits.length() - 1);
        boolean isLong = lastChar == 'L' || lastChar == 'l';
        if(isLong)
            digits = digits.substring(0, digits.length() - 1);
        Number value;
        if(digits.equals("0"))
        {
            if(isLong)
                value = new Long(0L);
            else
                value = new Integer(0);
        } else
        if(digits.charAt(0) != '0')
        {
            if(isLong)
                value = new Long(digits);
            else
                value = new Integer(digits);
        } else
        {
            char secondChar = digits.charAt(1);
            if(secondChar != 'x' && secondChar != 'X')
            {
                if(isLong)
                    value = new Long(Long.parseLong(digits, 8));
                else
                    value = new Integer(Integer.parseInt(digits, 8));
            } else
            if(isLong)
                value = new Long(Long.parseLong(digits.substring(2), 16));
            else
                value = new Integer(Integer.parseInt(digits.substring(2), 16));
        }
        return value;
    }

    public static final Number createFloatingPointFromLiteral(String digits)
    {
        char lastChar = digits.charAt(digits.length() - 1);
        boolean isFloat = lastChar == 'f' || lastChar == 'F';
        if(isFloat || lastChar == 'd' || lastChar == 'D')
            digits = digits.substring(0, digits.length() - 1);
        Number value;
        if(isFloat)
            value = new Float(digits);
        else
            value = new Double(digits);
        return value;
    }

    public static final Character createCharacterFromLiteral(String s)
    {
        return new Character(createCharFromPossiblyEscapedLiteral(s, 1, s.length() - 1, null));
    }

    public static final String createStringFromLiteral(String s)
    {
        StringBuffer b = new StringBuffer();
        int nextPosReturnVal[] = {
            0
        };
        int end = s.length() - 1;
        for(int i = 1; i < end; i = nextPosReturnVal[0])
            b.append(createCharFromPossiblyEscapedLiteral(s, i, end, nextPosReturnVal));

        return b.toString();
    }

    public static final char createCharFromPossiblyEscapedLiteral(String s, int start, int end, int nextPos[])
    {
        char c = s.charAt(start);
        int endOfEscape = start + 1;
        if('\\' == c)
        {
            char escapedChar = s.charAt(start + 1);
            endOfEscape++;
            if('n' == escapedChar)
                c = '\n';
            else
            if('r' == escapedChar)
                c = '\r';
            else
            if('t' == escapedChar)
                c = '\t';
            else
            if('b' == escapedChar)
                c = '\b';
            else
            if('f' == escapedChar)
                c = '\f';
            else
            if('\'' == escapedChar)
                c = '\'';
            else
            if('"' == escapedChar)
                c = '"';
            else
            if('\\' == escapedChar)
                c = '\\';
            else
            if(Character.isDigit(escapedChar))
            {
                for(; endOfEscape < end && endOfEscape < start + 3 && Character.isDigit(s.charAt(endOfEscape)); endOfEscape++);
                c = (char)Integer.parseInt(s.substring(start + 1, endOfEscape), 8);
            } else
            if('u' == escapedChar)
            {
                endOfEscape = start + 6;
                c = (char)Integer.parseInt(s.substring(start + 2, endOfEscape), 16);
            } else
            {
                throw new Error("Illegal char escape sequence: " + s.substring(start, start + 2));
            }
        }
        if(null != nextPos)
            nextPos[0] = endOfEscape;
        return c;
    }
}
