// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/JevaESGUIPanel.java

package edu.mit.ai.psg.jevaESUI;

import edu.mit.ai.psg.jevaES.IEnv;
import java.awt.*;

// Referenced classes of package edu.mit.ai.psg.jevaESUI:
//            JevaESGUI, JevaESGUIListener

public class JevaESGUIPanel extends Panel
{

    public JevaESGUIPanel(int options, String title, String initialMessage, Class returnClass, Class throwsClasses[], IEnv env, String initStatements)
    {
        super(new BorderLayout());
        listener = null;
        setSize(400, 480);
        JevaESGUI.setup(this, options, title, initialMessage, returnClass, throwsClasses, env, initStatements);
    }

    public JevaESGUIPanel()
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
}
