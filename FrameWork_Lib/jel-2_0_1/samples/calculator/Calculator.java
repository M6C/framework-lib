/*
 * $Id: Calculator.java 490 2006-10-01 16:08:04Z metlov $
 *
 * This file is part of the Java Expressions Library (JEL).
 *   For more information about JEL visit :
 *    http://galaxy.fzu.cz/JEL/
 *
 * (c) 1998 -- @year@ by Konstantin Metlov(metlov@fzu.cz);
 *
 * JEL is Distributed under the terms of GNU General Public License.
 *    This code comes with ABSOLUTELY NO WARRANTY.
 *  For license details see COPYING file in this directory.
 */

import gnu.jel.Evaluator;
import gnu.jel.CompiledExpression;
import gnu.jel.Library;
import gnu.jel.CompilationException;

public class Calculator {
 
  static final String[] help=
  {" This is a simple calculator, based on JEL.",
   "",
   " to use this calculator issue the following command :",
   " java Calculator \"expression\"",
   "",
   "Don't forget to use quotes around the expression if Your shell requires",
   " it. For example \"1&3\" will fail in WinNT cmd without quotes.",
   " The expression language is intuitively simple. You can use binary ",
   " operations : '+','-','*','/','%'(remainder),'&'(bitwise and),",
   " '|'(bitwise or), '^'(bitwise xor) , unary negation '-'. Also all ",
   " standard static functions of java.lang.Math are at Your disposal.",
   "Examples of expressions : \"1+1\", \"sin(1)\", \"random()\" .",
   "",
   "Of course the use of the compiler (what JEL is actually is) is crazy for ",
   "calculating expressions only once. This has a HUGE performance loss, but,",
   "this is just the demo. Enjoy !!!",
   "",
   "(c) 1998 by Konstantin Metlov (metlov@fzu.cz)",
   "    This  program is the free software  and  was distributed to You under",
   "    terms  of GNU General Public License. You should have the text of the",
   "    license together with  the source code of this sample and JEL itself.",
   "    If You don't have the source code or license- contact me immediately."
  };
  
  
  public static void main(String[] args) {
    if (args.length==0) {
      for(int i=0;i<help.length;i++)
	System.err.println(help[i]);
      return;
    };
    
    // Assemble the expression
    StringBuffer expr_sb=new StringBuffer();
    for(int i=0;i<args.length;i++) {
      expr_sb.append(args[i]);
      expr_sb.append(' ');
    };
    String expr=expr_sb.toString();

    // Set up library
    Class[] staticLib=new Class[1];
    try {
      staticLib[0]=Class.forName("java.lang.Math");
    } catch(ClassNotFoundException e) {
      // Can't be ;)) ...... in java ... ;)
    };
    Library lib=new Library(staticLib,null,null,null,null);
    try {
    lib.markStateDependent("random",null);
    } catch (CompilationException e) {
      // Can't be also
    };

    // Compile
    CompiledExpression expr_c=null;
    try {
      expr_c=Evaluator.compile(expr,lib);
    } catch (CompilationException ce) {
      System.err.print("--- COMPILATION ERROR :");
      System.err.println(ce.getMessage());
      System.err.print("                       ");
      System.err.println(expr);
      int column=ce.getColumn(); // Column, where error was found
      for(int i=0;i<column+23-1;i++) System.err.print(' ');
      System.err.println('^');
    };

    if (expr_c !=null) {
      
      // Evaluate (Can do it now any number of times FAST !!!)
      Object result=null;
      try {
	result=expr_c.evaluate(null);
      } catch (Throwable e) {
	System.err.println("Exception emerged from JEL compiled"+
			   " code (IT'S OK) :");
	System.err.print(e);
      };
      
      // Print result
      if (result==null) 
	System.out.println("void");
      else
	System.out.println(result.toString());
    };
    
    // Done
  };
};


