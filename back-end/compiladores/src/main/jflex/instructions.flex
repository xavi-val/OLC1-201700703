package com.backend.compiladores.services.scanner;

import java_cup.runtime.*;

%%
%public
%class Lexer
%type Token
%cup

digit = [0-9]
letter = [a-zA-Z]
whitespace = [ \n\t\r]

//Comentarios
one_line_comment = \/\/[^(\/|\n)]*
multi_comment = \/\*(.|\n)*\*\/
comment = ({multi_comment}|{one_line_comment})

//Tipos de datos
decimal = {digit}+(\.)?{digit}+
numero = ({digit}+|{decimal})
string = \"([^\"])*\"
Boolean = (Verdadero|Falso)
ascii = \'\$\{{digit}+\}\'
character = (\'.\' | {ascii})

//Simbolos matematicos
suma =\+
resta =\-
multiplicacion =\*
division =\/
equal =\=



//Sibolos de un solo caracter - Inicio
asignacion =->
coma =,
end_instruction =;
parA =\(
parC =\)
queA =\¿
queC =\?

//Palabras reservadas - TIPADO
tipado_numero = numero|número|Número|Numero|NUMERO|NÚMERO
tipado_cadena = cadena|Cadena|CADENA
tipado_boolean = boolean|Boolean|BOOLEAN
tipado_caracter = caracter|carácter|Caracter|Carácter|CARACTER|CARÁCTER

//Palabras reservadas - Inicio
potencia =potencia
modulo =mod
inicio =inicio
fin =fin
ingresar =ingresar
como =como
con_valor =con_valor
if =si
else =de_lo_contrario
else_if =o_si
then =entonces //repetido en if, select case
end_if =fin_si
select =segun
case =hacer //repetido en for , select case, mientras
default =de_lo_contrario_entonces
end_select =fin_segun
for =para
to =hasta
end_for =fin_para
incremental =con incremental
while =mientras
end_while =fin_mientras
repetir =repetir
hasta_que =hasta_que
return =retornar
metodo =metodo
fin_metodo =fin_metodo
con_parametros =con_parametros
function =funcion
end_function =fin_funcion
ejecutar =ejecutar
imprimir =imprimir
imprimir_sin_salto=imprimir_nl


//Variables
variable = \_{letter}({letter}|{digit})+\_

%eofval{
    return new Token(TokenConstant.EOF,null);
%eofval}


%%
//Comentarios
{comment} { return new Token(TokenConstant.COMMENT, yytext());}

//Tipos de datos
{numero} { return new Token(TokenConstant.NUMERO, yytext()); }
{string} { return new Token(TokenConstant.STRING, yytext()); }
{Boolean} { return new Token(TokenConstant.BOOLEAN, yytext()); }
{character} { return new Token(TokenConstant.CHARACTER, yytext()); }

//Simbolos matematicos
{suma} { return new Token(TokenConstant.SUMA, yytext()); }
{resta} { return new Token(TokenConstant.RESTA, yytext()); }
{multiplicacion} { return new Token(TokenConstant.MULTIPLICACION, yytext()); }
{division} { return new Token(TokenConstant.DIVISION, yytext()); }


//Sibolos de un solo caracter - Inicio
{asignacion} { return new Token(TokenConstant.ASIGNACION, yytext()); }
{coma} { return new Token(TokenConstant.COMA, yytext()); }
{end_instruction} { return new Token(TokenConstant.END_INSTRUCTION, yytext()); }
{parA} { return new Token(TokenConstant.PARA, yytext()); }
{parC} { return new Token(TokenConstant.PARC, yytext()); }
{queA} { return new Token(TokenConstant.QUEA, yytext()); }
{queC} { return new Token(TokenConstant.QUEC, yytext()); }


//Palabras reservadas - Tipado
{tipado_numero} { return new Token(TokenConstant.TIPADO_NUMERO, yytext()); }
{tipado_cadena} { return new Token(TokenConstant.TIPADO_CADENA, yytext()); }
{tipado_boolean} { return new Token(TokenConstant.TIPADO_BOOLEAN, yytext()); }
{tipado_caracter} { return new Token(TokenConstant.TIPADO_CARACTER, yytext()); }

//Palabras reservadas - Inicio
{potencia} { return new Token(TokenConstant.POTENCIA, yytext()); }
{modulo} { return new Token(TokenConstant.MODULO, yytext()); }
{inicio} { return new Token(TokenConstant.INICIO, yytext()); }
{fin} { return new Token(TokenConstant.FIN, yytext()); }
{ingresar} { return new Token(TokenConstant.INGRESAR, yytext()); }
{como} { return new Token(TokenConstant.COMO, yytext()); }
{con_valor} { return new Token(TokenConstant.CON_VALOR, yytext()); }
{if} { return new Token(TokenConstant.IF, yytext()); }
{else} { return new Token(TokenConstant.ELSE, yytext()); }
{else_if} { return new Token(TokenConstant.ELSE_IF, yytext()); }
{then} { return new Token(TokenConstant.THEN, yytext()); } //repetido en if, select case
{end_if} { return new Token(TokenConstant.END_IF, yytext()); }
{select} { return new Token(TokenConstant.SELECT, yytext()); }
{case} { return new Token(TokenConstant.CASE, yytext()); } //repetido en for , select case, mientras
{default} { return new Token(TokenConstant.DEFAULT, yytext()); }
{end_select} { return new Token(TokenConstant.END_SELECT, yytext()); }
{for} { return new Token(TokenConstant.FOR, yytext()); }
{to} { return new Token(TokenConstant.TO, yytext()); }
{end_for} { return new Token(TokenConstant.END_FOR, yytext()); }
{incremental} { return new Token(TokenConstant.INCREMENTAL, yytext()); }
{while} { return new Token(TokenConstant.WHILE, yytext()); }
{end_while} { return new Token(TokenConstant.END_WHILE, yytext()); }
{repetir} { return new Token(TokenConstant.REPETIR, yytext()); }
{hasta_que} { return new Token(TokenConstant.HASTA_QUE, yytext()); }
{return} { return new Token(TokenConstant.RETURN, yytext()); }
{metodo} { return new Token(TokenConstant.METODO, yytext()); }
{fin_metodo} { return new Token(TokenConstant.FIN_METODO, yytext()); }
{con_parametros} { return new Token(TokenConstant.CON_PARAMETROS, yytext()); }
{function} { return new Token(TokenConstant.FUNCTION, yytext()); }
{end_function} { return new Token(TokenConstant.NUMERO, yytext()); }
{ejecutar} { return new Token(TokenConstant.EJECUTAR, yytext()); }
{imprimir} { return new Token(TokenConstant.IMPRIMIR, yytext()); }
{imprimir_sin_salto} { return new Token(TokenConstant.IMPRIMIR_SIN_SALTO, yytext()); }


//Variables
{variable} { return new Token(TokenConstant.VARIABLE,yytext()); }

{whitespace} {}
[^] { return new Token(TokenConstant.ERROR, yytext()); }