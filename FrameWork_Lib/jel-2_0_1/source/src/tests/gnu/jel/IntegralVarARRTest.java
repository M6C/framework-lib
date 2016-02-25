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

public class IntegralVarARRTest extends TestingUtils {
  public IntegralVarARRTest(String name) {
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
    lib=new Library(staticLib,dynamicLib,null,null,null);
  }
  
  public void test1() throws Throwable {
    simExpression("5*xvar-66",new Double(25-66),Double.TYPE,rtp,lib,null);
  }

  public void test2() throws Throwable {
    simExpression("5*arr[1]-66",new Double(25-66),Double.TYPE,rtp,lib,null);
  }

  public void test3() throws Throwable {
    simExpression("5*(arrs[1]+arrs[2])-66",new Double(25-66),Double.TYPE,rtp,lib,null);
  }

  public void test4() throws Throwable {
    simExpression("5*(arrs[1]+arrs[(int)round(arr[0]-arrs[2])+1])-66",new Double(25-66),Double.TYPE,rtp,lib,null);
  }

  public void test5() throws Throwable {
    simExpression("arrDoubleJELObj1[0]+arrDoubleJELObj1[1]",
                   new Double(3.0), null, rtp, lib,null);
  }

  public void test6() throws Throwable {    
    simExpression("arrDoubleJELObj", vp.arrDoubleJELObj, null, rtp, lib,null);
  }
  
};
