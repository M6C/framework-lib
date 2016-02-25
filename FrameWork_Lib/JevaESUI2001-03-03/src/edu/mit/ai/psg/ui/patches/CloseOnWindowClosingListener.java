// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/patches/CloseableFrame.java

package edu.mit.ai.psg.ui.patches;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Referenced classes of package edu.mit.ai.psg.ui.patches:
//            CloseableFrame

class CloseOnWindowClosingListener extends WindowAdapter
{

    public void windowClosing(WindowEvent e)
    {
        ((CloseableFrame)e.getWindow()).setVisible(false);
    }

    protected CloseOnWindowClosingListener()
    {
    }

    public static final CloseOnWindowClosingListener instance = new CloseOnWindowClosingListener();

}
