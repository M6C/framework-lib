/*-*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 2 -*-
 * $Id: CharacterObject.java 306 2000-11-12 14:59:34Z metlov $
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

public class CharacterObject implements gnu.jel.reflect.Character {
  char val;
  
  public CharacterObject(char val) {
	this.val=val;
  };
  
  public char getValue() {
	return val;
  };

  public int aMethod() {
    return 1;
  };

};
