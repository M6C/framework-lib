/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 2 -*-
 * $Id: $
 * (c) 1998 -- 2007 by Konstantin L. Metlov
 */
package gnu.jel;
import junit.framework.TestCase;
import java.io.StringReader;
import java.lang.reflect.Member;

public class LibraryDotOPTest extends TestCase {
  public LibraryDotOPTest(String name) {
    super(name);
  }

  Library ldot;


  public void setUp() throws Exception {
    Class[] stl=new Class[1];
    Class[] dynl=new Class[1];
    Class[] dotl=new Class[2];
    stl[0]=Class.forName("java.lang.Math");
    dynl[0]=Class.forName("java.util.Random");
    dotl[0]=Class.forName("java.util.Hashtable");
    dotl[1]=Class.forName("java.util.Vector");
    ldot=new Library(stl,dynl,dotl);
  }

  public void tearDown() throws Exception {
  }

  public void testHashtableSize() throws Exception {
    Class[] params=new Class[0];
    Class htable=Class.forName("java.util.Hashtable");
    Member mf=ldot.getMember(htable,"size",params);
    assertTrue((mf!=null) && 
               (mf.equals(htable.getMethod("size",params))));
  }
  
  public void testVectorSize() throws Exception {
    Class[] params=new Class[0];
    Class vctr=Class.forName("java.util.Vector");
    Member mf=ldot.getMember(vctr,"size",params);
    assertTrue((mf!=null) && 
               (mf.equals(vctr.getMethod("size",params))));
  }

  public void testSizeNoLeak() throws Exception {
    Class[] params=new Class[0];
    try {
      Member mf=ldot.getMember(null,"size",params);
      assertTrue(false);
    } catch (CompilationException exc) {
      assertTrue(true);
    };
  }
 
}
