// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   utilities/ReferenceCleaner.java

package edu.mit.ai.psg.utilities;

import java.lang.ref.ReferenceQueue;

public class ReferenceCleaner
{
    public static interface Cleanable
    {

        public abstract void cleanUp();
    }


    public ReferenceCleaner()
    {
    }

    public static final ReferenceQueue referenceQueue = new ReferenceQueue();

    static 
    {
        (new Thread(new Runnable() {

            public void run()
            {
                while(true) 
                    try
                    {
                        Object ref;
                        do
                            ref = ReferenceCleaner.referenceQueue.remove();
                        while(!(ref instanceof Cleanable));
                        ((Cleanable)ref).cleanUp();
                    }
                    catch(InterruptedException interruptedexception) { }
            }

        }
, "ReferenceCleaner")).start();
    }
}
