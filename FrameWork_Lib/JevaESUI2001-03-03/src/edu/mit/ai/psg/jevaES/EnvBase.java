// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/EnvBase.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            IEnv, IStore, IStatementNode, IPackage

public abstract class EnvBase
    implements IEnv
{

    public final IEnv getNext()
    {
        return nextEnv;
    }

    public EnvBase(IEnv nextEnv)
        throws IllegalArgumentException
    {
        if(nextEnv == null)
        {
            throw new IllegalArgumentException("null");
        } else
        {
            this.nextEnv = nextEnv;
            return;
        }
    }

    public abstract String toString();

    public IStore lookupLocal(String queryName)
    {
        return nextEnv.lookupLocal(queryName);
    }

    public IStatementNode lookupLabel(String queryName, boolean isForContinue)
    {
        return nextEnv.lookupLabel(queryName, isForContinue);
    }

    public IEnv.ClassResult lookupClass(String queryName)
    {
        return nextEnv.lookupClass(queryName);
    }

    public IPackage lookupPackage(String rootPackageName)
    {
        return nextEnv.lookupPackage(rootPackageName);
    }

    public Class lookupReturnType()
    {
        return nextEnv.lookupReturnType();
    }

    public Class[] lookupThrows()
    {
        return nextEnv.lookupThrows();
    }

    public IPackage lookupEnclosingPackage()
    {
        return nextEnv.lookupEnclosingPackage();
    }

    public int getOptionBits()
    {
        return nextEnv.getOptionBits();
    }

    private final IEnv nextEnv;
}
