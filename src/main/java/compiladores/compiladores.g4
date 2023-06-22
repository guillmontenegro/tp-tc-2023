grammar compiladores;

@header {
package compiladores;
}

fragment LETRA : [A-Za-z] ;
fragment DIGITO : [0-9] ;

PYC : ';' ;
PA  : '(' ;
PC  : ')' ;
LLA : '{' ;
LLC : '}' ;
CA : '[' ;
CC : ']' ;
EQ : '=' ;
COMA  : ',' ;
SUMA  : '+' ;
RESTA : '-' ;
MULT  : '*' ;
DIV   : '/' ;
MOD   : '%' ;
NEG : '!';
DEQ : '==' ;
NEQ : '!=' ;
AND : '&&' ; 
OR : '||'; 
GT : '>' ; 
LT : '<'; 
GTE : '>=' ; 
LTE : '<=';
INC : '++';
DEC : '--';

NUMERO : DIGITO+ ;

IF : 'if' ;
INT : 'int' ;
FOR : 'for' ;
CHAR : 'char';
ELSE: 'else';
VOID: 'void';
FLOAT : 'float';
WHILE: 'while';
DOUBLE : 'double';
RETURN : 'return';

ID : (LETRA | '_')(LETRA | DIGITO | '_')* ;
WS : [ \t\n\r] -> skip ;

programa : instrucciones EOF ;

instrucciones : instruccion instrucciones
              |
              ;

instruccion : bloque
            | asignacion
            | declaracion
            | declaracion_f
            | call_f
            | return_i
            | while_i
            | for_i
            | if_i
            ;

bloque : LLA instrucciones LLC ;

return_i : RETURN expresion PYC ;

while_i : WHILE PA expresion PC instruccion ;

for_i : FOR PA expresion_for PYC expresion_for PYC expresion_for PC instruccion ;

if_i : IF PA expresion PC instruccion else_if;

else_if : ELSE instruccion
        |
        ;

expresion_for : exp_for listaexpfor
              | 
              ;

exp_for : expresion
        | asignacion_npyc
        ;

listaexpfor : COMA exp_for listaexpfor
            |
            ;

asignacion_npyc : ID EQ expresion ;

asignacion : asign_type listaasign PYC
           ;

asign_type : ID EQ expresion
           | id_factor
           ;

listaasign : COMA asign_type listaasign
           |
           ;

declaracion : tipodato ID inicializacion listaid PYC ;

declaracion_f : returndato_f ID PA params_f PC declaracion_f_end ;

returndato_f : tipodato
             | VOID
             ;

declaracion_f_end : PYC
                  | bloque
                  ;

params_f : tipodato ID listaparams_f
         |
         ;

listaparams_f : COMA tipodato ID listaparams_f
              |
              ;

call_f : ID PA params_call_f PC PYC ;

call_f_factor : ID PA params_call_f PC ;

params_call_f : expresion listaexpresion
              |
              ;

tipodato : INT
         | FLOAT
         | DOUBLE
         | CHAR
         ;

tipoasigacion : NUMERO
              | ID
              ;

inicializacion : EQ expresion
               |
               ;

listaid : COMA ID inicializacion listaid
        |
        ;

expresion : expresion_or ;

expresion_or : expresion_or OR expresion_or
             | expresion_and
             ;

expresion_and : expresion_and AND expresion_and
              | expresion_neg
              ;

expresion_neg : NEG expresion_neg
              | expresion_comp
              ;

expresion_comp : expresion_comp comparaciones expresion_comp
               | expresion_arit
               ;

comparaciones : LT | LTE | GT | GTE | DEQ | NEQ ;

expresion_arit : termino exp ;

listaexpresion : COMA expresion listaexpresion
               |
               ;

exp : SUMA  termino exp
    | RESTA termino exp
    |
    ;

termino : factor term ;

term : MULT factor term
     | DIV  factor term
     | MOD  factor term
     |
     ;

factor : sign_factor NUMERO
       | sign_factor ID
       | id_factor
       | sign_factor call_f_factor
       | sign_factor PA expresion PC 
       ;

id_factor : inc_dec ID
          | ID inc_dec
          ;

inc_dec : INC | DEC ;

sign_factor : SUMA | RESTA | ;