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

public class IntegralReferencesWideningTest extends TestingUtils {
  public IntegralReferencesWideningTest(String name) {
    super(name);
  }

  Library lib;
  Object[] rtp;
  VariableProvider vp;

  public void setUp() throws Exception {
    Class[] dynamicLib=new Class[1];
    rtp=new Object[1];
    vp=new VariableProvider();
    Class[] staticLib=new Class[2];
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
    lib=new Library(staticLib,dynamicLib,dotLib,vp,null);

    vp.addProperty("p1","p1value");
    vp.addProperty("p1.s1","p1s1value");
    vp.addProperty("p1.s2","p1s2value");
    vp.addProperty("p1.d1",VariableProvider.makeJELDoubleObject(1.0));
    vp.addProperty("p1.s2.ss1","p1s2ss1value");
    vp.addProperty("p1.b1t",VariableProvider.makeJELBooleanObject(true));
    vp.addProperty("p1.b1f",VariableProvider.makeJELBooleanObject(false));
  }
  
  
  public void test1() throws Throwable {
    simExpression("convertNumberToInt(makeDoubleObject(5.0))",new Integer(5),
                  null, rtp, lib,null);
  }

  public void test2() throws Throwable {
    simExpression("convertNumberToInt(arrIntegerObj[0])",new Integer(1),
                   null, rtp, lib,null);
  }

  public void test3() throws Throwable {
    simExpression("convertNumberToDouble(arrIntegerObj[0])",
                   new Double(1.0),
                   null, rtp, lib,null);
  }

  public void test4() throws Throwable {
    simExpression("convertNumberToDouble(makeDoubleObject(5.0))",
                   new Double(5.0),
                   null, rtp, lib,null);
  }

  public void test5() throws Throwable {
    simExpression("convertNumberToDouble(makeDoubleObject(5.0))",
                   new Double(5.0),
                   null, rtp, lib,null);
  }

  public void test6() throws Throwable {
    simExpression("addNumbersDbl(makeDoubleObject(5.0),arrIntegerObj[0])",
                   new Double(6.0),
                   null, rtp, lib,null);
  }
  public void test7() throws Throwable {
    simExpression("addNumbersInt(makeDoubleObject(5.0),arrIntegerObj[0])",
                   new Integer(6),
                  null, rtp, lib,null);
  }
  public void test8() throws Throwable {
    simExpression("isNullDouble(getDoubleNull())",
                   Boolean.TRUE,
                   null, rtp, lib,null);
  }

  public void test9() throws Throwable {
    simExpression("isNullDouble(makeDoubleObject(5.0))",
                   Boolean.FALSE,
                   null, rtp, lib,null);
  }


};
