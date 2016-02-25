// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/Outliner.java

package edu.mit.ai.psg.ui.outliner;

import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JFrame;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineMakerDefault, OutlineMaker, OutlineNode, OutlineRenderer

public class Outliner
{

    public static String getCopyright()
    {
        return "Copyright (c) 1997--1999 Massachusetts Institute of Technology.";
    }

    public static String getNoWarranty()
    {
        return "This software is provided ``as is'' without express or implied warranty.";
    }

    public static String getVersion()
    {
        return "prerelease version of 1999.06.09";
    }

    public static String getHomepage()
    {
        return "http://www.ai.mit.edu/people/caroma/tools/";
    }

    protected Outliner()
    {
    }

    public static JFrame outline(Object obj)
    {
        return outlineMaker.outline(obj);
    }

    public static JFrame makeFrame(Object obj)
    {
        return outlineMaker.makeFrame(obj);
    }

    public static JFrame makeFrame(String frameLabel, OutlineNode newRoot)
    {
        return outlineMaker.makeFrame(frameLabel, newRoot);
    }

    public static OutlineNode makeOutlineNode(Object obj)
    {
        return outlineMaker.makeOutlineNode(obj);
    }

    public static OutlineNode makeOutlineNode(Object label, Object obj)
    {
        return outlineMaker.makeOutlineNode(label, obj);
    }

    public static OutlineRenderer getMostSpecificRenderer(Object obj)
    {
        return outlineMaker.getMostSpecificRenderer(obj);
    }

    public static List getAllRenderers(Object obj)
    {
        return outlineMaker.getAllRenderers(obj);
    }

    public static void addRenderer(Class forInterface, OutlineRenderer renderer)
    {
        outlineMaker.addRenderer(forInterface, renderer);
    }

    public static boolean removeRenderer(Class forInterface, OutlineRenderer renderer)
    {
        return outlineMaker.removeRenderer(forInterface, renderer);
    }

    public static OutlineRenderer getNoteRenderer()
    {
        return outlineMaker.getNoteRenderer();
    }

    protected static String getOutlinerBundleString(String resourceKey)
    {
        return ResourceBundle.getBundle("edu.mit.ai.psg.ui.outliner.OutlinerBundle").getString(resourceKey);
    }

    static String formatWithOutlinerBundleString(String resourceKey, Object obj)
    {
        return MessageFormat.format(getOutlinerBundleString(resourceKey), new Object[] {
            obj
        });
    }

    static int fontStyleNameToStyle(String fontStyleName, int defaultStyle)
    {
        return "Plain".equalsIgnoreCase(fontStyleName) ? 0 : "Bold".equalsIgnoreCase(fontStyleName) ? 1 : "Italic".equalsIgnoreCase(fontStyleName) ? 2 : !"BoldItalic".equalsIgnoreCase(fontStyleName) && !"ItalicBold".equalsIgnoreCase(fontStyleName) ? defaultStyle : 3;
    }

    private static final String copyright = "Copyright (c) 1997--1999 Massachusetts Institute of Technology.";
    private static final String noWarranty = "This software is provided ``as is'' without express or implied warranty.";
    private static final String version = "prerelease version of 1999.06.09";
    private static final String homepage = "http://www.ai.mit.edu/people/caroma/tools/";
    public static OutlineMaker outlineMaker = new OutlineMakerDefault();
    protected static final String outlinerResourceName = "edu.mit.ai.psg.ui.outliner.OutlinerBundle";

}
