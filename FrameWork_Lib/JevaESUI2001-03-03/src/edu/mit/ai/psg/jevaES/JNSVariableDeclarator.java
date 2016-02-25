// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSVariableDeclarator.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IllegalNameException, EnvLocalStore, ThrowException, 
//            IllegalConstructException, IExpressionNode, IllegalTypeException, JNEArrayInitializer, 
//            VerifyingException, SimpleNode, IEnv, Token, 
//            TypeMethods, EvalMethods, PrimitiveOperations, Node

public class JNSVariableDeclarator extends JevaNode
{

    JNSVariableDeclarator(int id)
    {
        super(id);
        isInitialized = false;
        isConstant = false;
    }

    public Node getInitializer()
    {
        return 0 >= jjtGetNumChildren() ? null : jjtGetChild(0);
    }

    public IEnv verifyTypes(int modifiers, Class baseType, IEnv env)
        throws VerifyingException
    {
        if(!isInitialized)
            initialize(modifiers, baseType, env);
        if(null != env.lookupLocal(name))
            throw new IllegalNameException(this, env, "A local variable " + name + " has already been declared.");
        EnvLocalStore varEnv = new EnvLocalStore(Modifier.isFinal(modifiers), isConstant, type, name, env);
        verifyInitializerTypes(baseType, varEnv);
        if(isConstant)
            try
            {
                varEnv.initializeValue(evalInitializer(varEnv));
            }
            catch(ThrowException e)
            {
                throw new IllegalConstructException(this, env, "" + e);
            }
        return varEnv;
    }

    void initialize(int modifiers, Class baseType, IEnv env)
        throws VerifyingException
    {
        if(baseType == null)
            throw JevaNode.shouldNotHappen("null baseType");
        Token firstToken = getBeginToken();
        name = firstToken.image;
        nDims = 0;
        if("[".equals(firstToken.next.image))
        {
            for(Token tok = firstToken.next; "[".equals(tok.image); tok = tok.next.next)
                nDims++;

        }
        if(nDims == 0)
            type = baseType;
        else
            type = TypeMethods.makeArrayType(baseType, nDims);
        isConstant = Modifier.isFinal(modifiers) && (type.isPrimitive() || type.equals(java.lang.String.class)) && ((getInitializer() instanceof IExpressionNode) && EvalMethods.isConstant((IExpressionNode)getInitializer(), env));
        isInitialized = true;
    }

    void verifyInitializerTypes(Class baseType, IEnv env)
        throws VerifyingException
    {
        Node initializer = getInitializer();
        if(null != initializer)
            if(initializer instanceof IExpressionNode)
            {
                IExpressionNode initializerExpr = (IExpressionNode)initializer;
                if(!TypeMethods.isAssignable(type, initializerExpr, env))
                    throw new IllegalTypeException(this, env, "Can't initialize a variable of type " + baseType + "with an expression of type " + EvalMethods.getType(initializerExpr, env));
            } else
            {
                dims = ((JNEArrayInitializer)initializer).checkInitializerTypes(type, env);
            }
    }

    public IEnv extendEnv(int modifiers, Class baseType, IEnv env)
        throws ThrowException
    {
        if(!isInitialized)
            throw JevaNode.notVerified();
        EnvLocalStore localStoreEnv = new EnvLocalStore(Modifier.isFinal(modifiers), isConstant, type, name, env);
        Node initializer = getInitializer();
        if(null != initializer)
            localStoreEnv.initializeValue(evalInitializer(localStoreEnv));
        return localStoreEnv;
    }

    public Object evalInitializer(IEnv initializerEnv)
        throws ThrowException
    {
        if(!isInitialized)
            throw JevaNode.notVerified();
        Node initializer = getInitializer();
        Object value;
        if(initializer instanceof IExpressionNode)
        {
            value = EvalMethods.eval((IExpressionNode)initializer, initializerEnv);
            if(!TypeMethods.isReferenceType(type))
                value = PrimitiveOperations.castPrimitive(type, value);
        } else
        if(initializer instanceof JNEArrayInitializer)
        {
            value = Array.newInstance(type.getComponentType(), dims);
            ((JNEArrayInitializer)initializer).evalInitializer(value, initializerEnv);
        } else
        if(initializer == null)
            value = PrimitiveOperations.defaultValue(type);
        else
            throw JevaNode.shouldNotHappen("Unrecognized initializer: " + initializer);
        return value;
    }

    boolean isInitialized;
    boolean isConstant;
    String name;
    Class type;
    int nDims;
    int dims[];
}
