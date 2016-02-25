/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 2 -*-
 * $Id: $
 * (c) 1998 -- 2007 by Konstantin L. Metlov
 */
package gnu.jel;
import junit.framework.TestCase;
import java.io.StringReader;
import java.lang.reflect.Method;

public class IntegerStackTest extends TestCase {
  public IntegerStackTest(String name) {
    super(name);
  }

  public void setUp() throws Exception {
  }

  public void tearDown() throws Exception {
  }


  public void testPush() throws Exception {
    IntegerStack is=new IntegerStack(1);
    is.push(10);
    assertTrue(true);
  }

  public void testPeekPop() throws Exception {
    IntegerStack is=new IntegerStack(1);
    is.push(10);
    assertEquals(10,is.peek());
    assertEquals(10,is.pop());
  }

  public void testP3PeekP3() throws Exception {
    IntegerStack is=new IntegerStack(1);
    is.push(10);
    is.push(11);
    assertEquals(11,is.peek());
    is.push(12);
    assertEquals(12,is.peek());
    assertEquals(12,is.pop());
    assertEquals(11,is.pop());
    assertEquals(10,is.pop());
    assertEquals(0,is.size());
  }
  
  public void testSwap()  throws Exception {
    IntegerStack is1 = new IntegerStack(1);
    is1.push(10);
    is1.push(11);
    is1.push(12);
    is1.push(13);

    IntegerStack is2 = new IntegerStack(1);
    is2.push(0);
    is2.push(1);
    is2.push(2);
    is2.push(3);
    
    IntegerStack.swap(is1,3,is2,1);
    // 10,11,12,3,2,1
    // 0,13
    assertEquals(1,is1.pop());
    assertEquals(2,is1.pop());
    assertEquals(3,is1.pop());
    assertEquals(12,is1.pop());
    assertEquals(11,is1.pop());
    assertEquals(10,is1.pop());

    assertEquals(13,is2.pop());
    assertEquals(0,is2.pop());

  };
  
}
