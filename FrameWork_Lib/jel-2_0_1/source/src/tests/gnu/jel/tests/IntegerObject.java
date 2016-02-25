/*-*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 2 -*-
 * $Id: IntegerObject.java 316 2001-02-15 00:07:50Z metlov $
 *
 * This file is part of the Java Expressions Library (JEL).
 *   For more information about JEL visit :
 *    http://galaxy.fzu.cz/JEL/
 *
 * (c) 1998,1999 by Konstantin Metlov(metlov@fzu.cz);
 *
 * JEL is Distributed under the terms of GNU General Public License.
 *    This code comes with ABSOLUTELY NO WARRANTY.
 *  For license details see COPYING file in this directory.
 */

package gnu.jel.tests;

public class IntegerObject implements gnu.jel.reflect.Integer {
  int val;
  
  public IntegerObject(int val) {
	this.val=val;
  };
  
  public int getValue() {
	return val;
  };

  public int aMethod() {
    return 1;
  };

  public String getStringProperty(String name) {
    return name;
  };

};

