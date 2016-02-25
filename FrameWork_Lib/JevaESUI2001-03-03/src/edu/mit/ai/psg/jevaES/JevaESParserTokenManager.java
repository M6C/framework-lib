// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jevaES/JevaESParserTokenManager.java

package edu.mit.ai.psg.jevaES;

import java.io.IOException;

// Referenced classes of package edu.mit.ai.psg.jevaES:
//            TokenMgrError, JevaESParserConstants, ASCII_UCodeESC_CharStream, Token

public class JevaESParserTokenManager
    implements JevaESParserConstants
{

    private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1)
    {
        switch(pos)
        {
        case 0: // '\0'
            if((active0 & 0x7fffffffffffe0L) != 0L)
            {
                jjmatchedKind = 63;
                return 29;
            }
            if((active1 & 1024L) != 0L)
                return 5;
            return (active1 & 0x10020000000L) == 0L ? -1 : 50;

        case 1: // '\001'
            if((active0 & 0x4030000L) != 0L)
                return 29;
            if((active0 & 0x7ffffffbfcffe0L) != 0L)
            {
                if(jjmatchedPos != 1)
                {
                    jjmatchedKind = 63;
                    jjmatchedPos = 1;
                }
                return 29;
            } else
            {
                return -1;
            }

        case 2: // '\002'
            if((active0 & 0x80004c1000000L) != 0L)
                return 29;
            if((active0 & 0x77fffb3afeffe0L) != 0L)
            {
                if(jjmatchedPos != 2)
                {
                    jjmatchedKind = 63;
                    jjmatchedPos = 2;
                }
                return 29;
            } else
            {
                return -1;
            }

        case 3: // '\003'
            if((active0 & 0x63bff2b8faf4e0L) != 0L)
            {
                jjmatchedKind = 63;
                jjmatchedPos = 3;
                return 29;
            }
            return (active0 & 0x14400902040b00L) == 0L ? -1 : 29;

        case 4: // '\004'
            if((active0 & 0x418a0000f03480L) != 0L)
                return 29;
            if((active0 & 0x2235f2b80ac060L) != 0L)
            {
                if(jjmatchedPos != 4)
                {
                    jjmatchedKind = 63;
                    jjmatchedPos = 4;
                }
                return 29;
            } else
            {
                return -1;
            }

        case 5: // '\005'
            if((active0 & 0x222070a848c060L) != 0L)
            {
                jjmatchedKind = 63;
                jjmatchedPos = 5;
                return 29;
            }
            return (active0 & 0x1158210020000L) == 0L ? -1 : 29;

        case 6: // '\006'
            if((active0 & 0x3000488040L) != 0L)
                return 29;
            if((active0 & 0x222040a8004020L) != 0L)
            {
                jjmatchedKind = 63;
                jjmatchedPos = 6;
                return 29;
            } else
            {
                return -1;
            }

        case 7: // '\007'
            if((active0 & 0x20000000004020L) != 0L)
                return 29;
            if((active0 & 0x22040a8000000L) != 0L)
            {
                jjmatchedKind = 63;
                jjmatchedPos = 7;
                return 29;
            } else
            {
                return -1;
            }

        case 8: // '\b'
            if((active0 & 0x2004080000000L) != 0L)
                return 29;
            if((active0 & 0x200028000000L) != 0L)
            {
                jjmatchedKind = 63;
                jjmatchedPos = 8;
                return 29;
            } else
            {
                return -1;
            }

        case 9: // '\t'
            if((active0 & 0x200000000000L) != 0L)
            {
                jjmatchedKind = 63;
                jjmatchedPos = 9;
                return 29;
            }
            return (active0 & 0x28000000L) == 0L ? -1 : 29;

        case 10: // '\n'
            if((active0 & 0x200000000000L) != 0L)
            {
                jjmatchedKind = 63;
                jjmatchedPos = 10;
                return 29;
            } else
            {
                return -1;
            }
        }
        return -1;
    }

    private final int jjStartNfa_0(int pos, long active0, long active1)
    {
        return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
    }

    private final int jjStopAtPos(int pos, int kind)
    {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        return pos + 1;
    }

    private final int jjStartNfaWithStates_0(int pos, int kind, int state)
    {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            return pos + 1;
        }
        return jjMoveNfa_0(state, pos + 1);
    }

    private final int jjMoveStringLiteralDfa0_0()
    {
        switch(curChar)
        {
        case 33: // '!'
            jjmatchedKind = 78;
            return jjMoveStringLiteralDfa1_0(0L, 0x200000L);

        case 37: // '%'
            jjmatchedKind = 97;
            return jjMoveStringLiteralDfa1_0(0L, 0x100000000000L);

        case 38: // '&'
            jjmatchedKind = 94;
            return jjMoveStringLiteralDfa1_0(0L, 0x20000800000L);

        case 40: // '('
            return jjStopAtPos(0, 66);

        case 41: // ')'
            return jjStopAtPos(0, 67);

        case 42: // '*'
            jjmatchedKind = 92;
            return jjMoveStringLiteralDfa1_0(0L, 0x8000000000L);

        case 43: // '+'
            jjmatchedKind = 90;
            return jjMoveStringLiteralDfa1_0(0L, 0x2001000000L);

        case 44: // ','
            return jjStopAtPos(0, 73);

        case 45: // '-'
            jjmatchedKind = 91;
            return jjMoveStringLiteralDfa1_0(0L, 0x4002000000L);

        case 46: // '.'
            return jjStartNfaWithStates_0(0, 74, 5);

        case 47: // '/'
            jjmatchedKind = 93;
            return jjMoveStringLiteralDfa1_0(0L, 0x10000000000L);

        case 58: // ':'
            return jjStopAtPos(0, 81);

        case 59: // ';'
            return jjStopAtPos(0, 72);

        case 60: // '<'
            jjmatchedKind = 77;
            return jjMoveStringLiteralDfa1_0(0L, 0x200400080000L);

        case 61: // '='
            jjmatchedKind = 75;
            return jjMoveStringLiteralDfa1_0(0L, 0x40000L);

        case 62: // '>'
            jjmatchedKind = 76;
            return jjMoveStringLiteralDfa1_0(0L, 0xc01800100000L);

        case 63: // '?'
            return jjStopAtPos(0, 80);

        case 91: // '['
            return jjStopAtPos(0, 70);

        case 93: // ']'
            return jjStopAtPos(0, 71);

        case 94: // '^'
            jjmatchedKind = 96;
            return jjMoveStringLiteralDfa1_0(0L, 0x80000000000L);

        case 97: // 'a'
            return jjMoveStringLiteralDfa1_0(32L, 0L);

        case 98: // 'b'
            return jjMoveStringLiteralDfa1_0(448L, 0L);

        case 99: // 'c'
            return jjMoveStringLiteralDfa1_0(32256L, 0L);

        case 100: // 'd'
            return jjMoveStringLiteralDfa1_0(0x38000L, 0L);

        case 101: // 'e'
            return jjMoveStringLiteralDfa1_0(0xc0000L, 0L);

        case 102: // 'f'
            return jjMoveStringLiteralDfa1_0(0x1f00000L, 0L);

        case 103: // 'g'
            return jjMoveStringLiteralDfa1_0(0x2000000L, 0L);

        case 105: // 'i'
            return jjMoveStringLiteralDfa1_0(0xfc000000L, 0L);

        case 108: // 'l'
            return jjMoveStringLiteralDfa1_0(0x100000000L, 0L);

        case 110: // 'n'
            return jjMoveStringLiteralDfa1_0(0xe00000000L, 0L);

        case 112: // 'p'
            return jjMoveStringLiteralDfa1_0(0xf000000000L, 0L);

        case 114: // 'r'
            return jjMoveStringLiteralDfa1_0(0x10000000000L, 0L);

        case 115: // 's'
            return jjMoveStringLiteralDfa1_0(0x3e0000000000L, 0L);

        case 116: // 't'
            return jjMoveStringLiteralDfa1_0(0xfc00000000000L, 0L);

        case 118: // 'v'
            return jjMoveStringLiteralDfa1_0(0x30000000000000L, 0L);

        case 119: // 'w'
            return jjMoveStringLiteralDfa1_0(0x40000000000000L, 0L);

        case 123: // '{'
            return jjStopAtPos(0, 68);

        case 124: // '|'
            jjmatchedKind = 95;
            return jjMoveStringLiteralDfa1_0(0L, 0x40000400000L);

        case 125: // '}'
            return jjStopAtPos(0, 69);

        case 126: // '~'
            return jjStopAtPos(0, 79);

        case 34: // '"'
        case 35: // '#'
        case 36: // '$'
        case 39: // '\''
        case 48: // '0'
        case 49: // '1'
        case 50: // '2'
        case 51: // '3'
        case 52: // '4'
        case 53: // '5'
        case 54: // '6'
        case 55: // '7'
        case 56: // '8'
        case 57: // '9'
        case 64: // '@'
        case 65: // 'A'
        case 66: // 'B'
        case 67: // 'C'
        case 68: // 'D'
        case 69: // 'E'
        case 70: // 'F'
        case 71: // 'G'
        case 72: // 'H'
        case 73: // 'I'
        case 74: // 'J'
        case 75: // 'K'
        case 76: // 'L'
        case 77: // 'M'
        case 78: // 'N'
        case 79: // 'O'
        case 80: // 'P'
        case 81: // 'Q'
        case 82: // 'R'
        case 83: // 'S'
        case 84: // 'T'
        case 85: // 'U'
        case 86: // 'V'
        case 87: // 'W'
        case 88: // 'X'
        case 89: // 'Y'
        case 90: // 'Z'
        case 92: // '\\'
        case 95: // '_'
        case 96: // '`'
        case 104: // 'h'
        case 106: // 'j'
        case 107: // 'k'
        case 109: // 'm'
        case 111: // 'o'
        case 113: // 'q'
        case 117: // 'u'
        case 120: // 'x'
        case 121: // 'y'
        case 122: // 'z'
        default:
            return jjMoveNfa_0(1, 0);
        }
    }

    private final int jjMoveStringLiteralDfa1_0(long active0, long active1)
    {
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(0, active0, active1);
            return 1;
        }
        switch(curChar)
        {
        case 39: // '\''
        case 40: // '('
        case 41: // ')'
        case 42: // '*'
        case 44: // ','
        case 46: // '.'
        case 47: // '/'
        case 48: // '0'
        case 49: // '1'
        case 50: // '2'
        case 51: // '3'
        case 52: // '4'
        case 53: // '5'
        case 54: // '6'
        case 55: // '7'
        case 56: // '8'
        case 57: // '9'
        case 58: // ':'
        case 59: // ';'
        case 63: // '?'
        case 64: // '@'
        case 65: // 'A'
        case 66: // 'B'
        case 67: // 'C'
        case 68: // 'D'
        case 69: // 'E'
        case 70: // 'F'
        case 71: // 'G'
        case 72: // 'H'
        case 73: // 'I'
        case 74: // 'J'
        case 75: // 'K'
        case 76: // 'L'
        case 77: // 'M'
        case 78: // 'N'
        case 79: // 'O'
        case 80: // 'P'
        case 81: // 'Q'
        case 82: // 'R'
        case 83: // 'S'
        case 84: // 'T'
        case 85: // 'U'
        case 86: // 'V'
        case 87: // 'W'
        case 88: // 'X'
        case 89: // 'Y'
        case 90: // 'Z'
        case 91: // '['
        case 92: // '\\'
        case 93: // ']'
        case 94: // '^'
        case 95: // '_'
        case 96: // '`'
        case 99: // 'c'
        case 100: // 'd'
        case 103: // 'g'
        case 106: // 'j'
        case 107: // 'k'
        case 112: // 'p'
        case 113: // 'q'
        case 115: // 's'
        case 118: // 'v'
        case 122: // 'z'
        case 123: // '{'
        default:
            break;

        case 38: // '&'
            if((active1 & 0x800000L) != 0L)
                return jjStopAtPos(1, 87);
            break;

        case 43: // '+'
            if((active1 & 0x1000000L) != 0L)
                return jjStopAtPos(1, 88);
            break;

        case 45: // '-'
            if((active1 & 0x2000000L) != 0L)
                return jjStopAtPos(1, 89);
            break;

        case 60: // '<'
            if((active1 & 0x400000000L) != 0L)
            {
                jjmatchedKind = 98;
                jjmatchedPos = 1;
            }
            return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x200000000000L);

        case 61: // '='
            if((active1 & 0x40000L) != 0L)
                return jjStopAtPos(1, 82);
            if((active1 & 0x80000L) != 0L)
                return jjStopAtPos(1, 83);
            if((active1 & 0x100000L) != 0L)
                return jjStopAtPos(1, 84);
            if((active1 & 0x200000L) != 0L)
                return jjStopAtPos(1, 85);
            if((active1 & 0x2000000000L) != 0L)
                return jjStopAtPos(1, 101);
            if((active1 & 0x4000000000L) != 0L)
                return jjStopAtPos(1, 102);
            if((active1 & 0x8000000000L) != 0L)
                return jjStopAtPos(1, 103);
            if((active1 & 0x10000000000L) != 0L)
                return jjStopAtPos(1, 104);
            if((active1 & 0x20000000000L) != 0L)
                return jjStopAtPos(1, 105);
            if((active1 & 0x40000000000L) != 0L)
                return jjStopAtPos(1, 106);
            if((active1 & 0x80000000000L) != 0L)
                return jjStopAtPos(1, 107);
            if((active1 & 0x100000000000L) != 0L)
                return jjStopAtPos(1, 108);
            break;

        case 62: // '>'
            if((active1 & 0x800000000L) != 0L)
            {
                jjmatchedKind = 99;
                jjmatchedPos = 1;
            }
            return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0xc01000000000L);

        case 97: // 'a'
            return jjMoveStringLiteralDfa2_0(active0, 0x1200100600L, active1, 0L);

        case 98: // 'b'
            return jjMoveStringLiteralDfa2_0(active0, 32L, active1, 0L);

        case 101: // 'e'
            return jjMoveStringLiteralDfa2_0(active0, 0x10400008000L, active1, 0L);

        case 102: // 'f'
            if((active0 & 0x4000000L) != 0L)
                return jjStartNfaWithStates_0(1, 26, 29);
            break;

        case 104: // 'h'
            return jjMoveStringLiteralDfa2_0(active0, 0x41c20000000800L, active1, 0L);

        case 105: // 'i'
            return jjMoveStringLiteralDfa2_0(active0, 0x600000L, active1, 0L);

        case 108: // 'l'
            return jjMoveStringLiteralDfa2_0(active0, 0x841000L, active1, 0L);

        case 109: // 'm'
            return jjMoveStringLiteralDfa2_0(active0, 0x18000000L, active1, 0L);

        case 110: // 'n'
            return jjMoveStringLiteralDfa2_0(active0, 0xe0000000L, active1, 0L);

        case 111: // 'o'
            if((active0 & 0x10000L) != 0L)
            {
                jjmatchedKind = 16;
                jjmatchedPos = 1;
            }
            return jjMoveStringLiteralDfa2_0(active0, 0x30000103026040L, active1, 0L);

        case 114: // 'r'
            return jjMoveStringLiteralDfa2_0(active0, 0xe006000000080L, active1, 0L);

        case 116: // 't'
            return jjMoveStringLiteralDfa2_0(active0, 0x40000000000L, active1, 0L);

        case 117: // 'u'
            return jjMoveStringLiteralDfa2_0(active0, 0x88800000000L, active1, 0L);

        case 119: // 'w'
            return jjMoveStringLiteralDfa2_0(active0, 0x100000000000L, active1, 0L);

        case 120: // 'x'
            return jjMoveStringLiteralDfa2_0(active0, 0x80000L, active1, 0L);

        case 121: // 'y'
            return jjMoveStringLiteralDfa2_0(active0, 0x200000000100L, active1, 0L);

        case 124: // '|'
            if((active1 & 0x400000L) != 0L)
                return jjStopAtPos(1, 86);
            break;
        }
        return jjStartNfa_0(0, active0, active1);
    }

    private final int jjMoveStringLiteralDfa2_0(long old0, long active0, long old1, long active1)
    {
        if(((active0 &= old0) | (active1 &= old1)) == 0L)
            return jjStartNfa_0(0, old0, old1);
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(1, active0, active1);
            return 2;
        }
        switch(curChar)
        {
        case 63: // '?'
        case 64: // '@'
        case 65: // 'A'
        case 66: // 'B'
        case 67: // 'C'
        case 68: // 'D'
        case 69: // 'E'
        case 70: // 'F'
        case 71: // 'G'
        case 72: // 'H'
        case 73: // 'I'
        case 74: // 'J'
        case 75: // 'K'
        case 76: // 'L'
        case 77: // 'M'
        case 78: // 'N'
        case 79: // 'O'
        case 80: // 'P'
        case 81: // 'Q'
        case 82: // 'R'
        case 83: // 'S'
        case 84: // 'T'
        case 85: // 'U'
        case 86: // 'V'
        case 87: // 'W'
        case 88: // 'X'
        case 89: // 'Y'
        case 90: // 'Z'
        case 91: // '['
        case 92: // '\\'
        case 93: // ']'
        case 94: // '^'
        case 95: // '_'
        case 96: // '`'
        case 100: // 'd'
        case 103: // 'g'
        case 104: // 'h'
        case 106: // 'j'
        case 107: // 'k'
        case 109: // 'm'
        case 113: // 'q'
        case 118: // 'v'
        case 120: // 'x'
        default:
            break;

        case 61: // '='
            if((active1 & 0x200000000000L) != 0L)
                return jjStopAtPos(2, 109);
            if((active1 & 0x400000000000L) != 0L)
                return jjStopAtPos(2, 110);
            break;

        case 62: // '>'
            if((active1 & 0x1000000000L) != 0L)
            {
                jjmatchedKind = 100;
                jjmatchedPos = 2;
            }
            return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x800000000000L);

        case 97: // 'a'
            return jjMoveStringLiteralDfa3_0(active0, 0x2040000001800L, active1, 0L);

        case 98: // 'b'
            return jjMoveStringLiteralDfa3_0(active0, 0x8000000000L, active1, 0L);

        case 99: // 'c'
            return jjMoveStringLiteralDfa3_0(active0, 0x1000000000L, active1, 0L);

        case 101: // 'e'
            return jjMoveStringLiteralDfa3_0(active0, 128L, active1, 0L);

        case 102: // 'f'
            return jjMoveStringLiteralDfa3_0(active0, 32768L, active1, 0L);

        case 105: // 'i'
            return jjMoveStringLiteralDfa3_0(active0, 0x50502000000000L, active1, 0L);

        case 108: // 'l'
            return jjMoveStringLiteralDfa3_0(active0, 0x20000800100000L, active1, 0L);

        case 110: // 'n'
            return jjMoveStringLiteralDfa3_0(active0, 0x200100606000L, active1, 0L);

        case 111: // 'o'
            return jjMoveStringLiteralDfa3_0(active0, 0x24000800040L, active1, 0L);

        case 112: // 'p'
            return jjMoveStringLiteralDfa3_0(active0, 0x80018000000L, active1, 0L);

        case 114: // 'r'
            if((active0 & 0x1000000L) != 0L)
                return jjStartNfaWithStates_0(2, 24, 29);
            else
                return jjMoveStringLiteralDfa3_0(active0, 0x1800000000000L, active1, 0L);

        case 115: // 's'
            return jjMoveStringLiteralDfa3_0(active0, 0x20040220L, active1, 0L);

        case 116: // 't'
            if((active0 & 0x40000000L) != 0L)
            {
                jjmatchedKind = 30;
                jjmatchedPos = 2;
            }
            return jjMoveStringLiteralDfa3_0(active0, 0x10282080500L, active1, 0L);

        case 117: // 'u'
            return jjMoveStringLiteralDfa3_0(active0, 0x4000000020000L, active1, 0L);

        case 119: // 'w'
            if((active0 & 0x400000000L) != 0L)
                return jjStartNfaWithStates_0(2, 34, 29);
            break;

        case 121: // 'y'
            if((active0 & 0x8000000000000L) != 0L)
                return jjStartNfaWithStates_0(2, 51, 29);
            break;
        }
        return jjStartNfa_0(1, active0, active1);
    }

    private final int jjMoveStringLiteralDfa3_0(long old0, long active0, long old1, long active1)
    {
        if(((active0 &= old0) | (active1 &= old1)) == 0L)
            return jjStartNfa_0(1, old0, old1);
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(2, active0, active1);
            return 3;
        }
        switch(curChar)
        {
        case 62: // '>'
        case 63: // '?'
        case 64: // '@'
        case 65: // 'A'
        case 66: // 'B'
        case 67: // 'C'
        case 68: // 'D'
        case 69: // 'E'
        case 70: // 'F'
        case 71: // 'G'
        case 72: // 'H'
        case 73: // 'I'
        case 74: // 'J'
        case 75: // 'K'
        case 76: // 'L'
        case 77: // 'M'
        case 78: // 'N'
        case 79: // 'O'
        case 80: // 'P'
        case 81: // 'Q'
        case 82: // 'R'
        case 83: // 'S'
        case 84: // 'T'
        case 85: // 'U'
        case 86: // 'V'
        case 87: // 'W'
        case 88: // 'X'
        case 89: // 'Y'
        case 90: // 'Z'
        case 91: // '['
        case 92: // '\\'
        case 93: // ']'
        case 94: // '^'
        case 95: // '_'
        case 96: // '`'
        case 102: // 'f'
        case 104: // 'h'
        case 106: // 'j'
        case 109: // 'm'
        case 112: // 'p'
        case 113: // 'q'
        default:
            break;

        case 61: // '='
            if((active1 & 0x800000000000L) != 0L)
                return jjStopAtPos(3, 111);
            break;

        case 97: // 'a'
            return jjMoveStringLiteralDfa4_0(active0, 0x20000000e08080L, active1, 0L);

        case 98: // 'b'
            return jjMoveStringLiteralDfa4_0(active0, 0x20000L, active1, 0L);

        case 99: // 'c'
            return jjMoveStringLiteralDfa4_0(active0, 0x200000000400L, active1, 0L);

        case 100: // 'd'
            if((active0 & 0x10000000000000L) != 0L)
                return jjStartNfaWithStates_0(3, 52, 29);
            break;

        case 101: // 'e'
            if((active0 & 256L) != 0L)
                return jjStartNfaWithStates_0(3, 8, 29);
            if((active0 & 512L) != 0L)
                return jjStartNfaWithStates_0(3, 9, 29);
            if((active0 & 0x40000L) != 0L)
                return jjStartNfaWithStates_0(3, 18, 29);
            if((active0 & 0x4000000000000L) != 0L)
                return jjStartNfaWithStates_0(3, 50, 29);
            else
                return jjMoveStringLiteralDfa4_0(active0, 0x80080080000L, active1, 0L);

        case 103: // 'g'
            if((active0 & 0x100000000L) != 0L)
                return jjStartNfaWithStates_0(3, 32, 29);
            break;

        case 105: // 'i'
            return jjMoveStringLiteralDfa4_0(active0, 0x200000000L, active1, 0L);

        case 107: // 'k'
            return jjMoveStringLiteralDfa4_0(active0, 0x1000000000L, active1, 0L);

        case 108: // 'l'
            if((active0 & 0x800000000L) != 0L)
                return jjStartNfaWithStates_0(3, 35, 29);
            else
                return jjMoveStringLiteralDfa4_0(active0, 0x40008008000040L, active1, 0L);

        case 110: // 'n'
            return jjMoveStringLiteralDfa4_0(active0, 0x2000000000000L, active1, 0L);

        case 111: // 'o'
            if((active0 & 0x2000000L) != 0L)
                return jjStartNfaWithStates_0(3, 25, 29);
            else
                return jjMoveStringLiteralDfa4_0(active0, 0x1800010000000L, active1, 0L);

        case 114: // 'r'
            if((active0 & 2048L) != 0L)
                return jjStartNfaWithStates_0(3, 11, 29);
            else
                return jjMoveStringLiteralDfa4_0(active0, 0x20000000000L, active1, 0L);

        case 115: // 's'
            if((active0 & 0x400000000000L) != 0L)
                return jjStartNfaWithStates_0(3, 46, 29);
            else
                return jjMoveStringLiteralDfa4_0(active0, 0x103000L, active1, 0L);

        case 116: // 't'
            return jjMoveStringLiteralDfa4_0(active0, 0x144020004020L, active1, 0L);

        case 117: // 'u'
            return jjMoveStringLiteralDfa4_0(active0, 0x10000000000L, active1, 0L);

        case 118: // 'v'
            return jjMoveStringLiteralDfa4_0(active0, 0x2000000000L, active1, 0L);
        }
        return jjStartNfa_0(2, active0, active1);
    }

    private final int jjMoveStringLiteralDfa4_0(long old0, long active0, long old1, long active1)
    {
        if(((active0 &= old0) | (active1 &= old1)) == 0L)
            return jjStartNfa_0(2, old0, old1);
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(3, active0, 0L);
            return 4;
        }
        switch(curChar)
        {
        case 98: // 'b'
        case 100: // 'd'
        case 102: // 'f'
        case 103: // 'g'
        case 106: // 'j'
        case 109: // 'm'
        case 111: // 'o'
        case 112: // 'p'
        case 113: // 'q'
        default:
            break;

        case 97: // 'a'
            return jjMoveStringLiteralDfa5_0(active0, 0x3020000000L);

        case 99: // 'c'
            return jjMoveStringLiteralDfa5_0(active0, 0x100000000000L);

        case 101: // 'e'
            if((active0 & 0x100000L) != 0L)
                return jjStartNfaWithStates_0(4, 20, 29);
            if((active0 & 0x40000000000000L) != 0L)
                return jjStartNfaWithStates_0(4, 54, 29);
            else
                return jjMoveStringLiteralDfa5_0(active0, 0x4008000040L);

        case 104: // 'h'
            if((active0 & 1024L) != 0L)
                return jjStartNfaWithStates_0(4, 10, 29);
            else
                return jjMoveStringLiteralDfa5_0(active0, 0x200000000000L);

        case 105: // 'i'
            return jjMoveStringLiteralDfa5_0(active0, 0x48000004000L);

        case 107: // 'k'
            if((active0 & 128L) != 0L)
                return jjStartNfaWithStates_0(4, 7, 29);
            break;

        case 108: // 'l'
            if((active0 & 0x200000L) != 0L)
            {
                jjmatchedKind = 21;
                jjmatchedPos = 4;
            }
            return jjMoveStringLiteralDfa5_0(active0, 0x420000L);

        case 110: // 'n'
            return jjMoveStringLiteralDfa5_0(active0, 0x80000L);

        case 114: // 'r'
            if((active0 & 0x80000000000L) != 0L)
                return jjStartNfaWithStates_0(4, 43, 29);
            else
                return jjMoveStringLiteralDfa5_0(active0, 0x10090000020L);

        case 115: // 's'
            if((active0 & 4096L) != 0L)
                return jjStartNfaWithStates_0(4, 12, 29);
            else
                return jjMoveStringLiteralDfa5_0(active0, 0x2000000000000L);

        case 116: // 't'
            if((active0 & 8192L) != 0L)
                return jjStartNfaWithStates_0(4, 13, 29);
            if((active0 & 0x800000L) != 0L)
                return jjStartNfaWithStates_0(4, 23, 29);
            if((active0 & 0x20000000000L) != 0L)
                return jjStartNfaWithStates_0(4, 41, 29);
            else
                return jjMoveStringLiteralDfa5_0(active0, 0x20000000000000L);

        case 117: // 'u'
            return jjMoveStringLiteralDfa5_0(active0, 32768L);

        case 118: // 'v'
            return jjMoveStringLiteralDfa5_0(active0, 0x200000000L);

        case 119: // 'w'
            if((active0 & 0x800000000000L) != 0L)
            {
                jjmatchedKind = 47;
                jjmatchedPos = 4;
            }
            return jjMoveStringLiteralDfa5_0(active0, 0x1000000000000L);
        }
        return jjStartNfa_0(3, active0, 0L);
    }

    private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
    {
        if((active0 &= old0) == 0L)
            return jjStartNfa_0(3, old0, 0L);
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(4, active0, 0L);
            return 5;
        }
        switch(curChar)
        {
        case 98: // 'b'
        case 106: // 'j'
        case 107: // 'k'
        case 111: // 'o'
        case 112: // 'p'
        case 113: // 'q'
        default:
            break;

        case 97: // 'a'
            return jjMoveStringLiteralDfa6_0(active0, 96L);

        case 99: // 'c'
            if((active0 & 0x8000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 39, 29);
            if((active0 & 0x40000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 42, 29);
            else
                return jjMoveStringLiteralDfa6_0(active0, 0x4000000000L);

        case 100: // 'd'
            return jjMoveStringLiteralDfa6_0(active0, 0x80000L);

        case 101: // 'e'
            if((active0 & 0x20000L) != 0L)
                return jjStartNfaWithStates_0(5, 17, 29);
            if((active0 & 0x200000000L) != 0L)
                return jjStartNfaWithStates_0(5, 33, 29);
            break;

        case 102: // 'f'
            return jjMoveStringLiteralDfa6_0(active0, 0x80000000L);

        case 103: // 'g'
            return jjMoveStringLiteralDfa6_0(active0, 0x1000000000L);

        case 104: // 'h'
            if((active0 & 0x100000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 44, 29);
            break;

        case 105: // 'i'
            return jjMoveStringLiteralDfa6_0(active0, 0x22000000000000L);

        case 108: // 'l'
            return jjMoveStringLiteralDfa6_0(active0, 0x408000L);

        case 109: // 'm'
            return jjMoveStringLiteralDfa6_0(active0, 0x8000000L);

        case 110: // 'n'
            if((active0 & 0x10000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 40, 29);
            else
                return jjMoveStringLiteralDfa6_0(active0, 0x20004000L);

        case 114: // 'r'
            return jjMoveStringLiteralDfa6_0(active0, 0x200000000000L);

        case 115: // 's'
            if((active0 & 0x1000000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 48, 29);
            break;

        case 116: // 't'
            if((active0 & 0x10000000L) != 0L)
                return jjStartNfaWithStates_0(5, 28, 29);
            else
                return jjMoveStringLiteralDfa6_0(active0, 0x2000000000L);
        }
        return jjStartNfa_0(4, active0, 0L);
    }

    private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
    {
        if((active0 &= old0) == 0L)
            return jjStartNfa_0(4, old0, 0L);
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(5, active0, 0L);
            return 6;
        }
        switch(curChar)
        {
        case 98: // 'b'
        case 100: // 'd'
        case 102: // 'f'
        case 103: // 'g'
        case 104: // 'h'
        case 105: // 'i'
        case 106: // 'j'
        case 107: // 'k'
        case 109: // 'm'
        case 112: // 'p'
        case 113: // 'q'
        case 114: // 'r'
        case 118: // 'v'
        case 119: // 'w'
        case 120: // 'x'
        default:
            break;

        case 97: // 'a'
            return jjMoveStringLiteralDfa7_0(active0, 0x80000000L);

        case 99: // 'c'
            return jjMoveStringLiteralDfa7_0(active0, 0x20000020L);

        case 101: // 'e'
            if((active0 & 0x1000000000L) != 0L)
                return jjStartNfaWithStates_0(6, 36, 29);
            if((active0 & 0x2000000000L) != 0L)
                return jjStartNfaWithStates_0(6, 37, 29);
            else
                return jjMoveStringLiteralDfa7_0(active0, 0x2000008000000L);

        case 108: // 'l'
            return jjMoveStringLiteralDfa7_0(active0, 0x20000000000000L);

        case 110: // 'n'
            if((active0 & 64L) != 0L)
                return jjStartNfaWithStates_0(6, 6, 29);
            break;

        case 111: // 'o'
            return jjMoveStringLiteralDfa7_0(active0, 0x200000000000L);

        case 115: // 's'
            if((active0 & 0x80000L) != 0L)
                return jjStartNfaWithStates_0(6, 19, 29);
            break;

        case 116: // 't'
            if((active0 & 32768L) != 0L)
                return jjStartNfaWithStates_0(6, 15, 29);
            else
                return jjMoveStringLiteralDfa7_0(active0, 0x4000000000L);

        case 117: // 'u'
            return jjMoveStringLiteralDfa7_0(active0, 16384L);

        case 121: // 'y'
            if((active0 & 0x400000L) != 0L)
                return jjStartNfaWithStates_0(6, 22, 29);
            break;
        }
        return jjStartNfa_0(5, active0, 0L);
    }

    private final int jjMoveStringLiteralDfa7_0(long old0, long active0)
    {
        if((active0 &= old0) == 0L)
            return jjStartNfa_0(5, old0, 0L);
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(6, active0, 0L);
            return 7;
        }
        switch(curChar)
        {
        case 99: // 'c'
            return jjMoveStringLiteralDfa8_0(active0, 0x80000000L);

        case 101: // 'e'
            if((active0 & 16384L) != 0L)
                return jjStartNfaWithStates_0(7, 14, 29);
            if((active0 & 0x20000000000000L) != 0L)
                return jjStartNfaWithStates_0(7, 53, 29);
            else
                return jjMoveStringLiteralDfa8_0(active0, 0x4020000000L);

        case 110: // 'n'
            return jjMoveStringLiteralDfa8_0(active0, 0x2200008000000L);

        case 116: // 't'
            if((active0 & 32L) != 0L)
                return jjStartNfaWithStates_0(7, 5, 29);
            break;
        }
        return jjStartNfa_0(6, active0, 0L);
    }

    private final int jjMoveStringLiteralDfa8_0(long old0, long active0)
    {
        if((active0 &= old0) == 0L)
            return jjStartNfa_0(6, old0, 0L);
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(7, active0, 0L);
            return 8;
        }
        switch(curChar)
        {
        default:
            break;

        case 100: // 'd'
            if((active0 & 0x4000000000L) != 0L)
                return jjStartNfaWithStates_0(8, 38, 29);
            break;

        case 101: // 'e'
            if((active0 & 0x80000000L) != 0L)
                return jjStartNfaWithStates_0(8, 31, 29);
            break;

        case 105: // 'i'
            return jjMoveStringLiteralDfa9_0(active0, 0x200000000000L);

        case 111: // 'o'
            return jjMoveStringLiteralDfa9_0(active0, 0x20000000L);

        case 116: // 't'
            if((active0 & 0x2000000000000L) != 0L)
                return jjStartNfaWithStates_0(8, 49, 29);
            else
                return jjMoveStringLiteralDfa9_0(active0, 0x8000000L);
        }
        return jjStartNfa_0(7, active0, 0L);
    }

    private final int jjMoveStringLiteralDfa9_0(long old0, long active0)
    {
        if((active0 &= old0) == 0L)
            return jjStartNfa_0(7, old0, 0L);
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(8, active0, 0L);
            return 9;
        }
        switch(curChar)
        {
        default:
            break;

        case 102: // 'f'
            if((active0 & 0x20000000L) != 0L)
                return jjStartNfaWithStates_0(9, 29, 29);
            break;

        case 115: // 's'
            if((active0 & 0x8000000L) != 0L)
                return jjStartNfaWithStates_0(9, 27, 29);
            break;

        case 122: // 'z'
            return jjMoveStringLiteralDfa10_0(active0, 0x200000000000L);
        }
        return jjStartNfa_0(8, active0, 0L);
    }

    private final int jjMoveStringLiteralDfa10_0(long old0, long active0)
    {
        if((active0 &= old0) == 0L)
            return jjStartNfa_0(8, old0, 0L);
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(9, active0, 0L);
            return 10;
        }
        switch(curChar)
        {
        case 101: // 'e'
            return jjMoveStringLiteralDfa11_0(active0, 0x200000000000L);
        }
        return jjStartNfa_0(9, active0, 0L);
    }

    private final int jjMoveStringLiteralDfa11_0(long old0, long active0)
    {
        if((active0 &= old0) == 0L)
            return jjStartNfa_0(9, old0, 0L);
        try
        {
            curChar = input_stream.readChar();
        }
        catch(IOException ioexception)
        {
            jjStopStringLiteralDfa_0(10, active0, 0L);
            return 11;
        }
        switch(curChar)
        {
        case 100: // 'd'
            if((active0 & 0x200000000000L) != 0L)
                return jjStartNfaWithStates_0(11, 45, 29);
            break;
        }
        return jjStartNfa_0(10, active0, 0L);
    }

    private final void jjCheckNAdd(int state)
    {
        if(jjrounds[state] != jjround)
        {
            jjstateSet[jjnewStateCnt++] = state;
            jjrounds[state] = jjround;
        }
    }

    private final void jjAddStates(int start, int end)
    {
        do
            jjstateSet[jjnewStateCnt++] = jjnextStates[start];
        while(start++ != end);
    }

    private final void jjCheckNAddTwoStates(int state1, int state2)
    {
        jjCheckNAdd(state1);
        jjCheckNAdd(state2);
    }

    private final void jjCheckNAddStates(int start, int end)
    {
        do
            jjCheckNAdd(jjnextStates[start]);
        while(start++ != end);
    }

    private final void jjCheckNAddStates(int start)
    {
        jjCheckNAdd(jjnextStates[start]);
        jjCheckNAdd(jjnextStates[start + 1]);
    }

    private final int jjMoveNfa_0(int startState, int curPos)
    {
        int startsAt = 0;
        jjnewStateCnt = 68;
        int i = 1;
        jjstateSet[0] = startState;
        int kind = 0x7fffffff;
        do
        {
            if(++jjround == 0x7fffffff)
                ReInitRounds();
            if(curChar < '@')
            {
                long l = 1L << curChar;
                do
                    switch(jjstateSet[--i])
                    {
                    case 50: // '2'
                        if(curChar == '*')
                            jjCheckNAddTwoStates(63, 64);
                        else
                        if(curChar == '/')
                            jjCheckNAddStates(0, 2);
                        if(curChar == '*')
                            jjstateSet[jjnewStateCnt++] = 55;
                        break;

                    case 1: // '\001'
                        if((0x3ff000000000000L & l) != 0L)
                            jjCheckNAddStates(3, 9);
                        else
                        if((0x100003600L & l) != 0L)
                        {
                            if(kind > 1)
                                kind = 1;
                            jjCheckNAdd(0);
                        } else
                        if(curChar == '/')
                            jjAddStates(10, 12);
                        else
                        if(curChar == '$')
                        {
                            if(kind > 63)
                                kind = 63;
                            jjCheckNAdd(29);
                        } else
                        if(curChar == '"')
                            jjCheckNAddStates(13, 15);
                        else
                        if(curChar == '\'')
                            jjAddStates(16, 17);
                        else
                        if(curChar == '.')
                            jjCheckNAdd(5);
                        if((0x3fe000000000000L & l) != 0L)
                        {
                            if(kind > 55)
                                kind = 55;
                            jjCheckNAddTwoStates(2, 3);
                        } else
                        if(curChar == '0')
                        {
                            if(kind > 55)
                                kind = 55;
                            jjCheckNAddStates(18, 20);
                        }
                        break;

                    case 0: // '\0'
                        if((0x100003600L & l) != 0L)
                        {
                            if(kind > 1)
                                kind = 1;
                            jjCheckNAdd(0);
                        }
                        break;

                    case 2: // '\002'
                        if((0x3ff000000000000L & l) != 0L)
                        {
                            if(kind > 55)
                                kind = 55;
                            jjCheckNAddTwoStates(2, 3);
                        }
                        break;

                    case 4: // '\004'
                        if(curChar == '.')
                            jjCheckNAdd(5);
                        break;

                    case 5: // '\005'
                        if((0x3ff000000000000L & l) != 0L)
                        {
                            if(kind > 59)
                                kind = 59;
                            jjCheckNAddStates(21, 23);
                        }
                        break;

                    case 7: // '\007'
                        if((0x280000000000L & l) != 0L)
                            jjCheckNAdd(8);
                        break;

                    case 8: // '\b'
                        if((0x3ff000000000000L & l) != 0L)
                        {
                            if(kind > 59)
                                kind = 59;
                            jjCheckNAddTwoStates(8, 9);
                        }
                        break;

                    case 10: // '\n'
                        if(curChar == '\'')
                            jjAddStates(16, 17);
                        break;

                    case 11: // '\013'
                        if((0xffffff7fffffdbffL & l) != 0L)
                            jjCheckNAdd(12);
                        break;

                    case 12: // '\f'
                        if(curChar == '\'' && kind > 61)
                            kind = 61;
                        break;

                    case 14: // '\016'
                        if((0x8400000000L & l) != 0L)
                            jjCheckNAdd(12);
                        break;

                    case 15: // '\017'
                        if((0xff000000000000L & l) != 0L)
                            jjCheckNAddTwoStates(16, 12);
                        break;

                    case 16: // '\020'
                        if((0xff000000000000L & l) != 0L)
                            jjCheckNAdd(12);
                        break;

                    case 17: // '\021'
                        if((0xf000000000000L & l) != 0L)
                            jjstateSet[jjnewStateCnt++] = 18;
                        break;

                    case 18: // '\022'
                        if((0xff000000000000L & l) != 0L)
                            jjCheckNAdd(16);
                        break;

                    case 19: // '\023'
                        if(curChar == '"')
                            jjCheckNAddStates(13, 15);
                        break;

                    case 20: // '\024'
                        if((0xfffffffbffffdbffL & l) != 0L)
                            jjCheckNAddStates(13, 15);
                        break;

                    case 22: // '\026'
                        if((0x8400000000L & l) != 0L)
                            jjCheckNAddStates(13, 15);
                        break;

                    case 23: // '\027'
                        if(curChar == '"' && kind > 62)
                            kind = 62;
                        break;

                    case 24: // '\030'
                        if((0xff000000000000L & l) != 0L)
                            jjCheckNAddStates(24, 27);
                        break;

                    case 25: // '\031'
                        if((0xff000000000000L & l) != 0L)
                            jjCheckNAddStates(13, 15);
                        break;

                    case 26: // '\032'
                        if((0xf000000000000L & l) != 0L)
                            jjstateSet[jjnewStateCnt++] = 27;
                        break;

                    case 27: // '\033'
                        if((0xff000000000000L & l) != 0L)
                            jjCheckNAdd(25);
                        break;

                    case 28: // '\034'
                        if(curChar == '$')
                        {
                            if(kind > 63)
                                kind = 63;
                            jjCheckNAdd(29);
                        }
                        break;

                    case 29: // '\035'
                        if((0x3ff001000000000L & l) != 0L)
                        {
                            if(kind > 63)
                                kind = 63;
                            jjCheckNAdd(29);
                        }
                        break;

                    case 30: // '\036'
                        if((0x3ff000000000000L & l) != 0L)
                            jjCheckNAddStates(3, 9);
                        break;

                    case 31: // '\037'
                        if((0x3ff000000000000L & l) != 0L)
                            jjCheckNAddTwoStates(31, 32);
                        break;

                    case 32: // ' '
                        if(curChar == '.')
                        {
                            if(kind > 59)
                                kind = 59;
                            jjCheckNAddStates(28, 30);
                        }
                        break;

                    case 33: // '!'
                        if((0x3ff000000000000L & l) != 0L)
                        {
                            if(kind > 59)
                                kind = 59;
                            jjCheckNAddStates(28, 30);
                        }
                        break;

                    case 35: // '#'
                        if((0x280000000000L & l) != 0L)
                            jjCheckNAdd(36);
                        break;

                    case 36: // '$'
                        if((0x3ff000000000000L & l) != 0L)
                        {
                            if(kind > 59)
                                kind = 59;
                            jjCheckNAddTwoStates(36, 9);
                        }
                        break;

                    case 37: // '%'
                        if((0x3ff000000000000L & l) != 0L)
                            jjCheckNAddTwoStates(37, 38);
                        break;

                    case 39: // '\''
                        if((0x280000000000L & l) != 0L)
                            jjCheckNAdd(40);
                        break;

                    case 40: // '('
                        if((0x3ff000000000000L & l) != 0L)
                        {
                            if(kind > 59)
                                kind = 59;
                            jjCheckNAddTwoStates(40, 9);
                        }
                        break;

                    case 41: // ')'
                        if((0x3ff000000000000L & l) != 0L)
                            jjCheckNAddStates(31, 33);
                        break;

                    case 43: // '+'
                        if((0x280000000000L & l) != 0L)
                            jjCheckNAdd(44);
                        break;

                    case 44: // ','
                        if((0x3ff000000000000L & l) != 0L)
                            jjCheckNAddTwoStates(44, 9);
                        break;

                    case 45: // '-'
                        if(curChar == '0')
                        {
                            if(kind > 55)
                                kind = 55;
                            jjCheckNAddStates(18, 20);
                        }
                        break;

                    case 47: // '/'
                        if((0x3ff000000000000L & l) != 0L)
                        {
                            if(kind > 55)
                                kind = 55;
                            jjCheckNAddTwoStates(47, 3);
                        }
                        break;

                    case 48: // '0'
                        if((0xff000000000000L & l) != 0L)
                        {
                            if(kind > 55)
                                kind = 55;
                            jjCheckNAddTwoStates(48, 3);
                        }
                        break;

                    case 49: // '1'
                        if(curChar == '/')
                            jjAddStates(10, 12);
                        break;

                    case 51: // '3'
                        if((-9217L & l) != 0L)
                            jjCheckNAddStates(0, 2);
                        break;

                    case 52: // '4'
                        if((9216L & l) != 0L && kind > 2)
                            kind = 2;
                        break;

                    case 53: // '5'
                        if(curChar == '\n' && kind > 2)
                            kind = 2;
                        break;

                    case 54: // '6'
                        if(curChar == '\r')
                            jjstateSet[jjnewStateCnt++] = 53;
                        break;

                    case 55: // '7'
                        if(curChar == '*')
                            jjCheckNAddTwoStates(56, 57);
                        break;

                    case 56: // '8'
                        if((0xfffffbffffffffffL & l) != 0L)
                            jjCheckNAddTwoStates(56, 57);
                        break;

                    case 57: // '9'
                        if(curChar == '*')
                            jjCheckNAddStates(34, 36);
                        break;

                    case 58: // ':'
                        if((0xffff7bffffffffffL & l) != 0L)
                            jjCheckNAddTwoStates(59, 57);
                        break;

                    case 59: // ';'
                        if((0xfffffbffffffffffL & l) != 0L)
                            jjCheckNAddTwoStates(59, 57);
                        break;

                    case 60: // '<'
                        if(curChar == '/' && kind > 3)
                            kind = 3;
                        break;

                    case 61: // '='
                        if(curChar == '*')
                            jjstateSet[jjnewStateCnt++] = 55;
                        break;

                    case 62: // '>'
                        if(curChar == '*')
                            jjCheckNAddTwoStates(63, 64);
                        break;

                    case 63: // '?'
                        if((0xfffffbffffffffffL & l) != 0L)
                            jjCheckNAddTwoStates(63, 64);
                        break;

                    case 64: // '@'
                        if(curChar == '*')
                            jjCheckNAddStates(37, 39);
                        break;

                    case 65: // 'A'
                        if((0xffff7bffffffffffL & l) != 0L)
                            jjCheckNAddTwoStates(66, 64);
                        break;

                    case 66: // 'B'
                        if((0xfffffbffffffffffL & l) != 0L)
                            jjCheckNAddTwoStates(66, 64);
                        break;

                    case 67: // 'C'
                        if(curChar == '/' && kind > 4)
                            kind = 4;
                        break;
                    }
                while(i != startsAt);
            } else
            if(curChar < '\200')
            {
                long l = 1L << (curChar & 0x3f);
                do
                    switch(jjstateSet[--i])
                    {
                    case 1: // '\001'
                    case 29: // '\035'
                        if((0x7fffffe87fffffeL & l) != 0L)
                        {
                            if(kind > 63)
                                kind = 63;
                            jjCheckNAdd(29);
                        }
                        break;

                    case 3: // '\003'
                        if((0x100000001000L & l) != 0L && kind > 55)
                            kind = 55;
                        break;

                    case 6: // '\006'
                        if((0x2000000020L & l) != 0L)
                            jjAddStates(40, 41);
                        break;

                    case 9: // '\t'
                        if((0x5000000050L & l) != 0L && kind > 59)
                            kind = 59;
                        break;

                    case 11: // '\013'
                        if((0xffffffffefffffffL & l) != 0L)
                            jjCheckNAdd(12);
                        break;

                    case 13: // '\r'
                        if(curChar == '\\')
                            jjAddStates(42, 44);
                        break;

                    case 14: // '\016'
                        if((0x14404410000000L & l) != 0L)
                            jjCheckNAdd(12);
                        break;

                    case 20: // '\024'
                        if((0xffffffffefffffffL & l) != 0L)
                            jjCheckNAddStates(13, 15);
                        break;

                    case 21: // '\025'
                        if(curChar == '\\')
                            jjAddStates(45, 47);
                        break;

                    case 22: // '\026'
                        if((0x14404410000000L & l) != 0L)
                            jjCheckNAddStates(13, 15);
                        break;

                    case 34: // '"'
                        if((0x2000000020L & l) != 0L)
                            jjAddStates(48, 49);
                        break;

                    case 38: // '&'
                        if((0x2000000020L & l) != 0L)
                            jjAddStates(50, 51);
                        break;

                    case 42: // '*'
                        if((0x2000000020L & l) != 0L)
                            jjAddStates(52, 53);
                        break;

                    case 46: // '.'
                        if((0x100000001000000L & l) != 0L)
                            jjCheckNAdd(47);
                        break;

                    case 47: // '/'
                        if((0x7e0000007eL & l) != 0L)
                        {
                            if(kind > 55)
                                kind = 55;
                            jjCheckNAddTwoStates(47, 3);
                        }
                        break;

                    case 51: // '3'
                        jjAddStates(0, 2);
                        break;

                    case 56: // '8'
                        jjCheckNAddTwoStates(56, 57);
                        break;

                    case 58: // ':'
                    case 59: // ';'
                        jjCheckNAddTwoStates(59, 57);
                        break;

                    case 63: // '?'
                        jjCheckNAddTwoStates(63, 64);
                        break;

                    case 65: // 'A'
                    case 66: // 'B'
                        jjCheckNAddTwoStates(66, 64);
                        break;
                    }
                while(i != startsAt);
            } else
            {
                int hiByte = curChar >> 8;
                int i1 = hiByte >> 6;
                long l1 = 1L << (hiByte & 0x3f);
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 0x3f);
                do
                    switch(jjstateSet[--i])
                    {
                    case 1: // '\001'
                    case 29: // '\035'
                        if(jjCanMove_1(hiByte, i1, i2, l1, l2))
                        {
                            if(kind > 63)
                                kind = 63;
                            jjCheckNAdd(29);
                        }
                        break;

                    case 11: // '\013'
                        if(jjCanMove_0(hiByte, i1, i2, l1, l2))
                            jjstateSet[jjnewStateCnt++] = 12;
                        break;

                    case 20: // '\024'
                        if(jjCanMove_0(hiByte, i1, i2, l1, l2))
                            jjAddStates(13, 15);
                        break;

                    case 51: // '3'
                        if(jjCanMove_0(hiByte, i1, i2, l1, l2))
                            jjAddStates(0, 2);
                        break;

                    case 56: // '8'
                        if(jjCanMove_0(hiByte, i1, i2, l1, l2))
                            jjCheckNAddTwoStates(56, 57);
                        break;

                    case 58: // ':'
                    case 59: // ';'
                        if(jjCanMove_0(hiByte, i1, i2, l1, l2))
                            jjCheckNAddTwoStates(59, 57);
                        break;

                    case 63: // '?'
                        if(jjCanMove_0(hiByte, i1, i2, l1, l2))
                            jjCheckNAddTwoStates(63, 64);
                        break;

                    case 65: // 'A'
                    case 66: // 'B'
                        if(jjCanMove_0(hiByte, i1, i2, l1, l2))
                            jjCheckNAddTwoStates(66, 64);
                        break;
                    }
                while(i != startsAt);
            }
            if(kind != 0x7fffffff)
            {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            curPos++;
            if((i = jjnewStateCnt) == (startsAt = 68 - (jjnewStateCnt = startsAt)))
                return curPos;
            try
            {
                curChar = input_stream.readChar();
            }
            catch(IOException ioexception)
            {
                return curPos;
            }
        } while(true);
    }

    private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
    {
        switch(hiByte)
        {
        case 0: // '\0'
            return (jjbitVec2[i2] & l2) != 0L;
        }
        return (jjbitVec0[i1] & l1) != 0L;
    }

    private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2)
    {
        switch(hiByte)
        {
        case 0: // '\0'
            return (jjbitVec4[i2] & l2) != 0L;

        case 48: // '0'
            return (jjbitVec5[i2] & l2) != 0L;

        case 49: // '1'
            return (jjbitVec6[i2] & l2) != 0L;

        case 51: // '3'
            return (jjbitVec7[i2] & l2) != 0L;

        case 61: // '='
            return (jjbitVec8[i2] & l2) != 0L;
        }
        return (jjbitVec3[i1] & l1) != 0L;
    }

    public JevaESParserTokenManager(ASCII_UCodeESC_CharStream stream)
    {
        jjrounds = new int[68];
        jjstateSet = new int[136];
        curLexState = 0;
        defaultLexState = 0;
        input_stream = stream;
    }

    public JevaESParserTokenManager(ASCII_UCodeESC_CharStream stream, int lexState)
    {
        this(stream);
        SwitchTo(lexState);
    }

    public void ReInit(ASCII_UCodeESC_CharStream stream)
    {
        jjmatchedPos = jjnewStateCnt = 0;
        curLexState = defaultLexState;
        input_stream = stream;
        ReInitRounds();
    }

    private final void ReInitRounds()
    {
        jjround = 0x80000001;
        for(int i = 68; i-- > 0;)
            jjrounds[i] = 0x80000000;

    }

    public void ReInit(ASCII_UCodeESC_CharStream stream, int lexState)
    {
        ReInit(stream);
        SwitchTo(lexState);
    }

    public void SwitchTo(int lexState)
    {
        if(lexState >= 1 || lexState < 0)
        {
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
        } else
        {
            curLexState = lexState;
            return;
        }
    }

    private final Token jjFillToken()
    {
        Token t = Token.newToken(jjmatchedKind);
        t.kind = jjmatchedKind;
        String im = jjstrLiteralImages[jjmatchedKind];
        t.image = im != null ? im : input_stream.GetImage();
        t.beginLine = input_stream.getBeginLine();
        t.beginColumn = input_stream.getBeginColumn();
        t.endLine = input_stream.getEndLine();
        t.endColumn = input_stream.getEndColumn();
        return t;
    }

    public final Token getNextToken()
    {
        Token specialToken = null;
        int curPos = 0;
        do
        {
            try
            {
                curChar = input_stream.BeginToken();
            }
            catch(IOException ioexception)
            {
                jjmatchedKind = 0;
                Token matchedToken = jjFillToken();
                matchedToken.specialToken = specialToken;
                return matchedToken;
            }
            jjmatchedKind = 0x7fffffff;
            jjmatchedPos = 0;
            curPos = jjMoveStringLiteralDfa0_0();
            if(jjmatchedKind == 0x7fffffff)
                break;
            if(jjmatchedPos + 1 < curPos)
                input_stream.backup(curPos - jjmatchedPos - 1);
            if((jjtoToken[jjmatchedKind >> 6] & 1L << (jjmatchedKind & 0x3f)) != 0L)
            {
                Token matchedToken = jjFillToken();
                matchedToken.specialToken = specialToken;
                return matchedToken;
            }
            if((jjtoSpecial[jjmatchedKind >> 6] & 1L << (jjmatchedKind & 0x3f)) != 0L)
            {
                Token matchedToken = jjFillToken();
                if(specialToken == null)
                {
                    specialToken = matchedToken;
                } else
                {
                    matchedToken.specialToken = specialToken;
                    specialToken = specialToken.next = matchedToken;
                }
            }
        } while(true);
        int error_line = input_stream.getEndLine();
        int error_column = input_stream.getEndColumn();
        String error_after = null;
        boolean EOFSeen = false;
        try
        {
            input_stream.readChar();
            input_stream.backup(1);
        }
        catch(IOException ioexception1)
        {
            EOFSeen = true;
            error_after = curPos > 1 ? input_stream.GetImage() : "";
            if(curChar == '\n' || curChar == '\r')
            {
                error_line++;
                error_column = 0;
            } else
            {
                error_column++;
            }
        }
        if(!EOFSeen)
        {
            input_stream.backup(1);
            error_after = curPos > 1 ? input_stream.GetImage() : "";
        }
        throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, 0);
    }

    static final long jjbitVec0[] = {
        -2L, -1L, -1L, -1L
    };
    static final long jjbitVec2[] = {
        0L, 0L, -1L, -1L
    };
    static final long jjbitVec3[] = {
        0x1ff00000fffffffeL, -16384L, 0xffffffffL, 0x600000000000000L
    };
    static final long jjbitVec4[] = {
        0L, 0L, 0L, 0xff7fffffff7fffffL
    };
    static final long jjbitVec5[] = {
        0L, -1L, -1L, -1L
    };
    static final long jjbitVec6[] = {
        -1L, -1L, 65535L, 0L
    };
    static final long jjbitVec7[] = {
        -1L, -1L, 0L, 0L
    };
    static final long jjbitVec8[] = {
        0x3fffffffffffL, 0L, 0L, 0L
    };
    static final int jjnextStates[] = {
        51, 52, 54, 31, 32, 37, 38, 41, 42, 9, 
        50, 61, 62, 20, 21, 23, 11, 13, 46, 48, 
        3, 5, 6, 9, 20, 21, 25, 23, 33, 34, 
        9, 41, 42, 9, 57, 58, 60, 64, 65, 67, 
        7, 8, 14, 15, 17, 22, 24, 26, 35, 36, 
        39, 40, 43, 44
    };
    public static final String jjstrLiteralImages[] = {
        "", null, null, null, null, "abstract", "boolean", "break", "byte", "case", 
        "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "extends", 
        "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", 
        "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", 
        "return", "short", "static", "super", "switch", "synchronized", "this", "throw", "throws", "transient", 
        "true", "try", "void", "volatile", "while", null, null, null, null, null, 
        null, null, null, null, null, null, "(", ")", "{", "}", 
        "[", "]", ";", ",", ".", "=", ">", "<", "!", "~", 
        "?", ":", "==", "<=", ">=", "!=", "||", "&&", "++", "--", 
        "+", "-", "*", "/", "&", "|", "^", "%", "<<", ">>", 
        ">>>", "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", 
        ">>=", ">>>="
    };
    public static final String lexStateNames[] = {
        "DEFAULT"
    };
    static final long jjtoToken[] = {
        0xe8ffffffffffffe1L, 0xfffffffffffcL
    };
    static final long jjtoSkip[] = {
        30L, 0L
    };
    static final long jjtoSpecial[] = {
        30L, 0L
    };
    private ASCII_UCodeESC_CharStream input_stream;
    private final int jjrounds[];
    private final int jjstateSet[];
    protected char curChar;
    int curLexState;
    int defaultLexState;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;

}
