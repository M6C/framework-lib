// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/IPackage.java

package edu.mit.ai.psg.jevaES;


public interface IPackage
{

    public abstract String getFullName();

    public abstract String getSimpleName();

    public abstract Class getClass(String s, boolean flag);

    public abstract IPackage getIPackage(String s, boolean flag)
        throws IllegalArgumentException;
}
