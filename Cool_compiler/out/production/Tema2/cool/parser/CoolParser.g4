parser grammar CoolParser;

options {
    tokenVocab = CoolLexer;
}

@header{
    package cool.parser;
}

program
    :   (classes += class SEMICOLON)+ EOF
    ;

class
    :   CLASS type_id=TYPE_ID (INHERITS inherits=TYPE_ID)? LBRACE (features+=feature SEMICOLON)* RBRACE
    ;

feature
    :   id=ID LPAREN (formals+=formal (COMMA formals+=formal)*)? RPAREN COLON type=TYPE_ID LBRACE expression=expr RBRACE    #function
    |   id=ID COLON type=TYPE_ID (ASSIGN expression=expr)?                                                                 #variable
    ;

formal
    :   id=ID COLON type=TYPE_ID
    ;

let_var
    : ID COLON TYPE_ID (ASSIGN init=expr)?
    ;

expr
    :   calls=expr (AT castType=TYPE_ID)? DOT method=ID LPAREN (args+=expr (COMMA args+=expr)*)? RPAREN                                #call
    |   op=(NEG | MINUS) expression=expr                                                                                                        #neg
    |   op=ISVOID expression=expr                                                                                                               #isvoid
    |   LET varDecs+=let_var (COMMA varDecs+=let_var)* IN body=expr                                                                             #let
    |   CASE caseExpr=expr OF (caseVars+=ID COLON caseTypes+=TYPE_ID CASE_ASSIGN caseBodies+=expr SEMICOLON)+ ESAC                              #case
    |   LBRACE (seq+=expr SEMICOLON)+ RBRACE                                                                                                    #lrbrace
    |   IF cond=expr THEN then=expr ELSE elseBranch=expr FI                                                                                     #if
    |   WHILE cond=expr LOOP body=expr POOL                                                                                                     #while
    |   method=ID LPAREN (args+=expr (COMMA args+=expr)*)? RPAREN                                                                               #method
    |   left=expr op=(DIV | MULT) right=expr                                                                                                    #arithmetic
    |   left=expr op=(PLUS | MINUS) right=expr                                                                                                  #arithmetic
    |   LPAREN exprs+=expr* RPAREN                                                                                                              #lrparen
    |   left=expr op=(LT | LE | EQ) right=expr                                                                                                  #logicop
    |   NEW type=TYPE_ID                                                                                                                        #new
    |   NOT expression=expr                                                                                                                     #reverse
    |   var=ID ASSIGN expression = expr                                                                                                         #assignation
    |   ID                                                                                                                                      #id
    |   INT                                                                                                                                     #int
    |   STRING                                                                                                                                  #string
    |   TRUE                                                                                                                                    #true
    |   FALSE                                                                                                                                   #false
    ;



