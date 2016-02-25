// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jexa/ClassRenderer.java

package edu.mit.ai.psg.jexa;

import edu.mit.ai.psg.ui.outliner.*;
import java.util.*;

// Referenced classes of package edu.mit.ai.psg.jexa:
//            ObjectRenderer, Jexa

public class ClassRenderer extends ObjectRenderer
{

    public ClassRenderer(OutlineMaker outlineMaker)
    {
        super(outlineMaker);
    }

    public List makeChildOutlineNodes(OutlineNode outlineNode)
    {
        Object object = outlineNode.getObject();
        Vector childNodes = new Vector();
        if(object != null)
        {
            Class myClass = (Class)object;
            addFieldOutlineNodes(childNodes, null, myClass, true);
        }
        return childNodes;
    }

    private void addErrorNode(List childNodes, Throwable t)
    {
        ResourceBundle resources = ResourceBundle.getBundle("edu.mit.ai.psg.jexa.JexaBundle");
        childNodes.add(makeNoteOutlineNode(Jexa.formatWithJexaBundleString("ExceptionReflectingOnStaticFieldsFormat", t)));
    }

    public static void initialize(OutlineMaker outlineMaker)
    {
        outlineMaker.addRenderer(java.lang.Class.class, new ClassRenderer(outlineMaker));
    }

    static final String rkExceptionReflectingOnStaticFieldsFormat = "ExceptionReflectingOnStaticFieldsFormat";
}
