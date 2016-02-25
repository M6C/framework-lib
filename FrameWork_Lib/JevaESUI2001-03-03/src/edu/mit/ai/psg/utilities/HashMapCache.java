// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/HashMapCache.java

package edu.mit.ai.psg.utilities;

import java.lang.ref.SoftReference;
import java.util.*;

// Referenced classes of package edu.mit.ai.psg.utilities:
//            ReferenceCleaner

public class HashMapCache extends WeakHashMap
{
    protected static class Entry
        implements java.util.Map.Entry
    {

        public Object getKey()
        {
            return key;
        }

        public Object getValue()
        {
            return value;
        }

        public Object setValue(Object v)
        {
            throw new UnsupportedOperationException();
        }

        public int hashCode()
        {
            return (key != null ? key.hashCode() : 0) ^ (value != null ? value.hashCode() : 0);
        }

        public boolean equals(Object obj)
        {
            if(obj instanceof java.util.Map.Entry)
            {
                java.util.Map.Entry entry2 = (java.util.Map.Entry)obj;
                Object key2 = entry2.getKey();
                Object value2 = entry2.getValue();
                return (key != null ? key.equals(key2) : key2 == null) && (value != null ? value.equals(value2) : value2 == null);
            } else
            {
                return false;
            }
        }

        Object key;
        Object value;

        Entry(Object key, Object value)
        {
            this.key = key;
            this.value = value;
        }
    }

    protected class EntrySet extends AbstractSet
    {

        public Iterator iterator()
        {
            return new Iterator() {

                public boolean hasNext()
                {
                    if(nextEntry != null)
                        return true;
                    while(softIterator.hasNext()) 
                    {
                        java.util.Map.Entry softEntry = (java.util.Map.Entry)softIterator.next();
                        CacheReference valRef = (CacheReference)softEntry.getValue();
                        if(valRef != null)
                        {
                            Object value = valRef.get();
                            if(value != null)
                            {
                                nextEntry = new HashMapCache.Entry(softEntry.getKey(), value);
                                return true;
                            }
                        } else
                        {
                            nextEntry = new HashMapCache.Entry(softEntry.getKey(), null);
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
                        lastEntry = nextEntry;
                        nextEntry = null;
                        return lastEntry;
                    }
                }

                public void remove()
                {
                    if(lastEntry == null)
                        throw new IllegalStateException();
                    if(nextEntry != null)
                    {
                        throw new IllegalStateException();
                    } else
                    {
                        softIterator.remove();
                        return;
                    }
                }

                Iterator softIterator;
                java.util.Map.Entry nextEntry;
                java.util.Map.Entry lastEntry;

                
                {
                    softIterator = entrySet().iterator();
                    nextEntry = null;
                    lastEntry = null;
                }
            }
;
        }

        public int size()
        {
            int size = 0;
            for(Iterator iter = iterator(); iter.hasNext(); iter.next())
                size++;

            return size;
        }

        public boolean isEmpty()
        {
            return !iterator().hasNext();
        }


        protected EntrySet()
        {
        }
    }

    private class CacheReference extends SoftReference
        implements ReferenceCleaner.Cleanable
    {

        public void cleanUp()
        {
            Object key = keyRef != null ? keyRef.get() : null;
            if(key == null && keyRef != null)
                return;
            HashMapCache cache = HashMapCache.this;
            synchronized(cache)
            {
                if(cache.get(key) == this)
                    cache.remove(key);
            }
        }

        final SoftReference keyRef;

        CacheReference(Object key, Object value)
        {
            super(value, ReferenceCleaner.referenceQueue);
            keyRef = key != null ? new SoftReference(key) : null;
            if(value == null)
                throw new NullPointerException("null value");
            else
                return;
        }
    }


    public HashMapCache()
    {
        entrySet = null;
    }

    public HashMapCache(int initialCapacity)
    {
        super(initialCapacity);
        entrySet = null;
    }

    public HashMapCache(int initialCapacity, float loadFactor)
    {
        super(initialCapacity, loadFactor);
        entrySet = null;
    }

    public Object put(Object key, Object value)
    {
        Object oldValue = get(key);
        super.put(key, value != null ? ((Object) (new CacheReference(key, value))) : null);
        return oldValue;
    }

    public Object get(Object key)
    {
        CacheReference ref = (CacheReference)super.get(key);
        return ref != null ? ref.get() : null;
    }

    public Set entrySet()
    {
        if(entrySet == null)
            entrySet = new EntrySet();
        return entrySet;
    }

    private Set entrySet;

}
