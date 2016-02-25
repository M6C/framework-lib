// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSWhileStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IExpressionNode, IStatementNode, IllegalTypeException, 
//            EnvLabel, BreakException, ContinueException, JNSLabeledStatement, 
//            VerifyingException, AbruptCompletionException, SimpleNode, EvalMethods, 
//            IJevaNode, IEnv

public class JNSWhileStatement extends JevaNode
    implements IStatementNode
{

    JNSWhileStatement(int id)
    {
        super(id);
    }

    public IExpressionNode getTestExpressionNode()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    public IStatementNode getBodyStatementNode()
    {
        return (IStatementNode)jjtGetChild(1);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        IExpressionNode testExpr = getTestExpressionNode();
        if(EvalMethods.getType(testExpr, env) != Boolean.TYPE)
        {
            throw new IllegalTypeException(this, env, "Test of While is not a boolean expression:  (" + EvalMethods.getType(testExpr, env) + ") " + testExpr.printToString());
        } else
        {
            EvalMethods.verifyTypes(getBodyStatementNode(), new EnvLabel(this, env));
            return env;
        }
    }

    public IEnv eval(IEnv env)
        throws AbruptCompletionException
    {
        IExpressionNode testExpr = getTestExpressionNode();
        IStatementNode bodyStmt = getBodyStatementNode();
        IEnv bodyEnv = new EnvLabel(this, env);
        Node parent = jjtGetParent();
        while(((Boolean)EvalMethods.eval(testExpr, env)).booleanValue()) 
            try
            {
                EvalMethods.eval(bodyStmt, bodyEnv);
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
}
