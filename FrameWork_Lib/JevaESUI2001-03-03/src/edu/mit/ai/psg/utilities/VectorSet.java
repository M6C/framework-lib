// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/VectorSet.java

package edu.mit.ai.psg.utilities;

import java.util.*;

// Referenced classes of package edu.mit.ai.psg.utilities:
//            ICollection, ISet, Collecting, IUnaryPredicate, 
//            IUnaryFunction

public class VectorSet extends AbstractSet
    implements ISet, Cloneable
{

    public VectorSet()
    {
        elements = new Vector();
    }

    public VectorSet(int initialCapacity)
    {
        elements = new Vector(initialCapacity);
    }

    public VectorSet(int initialCapacity, int capacityIncrement)
    {
        elements = new Vector(initialCapacity, capacityIncrement);
    }

    public VectorSet(Object initElements[])
    {
        this(initElements.length);
        for(int i = 0; i < initElements.length; i++)
            add(initElements[i]);

    }

    public VectorSet(Collection initElements)
    {
        this(initElements.size());
        for(Iterator iter = initElements.iterator(); iter.hasNext(); add(iter.next()));
    }

    public VectorSet(Set initElements)
    {
        this(initElements.size());
        for(Iterator iter = initElements.iterator(); iter.hasNext(); elements.add(iter.next()));
    }

    public ICollection select(IUnaryPredicate thePredicate)
    {
        Collection result = Collecting.select(this, thePredicate);
        return (ICollection)result;
    }

    public ICollection map(IUnaryFunction theFunction)
    {
        return (ICollection)Collecting.map(this, theFunction);
    }

    public Iterator iterator()
    {
        return elements.iterator();
    }

    public int size()
    {
        return elements.size();
    }

    public boolean add(Object object)
    {
        if(!elements.contains(object))
            return elements.add(object);
        else
            return false;
    }

    public Object clone()
    {
        return new VectorSet(this);
    }

    public void trimToSize()
    {
        elements.trimToSize();
    }

    protected final Vector elements;
}
