// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/OutlineNode.java

package edu.mit.ai.psg.ui.outliner;

import javax.swing.tree.MutableTreeNode;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineRenderer

public interface OutlineNode
    extends MutableTreeNode
{

    public abstract Object getObject();

    public abstract Object getLabel();

    public abstract OutlineRenderer getRenderer();

    public abstract boolean childrenAreComputed();

    public abstract void setChildrenAreComputed(boolean flag);

    public abstract void setIsLeaf(boolean flag);

    public abstract void add(OutlineNode outlinenode);
}
