// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNSLocalVariableDeclaration.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, JNType, JNSVariableDeclarator, IStatementNode, 
//            JevaESParserConstants, VerifyingException, ThrowException, SimpleNode, 
//            Token, IEnv

public class JNSLocalVariableDeclaration extends JevaNode
    implements IStatementNode, JevaESParserConstants
{

    JNSLocalVariableDeclaration(int id)
    {
        super(id);
        isInitialized = false;
    }

    public JNType getTypeNode()
    {
        return (JNType)jjtGetChild(0);
    }

    public int getNumDeclarators()
    {
        return jjtGetNumChildren() - 1;
    }

    public JNSVariableDeclarator getDeclaratorNode(int i)
    {
        return (JNSVariableDeclarator)jjtGetChild(i + 1);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        if(!isInitialized)
            initialize(env);
        for(int i = 0; i < getNumDeclarators(); i++)
            env = getDeclaratorNode(i).verifyTypes(modifiers, baseType, env);

        return env;
    }

    public IEnv eval(IEnv env)
        throws ThrowException
    {
        if(!isInitialized)
            throw JevaNode.notVerified();
        for(int i = 0; i < getNumDeclarators(); i++)
            env = getDeclaratorNode(i).extendEnv(modifiers, baseType, env);

        return env;
    }

    void initialize(IEnv env)
        throws VerifyingException
    {
        baseType = getTypeNode().toType(env);
        modifiers = 21 != getBeginToken().kind ? 0 : 16;
        isInitialized = true;
    }

    boolean isInitialized;
    Class baseType;
    int modifiers;
}
