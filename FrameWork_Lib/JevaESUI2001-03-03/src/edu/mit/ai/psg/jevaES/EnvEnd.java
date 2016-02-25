// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/EnvEnd.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            EnvImportPackage, VerifyingException, ParseException, Package, 
//            IEnv, JevaESParser, JevaES, IStore, 
//            IStatementNode, IPackage

public class EnvEnd
    implements IEnv
{

    public static IEnv makeDefaultEnv(int initOptions)
    {
        EnvEnd envEnd = new EnvEnd(initOptions);
        try
        {
            JNName pkgNameNode = JevaESParser.parseStringName("java.lang");
            IEnv defaultEnv = new EnvImportPackage(pkgNameNode, envEnd);
            envEnd.rootPackage.setEnv(envEnd, defaultEnv);
            return defaultEnv;
        }
        catch(VerifyingException verifyingexception) { }
        catch(ParseException parseexception) { }
        throw new Error("java.lang inaccessible --- need JevaES.suppressPackageNameChecksBit");
    }

    private EnvEnd(int initOptions)
    {
        rootPackage = new Package(null, this);
        options = initOptions;
    }

    public String toString()
    {
        return "{EnvEnd: " + rootPackage + "}";
    }

    public IStore lookupLocal(String queryName)
    {
        return null;
    }

    public IStatementNode lookupLabel(String queryName, boolean isForContinue)
    {
        return null;
    }

    public IEnv.ClassResult lookupClass(String queryName)
    {
        return IEnv.ClassResult.make(true, rootPackage.getClass(queryName, JevaES.isSuppressingPackageNameChecks(this)));
    }

    public IPackage lookupPackage(String firstPackageName)
    {
        if(null == firstPackageName)
            return rootPackage;
        else
            return rootPackage.getIPackage(firstPackageName, JevaES.isSuppressingPackageNameChecks(this));
    }

    public Class lookupReturnType()
    {
        return null;
    }

    public Class[] lookupThrows()
    {
        return emptyThrows;
    }

    public IPackage lookupEnclosingPackage()
    {
        return rootPackage;
    }

    public int getOptionBits()
    {
        return options;
    }

    public IEnv getNext()
    {
        return null;
    }

    Package rootPackage;
    int options;
    static final Class emptyThrows[] = new Class[0];

}
