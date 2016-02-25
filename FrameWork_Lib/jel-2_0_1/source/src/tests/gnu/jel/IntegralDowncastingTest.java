/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 2 -*-
 * $Id: TestSuite.java,v 1.47 2004/03/16 15:51:25 metlov Exp $
 *
 * This file is part of the Java Expressions Library (JEL).
 *
 * (c) 1998 -- 2007 by Konstantin L. Metlov
 *
 * JEL is Distributed under the terms of GNU General Public License.
 *    This code comes with ABSOLUTELY NO WARRANTY.
 *  For license details see COPYING file in this directory.
 */

package gnu.jel;

import gnu.jel.tests.*;
import java.io.PrintStream;
import java.util.Stack;

public class IntegralDowncastingTest extends TestingUtils {
  public IntegralDowncastingTest(String name) {
    super(name);
  }

  Library lib;
  Object[] rtp;
  VariableProvider vp;
  Class[] staticLib;
  Class[] dynamicLib;
  java.util.HashMap<String,Class> allowedCasts;

  public void setUp() throws Exception {
    dynamicLib=new Class[1];
    rtp=new Object[1];
    vp=new VariableProvider();
    staticLib=new Class[2];
    staticLib[0]=Class.forName("java.lang.Math");
    // next line makes also static functions from VariablePrivider available
    staticLib[1]=vp.getClass();  
    vp.xvar=5.0;
    vp.strVar="strVar";
    rtp[0]=vp;
    dynamicLib[0]=vp.getClass();

    Class[] dotLib=new Class[5];
    dotLib[0]=Class.forName("java.lang.String");
    dotLib[1]=Class.forName("java.lang.Double");
    dotLib[2]=Class.forName("gnu.jel.reflect.Double");
    dotLib[3]=IntegerObject.class;
    dotLib[4]=DoubleObject.class;

    vp.addProperty("p1","p1value");
    vp.addProperty("p1.s1","p1s1value");
    vp.addProperty("p1.s2","p1s2value");
    vp.addProperty("p1.d1",VariableProvider.makeJELDoubleObject(1.0));
    vp.addProperty("p1.s2.ss1","p1s2ss1value");
    vp.addProperty("p1.b1t",VariableProvider.makeJELBooleanObject(true));
    vp.addProperty("p1.b1f",VariableProvider.makeJELBooleanObject(false));
    
    allowedCasts=new java.util.HashMap<String,Class>();
    allowedCasts.put("String",String.class);
    allowedCasts.put("Object",Object.class);
    allowedCasts.put("Double",java.lang.Double.class);
    lib=new Library(staticLib,dynamicLib,dotLib,vp,allowedCasts);
  }
  
  
  public void test1() throws Throwable {
      simExpression("(Double)((Object)valDoubleObj)",new Double(1.0),
                   null, rtp, lib,null);
  }
  public void test2() throws Throwable {
    simExpression("((String)((Object)\"abc\")).length",new Integer(3),
                   null, rtp, lib,null);
  }
  public void test3() throws Throwable {
    // as per Kumar Pandya's request
    allowedCasts.put("a1.a2.Strin",String.class);
    allowedCasts.put("a2.a3.Objec",Object.class);
    allowedCasts.put("a3.a4.Doubl",java.lang.Double.class);

    simExpression("(a3.a4.Doubl)((a2.a3.Objec)valDoubleObj)",new Double(1.0),
                   null, rtp, lib,null);
    simExpression("((a1.a2.Strin)((a2.a3.Objec)\"abc\")).length",new Integer(3),
                   null, rtp, lib,null);
  }

  public void test4() throws Throwable {
    // Test case for a bug submitted by Dave Ekhaus
    for(int i=0;i<8;i++) {
      Class[] newDynamicLib=new Class[dynamicLib.length+1];
      Object[] newRTP=new Object[rtp.length+1];
      System.arraycopy(dynamicLib,0,newDynamicLib,0,dynamicLib.length);
      System.arraycopy(rtp,0,newRTP,0,rtp.length);
      switch(i) {
      case 0:
        newRTP[newRTP.length-1]=VariableProvider.makeJELBooleanObject(false);
        break;
      case 1:
        newRTP[newRTP.length-1]=VariableProvider.makeJELByteObject((byte)55);
          break;
      case 2:
        newRTP[newRTP.length-1]=VariableProvider.makeJELCharacterObject(' ');
        break;
      case 3:
        newRTP[newRTP.length-1]=
          VariableProvider.makeJELShortObject((short)55);
          break;
      case 4:
        newRTP[newRTP.length-1]=VariableProvider.makeJELIntegerObject(55);
        break;
      case 5:
          newRTP[newRTP.length-1]=VariableProvider.makeJELLongObject(55);
          break;
      case 6:
        newRTP[newRTP.length-1]=VariableProvider.makeJELFloatObject(55);
        break;
      case 7:
        newRTP[newRTP.length-1]=VariableProvider.makeJELDoubleObject(55);
        break;
      default:
      };
        newDynamicLib[newDynamicLib.length-1]=
          newRTP[newRTP.length-1].getClass();
        
        Library lib1=new Library(staticLib,newDynamicLib,new Class[0],vp,null);
        simExpression("aMethod", new Integer(1),null,newRTP,lib1,null);
    };
  }

  public void test5() throws Throwable {
    // Check transmittance of unwrappable objects
    String[] typeNames={"Boolean","Byte","Character","Short","Integer",
                        "Long","Float","Double"};
    for(int k=0;k<8;k++) {
      String fname="val"+typeNames[k]+"JELObj";
      Object res=null;
      try {
        res=VariableProvider.class.getField(fname).get(vp);
        } catch(Exception e) {
          System.out.println(e);
          System.exit(1);
        };
      simExpression(fname,res,null,rtp,lib,null);
    };
  };

  public void test6() throws Throwable {
    simExpression("valString",new StringObject("strObj"),
                   null, rtp, lib, null);
  }

  public void test7() throws Throwable {
    simExpression("valString+\"_ttt\"","strObj_ttt", 
                   null, rtp, lib, null);
  }

  public void test8() throws Throwable {
    simExpression("\"ttt_\"+valString+\"_ttt\"","ttt_strObj_ttt", 
                   null, rtp, lib, null);
  }

  public void test9() throws Throwable {
    simExpression("append_ttt(\"strObj\")+\"_ttt\"","strObj_ttt_ttt",
                   null, rtp, lib, null);
  }

  public void test10() throws Throwable {
    simExpression("append_ttt(valString)+\"_ttt\"","strObj_ttt_ttt",
                   null, rtp, lib, null);
  }

  public void test11() throws Throwable {
    simExpression("methodOnInt(valInteger)",new Integer(1), 
                   null, rtp, lib, null);
  }

  public void test12() throws Throwable {
    simExpression("methodOnInt(valIntegerJELObj)",new Integer(2), 
                   null, rtp, lib, null);
  }
    
};
