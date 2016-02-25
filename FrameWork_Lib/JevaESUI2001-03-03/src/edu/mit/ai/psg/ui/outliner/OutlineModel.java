// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/OutlineModel.java

package edu.mit.ai.psg.ui.outliner;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import javax.swing.SwingUtilities;
import javax.swing.tree.*;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineNode, OutlineNodeDefault, OutlineRenderer

public class OutlineModel extends DefaultTreeModel
{
    private static class WaitingRoom
    {

        synchronized void waitForCompletion()
        {
            while(!isComplete) 
                try
                {
                    wait();
                }
                catch(InterruptedException interruptedexception) { }
        }

        synchronized void announceCompletion()
        {
            isComplete = true;
            notifyAll();
        }

        boolean isComplete;

        private WaitingRoom()
        {
            isComplete = false;
        }

    }

    public static class HiddenRootNode extends OutlineNodeDefault
    {

        public void setModel(OutlineModel model)
        {
            myModel = model;
        }

        public OutlineModel getModel()
        {
            return myModel;
        }

        OutlineModel myModel;

        public HiddenRootNode(OutlineRenderer noteRenderer)
        {
            super("Outline root", noteRenderer);
            myModel = null;
        }
    }


    public OutlineModel(HiddenRootNode hiddenRoot)
    {
        super(hiddenRoot);
    }

    public OutlineModel(OutlineNode displayRoot, OutlineRenderer hiddenRootRenderer)
    {
        this(new HiddenRootNode(hiddenRootRenderer));
        HiddenRootNode hiddenRoot = (HiddenRootNode)super.getRoot();
        hiddenRoot.setModel(this);
        addNodeChild(hiddenRoot, displayRoot);
        hiddenRoot.setChildrenAreComputed(true);
    }

    public static OutlineModel getModel(OutlineNode outlineNode)
    {
        TreeNode node = outlineNode;
        for(TreeNode parent = node.getParent(); parent != null; parent = parent.getParent())
            node = parent;

        if(node instanceof HiddenRootNode)
            return ((HiddenRootNode)node).getModel();
        else
            return null;
    }

    public int getChildCount(Object parent)
    {
        triggerComputingChildren((OutlineNode)parent);
        return super.getChildCount(parent);
    }

    public Object getChild(Object parent, int index)
    {
        triggerComputingChildren((OutlineNode)parent);
        return super.getChild(parent, index);
    }

    protected void triggerComputingChildren(final OutlineNode outlineNode)
    {
        if(!outlineNode.childrenAreComputed() && !isInProgress(outlineNode))
            runCompute(new Runnable() {

                public void run()
                {
                    ensureChildrenAreComputed(outlineNode);
                }

            }
);
    }

    public void ensureChildrenAreComputed(OutlineNode outlineNode)
    {
        if(SwingUtilities.isEventDispatchThread())
            throw new IllegalStateException("Should not be called from AWT Event Dispatch Thread.");
        if(!outlineNode.childrenAreComputed() && addInProgressOrWait(outlineNode))
            try
            {
                if(!outlineNode.childrenAreComputed())
                    computeChildren(outlineNode);
            }
            finally
            {
                removeInProgress(outlineNode);
            }
    }

    private static boolean addInProgressOrWait(OutlineNode key)
    {
        boolean firstAdded = true;
        WaitingRoom waitingRoom;
        synchronized(computingWaitingRooms)
        {
            waitingRoom = (WaitingRoom)computingWaitingRooms.get(key);
            if(waitingRoom == null)
            {
                waitingRoom = new WaitingRoom();
                computingWaitingRooms.put(key, waitingRoom);
            } else
            {
                firstAdded = false;
            }
        }
        if(!firstAdded)
        {
            waitingRoom.waitForCompletion();
            return false;
        } else
        {
            return true;
        }
    }

    private static boolean isInProgress(OutlineNode key)
    {
        boolean flag;
        synchronized(computingWaitingRooms)
        {
            flag = computingWaitingRooms.containsKey(key);
        }
        return flag;
    }

    private static void removeInProgress(OutlineNode key)
    {
        synchronized(computingWaitingRooms)
        {
            WaitingRoom waitingRoom = (WaitingRoom)computingWaitingRooms.get(key);
            waitingRoom.announceCompletion();
            computingWaitingRooms.remove(key);
        }
    }

    private void computeChildren(final OutlineNode outlineNode)
    {
        if(SwingUtilities.isEventDispatchThread())
            throw new IllegalStateException("Should not be called from AWT Event Dispatch Thread.");
        final OutlineRenderer renderer = outlineNode.getRenderer();
        try
        {
            List children = renderer.makeChildOutlineNodes(outlineNode);
            addChildrenSafely(outlineNode, children);
        }
        catch(final Throwable t)
        {
            final String errMsg = "Exception thrown using " + renderer + " to generate children for " + outlineNode + ":";
            System.err.println(errMsg);
            t.printStackTrace();
            runUpdate(new Runnable() {

                public void run()
                {
                    addNodeChild(outlineNode, renderer.makeNoteOutlineNode("<" + errMsg + "\n  " + t + ">"));
                }

            }
);
        }
    }

    public void addChildrenSafely(final OutlineNode outlineNode, final List children)
    {
        runUpdate(new Runnable() {

            public void run()
            {
                for(Iterator iter = children.iterator(); iter.hasNext(); addNodeChild(outlineNode, (OutlineNode)iter.next()));
                outlineNode.setChildrenAreComputed(true);
                outlineNode.setIsLeaf(outlineNode.getChildCount() == 0);
            }

        }
);
    }

    public void addNodeChild(OutlineNode parent, OutlineNode newChild)
    {
        insertNodeInto(newChild, parent, parent.getChildCount());
    }

    public void insertNodeInto(MutableTreeNode newChild, MutableTreeNode parent, int index)
    {
        super.insertNodeInto(newChild, parent, index);
        OutlineNode newOutlineChild = (OutlineNode)newChild;
        newOutlineChild.getRenderer().notifyInserted(this, newOutlineChild, index);
    }

    public void removeNodeFromParent(MutableTreeNode child)
    {
        if(child.getParent() != null)
        {
            OutlineNode outlineChild = (OutlineNode)child;
            outlineChild.getRenderer().notifyRemoving(this, outlineChild);
            super.removeNodeFromParent(outlineChild);
            child.setParent(null);
        }
    }

    public OutlineNode regenerateNode(final OutlineNode outlineNode, OutlineRenderer renderer)
    {
        final OutlineNode parent = (OutlineNode)outlineNode.getParent();
        if(parent == null)
        {
            throw new NullPointerException("null parent: " + outlineNode);
        } else
        {
            final OutlineNode newNode = renderer.makeOutlineNode(outlineNode.getLabel(), outlineNode.getObject());
            runUpdate(new Runnable() {

                public void run()
                {
                    int index = getIndexOfChild(parent, outlineNode);
                    insertNodeInto(newNode, parent, index);
                    removeNodeFromParent(outlineNode);
                }

            }
);
            return newNode;
        }
    }

    void runUpdate(Runnable doUpdate)
    {
        if(SwingUtilities.isEventDispatchThread())
            doUpdate.run();
        else
            try
            {
                SwingUtilities.invokeAndWait(doUpdate);
            }
            catch(InterruptedException interruptedexception) { }
            catch(InvocationTargetException e)
            {
                e.printStackTrace();
            }
    }

    void runCompute(Runnable doCompute)
    {
        if(!SwingUtilities.isEventDispatchThread())
            doCompute.run();
        else
            (new Thread(doCompute)).start();
    }

    private static final Map computingWaitingRooms = new HashMap(10);

}
