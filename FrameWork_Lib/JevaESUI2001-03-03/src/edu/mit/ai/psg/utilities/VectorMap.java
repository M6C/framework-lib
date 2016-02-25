// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/VectorMap.java

package edu.mit.ai.psg.utilities;

import java.util.*;

public class VectorMap extends AbstractMap
    implements Cloneable
{

    public VectorMap()
    {
        keysAndVals = new Vector();
    }

    public VectorMap(Map initElements)
    {
        keysAndVals = new Vector();
        super.putAll(initElements);
    }

    public VectorMap(Object initElements[])
    {
        keysAndVals = new Vector();
        if(initElements.length % 2 != 0)
            throw new IllegalArgumentException("Odd length: No value for last key.");
        keysAndVals.ensureCapacity(initElements.length);
        for(int i = 0; i < initElements.length; i += 2)
        {
            keysAndVals.add(initElements[i]);
            keysAndVals.add(initElements[i + 1]);
        }

    }

    private int findIndex(Object key)
    {
        for(int i = keysAndVals.size() - 2; i >= 0; i -= 2)
            if(key == null ? null == keysAndVals.get(i) : key.equals(keysAndVals.get(i)))
                return i;

        return -1;
    }

    public int size()
    {
        return keysAndVals.size() / 2;
    }

    public boolean isEmpty()
    {
        return keysAndVals.size() == 0;
    }

    public Object get(Object key)
    {
        int i = findIndex(key);
        if(i >= 0)
            return keysAndVals.get(i + 1);
        else
            return null;
    }

    public Object put(Object key, Object newVal)
    {
        int i = findIndex(key);
        if(i >= 0)
        {
            return keysAndVals.set(i + 1, newVal);
        } else
        {
            keysAndVals.add(key);
            keysAndVals.add(newVal);
            return null;
        }
    }

    public Object remove(Object key)
    {
        int i = findIndex(key);
        if(i >= 0)
            return remove(i);
        else
            return null;
    }

    private Object remove(int i)
    {
        Object oldVal = keysAndVals.get(i + 1);
        keysAndVals.remove(i + 1);
        keysAndVals.remove(i);
        return oldVal;
    }

    public Object clone()
    {
        return new VectorMap(this);
    }

    public void trimToSize()
    {
        keysAndVals.trimToSize();
    }

    public Set entrySet()
    {
        return new AbstractSet() {

            public int size()
            {
                return VectorMap.this.size();
            }

            public Iterator iterator()
            {
                return new Iterator() {

                    public boolean hasNext()
                    {
                        return position < keysAndVals.size();
                    }

                    public Object next()
                    {
                        final int entryPos = position;
                        position += 2;
                        isRemoved = false;
                        return new java.util.Map.Entry() {

                            public Object getKey()
                            {
                                return keysAndVals.get(entryPos);
                            }

                            public Object getValue()
                            {
                                return keysAndVals.get(entryPos + 1);
                            }

                            public Object setValue(Object v)
                            {
                                return keysAndVals.set(entryPos + 1, v);
                            }

                        }
;
                    }

                    public void remove()
                    {
                        if(isRemoved || position == 0)
                        {
                            throw new IllegalStateException();
                        } else
                        {
                            VectorMap.this.remove(position - 2);
                            isRemoved = true;
                            return;
                        }
                    }

                    int position;
                    boolean isRemoved;


                    
                    {
                        position = 0;
                        isRemoved = false;
                    }
                }
;
            }


        }
;
    }

    private final Vector keysAndVals;


}
