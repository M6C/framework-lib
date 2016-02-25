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

public class IntegralPrimitiveOPsTest extends TestingUtils {
  public IntegralPrimitiveOPsTest(String name) {
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
    for(int k=0;k<2;k++) {
      String[][] prefixes={{"val","val","val"},{"arr","arr","arr"}};
      String[][] suffixes={{"","Obj","JELObj"},{"[0]","Obj[0]","JELObj[0]"}};
      
      testUnaryPrimitive(0,6,lib,rtp,-1,null,prefixes[k],suffixes[k]); // -
      testUnaryPrimitive(1,5,lib,rtp,0xFFFFFFFFFFFFFFFEL,null,
                         prefixes[k],suffixes[k]); // ~
      testUnaryPrimitive(2,1,lib,rtp,0,null,prefixes[k],suffixes[k]); // !
      
      testBinaryPrimitive(0,45,lib,rtp,2,null,prefixes[k],suffixes[k]); // +
      testBinaryPrimitive(1,45,lib,rtp,0,null,prefixes[k],suffixes[k]); // -
      testBinaryPrimitive(2,45,lib,rtp,1,null,prefixes[k],suffixes[k]); // *
      testBinaryPrimitive(3,45,lib,rtp,1,null,prefixes[k],suffixes[k]); // /
      testBinaryPrimitive(4,45,lib,rtp,0,null,prefixes[k],suffixes[k]); // %
      testBinaryPrimitive(5,26,lib,rtp,1,null,prefixes[k],suffixes[k]); // &
      testBinaryPrimitive(6,26,lib,rtp,1,null,prefixes[k],suffixes[k]); // |
      testBinaryPrimitive(7,26,lib,rtp,0,null,prefixes[k],suffixes[k]); // ^
      testBinaryPrimitive(8,46,lib,rtp,1,null,prefixes[k],suffixes[k]); // ==
      testBinaryPrimitive(9,46,lib,rtp,0,null,prefixes[k],suffixes[k]); // !=
      testBinaryPrimitive(10,45,lib,rtp,0,null,prefixes[k],suffixes[k]); // <
      testBinaryPrimitive(11,45,lib,rtp,1,null,prefixes[k],suffixes[k]); // >=
      testBinaryPrimitive(12,45,lib,rtp,0,null,prefixes[k],suffixes[k]); // >
      testBinaryPrimitive(13,45,lib,rtp,1,null,prefixes[k],suffixes[k]); // <=
      testBinaryPrimitive(14,25,lib,rtp,2,null,prefixes[k],suffixes[k]); // <<
      testBinaryPrimitive(15,25,lib,rtp,0,null,prefixes[k],suffixes[k]); // >>
      testBinaryPrimitive(16,25,lib,rtp,0,null,prefixes[k],suffixes[k]); // >>>
      testBinaryPrimitive(17,1,lib,rtp,1,null,prefixes[k],suffixes[k]); // &&
      testBinaryPrimitive(18,1,lib,rtp,1,null,prefixes[k],suffixes[k]); // ||
    };

  }
    
};
