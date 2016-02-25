// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/SimpleNode.java

package edu.mit.ai.psg.jevaES;

import java.io.PrintStream;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            Node, JevaESParserTreeConstants

public class SimpleNode
    implements Node
{

    public SimpleNode(int i)
    {
        id = i;
    }

    public void jjtOpen()
    {
    }

    public void jjtClose()
    {
    }

    public void jjtSetParent(Node n)
    {
        parent = n;
    }

    public Node jjtGetParent()
    {
        return parent;
    }

    public void jjtAddChild(Node n, int i)
    {
        if(children == null)
            children = new Node[i + 1];
        else
        if(i >= children.length)
        {
            Node c[] = new Node[i + 1];
            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
    }

    public Node jjtGetChild(int i)
    {
        return children[i];
    }

    public int jjtGetNumChildren()
    {
        return children != null ? children.length : 0;
    }

    public String toString()
    {
        return JevaESParserTreeConstants.jjtNodeName[id];
    }

    public String toString(String prefix)
    {
        return prefix + toString();
    }

    public void dump(String prefix)
    {
        System.out.println(toString(prefix));
        if(children != null)
        {
            for(int i = 0; i < children.length; i++)
            {
                SimpleNode n = (SimpleNode)children[i];
                if(n != null)
                    n.dump(prefix + " ");
            }

        }
    }

    protected Node parent;
    protected Node children[];
    protected int id;
}
