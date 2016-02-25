// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/OutlineRenderer.java

package edu.mit.ai.psg.ui.outliner;

import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineNode, OutlineModel, OutlineMaker

public interface OutlineRenderer
    extends TreeCellRenderer
{

    public abstract OutlineNode makeOutlineNode(Object obj, Object obj1);

    public abstract OutlineNode makeOutlineNode(Object obj);

    public abstract void notifyInserted(OutlineModel outlinemodel, OutlineNode outlinenode, int i);

    public abstract void notifyRemoving(OutlineModel outlinemodel, OutlineNode outlinenode);

    public abstract OutlineNode makeNoteOutlineNode(String s);

    public abstract List makeChildOutlineNodes(OutlineNode outlinenode);

    public abstract void addPopupMenuTitle(java.awt.PopupMenu popupmenu, OutlineNode outlinenode, TreePath treepath, JTree jtree);

    public abstract void addPopupMenuItems(java.awt.PopupMenu popupmenu, OutlineNode outlinenode, TreePath treepath, JTree jtree);

    public abstract OutlineMaker getOutlineMaker();
}
