// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/EnvOptionBits.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            EnvBase, IEnv

public class EnvOptionBits extends EnvBase
{

    public EnvOptionBits(int optionBits, IEnv nextEnv)
    {
        super(nextEnv);
        myOptionBits = optionBits;
    }

    public int getOptionBits()
    {
        return myOptionBits;
    }

    public String toString()
    {
        return "{options:" + myOptionBits + " " + getNext() + "}";
    }

    int myOptionBits;
}
