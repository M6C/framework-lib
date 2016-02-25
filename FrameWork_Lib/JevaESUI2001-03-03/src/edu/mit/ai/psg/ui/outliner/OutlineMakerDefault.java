// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/OutlineMakerDefault.java

package edu.mit.ai.psg.ui.outliner;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.font.TextAttribute;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineRendererDefault, OutlineNode, OutlineModel, OutlineMouseListener, 
//            OutlineRenderer, OutlineMaker, Outliner, OutlineCommand

public class OutlineMakerDefault
    implements OutlineMaker
{

    public OutlineMakerDefault()
    {
        myRenderers = Collections.synchronizedMap(new HashMap());
        OutlineRenderer defaultRenderer = new OutlineRendererDefault(this);
        addRenderer(java.lang.Object.class, defaultRenderer);
        addRenderer(null, defaultRenderer);
        myNoteRenderer = new OutlineRendererDefault(this) {

            
            {
                super.labelComponent.setFont(OutlineMakerDefault.noteFont);
                super.objectComponent.setFont(OutlineMakerDefault.noteFont);
            }
        }
;
        initialBounds = new Rectangle(60, 28, 400, 300);
    }

    public JFrame outline(Object obj)
    {
        JFrame frame = makeFrame(obj);
        frame.show();
        return frame;
    }

    public JFrame makeFrame(Object obj)
    {
        return makeFrame(makeFrameTitle(obj), makeOutlineNode(obj));
    }

    public JFrame makeFrame(String frameLabel, OutlineNode displayRoot)
    {
        JFrame frame = new JFrame(frameLabel);
        frame.setBounds(initialBounds);
        Container contentPane = frame.getContentPane();
        initializePane(contentPane, displayRoot);
        return frame;
    }

    public String makeFrameTitle(Object obj)
    {
        return "O:" + obj;
    }

    public JTree getFrameJTree(JFrame frame)
    {
        return getPaneJTree(frame.getContentPane());
    }

    public void setFrameJTree(JFrame frame, JTree jTree)
    {
        setPaneJTree(frame.getContentPane(), jTree);
    }

    public JTree getPaneJTree(Container pane)
    {
        JScrollPane scrollPane = (JScrollPane)pane.getComponent(0);
        return (JTree)scrollPane.getViewport().getView();
    }

    public void setPaneJTree(Container pane, JTree jTree)
    {
        JScrollPane scrollPane = (JScrollPane)pane.getComponent(0);
        scrollPane.getViewport().setView(jTree);
    }

    public void initializePane(Container contentPane, OutlineNode displayRoot)
    {
        contentPane.removeAll();
        JTree treeViewer = makeOutlineJTree(displayRoot);
        contentPane.setLayout(new GridLayout(1, 1));
        synchronized(javax.swing.plaf.basic.BasicButtonListener.class)
        {
            contentPane.add(new JScrollPane(treeViewer));
        }
    }

    public JTree makeOutlineJTree(OutlineNode displayRoot)
    {
        OutlineModel model = makeOutlineModel(displayRoot);
        OutlineNode hiddenRoot = (OutlineNode)model.getRoot();
        JTree treeViewer = new JTree(model);
        treeViewer.expandPath(new TreePath(new Object[] {
            hiddenRoot, displayRoot
        }));
        treeViewer.setRootVisible(false);
        treeViewer.setShowsRootHandles(true);
        treeViewer.putClientProperty("JTree.lineStyle", "Angled");
        addTreeListeners(treeViewer);
        ToolTipManager.sharedInstance().registerComponent(treeViewer);
        treeViewer.setCellRenderer(new TreeCellRenderer() {

            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean isExpanded, boolean isLeaf, int row, boolean hasFocus)
            {
                return ((OutlineNode)value).getRenderer().getTreeCellRendererComponent(tree, value, isSelected, isExpanded, isLeaf, row, hasFocus);
            }

        }
);
        return treeViewer;
    }

    public OutlineModel makeOutlineModel(OutlineNode displayRoot)
    {
        return new OutlineModel(displayRoot, myNoteRenderer);
    }

    public OutlineNode makeOutlineNode(Object obj)
    {
        return makeOutlineNode(null, obj);
    }

    public OutlineNode makeOutlineNode(Object label, Object obj)
    {
        OutlineRenderer renderer = getMostSpecificRenderer(obj);
        OutlineNode outlineNode = renderer.makeOutlineNode(label, obj);
        return outlineNode;
    }

    public void addTreeListeners(JTree jTree)
    {
        jTree.addMouseListener(new OutlineMouseListener(jTree));
    }

    public OutlineRenderer getMostSpecificRenderer(Object obj)
    {
        return getExactRenderer(getMostSpecificInterfaceWithRenderers(obj));
    }

    public Class getMostSpecificInterfaceWithRenderers(Object obj)
    {
        if(obj != null)
        {
            for(Class objClass = obj.getClass(); objClass != null; objClass = objClass.getSuperclass())
            {
                if(null != getExactRenderer(objClass))
                    return objClass;
                Stack interfacesStack = new Stack();
                interfacesStack.addAll(reverse(Arrays.asList(objClass.getInterfaces())));
                Class anInterface;
                for(; !interfacesStack.isEmpty(); interfacesStack.addAll(reverse(Arrays.asList(anInterface.getInterfaces()))))
                {
                    anInterface = (Class)interfacesStack.pop();
                    if(null != getExactRenderer(anInterface))
                        return anInterface;
                }

            }

        } else
        if(null != getExactRenderer(null))
            return null;
        throw new NoSuchElementException(obj.getClass().getName());
    }

    public java.util.List getAllRenderers(Object obj)
    {
        java.util.List interfaces = getAllInterfacesWithRenderers(obj);
        Vector results = new Vector() {

            public boolean addAll(Collection c)
            {
                boolean changed = false;
                for(Iterator iter = c.iterator(); iter.hasNext();)
                {
                    Object element = iter.next();
                    if(!contains(element))
                    {
                        add(element);
                        changed = true;
                    }
                }

                return changed;
            }

        }
;
        for(Iterator iter = interfaces.iterator(); iter.hasNext(); results.addAll(getExactRenderers((Class)iter.next())));
        return new Vector(results);
    }

    public java.util.List getAllInterfacesWithRenderers(Object obj)
    {
        Vector results = new Vector();
        if(obj != null)
        {
            for(Class objClass = obj.getClass(); objClass != null; objClass = objClass.getSuperclass())
                if(!getExactRenderers(objClass).isEmpty())
                {
                    results.addElement(objClass);
                } else
                {
                    Stack interfacesStack = new Stack();
                    interfacesStack.addAll(reverse(Arrays.asList(objClass.getInterfaces())));
                    Class anInterface;
                    for(; !interfacesStack.isEmpty(); interfacesStack.addAll(reverse(Arrays.asList(anInterface.getInterfaces()))))
                    {
                        anInterface = (Class)interfacesStack.pop();
                        if(!getExactRenderers(anInterface).isEmpty())
                            results.addElement(anInterface);
                    }

                }

        } else
        if(!getExactRenderers(null).isEmpty())
            results.add(null);
        return results;
    }

    private java.util.List reverse(java.util.List l)
    {
        Collections.reverse(l);
        return l;
    }

    public OutlineRenderer getExactRenderer(Class anInterface)
    {
        Stack stack = (Stack)myRenderers.get(anInterface);
        if(stack == null)
            return null;
        else
            return (OutlineRenderer)stack.peek();
    }

    public java.util.List getExactRenderers(Class anInterface)
    {
        Stack stack = (Stack)myRenderers.get(anInterface);
        if(stack == null)
        {
            return Collections.EMPTY_LIST;
        } else
        {
            Vector v = new Vector(stack);
            Collections.reverse(v);
            return v;
        }
    }

    public void addRenderer(Class forInterface, OutlineRenderer renderer)
    {
        if(renderer == null)
            throw new IllegalArgumentException("null renderer");
        Stack stack = (Stack)myRenderers.get(forInterface);
        if(stack == null)
            stack = new Stack();
        else
            stack.remove(renderer);
        stack.push(renderer);
        stack.trimToSize();
        myRenderers.put(forInterface, stack);
    }

    public boolean removeRenderer(Class forInterface, OutlineRenderer renderer)
    {
        Stack stack = (Stack)myRenderers.get(forInterface);
        if(stack == null)
            return false;
        boolean result = stack.remove(renderer);
        if(stack.isEmpty())
            myRenderers.remove(forInterface);
        return result;
    }

    public OutlineRenderer getNoteRenderer()
    {
        return myNoteRenderer;
    }

    public void addPopupMenuItems(PopupMenu menu, final OutlineNode outlineNode, final TreePath treePath, final JTree jTree)
    {
        menu.addSeparator();
        if(!outlineNode.isLeaf())
            if(jTree.isExpanded(treePath))
            {
                OutlineCommand.installCommand(menu, Outliner.getOutlinerBundleString("Collapse"), new OutlineCommand(jTree, "Outliner-Collapse") {

                    protected void runCommand()
                    {
                        invokeSwingAndWait(new Runnable() {

                            public void run()
                            {
                                jTree.collapsePath(treePath);
                            }

                        }
);
                    }



                }
);
            } else
            {
                String name = outlineNode.childrenAreComputed() ? Outliner.getOutlinerBundleString("Expand") : Outliner.formatWithOutlinerBundleString("ExpandWithRendererFormat", outlineNode.getRenderer());
                OutlineCommand.installCommand(menu, name, new OutlineCommand(jTree, "Outliner-Expand") {

                    protected void runCommand()
                    {
                        invokeSwingAndWait(new Runnable() {

                            public void run()
                            {
                                jTree.expandPath(treePath);
                            }

                        }
);
                    }



                }
);
            }
        OutlineCommand.installCommand(menu, Outliner.getOutlinerBundleString("RerenderInOwnFrame"), new OutlineCommand(jTree, "Outline-ownFrame") {

            protected void runCommand()
            {
                Object object = outlineNode.getObject();
                OutlineRenderer renderer = outlineNode.getRenderer();
                makeFrame(makeFrameTitle(object), renderer.makeOutlineNode(object)).show();
            }

        }
);
        final Object label = outlineNode.getLabel();
        if(label != null && !(label instanceof String))
            OutlineCommand.installCommand(menu, Outliner.getOutlinerBundleString("RenderLabelInOwnFrame"), new OutlineCommand(jTree, "Jexa-examineLabel") {

                protected void runCommand()
                {
                    outlineNode.getRenderer().getOutlineMaker().outline(label);
                }

            }
);
        OutlineModel outlineModel = (OutlineModel)jTree.getModel();
        if(!(edu.mit.ai.psg.ui.outliner.OutlineModel.class).isInstance(outlineModel))
            throw new IllegalArgumentException("jTree.getModel() not instanceof OutlineModel:  " + outlineModel);
        if(null == outlineModel.getRoot())
            throw new IllegalArgumentException("jTree.getModel().getRoot() is null:  " + outlineModel);
        if(outlineNode.getParent() == null)
            throw new IllegalArgumentException("outlineNode.getParent() == null");
        Object obj = outlineNode.getObject();
        if(obj != null)
        {
            java.util.List renderers = getAllRenderers(obj);
            if(renderers.size() == 0)
                OutlineCommand.installDisabledCommand(menu, Outliner.getOutlinerBundleString("NoApplicableRenderers"));
            else
            if(renderers.size() == 1)
            {
                OutlineRenderer renderer = (OutlineRenderer)renderers.iterator().next();
                String name = MessageFormat.format(Outliner.getOutlinerBundleString("RerenderWithRendererFormat"), new Object[] {
                    renderer.toString()
                });
                installRendererCommand(menu, name, renderer, outlineNode, outlineModel, jTree);
            } else
            {
                Menu rerenderSubMenu = new Menu(Outliner.getOutlinerBundleString("RerenderWith..."));
                OutlineRenderer renderer;
                for(Iterator iter = renderers.iterator(); iter.hasNext(); installRendererCommand(rerenderSubMenu, renderer.toString(), renderer, outlineNode, outlineModel, jTree))
                    renderer = (OutlineRenderer)iter.next();

                menu.add(rerenderSubMenu);
            }
        }
    }

    private void installRendererCommand(Menu menu, String menuItemString, final OutlineRenderer renderer, final OutlineNode outlineNode, final OutlineModel outlineModel, final JTree jTree)
    {
        OutlineCommand.installCommand(menu, menuItemString, new OutlineCommand(jTree, "Outline-Rerenderer") {

            protected void runCommand()
            {
                final OutlineNode newOutlineNode = outlineModel.regenerateNode(outlineNode, renderer);
                invokeSwingAndWait(new Runnable() {

                    public void run()
                    {
                        jTree.expandPath(new TreePath(outlineModel.getPathToRoot(newOutlineNode)));
                    }

                }
);
            }



        }
);
    }

    protected Map myRenderers;
    public OutlineRenderer myNoteRenderer;
    public Rectangle initialBounds;
    static final String rkCollapse = "Collapse";
    static final String rkExpand = "Expand";
    static final String rkExpandWithRendererFormat = "ExpandWithRendererFormat";
    static final String rkRerenderInOwnFrame = "RerenderInOwnFrame";
    static final String rkRenderLabelInOwnFrame = "RenderLabelInOwnFrame";
    static final String rkNoApplicableRenderers = "NoApplicableRenderers";
    static final String rkRerenderWithRendererFormat = "RerenderWithRendererFormat";
    static final String rkRerenderWith = "RerenderWith...";
    public static Font noteFont;

    static 
    {
        Map attributes = new HashMap(1);
        attributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
        noteFont = Font.getFont(attributes);
    }
}
