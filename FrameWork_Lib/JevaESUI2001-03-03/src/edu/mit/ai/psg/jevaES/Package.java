// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/Package.java

package edu.mit.ai.psg.jevaES;

import java.io.*;
import java.util.*;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            IPackage, EvalMethods, ClassPathMethods, IEnv

public class Package
    implements IPackage
{

    public Package(String fullName, IEnv rootEnv)
    {
        myFullName = fullName;
        if(fullName != null)
            mySimpleName = fullName.substring(fullName.lastIndexOf('.') + 1);
        myRootEnv = rootEnv;
    }

    protected synchronized void setEnv(IEnv fromEnv, IEnv toEnv)
    {
        if(myRootEnv != fromEnv)
        {
            throw EvalMethods.shouldNotHappen("incorrect old env.");
        } else
        {
            myRootEnv = toEnv;
            return;
        }
    }

    public String getFullName()
    {
        return myFullName;
    }

    public String getSimpleName()
    {
        return mySimpleName;
    }

    public synchronized IPackage getIPackage(String simplePackageName, boolean suppressPackageNameCheck)
        throws IllegalArgumentException
    {
        Object value = myTable.get(simplePackageName);
        if(value instanceof IPackage)
            return (IPackage)value;
        if(value instanceof Class)
            throw new IllegalArgumentException("A class with name " + simplePackageName + " is already defined in package " + myFullName);
        if(value == null || value == Boolean.FALSE)
        {
            String pkgName = myFullName != null ? myFullName + "." + simplePackageName : simplePackageName;
            if(suppressPackageNameCheck || ClassPathMethods.verifyPackageNameFromClassPath(pkgName))
            {
                IPackage newPkg = new Package(pkgName, myRootEnv);
                myTable.put(simplePackageName, newPkg);
                return newPkg;
            } else
            {
                throw new IllegalArgumentException("No accessible package named " + pkgName + " found in classpath.");
            }
        } else
        {
            throw unrecognizedPackageValue(value);
        }
    }

    private Error unrecognizedPackageValue(Object value)
    {
        return EvalMethods.shouldNotHappen("unrecognized package value: " + value);
    }

    public synchronized Class getClass(String simpleClassName, boolean cacheFailures)
    {
        Object value = myTable.get(simpleClassName);
        if(value instanceof Class)
            return (Class)value;
        if(value == Boolean.FALSE || (value instanceof IPackage))
            return null;
        if(null == value)
        {
            String fullClassName = null != myFullName ? myFullName + "." + simpleClassName : simpleClassName;
            try
            {
                ClassPathMethods.ClassPathSearchResult binResult = ClassPathMethods.findBytecodesInClassPath(fullClassName);
                try
                {
                    if(binResult != null)
                    {
                        if(loadVerbosely)
                            System.err.print("[Trying to load " + fullClassName + " from " + binResult.locationPath + "...");
                        Class class1 = getClassUsingForName(simpleClassName, fullClassName, cacheFailures);
                        return class1;
                    }
                }
                finally
                {
                    try
                    {
                        if(binResult != null)
                            binResult.stream.close();
                    }
                    catch(IOException ioexception) { }
                }
            }
            catch(SecurityException securityexception) { }
            if(loadVerbosely)
                System.err.print("[Trying Class.forName(\"" + fullClassName + "\",true," + getClass().getClassLoader() + ")...");
            return getClassUsingForName(simpleClassName, fullClassName, cacheFailures);
        } else
        {
            throw unrecognizedPackageValue(value);
        }
    }

    private Class getClassUsingForName(String simpleClassName, String fullClassName, boolean cacheFailures)
    {
        try
        {
            Class loadedClass = Class.forName(fullClassName);
            if(loadVerbosely)
                System.err.print("]");
            myTable.put(simpleClassName, loadedClass);
            return loadedClass;
        }
        catch(SecurityException securityexception)
        {
            if(loadVerbosely)
                System.err.print(" failed, due to security.]");
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            if(loadVerbosely)
                System.err.print(" failed.]");
        }
        catch(LinkageError e)
        {
            if(loadVerbosely)
                System.err.print("While loading " + fullClassName + ".class,\n" + e);
        }
        if(cacheFailures)
            myTable.put(simpleClassName, Boolean.FALSE);
        return null;
    }

    public String toString()
    {
        StringBuffer results = new StringBuffer();
        results.append("Package_" + myFullName + "{");
        Enumeration keyEnum = myTable.keys();
        Enumeration valEnum = myTable.elements();
        while(keyEnum.hasMoreElements()) 
        {
            Object key = keyEnum.nextElement();
            Object val = valEnum.nextElement();
            if(val != Boolean.FALSE)
            {
                results.append("" + key + ":" + val);
                if(keyEnum.hasMoreElements())
                    results.append(", ");
            }
        }
        results.append("}");
        return results.toString();
    }

    String myFullName;
    String mySimpleName;
    final Dictionary myTable = new Hashtable();
    IEnv myRootEnv;
    public static boolean loadVerbosely = false;

}
