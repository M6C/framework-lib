// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/OutlineMouseListener.java

package edu.mit.ai.psg.ui.outliner;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineNode, OutlineRenderer, OutlineMaker

public class OutlineMouseListener extends MouseAdapter
    implements MouseListener
{

    public OutlineMouseListener(JTree jTree)
    {
        eventIsPopupTriggerIsImplemented = true;
        myJTree = jTree;
    }

    public void mouseClicked(MouseEvent event)
    {
        if(dbgLev > 0)
            dbgOut("OutlineMouseListener received event " + event);
        if(isPopupTrigger(event))
            popup(event);
        else
        if(0 != (event.getModifiers() & 0x10))
        {
            TreePath treePath = myJTree.getPathForLocation(event.getX(), event.getY());
            if(treePath == null)
                myJTree.setSelectionPath(null);
        }
    }

    public void mouseReleased(MouseEvent event)
    {
        if(dbgLev > 0)
            dbgOut("OutlineMouseListener received event " + event);
        if(isPopupTrigger(event))
            popup(event);
    }

    public void mousePressed(MouseEvent event)
    {
        if(dbgLev > 0)
            dbgOut("OutlineMouseListener received event " + event);
        if(isPopupTrigger(event))
            popup(event);
    }

    public boolean isPopupTrigger(MouseEvent event)
    {
        eventIsPopupTriggerIsImplemented |= event.isPopupTrigger();
        return event.isPopupTrigger() || !eventIsPopupTriggerIsImplemented && event.getID() == 501 && 0 != (event.getModifiers() & 0xf);
    }

    public void popup(final MouseEvent event)
    {
        if(dbgLev > 0)
            dbgOut("  Popup triggered.");
        final TreePath treePath = myJTree.getPathForLocation(event.getX(), event.getY());
        if(treePath != null && (treePath.getLastPathComponent() instanceof OutlineNode))
        {
            myJTree.setSelectionPath(treePath);
            (new Thread("Outline popup") {

                public void run()
                {
                    OutlineNode outlineNode = (OutlineNode)treePath.getLastPathComponent();
                    PopupMenu menu = new PopupMenu();
                    OutlineRenderer renderer = outlineNode.getRenderer();
                    renderer.addPopupMenuTitle(menu, outlineNode, treePath, myJTree);
                    renderer.addPopupMenuItems(menu, outlineNode, treePath, myJTree);
                    renderer.getOutlineMaker().addPopupMenuItems(menu, outlineNode, treePath, myJTree);
                    myJTree.getParent().add(menu);
                    menu.show(myJTree, event.getX(), event.getY());
                }

            }
).start();
        }
    }

    public static void dbgOut(String s)
    {
        System.err.println(s);
    }

    public boolean eventIsPopupTriggerIsImplemented;
    final JTree myJTree;
    public static int dbgLev = 0;

}
