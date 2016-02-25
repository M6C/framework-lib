// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jexa/CollectionRenderer.java

package edu.mit.ai.psg.jexa;

import edu.mit.ai.psg.ui.outliner.*;
import java.util.*;

// Referenced classes of package edu.mit.ai.psg.jexa:
//            ObjectRenderer

public class CollectionRenderer extends ObjectRenderer
{

    public CollectionRenderer(OutlineMaker outlineMaker)
    {
        super(outlineMaker);
    }

    public List makeChildOutlineNodes(OutlineNode outlineNode)
    {
        Collection collection = (Collection)outlineNode.getObject();
        List childNodes = new Vector();
        childNodes.add(super.myOutlineMaker.makeOutlineNode("size", new Integer(collection.size())));
        for(Iterator iter = collection.iterator(); iter.hasNext(); childNodes.add(super.myOutlineMaker.makeOutlineNode(null, iter.next())));
        return childNodes;
    }

    public static void initialize(OutlineMaker outlineMaker)
    {
        outlineMaker.addRenderer(java.util.Collection.class, new CollectionRenderer(outlineMaker));
    }
}
