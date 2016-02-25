// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jexa/Jexa.java

package edu.mit.ai.psg.jexa;

import edu.mit.ai.psg.strings.Stringify;
import edu.mit.ai.psg.ui.outliner.*;
import java.awt.Menu;
import java.awt.PopupMenu;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

// Referenced classes of package edu.mit.ai.psg.jexa:
//            ObjectRenderer, BeanRenderer, ClassRenderer, CollectionRenderer, 
//            ContainerRenderer, ListRenderer, MapRenderer, TreeNodeRenderer

public class Jexa extends Outliner
{
    public static class JexaOutlineMaker extends OutlineMakerDefault
    {

        public String makeFrameTitle(Object obj)
        {
            return "Jexa:" + Stringify.printToString(obj);
        }

        public void addPopupMenuItems(PopupMenu menu, final OutlineNode outlineNode, TreePath treePath, final JTree jTree)
        {
            super.addPopupMenuItems(menu, outlineNode, treePath, jTree);
            menu.addSeparator();
            final Object object = outlineNode.getObject();
            addHighlightPopupMenuItem(menu, Jexa.getJexaBundleString("Highlight appearances of (this)"), object, jTree);
            OutlineCommand.installCommand(menu, Jexa.getJexaBundleString("Set Jexa.mousedObject"), new OutlineCommand(jTree, "Jexa-setMousedObject") {

                protected void runCommand()
                {
                    Jexa.mousedObject = object;
                }

            }
);
            if(Jexa.mousedObject != null)
                addHighlightPopupMenuItem(menu, Jexa.getJexaBundleString("Highlight appearances of Jexa.mousedObject"), Jexa.mousedObject, jTree);
            if(Jexa.dbgLev > 0)
            {
                menu.addSeparator();
                OutlineCommand.installCommand(menu, Jexa.getJexaBundleString("Examine (outlineNode)"), new OutlineCommand(jTree, "Jexa-examineOutlineNode") {

                    protected void runCommand()
                    {
                        Jexa.examine(outlineNode);
                    }

                }
);
                OutlineCommand.installCommand(menu, Jexa.getJexaBundleString("Examine (jTree)"), new OutlineCommand(jTree, "Jexa-examineJTree") {

                    protected void runCommand()
                    {
                        Jexa.examine(jTree);
                    }

                }
);
            }
        }

        public static void addHighlightPopupMenuItem(PopupMenu menu, String itemString, final Object objectToHighlight, final JTree jTree)
        {
            final OutlineModel model = (OutlineModel)jTree.getModel();
            OutlineCommand.installCommand(menu, itemString, new OutlineCommand(jTree, "Jexa-Highlight") {

                protected void runCommand()
                {
                    invokeSwingAndWait(new Runnable() {

                        public void run()
                        {
                            findAndHighlightNodes((OutlineNode)model.getRoot());
                        }

                    }
);
                }

                private void findAndHighlightNodes(OutlineNode node)
                {
                    if(node.getObject() == objectToHighlight)
                        jTree.addSelectionPath(new TreePath(model.getPathToRoot(node)));
                    if(node.childrenAreComputed())
                    {
                        for(int i = 0; i < model.getChildCount(node); i++)
                            findAndHighlightNodes((OutlineNode)model.getChild(node, i));

                    }
                }



            }
);
        }

        public JexaOutlineMaker()
        {
            removeRenderer(java.lang.Object.class, getExactRenderer(java.lang.Object.class));
            removeRenderer(null, getExactRenderer(null));
            ObjectRenderer.initialize(this);
            BeanRenderer.initialize(this);
            addRenderer(null, getExactRenderer(java.lang.Object.class));
            ClassRenderer.initialize(this);
            CollectionRenderer.initialize(this);
            ContainerRenderer.initialize(this);
            ListRenderer.initialize(this);
            MapRenderer.initialize(this);
            TreeNodeRenderer.initialize(this);
        }
    }


    public Jexa()
    {
    }

    public static String getCopyright()
    {
        return "Copyright (c) 1996--1999 Massachusetts Institute of Technology.";
    }

    public static String getNoWarranty()
    {
        return "This software is provided ``as is'' without express or implied warranty.";
    }

    public static String getVersion()
    {
        return "prerelease version of 1999.10.11";
    }

    public static String getHomepage()
    {
        return "http://www.ai.mit.edu/people/caroma/tools/";
    }

    public static Object examine(Object obj)
    {
        Outliner.outline(obj);
        return obj;
    }

    public static void ensureInitialized()
    {
        if(!(Outliner.outlineMaker instanceof JexaOutlineMaker))
            Outliner.outlineMaker = new JexaOutlineMaker();
    }

    protected static String getJexaBundleString(String resourceKey)
    {
        return ResourceBundle.getBundle("edu.mit.ai.psg.jexa.JexaBundle").getString(resourceKey);
    }

    protected static String formatWithJexaBundleString(String resourceKey, Object obj)
    {
        return MessageFormat.format(getJexaBundleString(resourceKey), new Object[] {
            obj
        });
    }

    private static final String copyright = "Copyright (c) 1996--1999 Massachusetts Institute of Technology.";
    private static final String noWarranty = "This software is provided ``as is'' without express or implied warranty.";
    private static final String version = "prerelease version of 1999.10.11";
    private static final String homepage = "http://www.ai.mit.edu/people/caroma/tools/";
    public static Object mousedObject = null;
    public static int dbgLev = 0;
    protected static final String jexaResourceName = "edu.mit.ai.psg.jexa.JexaBundle";
    static final String rkHighlightAppearancesOfThis = "Highlight appearances of (this)";
    static final String rkSetJexaMousedObject = "Set Jexa.mousedObject";
    static final String rkHighlightAppearancesOfJexaMousedObject = "Highlight appearances of Jexa.mousedObject";
    static final String rkExamineOutlineNode = "Examine (outlineNode)";
    static final String rkExamineJTree = "Examine (jTree)";

    static 
    {
        ensureInitialized();
    }
}
