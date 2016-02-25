// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/HashedSet.java

package edu.mit.ai.psg.utilities;

import java.util.Collection;
import java.util.HashSet;

// Referenced classes of package edu.mit.ai.psg.utilities:
//            ICollection, ISet, Collecting, IUnaryPredicate, 
//            IUnaryFunction

public class HashedSet extends HashSet
    implements ISet, Cloneable
{

    public HashedSet()
    {
    }

    public HashedSet(Object initElements[])
    {
        for(int i = 0; i < initElements.length; i++)
            add(initElements[i]);

    }

    public HashedSet(Collection initElements)
    {
        super(initElements);
    }

    public ICollection select(IUnaryPredicate thePredicate)
    {
        return (ICollection)Collecting.select(this, thePredicate);
    }

    public ICollection map(IUnaryFunction theFunction)
    {
        return (ICollection)Collecting.map(this, theFunction);
    }

    public Object clone()
    {
        return new HashedSet(this);
    }
}
