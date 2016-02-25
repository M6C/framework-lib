// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNEArrayAccessExpression.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.Array;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ThrowException, IStore

class ArrayStore
    implements IStore
{

    protected ArrayStore(Object initArray, int initIndex)
    {
        array = initArray;
        index = initIndex;
    }

    public Class getType()
    {
        return array.getClass().getComponentType();
    }

    public boolean isFinal()
    {
        return false;
    }

    public boolean isConstant()
    {
        return false;
    }

    public Object getValue()
        throws ThrowException
    {
        try
        {
            return Array.get(array, index);
        }
        catch(NullPointerException e)
        {
            throw new ThrowException(e);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            throw new ThrowException(e);
        }
    }

    public void setValue(Object newValue)
        throws ThrowException
    {
        try
        {
            Array.set(array, index, newValue);
        }
        catch(NullPointerException e)
        {
            throw new ThrowException(e);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            throw new ThrowException(e);
        }
        catch(IllegalArgumentException e)
        {
            throw new ThrowException(new ArrayStoreException(e.getMessage()));
        }
    }

    Object array;
    int index;
}
