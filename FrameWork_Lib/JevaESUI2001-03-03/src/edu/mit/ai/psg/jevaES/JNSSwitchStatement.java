// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSSwitchStatement.java

package edu.mit.ai.psg.jevaES;

import java.util.*;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IllegalTypeException, EnvLabel, IStatementNode, 
//            BreakException, IExpressionNode, JNSSwitchLabel, ThrowException, 
//            VerifyingError, IllegalConstructException, VerifyingException, AbruptCompletionException, 
//            EvalMethods, TypeMethods, IJevaNode, SimpleNode, 
//            IEnv

public class JNSSwitchStatement extends JevaNode
    implements IStatementNode
{

    JNSSwitchStatement(int id)
    {
        super(id);
        isInitialized = false;
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        if(!isInitialized)
            initialize(env);
        Class exprType = EvalMethods.getType(switchExpr, env);
        if(!exprType.equals(Integer.TYPE) && !exprType.equals(Character.TYPE) && !exprType.equals(Byte.TYPE) && !exprType.equals(Short.TYPE))
            throw new IllegalTypeException(this, env, "Type of Switch expression not one of int, byte, short, or char:  (" + TypeMethods.toSourceString(exprType) + ") " + switchExpr.printToString());
        IEnv switchEnv = new EnvLabel(this, env);
        for(int stmtNum = 0; stmtNum < statements.size(); stmtNum++)
            switchEnv = EvalMethods.verifyTypes((IStatementNode)statements.elementAt(stmtNum), switchEnv);

        return env;
    }

    public IEnv eval(IEnv env)
        throws AbruptCompletionException
    {
        if(!isInitialized)
            throw JevaNode.notVerified();
        Object switchVal = EvalMethods.eval(switchExpr, env);
        Integer statementNumber = (Integer)caseTable.get(switchVal);
        int statementNum = null != statementNumber ? statementNumber.intValue() : defaultCase;
        if(statementNum >= 0)
            try
            {
                IEnv switchEnv = new EnvLabel(this, env);
                for(; statementNum < statements.size(); statementNum++)
                    switchEnv = EvalMethods.eval((IStatementNode)statements.elementAt(statementNum), switchEnv);

            }
            catch(BreakException e)
            {
                if("" != e.getLabel())
                    throw e;
            }
        return env;
    }

    void initialize(IEnv env)
        throws VerifyingException
    {
        switchExpr = (IExpressionNode)jjtGetChild(0);
        statements = new Vector();
        caseTable = new Hashtable();
        defaultCase = -1;
        for(int i = 1; i < jjtGetNumChildren(); i++)
        {
            Node child = jjtGetChild(i);
            if(child instanceof IStatementNode)
            {
                statements.addElement(child);
            } else
            {
                JNSSwitchLabel switchLabel = (JNSSwitchLabel)child;
                if(switchLabel.isCase())
                {
                    IExpressionNode caseExpr = switchLabel.getExpression();
                    if(EvalMethods.isConstant(caseExpr, env))
                        try
                        {
                            caseTable.put(EvalMethods.eval(caseExpr, env), new Integer(statements.size()));
                        }
                        catch(ThrowException e)
                        {
                            throw new VerifyingError(this, env, e);
                        }
                    else
                        throw new IllegalConstructException(this, env, "Switch Case expression must be a constant:  " + caseExpr.printToString());
                } else
                if(defaultCase == -1)
                    defaultCase = statements.size();
                else
                    throw new IllegalConstructException(this, env, "Switch can only have one 'default:' label.");
            }
        }

        isInitialized = true;
    }

    boolean isInitialized;
    IExpressionNode switchExpr;
    Dictionary caseTable;
    int defaultCase;
    Vector statements;
}
