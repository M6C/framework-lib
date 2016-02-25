// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/patches/FrameableApplet.java

package edu.mit.ai.psg.ui.patches;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

// Referenced classes of package edu.mit.ai.psg.ui.patches:
//            CloseableFrame, HTMLColor

public abstract class FrameableApplet extends Applet
{

    public FrameableApplet()
    {
    }

    protected abstract Dimension getMinSizeInPlace();

    protected abstract Dimension getMinSizeAsButton();

    public void init()
    {
        initAppletColors(this);
        Dimension size = getSize();
        Dimension inPlace = getMinSizeInPlace();
        if(size.width >= inPlace.width && size.height >= inPlace.height)
        {
            initPanelInPlaceApplet();
        } else
        {
            Dimension asButton = getMinSizeAsButton();
            if(size.width >= asButton.width && size.height >= asButton.height)
                initButtonForFrameApplet();
            else
                initFrameApplet();
        }
    }

    public String getAppletTitle()
    {
        String title = getParameter("title");
        return title != null ? title : "";
    }

    public Icon getTitleIcon()
    {
        return titleIcon;
    }

    public void initPanelInPlaceApplet()
    {
        if(!(getLayout() instanceof BorderLayout))
            setLayout(new BorderLayout());
        Panel titleBar = new Panel(new BorderLayout());
        titleBar.setBackground(SystemColor.activeCaption);
        JLabel label = new JLabel(getAppletTitle(), getTitleIcon(), 2);
        label.setFont(titleFont);
        label.setName("Title");
        label.setForeground(SystemColor.activeCaptionText);
        titleBar.add(label, "West");
        titleBar.add(makeButtonForFrame(), "East");
        add(titleBar, "North");
    }

    public void initButtonForFrameApplet()
    {
        add(makeButtonForFrame());
    }

    protected String getButtonStartText()
    {
        return "  Start Frame  ";
    }

    protected String getButtonStartingText()
    {
        return "Starting Frame ";
    }

    protected String getButtonStartedText()
    {
        return " Started Frame ";
    }

    public Button makeButtonForFrame()
    {
        final FrameableApplet applet = this;
        Button startButton = new Button(getButtonStartText());
        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                ((Button)e.getSource()).setLabel(applet.getButtonStartingText());
                applet.initFrameApplet();
                ((Button)e.getSource()).setLabel(applet.getButtonStartedText());
            }

        }
);
        return startButton;
    }

    public Frame initFrameApplet()
    {
        Frame window = new CloseableFrame(getAppletTitle());
        initAppletColors(window);
        return window;
    }

    protected void initAppletColors(Component component)
    {
        try
        {
            String textColorString = getParameter("textColor");
            if(textColorString != null)
                component.setForeground(HTMLColor.decode(textColorString));
        }
        catch(NumberFormatException numberformatexception) { }
        try
        {
            String bgColorString = getParameter("bgColor");
            if(bgColorString != null)
                component.setBackground(HTMLColor.decode(bgColorString));
        }
        catch(NumberFormatException numberformatexception1) { }
    }

    public String[][] getParameterInfo()
    {
        return (new String[][] {
            new String[] {
                "textColor", "HTML Color", "color of applet text"
            }, new String[] {
                "bgColor", "HTML Color", "color of background applet"
            }, new String[] {
                "title", "String", "window title"
            }
        });
    }

    public String getParameter(String name)
    {
        try
        {
            return super.getParameter(name);
        }
        catch(NullPointerException nullpointerexception)
        {
            return null;
        }
    }

    public java.util.List getAppletList()
    {
        Vector applets = new Vector();
        for(Enumeration enum = getAppletContext().getApplets(); enum.hasMoreElements(); applets.add(enum.nextElement()));
        return applets;
    }

    public static Font titleFont;
    static ImageIcon titleIcon = null;
    protected static final String pkTitle = "title";
    protected static final String pkTextColor = "textColor";
    protected static final String pkBgColor = "bgColor";

    static 
    {
        Map attributes = new HashMap(1);
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        titleFont = Font.getFont(attributes);
        java.net.URL iconURL = ClassLoader.getSystemResource("com/sun/java/swing/plaf/windows/icons/JavaCup.gif");
        titleIcon = iconURL != null ? new ImageIcon(iconURL) : null;
    }
}
