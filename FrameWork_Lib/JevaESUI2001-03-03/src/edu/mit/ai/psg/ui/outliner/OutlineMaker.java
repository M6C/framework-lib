// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/OutlineMaker.java

package edu.mit.ai.psg.ui.outliner;

import java.awt.Container;
import java.awt.PopupMenu;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineNode, OutlineModel, OutlineRenderer

public interface OutlineMaker
{

    public abstract JFrame outline(Object obj);

    public abstract JFrame makeFrame(Object obj);

    public abstract JFrame makeFrame(String s, OutlineNode outlinenode);

    public abstract String makeFrameTitle(Object obj);

    public abstract JTree getFrameJTree(JFrame jframe);

    public abstract void setFrameJTree(JFrame jframe, JTree jtree);

    public abstract void initializePane(Container container, OutlineNode outlinenode);

    public abstract JTree getPaneJTree(Container container);

    public abstract void setPaneJTree(Container container, JTree jtree);

    public abstract JTree makeOutlineJTree(OutlineNode outlinenode);

    public abstract OutlineModel makeOutlineModel(OutlineNode outlinenode);

    public abstract OutlineNode makeOutlineNode(Object obj);

    public abstract OutlineNode makeOutlineNode(Object obj, Object obj1);

    public abstract OutlineRenderer getMostSpecificRenderer(Object obj);

    public abstract Class getMostSpecificInterfaceWithRenderers(Object obj);

    public abstract java.util.List getAllRenderers(Object obj);

    public abstract java.util.List getAllInterfacesWithRenderers(Object obj);

    public abstract OutlineRenderer getExactRenderer(Class class1);

    public abstract java.util.List getExactRenderers(Class class1);

    public abstract void addRenderer(Class class1, OutlineRenderer outlinerenderer);

    public abstract boolean removeRenderer(Class class1, OutlineRenderer outlinerenderer);

    public abstract OutlineRenderer getNoteRenderer();

    public abstract void addPopupMenuItems(PopupMenu popupmenu, OutlineNode outlinenode, TreePath treepath, JTree jtree);
}
