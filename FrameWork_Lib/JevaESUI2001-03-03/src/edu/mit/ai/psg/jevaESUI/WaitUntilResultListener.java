// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/JevaESGUIListener.java

package edu.mit.ai.psg.jevaESUI;

import java.awt.AWTException;
import java.awt.Window;

// Referenced classes of package edu.mit.ai.psg.jevaESUI:
//            WaitUntilWindowClosedListener

class WaitUntilResultListener extends WaitUntilWindowClosedListener
{

    synchronized void setResult(Object result)
    {
        this.result = result;
        resultReceived = true;
        windowClosing();
    }

    synchronized void setThrown(Throwable thrown)
    {
        this.thrown = thrown;
        resultReceived = true;
        windowClosing();
    }

    protected WaitUntilResultListener(Window window)
    {
        super(window);
        resultReceived = false;
        result = null;
        thrown = null;
    }

    protected synchronized Object waitUntilResult()
        throws AWTException, Throwable
    {
        super.waitUntilClosed();
        if(!resultReceived)
            throw new AWTException("Window closed");
        if(thrown != null)
            throw thrown;
        else
            return result;
    }

    boolean resultReceived;
    Object result;
    Throwable thrown;
}
