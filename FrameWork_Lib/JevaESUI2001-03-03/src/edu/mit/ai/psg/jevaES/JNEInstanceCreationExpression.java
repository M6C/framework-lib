// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEInstanceCreationExpression.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.*;
import java.util.Vector;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, JNName, JNEArguments, IllegalTypeException, 
//            EvaluatingError, ThrowException, VerifyingException, SimpleNode, 
//            TypeMethods, JevaNode, IEnv, IPackage, 
//            JevaES, AccessibilityMethods, IJevaNode

public class JNEInstanceCreationExpression extends ExpressionNode
{

    JNEInstanceCreationExpression(int id)
    {
        super(id);
    }

    private JNName getTypeNode()
    {
        return (JNName)jjtGetChild(0);
    }

    private JNEArguments getArgumentsNode()
    {
        return (JNEArguments)jjtGetChild(1);
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        initConstructor(env);
        Class type = getTypeNode().getNamedClass(env);
        if(TypeMethods.isClassType(type) && !TypeMethods.isAbstractClass(type))
            return type;
        else
            throw new IllegalTypeException(this, env, "Not instantiable class: " + getTypeNode().printToString());
    }

    public boolean computeIsConstant(IEnv env)
    {
        return false;
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        if(null == myConstructor)
            throw new EvaluatingError(this, env, new IllegalStateException("Unverified"));
        try
        {
            return myConstructor.newInstance(getArgumentsNode().evalArguments(env));
        }
        catch(InvocationTargetException e)
        {
            throw new ThrowException(e.getTargetException());
        }
        catch(IllegalAccessException e)
        {
            throw new ThrowException(e);
        }
        catch(InstantiationException e)
        {
            throw new ThrowException(e);
        }
    }

    protected void initConstructor(IEnv env)
        throws VerifyingException
    {
        Class constructorClass = getTypeNode().getNamedClass(env);
        Class argExprClasses[] = getArgumentsNode().getTypes(env);
        myConstructor = findConstructor(constructorClass, argExprClasses, this, env);
    }

    static Constructor findConstructor(Class constructorClass, Class argExprClasses[], IJevaNode constructingNode, IEnv env)
        throws VerifyingException
    {
        Vector applicableConstructors = new Vector();
        String currentPkgName = env.lookupEnclosingPackage().getFullName();
        boolean isSuppressingAccessChecks = JevaES.isSuppressingAccessChecks(env);
        Constructor allConstructors[];
        try
        {
            allConstructors = constructorClass.getDeclaredConstructors();
        }
        catch(SecurityException securityexception)
        {
            allConstructors = constructorClass.getConstructors();
        }
        for(int i = 0; i < allConstructors.length; i++)
        {
            Constructor constructor = allConstructors[i];
            if(TypeMethods.isApplicable(constructor, argExprClasses) && (isSuppressingAccessChecks || AccessibilityMethods.isAccessibleFromPackage(constructor, currentPkgName)))
            {
                if(!AccessibilityMethods.isPublicToTopLevel(constructor))
                    constructor.setAccessible(true);
                applicableConstructors.addElement(constructor);
            }
        }

        if(0 == applicableConstructors.size())
            throw new IllegalTypeException(constructingNode, env, "No applicable constructor for new " + constructorClass.getName() + "(" + classesToString(argExprClasses) + ")");
        Constructor mostSpecificConstructor = (Constructor)applicableConstructors.elementAt(0);
        for(int i = 1; i < applicableConstructors.size(); i++)
        {
            Constructor m2 = (Constructor)applicableConstructors.elementAt(i);
            if(!TypeMethods.isMoreSpecific(mostSpecificConstructor, m2))
                if(TypeMethods.isMoreSpecific(m2, mostSpecificConstructor))
                    mostSpecificConstructor = m2;
                else
                    throw new IllegalTypeException(constructingNode, env, "Ambiguous constructors for new " + constructorClass.getName() + "(" + classesToString(argExprClasses) + ")");
        }

        return mostSpecificConstructor;
    }

    static String classesToString(Class classes[])
    {
        StringBuffer stringBuf = new StringBuffer("");
        if(classes.length > 0)
        {
            stringBuf.append(TypeMethods.toSourceString(classes[0]));
            for(int i = 1; i < classes.length; i++)
            {
                stringBuf.append(", ");
                stringBuf.append(TypeMethods.toSourceString(classes[i]));
            }

        }
        return stringBuf.toString();
    }

    Constructor myConstructor;
}
