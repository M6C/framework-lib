// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/CallOutlineNode.java

package edu.mit.ai.psg.ui.outliner;


// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineNode

public interface CallOutlineNode
    extends OutlineNode
{

    public abstract boolean isInvocation();

    public abstract CallOutlineNode getPartner();

    public abstract void setPartner(CallOutlineNode calloutlinenode);
}
