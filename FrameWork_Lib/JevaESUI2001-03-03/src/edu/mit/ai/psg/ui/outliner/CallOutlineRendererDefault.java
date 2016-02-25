// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/CallOutlineRendererDefault.java

package edu.mit.ai.psg.ui.outliner;

import javax.swing.tree.TreeNode;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineRendererDefault, CallOutlineNodeDefault, CallOutlineNode, OutlineNode, 
//            CallOutlineRenderer, OutlineModel, OutlineMaker

public class CallOutlineRendererDefault extends OutlineRendererDefault
    implements CallOutlineRenderer
{

    public CallOutlineRendererDefault(OutlineMaker outlineMaker)
    {
        super(outlineMaker);
    }

    public OutlineNode makeOutlineNode(Object label, Object obj)
    {
        CallOutlineNode invocationNode = new CallOutlineNodeDefault(label, obj, this);
        generatePartner(invocationNode);
        return invocationNode;
    }

    public void generatePartner(CallOutlineNode invocationNode)
    {
        new CallOutlineNodeDefault(invocationNode.getLabel(), invocationNode.getObject(), this, invocationNode);
    }

    public void notifyInserted(OutlineModel model, OutlineNode child, int idx)
    {
        super.notifyInserted(model, child, idx);
        CallOutlineNode callOutlineNode = (CallOutlineNode)child;
        if(callOutlineNode.isInvocation())
        {
            CallOutlineNode partner = callOutlineNode.getPartner();
            if(partner != null)
            {
                OutlineNode parent = (OutlineNode)callOutlineNode.getParent();
                if(partner.getParent() != parent)
                    model.insertNodeInto(partner, parent, parent.getIndex(callOutlineNode) + 1);
            }
        }
    }

    public void notifyRemoving(OutlineModel model, OutlineNode child)
    {
        super.notifyRemoving(model, child);
        Object firstNotified = firstNotifiedRemoving.get();
        if(firstNotified == null)
        {
            firstNotifiedRemoving.set(child);
            try
            {
                CallOutlineNode callOutlineNode = (CallOutlineNode)child;
                CallOutlineNode partner = callOutlineNode.getPartner();
                if(partner != null)
                    if(partner.getRenderer() instanceof CallOutlineRendererDefault)
                        model.removeNodeFromParent(partner);
                    else
                        throw new IllegalStateException("partner renderer " + partner.getRenderer() + " is not instanceof CallOutlineRendererDefault");
            }
            finally
            {
                firstNotifiedRemoving.set(null);
            }
        }
    }

    private static ThreadLocal firstNotifiedRemoving = new ThreadLocal();

}
