// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/IEnv.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            IStore, IStatementNode, IPackage

public interface IEnv
{
    public static class ClassResult
    {

        public static ClassResult make(boolean isBoundByName, Class classOrNull)
        {
            return classOrNull != null ? new ClassResult(isBoundByName, classOrNull) : null;
        }

        public final boolean isBoundByName;
        public final Class foundClass;

        private ClassResult(boolean isBoundByName, Class foundClass)
        {
            this.isBoundByName = isBoundByName;
            this.foundClass = foundClass;
        }
    }


    public abstract IStore lookupLocal(String s);

    public abstract IStatementNode lookupLabel(String s, boolean flag);

    public abstract ClassResult lookupClass(String s);

    public abstract IPackage lookupPackage(String s);

    public abstract Class lookupReturnType();

    public abstract Class[] lookupThrows();

    public abstract IPackage lookupEnclosingPackage();

    public abstract int getOptionBits();

    public abstract IEnv getNext();
}
