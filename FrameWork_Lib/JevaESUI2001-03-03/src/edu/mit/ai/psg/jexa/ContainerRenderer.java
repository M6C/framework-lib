// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jexa/ContainerRenderer.java

package edu.mit.ai.psg.jexa;

import edu.mit.ai.psg.ui.outliner.OutlineMaker;
import edu.mit.ai.psg.ui.outliner.OutlineNode;
import edu.mit.ai.psg.ui.outliner.OutlineRendererDefault;
import java.awt.Component;
import java.awt.Container;
import java.util.Vector;

// Referenced classes of package edu.mit.ai.psg.jexa:
//            ObjectRenderer

public class ContainerRenderer extends ObjectRenderer
{

    public ContainerRenderer(OutlineMaker outlineMaker)
    {
        super(outlineMaker);
    }

    public java.util.List makeChildOutlineNodes(OutlineNode outlineNode)
    {
        Container container = (Container)outlineNode.getObject();
        java.util.List childNodes = new Vector();
        childNodes.add(super.myOutlineMaker.makeOutlineNode("parent", container.getParent()));
        childNodes.add(super.myOutlineMaker.makeOutlineNode("componentCount", new Integer(container.getComponentCount())));
        Component components[] = container.getComponents();
        for(int i = 0; i < components.length; i++)
            childNodes.add(super.myOutlineMaker.makeOutlineNode("component[" + i + "]", components[i]));

        return childNodes;
    }

    public static void initialize(OutlineMaker outlineMaker)
    {
        outlineMaker.addRenderer(java.awt.Container.class, new ContainerRenderer(outlineMaker));
    }
}
