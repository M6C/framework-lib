// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/JevaESGUIListener.java

package edu.mit.ai.psg.jevaESUI;

import edu.mit.ai.psg.jevaES.*;
import edu.mit.ai.psg.jexa.Jexa;
import edu.mit.ai.psg.strings.Stringify;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.EventObject;
import java.util.Vector;

// Referenced classes of package edu.mit.ai.psg.jevaESUI:
//            WaitUntilResultListener, JevaESGUI, JevaESUI, ApprovalDialog

class JevaESGUIListener
    implements ActionListener, Runnable
{

    JevaESGUIListener(TextComponent initInputText, PrintWriter out, PrintWriter err, Component resultPane, Container container, String title, IEnv initEnv)
    {
        results = new Vector();
        inputText = initInputText;
        outWriter = out;
        errWriter = err;
        this.resultPane = resultPane;
        this.container = container;
        this.title = title;
        env = JevaES.extendEnv(true, java.util.Vector.class, "localResults", results, initEnv);
    }

    public Object awaitResult()
        throws Throwable
    {
        if(waitListener == null)
            try
            {
                Container window;
                for(window = container; window != null && !(window instanceof Window); window = window.getParent());
                if(window == null)
                    throw new Error("No containing java.awt.Window: " + container);
                waitListener = new WaitUntilResultListener((Window)window);
                Object obj = waitListener.waitUntilResult();
                return obj;
            }
            finally
            {
                waitListener = null;
            }
        else
            throw new IllegalStateException("Already has waiting thread.");
    }

    public synchronized void actionPerformed(ActionEvent e)
    {
        if(null == processingThread)
        {
            processingThread = new Thread(this);
            eventToProcess = e;
            processingThread.start();
        } else
        if(e.getActionCommand().equals(JevaESGUI.StopCommand))
        {
            Thread pThread = processingThread;
            if(pThread != null)
                pThread.stop();
        }
    }

    public void run()
    {
        Cursor oldContainerCursor = container.getCursor();
        Cursor oldResultCursor = resultPane.getCursor();
        Cursor oldInputCursor = inputText.getCursor();
        Button sourceButton = (Button)eventToProcess.getSource();
        String oldLabel = sourceButton.getLabel();
        try
        {
            container.setCursor(waitCursor);
            resultPane.setCursor(waitCursor);
            inputText.setCursor(waitCursor);
            sourceButton.setLabel(JevaESGUI.StopCommand);
            String command = eventToProcess.getActionCommand();
            if(command.equals(JevaESGUI.EvaluateCommand))
                performParseOrEvaluate(true);
            else
            if(command.equals(JevaESGUI.ParseCommand))
                performParseOrEvaluate(false);
            else
            if(command.equals(JevaESGUI.ExamineResultCommand))
                performExam();
        }
        finally
        {
            container.setCursor(oldContainerCursor);
            resultPane.setCursor(oldResultCursor);
            inputText.setCursor(oldInputCursor);
            sourceButton.setLabel(oldLabel);
            eventToProcess = null;
            processingThread = null;
        }
    }

    void performParseOrEvaluate(boolean evaluate)
    {
label0:
        {
            String text = inputText.getText().trim();
            if(text.equals(""))
            {
                outWriter.println(JevaESUI.getJevaESUIBundleString("NothingToParse"));
                break label0;
            }
            int nextIndex = results.size();
            int endParsePos = 0;
            try
            {
                try
                {
                    outWriter.print("\n" + MessageFormat.format(evaluate ? EvalResultLabelFormat : ParseResultLabelFormat, new Object[] {
                        new Integer(nextIndex)
                    }));
                    outWriter.flush();
                    Object result = null;
                    try
                    {
                        edu.mit.ai.psg.jevaES.IStatementNode stmt = JevaES.parseStringStatement(text);
                        endParsePos = JevaESUI.calculatePositionInString(stmt.getEndToken(), text);
                        outWriter.println(text.substring(0, endParsePos).trim());
                        if(evaluate)
                            env = EvalMethods.evaluate(stmt, env);
                        else
                            result = stmt;
                    }
                    catch(ParseException stmtExc)
                    {
                        try
                        {
                            edu.mit.ai.psg.jevaES.IExpressionNode expr = JevaES.parseStringExpression(text);
                            endParsePos = JevaESUI.calculatePositionInString(expr.getEndToken(), text);
                            outWriter.println(text.substring(0, endParsePos).trim());
                            if(evaluate)
                            {
                                result = EvalMethods.evaluate(expr, env);
                                outWriter.println(Stringify.printToString(result));
                            } else
                            {
                                result = expr;
                            }
                        }
                        catch(ParseException exprExc)
                        {
                            int endStmtParsePos = JevaESUI.calculatePositionInString(stmtExc.currentToken.next, text);
                            int endExprParsePos = JevaESUI.calculatePositionInString(exprExc.currentToken.next, text);
                            int errPos = Math.max(endStmtParsePos, endExprParsePos);
                            ParseException errExc = errPos != endStmtParsePos ? exprExc : stmtExc;
                            Token errToken = errExc.currentToken.next;
                            outWriter.println(text.substring(0, errPos) + errToken.image);
                            outWriter.println(JevaESUI.formatParseException(errExc));
                            selectText(text, errPos, errPos + errToken.image.length());
                            break label0;
                        }
                    }
                    if(!evaluate)
                        outWriter.println(Stringify.printToString(result));
                    results.addElement(result);
                    clearParsedText(text, endParsePos);
                }
                catch(VerifyingException termExc)
                {
                    results.addElement(termExc);
                    outWriter.println(termExc.toString());
                    int beginErrPos = JevaESUI.calculatePositionInString(termExc.node.getBeginToken(), text);
                    int endErrPos = JevaESUI.calculatePositionInString(termExc.node.getEndToken(), text);
                    selectText(text, beginErrPos, endErrPos);
                }
                catch(ReturnException returnEx)
                {
                    Object value = returnEx.getValue();
                    results.addElement(value);
                    if(null != waitListener && null != env.lookupReturnType() && ApprovalDialog.popup(container, JevaESUI.formatWithJevaESUIBundleString("ApproveReturnFromFormat", title), JevaESUI.formatWithJevaESUIBundleString("Return?Format", Void.TYPE != env.lookupReturnType() ? value : "")))
                    {
                        waitListener.setResult(value);
                    } else
                    {
                        outWriter.println(Stringify.printToString(value));
                        clearParsedText(text, endParsePos);
                    }
                }
                catch(ThrowException throwEx)
                {
                    Throwable thrown = throwEx.getThrown();
                    results.addElement(thrown);
                    if(null != waitListener && null != env.lookupReturnType() && ApprovalDialog.popup(container, JevaESUI.formatWithJevaESUIBundleString("ApproveThrowFromFormat", title), JevaESUI.formatWithJevaESUIBundleString("Throw?Format", thrown)))
                    {
                        waitListener.setThrown(thrown);
                    } else
                    {
                        outWriter.println(text.substring(0, endParsePos));
                        outWriter.println(JevaESUI.getJevaESUIBundleString("UncaughtException") + thrown);
                        selectText(text, 0, endParsePos);
                    }
                }
            }
            catch(Throwable thrown)
            {
                outWriter.println(text.substring(0, endParsePos));
                results.addElement(thrown);
                outWriter.println(JevaESUI.getJevaESUIBundleString("InternalError") + thrown.toString());
                selectText(text, 0, endParsePos);
            }
        }
        outWriter.flush();
        errWriter.flush();
        inputText.requestFocus();
    }

    protected void clearParsedText(String text, int endParsePos)
    {
        if(endParsePos == text.length())
        {
            inputText.setText("");
        } else
        {
            inputText.setText(text.substring(endParsePos).trim());
            inputText.selectAll();
        }
    }

    protected void selectText(String text, int startPos, int endPos)
    {
        inputText.setText(text);
        inputText.setSelectionStart(startPos);
        inputText.setSelectionEnd(endPos);
    }

    void performExam()
    {
        if(0 < results.size())
            Jexa.examine(results.lastElement());
        else
            outWriter.println(JevaESUI.getJevaESUIBundleString("NoResultToExamine"));
    }

    Container container;
    String title;
    WaitUntilResultListener waitListener;
    Component resultPane;
    TextComponent inputText;
    PrintWriter outWriter;
    PrintWriter errWriter;
    IEnv env;
    Vector results;
    ActionEvent eventToProcess;
    Thread processingThread;
    static final String rkNothingToParse = "NothingToParse";
    static final String rkEvalResultLabelFormat = "EvalResultLabelFormat";
    static final String rkParseResultLabelFormat = "ParseResultLabelFormat";
    static final String rkApproveReturnFromFormat = "ApproveReturnFromFormat";
    static final String rkReturnOkFormat = "Return?Format";
    static final String rkApproveThrowFromFormat = "ApproveThrowFromFormat";
    static final String rkThrowOkFormat = "Throw?Format";
    static final String rkNoResultToExamine = "NoResultToExamine";
    static Cursor waitCursor = Cursor.getPredefinedCursor(3);
    static String EvalResultLabelFormat = JevaESUI.getJevaESUIBundleString("EvalResultLabelFormat");
    static String ParseResultLabelFormat = JevaESUI.getJevaESUIBundleString("ParseResultLabelFormat");

}
