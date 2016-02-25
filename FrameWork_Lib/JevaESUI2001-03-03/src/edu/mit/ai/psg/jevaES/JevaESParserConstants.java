// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JevaESParserConstants.java

package edu.mit.ai.psg.jevaES;


public interface JevaESParserConstants
{

    public static final int EOF = 0;
    public static final int WHITE_SPACE = 1;
    public static final int SINGLE_LINE_COMMENT = 2;
    public static final int FORMAL_COMMENT = 3;
    public static final int MULTI_LINE_COMMENT = 4;
    public static final int ABSTRACT = 5;
    public static final int BOOLEAN = 6;
    public static final int BREAK = 7;
    public static final int BYTE = 8;
    public static final int CASE = 9;
    public static final int CATCH = 10;
    public static final int CHAR = 11;
    public static final int CLASS = 12;
    public static final int CONST = 13;
    public static final int CONTINUE = 14;
    public static final int _DEFAULT = 15;
    public static final int DO = 16;
    public static final int DOUBLE = 17;
    public static final int ELSE = 18;
    public static final int EXTENDS = 19;
    public static final int FALSE = 20;
    public static final int FINAL = 21;
    public static final int FINALLY = 22;
    public static final int FLOAT = 23;
    public static final int FOR = 24;
    public static final int GOTO = 25;
    public static final int IF = 26;
    public static final int IMPLEMENTS = 27;
    public static final int IMPORT = 28;
    public static final int INSTANCEOF = 29;
    public static final int INT = 30;
    public static final int INTERFACE = 31;
    public static final int LONG = 32;
    public static final int NATIVE = 33;
    public static final int NEW = 34;
    public static final int NULL = 35;
    public static final int PACKAGE = 36;
    public static final int PRIVATE = 37;
    public static final int PROTECTED = 38;
    public static final int PUBLIC = 39;
    public static final int RETURN = 40;
    public static final int SHORT = 41;
    public static final int STATIC = 42;
    public static final int SUPER = 43;
    public static final int SWITCH = 44;
    public static final int SYNCHRONIZED = 45;
    public static final int THIS = 46;
    public static final int THROW = 47;
    public static final int THROWS = 48;
    public static final int TRANSIENT = 49;
    public static final int TRUE = 50;
    public static final int TRY = 51;
    public static final int VOID = 52;
    public static final int VOLATILE = 53;
    public static final int WHILE = 54;
    public static final int INTEGER_LITERAL = 55;
    public static final int DECIMAL_LITERAL = 56;
    public static final int HEX_LITERAL = 57;
    public static final int OCTAL_LITERAL = 58;
    public static final int FLOATING_POINT_LITERAL = 59;
    public static final int EXPONENT = 60;
    public static final int CHARACTER_LITERAL = 61;
    public static final int STRING_LITERAL = 62;
    public static final int IDENTIFIER = 63;
    public static final int LETTER = 64;
    public static final int DIGIT = 65;
    public static final int LPAREN = 66;
    public static final int RPAREN = 67;
    public static final int LBRACE = 68;
    public static final int RBRACE = 69;
    public static final int LBRACKET = 70;
    public static final int RBRACKET = 71;
    public static final int SEMICOLON = 72;
    public static final int COMMA = 73;
    public static final int DOT = 74;
    public static final int ASSIGN = 75;
    public static final int GT = 76;
    public static final int LT = 77;
    public static final int BANG = 78;
    public static final int TILDE = 79;
    public static final int HOOK = 80;
    public static final int COLON = 81;
    public static final int EQ = 82;
    public static final int LE = 83;
    public static final int GE = 84;
    public static final int NE = 85;
    public static final int SC_OR = 86;
    public static final int SC_AND = 87;
    public static final int INCR = 88;
    public static final int DECR = 89;
    public static final int PLUS = 90;
    public static final int MINUS = 91;
    public static final int STAR = 92;
    public static final int SLASH = 93;
    public static final int BIT_AND = 94;
    public static final int BIT_OR = 95;
    public static final int XOR = 96;
    public static final int REM = 97;
    public static final int LSHIFT = 98;
    public static final int RSIGNEDSHIFT = 99;
    public static final int RUNSIGNEDSHIFT = 100;
    public static final int PLUSASSIGN = 101;
    public static final int MINUSASSIGN = 102;
    public static final int STARASSIGN = 103;
    public static final int SLASHASSIGN = 104;
    public static final int ANDASSIGN = 105;
    public static final int ORASSIGN = 106;
    public static final int XORASSIGN = 107;
    public static final int REMASSIGN = 108;
    public static final int LSHIFTASSIGN = 109;
    public static final int RSIGNEDSHIFTASSIGN = 110;
    public static final int RUNSIGNEDSHIFTASSIGN = 111;
    public static final int DEFAULT = 0;
    public static final String tokenImage[] = {
        "<EOF>", "<WHITE_SPACE>", "<SINGLE_LINE_COMMENT>", "<FORMAL_COMMENT>", "<MULTI_LINE_COMMENT>", "\"abstract\"", "\"boolean\"", "\"break\"", "\"byte\"", "\"case\"", 
        "\"catch\"", "\"char\"", "\"class\"", "\"const\"", "\"continue\"", "\"default\"", "\"do\"", "\"double\"", "\"else\"", "\"extends\"", 
        "\"false\"", "\"final\"", "\"finally\"", "\"float\"", "\"for\"", "\"goto\"", "\"if\"", "\"implements\"", "\"import\"", "\"instanceof\"", 
        "\"int\"", "\"interface\"", "\"long\"", "\"native\"", "\"new\"", "\"null\"", "\"package\"", "\"private\"", "\"protected\"", "\"public\"", 
        "\"return\"", "\"short\"", "\"static\"", "\"super\"", "\"switch\"", "\"synchronized\"", "\"this\"", "\"throw\"", "\"throws\"", "\"transient\"", 
        "\"true\"", "\"try\"", "\"void\"", "\"volatile\"", "\"while\"", "<INTEGER_LITERAL>", "<DECIMAL_LITERAL>", "<HEX_LITERAL>", "<OCTAL_LITERAL>", "<FLOATING_POINT_LITERAL>", 
        "<EXPONENT>", "<CHARACTER_LITERAL>", "<STRING_LITERAL>", "<IDENTIFIER>", "<LETTER>", "<DIGIT>", "\"(\"", "\")\"", "\"{\"", "\"}\"", 
        "\"[\"", "\"]\"", "\";\"", "\",\"", "\".\"", "\"=\"", "\">\"", "\"<\"", "\"!\"", "\"~\"", 
        "\"?\"", "\":\"", "\"==\"", "\"<=\"", "\">=\"", "\"!=\"", "\"||\"", "\"&&\"", "\"++\"", "\"--\"", 
        "\"+\"", "\"-\"", "\"*\"", "\"/\"", "\"&\"", "\"|\"", "\"^\"", "\"%\"", "\"<<\"", "\">>\"", 
        "\">>>\"", "\"+=\"", "\"-=\"", "\"*=\"", "\"/=\"", "\"&=\"", "\"|=\"", "\"^=\"", "\"%=\"", "\"<<=\"", 
        "\">>=\"", "\">>>=\""
    };

}
