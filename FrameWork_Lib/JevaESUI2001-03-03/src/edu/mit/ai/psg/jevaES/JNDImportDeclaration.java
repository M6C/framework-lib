// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNDImportDeclaration.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, JNName, Token, VerifyingException, 
//            EvaluatingError, EnvImportPackage, EnvImportClass, IllegalNameException, 
//            IStatementNode, JevaESParserConstants, ThrowException, SimpleNode, 
//            IEnv

public class JNDImportDeclaration extends JevaNode
    implements IStatementNode, JevaESParserConstants
{

    JNDImportDeclaration(int id)
    {
        super(id);
    }

    public JNName getNameNode()
    {
        return (JNName)jjtGetChild(0);
    }

    public boolean isPackageImport()
    {
        return (getNameNode().getEndToken().next instanceof Token) && getNameNode().getEndToken().next.kind == 92;
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        return importFor(env);
    }

    public IEnv eval(IEnv env)
        throws ThrowException
    {
        try
        {
            return importFor(env);
        }
        catch(VerifyingException e)
        {
            throw new EvaluatingError(this, env, e);
        }
    }

    IEnv importFor(IEnv env)
        throws VerifyingException
    {
        if(isAlreadyImported(env))
            return env;
        if(isPackageImport())
            return new EnvImportPackage(getNameNode(), env);
        try
        {
            return new EnvImportClass(getNameNode(), env);
        }
        catch(ClassNotFoundException e)
        {
            throw new IllegalNameException(this, env, e.toString());
        }
        catch(IllegalArgumentException e)
        {
            throw new IllegalNameException(this, env, e.getMessage());
        }
    }

    boolean isAlreadyImported(IEnv env)
        throws VerifyingException
    {
        JNName nameNode = getNameNode();
        String fullImportName = null;
        fullImportName = nameNode.getFullPackageOrClassName(env);
        if(isPackageImport())
            return EnvImportPackage.isAlreadyImported(fullImportName, env);
        else
            return EnvImportClass.isAlreadyImported(fullImportName, env);
    }
}
