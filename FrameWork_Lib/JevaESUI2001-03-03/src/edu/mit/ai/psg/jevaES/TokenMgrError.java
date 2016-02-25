// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/TokenMgrError.java

package edu.mit.ai.psg.jevaES;


public class TokenMgrError extends Error
{

    protected static final String addEscapes(String str)
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

    private static final String LexicalError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar)
    {
        return "Lexical error at line " + errorLine + ", column " + errorColumn + ".  Encountered: " + (EOFSeen ? "<EOF> " : "\"" + addEscapes(String.valueOf(curChar)) + "\"" + " (" + (int)curChar + "), ") + "after : \"" + addEscapes(errorAfter) + "\"";
    }

    public String getMessage()
    {
        return super.getMessage();
    }

    public TokenMgrError()
    {
    }

    public TokenMgrError(String message, int reason)
    {
        super(message);
        errorCode = reason;
    }

    public TokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar, int reason)
    {
        this(LexicalError(EOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
    }

    static final int LEXICAL_ERROR = 0;
    static final int STATIC_LEXER_ERROR = 1;
    static final int INVALID_LEXICAL_STATE = 2;
    static final int LOOP_DETECTED = 3;
    int errorCode;
}
