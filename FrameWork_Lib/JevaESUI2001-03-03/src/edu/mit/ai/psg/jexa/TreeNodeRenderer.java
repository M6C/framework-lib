// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jexa/TreeNodeRenderer.java

package edu.mit.ai.psg.jexa;

import edu.mit.ai.psg.ui.outliner.*;
import java.util.List;
import java.util.Vector;
import javax.swing.tree.TreeNode;

// Referenced classes of package edu.mit.ai.psg.jexa:
//            ObjectRenderer

public class TreeNodeRenderer extends ObjectRenderer
{

    public TreeNodeRenderer(OutlineMaker outlineMaker)
    {
        super(outlineMaker);
    }

    public List makeChildOutlineNodes(OutlineNode outlineNode)
    {
        TreeNode treeNode = (TreeNode)outlineNode.getObject();
        List childNodes = new Vector();
        childNodes.add(super.myOutlineMaker.makeOutlineNode("parent", treeNode.getParent()));
        childNodes.add(super.myOutlineMaker.makeOutlineNode("childCount", new Integer(treeNode.getChildCount())));
        for(int i = 0; i < treeNode.getChildCount(); i++)
            childNodes.add(super.myOutlineMaker.makeOutlineNode("child[" + i + "]", treeNode.getChildAt(i)));

        return childNodes;
    }

    public static void initialize(OutlineMaker outlineMaker)
    {
        outlineMaker.addRenderer(javax.swing.tree.TreeNode.class, new TreeNodeRenderer(outlineMaker));
    }
}
