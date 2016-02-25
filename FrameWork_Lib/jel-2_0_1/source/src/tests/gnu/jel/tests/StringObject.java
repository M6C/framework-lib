/*-*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 2 -*-
 * $Id: StringObject.java 311 2001-01-21 22:38:46Z metlov $
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

public class StringObject implements gnu.jel.reflect.String {
  String val;
  
  public StringObject(String val) {
	this.val=val;
  };
  
  public int aMethod() {
    return 1;
  };

  public boolean equals(Object obj) {
    if (!(obj instanceof StringObject)) return false;
    return val.equals(((StringObject)obj).val);
  };

  public String toString() {
    return val;
  };
};

