/*-*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 2 -*-
 * $Id: VariableProvider.java 488 2006-07-09 14:22:48Z metlov $
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

public class VariableProvider extends gnu.jel.DVMap {
  public Object anObject=new Object();

  public double xvar;
  public String strVar;
 
  
  public double x() {return xvar;};
  public static double xS() {return 5.3;};
  public java.lang.Double xX() {return new java.lang.Double(xvar);};
  public static java.lang.Double xXS() {return new java.lang.Double(5.3);};

  // void testing func
  public void voidf(String s) {
    return;
  };

  // --- Dynamic variables testing section

  private java.util.Hashtable properties=new java.util.Hashtable();

  public gnu.jel.reflect.Double getDoubleProperty(String name) {
    return (gnu.jel.reflect.Double) properties.get(name);
  };

  public DoubleObject getTSDoubleProperty(String name) {
    return (DoubleObject) properties.get(name);
  };

  public gnu.jel.reflect.Boolean getBooleanProperty(String name) {
    return (gnu.jel.reflect.Boolean) properties.get(name);
  };

  public String getStringProperty(String name) {
    return (String) properties.get(name);
  };

  public void addProperty(String name, Object value) {
    properties.put(name,value);
  };

  public String getTypeName(String name) {
    // this is for testing translated names
    if (name.startsWith("_T_")) return "String";

    Object val=properties.get(name);
    if (val==null) return null;
    if (val instanceof String) return "String";
    if (val.getClass()==DoubleObject.class) return "TSDouble";
    if (val instanceof gnu.jel.reflect.Double) return "Double";
    if (val instanceof gnu.jel.reflect.Boolean) return "Boolean";

    //-------- FOR JULIA DUNPHY
    if (val instanceof double[]) return "DoubleArr";
    //======== FOR JULIA DUNPHY

    return null;
  };

  //-------- FOR JULIA DUNPHY
  public double[] getDoubleArrProperty(String name) {
    return (double[]) properties.get(name);
  };
  //======== FOR JULIA DUNPHY

  // --- END OF (Dynamic variables testing section)

  public gnu.jel.reflect.Double[] arrDoubleJELObj1={
    new DoubleObject(1.0),
    new DoubleObject(2.0)    
  };

  public double[] arr;

  public double[][] aarr={{1.0,2.0},{3.0,4.0}};

  public java.lang.Double[][] aarrDouble={{new Double(1.0),new Double(2.0)},
                                          {new Double(3.0),new Double(4.0)}};
  
  public gnu.jel.reflect.Integer intObj;

  public gnu.jel.reflect.Byte byteObj;

  public static final double[] arrs={1.0,2.0,3.0};
  
  public VariableProvider() {
    arr=new double[3];
    arr[0]=4.0;
    arr[1]=5.0;
    arr[2]=6.0;
    intObj=new IntegerObject(555);
    byteObj=new ByteObject((byte)2);
  };

  public static int throw_arg_eq_4(int x) throws Exception {
    if (x==4) throw new Exception("An exception from testsuite.");
    return 0;
  };

  public static int bool2int(boolean b) {
    if (b) return 1; else return 0;
  };

  public static float NaNf() {
    return Float.NaN;
  };

  public static double NaNd() {
    return Double.NaN;
  };

  public static Double makeDoubleObject(double d) {
    return new Double(d);
  };

  public boolean[] arrBoolean={true};
  public byte[] arrByte={(byte)1};
  public char[] arrCharacter={(char)1};
  public short[] arrShort={(short)1};
  public int[] arrInteger={1};
  public long[] arrLong={1};
  public float[] arrFloat={(float)1.0};
  public double[] arrDouble={1.0};

  public java.lang.Boolean[] arrBooleanObj={new java.lang.Boolean(true)};
  public java.lang.Byte[] arrByteObj={new java.lang.Byte((byte)1)};
  public java.lang.Character[] arrCharacterObj=
    {new java.lang.Character((char)1)};
  public java.lang.Short[] arrShortObj={new java.lang.Short((short)1)};
  public java.lang.Integer[] arrIntegerObj={new java.lang.Integer(1)};
  public java.lang.Long[] arrLongObj={new java.lang.Long(1)};
  public java.lang.Float[] arrFloatObj={new java.lang.Float((float)1.0)};
  public java.lang.Double[] arrDoubleObj={new java.lang.Double(1.0)};

  public gnu.jel.reflect.Boolean[] arrBooleanJELObj={new BooleanObject(true)};
  public gnu.jel.reflect.Byte[] arrByteJELObj={new ByteObject((byte)1)};
  public gnu.jel.reflect.Character[] arrCharacterJELObj=
    {new CharacterObject((char)1)};
  public gnu.jel.reflect.Short[] arrShortJELObj={new ShortObject((short)1)};
  public gnu.jel.reflect.Integer[] arrIntegerJELObj={new IntegerObject(1)};
  public gnu.jel.reflect.Long[] arrLongJELObj={new LongObject(1)};
  public gnu.jel.reflect.Float[] arrFloatJELObj={new FloatObject((float)1.0)};
  public gnu.jel.reflect.Double[] arrDoubleJELObj={new DoubleObject(1.0)};


  public boolean valBoolean=true;
  public byte valByte=(byte)1;
  public char valCharacter=(char)1;
  public short valShort=(short)1;
  public int valInteger=1;
  public long valLong=1;
  public float valFloat=(float)1.0;
  public double valDouble=1.0;
  public StringObject valString=new StringObject("strObj");

  public java.lang.Boolean valBooleanObj=new java.lang.Boolean(true);
  public java.lang.Byte valByteObj=new java.lang.Byte((byte)1);
  public java.lang.Character valCharacterObj=new java.lang.Character((char)1);
  public java.lang.Short valShortObj=new java.lang.Short((short)1);
  public java.lang.Integer valIntegerObj=new java.lang.Integer(1);
  public java.lang.Long valLongObj=new java.lang.Long(1);
  public java.lang.Float valFloatObj=new java.lang.Float((float)1.0);
  public java.lang.Double valDoubleObj=new java.lang.Double(1.0);

  public gnu.jel.reflect.Boolean valBooleanJELObj=new BooleanObject(true);
  public gnu.jel.reflect.Byte valByteJELObj=new ByteObject((byte)1);
  public gnu.jel.reflect.Character valCharacterJELObj=new CharacterObject((char)1);
  public gnu.jel.reflect.Short valShortJELObj=new ShortObject((short)1);
  public gnu.jel.reflect.Integer valIntegerJELObj=new IntegerObject(1);
  public gnu.jel.reflect.Long valLongJELObj=new LongObject(1);
  public gnu.jel.reflect.Float valFloatJELObj=new FloatObject((float)1.0);
  public gnu.jel.reflect.Double valDoubleJELObj=new DoubleObject(1.0);

  public static boolean isNullDouble(java.lang.Double d) {
    return d==null;
  };

  public static java.lang.Double getDoubleNull() {
    return null;
  };

  
  public static int convertNumberToInt(Number n) {
    return n.intValue();
  };

  public static int addNumbersInt(Number n1,Number n2) {
    return n1.intValue()+n2.intValue();
  };

  public static double convertNumberToDouble(Number n) {
    return n.doubleValue();
  };

  public static double addNumbersDbl(Number n1,Number n2) {
    return n1.doubleValue()+n2.doubleValue();
  };
  
  public static gnu.jel.reflect.Boolean makeJELBooleanObject(boolean b) {
    return new BooleanObject(b);
  };

  public static gnu.jel.reflect.Byte makeJELByteObject(byte v) {
    return new ByteObject(v);
  };

  public static gnu.jel.reflect.Character makeJELCharacterObject(char v) {
    return new CharacterObject(v);
  };

  public static gnu.jel.reflect.Short makeJELShortObject(short v) {
    return new ShortObject(v);
  };

  public static IntegerObject makeJELIntegerObject(int v) {
    return new IntegerObject(v);
  };

  public static gnu.jel.reflect.Long makeJELLongObject(long v) {
    return new LongObject(v);
  };

  public static gnu.jel.reflect.Float makeJELFloatObject(float v) {
    return new FloatObject(v);
  };

  public static gnu.jel.reflect.Double makeJELDoubleObject(double d) {
    return new DoubleObject(d);
  };

  // next two methods test overloading of unwrappable types
  public static int methodOnInt(int i) {
    return i;
  };
  public static int methodOnInt(gnu.jel.reflect.Integer iobj) {
    return iobj.getValue()+1;
  };

  public static String append_ttt(String str) {
    return str+"_ttt";
  };

  public Object translate(String name) {
    if (name.startsWith("_T_")) {
      // this is for testing translated names
      return new Integer(name.charAt(3)-'a');
    } else
      return name;
  };

  public String getStringProperty(int num) {
    char lett=(char)('a'+num);
    return "_U_"+lett;
  };
  
};







