/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 2 -*-
 * $Id: $
 * (c) 1998 -- 2007 by Konstantin L. Metlov
 */
package gnu.jel;
import junit.framework.TestCase;
import java.io.StringReader;
import java.lang.reflect.Member;

public class LibraryTest extends TestCase {
  public LibraryTest(String name) {
    super(name);
  }

  Library ll;
  Class math;


  public void setUp() throws Exception {
    math=Class.forName("java.lang.Math");
    Class[] sl=new Class[1];
    sl[0]=math;
    ll=new Library(sl,null,null,null,null);
  }

  public void tearDown() throws Exception {
  }

  public void testRoundDBL() throws Exception {
    Class[] par=new Class[1];
    par[0]=Double.TYPE;
    Member mf=ll.getMember(null,"round",par);
    assertTrue((mf!=null) && 
               (mf.equals(math.getMethod("round",par))));
  }

  public void testRoundFLT() throws Exception {
    Class[] par=new Class[1];
    par[0]=Float.TYPE;
    Member mf=ll.getMember(null,"round",par);
    assertTrue((mf!=null) && 
               (mf.equals(math.getMethod("round",par))));
  }

  public void testRoundINT() throws Exception {
    // test that on invocation "rount(int)" the closest is
    // "round(float)"
    Class[] par=new Class[1];
    par[0]=Float.TYPE;
    Member mf=ll.getMember(null,"round",par);

    Class[] par1=new Class[1];
    par1[0]=Float.TYPE;
    assertTrue((mf!=null) && 
               (mf.equals(math.getMethod("round",par1))));
  }

  public void testAbsINT() throws Exception {
    Class[] par=new Class[1];
    par[0]=Integer.TYPE;
    Member mf=ll.getMember(null,"abs",par);
    
    Class[] par1=new Class[1];
    par1[0]=Integer.TYPE;
    assertTrue((mf!=null) && 
                (mf.equals(math.getMethod("abs",par1))));
  }

  public void testAbsBYTE() throws Exception {
    Class[] par=new Class[1];
    par[0]=Byte.TYPE;
    Member mf=ll.getMember(null,"abs",par);
    
    Class[] par1=new Class[1];
    par1[0]=Integer.TYPE; // closest is "abs(int)"
    assertTrue((mf!=null) && 
                (mf.equals(math.getMethod("abs",par1))));
  }

  public void testAbsCHAR() throws Exception {
    Class[] par=new Class[1];
    par[0]=Character.TYPE;
    Member mf=ll.getMember(null,"abs",par);
    
    Class[] par1=new Class[1];
    par1[0]=Integer.TYPE; // closest is "abs(int)"
    assertTrue((mf!=null) && 
                (mf.equals(math.getMethod("abs",par1))));
  }

  public void testMinINT_FLOAT() throws Exception {
    // "min(int,float)" -> "min(float,float)"
    Class[] par=new Class[2];
    par[0]=Integer.TYPE;
    par[1]=Float.TYPE;
    Member mf=ll.getMember(null,"min",par);
    
    Class[] par1=new Class[2];
    par1[0]=Float.TYPE;
    par1[1]=Float.TYPE;
    assertTrue((mf!=null) && 
                (mf.equals(math.getMethod("min",par1))));
  }

  public void testPI() throws Exception {
    Class[] par=new Class[0];
    Member f=ll.getMember(null,"PI",par);
    assertTrue((f!=null) && (f.getName().equals("PI")));
  }

  public void testStateDep() throws Exception {
    ll.markStateDependent("random",null);
    assertTrue(!ll.isStateless(ll.getMember(null,"random",null)));
  };

}
