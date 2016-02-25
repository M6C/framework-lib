// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNFormalParameter.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, JNType, EnvLocalStore, JevaESParserConstants, 
//            VerifyingException, SimpleNode, Token, TypeMethods, 
//            IEnv

public class JNFormalParameter extends JevaNode
    implements JevaESParserConstants
{

    JNFormalParameter(int id)
    {
        super(id);
        isInitialized = false;
    }

    public JNType getTypeNode()
    {
        return (JNType)jjtGetChild(0);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        if(!isInitialized)
            initialize(env);
        return new EnvLocalStore(isFinal(), false, getParameterType(env), getParameterName(), env);
    }

    public IEnv bindParameter(Object value, IEnv env)
    {
        EnvLocalStore newEnv = new EnvLocalStore(isFinal(), false, getParameterType(), getParameterName(), env);
        newEnv.initializeValue(value);
        return newEnv;
    }

    void initialize(IEnv env)
        throws VerifyingException
    {
        Class baseType = getTypeNode().toType(env);
        Token token = getTypeNode().getEndToken().next;
        int ndims = 0;
        for(; "[".equals(token.image) && "]".equals(token.next.image); token = token.next.next)
            ndims++;

        if(ndims == 0)
            type = baseType;
        else
            type = TypeMethods.makeArrayType(baseType, ndims);
        isInitialized = true;
    }

    public Class getParameterType(IEnv env)
        throws VerifyingException
    {
        if(!isInitialized)
            initialize(env);
        return type;
    }

    public Class getParameterType()
        throws IllegalStateException
    {
        if(!isInitialized)
            throw new IllegalStateException("Unverified.");
        else
            return type;
    }

    public String getParameterName()
    {
        return getTypeNode().getEndToken().image;
    }

    public boolean isFinal()
    {
        return 21 == getBeginToken().kind;
    }

    boolean isInitialized;
    Class type;
}
