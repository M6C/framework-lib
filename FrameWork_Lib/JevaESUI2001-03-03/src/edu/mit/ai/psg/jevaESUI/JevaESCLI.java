// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/JevaESCLI.java

package edu.mit.ai.psg.jevaESUI;

import edu.mit.ai.psg.jevaES.*;
import edu.mit.ai.psg.strings.Stringify;
import java.io.*;
import java.text.MessageFormat;
import java.util.Vector;

// Referenced classes of package edu.mit.ai.psg.jevaESUI:
//            JevaESUI

public class JevaESCLI
{

    public static void main(String arguments[])
    {
        String initStatements = null;
        String herald = JevaESUI.defaultHerald();
        String message = "";
        if(arguments != null)
        {
            for(int i = 0; i < arguments.length; i++)
                if("-noninteractive".equals(arguments[i]))
                    interactive = false;
                else
                if("-initStatements".equals(arguments[i]) && i + 1 < arguments.length)
                {
                    if(null == initStatements)
                        initStatements = arguments[++i];
                    else
                        initStatements = initStatements + arguments[++i];
                } else
                if("-message".equals(arguments[i]) && i + 1 < arguments.length)
                    message = message + JevaESUI.convertNewlines(arguments[++i]);
                else
                if("-herald".equals(arguments[i]) && i + 1 < arguments.length)
                {
                    herald = JevaESUI.convertNewlines(arguments[++i]);
                } else
                {
                    if(arguments.length > 0)
                        System.out.println(Stringify.printToString(arguments));
                    System.out.println(JevaESUI.getJevaESUIBundleString("JevaESCLIUsage"));
                    System.exit(1);
                }

        }
        replTop(0, herald + message, initStatements);
        System.exit(0);
    }

    private JevaESCLI()
    {
    }

    public static void replTop()
    {
        replTop(0, null, null);
    }

    public static void replTop(int options, String initStatements)
    {
        replTop(options, null, initStatements);
    }

    public static void replTop(int options, String initialMessage, String initStatements)
    {
        try
        {
            repl(options, initialMessage, null, null, null, JevaESUI.ThrowsThrowableDeclaration, null, initStatements);
        }
        catch(Throwable throwable)
        {
            System.exit(1);
        }
    }

    public static Object repl()
        throws Throwable
    {
        return repl(0, null, null, null, java.lang.Object.class, JevaESUI.ThrowsThrowableDeclaration, null, null);
    }

    public static Object repl(int options, String initialMessage, Reader inReader, PrintWriter outWriter, Class returnClass, Class throwsClasses[], IEnv env, String initStatements)
        throws Throwable
    {
        StringWriter initErrsWriter = new StringWriter();
        PrintWriter initErrs = new PrintWriter(initErrsWriter);
        if(initialMessage == null)
            initialMessage = JevaESUI.defaultHerald();
        if(inReader == null)
            inReader = new InputStreamReader(System.in);
        if(outWriter == null)
            outWriter = new PrintWriter(System.out, true);
        env = JevaESUI.initialEnv(env, options, returnClass, throwsClasses, initStatements, initErrs);
        env = JevaES.extendEnv(true, java.util.Vector.class, "localResults", results, env);
        if(interactive)
            outWriter.println(initialMessage);
        if(interactive && initErrsWriter.getBuffer().length() > 0)
            outWriter.println(initErrsWriter + "\n");
        StringWriter inputWriter = new StringWriter();
        for(boolean atEOF = false; !atEOF;)
        {
            int nextIndex = results.size();
            if(interactive)
            {
                outWriter.print(MessageFormat.format(inputWriter.getBuffer().length() != 0 ? continuePromptFormat : promptFormat, new Object[] {
                    new Integer(nextIndex)
                }));
                outWriter.flush();
            }
            do
            {
                int c = inReader.read();
                if(c == -1)
                {
                    atEOF = true;
                    break;
                }
                inputWriter.write(c);
            } while(inReader.ready());
            int endParsePos = 0;
            for(StringBuffer inputBuf = inputWriter.getBuffer(); inputBuf.length() > endParsePos; inputBuf.delete(0, endParsePos))
            {
                String text = inputBuf.toString() + "\n";
                endParsePos = 0;
                try
                {
                    try
                    {
                        edu.mit.ai.psg.jevaES.IStatementNode stmt = JevaES.parseStringStatement(text);
                        endParsePos = JevaESUI.calculatePositionInString(stmt.getEndToken(), text);
                        env = EvalMethods.evaluate(stmt, env);
                        results.add(null);
                        continue;
                    }
                    catch(ParseException stmtExc)
                    {
                        try
                        {
                            edu.mit.ai.psg.jevaES.IExpressionNode expr = JevaES.parseStringExpression(text);
                            endParsePos = JevaESUI.calculatePositionInString(expr.getEndToken(), text);
                            Object result = EvalMethods.evaluate(expr, env);
                            results.add(result);
                            outWriter.println(Stringify.printToString(result));
                            continue;
                        }
                        catch(ParseException exprExc)
                        {
                            int endStmtParsePos = JevaESUI.calculatePositionInString(stmtExc.currentToken.next, text);
                            int endExprParsePos = JevaESUI.calculatePositionInString(exprExc.currentToken.next, text);
                            if(Math.max(endStmtParsePos, endExprParsePos) < text.length() || atEOF)
                            {
                                outWriter.println(JevaESUI.formatParseException(endStmtParsePos < endExprParsePos ? exprExc : stmtExc));
                                inputBuf.delete(0, text.length());
                            }
                        }
                    }
                    break;
                }
                catch(VerifyingException e)
                {
                    outWriter.println(e);
                    inputBuf.delete(0, text.length());
                    break;
                }
                catch(ReturnException returnEx)
                {
                    Object value = returnEx.getValue();
                    results.add(value);
                    if(null != env.lookupReturnType())
                        return value;
                    outWriter.println(Stringify.printToString(value));
                }
                catch(ThrowException throwEx)
                {
                    Throwable thrown = throwEx.getThrown();
                    results.add(thrown);
                    if(null != env.lookupReturnType())
                        throw thrown;
                    outWriter.println(text.substring(0, endParsePos));
                    outWriter.println(JevaESUI.getJevaESUIBundleString("UncaughtException") + thrown);
                    inputBuf.delete(0, text.length());
                    if(!interactive)
                        throw thrown;
                }
                catch(Throwable t)
                {
                    outWriter.println(text.substring(0, endParsePos));
                    outWriter.println(JevaESUI.getJevaESUIBundleString("InternalError") + t);
                    inputBuf.delete(0, text.length());
                    if(!interactive)
                        throw t;
                }
            }

        }

        if(interactive)
            outWriter.println(JevaESUI.getJevaESUIBundleString("EOF---bye!"));
        return null;
    }

    static final String rkPromptFormat = "promptFormat";
    static final String rkContinuePromptFormat = "continuePromptFormat";
    static final String rkJevaESCLIUsage = "JevaESCLIUsage";
    static final String rkEofBye = "EOF---bye!";
    public static Vector results = new Vector();
    public static String promptFormat = JevaESUI.getJevaESUIBundleString("promptFormat");
    public static String continuePromptFormat = JevaESUI.getJevaESUIBundleString("continuePromptFormat");
    static boolean interactive = true;

}
