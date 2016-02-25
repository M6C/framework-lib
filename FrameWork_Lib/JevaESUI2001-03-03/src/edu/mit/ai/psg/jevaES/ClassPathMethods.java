// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/ClassPathMethods.java

package edu.mit.ai.psg.jevaES;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassPathMethods
{
    public static class StarFilenameFilter
        implements FilenameFilter
    {

        public boolean accept(File dir, String name)
        {
            if(null == name)
                throw new NullPointerException("Null filename");
            if(1 == mySubpatterns.size())
                return name.equals(mySubpatterns.get(0));
            String subpattern = (String)mySubpatterns.get(0);
            if(!name.startsWith(subpattern))
                return false;
            int index = subpattern.length();
            for(int n = 1; n < mySubpatterns.size() - 1; n++)
            {
                subpattern = (String)mySubpatterns.get(n);
                index = name.indexOf(subpattern, index);
                if(-1 == index)
                    return false;
                index += subpattern.length();
            }

            subpattern = (String)mySubpatterns.get(mySubpatterns.size() - 1);
            return name.lastIndexOf(subpattern) == name.length() - subpattern.length();
        }

        public static final char STAR = 42;
        final List mySubpatterns = new ArrayList();
        boolean ignoreCase;

        public StarFilenameFilter(String starPattern)
        {
            ignoreCase = false;
            if(-1 != starPattern.indexOf(File.separator))
                throw new Error("StarFilenameFilter: can't handle directories in names");
            mySubpatterns.clear();
            int start = 0;
            int end = starPattern.indexOf('*', start);
            if(-1 == end)
            {
                mySubpatterns.add(starPattern);
            } else
            {
                for(; end != -1; end = starPattern.indexOf('*', start))
                {
                    mySubpatterns.add(starPattern.substring(start, end));
                    start = end + 1;
                }

                mySubpatterns.add(starPattern.substring(start, starPattern.length()));
            }
        }
    }

    public static class ClassPathSearchResult
    {

        public String locationPath;
        public InputStream stream;
        public long modificationDate;

        public ClassPathSearchResult(String locPath, InputStream str, long lastModifiedDate)
        {
            locationPath = locPath;
            stream = str;
            modificationDate = lastModifiedDate;
        }
    }


    public ClassPathMethods()
    {
    }

    public static synchronized List getClassPathStrings()
    {
        if(classPaths != null)
            return classPaths;
        classPaths = new Vector();
        classPaths.addAll(parseClassPathString(bootClassPath));
        try
        {
            List extPaths = parseClassPathString(System.getProperty("java.ext.dirs"));
            FilenameFilter jarFilter = new StarFilenameFilter("*.jar");
            FilenameFilter zipFilter = new StarFilenameFilter("*.zip");
            for(Iterator iter = extPaths.iterator(); iter.hasNext();)
            {
                File dir = new File((String)iter.next());
                String extJarFiles[] = dir.list(jarFilter);
                if(extJarFiles != null)
                    classPaths.addAll(Arrays.asList(extJarFiles));
                String extZipFiles[] = dir.list(zipFilter);
                if(extZipFiles != null)
                    classPaths.addAll(Arrays.asList(extZipFiles));
            }

        }
        catch(SecurityException securityexception) { }
        try
        {
            String applicationClassPath = System.getProperty("java.class.path");
            if(applicationClassPath != null)
                classPaths.addAll(parseClassPathString(applicationClassPath));
        }
        catch(SecurityException securityexception1) { }
        return classPaths;
    }

    public static List parseClassPathString(String classPathString)
    {
        List roots = new Vector();
        if(classPathString != null)
        {
            for(int endIdx = classPathString.indexOf(File.pathSeparator); endIdx != -1; endIdx = classPathString.indexOf(File.pathSeparator))
            {
                String path = classPathString.substring(0, endIdx);
                if(!"".equals(path))
                    roots.add(path);
                classPathString = classPathString.substring(endIdx + File.pathSeparator.length());
            }

            if(!"".equals(classPathString))
                roots.add(classPathString);
        }
        return roots;
    }

    public static String dotsToSeparators(String input)
    {
        if(File.separator.length() != 1)
            throw new Error("java.io.File.separator.length() != 1 -- not handled.");
        else
            return dotsToSeparators(input, File.separatorChar);
    }

    public static String dotsToSeparators(String input, char separatorChar)
    {
        StringBuffer result = new StringBuffer(input);
        for(int i = 0; i < result.length(); i++)
            if(result.charAt(i) == '.')
                result.setCharAt(i, separatorChar);

        return result.toString();
    }

    public static ClassPathSearchResult findSourceInClassPath(String fullClassName)
    {
        return findInClassPath(getClassPathStrings(), fullClassName, "java");
    }

    public static ClassPathSearchResult findBytecodesInClassPath(String fullClassName)
    {
        return findInClassPath(getClassPathStrings(), fullClassName, "class");
    }

    public static ClassPathSearchResult findInClassPath(List classPathStrings, String fullClassName, String suffix)
    {
        String fileName = dotsToSeparators(fullClassName) + "." + suffix;
        String jarEntryName = dotsToSeparators(fullClassName, '/') + "." + suffix;
        for(Iterator pathIter = classPathStrings.iterator(); pathIter.hasNext();)
        {
            String path = (String)pathIter.next();
            if(path != null && (path.endsWith(".jar") || path.endsWith(".zip")))
            {
                ZipFile jarZipFile = getJar(path);
                if(jarZipFile != null)
                    try
                    {
                        ZipEntry classEntry = jarZipFile.getEntry(jarEntryName);
                        if(classEntry != null)
                        {
                            long lastModified = classEntry.getTime();
                            if(lastModified == -1L)
                            {
                                File jarFile = (File)fileCache.get(path);
                                lastModified = jarFile.lastModified();
                            }
                            return new ClassPathSearchResult(path, jarZipFile.getInputStream(classEntry), lastModified);
                        }
                    }
                    catch(IOException ioexception) { }
            } else
            {
                try
                {
                    File file = new File(path, fileName);
                    if(file.exists())
                        return new ClassPathSearchResult(path, new FileInputStream(file), file.lastModified());
                }
                catch(FileNotFoundException filenotfoundexception) { }
                catch(SecurityException securityexception) { }
            }
        }

        return null;
    }

    private static ZipFile getJar(String path)
    {
        ZipFile result = (ZipFile)jarFileCache.get(path);
        if(result != null)
            return result;
        File jarFile = new File(path);
        if(jarFile.exists())
            try
            {
                ZipFile jarZipFile = new ZipFile(jarFile);
                if(jarZipFile != null)
                {
                    jarFileCache.put(path, jarZipFile);
                    fileCache.put(path, jarFile);
                    return jarZipFile;
                } else
                {
                    return null;
                }
            }
            catch(IOException ioexception) { }
            catch(SecurityException securityexception) { }
        return null;
    }

    public static boolean verifyPackageNameFromClassPath(String fullPackageName)
    {
        return verifyPackageNameFromClassPath(getClassPathStrings(), fullPackageName);
    }

    public static boolean verifyPackageNameFromClassPath(List classPathStrings, String fullPackageName)
    {
        if(Boolean.TRUE == packageNameCache.get(fullPackageName))
            return true;
        String directoryName = dotsToSeparators(fullPackageName);
        String jarDirName = dotsToSeparators(fullPackageName, '/') + "/";
        for(Iterator pathIter = classPathStrings.iterator(); pathIter.hasNext();)
        {
            String path = (String)pathIter.next();
            if(path != null && (path.endsWith(".jar") || path.endsWith(".zip")))
            {
                ZipFile jarZipFile = getJar(path);
                if(jarZipFile != null)
                {
                    ZipEntry dirEntry = jarZipFile.getEntry(jarDirName);
                    if(dirEntry != null)
                    {
                        packageNameCache.put(fullPackageName, Boolean.TRUE);
                        return true;
                    }
                }
            } else
            {
                try
                {
                    File dir = new File(path, directoryName);
                    if(dir.exists() && dir.isDirectory() && dir.getCanonicalPath().endsWith(directoryName))
                    {
                        packageNameCache.put(fullPackageName, Boolean.TRUE);
                        return true;
                    }
                }
                catch(IOException ioexception) { }
                catch(SecurityException securityexception) { }
            }
        }

        return false;
    }

    public static void clearCaches()
    {
        classPaths = null;
        jarFileCache.clear();
        fileCache.clear();
        packageNameCache.clear();
    }

    public static List classPaths = null;
    public static String bootClassPath;
    static Hashtable jarFileCache = new Hashtable();
    static Hashtable fileCache = new Hashtable();
    static Hashtable packageNameCache = new Hashtable();

    static 
    {
        try
        {
            bootClassPath = System.getProperty("sun.boot.class.path");
        }
        catch(SecurityException securityexception) { }
    }
}
