// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNType.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, IJevaNode, JNPrimitiveType, JNName, 
//            VerifyingException, SimpleNode, Token, TypeMethods, 
//            IEnv

public class JNType extends JevaNode
{

    JNType(int id)
    {
        super(id);
        isInitialized = false;
    }

    public IJevaNode getBaseClassNode()
    {
        return (IJevaNode)jjtGetChild(0);
    }

    public Class toType(IEnv env)
        throws VerifyingException
    {
        if(!isInitialized)
            initialize(env);
        return type;
    }

    void initialize(IEnv env)
        throws VerifyingException
    {
        IJevaNode classNode = getBaseClassNode();
        Class baseType = (classNode instanceof JNPrimitiveType) ? ((JNPrimitiveType)classNode).getNamedType() : ((JNName)classNode).getNamedClass(env);
        if(getEndToken() == classNode.getEndToken())
        {
            type = baseType;
        } else
        {
            Token token = classNode.getEndToken();
            int ndims = 0;
            for(; "[".equals(token.image) && "]".equals(token.next.image); token = token.next.next)
                ndims++;

            type = TypeMethods.makeArrayType(baseType, ndims);
        }
        isInitialized = true;
    }

    boolean isInitialized;
    Class type;
}
