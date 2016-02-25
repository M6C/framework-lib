// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/OutlineRendererDefault.java

package edu.mit.ai.psg.ui.outliner;

import edu.mit.ai.psg.ui.patches.FlowLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.util.Collections;
import java.util.MissingResourceException;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreePath;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineNodeDefault, OutlineNode, OutlineRenderer, Outliner, 
//            OutlineMaker, OutlineModel

public class OutlineRendererDefault extends JComponent
    implements OutlineRenderer
{
    public static class OutlineText extends JTextArea
    {

        public float getAlignmentY()
        {
            return 0.0F;
        }

        public void paint(Graphics g)
        {
            super.paint(g);
            if(OutlineRendererDefault.dbgLev > 0)
            {
                Dimension dim = getSize();
                g.setColor(getForeground());
                g.drawRect(0, 0, dim.width - 1, dim.height - 1);
            }
        }

        public OutlineText()
        {
        }

        public OutlineText(String text)
        {
            super(text);
        }
    }


    public Component getLabelComponent()
    {
        return labelComponent;
    }

    public Component getObjectComponent()
    {
        return objectComponent;
    }

    public OutlineMaker getOutlineMaker()
    {
        return myOutlineMaker;
    }

    public OutlineRendererDefault(OutlineMaker outlineMaker)
    {
        labelComponent = new OutlineText();
        objectComponent = new OutlineText();
        myOutlineMaker = null;
        defaultToolTipText = Outliner.getOutlinerBundleString("mouseRMenu");
        if(outlineMaker == null)
        {
            throw new NullPointerException("outlineMaker");
        } else
        {
            myOutlineMaker = outlineMaker;
            setLayout(new FlowLayout(0, 0, 0));
            add(labelComponent);
            add(objectComponent);
            return;
        }
    }

    public OutlineNode makeOutlineNode(Object object)
    {
        return makeOutlineNode(null, object);
    }

    public OutlineNode makeOutlineNode(Object label, Object object)
    {
        return new OutlineNodeDefault(label, object, this);
    }

    public void notifyInserted(OutlineModel outlinemodel, OutlineNode outlinenode, int i)
    {
    }

    public void notifyRemoving(OutlineModel outlinemodel, OutlineNode outlinenode)
    {
    }

    public OutlineNode makeNoteOutlineNode(String note)
    {
        OutlineRenderer noteRenderer = myOutlineMaker.getNoteRenderer();
        OutlineNode outlineNode = noteRenderer.makeOutlineNode(null, note);
        outlineNode.setIsLeaf(true);
        outlineNode.setChildrenAreComputed(true);
        return outlineNode;
    }

    public java.util.List makeChildOutlineNodes(OutlineNode outlineNode)
    {
        return Collections.EMPTY_LIST;
    }

    public void addPopupMenuTitle(PopupMenu menu, OutlineNode outlineNode, TreePath treePath, JTree jTree)
    {
        menu.add(new MenuItem(getObjectText(outlineNode)));
        menu.add(new MenuItem(" " + Outliner.formatWithOutlinerBundleString("byRendererFormat", this)));
    }

    public void addPopupMenuItems(PopupMenu popupmenu, OutlineNode outlinenode, TreePath treepath, JTree jtree)
    {
    }

    public String toString()
    {
        String className = getClass().getName();
        String shortName = className.substring(className.lastIndexOf('.') + 1);
        try
        {
            return Outliner.getOutlinerBundleString(shortName);
        }
        catch(MissingResourceException missingresourceexception)
        {
            return shortName;
        }
    }

    public boolean equals(Object other)
    {
        return other != null && getClass() == other.getClass() && myOutlineMaker == ((OutlineRendererDefault)other).myOutlineMaker;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean isExpanded, boolean isLeaf, int row, boolean hasFocus)
    {
        OutlineNode outlineNode = (OutlineNode)value;
        JTextArea labelTextArea = (JTextArea)labelComponent;
        labelTextArea.setText(getLabelText(outlineNode));
        JTextArea objectTextArea = (JTextArea)objectComponent;
        objectTextArea.setText(getObjectText(outlineNode));
        if(isSelected)
            objectTextArea.selectAll();
        else
            objectTextArea.select(0, 0);
        invalidate();
        setToolTipText(getToolTipText(outlineNode));
        return this;
    }

    public boolean isShowing()
    {
        return true;
    }

    public String computeLabelText(OutlineNode node)
    {
        Object label = node.getLabel();
        return label != null ? label.toString() + ":  " : "";
    }

    public String computeObjectText(OutlineNode node)
    {
        Object object = node.getObject();
        return object != null ? object.toString() : "null";
    }

    public String computeToolTipText(OutlineNode node)
    {
        return defaultToolTipText;
    }

    public String getLabelText(OutlineNode node)
    {
        return computeLabelText(node);
    }

    public String getObjectText(OutlineNode node)
    {
        return computeObjectText(node);
    }

    public String getToolTipText(OutlineNode node)
    {
        return computeToolTipText(node);
    }

    /**
     * @deprecated Method computeLabelText is deprecated
     */

    public String computeLabelText(Object label)
    {
        return "deprecated";
    }

    /**
     * @deprecated Method computeObjectText is deprecated
     */

    public String computeObjectText(Object object)
    {
        return "deprecated";
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if(dbgLev > 0)
        {
            Dimension dim = getSize();
            g.setColor(getForeground());
            g.drawRect(0, 0, dim.width - 1, dim.height - 1);
        }
    }

    protected Component labelComponent;
    protected Component objectComponent;
    protected OutlineMaker myOutlineMaker;
    static final String rkByRendererFormat = "byRendererFormat";
    static final String rkMouseRMenu = "mouseRMenu";
    public String defaultToolTipText;
    static int dbgLev = 0;

}
