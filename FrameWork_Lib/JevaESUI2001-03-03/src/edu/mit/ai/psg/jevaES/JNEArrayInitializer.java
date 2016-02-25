// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEArrayInitializer.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.Array;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IllegalConstructException, IJevaNode, IExpressionNode, 
//            IllegalTypeException, VerifyingException, ThrowException, SimpleNode, 
//            TypeMethods, EvalMethods, IEnv

public class JNEArrayInitializer extends JevaNode
{

    JNEArrayInitializer(int id)
    {
        super(id);
    }

    public int[] checkInitializerTypes(Class arrayClass, IEnv env)
        throws VerifyingException
    {
        if(!arrayClass.isArray())
            throw new IllegalConstructException((IJevaNode)jjtGetParent(), env, "Array initializer nested more levels than number of dimensions.");
        Class componentType = arrayClass.getComponentType();
        int dims[] = {
            jjtGetNumChildren()
        };
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            Node childNode = jjtGetChild(i);
            if(childNode instanceof IExpressionNode)
            {
                IExpressionNode childExpr = (IExpressionNode)childNode;
                if(!TypeMethods.isAssignable(componentType, childExpr, env))
                    throw new IllegalTypeException((IJevaNode)jjtGetParent(), env, "Array requires type " + TypeMethods.toSourceString(componentType) + " but initializer " + childExpr.printToString() + " is of type " + TypeMethods.toSourceString(EvalMethods.getType(childExpr, env)));
            } else
            {
                int childDims[] = ((JNEArrayInitializer)childNode).checkInitializerTypes(componentType, env);
                dims = mergeDimensions(dims, childDims);
            }
        }

        return dims;
    }

    protected static int[] mergeDimensions(int dims[], int nestedDims[])
    {
        int newDims[];
        if(nestedDims.length + 1 > dims.length)
        {
            newDims = new int[nestedDims.length + 1];
            for(int j = 0; j < dims.length; j++)
                newDims[j] = dims[j];

        } else
        {
            newDims = dims;
        }
        for(int j = 0; j < nestedDims.length; j++)
            newDims[j + 1] = j >= dims.length ? nestedDims[j] : Math.max(newDims[j + 1], nestedDims[j]);

        return newDims;
    }

    public void evalInitializer(Object array, IEnv env)
        throws ThrowException
    {
        for(int i = 0; i < jjtGetNumChildren(); i++)
        {
            Node childNode = jjtGetChild(i);
            if(childNode instanceof IExpressionNode)
                Array.set(array, i, EvalMethods.eval((IExpressionNode)childNode, env));
            else
                ((JNEArrayInitializer)childNode).evalInitializer(Array.get(array, i), env);
        }

    }
}
