// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/CallOutlineNodeDefault.java

package edu.mit.ai.psg.ui.outliner;

import edu.mit.ai.psg.strings.Stringify;
import javax.swing.tree.DefaultMutableTreeNode;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineNodeDefault, CallOutlineNode, OutlineRenderer

public class CallOutlineNodeDefault extends OutlineNodeDefault
    implements CallOutlineNode
{

    public CallOutlineNodeDefault(Object obj, OutlineRenderer renderer)
    {
        super(obj, renderer);
        isInvocation = true;
        callPartner = null;
    }

    public CallOutlineNodeDefault(Object label, Object obj, OutlineRenderer renderer)
    {
        super(label, obj, renderer);
        isInvocation = true;
        callPartner = null;
    }

    public CallOutlineNodeDefault(Object obj, OutlineRenderer renderer, CallOutlineNode callPartner)
    {
        this(null, obj, renderer, callPartner);
    }

    public CallOutlineNodeDefault(Object label, Object obj, OutlineRenderer renderer, CallOutlineNode callPartner)
    {
        super(label, obj, renderer);
        isInvocation = true;
        this.callPartner = null;
        isInvocation = false;
        this.callPartner = callPartner;
        callPartner.setPartner(this);
        setIsLeaf(true);
        setChildrenAreComputed(true);
    }

    public boolean isInvocation()
    {
        return isInvocation;
    }

    public CallOutlineNode getPartner()
    {
        return callPartner;
    }

    public void setPartner(CallOutlineNode partner)
    {
        if(callPartner == null)
            callPartner = partner;
        else
            throw new Error("Already has partner.");
    }

    public String toString()
    {
        return getClass().getName() + "(" + (isInvocation ? "invoke: " : "result: ") + Stringify.printToString(getUserObject()) + ")";
    }

    boolean isInvocation;
    CallOutlineNode callPartner;
}
