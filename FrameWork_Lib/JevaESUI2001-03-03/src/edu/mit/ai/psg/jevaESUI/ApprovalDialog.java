// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaESUI/JevaESGUIListener.java

package edu.mit.ai.psg.jevaESUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Referenced classes of package edu.mit.ai.psg.jevaESUI:
//            WaitUntilResultListener, JevaESUI

class ApprovalDialog extends Dialog
    implements ActionListener
{

    public static boolean popup(Component parent, String title, String text)
    {
        for(; !(parent instanceof Frame); parent = parent.getParent());
        ApprovalDialog dialog = new ApprovalDialog((Frame)parent, title, text);
        dialog.show();
        try
        {
            boolean flag1;
            try
            {
                Object result = dialog.waitListener.waitUntilResult();
                boolean flag = Boolean.TRUE.equals(result);
                return flag;
            }
            catch(Throwable throwable)
            {
                flag1 = false;
            }
            return flag1;
        }
        finally
        {
            dialog.setVisible(false);
        }
    }

    public ApprovalDialog(Frame parent, String title, String text)
    {
        super(parent, title);
        FontMetrics fontMetrics = getFontMetrics(getFont());
        char chars[] = (title + "      \n" + text + "\n").toCharArray();
        int rows = -1;
        int columns = 20;
        int col = 0;
        int maxWidth = 0;
        for(int i = 0; i < chars.length; i++)
            if('\n' == chars[i])
            {
                rows++;
                maxWidth = Math.max(maxWidth, fontMetrics.charsWidth(chars, i - col, col - 1));
                columns = Math.max(columns, col);
                col = 0;
            } else
            {
                col++;
            }

        int maxHeight = rows * fontMetrics.getHeight();
        TextArea textArea = new TextArea(text, rows, columns, 3);
        textArea.setEditable(false);
        add(textArea, "Center");
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());
        Button okButton = new Button(OKCommand);
        okButton.addActionListener(this);
        buttonPanel.add(okButton);
        Button cancelButton = new Button(CancelCommand);
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);
        add(buttonPanel, "South");
        addWindowListener(waitListener);
        setSize(maxWidth + 2 * fontMetrics.getMaxAdvance(), maxHeight + 4 * fontMetrics.getHeight());
    }

    public synchronized void actionPerformed(ActionEvent e)
    {
        if(OKCommand.equals(e.getActionCommand()))
            waitListener.setResult(Boolean.TRUE);
        else
        if(CancelCommand.equals(e.getActionCommand()))
            waitListener.setResult(null);
    }

    public final WaitUntilResultListener waitListener = new WaitUntilResultListener(this);
    static final String rkOk = "OK";
    static final String rkCancel = "Cancel";
    static final String OKCommand = JevaESUI.getJevaESUIBundleString("OK");
    static final String CancelCommand = JevaESUI.getJevaESUIBundleString("Cancel");

}
