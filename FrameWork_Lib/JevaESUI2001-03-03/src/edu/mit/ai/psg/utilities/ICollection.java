// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/ICollection.java

package edu.mit.ai.psg.utilities;

import java.util.Collection;

// Referenced classes of package edu.mit.ai.psg.utilities:
//            IUnaryPredicate, IUnaryFunction

public interface ICollection
    extends Collection
{

    public abstract ICollection select(IUnaryPredicate iunarypredicate);

    public abstract ICollection map(IUnaryFunction iunaryfunction);
}
