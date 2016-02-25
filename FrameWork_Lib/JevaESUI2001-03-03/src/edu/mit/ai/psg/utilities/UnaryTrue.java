// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/UnaryTrue.java

package edu.mit.ai.psg.utilities;


// Referenced classes of package edu.mit.ai.psg.utilities:
//            IUnaryPredicate

public class UnaryTrue
    implements IUnaryPredicate
{

    public UnaryTrue()
    {
    }

    public boolean execute(Object obj)
    {
        return true;
    }

    public static final UnaryTrue instance = new UnaryTrue();

}
