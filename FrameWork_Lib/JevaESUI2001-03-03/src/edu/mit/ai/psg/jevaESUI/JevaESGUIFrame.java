// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/JevaESGUIFrame.java

package edu.mit.ai.psg.jevaESUI;

import edu.mit.ai.psg.jevaES.IEnv;
import edu.mit.ai.psg.ui.patches.CloseableFrame;
import java.awt.Component;
import java.awt.Rectangle;

// Referenced classes of package edu.mit.ai.psg.jevaESUI:
//            JevaESUI, JevaESGUI, JevaESGUIListener

public class JevaESGUIFrame extends CloseableFrame
{

    public JevaESGUIFrame(int options, String title, String initialMessage, Class returnClass, Class throwsClasses[], IEnv env, String initStatements)
    {
        super(title == null ? JevaESUI.defaultTitle() : title);
        listener = null;
        setBounds(initialBounds);
        JevaESGUI.setup(this, options, title, initialMessage, returnClass, throwsClasses, env, initStatements);
    }

    public JevaESGUIFrame()
    {
        this(0, null, null, null, null, null, null);
    }

    void initializeListener(JevaESGUIListener initListener)
    {
        if(listener == null)
            listener = initListener;
        else
            throw new IllegalStateException("already has JevaESGUIListener");
    }

    Object awaitResult()
        throws Throwable
    {
        return listener.awaitResult();
    }

    protected JevaESGUIListener listener;
    public static Rectangle initialBounds = new Rectangle(60, 28, 580, 420);

}
