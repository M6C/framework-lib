// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEMethodInvocationExpression.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.*;
import java.util.Stack;
import java.util.Vector;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IllegalTypeException, ThrowException, IExpressionNode, 
//            JNEArguments, JNName, VerifyingException, JevaNode, 
//            TypeMethods, IEnv, EvalMethods, SimpleNode, 
//            IJevaNode, Token, JevaES, IPackage, 
//            AccessibilityMethods

public class JNEMethodInvocationExpression extends ExpressionNode
{
    private class ApplicableMethodsVector extends Vector
    {

        void addApplicableDeclaredMethods(Class aClass)
        {
            Method declMethods[] = aClass.getDeclaredMethods();
            for(int i = 0; i < declMethods.length; i++)
                addMethodIfApplicable(declMethods[i]);

        }

        void addMethodIfApplicable(Method method)
        {
            if(methodName.equals(method.getName()) && TypeMethods.isApplicable(method, argClasses) && (isSuppressingAccessChecks || AccessibilityMethods.isAccessibleFromPackage(method, currentPackageName)))
            {
                if(!AccessibilityMethods.isPublicToTopLevel(method))
                    method.setAccessible(true);
                super.addElement(method);
            }
        }

        String methodName;
        Class argClasses[];
        boolean isSuppressingAccessChecks;
        String currentPackageName;

        ApplicableMethodsVector(String methodName, Class argClasses[], boolean isSuppressingAccessChecks, String currentPackageName)
        {
            this.methodName = methodName;
            this.argClasses = argClasses;
            this.isSuppressingAccessChecks = isSuppressingAccessChecks;
            this.currentPackageName = currentPackageName;
        }
    }


    JNEMethodInvocationExpression(int id)
    {
        super(id);
        methodSignature = null;
        targetExpression = null;
        argumentExpressions = null;
        invocationMode = 0;
    }

    public Method getMethodSignature()
    {
        if(methodSignature != null)
            return methodSignature;
        else
            throw JevaNode.notVerified();
    }

    public IExpressionNode getTargetExpression()
    {
        if(targetExpression == null)
            throw JevaNode.notVerified();
        else
            return targetExpression;
    }

    public int getInvocationMode()
    {
        if(invocationMode != 0)
            return invocationMode;
        else
            throw JevaNode.notVerified();
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        initMethodSignature(env);
        Class exceptionTypes[] = methodSignature.getExceptionTypes();
        for(int i = 0; i < exceptionTypes.length; i++)
            if(!TypeMethods.isUncheckedException(exceptionTypes[i]) && !TypeMethods.isCaughtOrDeclaredException(exceptionTypes[i], env.lookupThrows()))
                throw new IllegalTypeException(this, env, "Checked exception not caught or declared: " + exceptionTypes[i].getName());

        return methodSignature.getReturnType();
    }

    public boolean computeIsConstant(IEnv env)
    {
        return false;
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        if(null == methodSignature)
            throw JevaNode.notVerified();
        Object target = 5 != invocationMode ? EvalMethods.eval(targetExpression, env) : null;
        Object argValues[] = argumentExpressions.evalArguments(env);
        try
        {
            return methodSignature.invoke(target, argValues);
        }
        catch(InvocationTargetException e)
        {
            throw new ThrowException(e.getTargetException());
        }
        catch(IllegalAccessException e)
        {
            throw new ThrowException(e);
        }
        catch(SecurityException e)
        {
            throw new ThrowException(e);
        }
    }

    protected void initMethodSignature(IEnv env)
        throws VerifyingException
    {
        boolean isCallingStatic = false;
        if(1 == jjtGetNumChildren())
            throw JevaNode.shouldNotHappen("No target-less method() invocation in JevaES.");
        targetExpression = (IExpressionNode)jjtGetChild(0);
        argumentExpressions = (JNEArguments)jjtGetChild(1);
        String methodName = targetExpression.getEndToken().next.image;
        Class targetClass;
        if(targetExpression instanceof JNName)
        {
            JNName targetName = (JNName)targetExpression;
            int targetNameCategory = targetName.getNameCategory(env);
            if(targetNameCategory == 5)
            {
                isCallingStatic = true;
                targetClass = ((JNName)targetExpression).getNamedClass();
                if(!TypeMethods.isClassType(targetClass))
                    throw new IllegalTypeException(this, env, "Not a Class for static methods: " + targetClass.getName());
            } else
            {
                if(targetNameCategory == 6)
                    throw new IllegalTypeException(this, env, "Illegal target for method call:  " + targetName.getFullNameAndCurrentCategory());
                targetClass = EvalMethods.getType(targetExpression, env);
            }
        } else
        {
            targetClass = EvalMethods.getType(targetExpression, env);
        }
        if(null == targetClass)
            throw new IllegalTypeException(this, env, "Illegal target for method call.");
        Class argClasses[] = argumentExpressions.getTypes(env);
        ApplicableMethodsVector applicableMethods = new ApplicableMethodsVector(methodName, argClasses, JevaES.isSuppressingAccessChecks(env), env.lookupEnclosingPackage().getFullName());
        try
        {
            if(!targetClass.isInterface())
            {
                for(Class declClass = targetClass; declClass != null; declClass = declClass.getSuperclass())
                    applicableMethods.addApplicableDeclaredMethods(declClass);

            } else
            {
                Stack interfaces = new Stack();
                interfaces.push(targetClass);
                while(!interfaces.isEmpty()) 
                {
                    Class anInterface = (Class)interfaces.pop();
                    applicableMethods.addApplicableDeclaredMethods(anInterface);
                    Class superInterfaces[] = anInterface.getInterfaces();
                    for(int i = superInterfaces.length - 1; i >= 0; i--)
                        interfaces.push(superInterfaces[i]);

                }
            }
        }
        catch(SecurityException securityexception)
        {
            applicableMethods.removeAllElements();
            Method allPublicMethods[] = targetClass.getMethods();
            for(int i = 0; i < allPublicMethods.length; i++)
                applicableMethods.addMethodIfApplicable(allPublicMethods[i]);

        }
        if(0 == applicableMethods.size())
            throw new IllegalTypeException(this, env, "No Applicable method on " + targetClass + " for " + methodName + "(" + classNames(argClasses) + ")");
        Method mostSpecificMethod = (Method)applicableMethods.elementAt(0);
        for(int i = 1; i < applicableMethods.size(); i++)
        {
            Method m2 = (Method)applicableMethods.elementAt(i);
            if(!TypeMethods.isMoreSpecific(mostSpecificMethod, m2))
                if(TypeMethods.isMoreSpecific(m2, mostSpecificMethod))
                    mostSpecificMethod = m2;
                else
                    throw new IllegalTypeException(this, env, "Ambiguous methods on " + targetClass + " for " + methodName + "(" + classNames(argClasses) + ")");
        }

        if(isCallingStatic && !Modifier.isStatic(mostSpecificMethod.getModifiers()))
            throw new IllegalTypeException(this, env, "Method " + methodName + " of Class " + targetClass.getName() + " is not static.");
        methodSignature = mostSpecificMethod;
        if(Modifier.isStatic(mostSpecificMethod.getModifiers()))
            invocationMode = 5;
        else
        if(Modifier.isPrivate(methodSignature.getModifiers()))
            invocationMode = 1;
        else
        if(targetClass.isInterface())
            invocationMode = 3;
        else
            invocationMode = 2;
    }

    private static StringBuffer classNames(Class argClasses[])
    {
        StringBuffer argsString = new StringBuffer("");
        if(argClasses.length > 0)
        {
            argsString.append("" + argClasses[0]);
            for(int i = 1; i < argClasses.length; i++)
            {
                argsString.append(", ");
                argsString.append("" + argClasses[i]);
            }

        }
        return argsString;
    }

    Method methodSignature;
    IExpressionNode targetExpression;
    JNEArguments argumentExpressions;
    int invocationMode;
    static final int NONVIRTUAL = 1;
    static final int VIRTUAL = 2;
    static final int INTERFACE = 3;
    static final int SUPER = 4;
    static final int STATIC = 5;
}
