// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/JevaESUI.java

package edu.mit.ai.psg.jevaESUI;

import edu.mit.ai.psg.jevaES.*;
import edu.mit.ai.psg.strings.Prettify;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class JevaESUI
{
    static class EnvStubReturnTypeAndThrows extends EnvBase
    {

        public Class lookupReturnType()
        {
            return returnType;
        }

        public Class[] lookupThrows()
        {
            return throwsTypes;
        }

        public String toString()
        {
            return "{returns " + JevaESUI.classToString(returnType) + (throwsTypes.length != 0 ? " throws " + JevaESUI.classesToString(throwsTypes) : "") + " " + getNext() + "}";
        }

        Class returnType;
        Class throwsTypes[];

        EnvStubReturnTypeAndThrows(Class returnType, Class throwsTypes[], IEnv nextEnv)
        {
            super(nextEnv);
            this.returnType = returnType;
            this.throwsTypes = throwsTypes;
        }
    }


    public JevaESUI()
    {
    }

    public static String defaultTitle()
    {
        return getJevaESUIBundleString("Title");
    }

    public static String defaultHerald()
    {
        return MessageFormat.format(getJevaESUIBundleString("HeraldFormat"), new Object[] {
            defaultTitle(), JevaES.getVersion(), JevaES.getCopyright(), JevaES.getNoWarranty(), JevaES.getHomepage()
        });
    }

    static IEnv initialEnv(IEnv env, int options, Class returnType, Class throwsTypes[], String initStatements, PrintWriter initErrs)
    {
        boolean warned = false;
        if(env == null)
            try
            {
                env = JevaES.makeDefaultEnv(options);
            }
            catch(SecurityException securityexception)
            {
                options |= 1;
                env = JevaES.makeDefaultEnv(options);
                initErrs.println(getJevaESUIBundleString("WarningUnableToCheckPackageNames"));
                warned = true;
            }
        if(JevaES.isSuppressingPackageNameChecks(env) && !warned)
            initErrs.println("[" + getJevaESUIBundleString("NotCheckingPackageNames") + "]");
        if(throwsTypes == null)
            throwsTypes = NoThrowsDeclarations;
        try
        {
            env = JevaES.parseEvalStringStatement("import edu.mit.ai.psg.jexa.Jexa;", env);
        }
        catch(Exception e)
        {
            throw new Error(e.toString());
        }
        if(initStatements != null)
            try
            {
                env = parseEvalStringAllStatements(initStatements, env);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                throw new Error(e.toString());
            }
        return new EnvStubReturnTypeAndThrows(returnType, throwsTypes, env);
    }

    static String classToString(Class c)
    {
        return c != null ? c.getName() : "null";
    }

    static String classesToString(Class classes[])
    {
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < classes.length; i++)
        {
            result.append(classToString(classes[i]));
            if(i + 1 < classes.length)
                result.append(", ");
        }

        return result.toString();
    }

    static IEnv parseEvalStringAllStatements(String s, IEnv env)
        throws ParseException, VerifyingException, AbruptCompletionException
    {
        IStatementNode block = JevaES.parseStringStatement("{" + s + "}");
        IStatementNode nodes[] = new IStatementNode[block.jjtGetNumChildren()];
        for(int i = 0; i < block.jjtGetNumChildren(); i++)
            nodes[i] = (IStatementNode)block.jjtGetChild(i);

        IEnv verifyEnv = env;
        for(int i = 0; i < nodes.length; i++)
            verifyEnv = EvalMethods.verifyTypes(nodes[i], verifyEnv);

        for(int i = 0; i < nodes.length; i++)
            env = EvalMethods.eval(nodes[i], env);

        return env;
    }

    static int calculatePositionInString(Token token, String text)
    {
        if(token.kind == 0)
            return text.length();
        int pos = 0;
        for(int line = 1; line < token.beginLine; line++)
            while(text.charAt(pos++) != '\n') ;

        for(int column = 1; column < token.beginColumn; column++)
            pos++;

        return pos;
    }

    static String formatParseException(ParseException parseException)
    {
        int fillWidth = 54;
        Token errToken = parseException.currentToken.next;
        String msg = parseException.toString();
        int i = msg.indexOf("ParseException");
        if(i > 0)
            msg = msg.substring(i);
        i = msg.lastIndexOf("expect");
        String expects = msg.substring(i);
        msg = msg.substring(0, i);
        while((i = expects.indexOf("...")) != -1) 
            expects = expects.substring(0, i) + expects.substring(i + "...\n ".length());
        expects = Prettify.string(3, expects, fillWidth);
        return msg + expects;
    }

    static String convertNewlines(String s)
    {
        if(s == null)
            s = "";
        StringBuffer buf = new StringBuffer(s);
        for(int i = 0; i < buf.length() - 1; i++)
            if(buf.charAt(i) == '\\' && buf.charAt(i + 1) == 'n')
            {
                buf.setCharAt(i, '\n');
                buf.deleteCharAt(i + 1);
            }

        if(buf.length() > 0)
            buf.append("\n");
        return buf.toString();
    }

    protected static String getJevaESUIBundleString(String resourceKey)
    {
        return ResourceBundle.getBundle("edu.mit.ai.psg.jevaESUI.JevaESUIBundle").getString(resourceKey);
    }

    protected static String formatWithJevaESUIBundleString(String resourceKey, Object obj)
    {
        return MessageFormat.format(getJevaESUIBundleString(resourceKey), new Object[] {
            obj
        });
    }

    static Class _mthclass$(String x0)
    {
        try
        {
            return Class.forName(x0);
        }
        catch(ClassNotFoundException x1)
        {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    public static final Class NoThrowsDeclarations[] = new Class[0];
    public static final Class ThrowsThrowableDeclaration[];
    public static final int leftmostOptionBit = 2;
    protected static final String jevaESUIResourceName = "edu.mit.ai.psg.jevaESUI.JevaESUIBundle";
    static final String rkTitle = "Title";
    static final String rkHeraldFormat = "HeraldFormat";
    static final String rkWarningUnableToCheckPackageNames = "WarningUnableToCheckPackageNames";
    static final String rkNotCheckingPackageNames = "NotCheckingPackageNames";
    static final String rkUncaughtException = "UncaughtException";
    static final String rkInternalError = "InternalError";

    static 
    {
        ThrowsThrowableDeclaration = (new Class[] {
            java.lang.Throwable.class
        });
    }
}
