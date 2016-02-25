// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/EnvLocalStore.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            EnvBase, ThrowException, IStore, IEnv

public class EnvLocalStore extends EnvBase
    implements IStore, IEnv
{

    protected EnvLocalStore(boolean isFinal, boolean isConstant, Class type, String name, IEnv nextEnv)
    {
        super(nextEnv);
        this.isFinal = false;
        this.isConstant = false;
        isInitialized = false;
        this.name = name;
        this.type = type;
        this.isFinal = isFinal;
        this.isConstant = isConstant;
    }

    void initializeValue(Object initialValue)
    {
        if(!isInitialized)
        {
            value = initialValue;
            isInitialized = true;
        } else
        {
            throw new IllegalStateException(name + " is already initialized");
        }
    }

    public String getName()
    {
        return name;
    }

    public Class getType()
    {
        return type;
    }

    public boolean isFinal()
    {
        return isFinal;
    }

    public boolean isConstant()
    {
        return isConstant;
    }

    public Object getValue()
    {
        if(isInitialized)
            return value;
        else
            throw new IllegalStateException(name + " is uninitialized");
    }

    public void setValue(Object newValue)
        throws ThrowException
    {
        if(isFinal)
            throw new ThrowException(new IllegalAccessException(name + "is final and cannot be set"));
        value = newValue;
        if(!isInitialized)
            isInitialized = true;
    }

    public IStore lookupLocal(String queryName)
    {
        if(name.equals(queryName))
            return this;
        else
            return super.lookupLocal(queryName);
    }

    public String toString()
    {
        return "{" + name + "=(" + type + ")" + value + ", " + getNext() + "}";
    }

    String name;
    Class type;
    boolean isFinal;
    boolean isConstant;
    boolean isInitialized;
    Object value;
}
