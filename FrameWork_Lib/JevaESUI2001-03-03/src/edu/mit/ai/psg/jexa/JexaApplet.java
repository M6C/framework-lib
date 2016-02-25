// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jexa/JexaApplet.java

package edu.mit.ai.psg.jexa;

import edu.mit.ai.psg.ui.outliner.OutlineMaker;
import edu.mit.ai.psg.ui.outliner.Outliner;
import edu.mit.ai.psg.ui.patches.FrameableApplet;
import java.awt.*;

// Referenced classes of package edu.mit.ai.psg.jexa:
//            Jexa

public class JexaApplet extends FrameableApplet
{

    public JexaApplet()
    {
    }

    public String getAppletInfo()
    {
        return "JexaApplet\nCopyright (c) 1996--1999 Massachusetts Institute of Technology\nThis software is provided ``as is'' without express or implied warranty.\nDocumentation available at http://www.ai.mit.edu/people/caroma/tools/";
    }

    public String getAppletTitle()
    {
        String title = getParameter("title");
        if(title == null)
            title = Jexa.getJexaBundleString("AppletsInPage");
        return "Jexa: " + title;
    }

    public Object getObjectToExamine()
    {
        return getAppletList();
    }

    protected Dimension getMinSizeInPlace()
    {
        return minSizeInPlace;
    }

    protected Dimension getMinSizeAsButton()
    {
        return minSizeAsButton;
    }

    public void initPanelInPlaceApplet()
    {
        super.initPanelInPlaceApplet();
        Panel jexaPanel = new Panel();
        jexaPanel.setName("JexaApplet outline");
        add(jexaPanel, "Center");
        Jexa.ensureInitialized();
        Outliner.outlineMaker.initializePane(jexaPanel, Outliner.makeOutlineNode(getObjectToExamine()));
    }

    protected String getButtonStartText()
    {
        return Jexa.getJexaBundleString("JexaWindowStart");
    }

    protected String getButtonStartingText()
    {
        return Jexa.getJexaBundleString("JexaWindowStarting");
    }

    protected String getButtonStartedText()
    {
        return Jexa.getJexaBundleString("JexaWindowStarted");
    }

    public Frame initFrameApplet()
    {
        Jexa.ensureInitialized();
        Frame window = Outliner.makeFrame(getAppletTitle(), Outliner.makeOutlineNode(getObjectToExamine()));
        initAppletColors(window);
        window.show();
        return window;
    }

    public String toString()
    {
        return "JexaApplet[" + getSize().width + "x" + getSize().height + ", " + getAppletTitle() + "]";
    }

    protected static Dimension minSizeInPlace = new Dimension(300, 150);
    protected static Dimension minSizeAsButton = new Dimension(200, 30);
    private static final String rkAppletsInPage = "AppletsInPage";
    private static final String rkJexaWindowStart = "JexaWindowStart";
    private static final String rkJexaWindowStarting = "JexaWindowStarting";
    private static final String rkJexaWindowStarted = "JexaWindowStarted";

}
