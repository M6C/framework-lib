// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/EnvImportClass.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            EnvBase, VerifyingException, IEnv, JNName, 
//            JevaNode, JevaES, IPackage

class EnvImportClass extends EnvBase
    implements IEnv
{

    public String getFullClassName()
    {
        return fullClassName;
    }

    public String getSimpleClassName()
    {
        return simpleClassName;
    }

    protected EnvImportClass(JNName nameNode, IEnv env)
        throws VerifyingException, ClassNotFoundException
    {
        super(env);
        try
        {
            fullClassName = nameNode.getFullPackageOrClassName(env);
        }
        catch(VerifyingException verifyingexception)
        {
            fullClassName = nameNode.printToString().trim();
        }
        try
        {
            simpleClassName = nameNode.getSimpleName(env);
        }
        catch(VerifyingException verifyingexception1)
        {
            simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        }
        IPackage pkg = nameNode.computeParentPackage(env);
        myClass = pkg.getClass(simpleClassName, JevaES.isSuppressingPackageNameChecks(this));
        if(null == myClass)
            throw new ClassNotFoundException("No accessable class named " + fullClassName + " found in classpath.");
        try
        {
            IEnv.ClassResult nextResult = super.lookupClass(simpleClassName);
            if(nextResult != null && nextResult.isBoundByName)
                throw new IllegalArgumentException("Name conflict: " + nextResult.foundClass.getName() + ", " + fullClassName);
        }
        catch(IllegalArgumentException illegalargumentexception) { }
    }

    public static boolean isAlreadyImported(String fullImportName, IEnv env)
    {
        for(IEnv e = env; e != null; e = e.getNext())
            if((e instanceof EnvImportClass) && fullImportName.equals(((EnvImportClass)e).getFullClassName()))
                return true;

        return false;
    }

    public IEnv.ClassResult lookupClass(String queryName)
        throws IllegalArgumentException
    {
        if(!simpleClassName.equals(queryName))
            return super.lookupClass(queryName);
        else
            return IEnv.ClassResult.make(true, myClass);
    }

    public String toString()
    {
        return "{import " + fullClassName + " " + getNext() + "}";
    }

    String fullClassName;
    String simpleClassName;
    Class myClass;
}
