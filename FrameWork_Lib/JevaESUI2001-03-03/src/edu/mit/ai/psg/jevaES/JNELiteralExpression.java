// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNELiteralExpression.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, JevaNode, IEnv

public class JNELiteralExpression extends ExpressionNode
{

    JNELiteralExpression(int id)
    {
        super(id);
    }

    public void setValue(Object val, Class literalType)
    {
        super.constantValue = val;
        super.isConstant = true;
        super.isConstantIsComputed = true;
        super.type = literalType;
        super.typeIsComputed = true;
    }

    public boolean computeIsConstant(IEnv env)
    {
        if(super.isConstantIsComputed)
            return true;
        else
            throw JevaNode.shouldNotHappen("Uninitialized literal.");
    }

    public Class computeType(IEnv env)
    {
        if(super.typeIsComputed)
            return super.type;
        else
            throw JevaNode.shouldNotHappen("Uninitialized literal.");
    }

    public Object eval(IEnv env)
    {
        throw JevaNode.shouldNotHappen("Use EvalMethods.eval");
    }
}
