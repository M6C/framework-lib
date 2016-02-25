// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/patches/FlowLayout.java

package edu.mit.ai.psg.ui.patches;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

public class FlowLayout extends java.awt.FlowLayout
{

    public FlowLayout()
    {
    }

    public FlowLayout(int align)
    {
        super(align);
    }

    public FlowLayout(int align, int hgap, int vgap)
    {
        super(align, hgap, vgap);
    }

    private void moveComponents(Container target, int x, int y, int width, int height, int rowStart, int rowEnd, 
            boolean ltr)
    {
        synchronized(target.getTreeLock())
        {
            switch(getAlignment())
            {
            case 0: // '\0'
                x += ltr ? 0 : width;
                break;

            case 1: // '\001'
                x += width / 2;
                break;

            case 2: // '\002'
                x += ltr ? width : 0;
                break;

            case 4: // '\004'
                x += width;
                break;
            }
            for(int i = rowStart; i < rowEnd; i++)
            {
                Component m = target.getComponent(i);
                if(m.isVisible())
                {
                    int yCoord = y + (int)((float)(height - m.getHeight()) * m.getAlignmentY());
                    if(ltr)
                        m.setLocation(x, yCoord);
                    else
                        m.setLocation(target.getWidth() - x - m.getWidth(), yCoord);
                    x += m.getWidth() + getHgap();
                }
            }

        }
    }

    public void layoutContainer(Container target)
    {
        synchronized(target.getTreeLock())
        {
            Insets insets = target.getInsets();
            int maxwidth = target.getWidth() - (insets.left + insets.right + getHgap() * 2);
            int nmembers = target.getComponentCount();
            int x = 0;
            int y = insets.top + getVgap();
            int rowh = 0;
            int start = 0;
            boolean ltr = target.getComponentOrientation().isLeftToRight();
            for(int i = 0; i < nmembers; i++)
            {
                Component m = target.getComponent(i);
                if(m.isVisible())
                {
                    Dimension d = m.getPreferredSize();
                    m.setSize(d.width, d.height);
                    if(x == 0 || x + d.width <= maxwidth)
                    {
                        if(x > 0)
                            x += getHgap();
                        x += d.width;
                        rowh = Math.max(rowh, d.height);
                    } else
                    {
                        moveComponents(target, insets.left + getHgap(), y, maxwidth - x, rowh, start, i, ltr);
                        x = d.width;
                        y += getVgap() + rowh;
                        rowh = d.height;
                        start = i;
                    }
                }
            }

            moveComponents(target, insets.left + getHgap(), y, maxwidth - x, rowh, start, nmembers, ltr);
        }
    }
}
