// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSForStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, EnvLabel, BreakException, ContinueException, 
//            JNSLabeledStatement, IJevaNode, IStatementNode, JNSLocalVariableDeclaration, 
//            IExpressionNode, IllegalTypeException, VerifyingException, AbruptCompletionException, 
//            EvalMethods, SimpleNode, Token, TypeMethods, 
//            Node, IEnv

public class JNSForStatement extends JevaNode
    implements IStatementNode
{

    JNSForStatement(int id)
    {
        super(id);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        if(!isInitialized)
            initialize(env);
        IEnv env1 = new EnvLabel(this, env);
        IEnv forEnv = null != forInit ? EvalMethods.verifyTypes(forInit, env1) : env1;
        EvalMethods.getType(forTest, forEnv);
        if(forUpdate != null)
            EvalMethods.verifyTypes(forUpdate, forEnv);
        EvalMethods.verifyTypes(forBody, forEnv);
        return env;
    }

    public IEnv eval(IEnv env)
        throws AbruptCompletionException
    {
        if(!isInitialized)
            throw JevaNode.notVerified();
        IEnv env1 = new EnvLabel(this, env);
        Object ign;
        for(IEnv forEnv = null != forInit ? EvalMethods.eval(forInit, env1) : env1; ((Boolean)EvalMethods.eval(forTest, forEnv)).booleanValue(); ign = null != forUpdate ? ((Object) (EvalMethods.eval(forUpdate, forEnv))) : null)
            try
            {
                EvalMethods.eval(forBody, forEnv);
                continue;
            }
            catch(BreakException e)
            {
                if("" != e.getLabel())
                    throw e;
                break;
            }
            catch(ContinueException e)
            {
                if("" != e.getLabel() && (!(parent instanceof JNSLabeledStatement) || !e.getLabel().equals(((JNSLabeledStatement)parent).getLabel())))
                    throw e;
            }

        return env;
    }

    void initialize(IEnv env)
        throws VerifyingException
    {
        int lastChildNum = jjtGetNumChildren() - 1;
        int childNum = 0;
        IJevaNode child = (IJevaNode)jjtGetChild(childNum);
        IEnv forEnv = new EnvLabel(this, env);
        if(getBeginToken().next.next == child.getBeginToken())
        {
            forInit = (IStatementNode)child;
            if(forInit instanceof JNSLocalVariableDeclaration)
                forEnv = ((JNSLocalVariableDeclaration)forInit).verifyTypes(env);
            child = (IJevaNode)jjtGetChild(++childNum);
        }
        if(childNum < lastChildNum && ";".equals(child.getEndToken().image))
        {
            forTest = (IExpressionNode)child;
            if(EvalMethods.getType(forTest, forEnv) != Boolean.TYPE)
                throw new IllegalTypeException(this, env, "Test of For is not a boolean expression:  (" + TypeMethods.toSourceString(EvalMethods.getType(forTest, forEnv)) + ") " + forTest.printToString());
            child = (IJevaNode)jjtGetChild(++childNum);
        }
        if(childNum < lastChildNum && ")".equals(child.getEndToken().image))
            forUpdate = (IStatementNode)child;
        forBody = (IStatementNode)jjtGetChild(lastChildNum);
        parent = jjtGetParent();
        isInitialized = true;
    }

    boolean isInitialized;
    IStatementNode forInit;
    IExpressionNode forTest;
    IStatementNode forUpdate;
    IStatementNode forBody;
    Node parent;
}
