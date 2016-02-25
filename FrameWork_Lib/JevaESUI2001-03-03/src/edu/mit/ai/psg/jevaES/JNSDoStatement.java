// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSDoStatement.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IStatementNode, IExpressionNode, IllegalTypeException, 
//            EnvLabel, BreakException, ContinueException, JNSLabeledStatement, 
//            VerifyingException, AbruptCompletionException, SimpleNode, EvalMethods, 
//            TypeMethods, IJevaNode, IEnv

public class JNSDoStatement extends JevaNode
    implements IStatementNode
{

    JNSDoStatement(int id)
    {
        super(id);
    }

    public IStatementNode getBodyStatementNode()
    {
        return (IStatementNode)jjtGetChild(0);
    }

    public IExpressionNode getTestExpressionNode()
    {
        return (IExpressionNode)jjtGetChild(1);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        IExpressionNode testExpr = getTestExpressionNode();
        if(EvalMethods.getType(testExpr, env) != Boolean.TYPE)
        {
            throw new IllegalTypeException(this, env, "Test of Do...while is not a boolean expression:  (" + TypeMethods.toSourceString(EvalMethods.getType(testExpr, env)) + ") " + testExpr.printToString());
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
        do
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
        while(((Boolean)EvalMethods.eval(testExpr, env)).booleanValue());
        return env;
    }
}
