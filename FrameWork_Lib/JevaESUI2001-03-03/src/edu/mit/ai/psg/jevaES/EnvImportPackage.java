// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/EnvImportPackage.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            EnvBase, VerifyingException, IEnv, JNName, 
//            JevaNode, JevaES, IPackage

class EnvImportPackage extends EnvBase
    implements IEnv
{

    public IPackage getPackage()
    {
        return myPackage;
    }

    public String getPackageName()
    {
        return myPackageName;
    }

    EnvImportPackage(JNName nameNode, IEnv env)
        throws VerifyingException
    {
        super(env);
        try
        {
            myPackageName = nameNode.getFullPackageOrClassName(env);
        }
        catch(VerifyingException verifyingexception)
        {
            myPackageName = nameNode.printToString();
        }
        myPackage = nameNode.computePackage(env);
    }

    EnvImportPackage(IEnv env)
    {
        super(env);
        myPackageName = null;
        myPackage = env.lookupPackage(null);
    }

    public static boolean isAlreadyImported(String importName, IEnv env)
    {
        for(; env != null; env = env.getNext())
            if((env instanceof EnvImportPackage) && importName.equals(((EnvImportPackage)env).getPackageName()))
                return true;

        return false;
    }

    public IEnv.ClassResult lookupClass(String queryName)
        throws IllegalArgumentException
    {
        IEnv.ClassResult nextResult = super.lookupClass(queryName);
        if(nextResult != null && nextResult.isBoundByName)
            return nextResult;
        Class myOnDemandClass = myPackage.getClass(queryName, JevaES.isSuppressingPackageNameChecks(this));
        if(null == nextResult)
            return IEnv.ClassResult.make(false, myOnDemandClass);
        if(null == myOnDemandClass || myOnDemandClass == nextResult.foundClass)
            return nextResult;
        else
            throw new IllegalArgumentException("Import-on-demand conflict: " + nextResult.foundClass.getName() + ", " + myOnDemandClass.getName());
    }

    public String toString()
    {
        return "{import " + myPackageName + ".* " + getNext() + "}";
    }

    IPackage myPackage;
    String myPackageName;
}
