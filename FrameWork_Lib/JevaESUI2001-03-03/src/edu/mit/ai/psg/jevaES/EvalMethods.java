// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/EvalMethods.java

package edu.mit.ai.psg.jevaES;

import java.io.PrintStream;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            VerifyingException, VerifyingError, ThrowException, EvaluatingError, 
//            AbruptCompletionException, IExpressionNode, IStatementNode, IExpressionStoreNode, 
//            EnvEnd, IEnv, IStore, IJevaNode

public class EvalMethods
{

    public static Object evaluate(IExpressionNode node)
        throws VerifyingException, ThrowException
    {
        return evaluate(node, defaultEnv);
    }

    public static IEnv evaluate(IStatementNode node)
        throws VerifyingException, AbruptCompletionException
    {
        return evaluate(node, defaultEnv);
    }

    public static Object evaluate(IExpressionNode node, IEnv env)
        throws VerifyingException, ThrowException
    {
        getType(node, env);
        return eval(node, env);
    }

    public static IEnv evaluate(IStatementNode node, IEnv env)
        throws VerifyingException, AbruptCompletionException
    {
        verifyTypes(node, env);
        return eval(node, env);
    }

    public static Class getType(IExpressionNode node)
        throws VerifyingException
    {
        return getType(node, defaultEnv);
    }

    public static boolean isConstant(IExpressionNode node)
        throws VerifyingException
    {
        return isConstant(node, defaultEnv);
    }

    public static IEnv verifyTypes(IStatementNode node)
        throws VerifyingException
    {
        return verifyTypes(node, defaultEnv);
    }

    public static Object eval(IExpressionNode node)
        throws ThrowException
    {
        return eval(node, defaultEnv);
    }

    public static IEnv eval(IStatementNode node)
        throws AbruptCompletionException
    {
        return eval(node, defaultEnv);
    }

    public static IStore evalToStore(IExpressionStoreNode node)
        throws ThrowException
    {
        return evalToStore(node, defaultEnv);
    }

    public static Class getType(IExpressionNode node, IEnv env)
        throws VerifyingException
    {
        try
        {
            return node.getType(env);
        }
        catch(VerifyingException e)
        {
            throw e;
        }
        catch(VerifyingError e)
        {
            throw e;
        }
        catch(Throwable e)
        {
            throw wrapVerifyErr(e, node, env);
        }
    }

    public static boolean isConstant(IExpressionNode node, IEnv env)
        throws VerifyingException
    {
        try
        {
            return node.isConstant(env);
        }
        catch(VerifyingException e)
        {
            throw e;
        }
        catch(VerifyingError e)
        {
            throw e;
        }
        catch(Throwable e)
        {
            throw wrapVerifyErr(e, node, env);
        }
    }

    public static IEnv verifyTypes(IStatementNode node, IEnv env)
        throws VerifyingException
    {
        try
        {
            return node.verifyTypes(env);
        }
        catch(VerifyingException e)
        {
            throw e;
        }
        catch(VerifyingError e)
        {
            throw e;
        }
        catch(Throwable e)
        {
            throw wrapVerifyErr(e, node, env);
        }
    }

    public static Object eval(IExpressionNode enode, IEnv env)
        throws ThrowException
    {
        try
        {
            return enode.getValue(env);
        }
        catch(ThrowException e)
        {
            throw e;
        }
        catch(EvaluatingError e)
        {
            throw e;
        }
        catch(Throwable e)
        {
            throw wrapEvalErr(e, enode, env);
        }
    }

    public static IEnv eval(IStatementNode snode, IEnv env)
        throws AbruptCompletionException
    {
        try
        {
            return snode.eval(env);
        }
        catch(AbruptCompletionException e)
        {
            throw e;
        }
        catch(EvaluatingError e)
        {
            throw e;
        }
        catch(Throwable e)
        {
            throw wrapEvalErr(e, snode, env);
        }
    }

    public static IStore evalToStore(IExpressionStoreNode esnode, IEnv env)
        throws ThrowException
    {
        try
        {
            return esnode.evalToStore(env);
        }
        catch(ThrowException e)
        {
            throw e;
        }
        catch(EvaluatingError e)
        {
            throw e;
        }
        catch(Throwable e)
        {
            throw wrapEvalErr(e, esnode, env);
        }
    }

    static VerifyingError wrapVerifyErr(Throwable e, IJevaNode node, IEnv env)
    {
        VerifyingError exc = new VerifyingError(node, env, e);
        System.out.println(exc.toString());
        exc.error.printStackTrace();
        return exc;
    }

    static EvaluatingError wrapEvalErr(Throwable e, IJevaNode node, IEnv env)
    {
        EvaluatingError exc = new EvaluatingError(node, env, e);
        System.out.println(exc.toString());
        exc.error.printStackTrace();
        return exc;
    }

    public static Error shouldNotHappen(String explain, Throwable e)
    {
        String es = "Shouldn't happen --- " + explain + (e != null ? "" + e : "");
        System.err.println(es);
        if(e == null)
            Thread.dumpStack();
        else
            e.printStackTrace();
        return new Error(es);
    }

    public static Error shouldNotHappen(Throwable e)
    {
        return shouldNotHappen("", e);
    }

    public static Error shouldNotHappen(String explain)
    {
        return shouldNotHappen(explain, null);
    }

    private EvalMethods()
    {
    }

    public static IEnv defaultEnv = null;

    static 
    {
        try
        {
            defaultEnv = EnvEnd.makeDefaultEnv(0);
        }
        catch(Error error)
        {
            defaultEnv = EnvEnd.makeDefaultEnv(1);
        }
    }
}
