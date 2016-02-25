// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/InitializableThreadLocal.java

package edu.mit.ai.psg.utilities;

import java.util.*;

public class InitializableThreadLocal extends ThreadLocal
{

    public InitializableThreadLocal()
    {
    }

    public void initialize(Thread t, Object value)
    {
        myInitialValues.put(t, value);
    }

    protected Object initialValue()
    {
        return myInitialValues.remove(Thread.currentThread());
    }

    private final Map myInitialValues = Collections.synchronizedMap(new WeakHashMap());
}
