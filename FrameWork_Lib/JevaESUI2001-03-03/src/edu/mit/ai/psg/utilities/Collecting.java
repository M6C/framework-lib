// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/Collecting.java

package edu.mit.ai.psg.utilities;

import java.util.Collection;
import java.util.Iterator;

// Referenced classes of package edu.mit.ai.psg.utilities:
//            IUnaryPredicate, IUnaryFunction

public class Collecting
{

    public static Collection select(Collection collection, IUnaryPredicate predicate)
    {
        Collection result = makeEmptyCollection(collection);
        for(Iterator iter = collection.iterator(); iter.hasNext();)
        {
            Object value = iter.next();
            if(predicate.execute(value))
                result.add(value);
        }

        return result;
    }

    public static Collection makeEmptyCollection(Collection collection)
    {
        try
        {
            return (Collection)collection.getClass().newInstance();
        }
        catch(InstantiationException e)
        {
            throw new IllegalArgumentException("" + e);
        }
        catch(IllegalAccessException e)
        {
            throw new IllegalArgumentException("" + e);
        }
    }

    public static Collection map(Collection collection, IUnaryFunction function)
    {
        Collection result = makeEmptyCollection(collection);
        for(Iterator iter = collection.iterator(); iter.hasNext(); result.add(function.execute(iter.next())));
        return result;
    }

    private Collecting()
    {
    }
}
