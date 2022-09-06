package com.backend.compiladores.services;

import java_cup.runtime.*;
import com.backend.compiladores.services.ParserSym;

%%
%public
%class Lexer
%cup
%line
%column
%caseless
%ignorecase

digit = [0-9]
letter = [a-zA-Z]
whitespace = [ \n\t\r]

//Comentarios
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/" /*Comentarios multilinea*/
// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}? /*Comentarios de una linea*/
comment = {TraditionalComment} | {EndOfLineComment}


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

//Operadores relacionales
mayor=mayor
menor=menor
mayorIgual=mayor_o_igual
menorIgual=menor_o_igual
igual=es_igual
diferente=es_diferente

//operadores logicos
or=or
and=and
not=not


//Sibolos de un solo caracter - Inicio
asignacion =->
coma =,
semi_colon =bueno
Lpar =\(
Rpar =\)
Lque =\¿
Rque =\?

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
if=si
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
    return symbol(ParserSym.EOF, "EOF");
%eofval}


%%
//Comentarios
{comment} { return symbol(ParserSym.COMENTARIO, yytext());} //O

//Tipos de datos
{numero} { return symbol(ParserSym.NUMERO, yytext()); } //O
{string} { return symbol(ParserSym.CADENA, yytext()); } //O
{Boolean} { return symbol(ParserSym.BOOLEAN, yytext()); } //O
{character} { return symbol(ParserSym.CARACTER, yytext()); } //O

//Simbolos matematicos
{suma} { return symbol(ParserSym.SUMA, yytext()); } //O
{resta} { return symbol(ParserSym.RESTA, yytext()); } //O
{multiplicacion} { return symbol(ParserSym.MULTI, yytext()); } //O
{division} { return symbol(ParserSym.DIVI, yytext()); } //O

//Operadores relacionales
{mayor} { return symbol(ParserSym.MAYOR, yytext()); }//O
{menor} { return symbol(ParserSym.MENOR, yytext());}//O
{mayorIgual} { return symbol(ParserSym.MAYORIGUAL, yytext());}//O
{menorIgual} { return symbol(ParserSym.MENORIGUAL, yytext());}//O
{igual} { return symbol(ParserSym.IGUAL, yytext());}//O
{diferente} { return symbol(ParserSym.DIFERENTE, yytext());}//O

//operadores logicos
{or} {return symbol(ParserSym.OR, yytext());}//O
{and} {return symbol(ParserSym.AND, yytext());}//O
{not} { return symbol(ParserSym.NOT, yytext());}//O


//Sibolos de un solo caracter - Inicio
{asignacion} { return symbol(ParserSym.ASIGNACION, yytext()); } //O
{coma} { return symbol(ParserSym.COMA, yytext()); }//O
{semi_colon} { symbol(ParserSym.SEMI_COLON, yytext()); }
{Lpar} { return symbol(ParserSym.LPAREN, yytext()); } //O
{Rpar} { return symbol(ParserSym.RPAREN, yytext()); } //O
{Lque} { symbol(ParserSym.LQUE, yytext()); } //0
{Rque} { symbol(ParserSym.RQUE, yytext()); } //0


//Palabras reservadas - Tipado
{tipado_numero} { return symbol(ParserSym.TIPO, yytext()); } //O
{tipado_cadena} { return symbol(ParserSym.TIPO, yytext()); } //O
{tipado_boolean} { return symbol(ParserSym.TIPO, yytext()); } //O
{tipado_caracter} { return symbol(ParserSym.TIPO, yytext()); } //O

//Palabras reservadas - Inicio
{potencia} { return symbol(ParserSym.POTENCIA, yytext()); } //O
{modulo} { return symbol(ParserSym.MODULO, yytext()); } //O
{inicio} { return symbol(ParserSym.INICIO, yytext()); } //O
{fin} { return symbol(ParserSym.FIN, yytext()); } //O
{ingresar} { return symbol(ParserSym.INGRESAR, yytext()); } //O
{como} { return symbol(ParserSym.COMO, yytext()); } //O
{con_valor} { return symbol(ParserSym.CON_VALOR, yytext()); } //O
{if} { return symbol(ParserSym.IF, yytext()); } //O
{else} { return symbol(ParserSym.ELSE, yytext()); } //0
{else_if} { return symbol(ParserSym.ELSE_IF, yytext()); }//O
{then} { return symbol(ParserSym.THEN, yytext()); } //repetido en if, select case //O
{end_if} { return symbol(ParserSym.END_IF, yytext()); } //O
{select} { symbol(ParserSym.SELECT, yytext()); } //0
{case} { symbol(ParserSym.CASE, yytext()); } //repetido en for , select case, mientras //0
{default} { symbol(ParserSym.DEFAULT, yytext()); } //0
{end_select} { symbol(ParserSym.END_SELECT, yytext()); } //0
//{for} { symbol(ParserSym.FOR, yytext()); }
//{to} { symbol(ParserSym.TO, yytext()); }
//{end_for} { symbol(ParserSym.END_FOR, yytext()); }
//{incremental} { symbol(ParserSym.INCREMENTAL, yytext()); }
//{while} { symbol(ParserSym.WHILE, yytext()); }
//{end_while} { symbol(ParserSym.END_WHILE, yytext()); }
//{repetir} { symbol(ParserSym.REPETIR, yytext()); }
//{hasta_que} { symbol(ParserSym.HASTA_QUE, yytext()); }
//{return} { symbol(ParserSym.RETURN, yytext()); }
//{metodo} { symbol(ParserSym.METODO, yytext()); }
//{fin_metodo} { symbol(ParserSym.FIN_METODO, yytext()); }
//{con_parametros} { symbol(ParserSym.CON_PARAMETROS, yytext()); }
//{function} { symbol(ParserSym.FUNCTION, yytext()); }
//{end_function} { symbol(ParserSym.NUMERO, yytext()); }
//{ejecutar} { symbol(ParserSym.EJECUTAR, yytext()); }
//{imprimir} { symbol(ParserSym.IMPRIMIR, yytext()); }
//{imprimir_sin_salto} { symbol(ParserSym.IMPRIMIR_SIN_SALTO, yytext()); }


//Variables
{variable} { return symbol(ParserSym.VARIABLE,yytext()); }

{whitespace} {/*SKIP WHITE SPACE*/}
[^] { return symbol(ParserSym.ERROR, yytext()); }