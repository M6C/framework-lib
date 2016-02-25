// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/ArrayMap.java

package edu.mit.ai.psg.utilities;

import java.util.*;

public class ArrayMap extends AbstractMap
    implements Cloneable
{

    public ArrayMap()
    {
        keysAndVals = new Object[0];
    }

    public ArrayMap(Map initElements)
    {
        keysAndVals = new Object[0];
        setCapacity(initElements.size());
        int i = 0;
        for(Iterator iter = initElements.entrySet().iterator(); iter.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
            keysAndVals[i] = entry.getKey();
            keysAndVals[i + 1] = entry.getValue();
            i += 2;
        }

    }

    public ArrayMap(Object initElements[])
    {
        keysAndVals = new Object[0];
        if(initElements.length % 2 != 0)
        {
            throw new IllegalArgumentException("Odd length: No value for last key.");
        } else
        {
            setCapacity(initElements.length / 2);
            System.arraycopy(((Object) (initElements)), 0, ((Object) (keysAndVals)), 0, initElements.length);
            return;
        }
    }

    private int findIndex(Object key)
    {
        for(int i = keysAndVals.length - 2; i >= 0; i -= 2)
            if(key == null ? null == keysAndVals[i] : key.equals(keysAndVals[i]))
                return i;

        return -1;
    }

    private void setCapacity(int capacity)
    {
        if(keysAndVals.length != capacity * 2)
        {
            Object newKeysAndVals[] = new Object[capacity * 2];
            System.arraycopy(((Object) (keysAndVals)), 0, ((Object) (newKeysAndVals)), 0, Math.min(keysAndVals.length, newKeysAndVals.length));
            keysAndVals = newKeysAndVals;
        }
    }

    public int size()
    {
        return keysAndVals.length / 2;
    }

    public boolean isEmpty()
    {
        return keysAndVals.length == 0;
    }

    public Object get(Object key)
    {
        int i = findIndex(key);
        if(i >= 0)
            return keysAndVals[i + 1];
        else
            return null;
    }

    public Object put(Object key, Object newVal)
    {
        int i = findIndex(key);
        if(i >= 0)
        {
            Object oldVal = keysAndVals[i + 1];
            keysAndVals[i + 1] = newVal;
            return oldVal;
        } else
        {
            setCapacity(size() + 1);
            keysAndVals[keysAndVals.length - 2] = key;
            keysAndVals[keysAndVals.length - 1] = newVal;
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
        Object oldVal = keysAndVals[i + 1];
        Object newKeysAndVals[] = new Object[keysAndVals.length - 2];
        System.arraycopy(((Object) (keysAndVals)), 0, ((Object) (newKeysAndVals)), 0, i);
        System.arraycopy(((Object) (keysAndVals)), i + 2, ((Object) (newKeysAndVals)), i, keysAndVals.length - (i + 2));
        keysAndVals = newKeysAndVals;
        return oldVal;
    }

    public Object clone()
    {
        return new ArrayMap(this);
    }

    public Set entrySet()
    {
        return new AbstractSet() {

            public int size()
            {
                return ArrayMap.this.size();
            }

            public Iterator iterator()
            {
                return new Iterator() {

                    public boolean hasNext()
                    {
                        return position < keysAndVals.length;
                    }

                    public Object next()
                    {
                        final int entryPos = position;
                        position += 2;
                        isRemoved = false;
                        return new java.util.Map.Entry() {

                            public Object getKey()
                            {
                                return keysAndVals[entryPos];
                            }

                            public Object getValue()
                            {
                                return keysAndVals[entryPos + 1];
                            }

                            public Object setValue(Object value)
                            {
                                Object oldValue = keysAndVals[entryPos + 1];
                                keysAndVals[entryPos + 1] = value;
                                return oldValue;
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
                            ArrayMap.this.remove(position - 2);
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

    private Object keysAndVals[];


}
