package com.backend.compiladores.services;

import java_cup.runtime.*;
import com.backend.compiladores.services.ParserSym;

%%
%public
%class Lexer
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
Lpar =\(
Rpar =\)
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

%{

    StringBuffer string = new StringBuffer ();
    private Symbol symbol (int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol (int type, Object value){
        return new Symbol (type, yyline, yycolumn, value) ;
    }

%}

%eofval{
    return symbol(ParserSym.EOF);
%eofval}


%%
//Comentarios
{comment} { symbol(ParserSym.COMENTARIO, yytext());}

//Tipos de datos
{numero} { symbol(ParserSym.NUMERO, yytext()); }
{string} { symbol(ParserSym.CADENA, yytext()); }
{Boolean} { symbol(ParserSym.BOOLEAN, yytext()); }
{character} { symbol(ParserSym.CARACTER, yytext()); }

//Simbolos matematicos
{suma} { symbol(ParserSym.SUMA, yytext()); }
{resta} { symbol(ParserSym.RESTA, yytext()); }
{multiplicacion} { symbol(ParserSym.MULTI, yytext()); }
{division} { symbol(ParserSym.DIVI, yytext()); }


//Sibolos de un solo caracter - Inicio
{asignacion} { symbol(ParserSym.ASIGNACION, yytext()); }
{coma} { symbol(ParserSym.COMA, yytext()); }
{end_instruction} { symbol(TokenConstant.END_INSTRUCTION, yytext()); }
{Lpar} { symbol(ParserSym.LPAREN, yytext()); }
{Rpar} { symbol(TokenConstant.PARC, yytext()); }
{queA} { symbol(TokenConstant.QUEA, yytext()); }
{queC} { symbol(TokenConstant.QUEC, yytext()); }


//Palabras reservadas - Tipado
{tipado_numero} { symbol(TokenConstant.TIPADO_NUMERO, yytext()); }
{tipado_cadena} { symbol(TokenConstant.TIPADO_CADENA, yytext()); }
{tipado_boolean} { symbol(TokenConstant.TIPADO_BOOLEAN, yytext()); }
{tipado_caracter} { symbol(TokenConstant.TIPADO_CARACTER, yytext()); }

//Palabras reservadas - Inicio
{potencia} { symbol(TokenConstant.POTENCIA, yytext()); }
{modulo} { symbol(TokenConstant.MODULO, yytext()); }
{inicio} { symbol(TokenConstant.INICIO, yytext()); }
{fin} { symbol(TokenConstant.FIN, yytext()); }
{ingresar} { symbol(TokenConstant.INGRESAR, yytext()); }
{como} { symbol(TokenConstant.COMO, yytext()); }
{con_valor} { symbol(TokenConstant.CON_VALOR, yytext()); }
{if} { symbol(TokenConstant.IF, yytext()); }
{else} { symbol(TokenConstant.ELSE, yytext()); }
{else_if} { symbol(TokenConstant.ELSE_IF, yytext()); }
{then} { symbol(TokenConstant.THEN, yytext()); } //repetido en if, select case
{end_if} { symbol(TokenConstant.END_IF, yytext()); }
{select} { symbol(TokenConstant.SELECT, yytext()); }
{case} { symbol(TokenConstant.CASE, yytext()); } //repetido en for , select case, mientras
{default} { symbol(TokenConstant.DEFAULT, yytext()); }
{end_select} { symbol(TokenConstant.END_SELECT, yytext()); }
{for} { symbol(TokenConstant.FOR, yytext()); }
{to} { symbol(TokenConstant.TO, yytext()); }
{end_for} { symbol(TokenConstant.END_FOR, yytext()); }
{incremental} { symbol(TokenConstant.INCREMENTAL, yytext()); }
{while} { symbol(TokenConstant.WHILE, yytext()); }
{end_while} { symbol(TokenConstant.END_WHILE, yytext()); }
{repetir} { symbol(TokenConstant.REPETIR, yytext()); }
{hasta_que} { symbol(TokenConstant.HASTA_QUE, yytext()); }
{return} { symbol(TokenConstant.RETURN, yytext()); }
{metodo} { symbol(TokenConstant.METODO, yytext()); }
{fin_metodo} { symbol(TokenConstant.FIN_METODO, yytext()); }
{con_parametros} { symbol(TokenConstant.CON_PARAMETROS, yytext()); }
{function} { symbol(TokenConstant.FUNCTION, yytext()); }
{end_function} { symbol(TokenConstant.NUMERO, yytext()); }
{ejecutar} { symbol(TokenConstant.EJECUTAR, yytext()); }
{imprimir} { symbol(TokenConstant.IMPRIMIR, yytext()); }
{imprimir_sin_salto} { symbol(TokenConstant.IMPRIMIR_SIN_SALTO, yytext()); }


//Variables
{variable} { symbol(TokenConstant.VARIABLE,yytext()); }

{whitespace} {/*SKIP WHITE SPACE*/}
[^] { symbol(TokenConstant.ERROR, yytext()); }