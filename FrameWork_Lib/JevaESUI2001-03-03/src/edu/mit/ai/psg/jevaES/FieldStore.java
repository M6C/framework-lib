// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNName.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ThrowException, IStore, EvalMethods

class FieldStore
    implements IStore
{

    FieldStore(Field initField, Object initTarget)
    {
        field = initField;
        target = initTarget;
    }

    public Class getType()
    {
        return field.getType();
    }

    public boolean isFinal()
    {
        return Modifier.isFinal(field.getModifiers());
    }

    public boolean isConstant()
    {
        return isFinal() && Modifier.isStatic(field.getModifiers()) && (getType().isPrimitive() || getType().equals(java.lang.String.class));
    }

    public Object getValue()
        throws ThrowException
    {
        try
        {
            return field.get(target);
        }
        catch(NullPointerException e)
        {
            throw new ThrowException(e);
        }
        catch(IllegalAccessException e)
        {
            throw EvalMethods.shouldNotHappen(e);
        }
    }

    public void setValue(Object newValue)
        throws ThrowException
    {
        try
        {
            field.set(target, newValue);
        }
        catch(NullPointerException e)
        {
            throw new ThrowException(e);
        }
        catch(IllegalAccessException e)
        {
            throw EvalMethods.shouldNotHappen(e);
        }
    }

    Field field;
    Object target;
}
