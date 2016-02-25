// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jexa/ObjectRenderer.java

package edu.mit.ai.psg.jexa;

import edu.mit.ai.psg.strings.Prettify;
import edu.mit.ai.psg.strings.Stringify;
import edu.mit.ai.psg.ui.outliner.OutlineMaker;
import edu.mit.ai.psg.ui.outliner.OutlineNode;
import edu.mit.ai.psg.ui.outliner.OutlineRendererCaching;
import edu.mit.ai.psg.ui.outliner.OutlineRendererDefault;
import java.awt.Component;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.font.TextAttribute;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ReflectPermission;
import java.security.AccessControlException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

// Referenced classes of package edu.mit.ai.psg.jexa:
//            Jexa

public class ObjectRenderer extends OutlineRendererCaching
{

    public ObjectRenderer(OutlineMaker outlineMaker)
    {
        super(outlineMaker);
        super.getLabelComponent().setFont(defaultFont);
        super.getObjectComponent().setFont(defaultFont);
    }

    public OutlineNode makeOutlineNode(Object label, Object object)
    {
        OutlineNode outlineNode = super.makeOutlineNode(label, object);
        if(object == null || (object instanceof Number) || (object instanceof Character) || (object instanceof Boolean))
        {
            outlineNode.setChildrenAreComputed(true);
            outlineNode.setIsLeaf(true);
        }
        return outlineNode;
    }

    public java.util.List makeChildOutlineNodes(OutlineNode outlineNode)
    {
        Object object = outlineNode.getObject();
        Vector childNodes = new Vector();
        if(object != null)
        {
            Class objClass = object.getClass();
            if(objClass.isArray())
            {
                childNodes.add(super.myOutlineMaker.makeOutlineNode("length", new Integer(Array.getLength(object))));
                for(int i = 0; i < Array.getLength(object); i++)
                {
                    Object obj = Array.get(object, i);
                    childNodes.add(super.myOutlineMaker.makeOutlineNode("[" + i + "]", obj));
                }

            } else
            {
                addFieldOutlineNodes(childNodes, object, objClass, false);
            }
        }
        return childNodes;
    }

    protected void addFieldOutlineNodes(Vector childNodes, Object object, Class objClass, boolean addStatics)
    {
        boolean hasReflectPermission = true;
        try
        {
            SecurityManager security = System.getSecurityManager();
            if(security != null)
                security.checkPermission(reflectPermission);
        }
        catch(AccessControlException accesscontrolexception)
        {
            hasReflectPermission = false;
        }
        try
        {
            if(hasReflectPermission)
            {
                String dividerFormat = Jexa.getJexaBundleString("ClassNameFieldsFormat");
                for(Class aClass = objClass; aClass != null; aClass = aClass.getSuperclass())
                {
                    int firstClassFieldIndex = childNodes.size();
                    boolean aClassHasFields = false;
                    boolean aClassIsPublicToTopLevel = isPublicToTopLevel(aClass);
                    Field fields[] = aClass.getDeclaredFields();
                    for(int i = 0; i < fields.length; i++)
                        try
                        {
                            Field field = fields[i];
                            if(addStatics == Modifier.isStatic(field.getModifiers()))
                            {
                                if(!aClassIsPublicToTopLevel || !Modifier.isPublic(field.getModifiers()))
                                    field.setAccessible(true);
                                childNodes.add(super.myOutlineMaker.makeOutlineNode(field.getName(), field.get(object)));
                                aClassHasFields = true;
                            }
                        }
                        catch(SecurityException securityexception) { }
                        catch(IllegalAccessException illegalaccessexception) { }

                    if(aClassHasFields && aClass != objClass)
                        childNodes.insertElementAt(makeNoteOutlineNode(MessageFormat.format(dividerFormat, new Object[] {
                            aClass.getName()
                        })), firstClassFieldIndex);
                }

            } else
            {
                Field fields[] = objClass.getFields();
                for(int i = 0; i < fields.length; i++)
                {
                    Field field = fields[i];
                    if(addStatics == Modifier.isStatic(field.getModifiers()))
                        childNodes.add(super.myOutlineMaker.makeOutlineNode(field.getName(), field.get(object)));
                }

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
    }

    private void addErrorNode(java.util.List childNodes, Throwable t)
    {
        childNodes.add(makeNoteOutlineNode(Jexa.formatWithJexaBundleString("ExceptionReflectingOnFieldsFormat", t)));
    }

    public static boolean isPublicToTopLevel(Class aClassOrInterface)
    {
        return Modifier.isPublic(aClassOrInterface.getModifiers()) && (null == aClassOrInterface.getDeclaringClass() || isPublicToTopLevel(aClassOrInterface.getDeclaringClass()));
    }

    public void addPopupMenuTitle(PopupMenu menu, OutlineNode outlineNode, TreePath treePath, JTree jtree)
    {
        String description = Prettify.string(0, Stringify.printToString(outlineNode.getObject()), -1, 3, 1, true, Prettify.defaultSyntax);
        if(description.length() > 80)
            description = description.substring(0, 77) + "...";
        menu.add(new MenuItem(description));
        menu.add(new MenuItem(" " + Jexa.formatWithJexaBundleString("ByRendererFormat", toString())));
    }

    public String computeObjectText(OutlineNode outlineNode)
    {
        return Prettify.string(Stringify.printToString(outlineNode.getObject()));
    }

    public String computeLabelText(OutlineNode outlineNode)
    {
        Object label = outlineNode.getLabel();
        if(label != null && !(label instanceof String))
            return Prettify.string(Stringify.printToString(label)) + ":  ";
        else
            return super.computeLabelText(outlineNode);
    }

    public static void initialize(OutlineMaker outlineMaker)
    {
        outlineMaker.addRenderer(java.lang.Object.class, new ObjectRenderer(outlineMaker));
    }

    public static Font defaultFont;
    static final String rkClassNameFieldsFormat = "ClassNameFieldsFormat";
    static final String rkExceptionReflectingOnFieldsFormat = "ExceptionReflectingOnFieldsFormat";
    static final String rkByRendererFormat = "ByRendererFormat";
    private static ReflectPermission reflectPermission = new ReflectPermission("suppressAccessChecks");

    static 
    {
        Map attributes = new HashMap(1);
        attributes.put(TextAttribute.FAMILY, "Monospaced");
        defaultFont = Font.getFont(attributes);
    }
}
