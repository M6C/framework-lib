// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSTryStatement.java

package edu.mit.ai.psg.jevaES;

import java.util.Vector;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, JNSBlock, JNFormalParameter, ThrowException, 
//            VerifyingException, EvaluatingError, IllegalTypeException, IStatementNode, 
//            AbruptCompletionException, SimpleNode, EvalMethods, TypeMethods, 
//            IEnv, EnvBase

public class JNSTryStatement extends JevaNode
    implements IStatementNode
{
    class EnvTryBlock extends EnvBase
    {

        public Class[] lookupThrows()
        {
            return caughtOrDeclaredExceptions;
        }

        public String toString()
        {
            StringBuffer buf = new StringBuffer();
            buf.append("try:caughtOrDeclared[");
            for(int i = 0; i < caughtOrDeclaredExceptions.length; i++)
            {
                buf.append(caughtOrDeclaredExceptions[i].getName());
                if(i + 1 < caughtOrDeclaredExceptions.length)
                    buf.append(", ");
            }

            buf.append("]");
            return "{" + buf + getNext() + "}";
        }

        private void initializeCaughtOrDeclaredExceptions()
            throws VerifyingException
        {
            Class nextCaughtOrDeclareds[] = getNext().lookupThrows();
            Vector myCaughtExceptions = new Vector();
            for(int catchNum = 0; catchNum < getNumCatches(); catchNum++)
            {
                Class paramType = getCatchFormalParameter(catchNum).getParameterType(this);
                if(!TypeMethods.isUncheckedException(paramType) && !TypeMethods.isCaughtOrDeclaredException(paramType, nextCaughtOrDeclareds))
                    myCaughtExceptions.add(paramType);
            }

            if(myCaughtExceptions.size() == 0)
            {
                caughtOrDeclaredExceptions = nextCaughtOrDeclareds;
            } else
            {
                caughtOrDeclaredExceptions = new Class[myCaughtExceptions.size() + nextCaughtOrDeclareds.length];
                myCaughtExceptions.copyInto(caughtOrDeclaredExceptions);
                System.arraycopy(nextCaughtOrDeclareds, 0, caughtOrDeclaredExceptions, myCaughtExceptions.size(), nextCaughtOrDeclareds.length);
            }
        }

        Class caughtOrDeclaredExceptions[];

        EnvTryBlock(IEnv nextEnv)
            throws VerifyingException
        {
            super(nextEnv);
            caughtOrDeclaredExceptions = null;
            initializeCaughtOrDeclaredExceptions();
        }
    }


    JNSTryStatement(int id)
    {
        super(id);
        isInitialized = false;
    }

    final JNSBlock getTryBlock()
    {
        return (JNSBlock)jjtGetChild(0);
    }

    final int getNumCatches()
    {
        return (jjtGetNumChildren() - 1) / 2;
    }

    final JNFormalParameter getCatchFormalParameter(int n)
    {
        Node node = jjtGetChild(1 + n * 2);
        if(node instanceof JNFormalParameter)
            return (JNFormalParameter)node;
        else
            return null;
    }

    final JNSBlock getCatchBlock(int n)
    {
        return (JNSBlock)jjtGetChild(1 + n * 2 + 1);
    }

    final JNSBlock getFinallyBlock()
    {
        if(jjtGetNumChildren() % 2 == 0)
            return (JNSBlock)jjtGetChild(jjtGetNumChildren() - 1);
        else
            return null;
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        if(!isInitialized)
            initialize(env);
        EvalMethods.verifyTypes(getTryBlock(), new EnvTryBlock(env));
        for(int catchNum = 0; catchNum < getNumCatches(); catchNum++)
        {
            JNFormalParameter param = getCatchFormalParameter(catchNum);
            IEnv catchEnv = param.verifyTypes(env);
            EvalMethods.verifyTypes(getCatchBlock(catchNum), catchEnv);
        }

        JNSBlock finallyBlock = getFinallyBlock();
        if(null != finallyBlock)
            EvalMethods.verifyTypes(finallyBlock, env);
        return env;
    }

    public IEnv eval(IEnv env)
        throws AbruptCompletionException
    {
        if(!isInitialized)
            throw JevaNode.notVerified();
        try
        {
            EvalMethods.eval(getTryBlock(), env);
        }
        catch(ThrowException e)
        {
            Throwable thrown = e.getThrown();
            Class thrownClass = thrown.getClass();
            for(int catchNum = 0; catchNum < getNumCatches(); catchNum++)
            {
                JNFormalParameter param = getCatchFormalParameter(catchNum);
                Class paramType;
                try
                {
                    paramType = param.getParameterType(env);
                }
                catch(VerifyingException ve)
                {
                    throw new EvaluatingError(this, env, ve);
                }
                if(TypeMethods.isBindableType(paramType, thrownClass))
                {
                    IEnv blockEnv = param.bindParameter(thrown, env);
                    EvalMethods.eval(getCatchBlock(catchNum), blockEnv);
                    IEnv ienv = env;
                    return ienv;
                }
            }

            throw e;
        }
        finally
        {
            JNSBlock finallyBlock = getFinallyBlock();
            if(null != finallyBlock)
                EvalMethods.eval(finallyBlock, env);
        }
        return env;
    }

    void initialize(IEnv env)
        throws VerifyingException
    {
        if(jjtGetNumChildren() <= 1)
            throw JevaNode.shouldNotHappen("'Try' has no 'catch' or 'finally' clause.");
        for(int catchNum = 0; catchNum < getNumCatches(); catchNum++)
        {
            Class paramType = getCatchFormalParameter(catchNum).getParameterType(env);
            if(!TypeMethods.isBindableType(java.lang.Throwable.class, paramType))
                throw new IllegalTypeException(this, env, "Catch parameter type is not subclass of Throwable: " + TypeMethods.toSourceString(paramType));
        }

        isInitialized = true;
    }

    boolean isInitialized;
    static final int offset = 1;
}
