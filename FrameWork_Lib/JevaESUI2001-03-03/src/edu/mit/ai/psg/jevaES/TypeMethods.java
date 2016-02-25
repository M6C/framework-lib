// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/TypeMethods.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.*;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ThrowException, VerifyingError, VerifyingException, Token, 
//            EvalMethods, IExpressionNode, IEnv

public class TypeMethods
{

    private TypeMethods()
    {
    }

    public static final boolean isReferenceType(Class type)
    {
        return type != null && !type.isPrimitive();
    }

    public static final boolean isClassType(Class type)
    {
        return type != null && !type.isPrimitive() && !type.isArray() && !type.isInterface();
    }

    public static final boolean isNumericType(Class type)
    {
        return type != null && type.isPrimitive() && type != Boolean.TYPE;
    }

    public static final boolean isIntegralType(Class type)
    {
        return isNumericType(type) && type != Double.TYPE && type != Float.TYPE;
    }

    public static final Class unaryPromoteType(Class type)
    {
        if(type == Integer.TYPE || type == Long.TYPE || type == Double.TYPE || type == Float.TYPE)
            return type;
        if(type == Character.TYPE || type == Short.TYPE || type == Byte.TYPE)
            return Integer.TYPE;
        else
            throw new IllegalArgumentException("Non numeric type: " + toSourceString(type));
    }

    public static final Class binaryPromoteType(Class cX, Class cY)
    {
        if(cX == Double.TYPE || cY == Double.TYPE)
            return Double.TYPE;
        if(cX == Float.TYPE || cY == Float.TYPE)
            return Float.TYPE;
        if(cX == Long.TYPE || cY == Long.TYPE)
            return Long.TYPE;
        else
            return Integer.TYPE;
    }

    public static final Class binaryCombineNumericTypes(Token operator, Class cX, Class cY)
    {
        if(isNumericType(cX) && isNumericType(cY))
            return binaryPromoteType(cX, cY);
        else
            throw new IllegalArgumentException("Can't combine non-numeric types (" + toSourceString(cX) + ") " + operator.image + " (" + toSourceString(cY) + ")");
    }

    public static final Class binaryCombineBitwiseTypes(Token operator, Class cX, Class cY)
    {
        if(cX == Boolean.TYPE && cY == Boolean.TYPE)
            return Boolean.TYPE;
        if(isIntegralType(cX) && isIntegralType(cY))
            return binaryCombineNumericTypes(operator, cX, cY);
        else
            throw new IllegalArgumentException(operator.image + " does not operate bitwise on " + "(" + toSourceString(cX) + ")" + operator.image + "(" + toSourceString(cY) + ")");
    }

    public static final Class binaryCombineShiftTypes(Token operator, Class leftType, Class rightType)
    {
        if(isIntegralType(leftType) && isIntegralType(rightType))
            return leftType;
        else
            throw new IllegalArgumentException("Operands to shift not primitive integral numbers: " + leftType.getName() + operator.image + rightType.getName());
    }

    public static final boolean isAssignable(Class receivingType, IExpressionNode expr, IEnv env)
        throws VerifyingException
    {
        Class exprType = EvalMethods.getType(expr, env);
        return receivingType == exprType || (isReferenceType(receivingType) ? canWidenReference(receivingType, exprType) : canWidenPrimitive(receivingType, exprType) || canNarrowPrimitiveConstant(receivingType, exprType, expr, env));
    }

    public static final boolean isBindableType(Class receivingType, Class exprType)
    {
        return receivingType == exprType || (isReferenceType(receivingType) ? canWidenReference(receivingType, exprType) : canWidenPrimitive(receivingType, exprType));
    }

    public static final boolean canWidenPrimitive(Class to, Class from)
    {
        return from == Byte.TYPE && (to == Short.TYPE || to == Integer.TYPE || to == Long.TYPE || to == Float.TYPE || to == Double.TYPE) || from == Short.TYPE && (to == Integer.TYPE || to == Long.TYPE || to == Float.TYPE || to == Double.TYPE) || from == Character.TYPE && (to == Integer.TYPE || to == Long.TYPE || to == Float.TYPE || to == Double.TYPE) || from == Integer.TYPE && (to == Long.TYPE || to == Float.TYPE || to == Double.TYPE) || from == Long.TYPE && (to == Float.TYPE || to == Double.TYPE) || from == Float.TYPE && to == Double.TYPE;
    }

    public static final boolean canNarrowPrimitiveConstant(Class receivingType, Class exprType, IExpressionNode expr, IEnv env)
        throws VerifyingException
    {
        if((receivingType == Character.TYPE || receivingType == Short.TYPE || receivingType == Byte.TYPE) && exprType == Integer.TYPE && EvalMethods.isConstant(expr, env))
        {
            int constantValue;
            try
            {
                constantValue = ((Integer)EvalMethods.eval(expr, env)).intValue();
            }
            catch(ThrowException e)
            {
                throw new VerifyingError(expr, env, e);
            }
            if(Character.TYPE == receivingType)
                return 0 <= constantValue && constantValue <= 65535;
            if(Byte.TYPE == receivingType)
                return -128 <= constantValue && constantValue <= 127;
            if(Short.TYPE == receivingType)
                return -32768 <= constantValue && constantValue <= 32767;
        }
        return false;
    }

    public static final boolean canNarrowPrimitive(Class to, Class from)
    {
        return from == Byte.TYPE && to == Character.TYPE || from == Short.TYPE && (to == Byte.TYPE || to == Character.TYPE) || from == Character.TYPE && (to == Byte.TYPE || to == Short.TYPE) || from == Integer.TYPE && (to == Byte.TYPE || to == Short.TYPE || to == Character.TYPE) || from == Long.TYPE && (to == Byte.TYPE || to == Short.TYPE || to == Character.TYPE || to == Integer.TYPE) || from == Float.TYPE && (to == Byte.TYPE || to == Short.TYPE || to == Character.TYPE || to == Integer.TYPE || to == Long.TYPE) || from == Double.TYPE && (to == Byte.TYPE || to == Short.TYPE || to == Character.TYPE || to == Integer.TYPE || to == Long.TYPE || to == Float.TYPE);
    }

    public static final boolean canWidenReference(Class to, Class from)
    {
        return from == null || to.isAssignableFrom(from);
    }

    public static final boolean isFinalClass(Class aClass)
    {
        return isClassType(aClass) && Modifier.isFinal(aClass.getModifiers());
    }

    public static final boolean isAbstractClass(Class aClass)
    {
        return isClassType(aClass) && Modifier.isAbstract(aClass.getModifiers());
    }

    public static final boolean isSubinterface(Class sub, Class aSuper)
    {
        return sub.isInterface() && aSuper.isInterface() && isSubclass(sub, aSuper);
    }

    public static final boolean isSubclass(Class sub, Class aSuper)
    {
        for(sub = sub.getSuperclass(); sub != null; sub = sub.getSuperclass())
            if(sub == aSuper)
                return true;

        return false;
    }

    public static final boolean isImplementation(Class sub, Class anInterface)
    {
        for(; sub != null; sub = sub.getSuperclass())
        {
            Class ifaces[] = sub.getInterfaces();
            for(int i = 0; i < ifaces.length; i++)
                if(anInterface == ifaces[i])
                    return true;

        }

        return false;
    }

    public static final boolean canNarrowReference(Class to, Class from)
    {
        if(to.isArray() && from.isArray())
            return canNarrowReference(to.getComponentType(), from.getComponentType());
        else
            return from == (java.lang.Object.class) && isReferenceType(to) || (isReferenceType(from) && to.isInterface() && !isFinalClass(from) && !to.isAssignableFrom(from) || from.isInterface() && (isReferenceType(to) && !isFinalClass(to) || isFinalClass(to) && from.isAssignableFrom(to) || to.isInterface() && !isSubinterface(from, to)));
    }

    public static final Class toPrimitiveType(Class wrapperType)
    {
        if((java.lang.Integer.class).equals(wrapperType))
            return Integer.TYPE;
        if((java.lang.Long.class).equals(wrapperType))
            return Long.TYPE;
        if((java.lang.Double.class).equals(wrapperType))
            return Double.TYPE;
        if((java.lang.Float.class).equals(wrapperType))
            return Float.TYPE;
        if((java.lang.Character.class).equals(wrapperType))
            return Character.TYPE;
        if((java.lang.Byte.class).equals(wrapperType))
            return Byte.TYPE;
        if((java.lang.Short.class).equals(wrapperType))
            return Short.TYPE;
        else
            return wrapperType;
    }

    public static final Class nameToPrimitiveType(String name)
        throws ClassNotFoundException
    {
        if("int".equals(name))
            return Integer.TYPE;
        if("long".equals(name))
            return Long.TYPE;
        if("double".equals(name))
            return Double.TYPE;
        if("float".equals(name))
            return Float.TYPE;
        if("char".equals(name))
            return Character.TYPE;
        if("byte".equals(name))
            return Byte.TYPE;
        if("short".equals(name))
            return Short.TYPE;
        if("boolean".equals(name))
            return Boolean.TYPE;
        if("void".equals(name))
            return Void.TYPE;
        else
            throw new ClassNotFoundException();
    }

    public static final String toSourceString(Class type)
    {
        if(!type.isArray())
            return type.getName();
        else
            return toSourceString(type.getComponentType()) + "[]";
    }

    public static final Class makeArrayType(Class baseType, int nDims)
    {
        return Array.newInstance(baseType, new int[nDims]).getClass();
    }

    public static final void checkCastTypes(Class castType, Class exprType)
    {
        if(null == exprType)
        {
            if(castType.isPrimitive())
                throw new ClassCastException("Can't cast null to primitive " + castType);
        } else
        if(exprType.isPrimitive() || castType.isPrimitive())
        {
            if(exprType.isPrimitive() != castType.isPrimitive())
                throw new ClassCastException("Can't cast primitive to reference or vice versa.");
        } else
        if(isClassType(exprType))
        {
            if(isClassType(castType))
            {
                if(!exprType.equals(castType) && !isSubclass(exprType, castType) && !isSubclass(castType, exprType))
                    throw new ClassCastException("Illegal cast -- unrelated types.");
            } else
            if(castType.isInterface())
            {
                if(isFinalClass(exprType) && !isImplementation(exprType, castType))
                    throw new ClassCastException("Final class doesn't implement interface.");
            } else
            if(exprType.isArray() && !(java.lang.Object.class).equals(castType))
                throw new ClassCastException("Can only cast arrays to Object.");
        } else
        if(exprType.isInterface())
        {
            if(isFinalClass(castType) && !isImplementation(castType, exprType))
                throw new ClassCastException("Final class doesn't implement interface.");
        } else
        if(exprType.isArray())
        {
            if(isClassType(castType) && !(java.lang.Object.class).equals(castType))
                throw new ClassCastException("Can only cast arrays to Object.");
            if(castType.isInterface() && !(java.lang.Cloneable.class).equals(castType))
                throw new ClassCastException("Arrays only implement Cloneable.");
            if(castType.isArray())
            {
                Class exprCompType = exprType.getComponentType();
                Class castCompType = castType.getComponentType();
                checkCastTypes(castCompType, exprCompType);
            }
        } else
        {
            throw EvalMethods.shouldNotHappen("Unrecognized cast type: " + castType);
        }
    }

    public static boolean isThrowableClass(Throwable thrown, Class throwClasses[])
    {
        for(int i = 0; i < throwClasses.length; i++)
            if(throwClasses[i].isInstance(thrown))
                return true;

        return (thrown instanceof Error) || (thrown instanceof RuntimeException);
    }

    public static final boolean isApplicable(Method method, Class argExprTypes[])
    {
        return isApplicable(method.getParameterTypes(), argExprTypes);
    }

    public static final boolean isApplicable(Constructor constructor, Class argExprTypes[])
    {
        return isApplicable(constructor.getParameterTypes(), argExprTypes);
    }

    public static final boolean isApplicable(Class parameterTypes[], Class argExprTypes[])
    {
        if(parameterTypes.length != argExprTypes.length)
            return false;
        for(int i = 0; i < parameterTypes.length; i++)
            if(!isBindableType(parameterTypes[i], argExprTypes[i]))
                return false;

        return true;
    }

    public static final boolean isMoreSpecific(Method m1, Method m2)
    {
        return isBindableType(m2.getDeclaringClass(), m1.getDeclaringClass()) && isApplicable(m2, m1.getParameterTypes());
    }

    public static final boolean isMoreSpecific(Constructor c1, Constructor c2)
    {
        return isApplicable(c2, c1.getParameterTypes());
    }

    public static final boolean isIntExpression(IExpressionNode expr, IEnv env)
        throws VerifyingException
    {
        Class exprType = EvalMethods.getType(expr, env);
        return isNumericType(exprType) && Integer.TYPE.equals(unaryPromoteType(exprType));
    }

    public static boolean isUncheckedException(Class exceptionClass)
    {
        return (java.lang.Error.class).isAssignableFrom(exceptionClass) || (java.lang.RuntimeException.class).isAssignableFrom(exceptionClass);
    }

    public static boolean isCaughtOrDeclaredException(Class exceptionClass, Class caughtOrDeclareds[])
    {
        for(int i = 0; i < caughtOrDeclareds.length; i++)
            if(caughtOrDeclareds[i].isAssignableFrom(exceptionClass))
                return true;

        return false;
    }
}
