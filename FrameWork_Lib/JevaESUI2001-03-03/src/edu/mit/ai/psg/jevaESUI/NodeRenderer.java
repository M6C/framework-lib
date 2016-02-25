// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/NodeRenderer.java

package edu.mit.ai.psg.jevaESUI;

import edu.mit.ai.psg.jevaES.IJevaNode;
import edu.mit.ai.psg.jevaES.Node;
import edu.mit.ai.psg.jexa.ObjectRenderer;
import edu.mit.ai.psg.strings.Prettify;
import edu.mit.ai.psg.ui.outliner.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Vector;

// Referenced classes of package edu.mit.ai.psg.jevaESUI:
//            JevaESUI

public class NodeRenderer extends ObjectRenderer
{

    public NodeRenderer(OutlineMaker outlineMaker)
    {
        super(outlineMaker);
    }

    public List makeChildOutlineNodes(OutlineNode outlineNode)
    {
        Node node = (Node)outlineNode.getObject();
        List childNodes = new Vector();
        childNodes.add(super.myOutlineMaker.makeOutlineNode("parent", node.jjtGetParent()));
        for(int i = 0; i < node.jjtGetNumChildren(); i++)
        {
            Object child = node.jjtGetChild(i);
            childNodes.add(super.myOutlineMaker.makeOutlineNode("child[" + i + "]", child));
        }

        if(node instanceof IJevaNode)
        {
            childNodes.add(super.myOutlineMaker.makeOutlineNode("beginToken", ((IJevaNode)node).getBeginToken()));
            childNodes.add(super.myOutlineMaker.makeOutlineNode("endToken", ((IJevaNode)node).getEndToken()));
        }
        Class nodeClass = node.getClass();
        try
        {
            Field fields[] = nodeClass.getFields();
            for(int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                if(!Modifier.isStatic(field.getModifiers()))
                    childNodes.add(super.myOutlineMaker.makeOutlineNode(field.getName(), field.get(node)));
            }

        }
        catch(SecurityException e)
        {
            addErrorNode(childNodes, e);
        }
        catch(IllegalAccessException e)
        {
            addErrorNode(childNodes, e);
        }
        catch(IllegalArgumentException e)
        {
            addErrorNode(childNodes, e);
        }
        return childNodes;
    }

    private void addErrorNode(List childNodes, Throwable t)
    {
        childNodes.add(makeNoteOutlineNode(JevaESUI.formatWithJevaESUIBundleString("ExceptionReflectingOnFieldsFormat", t)));
    }

    public String computeObjectText(OutlineNode outlineNode)
    {
        Object object = outlineNode.getObject();
        return Prettify.string(0, object.toString() + ((object instanceof IJevaNode) ? "(" + ((IJevaNode)object).printToString(false) + ")" : ""), 80, 3, 10, true, Prettify.defaultSyntax);
    }

    public static void initialize(OutlineMaker outlineMaker)
    {
        outlineMaker.addRenderer(edu.mit.ai.psg.jevaES.Node.class, new NodeRenderer(outlineMaker));
    }

    static final String rkExceptionReflectingOnFieldsFormat = "ExceptionReflectingOnFieldsFormat";
}
