// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/PrimitiveOperations.java

package edu.mit.ai.psg.jevaES;


// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ThrowException, TypeMethods

public class PrimitiveOperations
{

    private PrimitiveOperations()
    {
    }

    public static final boolean isNumericValue(Object obj)
    {
        return (obj instanceof Number) || (obj instanceof Character);
    }

    static final Number toNumber(Object obj)
    {
        if(obj instanceof Number)
            return (Number)obj;
        if(obj instanceof Character)
            return new Integer(((Character)obj).charValue());
        else
            throw new IllegalArgumentException("Not a Number or Character: " + obj);
    }

    public static final int unaryPromoteToInt(Object indexWrapper)
    {
        if(indexWrapper instanceof Number)
            return ((Number)indexWrapper).intValue();
        if(indexWrapper instanceof Character)
            return ((Character)indexWrapper).charValue();
        else
            throw new IllegalArgumentException("not Number or Character: " + indexWrapper);
    }

    public static final Object unaryPlus(Object obj)
    {
        Number n = toNumber(obj);
        if((n instanceof Integer) || (n instanceof Long) || (n instanceof Double) || (n instanceof Float))
            return n;
        else
            return new Integer(n.intValue());
    }

    public static final Object negate(Object obj)
    {
        Number n = toNumber(obj);
        if(n instanceof Long)
            return new Long(-n.longValue());
        if(n instanceof Double)
            return new Double(-n.doubleValue());
        if(n instanceof Float)
            return new Float(-n.floatValue());
        else
            return new Integer(-n.intValue());
    }

    public static final Object multiply(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            return new Double(nX.doubleValue() * nY.doubleValue());
        if((nX instanceof Float) || (nY instanceof Float))
            return new Float(nX.floatValue() * nY.floatValue());
        if((nX instanceof Long) || (nY instanceof Long))
            return new Long(nX.longValue() * nY.longValue());
        else
            return new Integer(nX.intValue() * nY.intValue());
    }

    public static final Object divide(Object oX, Object oY)
        throws ThrowException
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        try
        {
            if((nX instanceof Double) || (nY instanceof Double))
                return new Double(nX.doubleValue() / nY.doubleValue());
            if((nX instanceof Float) || (nY instanceof Float))
                return new Float(nX.floatValue() / nY.floatValue());
            if((nX instanceof Long) || (nY instanceof Long))
                return new Long(nX.longValue() / nY.longValue());
            else
                return new Integer(nX.intValue() / nY.intValue());
        }
        catch(ArithmeticException e)
        {
            throw new ThrowException(e);
        }
    }

    public static final Object remainder(Object oX, Object oY)
        throws ThrowException
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        try
        {
            if((nX instanceof Double) || (nY instanceof Double))
                return new Double(nX.doubleValue() % nY.doubleValue());
            if((nX instanceof Float) || (nY instanceof Float))
                return new Float(nX.floatValue() % nY.floatValue());
            if((nX instanceof Long) || (nY instanceof Long))
                return new Long(nX.longValue() % nY.longValue());
            else
                return new Integer(nX.intValue() % nY.intValue());
        }
        catch(ArithmeticException e)
        {
            throw new ThrowException(e);
        }
    }

    public static final Object add(Object oX, Object oY)
    {
        if((oX instanceof String) || (oY instanceof String))
            return "" + oX + oY;
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            return new Double(nX.doubleValue() + nY.doubleValue());
        if((nX instanceof Float) || (nY instanceof Float))
            return new Float(nX.floatValue() + nY.floatValue());
        if((nX instanceof Long) || (nY instanceof Long))
            return new Long(nX.longValue() + nY.longValue());
        else
            return new Integer(nX.intValue() + nY.intValue());
    }

    public static final Object subtract(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            return new Double(nX.doubleValue() - nY.doubleValue());
        if((nX instanceof Float) || (nY instanceof Float))
            return new Float(nX.floatValue() - nY.floatValue());
        if((nX instanceof Long) || (nY instanceof Long))
            return new Long(nX.longValue() - nY.longValue());
        else
            return new Integer(nX.intValue() - nY.intValue());
    }

    public static final Boolean lessThan(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            return toBoolean(nX.doubleValue() < nY.doubleValue());
        if((nX instanceof Float) || (nY instanceof Float))
            return toBoolean(nX.floatValue() < nY.floatValue());
        if((nX instanceof Long) || (nY instanceof Long))
            return toBoolean(nX.longValue() < nY.longValue());
        else
            return toBoolean(nX.intValue() < nY.intValue());
    }

    public static final Boolean greaterThan(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            return toBoolean(nX.doubleValue() > nY.doubleValue());
        if((nX instanceof Float) || (nY instanceof Float))
            return toBoolean(nX.floatValue() > nY.floatValue());
        if((nX instanceof Long) || (nY instanceof Long))
            return toBoolean(nX.longValue() > nY.longValue());
        else
            return toBoolean(nX.intValue() > nY.intValue());
    }

    public static final Boolean lessThanOrEqualTo(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            return toBoolean(nX.doubleValue() <= nY.doubleValue());
        if((nX instanceof Float) || (nY instanceof Float))
            return toBoolean(nX.floatValue() <= nY.floatValue());
        if((nX instanceof Long) || (nY instanceof Long))
            return toBoolean(nX.longValue() <= nY.longValue());
        else
            return toBoolean(nX.intValue() <= nY.intValue());
    }

    public static final Boolean greaterThanOrEqualTo(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            return toBoolean(nX.doubleValue() >= nY.doubleValue());
        if((nX instanceof Float) || (nY instanceof Float))
            return toBoolean(nX.floatValue() >= nY.floatValue());
        if((nX instanceof Long) || (nY instanceof Long))
            return toBoolean(nX.longValue() >= nY.longValue());
        else
            return toBoolean(nX.intValue() >= nY.intValue());
    }

    public static final Boolean equals(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            return toBoolean(nX.doubleValue() == nY.doubleValue());
        if((nX instanceof Float) || (nY instanceof Float))
            return toBoolean(nX.floatValue() == nY.floatValue());
        if((nX instanceof Long) || (nY instanceof Long))
            return toBoolean(nX.longValue() == nY.longValue());
        else
            return toBoolean(nX.intValue() == nY.intValue());
    }

    public static final Boolean notEquals(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            return toBoolean(nX.doubleValue() != nY.doubleValue());
        if((nX instanceof Float) || (nY instanceof Float))
            return toBoolean(nX.floatValue() != nY.floatValue());
        if((nX instanceof Long) || (nY instanceof Long))
            return toBoolean(nX.longValue() != nY.longValue());
        else
            return toBoolean(nX.intValue() != nY.intValue());
    }

    public static final Object and(Object oX, Object oY)
    {
        if((oX instanceof Boolean) && (oY instanceof Boolean))
            return ((Boolean)oX).booleanValue() & ((Boolean)oY).booleanValue() ? Boolean.TRUE : Boolean.FALSE;
        if(isNumericValue(oX) && isNumericValue(oY))
        {
            Number nX = toNumber(oX);
            Number nY = toNumber(oY);
            if((nX instanceof Double) || (nY instanceof Double))
                throw new IllegalArgumentException("& (Bitwise AND) on Double");
            if((nX instanceof Float) || (nY instanceof Float))
                throw new IllegalArgumentException("& (Bitwise AND) on Float");
            if((nX instanceof Long) || (nY instanceof Long))
                return new Long(nX.longValue() & nY.longValue());
            else
                return new Integer(nX.intValue() & nY.intValue());
        } else
        {
            throw new IllegalArgumentException("Illegal arguments to &:" + oX + " & " + oY);
        }
    }

    public static final Object xor(Object oX, Object oY)
    {
        if((oX instanceof Boolean) && (oY instanceof Boolean))
            return ((Boolean)oX).booleanValue() ^ ((Boolean)oY).booleanValue() ? Boolean.TRUE : Boolean.FALSE;
        if(isNumericValue(oX) && isNumericValue(oY))
        {
            Number nX = toNumber(oX);
            Number nY = toNumber(oY);
            if((nX instanceof Double) || (nY instanceof Double))
                throw new IllegalArgumentException("^ (Bitwise XOR) on Double");
            if((nX instanceof Float) || (nY instanceof Float))
                throw new IllegalArgumentException("^ (Bitwise XOR) on Float");
            if((nX instanceof Long) || (nY instanceof Long))
                return new Long(nX.longValue() ^ nY.longValue());
            else
                return new Integer(nX.intValue() ^ nY.intValue());
        } else
        {
            throw new IllegalArgumentException("Illegal arguments to ^:" + oX + " ^ " + oY);
        }
    }

    public static final Object or(Object oX, Object oY)
    {
        if((oX instanceof Boolean) && (oY instanceof Boolean))
            return ((Boolean)oX).booleanValue() | ((Boolean)oY).booleanValue() ? Boolean.TRUE : Boolean.FALSE;
        if(isNumericValue(oX) && isNumericValue(oY))
        {
            Number nX = toNumber(oX);
            Number nY = toNumber(oY);
            if((nX instanceof Double) || (nY instanceof Double))
                throw new IllegalArgumentException("| (Bitwise OR) on Double");
            if((nX instanceof Float) || (nY instanceof Float))
                throw new IllegalArgumentException("| (Bitwise OR) on Float");
            if((nX instanceof Long) || (nY instanceof Long))
                return new Long(nX.longValue() | nY.longValue());
            else
                return new Integer(nX.intValue() | nY.intValue());
        } else
        {
            throw new IllegalArgumentException("Illegal arguments to |:" + oX + " | " + oY);
        }
    }

    public static final Object bitwiseComplement(Object oX)
    {
        Number nX = toNumber(oX);
        if(nX instanceof Double)
            throw new IllegalArgumentException("~ (Bitwise Complement) on Double");
        if(nX instanceof Float)
            throw new IllegalArgumentException("~ (Bitwise Complement) on Float");
        if(nX instanceof Long)
            return new Long(~nX.longValue());
        else
            return new Integer(~nX.intValue());
    }

    public static final Object leftShift(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            throw new IllegalArgumentException("<< (left shift) on Double");
        if((nX instanceof Float) || (nY instanceof Float))
            throw new IllegalArgumentException("<< (left shift) on Float");
        if(nX instanceof Long)
            return new Long(nX.longValue() << nY.intValue());
        else
            return new Integer(nX.intValue() << nY.intValue());
    }

    public static final Object rightSignedShift(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            throw new IllegalArgumentException(">> (right signed shift) on Double");
        if((nX instanceof Float) || (nY instanceof Float))
            throw new IllegalArgumentException(">> (right signed shift) on Float");
        if(nX instanceof Long)
            return new Long(nX.longValue() >> nY.intValue());
        else
            return new Integer(nX.intValue() >> nY.intValue());
    }

    public static final Object rightUnsignedShift(Object oX, Object oY)
    {
        Number nX = toNumber(oX);
        Number nY = toNumber(oY);
        if((nX instanceof Double) || (nY instanceof Double))
            throw new IllegalArgumentException(">>>(right unsigned shift)on Double");
        if((nX instanceof Float) || (nY instanceof Float))
            throw new IllegalArgumentException(">>>(right unsigned shift)on Float");
        if(nX instanceof Long)
            return new Long(nX.longValue() >>> nY.intValue());
        else
            return new Integer(nX.intValue() >>> nY.intValue());
    }

    public static final Object castPrimitive(Class castType, Object value)
        throws ClassCastException
    {
        if(Integer.TYPE == castType)
        {
            if(value instanceof Integer)
                return value;
            if(value instanceof Number)
                return new Integer(((Number)value).intValue());
            if(value instanceof Character)
                return new Integer(((Character)value).charValue());
            else
                throw primitiveCastException("(int) ", value);
        }
        if(Byte.TYPE == castType)
        {
            if(value instanceof Byte)
                return value;
            if(value instanceof Number)
                return new Byte(((Number)value).byteValue());
            if(value instanceof Character)
                return new Byte((byte)((Character)value).charValue());
            else
                throw primitiveCastException("(byte) ", value);
        }
        if(Short.TYPE == castType)
        {
            if(value instanceof Short)
                return value;
            if(value instanceof Number)
                return new Short(((Number)value).shortValue());
            if(value instanceof Character)
                return new Short((short)((Character)value).charValue());
            else
                throw primitiveCastException("(short) ", value);
        }
        if(Long.TYPE == castType)
        {
            if(value instanceof Long)
                return value;
            if(value instanceof Number)
                return new Long(((Number)value).longValue());
            if(value instanceof Character)
                return new Long(((Character)value).charValue());
            else
                throw primitiveCastException("(long) ", value);
        }
        if(Float.TYPE == castType)
        {
            if(value instanceof Float)
                return value;
            if(value instanceof Number)
                return new Float(((Number)value).floatValue());
            if(value instanceof Character)
                return new Float(((Character)value).charValue());
            else
                throw primitiveCastException("(float) ", value);
        }
        if(Double.TYPE == castType)
        {
            if(value instanceof Double)
                return value;
            if(value instanceof Number)
                return new Double(((Number)value).doubleValue());
            if(value instanceof Character)
                return new Double(((Character)value).charValue());
            else
                throw primitiveCastException("(double) ", value);
        }
        if(Character.TYPE == castType)
        {
            if(value instanceof Character)
                return value;
            if(value instanceof Number)
                return new Character((char)((Number)value).intValue());
            else
                throw primitiveCastException("(char) ", value);
        }
        if(Boolean.TYPE == castType)
        {
            if(value instanceof Boolean)
                return value;
            else
                throw primitiveCastException("(boolean) ", value);
        } else
        {
            throw new IllegalArgumentException("Unrecognized primitive type: " + TypeMethods.toSourceString(castType));
        }
    }

    private static ClassCastException primitiveCastException(String castInParens, Object value)
    {
        return new ClassCastException(castInParens + (value != null ? TypeMethods.toSourceString(value.getClass()) : "null"));
    }

    public static final Object defaultValue(Class storeType)
    {
        if(Boolean.TYPE == storeType)
            return defaultBoolean;
        if(Byte.TYPE == storeType)
            return defaultByte;
        if(Short.TYPE == storeType)
            return defaultShort;
        if(Character.TYPE == storeType)
            return defaultCharacter;
        if(Integer.TYPE == storeType)
            return defaultInteger;
        if(Long.TYPE == storeType)
            return defaultLong;
        if(Float.TYPE == storeType)
            return defaultFloat;
        if(Double.TYPE == storeType)
            return defaultDouble;
        else
            return null;
    }

    public static final Boolean toBoolean(boolean value)
    {
        return value ? Boolean.TRUE : Boolean.FALSE;
    }

    static final Boolean defaultBoolean = toBoolean(false);
    static final Byte defaultByte = new Byte((byte)0);
    static final Short defaultShort = new Short((short)0);
    static final Character defaultCharacter = new Character('\0');
    static final Integer defaultInteger = new Integer(0);
    static final Long defaultLong = new Long(0L);
    static final Float defaultFloat = new Float(0.0F);
    static final Double defaultDouble = new Double(0.0D);

}
