// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/AccessibilityMethods.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public class AccessibilityMethods
{

    public static boolean isPublicToTopLevel(Class aClassOrInterface)
    {
        return Modifier.isPublic(aClassOrInterface.getModifiers()) && (null == aClassOrInterface.getDeclaringClass() || isPublicToTopLevel(aClassOrInterface.getDeclaringClass()));
    }

    public static boolean isPublicToTopLevel(Member member)
    {
        return Modifier.isPublic(member.getModifiers()) && isPublicToTopLevel(member.getDeclaringClass());
    }

    public static boolean isAccessibleFromPackage(Member member, String packageName)
    {
        return isAccessibleFromPackage(member.getDeclaringClass(), packageName) && isAccessibleFromPackage(member.getModifiers(), member.getDeclaringClass(), packageName);
    }

    public static boolean isAccessibleFromPackage(Class memberClass, String packageName)
    {
        return (memberClass.getDeclaringClass() == null || isAccessibleFromPackage(memberClass.getDeclaringClass(), packageName)) && isAccessibleFromPackage(memberClass.getModifiers(), memberClass, packageName);
    }

    private static boolean isAccessibleFromPackage(int memberModifiers, Class memberClass, String packageName)
    {
        if(Modifier.isPublic(memberModifiers))
            return true;
        if(Modifier.isPrivate(memberModifiers))
            return false;
        else
            return getPackageNameFromClass(memberClass).equals(packageName != null ? ((Object) (packageName)) : "");
    }

    private static String getPackageNameFromClass(Class aClass)
    {
        for(; aClass.getDeclaringClass() != null; aClass = aClass.getDeclaringClass());
        String aClassName = aClass.getName();
        int lastDotIndex = aClassName.lastIndexOf('.');
        return lastDotIndex != -1 ? aClassName.substring(0, lastDotIndex) : "";
    }

    private AccessibilityMethods()
    {
    }
}
