// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/JevaESGUI.java

package edu.mit.ai.psg.jevaESUI;

import edu.mit.ai.psg.jevaES.IEnv;
import edu.mit.ai.psg.jevaES.JevaES;
import edu.mit.ai.psg.jexa.Jexa;
import edu.mit.ai.psg.ui.outliner.OutlineMaker;
import edu.mit.ai.psg.ui.outliner.Outliner;
import edu.mit.ai.psg.ui.patches.FrameableApplet;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;

// Referenced classes of package edu.mit.ai.psg.jevaESUI:
//            JevaESGUIPanel, JevaESGUIFrame, TextAreaOutputStream, JevaESGUIListener, 
//            NodeRenderer, JevaESUI

public class JevaESGUI extends FrameableApplet
{

    public static void main(String arguments[])
    {
        int options = 4;
        String title = null;
        String fontName = defaultFont.getName();
        int fontSize = defaultFont.getSize();
        String initStatements = null;
        String herald = JevaESUI.defaultHerald();
        String message = "";
        if(arguments != null)
        {
            for(int i = 0; i < arguments.length; i++)
                if("-noredirect".equals(arguments[i]))
                    options &= -5;
                else
                if("-title".equals(arguments[i]) && i + 1 < arguments.length)
                    title = arguments[++i];
                else
                if("-font".equals(arguments[i]) && i + 1 < arguments.length)
                {
                    try
                    {
                        fontSize = Integer.parseInt(arguments[++i]);
                    }
                    catch(NumberFormatException numberformatexception)
                    {
                        fontName = arguments[i];
                        if(i + 1 < arguments.length)
                            try
                            {
                                fontSize = Integer.parseInt(arguments[++i]);
                            }
                            catch(NumberFormatException numberformatexception1)
                            {
                                i--;
                            }
                    }
                    defaultFont = new Font(fontName, 0, fontSize);
                } else
                if("-initStatements".equals(arguments[i]) && i + 1 < arguments.length)
                    initStatements = arguments[++i];
                else
                if("-message".equals(arguments[i]) && i + 1 < arguments.length)
                    message = message + JevaESUI.convertNewlines(arguments[++i]);
                else
                if("-herald".equals(arguments[i]) && i + 1 < arguments.length)
                {
                    herald = JevaESUI.convertNewlines(arguments[++i]);
                } else
                {
                    System.out.println(MessageFormat.format(JevaESUI.getJevaESUIBundleString("JevaESGUIUsageFormat"), new Object[] {
                        fontName, new Integer(fontSize)
                    }));
                    System.exit(1);
                }

        }
        System.out.println(JevaESUI.getJevaESUIBundleString("startingWindow"));
        replTop(options, title, herald + message, initStatements);
        System.exit(0);
    }

    protected Dimension getMinSizeInPlace()
    {
        return minSizeInPlace;
    }

    protected Dimension getMinSizeAsButton()
    {
        return minSizeAsButton;
    }

    public String getAppletTitle()
    {
        String title = getParameter("title");
        return title != null ? "JevaES: " + title : JevaESUI.defaultTitle();
    }

    public void initPanelInPlaceApplet()
    {
        super.initPanelInPlaceApplet();
        int options = 1;
        String herald = JevaESUI.convertNewlines(getParameter("herald"));
        if("".equals(herald))
            herald = JevaESUI.defaultHerald();
        String message = JevaESUI.convertNewlines(getParameter("message"));
        add(new JevaESGUIPanel(options, getAppletTitle(), herald + message, null, JevaESUI.ThrowsThrowableDeclaration, bindAppletEnv(options), getParameter("initStatements")), "Center");
    }

    protected String getButtonStartText()
    {
        return JevaESUI.getJevaESUIBundleString("JevaESGUIAppletStart");
    }

    protected String getButtonStartingText()
    {
        return JevaESUI.getJevaESUIBundleString("JevaESGUIAppletStarting");
    }

    protected String getButtonStartedText()
    {
        return JevaESUI.getJevaESUIBundleString("JevaESGUIAppletStarted");
    }

    public Frame initFrameApplet()
    {
        int options = 1;
        String herald = JevaESUI.convertNewlines(getParameter("herald"));
        if("".equals(herald))
            herald = JevaESUI.defaultHerald();
        String message = JevaESUI.convertNewlines(getParameter("message"));
        Frame window = new JevaESGUIFrame(options, getAppletTitle(), herald + message, null, JevaESUI.ThrowsThrowableDeclaration, bindAppletEnv(options), getParameter("initStatements"));
        initAppletColors(window);
        window.show();
        return window;
    }

    protected IEnv bindAppletEnv(int options)
    {
        String bindApplet = getParameter("bindLocalApplet");
        if(bindApplet != null && !"".equals(bindApplet) && !"false".equalsIgnoreCase(bindApplet))
            return JevaES.extendEnv(true, getClass(), "localApplet", this, JevaES.makeDefaultEnv(options));
        else
            return null;
    }

    public String[][] getParameterInfo()
    {
        Vector parameterInfo = new Vector();
        parameterInfo.addAll(Arrays.asList(super.getParameterInfo()));
        parameterInfo.addAll(Arrays.asList(new String[][] {
            new String[] {
                "message", "String", "initial message"
            }, new String[] {
                "herald", "String", "replacement herald"
            }, new String[] {
                "bindLocalApplet", "boolean", "whether to bind localApplet to this applet"
            }, new String[] {
                "initStatements", "s1; s2; ...", "initial java source statements to run, e.g., for imports"
            }
        }));
        String result[][] = new String[parameterInfo.size()][];
        System.arraycopy(((Object) (parameterInfo.toArray())), 0, result, 0, parameterInfo.size());
        return result;
    }

    public String getAppletInfo()
    {
        return JevaESUI.defaultHerald();
    }

    public static boolean isRedirectingStreams(int options)
    {
        return (4 & options) != 0;
    }

    public JevaESGUI()
    {
    }

    public static void replTop()
    {
        replTop(0, null, null);
    }

    public static void replTop(int options, String title, String initStatements)
    {
        replTop(options, title, null, initStatements);
    }

    public static void replTop(int options, String title, String initialMessage, String initStatements)
    {
        try
        {
            repl(options, title, initialMessage, null, JevaESUI.ThrowsThrowableDeclaration, null, initStatements);
        }
        catch(AWTException awtexception) { }
        catch(Throwable t)
        {
            t.printStackTrace();
            throw new Error("Shouldn't reach here: " + t);
        }
    }

    public static Object repl()
        throws Throwable, AWTException
    {
        return repl(0, JevaESUI.defaultTitle(), JevaESUI.defaultHerald(), java.lang.Object.class, JevaESUI.ThrowsThrowableDeclaration, null, null);
    }

    public static Object repl(int options, String title, String initialMessage, Class returnClass, Class throwsClasses[], IEnv env)
        throws Throwable, AWTException
    {
        return repl(options, title, initialMessage, returnClass, throwsClasses, env, null);
    }

    public static Object repl(int options, String title, String initialMessage, Class returnClass, Class throwsClasses[], IEnv env, String initStatements)
        throws Throwable, AWTException
    {
        Frame window = new JevaESGUIFrame(options, title, initialMessage, returnClass, throwsClasses, env, initStatements);
        try
        {
            window.show();
            Object obj = awaitResult(window);
            return obj;
        }
        finally
        {
            window.dispose();
        }
    }

    public static Object awaitResult(Container container)
        throws Throwable, AWTException
    {
        if(container instanceof JevaESGUIFrame)
            return ((JevaESGUIFrame)container).awaitResult();
        if(container instanceof JevaESGUIPanel)
            return ((JevaESGUIPanel)container).awaitResult();
        else
            throw new IllegalArgumentException("not JevaESGUI Frame or Panel");
    }

    static void setup(Container container, int options, String title, String initialMessage, Class returnClass, Class throwsClasses[], IEnv env, String initStatements)
    {
        ensureJexaInitialized();
        StringWriter initErrsWriter = new StringWriter();
        PrintWriter initErrs = new PrintWriter(initErrsWriter);
        if(!(container instanceof JevaESGUIFrame) && !(container instanceof JevaESGUIPanel))
            throw new IllegalArgumentException("Not JevaESGUI Frame or Panel: " + container);
        container.setFont(defaultFont);
        if(title == null)
            title = JevaESUI.defaultTitle();
        if(initialMessage == null)
            initialMessage = JevaESUI.defaultHerald();
        env = JevaESUI.initialEnv(env, options, returnClass, throwsClasses, initStatements, initErrs);
        TextArea resultText = new TextArea(22, 72);
        resultText.setEditable(false);
        container.add(resultText, "Center");
        java.io.OutputStream textAreaOutStream = new TextAreaOutputStream(resultText);
        PrintWriter out = new PrintWriter(textAreaOutStream, true);
        java.io.OutputStream textAreaErrStream = new TextAreaOutputStream(resultText);
        PrintWriter err = new PrintWriter(textAreaErrStream, true);
        env = JevaES.extendEnv(true, java.io.PrintWriter.class, "localOut", out, JevaES.extendEnv(true, java.io.PrintWriter.class, "localErr", err, env));
        if(isRedirectingStreams(options))
        {
            try
            {
                System.setOut(new PrintStream(textAreaOutStream, true));
            }
            catch(SecurityException securityexception)
            {
                initErrs.println(MessageFormat.format(JevaESUI.getJevaESUIBundleString("WarningUnableToRedirectFormat"), new Object[] {
                    "System.out", "localOut"
                }));
            }
            try
            {
                System.setErr(new PrintStream(textAreaErrStream, true));
            }
            catch(SecurityException securityexception1)
            {
                initErrs.println(MessageFormat.format(JevaESUI.getJevaESUIBundleString("WarningUnableToRedirectFormat"), new Object[] {
                    "System.err", "localErr"
                }));
            }
        } else
        {
            initErrs.println("[" + MessageFormat.format(JevaESUI.getJevaESUIBundleString("NotRedirectingFormat"), new Object[] {
                "System.out, System.err", "localOut, localErr"
            }) + "]");
        }
        Panel inputPanel = new Panel();
        inputPanel.setLayout(new BorderLayout());
        TextArea inputText = new TextArea(5, 72);
        inputPanel.add(inputText, "North");
        JevaESGUIListener actionListener = new JevaESGUIListener(inputText, out, err, resultText, container, title, env);
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(1, 2, 2));
        buttonPanel.setBackground(SystemColor.control);
        buttonPanel.setFont(new Font("Dialog", 0, (defaultFont.getSize() * 5) / 4));
        Button evalButton = new Button(EvaluateCommand);
        evalButton.addActionListener(actionListener);
        buttonPanel.add(evalButton);
        Button parseButton = new Button(ParseCommand);
        parseButton.addActionListener(actionListener);
        buttonPanel.add(parseButton);
        Button examButton = new Button(ExamineResultCommand);
        examButton.addActionListener(actionListener);
        buttonPanel.add(examButton);
        inputPanel.add(buttonPanel, "South");
        container.add(inputPanel, "South");
        if(container instanceof JevaESGUIFrame)
            ((JevaESGUIFrame)container).initializeListener(actionListener);
        else
        if(container instanceof JevaESGUIPanel)
            ((JevaESGUIPanel)container).initializeListener(actionListener);
        else
            throw new Error("unknown IJevaESGUI container " + container);
        out.println(initialMessage);
        if(initErrsWriter.getBuffer().length() > 0)
            out.println(initErrsWriter + "\n");
    }

    static void ensureJexaInitialized()
    {
        Jexa.ensureInitialized();
        if(!(Outliner.outlineMaker.getExactRenderer(edu.mit.ai.psg.jevaES.Node.class) instanceof NodeRenderer))
            NodeRenderer.initialize(Outliner.outlineMaker);
    }

    protected static Dimension minSizeInPlace = new Dimension(300, 300);
    protected static Dimension minSizeAsButton = new Dimension(200, 30);
    protected static final String pkHerald = "herald";
    protected static final String pkMessage = "message";
    protected static final String pkBindLocalApplet = "bindLocalApplet";
    protected static final String pkInitStatements = "initStatements";
    static final String rkJevaESGUIUsageFormat = "JevaESGUIUsageFormat";
    static final String rkStartingWindow = "startingWindow";
    static final String rkJevaESGUIAppletStart = "JevaESGUIAppletStart";
    static final String rkJevaESGUIAppletStarting = "JevaESGUIAppletStarting";
    static final String rkJevaESGUIAppletStarted = "JevaESGUIAppletStarted";
    static final String rkEvaluateCommand = "EvaluateCommand";
    static final String rkParseCommand = "ParseCommand";
    static final String rkExamineResultCommand = "ExamineResultCommand";
    static final String rkStopCommand = "StopCommand";
    static final String rkWarningUnableToRedirectFormat = "WarningUnableToRedirectFormat";
    static final String rkNotRedirectingFormat = "NotRedirectingFormat";
    public static PrintStream systemOut;
    public static PrintStream systemErr;
    public static Font defaultFont;
    public static final int redirectStreamsBit = 4;
    public static final int leftmostOptionBit = 4;
    static final String EvaluateCommand = JevaESUI.getJevaESUIBundleString("EvaluateCommand");
    static final String ParseCommand = JevaESUI.getJevaESUIBundleString("ParseCommand");
    static final String ExamineResultCommand = JevaESUI.getJevaESUIBundleString("ExamineResultCommand");
    static final String StopCommand = JevaESUI.getJevaESUIBundleString("StopCommand");

    static 
    {
        systemOut = System.out;
        systemErr = System.err;
        Map attributes = new HashMap(1);
        attributes.put(TextAttribute.FAMILY, "Monospaced");
        defaultFont = Font.getFont(attributes);
    }
}
