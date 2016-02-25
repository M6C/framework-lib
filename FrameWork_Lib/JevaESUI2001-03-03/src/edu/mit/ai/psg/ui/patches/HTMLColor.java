// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/patches/HTMLColor.java

package edu.mit.ai.psg.ui.patches;

import java.awt.Color;
import java.util.*;

public class HTMLColor
{

    public HTMLColor()
    {
    }

    public static Color decode(String colorString)
        throws NumberFormatException
    {
        Color color = (Color)htmlColorNames.get(colorString.toLowerCase());
        if(color != null)
            return color;
        else
            return Color.decode(colorString);
    }

    public static final Color black;
    public static final Color gray;
    public static final Color silver;
    public static final Color white;
    public static final Color maroon;
    public static final Color red;
    public static final Color purple;
    public static final Color fuchsia;
    public static final Color green;
    public static final Color lime;
    public static final Color olive;
    public static final Color yellow;
    public static final Color navy;
    public static final Color blue;
    public static final Color teal;
    public static final Color aqua;
    public static final Map htmlColorNames;

    static 
    {
        black = Color.decode("#000000");
        gray = Color.decode("#808080");
        silver = Color.decode("#C0C0C0");
        white = Color.decode("#FFFFFF");
        maroon = Color.decode("#800000");
        red = Color.decode("#FF0000");
        purple = Color.decode("#800080");
        fuchsia = Color.decode("#FF00FF");
        green = Color.decode("#008000");
        lime = Color.decode("#00FF00");
        olive = Color.decode("#808000");
        yellow = Color.decode("#FFFF00");
        navy = Color.decode("#000080");
        blue = Color.decode("#0000FF");
        teal = Color.decode("#008080");
        aqua = Color.decode("#00FFFF");
        Map names = new HashMap(16);
        names.put("black", black);
        names.put("gray", gray);
        names.put("silver", silver);
        names.put("white", white);
        names.put("maroon", maroon);
        names.put("red", red);
        names.put("purple", purple);
        names.put("fuchsia", fuchsia);
        names.put("green", green);
        names.put("lime", lime);
        names.put("olive", olive);
        names.put("yellow", yellow);
        names.put("navy", navy);
        names.put("blue", blue);
        names.put("teal", teal);
        names.put("aqua", aqua);
        htmlColorNames = Collections.unmodifiableMap(names);
    }
}
