// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jexa/MapRenderer.java

package edu.mit.ai.psg.jexa;

import edu.mit.ai.psg.ui.outliner.*;
import java.util.*;

// Referenced classes of package edu.mit.ai.psg.jexa:
//            ObjectRenderer

public class MapRenderer extends ObjectRenderer
{

    public MapRenderer(OutlineMaker outlineMaker)
    {
        super(outlineMaker);
    }

    public List makeChildOutlineNodes(OutlineNode outlineNode)
    {
        Map map = (Map)outlineNode.getObject();
        List childNodes = new Vector();
        childNodes.add(super.myOutlineMaker.makeOutlineNode("Map size", new Integer(map.size())));
        Iterator keyIter;
        if(map instanceof SortedMap)
            keyIter = map.keySet().iterator();
        else
            try
            {
                keyIter = (new TreeSet(map.keySet())).iterator();
            }
            catch(ClassCastException classcastexception)
            {
                keyIter = map.keySet().iterator();
            }
        Object key;
        Object val;
        for(; keyIter.hasNext(); childNodes.add(super.myOutlineMaker.makeOutlineNode(key, val)))
        {
            key = keyIter.next();
            val = map.get(key);
        }

        return childNodes;
    }

    public static void initialize(OutlineMaker outlineMaker)
    {
        outlineMaker.addRenderer(java.util.Map.class, new MapRenderer(outlineMaker));
    }
}
