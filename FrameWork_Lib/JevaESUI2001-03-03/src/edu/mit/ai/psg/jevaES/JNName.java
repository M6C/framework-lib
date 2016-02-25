// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JNName.java

package edu.mit.ai.psg.jevaES;

import java.lang.reflect.*;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            ExpressionNode, IExpressionNode, IJevaNode, VerifyingException, 
//            IllegalNameException, IPackage, EvaluatingError, FieldStore, 
//            JNResultType, IllegalConstructException, IllegalTypeException, IExpressionStoreNode, 
//            JevaESParserConstants, ThrowException, SimpleNode, JevaNode, 
//            IEnv, IStore, EvalMethods, Token, 
//            TypeMethods, AccessibilityMethods, JevaES

public class JNName extends ExpressionNode
    implements IExpressionStoreNode, JevaESParserConstants
{

    JNName(int id)
    {
        super(id);
        name = null;
        category = 0;
        fieldClassOrPkg = null;
    }

    private IExpressionNode getChildExpression()
    {
        return (IExpressionNode)jjtGetChild(0);
    }

    private JNName getChildName()
    {
        return (JNName)jjtGetChild(0);
    }

    private IJevaNode getChild()
    {
        return (IJevaNode)jjtGetChild(0);
    }

    private boolean categoryIsAmbiguous()
    {
        return 0 == category || 1 == category;
    }

    private boolean categoryIsExpression()
    {
        return 1 == category || 2 == category || 3 == category || 4 == category;
    }

    public void setNameCategoryToAmbiguousExpression()
    {
        switch(category)
        {
        default:
            throw categorySetError("AMBIGUOUS_EXPRESSION_NAME");

        case 0: // '\0'
        case 1: // '\001'
            category = 1;
            fieldClassOrPkg = null;
            // fall through

        case 2: // '\002'
            return;
        }
    }

    public void setNameCategoryToVariable()
    {
        switch(category)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
            category = 2;
            fieldClassOrPkg = null;
            break;

        default:
            throw categorySetError("VARIABLE_NAME");
        }
    }

    public void setNameCategoryToField(Field f)
    {
        switch(category)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 3: // '\003'
            category = 3;
            fieldClassOrPkg = f;
            break;

        case 2: // '\002'
        default:
            throw categorySetError("FIELD_NAME");
        }
    }

    public void setNameCategoryToArrayField()
    {
        switch(category)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 4: // '\004'
            category = 4;
            fieldClassOrPkg = null;
            break;

        case 2: // '\002'
        case 3: // '\003'
        default:
            throw categorySetError("FIELD_NAME");
        }
    }

    public void setNameCategoryToType(Class c)
    {
        switch(category)
        {
        case 0: // '\0'
        case 5: // '\005'
            category = 5;
            fieldClassOrPkg = c;
            break;

        default:
            throw categorySetError("TYPE_NAME");
        }
    }

    public void setNameCategoryToPackage(IPackage p)
    {
        switch(category)
        {
        case 0: // '\0'
        case 6: // '\006'
            category = 6;
            fieldClassOrPkg = p;
            break;

        default:
            throw categorySetError("PACKAGE_NAME");
        }
    }

    public void setNameCategoryToThis()
    {
        switch(category)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 7: // '\007'
            category = 7;
            fieldClassOrPkg = null;
            break;

        default:
            throw categorySetError("SPECIAL_NAME_THIS");
        }
    }

    public void setNameCategoryToSuper()
    {
        switch(category)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 8: // '\b'
            category = 8;
            fieldClassOrPkg = null;
            break;

        default:
            throw categorySetError("SPECIAL_NAME_SUPER");
        }
    }

    public void setNameCategoryToClassLiteral(Class c)
    {
        switch(category)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 9: // '\t'
            category = 9;
            fieldClassOrPkg = c;
            break;

        default:
            throw categorySetError("SPECIAL_NAME_CLASS");
        }
    }

    public int getNameCategory(IEnv env)
        throws VerifyingException
    {
        if(categoryIsAmbiguous())
            reclassifyName(env);
        return category;
    }

    public int getNameCategory()
    {
        if(categoryIsAmbiguous())
            throw JevaNode.notVerified();
        else
            return category;
    }

    protected Error categorySetError(String newCategory)
    {
        return JevaNode.shouldNotHappen("attempt to set a " + getNameCategoryName() + " to " + newCategory);
    }

    public String getFullPackageOrClassName(IEnv env)
        throws VerifyingException
    {
        if(categoryIsAmbiguous())
            reclassifyName(env);
        if(6 == category)
            if(0 == jjtGetNumChildren())
            {
                return name;
            } else
            {
                JNName child = getChildName();
                return child.getFullPackageOrClassName(env) + "." + name;
            }
        if(5 == category)
            return getNamedClass().getName();
        else
            throw JevaNode.shouldNotHappen("Not package or class name.");
    }

    public String getSimpleName(IEnv env)
        throws VerifyingException
    {
        if(categoryIsAmbiguous())
            reclassifyName(env);
        return name;
    }

    public String getSimpleName()
    {
        if(categoryIsAmbiguous())
            throw JevaNode.notVerified();
        else
            return name;
    }

    public String getFullPackageOrClassName()
    {
        if(categoryIsAmbiguous())
            throw JevaNode.notVerified();
        try
        {
            return getFullPackageOrClassName(null);
        }
        catch(VerifyingException e)
        {
            throw JevaNode.shouldNotHappen(e);
        }
    }

    public Class getNamedClass(IEnv env)
        throws VerifyingException
    {
        if(categoryIsAmbiguous())
            reclassifyName(env);
        if(5 == category)
            return (Class)fieldClassOrPkg;
        else
            throw new IllegalNameException(this, env, "Not a class name: " + getFullNameAndCurrentCategory());
    }

    protected Class getNamedClass()
    {
        return (Class)fieldClassOrPkg;
    }

    protected Field getNamedField()
    {
        return (Field)fieldClassOrPkg;
    }

    protected IPackage getNamedPackage()
    {
        return (IPackage)fieldClassOrPkg;
    }

    public Class computeType(IEnv env)
        throws VerifyingException
    {
        if(categoryIsAmbiguous())
        {
            setNameCategoryToAmbiguousExpression();
            reclassifyName(env);
        }
        switch(category)
        {
        case 9: // '\t'
            return java.lang.Class.class;

        case 2: // '\002'
            IStore store = env.lookupLocal(name);
            if(store != null)
                return store.getType();
            else
                throw new IllegalNameException(this, env, "Unbound local variable: " + name);

        case 3: // '\003'
            return getNamedField().getType();

        case 4: // '\004'
            return Integer.TYPE;

        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        default:
            throw ErrorNotVariableOrField();
        }
    }

    public boolean computeIsConstant(IEnv env)
        throws VerifyingException
    {
        if(categoryIsAmbiguous())
        {
            setNameCategoryToAmbiguousExpression();
            reclassifyName(env);
        }
        switch(category)
        {
        case 9: // '\t'
            return false;

        case 2: // '\002'
            IStore store = env.lookupLocal(name);
            if(store != null)
                return store.isConstant();
            else
                throw new IllegalNameException(this, env, "Unbound local variable: " + name);

        case 3: // '\003'
            Field field = getNamedField();
            Class type = field.getType();
            if(type.isPrimitive() || type.equals(java.lang.String.class))
                return Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers());
            // fall through

        case 4: // '\004'
            return false;

        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        default:
            throw ErrorNotVariableOrField();
        }
    }

    protected Error ErrorNotVariableOrField()
    {
        return JevaNode.shouldNotHappen("name is not variable or field: (" + getNameCategoryName() + ") " + printToString(false));
    }

    public boolean isFinal(IEnv env)
        throws VerifyingException
    {
        if(categoryIsAmbiguous())
        {
            setNameCategoryToAmbiguousExpression();
            reclassifyName(env);
        }
        switch(category)
        {
        case 2: // '\002'
            return env.lookupLocal(name).isFinal();

        case 3: // '\003'
            return Modifier.isFinal(getNamedField().getModifiers());

        case 4: // '\004'
        case 7: // '\007'
        case 8: // '\b'
        case 9: // '\t'
            return true;

        case 5: // '\005'
        case 6: // '\006'
        default:
            throw ErrorNotVariableOrField();
        }
    }

    public Object eval(IEnv env)
        throws ThrowException
    {
        switch(category)
        {
        case 2: // '\002'
        case 3: // '\003'
            return evalToStore(env).getValue();

        case 4: // '\004'
            return new Integer(Array.getLength(EvalMethods.eval(getChildExpression(), env)));

        case 9: // '\t'
            return getNamedClass();

        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        default:
            throw ErrorNotVariableOrField();
        }
    }

    public IStore evalToStore(IEnv env)
        throws ThrowException
    {
        switch(category)
        {
        case 2: // '\002'
            return env.lookupLocal(name);

        case 3: // '\003'
            Field field = getNamedField();
            IExpressionNode childExpr = getChildExpression();
            if(childExpr instanceof JNName)
            {
                int childCategory;
                try
                {
                    childCategory = ((JNName)childExpr).getNameCategory(env);
                }
                catch(VerifyingException e)
                {
                    throw new EvaluatingError(this, env, e);
                }
                if(5 == childCategory)
                    return new FieldStore(field, null);
            }
            return new FieldStore(field, EvalMethods.eval(childExpr, env));
        }
        throw ErrorNotVariableOrField();
    }

    protected void reclassifyName(IEnv env)
        throws VerifyingException
    {
        if(categoryIsAmbiguous())
            if(0 == jjtGetNumChildren())
            {
                reclassifySimpleName(env);
            } else
            {
                IJevaNode child = getChild();
                name = child.getEndToken().next.image;
                if(child instanceof JNResultType)
                    setNameCategoryToClassLiteral(((JNResultType)child).toType(env));
                else
                if(!(child instanceof JNName))
                {
                    reclassifyFieldName((IExpressionNode)child, name, env);
                } else
                {
                    JNName prefixName = (JNName)child;
                    int prefixCategory = prefixName.getNameCategory(env);
                    switch(prefixCategory)
                    {
                    case 2: // '\002'
                    case 3: // '\003'
                    case 4: // '\004'
                        reclassifyFieldName(prefixName, name, env);
                        break;

                    case 5: // '\005'
                        reclassifyStaticName(env);
                        break;

                    case 6: // '\006'
                        if(category != 1)
                            reclassifyClassOrPackageName(env);
                        else
                            throw new IllegalNameException(this, env, prefixName.getFullPackageOrClassName(env) + " not found");
                        break;

                    default:
                        throw JevaNode.shouldNotHappen("prefix is " + prefixName.getNameCategoryName());
                    }
                }
            }
    }

    private void reclassifySimpleName(IEnv env)
        throws VerifyingException
    {
        name = getBeginToken().image;
        if("this".equals(name) || "super".equals(name))
            throw new IllegalConstructException(this, env, "'" + name + "' is valid only in non-static contexts");
        if(null != env.lookupLocal(name))
            setNameCategoryToVariable();
        else
        if(category != 1)
            reclassifyClassOrPackageName(env);
        else
            throw new IllegalNameException(this, env, "expression '" + name + "' is undefined.");
    }

    private void reclassifyFieldName(IExpressionNode child, String name, IEnv env)
        throws VerifyingException
    {
        Class childType = EvalMethods.getType(child, env);
        if(childType != null && childType.isArray() && "length".equals(name))
            setNameCategoryToArrayField();
        else
            setNameCategoryToField(computeField(env));
    }

    private void reclassifyStaticName(IEnv env)
        throws VerifyingException
    {
        try
        {
            Field foundField = computeField(env);
            setNameCategoryToField(foundField);
            return;
        }
        catch(VerifyingException verifyingexception) { }
        try
        {
            Class foundClassOrInterface = computeClassOrInterface(env);
            setNameCategoryToType(foundClassOrInterface);
            return;
        }
        catch(VerifyingException verifyingexception1) { }
        if(categoryIsAmbiguous())
            throw new IllegalNameException(this, env, "Class or interface has no accessible part named " + name);
        else
            return;
    }

    private void reclassifyClassOrPackageName(IEnv env)
        throws VerifyingException
    {
        try
        {
            Class foundClassOrInterface = computeClassOrInterface(env);
            setNameCategoryToType(foundClassOrInterface);
            return;
        }
        catch(VerifyingException verifyingexception) { }
        try
        {
            IPackage pkg = computePackageInternal(env);
            setNameCategoryToPackage(pkg);
            return;
        }
        catch(IllegalNameException illegalnameexception)
        {
            throw new IllegalNameException(this, env, "\"" + printToString(false).trim() + "\" was not found.");
        }
    }

    Field computeField(IEnv env)
        throws VerifyingException
    {
        Class classWithField = null;
        if(jjtGetNumChildren() == 0)
            throw new IllegalConstructException(this, env, "Illegal access to field outside class: " + name);
        IExpressionNode child = getChildExpression();
        if(child instanceof JNName)
        {
            int childCategory = ((JNName)child).getNameCategory(env);
            switch(childCategory)
            {
            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
                classWithField = EvalMethods.getType(child, env);
                break;

            case 5: // '\005'
                classWithField = ((JNName)child).getNamedClass();
                break;

            default:
                throw new IllegalNameException(this, env, "Invalid class or expression for field access:  " + ((JNName)child).getNameCategoryName() + " " + child.printToString(false));
            }
        } else
        {
            classWithField = EvalMethods.getType(child, env);
        }
        if(classWithField == null)
            throw new IllegalTypeException(this, env, "Null has no fields.");
        Field field;
        try
        {
            field = computeAccessibleField(name, classWithField, env);
        }
        catch(NoSuchFieldException nosuchfieldexception)
        {
            throw new IllegalNameException(this, env, TypeMethods.toSourceString(classWithField) + " has no field named " + name);
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            throw new IllegalNameException(this, env, "Field " + classWithField.getName() + "." + name + " is inaccessible");
        }
        catch(SecurityException e)
        {
            throw new IllegalNameException(this, env, e.toString());
        }
        return field;
    }

    static Field computeAccessibleField(String name, Class classWithField, IEnv env)
        throws NoSuchFieldException, IllegalAccessException
    {
        Field field = null;
        try
        {
            field = classWithField.getField(name);
        }
        catch(NoSuchFieldException nosuchfieldexception)
        {
            for(Class classDeclaringField = classWithField; classDeclaringField != null;)
                try
                {
                    field = classDeclaringField.getDeclaredField(name);
                    break;
                }
                catch(NoSuchFieldException nosuchfieldexception1)
                {
                    classDeclaringField = classDeclaringField.getSuperclass();
                }

        }
        if(field == null)
            throw new NoSuchFieldException(name);
        if(!AccessibilityMethods.isPublicToTopLevel(field))
            if(JevaES.isSuppressingAccessChecks(env) || AccessibilityMethods.isAccessibleFromPackage(field, env.lookupEnclosingPackage().getFullName()))
                field.setAccessible(true);
            else
                throw new IllegalAccessException(field.toString());
        return field;
    }

    private Class computeClassOrInterface(IEnv env)
        throws VerifyingException
    {
        Class classOrInterface = null;
        if(jjtGetNumChildren() == 0)
            try
            {
                IEnv.ClassResult result = env.lookupClass(name);
                classOrInterface = result != null ? result.foundClass : null;
            }
            catch(IllegalArgumentException e)
            {
                throw new IllegalNameException(this, env, e.getMessage());
            }
        else
            try
            {
                JNName prefixName = (JNName)getChildExpression();
                int prefixCategory = prefixName.getNameCategory(env);
label0:
                switch(prefixCategory)
                {
                default:
                    break;

                case 6: // '\006'
                    boolean cacheFailures = JevaES.isSuppressingPackageNameChecks(env);
                    classOrInterface = prefixName.getNamedPackage().getClass(name, cacheFailures);
                    break;

                case 5: // '\005'
                    try
                    {
                        Class prefixClass = prefixName.getNamedClass();
                        Class publicClassesOrInterfaces[] = prefixClass.getClasses();
                        for(int i = 0; i < publicClassesOrInterfaces.length; i++)
                        {
                            String fullName = publicClassesOrInterfaces[i].getName();
                            String simpleName = fullName.substring(fullName.lastIndexOf('.') + 1);
                            if(name.equals(simpleName))
                            {
                                classOrInterface = publicClassesOrInterfaces[i];
                                break label0;
                            }
                        }

                        for(Class declaringClass = prefixClass; declaringClass != null; declaringClass = declaringClass.getSuperclass())
                        {
                            Class declaredClassesOrInterfaces[] = declaringClass.getDeclaredClasses();
                            for(int i = 0; i < declaredClassesOrInterfaces.length; i++)
                            {
                                String fullName = declaredClassesOrInterfaces[i].getName();
                                String simpleName = fullName.substring(fullName.lastIndexOf('.') + 1);
                                if(name.equals(simpleName))
                                {
                                    classOrInterface = declaredClassesOrInterfaces[i];
                                    break label0;
                                }
                            }

                        }

                        break;
                    }
                    catch(SecurityException securityexception) { }
                    break;
                }
            }
            catch(ClassCastException classcastexception) { }
        if(classOrInterface == null)
            throw new IllegalNameException(this, env, "No such class or interface");
        if(!AccessibilityMethods.isPublicToTopLevel(classOrInterface) && !JevaES.isSuppressingAccessChecks(env) && !AccessibilityMethods.isAccessibleFromPackage(classOrInterface, env.lookupEnclosingPackage().getFullName()))
            throw new IllegalNameException(this, env, "Inaccessible class or interface");
        else
            return classOrInterface;
    }

    public IPackage computePackage(IEnv env)
        throws VerifyingException
    {
        if(categoryIsAmbiguous())
            reclassifyName(env);
        if(6 == category)
            return getNamedPackage();
        else
            throw new IllegalNameException(this, env, "Name " + getFullPackageOrClassName(env) + " already classified as a " + getNameCategoryName());
    }

    private IPackage computePackageInternal(IEnv env)
        throws VerifyingException
    {
        if(name != null)
        {
            if(0 == jjtGetNumChildren())
                try
                {
                    return env.lookupPackage(name);
                }
                catch(IllegalArgumentException e)
                {
                    throw new IllegalNameException(this, env, e.toString());
                }
            if(getChild() instanceof JNName)
            {
                IPackage parentPkg = computeParentPackageInternal(env);
                try
                {
                    return parentPkg.getIPackage(name, JevaES.isSuppressingPackageNameChecks(env));
                }
                catch(IllegalArgumentException e)
                {
                    throw new IllegalNameException(this, env, e.toString());
                }
            } else
            {
                throw JevaNode.shouldNotHappen("package name suffix not a name");
            }
        } else
        {
            throw JevaNode.shouldNotHappen("null name");
        }
    }

    public IPackage computeParentPackage(IEnv env)
        throws VerifyingException
    {
        if(categoryIsAmbiguous())
            reclassifyName(env);
        if(6 == category || 5 == category)
            return computeParentPackageInternal(env);
        else
            throw JevaNode.shouldNotHappen("Name " + getFullPackageOrClassName(env) + " already classified as a " + getNameCategoryName());
    }

    private IPackage computeParentPackageInternal(IEnv env)
        throws VerifyingException
    {
        if(0 == jjtGetNumChildren())
            return env.lookupPackage(null);
        if(getChild() instanceof JNName)
            return getChildName().computePackage(env);
        else
            throw JevaNode.shouldNotHappen("suffix of package or class name not a name");
    }

    public String getFullNameAndCurrentCategory()
    {
        return "(" + getNameCategoryName() + ") " + printToString(false);
    }

    public String getNameCategoryName()
    {
        switch(category)
        {
        case 0: // '\0'
            return "AMBIGUOUS_NAME";

        case 1: // '\001'
            return "AMBIGUOUS_EXPRESSION_NAME";

        case 2: // '\002'
            return "VARIABLE_NAME";

        case 3: // '\003'
            return "FIELD_NAME";

        case 4: // '\004'
            return "ARRAY_FIELD_NAME";

        case 5: // '\005'
            return "TYPE_NAME";

        case 6: // '\006'
            return "PACKAGE_NAME";

        case 7: // '\007'
            return "SPECIAL_NAME_THIS";

        case 8: // '\b'
            return "SPECIAL_NAME_SUPER";

        case 9: // '\t'
            return "SPECIAL_NAME_CLASS";
        }
        throw JevaNode.shouldNotHappen("JNName: Invalid category " + category);
    }

    String name;
    private int category;
    private Object fieldClassOrPkg;
    public static final int AMBIGUOUS_NAME = 0;
    public static final int AMBIGUOUS_EXPRESSION_NAME = 1;
    public static final int VARIABLE_NAME = 2;
    public static final int FIELD_NAME = 3;
    public static final int ARRAY_FIELD_NAME = 4;
    public static final int TYPE_NAME = 5;
    public static final int PACKAGE_NAME = 6;
    public static final int SPECIAL_NAME_THIS = 7;
    public static final int SPECIAL_NAME_SUPER = 8;
    public static final int SPECIAL_NAME_CLASS = 9;
}
