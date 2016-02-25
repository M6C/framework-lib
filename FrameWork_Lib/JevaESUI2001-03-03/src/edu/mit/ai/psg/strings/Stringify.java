// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   strings/Stringify.java

package edu.mit.ai.psg.strings;

import java.lang.reflect.Array;
import java.util.*;

// Referenced classes of package edu.mit.ai.psg.strings:
//            InProgress

public class Stringify
{

    public Stringify()
    {
    }

    public static String printToString(Object obj)
    {
        return printToString(printLength, obj);
    }

    public static String printToString(int printLength, Object obj)
    {
        boolean isNew = InProgress.add(obj);
        if(!isNew)
            return makeShortString(obj);
        try
        {
            String result = printToStringUnchecked(printLength, obj);
            if(!InProgress.isCyclic(obj))
            {
                String s = result;
                return s;
            }
            String s1 = makeShortString(obj) + "=" + result;
            return s1;
        }
        finally
        {
            InProgress.remove(obj);
        }
    }

    static String makeShortString(Object obj)
    {
        String className = arrayClassToString(obj.getClass());
        String shortClassName = className.substring(className.lastIndexOf('.') + 1);
        return "<" + shortClassName + "@" + Integer.toHexString(obj.hashCode()) + ">";
    }

    static String printToStringUnchecked(int printLength, Object obj)
    {
        if(null == obj)
            return "null";
        if(obj instanceof String)
            return "\"" + obj + "\"";
        if(obj.getClass().isArray())
        {
            StringBuffer str = new StringBuffer("[");
            if(Array.getLength(obj) > 0 && printLength > 0)
            {
                str.append(printToString(printLength, Array.get(obj, 0)));
                int limit = Math.min(Array.getLength(obj), printLength);
                for(int i = 1; i < limit; i++)
                {
                    str.append(", ");
                    str.append(printToString(printLength, Array.get(obj, i)));
                }

                if(limit < Array.getLength(obj))
                    str.append("... ");
            }
            str.append("]");
            return str.toString();
        }
        if(obj instanceof Map)
        {
            StringBuffer str = new StringBuffer(obj.getClass().getName() + "{");
            Map map = (Map)obj;
            if(map.size() > 0 && printLength > 0)
            {
                Iterator keyIter = map.keySet().iterator();
                int limit = Math.min(map.size(), printLength);
                for(int i = 1; i <= limit; i++)
                {
                    Object key = keyIter.next();
                    str.append("[" + printToString(printLength, key) + ":" + printToString(printLength, map.get(key)) + "]");
                    if(i < limit)
                        str.append(", ");
                }

                if(limit < map.size())
                    str.append("... ");
            }
            str.append("}");
            return str.toString();
        }
        if(obj instanceof Collection)
        {
            StringBuffer str = new StringBuffer(obj.getClass().getName());
            str.append((obj instanceof Set) ? "{" : "[");
            Collection collection = (Collection)obj;
            if(collection.size() > 0 && printLength > 0)
            {
                Iterator iter = collection.iterator();
                for(int i = 1; i <= printLength && iter.hasNext(); i++)
                {
                    str.append(printToString(printLength, iter.next()));
                    if(i < printLength && iter.hasNext())
                        str.append(", ");
                }

                if(printLength < collection.size())
                    str.append("... ");
            }
            str.append((obj instanceof Set) ? "}" : "]");
            return str.toString();
        }
        if(obj instanceof Class)
            return arrayClassToString((Class)obj) + ".class";
        try
        {
            return obj.toString();
        }
        catch(Throwable e)
        {
            return "<" + obj.getClass().getName() + "@" + obj.hashCode() + ": Error getting string -- " + e.getClass().getName() + ">";
        }
    }

    static String arrayClassToString(Class type)
    {
        if(!type.isArray())
            return type.getName();
        else
            return arrayClassToString(type.getComponentType()) + "[]";
    }

    public static String addCharEscapes(char charToEscape, String s)
    {
        StringBuffer buf = new StringBuffer(s);
        for(int i = 0; i < buf.length(); i++)
            if(buf.charAt(i) == charToEscape)
                buf.insert(i++, '\\');

        return buf.toString();
    }

    public static String removeCharEscapes(char escapedChar, String s)
    {
        StringBuffer buf = new StringBuffer(s);
        for(int i = 0; i + 1 < buf.length(); i++)
            if(buf.charAt(i) == '\\' && buf.charAt(i + 1) == escapedChar)
                buf.deleteCharAt(i);

        return buf.toString();
    }

    public static int printLength = 3;

}
