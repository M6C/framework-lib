// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/JevaESGUI.java

package edu.mit.ai.psg.jevaESUI;

import java.awt.TextArea;
import java.io.OutputStream;

class TextAreaOutputStream extends OutputStream
{

    TextAreaOutputStream(TextArea outputTextArea)
    {
        outTextArea = outputTextArea;
    }

    public void write(int b)
    {
        byte buf[] = new byte[1];
        buf[0] = (byte)b;
        outTextArea.append(new String(buf));
    }

    public void write(byte b[])
    {
        outTextArea.append(new String(b));
    }

    public void write(byte b[], int off, int len)
    {
        outTextArea.append((new String(b)).substring(off, len));
    }

    TextArea outTextArea;
}
