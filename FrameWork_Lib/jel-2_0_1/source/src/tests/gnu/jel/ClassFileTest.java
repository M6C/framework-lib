/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 2 -*-
 * $Id: $
 * (c) 1998 -- 2007 by Konstantin L. Metlov
 */
package gnu.jel;
import junit.framework.TestCase;
import java.io.StringReader;
import java.lang.reflect.Method;

public class ClassFileTest extends TestCase {
  public ClassFileTest(String name) {
    super(name);
  }

  ClassFile cf;

  public void setUp() throws Exception {
    LocalField[] lf=new LocalField[1];
    //    private Object[] e;
    lf[0]=new LocalField(0x0002,(new Object[0]).getClass(),"e",null);

	cf = new ClassFile(0x0001,"dump",(new Object()).getClass(),null,lf);
  }

  public void tearDown() throws Exception {
  }


  public void testAdd2UTF() throws Exception {
    String s1="some string";
    assertEquals(cf.getUTFIndex(s1),
                 cf.getUTFIndex(s1));
  }

  public void testAdd2Long() throws Exception {
    assertEquals(cf.getIndex(new Long(15),5),
                 cf.getIndex(new Long(15),5));
  }
  public void testAdd2Int() throws Exception {
    assertEquals(cf.getIndex(new Integer(15),4),
                 cf.getIndex(new Integer(15),4));
  }

  public void testAdd2Float() throws Exception {
    assertEquals(cf.getIndex(new Float(15.0f),6),
                 cf.getIndex(new Float(15.0f),6));
  }

  public void testAdd2Double() throws Exception {
    assertEquals(cf.getIndex(new Double(15.0),7),
                 cf.getIndex(new Double(15.0),7));
  }

  public void testAdd2Str() throws Exception {
    String s1="some string";
    assertEquals(cf.getUTFIndex(s1),
                 cf.getUTFIndex(s1));

    String s2="some other string";
    assertEquals(cf.getIndex(s2,8),
                 cf.getIndex(s2,8));

    assertEquals(cf.getIndex(s1,8),
                 cf.getIndex(s1,8));
  }

  public void testAdd2Cls() throws Exception {
    assertEquals(cf.getIndex(cf.getClass(),9),
                 cf.getIndex(cf.getClass(),9));
  }

  public void testAdd2Mth() throws Exception {
    Class[] params=new Class[1];
    Method a_method;
    params[0]=Class.forName("gnu.jel.ClassFile");
    a_method=this.getClass().getMethod("dumpImage",params);
    assertEquals(cf.getIndex(a_method,10),
                 cf.getIndex(a_method,10));
  }

  public static void dumpImage(ClassFile cf) throws Exception {
    java.io.FileOutputStream fos=
      new java.io.FileOutputStream("dump.class");
    fos.write(cf.getImage());
    fos.close();
  };
  
}
