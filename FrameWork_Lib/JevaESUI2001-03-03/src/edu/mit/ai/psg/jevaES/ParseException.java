// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/ParseException.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            Token

public class ParseException extends Exception
{

    public ParseException(Token currentTokenVal, int expectedTokenSequencesVal[][], String tokenImageVal[])
    {
        super("");
        eol = System.getProperty("line.separator", "\n");
        specialConstructor = true;
        currentToken = currentTokenVal;
        expectedTokenSequences = expectedTokenSequencesVal;
        tokenImage = tokenImageVal;
    }

    public ParseException()
    {
        eol = System.getProperty("line.separator", "\n");
        specialConstructor = false;
    }

    public ParseException(String message)
    {
        super(message);
        eol = System.getProperty("line.separator", "\n");
        specialConstructor = false;
    }

    public String getMessage()
    {
        if(!specialConstructor)
            return super.getMessage();
        String expected = "";
        int maxSize = 0;
        for(int i = 0; i < expectedTokenSequences.length; i++)
        {
            if(maxSize < expectedTokenSequences[i].length)
                maxSize = expectedTokenSequences[i].length;
            for(int j = 0; j < expectedTokenSequences[i].length; j++)
                expected = expected + tokenImage[expectedTokenSequences[i][j]] + " ";

            if(expectedTokenSequences[i][expectedTokenSequences[i].length - 1] != 0)
                expected = expected + "...";
            expected = expected + eol + "    ";
        }

        String retval = "Encountered \"";
        Token tok = currentToken.next;
        for(int i = 0; i < maxSize; i++)
        {
            if(i != 0)
                retval = retval + " ";
            if(tok.kind == 0)
            {
                retval = retval + tokenImage[0];
                break;
            }
            retval = retval + add_escapes(tok.image);
            tok = tok.next;
        }

        retval = retval + "\" at line " + currentToken.next.beginLine + ", column " + currentToken.next.beginColumn + "." + eol;
        if(expectedTokenSequences.length == 1)
            retval = retval + "Was expecting:" + eol + "    ";
        else
            retval = retval + "Was expecting one of:" + eol + "    ";
        retval = retval + expected;
        return retval;
    }

    protected String add_escapes(String str)
    {
        StringBuffer retval = new StringBuffer();
        for(int i = 0; i < str.length(); i++)
        {
            char ch;
            switch(str.charAt(i))
            {
            case 0: // '\0'
                break;

            case 8: // '\b'
                retval.append("\\b");
                break;

            case 9: // '\t'
                retval.append("\\t");
                break;

            case 10: // '\n'
                retval.append("\\n");
                break;

            case 12: // '\f'
                retval.append("\\f");
                break;

            case 13: // '\r'
                retval.append("\\r");
                break;

            case 34: // '"'
                retval.append("\\\"");
                break;

            case 39: // '\''
                retval.append("\\'");
                break;

            case 92: // '\\'
                retval.append("\\\\");
                break;

            default:
                if((ch = str.charAt(i)) < ' ' || ch > '~')
                {
                    String s = "0000" + Integer.toString(ch, 16);
                    retval.append("\\u" + s.substring(s.length() - 4, s.length()));
                } else
                {
                    retval.append(ch);
                }
                break;
            }
        }

        return retval.toString();
    }

    protected boolean specialConstructor;
    public Token currentToken;
    public int expectedTokenSequences[][];
    public String tokenImage[];
    protected String eol;
}
