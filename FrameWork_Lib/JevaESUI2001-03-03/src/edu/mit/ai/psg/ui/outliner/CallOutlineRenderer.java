// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/CallOutlineRenderer.java

package edu.mit.ai.psg.ui.outliner;


// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineRenderer, CallOutlineNode

public interface CallOutlineRenderer
    extends OutlineRenderer
{

    public abstract void generatePartner(CallOutlineNode calloutlinenode);
}
