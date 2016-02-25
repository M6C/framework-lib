// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   strings/Prettify.java

package edu.mit.ai.psg.strings;

import java.io.PrintStream;

// Referenced classes of package edu.mit.ai.psg.strings:
//            Indent

public class Prettify
{
    static class BacktrackException extends Exception
    {

        BacktrackException()
        {
        }
    }

    static class Values
    {

        Values assign(Values newvals)
        {
            string = newvals.string;
            consumedToPosition = newvals.consumedToPosition;
            endColumn = newvals.endColumn;
            isMultiline = newvals.isMultiline;
            return this;
        }

        String string;
        int consumedToPosition;
        int endColumn;
        boolean isMultiline;

        Values(String s, int theConsumedToPosition, int theEndingColumn, boolean multipleLines)
        {
            string = s;
            consumedToPosition = theConsumedToPosition;
            endColumn = theEndingColumn;
            isMultiline = multipleLines;
        }
    }

    public static class JavaSyntaxNoEscapes extends JavaSyntax
    {

        public boolean isEscapeChar(char c)
        {
            return false;
        }

        public JavaSyntaxNoEscapes()
        {
        }
    }

    public static class JavaSyntax extends ASCIISyntax
    {

        public boolean isWhitespace(char c)
        {
            return Character.isWhitespace(c);
        }

        public boolean isLetterOrDigit(char c)
        {
            return Character.isJavaIdentifierPart(c);
        }

        public JavaSyntax()
        {
        }
    }

    public static class ASCIISyntax
        implements Syntax
    {

        public boolean isWhitespace(char c)
        {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f';
        }

        public boolean isLetterOrDigit(char c)
        {
            return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9';
        }

        public boolean isOpen(char c)
        {
            return c == '(' || c == '[' || c == '{';
        }

        public boolean isClose(char c)
        {
            return c == ')' || c == ']' || c == '}';
        }

        public boolean isMatchedClose(char open, char close)
        {
            return open == '(' && close == ')' || open == '[' && close == ']' || open == '{' && close == '}';
        }

        public boolean isSeparator(char c)
        {
            return c == ',' || c == ';';
        }

        public boolean isOpenQuote(char c)
        {
            return c == '"';
        }

        public boolean isCloseQuote(char c)
        {
            return c == '"';
        }

        public boolean isMatchedCloseQuote(char open, char close)
        {
            return open == '"' && close == '"';
        }

        public boolean isEscapeChar(char c)
        {
            return c == '\\';
        }

        public int skipWhitespace(String input, int position)
        {
            try
            {
                for(char c = input.charAt(position); isWhitespace(c); c = input.charAt(++position));
            }
            catch(StringIndexOutOfBoundsException stringindexoutofboundsexception) { }
            return position;
        }

        public int skipWord(String input, int position)
            throws StringIndexOutOfBoundsException
        {
            char c = input.charAt(position);
            try
            {
                while(isLetterOrDigit(c) || !isWhitespace(c) && !isSeparator(c) && !isOpen(c) && !isClose(c) && !isOpenQuote(c) && !isCloseQuote(c)) 
                {
                    if(isEscapeChar(c))
                        position++;
                    c = input.charAt(++position);
                }
            }
            catch(StringIndexOutOfBoundsException stringindexoutofboundsexception)
            {
                if(position > input.length())
                    throw new IllegalArgumentException("escape char at end of input");
            }
            return position;
        }

        public int skipString(String input, int position)
            throws StringIndexOutOfBoundsException
        {
            char open = input.charAt(position);
            if(!isOpenQuote(open))
                throw new IllegalArgumentException("doesn't start with open quote");
            try
            {
                for(char c = input.charAt(++position); !isMatchedCloseQuote(open, c); c = input.charAt(++position))
                    if(isEscapeChar(c))
                        position++;

            }
            catch(StringIndexOutOfBoundsException stringindexoutofboundsexception)
            {
                throw new IllegalArgumentException("mismatched quote");
            }
            return ++position;
        }

        public ASCIISyntax()
        {
        }
    }

    public static interface Syntax
    {

        public abstract boolean isWhitespace(char c);

        public abstract boolean isLetterOrDigit(char c);

        public abstract boolean isOpen(char c);

        public abstract boolean isClose(char c);

        public abstract boolean isMatchedClose(char c, char c1);

        public abstract boolean isSeparator(char c);

        public abstract boolean isOpenQuote(char c);

        public abstract boolean isCloseQuote(char c);

        public abstract boolean isMatchedCloseQuote(char c, char c1);

        public abstract boolean isEscapeChar(char c);

        public abstract int skipWhitespace(String s, int i);

        public abstract int skipWord(String s, int i)
            throws IllegalArgumentException;

        public abstract int skipString(String s, int i);
    }

    static class Shortest
    {

        Values stringShTerm(int start, int outCol, int widthLeft, int maxDepth, int maxLength, boolean eliding)
            throws BacktrackException
        {
            char startChar = input.charAt(start);
            if(syntax.isWhitespace(startChar))
                throw new IllegalArgumentException("illegal space at char " + start);
            if(syntax.isClose(startChar))
                throw new IllegalArgumentException("extra close at char " + start);
            if(syntax.isLetterOrDigit(startChar) || syntax.isOpen(startChar))
            {
                int end1 = syntax.skipWord(input, start);
                if(end1 < input.length() && syntax.isOpen(input.charAt(end1)))
                    return stringShCompoundTerm(start, end1 + 1, outCol, widthLeft, maxDepth, maxLength, eliding);
            }
            int end = start;
            boolean isMultiline = false;
            if(syntax.isOpenQuote(startChar))
            {
                end = syntax.skipString(input, start);
                isMultiline = -1 != input.substring(start, end).indexOf('\n');
            } else
            {
                end = syntax.skipWord(input, start);
            }
            if(end < input.length() && syntax.isSeparator(input.charAt(end)))
                end++;
            if(eliding)
                return new Values("", end, outCol, false);
            if(end - start > widthLeft)
                throw Prettify.backtrackException;
            else
                return new Values(input.substring(start, end), end, outCol + (end - start), isMultiline);
        }

        Values stringShCompoundTerm(int start, int endHead, int column, int widthLeft, int maxDepth, int maxLength, boolean eliding)
            throws BacktrackException
        {
            String head = input.substring(start, endHead);
            int headlen = eliding ? 0 : head.length();
            if(!eliding && headlen > widthLeft)
                throw Prettify.backtrackException;
            Values result1 = null;
            try
            {
                result1 = stringShTermList(endHead, column + headlen, widthLeft - headlen, maxDepth, maxLength, eliding);
                if(!eliding)
                    result1.string = head + result1.string;
                if(!result1.isMultiline || headlen <= 2)
                    return result1;
            }
            catch(BacktrackException backtrackexception) { }
            Values result2 = null;
            if(headlen > 2)
                try
                {
                    result2 = stringShTermList(endHead, column + 2, widthLeft - 2, maxDepth, maxLength, eliding);
                    result2.string = head + "\n" + Prettify.makeIndent(column + 2) + result2.string;
                    result2.isMultiline = true;
                    if(result1 != null && result1.string.length() <= result2.string.length())
                        return result1;
                    else
                        return result2;
                }
                catch(BacktrackException backtrackexception1) { }
            throw Prettify.backtrackException;
        }

        Values stringShTermList(int inStart, int initOutCol, int initWidthLeft, int maxDepth, int maxLength, boolean eliding)
            throws BacktrackException
        {
            int butLastInStart;
            int butLastLengthLeft;
            boolean butLastEliding;
            Values butLastResults;
            Values results1;
label0:
            {
                butLastInStart = inStart;
                inStart = syntax.skipWhitespace(input, inStart);
                int outCol = initOutCol;
                int widthLeft = initWidthLeft;
                int depthLeft = maxDepth <= 0 ? maxDepth : maxDepth - 1;
                int lengthLeft = maxLength;
                butLastLengthLeft = lengthLeft;
                eliding = eliding || depthLeft == 0 || lengthLeft == 0;
                boolean firstOnLine = true;
                boolean butLastFirstOnLine = true;
                boolean firstOnLineElided = eliding;
                butLastEliding = eliding;
                butLastResults = new Values("", 0, 0, false);
                results1 = new Values("", inStart, outCol, false);
                Values termResult = null;
                try
                {
                    for(; inStart < input.length() && !syntax.isClose(input.charAt(inStart)); firstOnLine = false)
                    {
                        butLastInStart = inStart;
                        butLastLengthLeft = lengthLeft;
                        butLastResults.assign(results1);
                        butLastFirstOnLine = firstOnLine;
                        butLastEliding = eliding;
                        if(!firstOnLine && !eliding)
                        {
                            outCol++;
                            widthLeft--;
                        }
                        termResult = stringShTerm(inStart, outCol, widthLeft, depthLeft, maxLength, eliding);
                        if(!eliding)
                        {
                            if(lengthLeft > 0 && --lengthLeft == 0)
                                eliding = true;
                            if(firstOnLine)
                                results1.string = termResult.string;
                            else
                                results1.string = results1.string + " " + termResult.string;
                            results1.isMultiline = results1.isMultiline || termResult.isMultiline;
                            widthLeft -= termResult.endColumn - outCol;
                            outCol = results1.endColumn = termResult.endColumn;
                        }
                        inStart = syntax.skipWhitespace(input, termResult.consumedToPosition);
                        if(eliding || !termResult.isMultiline || inStart >= input.length() || syntax.isClose(input.charAt(inStart)))
                            continue;
                        Values restResults1 = stringShTermList(inStart, initOutCol, initWidthLeft, maxDepth, lengthLeft - 1, eliding);
                        results1.string = results1.string + "\n" + Prettify.makeIndent(initOutCol) + restResults1.string;
                        results1.consumedToPosition = restResults1.consumedToPosition;
                        results1.endColumn = restResults1.endColumn;
                        results1.isMultiline = true;
                        if(firstOnLine)
                            return results1;
                        break label0;
                    }

                    StringBuffer closing = new StringBuffer(6);
                    int extendedClosingLength = 0;
                    if(butLastEliding)
                    {
                        if(!firstOnLineElided)
                            closing.append(' ');
                        closing.append("...");
                    }
                    if(inStart < input.length() && syntax.isClose(input.charAt(inStart)))
                    {
                        closing.append(input.charAt(inStart++));
                        if(inStart < input.length() && syntax.isSeparator(input.charAt(inStart)))
                        {
                            closing.append(input.charAt(inStart++));
                        } else
                        {
                            for(int extendedClosingEnd = syntax.skipWhitespace(input, inStart); extendedClosingEnd < input.length() && syntax.isClose(input.charAt(extendedClosingEnd));)
                            {
                                extendedClosingEnd = syntax.skipWhitespace(input, extendedClosingEnd + 1);
                                extendedClosingLength++;
                                if(extendedClosingEnd < input.length() && syntax.isSeparator(input.charAt(extendedClosingEnd)))
                                {
                                    extendedClosingLength++;
                                    break;
                                }
                            }

                        }
                    }
                    if(closing.length() + extendedClosingLength > widthLeft)
                        throw Prettify.backtrackException;
                    results1.string = results1.string + closing;
                    results1.endColumn += closing.length();
                    results1.consumedToPosition = inStart;
                    if(!results1.isMultiline || butLastFirstOnLine)
                        return results1;
                }
                catch(BacktrackException backtrackexception)
                {
                    if(butLastFirstOnLine)
                        throw Prettify.backtrackException;
                    results1 = null;
                }
            }
            Values results2 = stringShTermList(butLastInStart, initOutCol, initWidthLeft, maxDepth, butLastLengthLeft, butLastEliding);
            results2.string = butLastResults.string + "\n" + Prettify.makeIndent(initOutCol) + results2.string;
            results2.isMultiline = true;
            if(results1 != null && results1.string.length() <= results2.string.length())
                return results1;
            else
                return results2;
        }

        private final Syntax syntax;
        private final String input;

        Shortest(Syntax syntax, String input)
        {
            this.syntax = syntax;
            this.input = input;
        }
    }

    static class Backtracking
    {

        Values stringBTTerm(int start, int outCol, int widthLeft, int maxDepth, int maxLength, boolean eliding)
            throws BacktrackException
        {
            char startChar = input.charAt(start);
            if(syntax.isWhitespace(startChar))
                throw new IllegalArgumentException("illegal space at char " + start);
            if(syntax.isClose(startChar))
                throw new IllegalArgumentException("extra close at char " + start);
            if(syntax.isLetterOrDigit(startChar) || syntax.isOpen(startChar))
            {
                int end1 = syntax.skipWord(input, start);
                if(end1 < input.length() && syntax.isOpen(input.charAt(end1)))
                    return stringBTCompoundTerm(start, end1 + 1, outCol, widthLeft, maxDepth, maxLength, eliding);
            }
            int end = start;
            boolean isMultiline = false;
            if(syntax.isOpenQuote(startChar))
            {
                end = syntax.skipString(input, start);
                isMultiline = -1 != input.substring(start, end).indexOf('\n');
            } else
            {
                end = syntax.skipWord(input, start);
            }
            if(end < input.length() && syntax.isSeparator(input.charAt(end)))
                end++;
            if(eliding)
                return new Values("", end, outCol, false);
            if(end - start > widthLeft)
                throw Prettify.backtrackException;
            else
                return new Values(input.substring(start, end), end, outCol + (end - start), isMultiline);
        }

        Values stringBTCompoundTerm(int start, int endHead, int column, int widthLeft, int maxDepth, int maxLength, boolean eliding)
            throws BacktrackException
        {
            String head = input.substring(start, endHead);
            int headlen = eliding ? 0 : head.length();
            if(!eliding && headlen > widthLeft)
                throw Prettify.backtrackException;
            try
            {
                Values result = stringBTTermList(endHead, column + headlen, widthLeft - headlen, maxDepth, maxLength, eliding);
                if(!eliding)
                    result.string = head + result.string;
                return result;
            }
            catch(BacktrackException backtrackexception) { }
            if(headlen > 2)
                try
                {
                    Values result = stringBTTermList(endHead, column + 2, widthLeft - 2, maxDepth, maxLength, eliding);
                    result.string = head + "\n" + Prettify.makeIndent(column + 2) + result.string;
                    result.isMultiline = true;
                    return result;
                }
                catch(BacktrackException backtrackexception1) { }
            throw Prettify.backtrackException;
        }

        Values stringBTTermList(int inStart, int initOutCol, int initWidthLeft, int maxDepth, int maxLength, boolean eliding)
            throws BacktrackException
        {
            int butLastInStart = inStart;
            inStart = syntax.skipWhitespace(input, inStart);
            int outCol = initOutCol;
            int widthLeft = initWidthLeft;
            int depthLeft = maxDepth <= 0 ? maxDepth : maxDepth - 1;
            int lengthLeft = maxLength;
            int butLastLengthLeft = lengthLeft;
            eliding = eliding || depthLeft == 0 || lengthLeft == 0;
            boolean firstOnLine = true;
            boolean butLastFirstOnLine = true;
            boolean firstOnLineElided = eliding;
            boolean butLastEliding = eliding;
            Values butLastResults = new Values("", 0, 0, false);
            Values results = new Values("", inStart, outCol, false);
            Values termResult = null;
            try
            {
                while(inStart < input.length() && !syntax.isClose(input.charAt(inStart))) 
                {
                    butLastInStart = inStart;
                    butLastLengthLeft = lengthLeft;
                    butLastResults.assign(results);
                    butLastFirstOnLine = firstOnLine;
                    butLastEliding = eliding;
                    if(termResult != null && termResult.isMultiline)
                        throw Prettify.backtrackException;
                    if(!firstOnLine && !eliding)
                    {
                        outCol++;
                        widthLeft--;
                    }
                    termResult = stringBTTerm(inStart, outCol, widthLeft, depthLeft, maxLength, eliding);
                    if(!eliding)
                    {
                        if(lengthLeft > 0 && --lengthLeft == 0)
                            eliding = true;
                        if(firstOnLine)
                            results.string = termResult.string;
                        else
                            results.string = results.string + " " + termResult.string;
                        results.isMultiline = results.isMultiline || termResult.isMultiline;
                        widthLeft -= termResult.endColumn - outCol;
                        outCol = results.endColumn = termResult.endColumn;
                    }
                    inStart = syntax.skipWhitespace(input, termResult.consumedToPosition);
                    firstOnLine = false;
                }
                StringBuffer closing = new StringBuffer(6);
                int extendedClosingLength = 0;
                if(butLastEliding)
                {
                    if(!firstOnLineElided)
                        closing.append(' ');
                    closing.append("...");
                }
                if(inStart < input.length() && syntax.isClose(input.charAt(inStart)))
                {
                    closing.append(input.charAt(inStart++));
                    if(inStart < input.length() && syntax.isSeparator(input.charAt(inStart)))
                    {
                        closing.append(input.charAt(inStart++));
                    } else
                    {
                        for(int extendedClosingEnd = syntax.skipWhitespace(input, inStart); extendedClosingEnd < input.length() && syntax.isClose(input.charAt(extendedClosingEnd));)
                        {
                            extendedClosingEnd = syntax.skipWhitespace(input, extendedClosingEnd + 1);
                            extendedClosingLength++;
                            if(extendedClosingEnd < input.length() && syntax.isSeparator(input.charAt(extendedClosingEnd)))
                            {
                                extendedClosingLength++;
                                break;
                            }
                        }

                    }
                }
                if(closing.length() + extendedClosingLength > widthLeft)
                {
                    throw Prettify.backtrackException;
                } else
                {
                    results.string = results.string + closing;
                    results.endColumn += closing.length();
                    results.consumedToPosition = inStart;
                    return results;
                }
            }
            catch(BacktrackException backtrackexception) { }
            if(butLastFirstOnLine)
            {
                throw Prettify.backtrackException;
            } else
            {
                results = stringBTTermList(butLastInStart, initOutCol, initWidthLeft, maxDepth, butLastLengthLeft, butLastEliding);
                results.string = butLastResults.string + "\n" + Prettify.makeIndent(initOutCol) + results.string;
                results.isMultiline = true;
                return results;
            }
        }

        private final Syntax syntax;
        private final String input;

        Backtracking(Syntax syntax, String input)
        {
            this.syntax = syntax;
            this.input = input;
        }
    }


    public Prettify()
    {
    }

    public static String getCopyright()
    {
        return "Copyright (c) 1996--1999 Massachusetts Institute of Technology.";
    }

    public static String getNoWarranty()
    {
        return "This software is provided ``as is'' without express or implied warranty.";
    }

    public static String getVersion()
    {
        return "prerelease version of 1999.05.31";
    }

    public static String getHomepage()
    {
        return "http://www.ai.mit.edu/people/caroma/tools/";
    }

    public static String string(String uglyString)
    {
        return string(0, uglyString);
    }

    public static String string(int indent, String uglyString)
    {
        return string(indent, uglyString, widthLimit, depthLimit, lengthLimit, shorterNotFaster, defaultSyntax);
    }

    public static String string(int indent, String uglyString, boolean doShorterNotFaster)
    {
        return string(indent, uglyString, widthLimit, depthLimit, lengthLimit, doShorterNotFaster, defaultSyntax);
    }

    public static String string(int indent, String uglyString, int maxWidth)
    {
        return string(indent, uglyString, maxWidth, depthLimit, lengthLimit, shorterNotFaster, defaultSyntax);
    }

    /**
     * @deprecated Method string is deprecated
     */

    public static String string(int indent, String uglyString, int maxWidth, int maxDepth, int maxLength, boolean doShorterNotFaster)
    {
        return string(indent, uglyString, maxWidth, maxDepth, maxLength, doShorterNotFaster, defaultSyntax);
    }

    public static String string(int indent, String uglyString, int maxWidth, int maxDepth, int maxLength, boolean doShorterNotFaster, Syntax syntax)
    {
        String trimmedString = uglyString.trim();
        maxWidth = maxWidth >= 0 ? maxWidth - indent : 0x7fffffff;
        maxDepth = maxDepth >= 0 ? maxDepth + 1 : maxDepth;
        try
        {
            if(doShorterNotFaster)
                return (new Shortest(syntax, trimmedString)).stringShTermList(0, indent, maxWidth, maxDepth, maxLength, false).string;
            else
                return (new Backtracking(syntax, trimmedString)).stringBTTermList(0, indent, maxWidth, maxDepth, maxLength, false).string;
        }
        catch(BacktrackException backtrackexception)
        {
            return trimmedString;
        }
        catch(IllegalArgumentException e)
        {
            System.err.println(e.toString());
        }
        return trimmedString;
    }

    private static String makeIndent(int length)
    {
        return Indent.makeSpaces(length);
    }

    private static final String copyright = "Copyright (c) 1996--1999 Massachusetts Institute of Technology.";
    private static final String noWarranty = "This software is provided ``as is'' without express or implied warranty.";
    private static final String version = "prerelease version of 1999.05.31";
    private static final String homepage = "http://www.ai.mit.edu/people/caroma/tools/";
    public static int depthLimit = -1;
    public static int lengthLimit = -1;
    public static int widthLimit = 80;
    public static boolean shorterNotFaster = true;
    public static Syntax defaultSyntax = new JavaSyntaxNoEscapes();
    static final BacktrackException backtrackException = new BacktrackException();


}
