grammar Crux;
program
 : declarationList EOF
 ;

declarationList
 : declaration*
 ;

declaration
 : variableDeclaration
 | arrayDeclaration
 | functionDefinition
 ;

variableDeclaration
 : Var Identifier Colon type SemiColon
 ;

type
 : Identifier
 | Void
 | Bool
 | Int
 ;

literal
 : Integer
 | True
 | False
 ;

 reserved
 : And
 | Or
 | Not
 | Let
 | Var
 | Array
 | Func
 | If
 | Else
 | While
 | True
 | False
 | Return
 | Void
 | Bool
 | Int
 ;

identifier
 : ('_' | LETTER) ('_' | LETTER | DIGIT)*
 ;

designator
 : Identifier (Open_Bracket expression0 Close_Bracket)?
 ;

op0
 : Greater_Equal
 | Lesser_Equal
 | Not_Equal
 | Equal
 | Greater_Than
 | Less_Than
 ;

op1
 : Add
 | Sub
 | Or
 ;

op2
 : Mul
 | Div
 | And
 ;

expression0
: expression1 (op0 expression1)?
;

expression1
: expression2 (op1  expression2)*
;

expression2
: expression3 (op2 expression3)*
;

expression3
: Not expression3
| Open_Paren expression0 Close_Paren
| designator
| callExpression
| literal
;

callExpression
: Call Identifier Open_Paren expressionList Close_Paren
;

expressionList
 : ( expression0 ( Comma expression0 )* )?
 ;

parameter
 : Identifier Colon type
 ;

parameterList
 : ( parameter ( Comma parameter )* )?
 ;

arrayDeclaration
 : Array Identifier Colon type Open_Bracket Integer Close_Bracket SemiColon
 ;

functionDefinition
 : Func Identifier Open_Paren parameterList Close_Paren Colon type statementBlock
 ;

assignmentStatement
 : Let designator Assign expression0 SemiColon
 ;

callStatement
 : callExpression SemiColon
 ;

ifStatement
 : If expression0 statementBlock ( Else statementBlock )?
 ;

whileStatement
 : While expression0 statementBlock
 ;

returnStatement
 : Return expression0 SemiColon
 ;

statement
 : variableDeclaration
 | callStatement
 | assignmentStatement
 | ifStatement
 | whileStatement
 | returnStatement
 ;

statementList
 : ( statement )*
 ;

statementBlock
 : Open_Brace statementList Close_Brace
 ;

SemiColon: ';';
Colon: ':';
Open_Paren: '(';
Close_Paren: ')';
Open_Brace: '{';
Close_Brace: '}';
Open_Bracket: '[';
Close_Bracket: ']';
Add: '+';
Sub: '-';
Mul: '*';
Div: '/';
Greater_Equal: '>=';
Lesser_Equal: '<=';
Not_Equal: '!=';
Equal: '==';
Greater_Than: '>';
Less_Than: '<';
Assign: '=';
Comma: ',';
Call: '::';

Integer
 : '0'
 | [1-9] [0-9]*
 ;

True: 'true';
False: 'false';

Let: 'let';
Var: 'var';
Void: 'void';
Func: 'func';
Return: 'return';

Bool: 'bool';
Int: 'int';
Array: 'array';

And: 'and';
Or: 'or';
Not: 'not';

If: 'if';
Else: 'else';
While: 'while';

Identifier
 : [a-zA-Z] [a-zA-Z0-9_]*
 | Void
 | Bool
 | Int
 ;

WhiteSpaces
 : [ \t\r\n]+ -> skip
 ;

Comment
 : '//' ~[\r\n]* -> skip
 ;

DIGIT: [0-9];
LOWERCASE_LETTER: [a-z];
UPPERCASE_LETTER: [A-Z];
LETTER: LOWERCASE_LETTER | UPPERCASE_LETTER;
