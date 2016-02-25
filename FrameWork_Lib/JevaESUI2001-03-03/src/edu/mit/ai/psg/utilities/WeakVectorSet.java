// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/WeakVectorSet.java

package edu.mit.ai.psg.utilities;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.*;

// Referenced classes of package edu.mit.ai.psg.utilities:
//            VectorSet, ReferenceCleaner

public class WeakVectorSet extends VectorSet
{
    private static class EmptyValue
    {

        public String toString()
        {
            return "<EMPTY>";
        }

        private EmptyValue()
        {
        }

    }

    private class IteratorWeakReference extends WeakReference
        implements ReferenceCleaner.Cleanable
    {

        public void cleanUp()
        {
            WeakVectorSet set = WeakVectorSet.this;
            synchronized(set)
            {
                set.cleanIteratorRef(this);
            }
        }

        IteratorWeakReference(Iterator value)
        {
            super(value, ReferenceCleaner.referenceQueue);
        }
    }

    private class ElementWeakReference extends WeakReference
        implements ReferenceCleaner.Cleanable
    {

        public void cleanUp()
        {
            WeakVectorSet set = WeakVectorSet.this;
            synchronized(set)
            {
                set.cleanElementRef(this);
            }
        }

        public boolean equals(Object other)
        {
            return (other instanceof ElementWeakReference) && super.get() == ((Reference)other).get();
        }

        ElementWeakReference(Object value)
        {
            super(value, ReferenceCleaner.referenceQueue);
        }
    }


    public WeakVectorSet()
    {
        weakRefsAwaitingRemoval = new Vector();
        outstandingIterators = new Vector();
    }

    public WeakVectorSet(Object initElements[])
    {
        super(initElements);
        weakRefsAwaitingRemoval = new Vector();
        outstandingIterators = new Vector();
    }

    public WeakVectorSet(Collection initElements)
    {
        super(initElements);
        weakRefsAwaitingRemoval = new Vector();
        outstandingIterators = new Vector();
    }

    public WeakVectorSet(Set initElements)
    {
        weakRefsAwaitingRemoval = new Vector();
        outstandingIterators = new Vector();
        for(Iterator iter = initElements.iterator(); iter.hasNext(); super.elements.add(new ElementWeakReference(iter.next())));
    }

    public synchronized boolean add(Object object)
    {
        if(!super.elements.contains(object))
            return super.elements.add(new ElementWeakReference(object));
        else
            return false;
    }

    public synchronized Iterator iterator()
    {
        Iterator iter = new Iterator() {

            public boolean hasNext()
            {
                if(nextValue != WeakVectorSet.EMPTY)
                    return true;
                while(weakIterator.hasNext()) 
                {
                    Reference valRef = (Reference)weakIterator.next();
                    if(valRef != null)
                    {
                        Object value = valRef.get();
                        if(value != null)
                        {
                            nextValue = value;
                            return true;
                        }
                    } else
                    {
                        nextValue = null;
                        return true;
                    }
                }
                return false;
            }

            public Object next()
            {
                if(!hasNext())
                {
                    throw new NoSuchElementException();
                } else
                {
                    lastValue = nextValue;
                    nextValue = WeakVectorSet.EMPTY;
                    return lastValue;
                }
            }

            public void remove()
            {
                if(lastValue == WeakVectorSet.EMPTY || nextValue != WeakVectorSet.EMPTY)
                {
                    throw new IllegalStateException();
                } else
                {
                    weakIterator.remove();
                    return;
                }
            }

            Iterator weakIterator;
            Object nextValue;
            Object lastValue;

            
            {
                weakIterator = elements.iterator();
                nextValue = WeakVectorSet.EMPTY;
                lastValue = WeakVectorSet.EMPTY;
            }
        }
;
        addIterator(iter);
        return iter;
    }

    private synchronized void cleanElementRef(Reference ref)
    {
        weakRefsAwaitingRemoval.add(ref);
        removeWaitingRefsIfNoOutstandingIterators();
    }

    private synchronized void addIterator(Iterator iter)
    {
        outstandingIterators.add(new IteratorWeakReference(iter));
    }

    private synchronized void cleanIteratorRef(IteratorWeakReference iterRef)
    {
        outstandingIterators.remove(iterRef);
        removeWaitingRefsIfNoOutstandingIterators();
    }

    private void removeWaitingRefsIfNoOutstandingIterators()
    {
        if(outstandingIterators.isEmpty() && !weakRefsAwaitingRemoval.isEmpty())
        {
            for(Iterator iter = weakRefsAwaitingRemoval.iterator(); iter.hasNext(); super.elements.remove(iter.next()));
        }
    }

    private static final Object EMPTY = new EmptyValue();
    private final Vector weakRefsAwaitingRemoval;
    private final Vector outstandingIterators;




}
