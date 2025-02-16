lexer grammar CoolLexer;

tokens { ERROR } 

@header{
    package cool.lexer;	
}

@members{    
    private void raiseError(String msg) {
        setText(msg);
        setType(ERROR);
    }
}

fragment DIGIT : [0-9];
INT : DIGIT+;

fragment LETTER :[a-zA-Z];
fragment CAPITAL_LETTER : [A-Z];
fragment LOW_LETTER: [a-z];

CLASS : 'class';
ELSE : 'else';
FI : 'fi';
IF : 'if';
IN : 'in';
INHERITS : 'inherits';
ISVOID : 'isvoid';
LET : 'let';
LOOP : 'loop';
POOL : 'pool';
THEN : 'then';
WHILE : 'while';
CASE : 'case';
ESAC : 'esac';
NEW : 'new';
OF : 'of';
NOT : 'not';

CASE_ASSIGN: '=>';
ASSIGN : '<-';
LE : '<=';
LT : '<';
EQ : '=';
PLUS : '+';
MINUS : '-';
MULT : '*';
DIV : '/';
NEG : '~';
AT : '@';
DOT : '.';

LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';

COLON : ':';
SEMICOLON : ';';
COMMA : ',';

FALSE : 'false' | 'FALSE';
TRUE : 'true' | 'TRUE';

TYPE_ID : CAPITAL_LETTER(LETTER | '_' | DIGIT)*;
ID : (LETTER | '_')(LETTER | '_' | DIGIT)*;
OBJ_ID : LOW_LETTER(LETTER | '_' | DIGIT)*;

STRING
    :   '"'
        (
            '\\\n'
            |'\\'
            | '\u0000' {raiseError("String contains null character"); }
            | .
        )*?
        (
        '"'
        {
            String text = getText();
            if(text.equals("String contains null character")
                        || text.equals("Unterminated string constant"))
                return;
            text = text.substring(1, getText().length() - 1);

            text = text.replace("\\n", "\n");
            text = text.replace("\\t", "\t");
            text = text.replace("\\b", "\b");
            text = text.replace("\\f", "\f");

            text = text.replaceAll("\\\\\\\\", "__TEMP_BACKSLASH__");
            text = text.replaceAll("\\\\", "");
            text = text.replaceAll("__TEMP_BACKSLASH__", "\\\\");

            setText(text);

            if(text.length() >= 1024)
                raiseError("String constant too long");
        }
        | '\n' {raiseError ("Unterminated string constant");}
        | EOF {raiseError("EOF in string constant");}
        )
    ;

fragment NEW_LINE : '\r'? '\n';

BLOCK_COMMENT
    : '(*'
        (BLOCK_COMMENT | .)*?
        ('*)' {skip();} | EOF { raiseError("EOF in comment"); })
    ;


BLOCK_COMMENT_RIGHT_SIDE
    : '*)' { raiseError("Unmatched *)"); };

LINE_COMMENT
    : '--' .*? ('\n' | EOF) -> skip
    ;

WS
    :   [ \n\f\r\t]+ -> skip
    ;


INVALID_CHAR
    : .
    {
        raiseError("Invalid character: " + getText());
    };
