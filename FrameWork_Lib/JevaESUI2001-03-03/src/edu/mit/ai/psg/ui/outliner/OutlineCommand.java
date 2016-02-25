// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/OutlineCommand.java

package edu.mit.ai.psg.ui.outliner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

public abstract class OutlineCommand
    implements ActionListener, Runnable
{

    public static void installCommand(Menu menu, String menuItemString, ActionListener outlineCommand)
    {
        if("".equals(menuItemString.trim()))
        {
            System.err.println("Warning: empty menu item string for " + outlineCommand);
            Thread.dumpStack();
        }
        MenuItem item = new MenuItem(menuItemString);
        item.addActionListener(outlineCommand);
        menu.add(item);
    }

    public static void installDisabledCommand(Menu menu, String menuItemString)
    {
        MenuItem item = new MenuItem(menuItemString);
        item.setEnabled(false);
        menu.add(item);
    }

    public OutlineCommand(JTree jTree, String threadName)
    {
        myJTree = jTree;
        myThreadName = threadName;
    }

    public void actionPerformed(ActionEvent event)
    {
        (new Thread(this, myThreadName)).start();
    }

    public void run()
    {
        Cursor oldJTreeCursor = myJTree.getCursor();
        try
        {
            myJTree.setCursor(waitCursor);
            runCommand();
        }
        finally
        {
            myJTree.setCursor(oldJTreeCursor);
        }
    }

    protected abstract void runCommand();

    protected void invokeSwingAndWait(Runnable doUpdate)
    {
        try
        {
            SwingUtilities.invokeAndWait(doUpdate);
        }
        catch(InterruptedException interruptedexception) { }
        catch(InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    protected final JTree myJTree;
    protected final String myThreadName;
    public static Cursor waitCursor = Cursor.getPredefinedCursor(3);

}
