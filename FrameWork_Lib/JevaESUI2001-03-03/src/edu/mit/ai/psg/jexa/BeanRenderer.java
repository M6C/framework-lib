// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jexa/BeanRenderer.java

package edu.mit.ai.psg.jexa;

import edu.mit.ai.psg.ui.outliner.*;
import java.beans.*;
import java.lang.reflect.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.Vector;

// Referenced classes of package edu.mit.ai.psg.jexa:
//            ObjectRenderer, Jexa

public class BeanRenderer extends ObjectRenderer
{

    public BeanRenderer(OutlineMaker outlineMaker)
    {
        super(outlineMaker);
    }

    public List makeChildOutlineNodes(OutlineNode outlineNode)
    {
        Object object = outlineNode.getObject();
        List childNodes = new Vector();
        if(object != null)
        {
            Class objClass = object.getClass();
            if(objClass.isArray())
            {
                childNodes.add(super.myOutlineMaker.makeOutlineNode("length", new Integer(Array.getLength(object))));
                int i;
                for(i = 0; i < Array.getLength(object) && i <= maxChildIndex; i++)
                {
                    Object obj = Array.get(object, i);
                    childNodes.add(super.myOutlineMaker.makeOutlineNode("[" + i + "]", obj));
                }

                if(i < Array.getLength(object))
                    addReachedLimitNode(childNodes);
            } else
            {
                try
                {
                    BeanInfo beanInfo = Introspector.getBeanInfo(objClass);
                    PropertyDescriptor descriptors[] = sortDescriptorsByReadMethodOrder(beanInfo.getPropertyDescriptors(), objClass);
                    for(int d = 0; d < descriptors.length; d++)
                    {
                        PropertyDescriptor descriptor = descriptors[d];
                        Method readMethod = findInvokeableMethod(descriptor.getReadMethod(), objClass);
                        if(readMethod != null)
                        {
                            String name = descriptor.getName();
                            Object value = null;
                            try
                            {
                                value = readMethod.invoke(object, noArgs);
                            }
                            catch(InvocationTargetException e)
                            {
                                value = makeExceptionNote(readMethod.getName() + "()", e.getTargetException());
                            }
                            catch(Exception e)
                            {
                                value = makeExceptionNote(readMethod.getName() + "()", e);
                            }
                            childNodes.add(super.myOutlineMaker.makeOutlineNode(name, value));
                        } else
                        if(descriptor instanceof IndexedPropertyDescriptor)
                        {
                            IndexedPropertyDescriptor indexedPD = (IndexedPropertyDescriptor)descriptor;
                            Method indexedReadMethod = findInvokeableMethod(indexedPD.getIndexedReadMethod(), objClass);
                            if(indexedReadMethod != null)
                            {
                                String name = indexedPD.getName();
                                Object indexArgs[] = new Object[1];
                                int i = 0;
                                try
                                {
                                    while(i <= maxChildIndex) 
                                    {
                                        indexArgs[0] = new Integer(i);
                                        Object value = indexedReadMethod.invoke(object, indexArgs);
                                        childNodes.add(super.myOutlineMaker.makeOutlineNode(name + "[" + i + "]", value));
                                        i++;
                                    }
                                    if(i > maxChildIndex)
                                        addReachedLimitNode(childNodes);
                                }
                                catch(InvocationTargetException ie)
                                {
                                    Throwable e = ie.getTargetException();
                                    if(!(e instanceof IndexOutOfBoundsException))
                                        addExceptionNode(childNodes, indexedReadMethod.getName() + "(" + i + ")", e);
                                }
                                catch(Exception e)
                                {
                                    addExceptionNode(childNodes, indexedReadMethod.getName() + "(" + i + ")", e);
                                }
                            }
                        }
                    }

                }
                catch(IntrospectionException e)
                {
                    addErrorNode(childNodes, e);
                }
                catch(SecurityException e)
                {
                    addErrorNode(childNodes, e);
                }
            }
        }
        return childNodes;
    }

    private void addReachedLimitNode(List childNodes)
    {
        childNodes.add(makeNoteOutlineNode(Jexa.formatWithJexaBundleString("ReachedLimitFormat", "BeanRenderer.maxChildIndex")));
    }

    private void addExceptionNode(List childNodes, String methodCall, Throwable t)
    {
        childNodes.add(makeNoteOutlineNode(makeExceptionNote(methodCall, t)));
    }

    private String makeExceptionNote(String methodCall, Throwable e)
    {
        return MessageFormat.format(Jexa.getJexaBundleString("ExceptionInvokingFormat"), new Object[] {
            methodCall, e.toString()
        });
    }

    private void addErrorNode(List childNodes, Throwable t)
    {
        childNodes.add(makeNoteOutlineNode(Jexa.formatWithJexaBundleString("ExceptionIntrospectingFormat", t)));
    }

    public static PropertyDescriptor[] sortDescriptorsByReadMethodOrder(PropertyDescriptor descriptors[], Class objClass)
    {
        Method publicMethods[] = objClass.getMethods();
        PropertyDescriptor results[] = new PropertyDescriptor[descriptors.length];
        int numResults = 0;
        for(int mi = 0; mi < publicMethods.length; mi++)
        {
            Method method = publicMethods[mi];
            for(int di = 0; di < descriptors.length; di++)
            {
                PropertyDescriptor descriptor = descriptors[di];
                if(method.equals(descriptor.getReadMethod()) || (descriptor instanceof IndexedPropertyDescriptor) && method.equals(((IndexedPropertyDescriptor)descriptor).getIndexedReadMethod()))
                    results[numResults++] = descriptor;
            }

        }

        PropertyDescriptor value[] = new PropertyDescriptor[numResults];
        System.arraycopy(results, 0, value, 0, numResults);
        return value;
    }

    public static Method findInvokeableMethod(Method method, Class targetClass)
    {
        if(method == null)
            return null;
        if(Modifier.isPublic(method.getModifiers()) && ObjectRenderer.isPublicToTopLevel(method.getDeclaringClass()))
            return method;
        String methodName = method.getName();
        Class methodParamTypes[] = method.getParameterTypes();
        for(Class aClass = targetClass; aClass != null; aClass = aClass.getSuperclass())
        {
            Class declaredInterfaces[] = aClass.getInterfaces();
            for(int i = 0; i < declaredInterfaces.length; i++)
            {
                Class anInterface = declaredInterfaces[i];
                if(ObjectRenderer.isPublicToTopLevel(anInterface))
                    try
                    {
                        return anInterface.getMethod(methodName, methodParamTypes);
                    }
                    catch(NoSuchMethodException nosuchmethodexception) { }
            }

        }

        return null;
    }

    public static void initialize(OutlineMaker outlineMaker)
    {
        outlineMaker.addRenderer(java.lang.Object.class, new BeanRenderer(outlineMaker));
    }

    public static int maxChildIndex = 20;
    static final String rkReachedLimitFormat = "ReachedLimitFormat";
    static final String rkExceptionInvokingFormat = "ExceptionInvokingFormat";
    static final String rkExceptionIntrospectingFormat = "ExceptionIntrospectingFormat";
    static final Object noArgs[] = new Object[0];

}
