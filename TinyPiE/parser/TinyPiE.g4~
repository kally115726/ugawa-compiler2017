// antlr4 -package parser -o antlr-generated  -no-listener parser/TinyPiE.g4
grammar TinyPiE;

expr: bitOrExpr
      ;

bitOrExpr: bitOrExpr BITOR bitAndExpr
	| bitAndExpr
	;

bitAndExpr: bitAndExpr BITAND addExpr
	| addExpr
	;

addExpr: addExpr ADDOP mulExpr
	| mulExpr
	;

mulExpr: mulExpr MULOP unaryExpr
	| unaryExpr
	;

unaryExpr: VALUE			# literalExpr
	| IDENTIFIER			# varExpr
	| '-' unaryExpr		# minusExpr
	| '~' unaryExpr		# notExpr
	| '(' expr ')'		# parenExpr
	;

ADDOP: '+'|'-';
MULOP: '*'|'/';


BITOR: '|';
BITAND: '&';

IDENTIFIER: 'x'|'y'|'z';
VALUE: [0-9]|[1-9]+[0-9]|'0';
WS: [ \t\r\n] -> skip;
