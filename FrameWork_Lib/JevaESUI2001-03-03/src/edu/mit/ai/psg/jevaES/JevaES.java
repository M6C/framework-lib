// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JevaES.java

package edu.mit.ai.psg.jevaES;

import java.io.*;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            EnvLocalStore, ParseException, VerifyingException, ThrowException, 
//            AbruptCompletionException, IEnv, EnvEnd, TypeMethods, 
//            JevaESParser, EvalMethods, IExpressionNode, IStatementNode

public class JevaES
{

    private JevaES()
    {
    }

    public static String getCopyright()
    {
        return "Copyright (c) 1997--2000 Massachusetts Institute of Technology.";
    }

    public static String getNoWarranty()
    {
        return "This software is provided ``as is'' without express or implied warranty.";
    }

    public static String getVersion()
    {
        return "prerelease version of 2000.04.06";
    }

    public static String getHomepage()
    {
        return "http://www.ai.mit.edu/people/caroma/tools/";
    }

    public static final boolean isSuppressingPackageNameChecks(IEnv env)
    {
        return (1 & env.getOptionBits()) != 0;
    }

    public static final boolean isSuppressingAccessChecks(IEnv env)
    {
        return (2 & env.getOptionBits()) != 0;
    }

    public static IEnv makeDefaultEnv()
    {
        return EnvEnd.makeDefaultEnv(0);
    }

    public static IEnv makeDefaultEnv(int optionBits)
    {
        return EnvEnd.makeDefaultEnv(optionBits);
    }

    public static IEnv extendEnv(Class type, String name, Object value, IEnv nextEnv)
    {
        return extendEnv(false, type, name, value, nextEnv);
    }

    public static IEnv extendEnv(boolean isFinal, Class type, String name, Object value, IEnv nextEnv)
    {
        if(null == type)
            throw new IllegalArgumentException("Null type");
        if(null == name)
            throw new IllegalArgumentException("Null name");
        if(null == nextEnv)
            throw new IllegalArgumentException("Null env (consider JevaES.makeDefaultEnv()");
        if(null != nextEnv.lookupLocal(name))
            throw new IllegalArgumentException("A local variable " + name + " has already been declared.");
        Class valueClass = null != value ? value.getClass() : null;
        if(!TypeMethods.isBindableType(type, valueClass) && (!type.isPrimitive() || !TypeMethods.isBindableType(type, TypeMethods.toPrimitiveType(valueClass))))
        {
            throw new IllegalArgumentException("Cannot initialize " + type.getName() + " " + name + " " + "with value of type " + (null != valueClass ? valueClass.getName() : "" + null));
        } else
        {
            boolean isConstant = isFinal && (type.isPrimitive() || type.equals(java.lang.String.class));
            EnvLocalStore envStore = new EnvLocalStore(isFinal, isConstant, type, name, nextEnv);
            envStore.initializeValue(value);
            return envStore;
        }
    }

    public static Object parseEvalStringExpression(String s)
        throws ParseException, VerifyingException, ThrowException
    {
        return parseEvalStreamExpression(new ByteArrayInputStream(s.getBytes()));
    }

    public static Object parseEvalStreamExpression(InputStream stream)
        throws ParseException, VerifyingException, ThrowException
    {
        IExpressionNode node = JevaESParser.parseStreamExpression(stream);
        Object value = EvalMethods.evaluate(node);
        return value;
    }

    public static Object parseEvalStringExpression(String s, IEnv env)
        throws ParseException, VerifyingException, ThrowException
    {
        return parseEvalStreamExpression(new ByteArrayInputStream(s.getBytes()), env);
    }

    public static Object parseEvalStreamExpression(InputStream stream, IEnv env)
        throws ParseException, VerifyingException, ThrowException
    {
        IExpressionNode node = JevaESParser.parseStreamExpression(stream);
        Object value = EvalMethods.evaluate(node, env);
        return value;
    }

    public static IEnv parseEvalStringStatement(String s)
        throws ParseException, VerifyingException, AbruptCompletionException
    {
        return parseEvalStreamStatement(new ByteArrayInputStream(s.getBytes()));
    }

    public static IEnv parseEvalStreamStatement(InputStream stream)
        throws ParseException, VerifyingException, AbruptCompletionException
    {
        IStatementNode node = JevaESParser.parseStreamStatement(stream);
        return EvalMethods.evaluate(node);
    }

    public static IEnv parseEvalStringStatement(String s, IEnv env)
        throws ParseException, VerifyingException, AbruptCompletionException
    {
        return parseEvalStreamStatement(new ByteArrayInputStream(s.getBytes()), env);
    }

    public static IEnv parseEvalStreamStatement(InputStream stream, IEnv env)
        throws ParseException, VerifyingException, AbruptCompletionException
    {
        IStatementNode node = JevaESParser.parseStreamStatement(stream);
        return EvalMethods.evaluate(node, env);
    }

    public static IExpressionNode parseStreamExpression(InputStream stream)
        throws ParseException
    {
        return JevaESParser.parseStreamExpression(stream);
    }

    public static IExpressionNode parseStringExpression(String s)
        throws ParseException
    {
        return JevaESParser.parseStringExpression(s);
    }

    public static IStatementNode parseStreamStatement(InputStream stream)
        throws ParseException
    {
        return JevaESParser.parseStreamStatement(stream);
    }

    public static IStatementNode parseStringStatement(String s)
        throws ParseException
    {
        return JevaESParser.parseStringStatement(s);
    }

    public static void main(String parameters[])
    {
        if(parameters.length == 0)
            usageExit();
        else
        if("-echo".equalsIgnoreCase(parameters[0]))
        {
            System.out.print("[");
            for(int i = 0; i < parameters.length; i++)
            {
                System.out.print("\"" + parameters[i] + "\"");
                if(i + 1 < parameters.length)
                    System.out.print(", ");
            }

            System.out.println("]");
        } else
        if("-expression".equalsIgnoreCase(parameters[0]))
        {
            if(parameters.length != 2)
            {
                usageExit();
            } else
            {
                Object result = null;
                try
                {
                    result = parseEvalStringExpression(parameters[1]);
                }
                catch(Throwable t)
                {
                    errorExit(t);
                }
                System.out.println(result);
            }
        } else
        if("-statement".equalsIgnoreCase(parameters[0]))
        {
            if(parameters.length != 2)
                usageExit();
            else
                try
                {
                    parseEvalStringStatement(parameters[1]);
                }
                catch(Throwable t)
                {
                    errorExit(t);
                }
        } else
        {
            usageExit();
        }
    }

    private static void usageExit()
    {
        System.err.println("usage: java edu.mit.ai.psg.jevaES.JevaES fullClassName [arguments...]\n         runs public static void main(String[]) of class in classpath\n   or: java edu.mit.ai.psg.jevaES.JevaES -expression \"java expression\"\n   or: java edu.mit.ai.psg.jevaES.JevaES -statement \"java statement\"\n   or: java edu.mit.ai.psg.jevaES.JevaES -echo [arguments...]\n         echo what Jeva receives as arguments (to diagnose quoting)\n");
        System.exit(1);
    }

    private static void noMainMethodExit(String className)
    {
        System.err.println("No public static void main(String[]) method found on class " + className);
        System.exit(1);
    }

    private static void errorExit(Throwable t)
    {
        System.err.println(t);
        System.exit(1);
    }

    private static final String copyright = "Copyright (c) 1997--2000 Massachusetts Institute of Technology.";
    private static final String noWarranty = "This software is provided ``as is'' without express or implied warranty.";
    private static final String version = "prerelease version of 2000.04.06";
    private static final String homepage = "http://www.ai.mit.edu/people/caroma/tools/";
    public static final int suppressPackageNameChecksBit = 1;
    public static final int suppressAccessChecksBit = 2;
    public static final int leftmostOptionBit = 2;
}
