// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/OutlineNodeDefault.java

package edu.mit.ai.psg.ui.outliner;

import edu.mit.ai.psg.strings.Stringify;
import javax.swing.tree.DefaultMutableTreeNode;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineNode, OutlineRenderer

public class OutlineNodeDefault extends DefaultMutableTreeNode
    implements OutlineNode
{

    public OutlineNodeDefault(Object obj, OutlineRenderer renderer)
    {
        this(null, obj, renderer);
    }

    public OutlineNodeDefault(Object label, Object obj, OutlineRenderer renderer)
    {
        super(obj);
        myLabel = null;
        myChildrenAreComputed = false;
        isLeaf = false;
        myRenderer = renderer;
        myLabel = label;
    }

    public Object getObject()
    {
        return getUserObject();
    }

    public Object getLabel()
    {
        return myLabel;
    }

    public OutlineRenderer getRenderer()
    {
        return myRenderer;
    }

    public boolean childrenAreComputed()
    {
        return myChildrenAreComputed;
    }

    public void setChildrenAreComputed(boolean v)
    {
        myChildrenAreComputed = v;
    }

    public boolean isLeaf()
    {
        return isLeaf;
    }

    public void setIsLeaf(boolean v)
    {
        isLeaf = v;
    }

    public void add(OutlineNode newChild)
    {
        super.add(newChild);
    }

    public String toString()
    {
        return getClass().getName() + "(" + Stringify.printToString(getUserObject()) + ")";
    }

    OutlineRenderer myRenderer;
    Object myLabel;
    boolean myChildrenAreComputed;
    boolean isLeaf;
}
