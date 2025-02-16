// Generated from D:/Files/Facultate/CPL/teme-main/tema3/Tema2/src/cool/lexer/CoolLexer.g4 by ANTLR 4.13.2

    package cool.lexer;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class CoolLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ERROR=1, CASE=2, OF=3, ESAC=4, CLASS=5, INHERITS=6, IF=7, THEN=8, ELSE=9, 
		FI=10, BOOL=11, TYPE=12, FOR=13, DO=14, SEMI=15, COMMA=16, LPAREN=17, 
		RPAREN=18, LBRACE=19, RBRACE=20, PLUS=21, MINUS=22, MULT=23, DIV=24, EQUAL=25, 
		LT=26, LE=27, DOT=28, COLON=29, ASSIGN=30, RESULT=31, ANNOTATE=32, NEW=33, 
		ISVOID=34, NOT=35, COMPLEMENT=36, WHILE=37, LOOP=38, POOL=39, LET=40, 
		IN=41, ID=42, INT=43, STRING=44, LINE_COMMENT=45, BLOCK_COMMENT=46, ERROR_COMMENT=47, 
		WS=48, INVALID_CHARACTER=49;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"CASE", "OF", "ESAC", "CLASS", "INHERITS", "IF", "THEN", "ELSE", "FI", 
			"BOOL", "SELF_TYPE", "TYPE", "FOR", "DO", "SEMI", "COMMA", "LPAREN", 
			"RPAREN", "LBRACE", "RBRACE", "PLUS", "MINUS", "MULT", "DIV", "EQUAL", 
			"LT", "LE", "DOT", "COLON", "ASSIGN", "RESULT", "ANNOTATE", "NEW", "ISVOID", 
			"NOT", "COMPLEMENT", "WHILE", "LOOP", "POOL", "LET", "IN", "LETTER", 
			"ID", "DIGIT", "INT", "DIGITS", "FRACTION", "EXPONENT", "STRING", "NEW_LINE", 
			"LINE_COMMENT", "BLOCK_COMMENT", "ERROR_COMMENT", "WS", "INVALID_CHARACTER"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'case'", "'of'", "'esac'", "'class'", "'inherits'", "'if'", 
			"'then'", "'else'", "'fi'", null, null, "'for'", "'do'", "';'", "','", 
			"'('", "')'", "'{'", "'}'", "'+'", "'-'", "'*'", "'/'", "'='", "'<'", 
			"'<='", "'.'", "':'", "'<-'", "'=>'", "'@'", "'new'", "'isvoid'", "'not'", 
			"'~'", "'while'", "'loop'", "'pool'", "'let'", "'in'", null, null, null, 
			null, null, "'*)'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ERROR", "CASE", "OF", "ESAC", "CLASS", "INHERITS", "IF", "THEN", 
			"ELSE", "FI", "BOOL", "TYPE", "FOR", "DO", "SEMI", "COMMA", "LPAREN", 
			"RPAREN", "LBRACE", "RBRACE", "PLUS", "MINUS", "MULT", "DIV", "EQUAL", 
			"LT", "LE", "DOT", "COLON", "ASSIGN", "RESULT", "ANNOTATE", "NEW", "ISVOID", 
			"NOT", "COMPLEMENT", "WHILE", "LOOP", "POOL", "LET", "IN", "ID", "INT", 
			"STRING", "LINE_COMMENT", "BLOCK_COMMENT", "ERROR_COMMENT", "WS", "INVALID_CHARACTER"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	    private void raiseError(String msg) {
	        setText(msg);
	        setType(ERROR);
	    }

	    private void getString(){
	        setText(getText().substring(1, getText().length() - 1));
	        if(getText().contains("\0")) {
	            raiseError("String contains null character");
	        }
	        if(getText().length() > 1024) {
	            raiseError("String constant too long");
	        }
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < getText().length(); i++) {
	            if (getText().charAt(i) == '\\') {
	                i++;
	                switch (getText().charAt(i)) {
	                    case 'b':
	                        sb.append('\b');
	                        break;
	                    case 't':
	                        sb.append('\t');
	                        break;
	                    case 'n':
	                        sb.append('\n');
	                        break;
	                    case 'f':
	                        sb.append('\f');
	                        break;
	                    default:
	                        sb.append(getText().charAt(i));
	                        break;
	                }
	            } else {
	                sb.append(getText().charAt(i));
	            }
	        }
	        setText(sb.toString());
	     }


	public CoolLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CoolLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 48:
			STRING_action((RuleContext)_localctx, actionIndex);
			break;
		case 51:
			BLOCK_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 52:
			ERROR_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 54:
			INVALID_CHARACTER_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void STRING_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 getString(); 
			break;
		case 1:
			 raiseError("EOF in string constant"); 
			break;
		case 2:
			 raiseError("Unterminated string constant"); 
			break;
		}
	}
	private void BLOCK_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			 skip(); 
			break;
		case 4:
			 raiseError("EOF in comment"); 
			break;
		}
	}
	private void ERROR_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 5:
			 raiseError("Unmatched *)"); 
			break;
		}
	}
	private void INVALID_CHARACTER_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 6:
			 raiseError("Invalid character: " + getText()); 
			break;
		}
	}

	public static final String _serializedATN =
		"\u0004\u00001\u0187\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u0007"+
		"0\u00021\u00071\u00022\u00072\u00023\u00073\u00024\u00074\u00025\u0007"+
		"5\u00026\u00076\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u00a5\b\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u00bc\b\u000b"+
		"\n\u000b\f\u000b\u00bf\t\u000b\u0001\u000b\u0003\u000b\u00c2\b\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f"+
		"\u0001\u001f\u0001 \u0001 \u0001 \u0001 \u0001!\u0001!\u0001!\u0001!\u0001"+
		"!\u0001!\u0001!\u0001\"\u0001\"\u0001\"\u0001\"\u0001#\u0001#\u0001$\u0001"+
		"$\u0001$\u0001$\u0001$\u0001$\u0001%\u0001%\u0001%\u0001%\u0001%\u0001"+
		"&\u0001&\u0001&\u0001&\u0001&\u0001\'\u0001\'\u0001\'\u0001\'\u0001(\u0001"+
		"(\u0001(\u0001)\u0001)\u0001*\u0001*\u0003*\u011e\b*\u0001*\u0001*\u0001"+
		"*\u0005*\u0123\b*\n*\f*\u0126\t*\u0001+\u0001+\u0001,\u0004,\u012b\b,"+
		"\u000b,\f,\u012c\u0001-\u0004-\u0130\b-\u000b-\f-\u0131\u0001.\u0001."+
		"\u0003.\u0136\b.\u0003.\u0138\b.\u0001/\u0001/\u0003/\u013c\b/\u0001/"+
		"\u0001/\u00010\u00010\u00010\u00010\u00010\u00010\u00050\u0146\b0\n0\f"+
		"0\u0149\t0\u00010\u00010\u00010\u00010\u00010\u00010\u00030\u0151\b0\u0001"+
		"1\u00031\u0154\b1\u00011\u00011\u00012\u00012\u00012\u00012\u00052\u015c"+
		"\b2\n2\f2\u015f\t2\u00012\u00012\u00032\u0163\b2\u00012\u00012\u00013"+
		"\u00013\u00013\u00013\u00013\u00053\u016c\b3\n3\f3\u016f\t3\u00013\u0001"+
		"3\u00013\u00013\u00013\u00013\u00033\u0177\b3\u00014\u00014\u00014\u0001"+
		"4\u00014\u00015\u00045\u017f\b5\u000b5\f5\u0180\u00015\u00015\u00016\u0001"+
		"6\u00016\u0003\u0147\u015d\u016d\u00007\u0001\u0002\u0003\u0003\u0005"+
		"\u0004\u0007\u0005\t\u0006\u000b\u0007\r\b\u000f\t\u0011\n\u0013\u000b"+
		"\u0015\u0000\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011"+
		"#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b"+
		"7\u001c9\u001d;\u001e=\u001f? A!C\"E#G$I%K&M\'O(Q)S\u0000U*W\u0000Y+["+
		"\u0000]\u0000_\u0000a,c\u0000e-g.i/k0m1\u0001\u0000\u0005\u0001\u0000"+
		"AZ\u0002\u0000AZaz\u0001\u000009\u0002\u0000++--\u0003\u0000\t\n\f\r "+
		" \u019b\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000"+
		"\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000"+
		"\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000"+
		"\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000"+
		"\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000"+
		"\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000"+
		"\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000"+
		"\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001"+
		"\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000"+
		"\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000"+
		"\u0000-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001"+
		"\u0001\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000"+
		"\u0000\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000"+
		"\u0000;\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?"+
		"\u0001\u0000\u0000\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000"+
		"\u0000\u0000\u0000E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000"+
		"\u0000I\u0001\u0000\u0000\u0000\u0000K\u0001\u0000\u0000\u0000\u0000M"+
		"\u0001\u0000\u0000\u0000\u0000O\u0001\u0000\u0000\u0000\u0000Q\u0001\u0000"+
		"\u0000\u0000\u0000U\u0001\u0000\u0000\u0000\u0000Y\u0001\u0000\u0000\u0000"+
		"\u0000a\u0001\u0000\u0000\u0000\u0000e\u0001\u0000\u0000\u0000\u0000g"+
		"\u0001\u0000\u0000\u0000\u0000i\u0001\u0000\u0000\u0000\u0000k\u0001\u0000"+
		"\u0000\u0000\u0000m\u0001\u0000\u0000\u0000\u0001o\u0001\u0000\u0000\u0000"+
		"\u0003t\u0001\u0000\u0000\u0000\u0005w\u0001\u0000\u0000\u0000\u0007|"+
		"\u0001\u0000\u0000\u0000\t\u0082\u0001\u0000\u0000\u0000\u000b\u008b\u0001"+
		"\u0000\u0000\u0000\r\u008e\u0001\u0000\u0000\u0000\u000f\u0093\u0001\u0000"+
		"\u0000\u0000\u0011\u0098\u0001\u0000\u0000\u0000\u0013\u00a4\u0001\u0000"+
		"\u0000\u0000\u0015\u00a6\u0001\u0000\u0000\u0000\u0017\u00c1\u0001\u0000"+
		"\u0000\u0000\u0019\u00c3\u0001\u0000\u0000\u0000\u001b\u00c7\u0001\u0000"+
		"\u0000\u0000\u001d\u00ca\u0001\u0000\u0000\u0000\u001f\u00cc\u0001\u0000"+
		"\u0000\u0000!\u00ce\u0001\u0000\u0000\u0000#\u00d0\u0001\u0000\u0000\u0000"+
		"%\u00d2\u0001\u0000\u0000\u0000\'\u00d4\u0001\u0000\u0000\u0000)\u00d6"+
		"\u0001\u0000\u0000\u0000+\u00d8\u0001\u0000\u0000\u0000-\u00da\u0001\u0000"+
		"\u0000\u0000/\u00dc\u0001\u0000\u0000\u00001\u00de\u0001\u0000\u0000\u0000"+
		"3\u00e0\u0001\u0000\u0000\u00005\u00e2\u0001\u0000\u0000\u00007\u00e5"+
		"\u0001\u0000\u0000\u00009\u00e7\u0001\u0000\u0000\u0000;\u00e9\u0001\u0000"+
		"\u0000\u0000=\u00ec\u0001\u0000\u0000\u0000?\u00ef\u0001\u0000\u0000\u0000"+
		"A\u00f1\u0001\u0000\u0000\u0000C\u00f5\u0001\u0000\u0000\u0000E\u00fc"+
		"\u0001\u0000\u0000\u0000G\u0100\u0001\u0000\u0000\u0000I\u0102\u0001\u0000"+
		"\u0000\u0000K\u0108\u0001\u0000\u0000\u0000M\u010d\u0001\u0000\u0000\u0000"+
		"O\u0112\u0001\u0000\u0000\u0000Q\u0116\u0001\u0000\u0000\u0000S\u0119"+
		"\u0001\u0000\u0000\u0000U\u011d\u0001\u0000\u0000\u0000W\u0127\u0001\u0000"+
		"\u0000\u0000Y\u012a\u0001\u0000\u0000\u0000[\u012f\u0001\u0000\u0000\u0000"+
		"]\u0137\u0001\u0000\u0000\u0000_\u0139\u0001\u0000\u0000\u0000a\u013f"+
		"\u0001\u0000\u0000\u0000c\u0153\u0001\u0000\u0000\u0000e\u0157\u0001\u0000"+
		"\u0000\u0000g\u0166\u0001\u0000\u0000\u0000i\u0178\u0001\u0000\u0000\u0000"+
		"k\u017e\u0001\u0000\u0000\u0000m\u0184\u0001\u0000\u0000\u0000op\u0005"+
		"c\u0000\u0000pq\u0005a\u0000\u0000qr\u0005s\u0000\u0000rs\u0005e\u0000"+
		"\u0000s\u0002\u0001\u0000\u0000\u0000tu\u0005o\u0000\u0000uv\u0005f\u0000"+
		"\u0000v\u0004\u0001\u0000\u0000\u0000wx\u0005e\u0000\u0000xy\u0005s\u0000"+
		"\u0000yz\u0005a\u0000\u0000z{\u0005c\u0000\u0000{\u0006\u0001\u0000\u0000"+
		"\u0000|}\u0005c\u0000\u0000}~\u0005l\u0000\u0000~\u007f\u0005a\u0000\u0000"+
		"\u007f\u0080\u0005s\u0000\u0000\u0080\u0081\u0005s\u0000\u0000\u0081\b"+
		"\u0001\u0000\u0000\u0000\u0082\u0083\u0005i\u0000\u0000\u0083\u0084\u0005"+
		"n\u0000\u0000\u0084\u0085\u0005h\u0000\u0000\u0085\u0086\u0005e\u0000"+
		"\u0000\u0086\u0087\u0005r\u0000\u0000\u0087\u0088\u0005i\u0000\u0000\u0088"+
		"\u0089\u0005t\u0000\u0000\u0089\u008a\u0005s\u0000\u0000\u008a\n\u0001"+
		"\u0000\u0000\u0000\u008b\u008c\u0005i\u0000\u0000\u008c\u008d\u0005f\u0000"+
		"\u0000\u008d\f\u0001\u0000\u0000\u0000\u008e\u008f\u0005t\u0000\u0000"+
		"\u008f\u0090\u0005h\u0000\u0000\u0090\u0091\u0005e\u0000\u0000\u0091\u0092"+
		"\u0005n\u0000\u0000\u0092\u000e\u0001\u0000\u0000\u0000\u0093\u0094\u0005"+
		"e\u0000\u0000\u0094\u0095\u0005l\u0000\u0000\u0095\u0096\u0005s\u0000"+
		"\u0000\u0096\u0097\u0005e\u0000\u0000\u0097\u0010\u0001\u0000\u0000\u0000"+
		"\u0098\u0099\u0005f\u0000\u0000\u0099\u009a\u0005i\u0000\u0000\u009a\u0012"+
		"\u0001\u0000\u0000\u0000\u009b\u009c\u0005t\u0000\u0000\u009c\u009d\u0005"+
		"r\u0000\u0000\u009d\u009e\u0005u\u0000\u0000\u009e\u00a5\u0005e\u0000"+
		"\u0000\u009f\u00a0\u0005f\u0000\u0000\u00a0\u00a1\u0005a\u0000\u0000\u00a1"+
		"\u00a2\u0005l\u0000\u0000\u00a2\u00a3\u0005s\u0000\u0000\u00a3\u00a5\u0005"+
		"e\u0000\u0000\u00a4\u009b\u0001\u0000\u0000\u0000\u00a4\u009f\u0001\u0000"+
		"\u0000\u0000\u00a5\u0014\u0001\u0000\u0000\u0000\u00a6\u00a7\u0005S\u0000"+
		"\u0000\u00a7\u00a8\u0005E\u0000\u0000\u00a8\u00a9\u0005L\u0000\u0000\u00a9"+
		"\u00aa\u0005F\u0000\u0000\u00aa\u00ab\u0005_\u0000\u0000\u00ab\u00ac\u0005"+
		"T\u0000\u0000\u00ac\u00ad\u0005Y\u0000\u0000\u00ad\u00ae\u0005P\u0000"+
		"\u0000\u00ae\u00af\u0005E\u0000\u0000\u00af\u0016\u0001\u0000\u0000\u0000"+
		"\u00b0\u00b1\u0005I\u0000\u0000\u00b1\u00b2\u0005n\u0000\u0000\u00b2\u00c2"+
		"\u0005t\u0000\u0000\u00b3\u00b4\u0005B\u0000\u0000\u00b4\u00b5\u0005o"+
		"\u0000\u0000\u00b5\u00b6\u0005o\u0000\u0000\u00b6\u00c2\u0005l\u0000\u0000"+
		"\u00b7\u00bd\u0007\u0000\u0000\u0000\u00b8\u00bc\u0003W+\u0000\u00b9\u00bc"+
		"\u0003S)\u0000\u00ba\u00bc\u0005_\u0000\u0000\u00bb\u00b8\u0001\u0000"+
		"\u0000\u0000\u00bb\u00b9\u0001\u0000\u0000\u0000\u00bb\u00ba\u0001\u0000"+
		"\u0000\u0000\u00bc\u00bf\u0001\u0000\u0000\u0000\u00bd\u00bb\u0001\u0000"+
		"\u0000\u0000\u00bd\u00be\u0001\u0000\u0000\u0000\u00be\u00c2\u0001\u0000"+
		"\u0000\u0000\u00bf\u00bd\u0001\u0000\u0000\u0000\u00c0\u00c2\u0003\u0015"+
		"\n\u0000\u00c1\u00b0\u0001\u0000\u0000\u0000\u00c1\u00b3\u0001\u0000\u0000"+
		"\u0000\u00c1\u00b7\u0001\u0000\u0000\u0000\u00c1\u00c0\u0001\u0000\u0000"+
		"\u0000\u00c2\u0018\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005f\u0000\u0000"+
		"\u00c4\u00c5\u0005o\u0000\u0000\u00c5\u00c6\u0005r\u0000\u0000\u00c6\u001a"+
		"\u0001\u0000\u0000\u0000\u00c7\u00c8\u0005d\u0000\u0000\u00c8\u00c9\u0005"+
		"o\u0000\u0000\u00c9\u001c\u0001\u0000\u0000\u0000\u00ca\u00cb\u0005;\u0000"+
		"\u0000\u00cb\u001e\u0001\u0000\u0000\u0000\u00cc\u00cd\u0005,\u0000\u0000"+
		"\u00cd \u0001\u0000\u0000\u0000\u00ce\u00cf\u0005(\u0000\u0000\u00cf\""+
		"\u0001\u0000\u0000\u0000\u00d0\u00d1\u0005)\u0000\u0000\u00d1$\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d3\u0005{\u0000\u0000\u00d3&\u0001\u0000\u0000\u0000"+
		"\u00d4\u00d5\u0005}\u0000\u0000\u00d5(\u0001\u0000\u0000\u0000\u00d6\u00d7"+
		"\u0005+\u0000\u0000\u00d7*\u0001\u0000\u0000\u0000\u00d8\u00d9\u0005-"+
		"\u0000\u0000\u00d9,\u0001\u0000\u0000\u0000\u00da\u00db\u0005*\u0000\u0000"+
		"\u00db.\u0001\u0000\u0000\u0000\u00dc\u00dd\u0005/\u0000\u0000\u00dd0"+
		"\u0001\u0000\u0000\u0000\u00de\u00df\u0005=\u0000\u0000\u00df2\u0001\u0000"+
		"\u0000\u0000\u00e0\u00e1\u0005<\u0000\u0000\u00e14\u0001\u0000\u0000\u0000"+
		"\u00e2\u00e3\u0005<\u0000\u0000\u00e3\u00e4\u0005=\u0000\u0000\u00e46"+
		"\u0001\u0000\u0000\u0000\u00e5\u00e6\u0005.\u0000\u0000\u00e68\u0001\u0000"+
		"\u0000\u0000\u00e7\u00e8\u0005:\u0000\u0000\u00e8:\u0001\u0000\u0000\u0000"+
		"\u00e9\u00ea\u0005<\u0000\u0000\u00ea\u00eb\u0005-\u0000\u0000\u00eb<"+
		"\u0001\u0000\u0000\u0000\u00ec\u00ed\u0005=\u0000\u0000\u00ed\u00ee\u0005"+
		">\u0000\u0000\u00ee>\u0001\u0000\u0000\u0000\u00ef\u00f0\u0005@\u0000"+
		"\u0000\u00f0@\u0001\u0000\u0000\u0000\u00f1\u00f2\u0005n\u0000\u0000\u00f2"+
		"\u00f3\u0005e\u0000\u0000\u00f3\u00f4\u0005w\u0000\u0000\u00f4B\u0001"+
		"\u0000\u0000\u0000\u00f5\u00f6\u0005i\u0000\u0000\u00f6\u00f7\u0005s\u0000"+
		"\u0000\u00f7\u00f8\u0005v\u0000\u0000\u00f8\u00f9\u0005o\u0000\u0000\u00f9"+
		"\u00fa\u0005i\u0000\u0000\u00fa\u00fb\u0005d\u0000\u0000\u00fbD\u0001"+
		"\u0000\u0000\u0000\u00fc\u00fd\u0005n\u0000\u0000\u00fd\u00fe\u0005o\u0000"+
		"\u0000\u00fe\u00ff\u0005t\u0000\u0000\u00ffF\u0001\u0000\u0000\u0000\u0100"+
		"\u0101\u0005~\u0000\u0000\u0101H\u0001\u0000\u0000\u0000\u0102\u0103\u0005"+
		"w\u0000\u0000\u0103\u0104\u0005h\u0000\u0000\u0104\u0105\u0005i\u0000"+
		"\u0000\u0105\u0106\u0005l\u0000\u0000\u0106\u0107\u0005e\u0000\u0000\u0107"+
		"J\u0001\u0000\u0000\u0000\u0108\u0109\u0005l\u0000\u0000\u0109\u010a\u0005"+
		"o\u0000\u0000\u010a\u010b\u0005o\u0000\u0000\u010b\u010c\u0005p\u0000"+
		"\u0000\u010cL\u0001\u0000\u0000\u0000\u010d\u010e\u0005p\u0000\u0000\u010e"+
		"\u010f\u0005o\u0000\u0000\u010f\u0110\u0005o\u0000\u0000\u0110\u0111\u0005"+
		"l\u0000\u0000\u0111N\u0001\u0000\u0000\u0000\u0112\u0113\u0005l\u0000"+
		"\u0000\u0113\u0114\u0005e\u0000\u0000\u0114\u0115\u0005t\u0000\u0000\u0115"+
		"P\u0001\u0000\u0000\u0000\u0116\u0117\u0005i\u0000\u0000\u0117\u0118\u0005"+
		"n\u0000\u0000\u0118R\u0001\u0000\u0000\u0000\u0119\u011a\u0007\u0001\u0000"+
		"\u0000\u011aT\u0001\u0000\u0000\u0000\u011b\u011e\u0003S)\u0000\u011c"+
		"\u011e\u0005_\u0000\u0000\u011d\u011b\u0001\u0000\u0000\u0000\u011d\u011c"+
		"\u0001\u0000\u0000\u0000\u011e\u0124\u0001\u0000\u0000\u0000\u011f\u0123"+
		"\u0003S)\u0000\u0120\u0123\u0005_\u0000\u0000\u0121\u0123\u0003W+\u0000"+
		"\u0122\u011f\u0001\u0000\u0000\u0000\u0122\u0120\u0001\u0000\u0000\u0000"+
		"\u0122\u0121\u0001\u0000\u0000\u0000\u0123\u0126\u0001\u0000\u0000\u0000"+
		"\u0124\u0122\u0001\u0000\u0000\u0000\u0124\u0125\u0001\u0000\u0000\u0000"+
		"\u0125V\u0001\u0000\u0000\u0000\u0126\u0124\u0001\u0000\u0000\u0000\u0127"+
		"\u0128\u0007\u0002\u0000\u0000\u0128X\u0001\u0000\u0000\u0000\u0129\u012b"+
		"\u0003W+\u0000\u012a\u0129\u0001\u0000\u0000\u0000\u012b\u012c\u0001\u0000"+
		"\u0000\u0000\u012c\u012a\u0001\u0000\u0000\u0000\u012c\u012d\u0001\u0000"+
		"\u0000\u0000\u012dZ\u0001\u0000\u0000\u0000\u012e\u0130\u0003W+\u0000"+
		"\u012f\u012e\u0001\u0000\u0000\u0000\u0130\u0131\u0001\u0000\u0000\u0000"+
		"\u0131\u012f\u0001\u0000\u0000\u0000\u0131\u0132\u0001\u0000\u0000\u0000"+
		"\u0132\\\u0001\u0000\u0000\u0000\u0133\u0135\u0005.\u0000\u0000\u0134"+
		"\u0136\u0003[-\u0000\u0135\u0134\u0001\u0000\u0000\u0000\u0135\u0136\u0001"+
		"\u0000\u0000\u0000\u0136\u0138\u0001\u0000\u0000\u0000\u0137\u0133\u0001"+
		"\u0000\u0000\u0000\u0137\u0138\u0001\u0000\u0000\u0000\u0138^\u0001\u0000"+
		"\u0000\u0000\u0139\u013b\u0005e\u0000\u0000\u013a\u013c\u0007\u0003\u0000"+
		"\u0000\u013b\u013a\u0001\u0000\u0000\u0000\u013b\u013c\u0001\u0000\u0000"+
		"\u0000\u013c\u013d\u0001\u0000\u0000\u0000\u013d\u013e\u0003[-\u0000\u013e"+
		"`\u0001\u0000\u0000\u0000\u013f\u0147\u0005\"\u0000\u0000\u0140\u0141"+
		"\u0005\\\u0000\u0000\u0141\u0146\u0005\"\u0000\u0000\u0142\u0143\u0005"+
		"\\\u0000\u0000\u0143\u0146\u0005\n\u0000\u0000\u0144\u0146\t\u0000\u0000"+
		"\u0000\u0145\u0140\u0001\u0000\u0000\u0000\u0145\u0142\u0001\u0000\u0000"+
		"\u0000\u0145\u0144\u0001\u0000\u0000\u0000\u0146\u0149\u0001\u0000\u0000"+
		"\u0000\u0147\u0148\u0001\u0000\u0000\u0000\u0147\u0145\u0001\u0000\u0000"+
		"\u0000\u0148\u0150\u0001\u0000\u0000\u0000\u0149\u0147\u0001\u0000\u0000"+
		"\u0000\u014a\u014b\u0005\"\u0000\u0000\u014b\u0151\u00060\u0000\u0000"+
		"\u014c\u014d\u0005\u0000\u0000\u0001\u014d\u0151\u00060\u0001\u0000\u014e"+
		"\u014f\u0005\n\u0000\u0000\u014f\u0151\u00060\u0002\u0000\u0150\u014a"+
		"\u0001\u0000\u0000\u0000\u0150\u014c\u0001\u0000\u0000\u0000\u0150\u014e"+
		"\u0001\u0000\u0000\u0000\u0151b\u0001\u0000\u0000\u0000\u0152\u0154\u0005"+
		"\r\u0000\u0000\u0153\u0152\u0001\u0000\u0000\u0000\u0153\u0154\u0001\u0000"+
		"\u0000\u0000\u0154\u0155\u0001\u0000\u0000\u0000\u0155\u0156\u0005\n\u0000"+
		"\u0000\u0156d\u0001\u0000\u0000\u0000\u0157\u0158\u0005-\u0000\u0000\u0158"+
		"\u0159\u0005-\u0000\u0000\u0159\u015d\u0001\u0000\u0000\u0000\u015a\u015c"+
		"\t\u0000\u0000\u0000\u015b\u015a\u0001\u0000\u0000\u0000\u015c\u015f\u0001"+
		"\u0000\u0000\u0000\u015d\u015e\u0001\u0000\u0000\u0000\u015d\u015b\u0001"+
		"\u0000\u0000\u0000\u015e\u0162\u0001\u0000\u0000\u0000\u015f\u015d\u0001"+
		"\u0000\u0000\u0000\u0160\u0163\u0003c1\u0000\u0161\u0163\u0005\u0000\u0000"+
		"\u0001\u0162\u0160\u0001\u0000\u0000\u0000\u0162\u0161\u0001\u0000\u0000"+
		"\u0000\u0163\u0164\u0001\u0000\u0000\u0000\u0164\u0165\u00062\u0003\u0000"+
		"\u0165f\u0001\u0000\u0000\u0000\u0166\u0167\u0005(\u0000\u0000\u0167\u0168"+
		"\u0005*\u0000\u0000\u0168\u016d\u0001\u0000\u0000\u0000\u0169\u016c\u0003"+
		"g3\u0000\u016a\u016c\t\u0000\u0000\u0000\u016b\u0169\u0001\u0000\u0000"+
		"\u0000\u016b\u016a\u0001\u0000\u0000\u0000\u016c\u016f\u0001\u0000\u0000"+
		"\u0000\u016d\u016e\u0001\u0000\u0000\u0000\u016d\u016b\u0001\u0000\u0000"+
		"\u0000\u016e\u0176\u0001\u0000\u0000\u0000\u016f\u016d\u0001\u0000\u0000"+
		"\u0000\u0170\u0171\u0005*\u0000\u0000\u0171\u0172\u0005)\u0000\u0000\u0172"+
		"\u0173\u0001\u0000\u0000\u0000\u0173\u0177\u00063\u0004\u0000\u0174\u0175"+
		"\u0005\u0000\u0000\u0001\u0175\u0177\u00063\u0005\u0000\u0176\u0170\u0001"+
		"\u0000\u0000\u0000\u0176\u0174\u0001\u0000\u0000\u0000\u0177h\u0001\u0000"+
		"\u0000\u0000\u0178\u0179\u0005*\u0000\u0000\u0179\u017a\u0005)\u0000\u0000"+
		"\u017a\u017b\u0001\u0000\u0000\u0000\u017b\u017c\u00064\u0006\u0000\u017c"+
		"j\u0001\u0000\u0000\u0000\u017d\u017f\u0007\u0004\u0000\u0000\u017e\u017d"+
		"\u0001\u0000\u0000\u0000\u017f\u0180\u0001\u0000\u0000\u0000\u0180\u017e"+
		"\u0001\u0000\u0000\u0000\u0180\u0181\u0001\u0000\u0000\u0000\u0181\u0182"+
		"\u0001\u0000\u0000\u0000\u0182\u0183\u00065\u0003\u0000\u0183l\u0001\u0000"+
		"\u0000\u0000\u0184\u0185\t\u0000\u0000\u0000\u0185\u0186\u00066\u0007"+
		"\u0000\u0186n\u0001\u0000\u0000\u0000\u0017\u0000\u00a4\u00bb\u00bd\u00c1"+
		"\u011d\u0122\u0124\u012c\u0131\u0135\u0137\u013b\u0145\u0147\u0150\u0153"+
		"\u015d\u0162\u016b\u016d\u0176\u0180\b\u00010\u0000\u00010\u0001\u0001"+
		"0\u0002\u0006\u0000\u0000\u00013\u0003\u00013\u0004\u00014\u0005\u0001"+
		"6\u0006";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}