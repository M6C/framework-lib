// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jexa/ListRenderer.java

package edu.mit.ai.psg.jexa;

import edu.mit.ai.psg.ui.outliner.*;
import java.util.List;
import java.util.Vector;

// Referenced classes of package edu.mit.ai.psg.jexa:
//            ObjectRenderer

public class ListRenderer extends ObjectRenderer
{

    public ListRenderer(OutlineMaker outlineMaker)
    {
        super(outlineMaker);
    }

    public List makeChildOutlineNodes(OutlineNode outlineNode)
    {
        List list = (List)outlineNode.getObject();
        List childNodes = new Vector();
        childNodes.add(super.myOutlineMaker.makeOutlineNode("List size", new Integer(list.size())));
        for(int i = 0; i < list.size(); i++)
            childNodes.add(super.myOutlineMaker.makeOutlineNode("[" + i + "]", list.get(i)));

        return childNodes;
    }

    public static void initialize(OutlineMaker outlineMaker)
    {
        outlineMaker.addRenderer(java.util.List.class, new ListRenderer(outlineMaker));
    }
}
