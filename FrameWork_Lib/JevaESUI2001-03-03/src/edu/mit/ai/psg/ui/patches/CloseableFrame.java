// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/patches/CloseableFrame.java

package edu.mit.ai.psg.ui.patches;

import java.awt.Frame;
import java.awt.Window;

// Referenced classes of package edu.mit.ai.psg.ui.patches:
//            CloseOnWindowClosingListener

public class CloseableFrame extends Frame
{

    public CloseableFrame()
    {
    }

    public CloseableFrame(String title)
    {
        super(title);
        addWindowListener(CloseOnWindowClosingListener.instance);
    }
}
