// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNDPackageDeclaration.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.ReflectPermission;
import java.security.AccessControlException;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            JevaNode, JNName, ThrowException, VerifyingException, 
//            EvaluatingError, IStatementNode, SimpleNode, IEnv, 
//            EnvImportPackage, EnvBase, IPackage

public class JNDPackageDeclaration extends JevaNode
    implements IStatementNode
{
    static class EnvPackage extends EnvImportPackage
    {

        public IPackage lookupEnclosingPackage()
        {
            return getPackage();
        }

        public String toString()
        {
            return "{package " + getPackage() + " " + getNext() + "}";
        }

        EnvPackage(JNName nameNode, IEnv nextEnv)
            throws VerifyingException
        {
            super(nameNode, nextEnv);
        }

        EnvPackage(IEnv nextEnv)
        {
            super(nextEnv);
        }
    }


    JNDPackageDeclaration(int id)
    {
        super(id);
    }

    public JNName getNameNode()
    {
        return (JNName)(jjtGetNumChildren() != 0 ? jjtGetChild(0) : null);
    }

    public IEnv verifyTypes(IEnv env)
        throws VerifyingException
    {
        if(getNameNode() == null)
            return new EnvPackage(env);
        else
            return new EnvPackage(getNameNode(), env);
    }

    public IEnv eval(IEnv env)
        throws ThrowException
    {
        try
        {
            SecurityManager security = System.getSecurityManager();
            if(security != null)
                security.checkPermission(reflectPermission);
        }
        catch(AccessControlException e)
        {
            throw new ThrowException(e);
        }
        try
        {
            return verifyTypes(env);
        }
        catch(VerifyingException e)
        {
            throw new EvaluatingError(this, env, e);
        }
    }

    private static ReflectPermission reflectPermission = new ReflectPermission("suppressAccessChecks");

}
