// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/JevaESGUIListener.java

package edu.mit.ai.psg.jevaESUI;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class WaitUntilWindowClosedListener extends WindowAdapter
{

    protected WaitUntilWindowClosedListener(Window w)
    {
        closed = false;
        window = w;
        window.addWindowListener(this);
    }

    protected synchronized void waitUntilClosed()
    {
        if(!closed)
            try
            {
                wait();
            }
            catch(InterruptedException interruptedexception) { }
    }

    public void windowClosing(WindowEvent e)
    {
        windowClosing();
    }

    protected synchronized void windowClosing()
    {
        closed = true;
        notify();
        window.removeWindowListener(this);
    }

    boolean closed;
    final Window window;
}
