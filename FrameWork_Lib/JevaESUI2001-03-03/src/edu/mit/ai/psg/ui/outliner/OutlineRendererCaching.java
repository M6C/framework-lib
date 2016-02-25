// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ui/outliner/OutlineRendererCaching.java

package edu.mit.ai.psg.ui.outliner;

import edu.mit.ai.psg.utilities.HashMapCache;
import java.util.Map;

// Referenced classes of package edu.mit.ai.psg.ui.outliner:
//            OutlineRendererDefault, OutlineNode, OutlineMaker, OutlineModel

public class OutlineRendererCaching extends OutlineRendererDefault
{

    public OutlineRendererCaching(OutlineMaker outlineMakr)
    {
        super(outlineMakr);
        labelTextCache = makeLabelTextCache();
        objectTextCache = makeObjectTextCache();
        toolTipTextCache = makeToolTipTextCache();
    }

    protected Map makeLabelTextCache()
    {
        return new HashMapCache(1);
    }

    protected Map makeObjectTextCache()
    {
        return new HashMapCache(1);
    }

    protected Map makeToolTipTextCache()
    {
        return new HashMapCache(1);
    }

    public String getLabelText(OutlineNode node)
    {
        if(labelTextCache == null)
            return computeLabelText(node);
        Object key = getLabelTextCacheKey(node);
        String cachedString = (String)labelTextCache.get(key);
        if(cachedString == null)
        {
            cachedString = computeLabelText(node);
            labelTextCache.put(key, cachedString);
        }
        return cachedString;
    }

    public String getObjectText(OutlineNode node)
    {
        if(objectTextCache == null)
            return computeObjectText(node);
        Object key = getObjectTextCacheKey(node);
        String cachedString = (String)objectTextCache.get(key);
        if(cachedString == null)
        {
            cachedString = computeObjectText(node);
            objectTextCache.put(key, cachedString);
        }
        return cachedString;
    }

    public String getToolTipText(OutlineNode node)
    {
        if(toolTipTextCache == null)
            return computeToolTipText(node);
        Object key = getToolTipTextCacheKey(node);
        String cachedString = (String)toolTipTextCache.get(key);
        if(cachedString == null)
        {
            cachedString = computeToolTipText(node);
            toolTipTextCache.put(key, cachedString);
        }
        return cachedString;
    }

    protected Object getLabelTextCacheKey(OutlineNode node)
    {
        return node.getLabel();
    }

    protected Object getObjectTextCacheKey(OutlineNode node)
    {
        return node.getObject();
    }

    protected Object getToolTipTextCacheKey(OutlineNode node)
    {
        return null;
    }

    protected void decacheLabelText(OutlineNode node)
    {
        if(labelTextCache != null)
            labelTextCache.remove(getLabelTextCacheKey(node));
    }

    protected void decacheObjectText(OutlineNode node)
    {
        if(objectTextCache != null)
            objectTextCache.remove(getObjectTextCacheKey(node));
    }

    protected void decacheToolTipText(OutlineNode node)
    {
        if(toolTipTextCache != null)
            toolTipTextCache.remove(getToolTipTextCacheKey(node));
    }

    public void notifyInserted(OutlineModel model, OutlineNode child, int index)
    {
        decacheLabelText(child);
        decacheObjectText(child);
        decacheToolTipText(child);
    }

    private Map labelTextCache;
    private Map objectTextCache;
    private Map toolTipTextCache;
}
