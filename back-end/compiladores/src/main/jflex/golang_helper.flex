package com.backend.compiladores.services.helper;

import java_cup.runtime.*;
import com.backend.compiladores.services.helper.golang_helperSym;

%%
%public
%class golang_helper_lexer

%line
%column
%caseless
%ignorecase
%cup

digit = [0-9]
letter = [a-zA-Z]
whitespace = [ \n\t\r]

//Comentarios
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

TraditionalComment   = "/*" ~"*/" | "/*" "*"+ "/" /*Comentarios multilinea*/
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
coma =\,
scolon =\;
Lpar =\(
Rpar =\)
Lcor =\[
Rcor =\]
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
else =de_lo_contrario //REPETIDO EN EL ELSE Y EL DEFAULT DE SELECT CASE
else_if =o_si
then =entonces //repetido en if, select case
end_if =fin_si
select =segun
case =hacer //repetido en for , select case, mientras
end_select =fin_segun
for =para
to =hasta
end_for =fin_para
incremental =con_incremental
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
potencia_golang=math.Pow
float=float64


//Variables
variable = \_{letter}({letter}|{digit})*\_

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
    return symbol(golang_helperSym.EOF, "EOF");
%eofval}


%%
//Comentarios
{comment} { return symbol(golang_helperSym.COMENTARIO, yytext());} //O

//Tipos de datos
{numero} { return symbol(golang_helperSym.NUMERO, yytext()); } //O
{string} { return symbol(golang_helperSym.CADENA, yytext()); } //O
{Boolean} { return symbol(golang_helperSym.BOOLEAN, yytext()); } //O
{character} { return symbol(golang_helperSym.CARACTER, yytext()); } //O

//Simbolos matematicos
{suma} { return symbol(golang_helperSym.SUMA, yytext()); } //O
{resta} { return symbol(golang_helperSym.RESTA, yytext()); } //O
{multiplicacion} { return symbol(golang_helperSym.MULTI, yytext()); } //O
{division} { return symbol(golang_helperSym.DIVI, yytext()); } //O

//Operadores relacionales
{mayor} { return symbol(golang_helperSym.MAYOR, yytext()); }//O
{menor} { return symbol(golang_helperSym.MENOR, yytext());}//O
{mayorIgual} { return symbol(golang_helperSym.MAYORIGUAL, yytext());}//O
{menorIgual} { return symbol(golang_helperSym.MENORIGUAL, yytext());}//O
{igual} { return symbol(golang_helperSym.IGUAL, yytext());}//O
{diferente} { return symbol(golang_helperSym.DIFERENTE, yytext());}//O

//operadores logicos
{or} {return symbol(golang_helperSym.OR, yytext());}//O
{and} {return symbol(golang_helperSym.AND, yytext());}//O
{not} { return symbol(golang_helperSym.NOT, yytext());}//O


//Sibolos de un solo caracter - Inicio
{asignacion} { return symbol(golang_helperSym.ASIGNACION, yytext()); } //O
{coma} { return symbol(golang_helperSym.COMA, yytext()); }//O
{scolon} { return symbol(golang_helperSym.SEMI_COLON, yytext()); }
{Lpar} { return symbol(golang_helperSym.LPAREN, yytext()); } //O
{Rpar} { return symbol(golang_helperSym.RPAREN, yytext()); } //O
{Lque} { return symbol(golang_helperSym.LQUE, yytext()); } //0
{Rque} { return symbol(golang_helperSym.RQUE, yytext()); } //0
{Lcor} { return symbol(golang_helperSym.LCOR, yytext()); } //0
{Rcor} { return symbol(golang_helperSym.RCOR, yytext()); } //0


//Palabras reservadas - Tipado
{tipado_numero} { return symbol(golang_helperSym.TIPO, yytext()); } //O
{tipado_cadena} { return symbol(golang_helperSym.TIPO, yytext()); } //O
{tipado_boolean} { return symbol(golang_helperSym.TIPO, yytext()); } //O
{tipado_caracter} { return symbol(golang_helperSym.TIPO, yytext()); } //O

//Palabras reservadas - Inicio
{potencia} { return symbol(golang_helperSym.POTENCIA, yytext()); } //O
{modulo} { return symbol(golang_helperSym.MODULO, yytext()); } //O
{inicio} { return symbol(golang_helperSym.INICIO, yytext()); } //O
{fin} { return symbol(golang_helperSym.FIN, yytext()); } //O
{ingresar} { return symbol(golang_helperSym.INGRESAR, yytext()); } //O
{como} { return symbol(golang_helperSym.COMO, yytext()); } //O
{con_valor} { return symbol(golang_helperSym.CON_VALOR, yytext()); } //O
{if} { return symbol(golang_helperSym.IF, yytext()); } //O
{else} { return symbol(golang_helperSym.ELSE, yytext()); } //0
{else_if} { return symbol(golang_helperSym.ELSE_IF, yytext()); }//O
{then} { return symbol(golang_helperSym.THEN, yytext()); } //repetido en if, select case //O
{end_if} { return symbol(golang_helperSym.END_IF, yytext()); } //O
{select} { return symbol(golang_helperSym.SELECT, yytext()); } //0
{case} { return symbol(golang_helperSym.CASE, yytext()); } //repetido en for , select case, mientras //0
{end_select} { return symbol(golang_helperSym.END_SELECT, yytext()); } //0
{for} { return symbol(golang_helperSym.FOR, yytext()); } //0
{to} { return symbol(golang_helperSym.TO, yytext()); } //0
{end_for} { return symbol(golang_helperSym.END_FOR, yytext()); } //0
{incremental} { return symbol(golang_helperSym.INCREMENTAL, yytext()); } //0
{while} { return symbol(golang_helperSym.WHILE, yytext()); }//0
{end_while} { return symbol(golang_helperSym.END_WHILE, yytext()); } //0
{repetir} { return symbol(golang_helperSym.REPETIR, yytext()); } //0
{hasta_que} { return symbol(golang_helperSym.HASTA_QUE, yytext()); } //0
{return} { return symbol(golang_helperSym.RETURN, yytext()); }
{metodo} { return symbol(golang_helperSym.METODO, yytext()); }
{fin_metodo} { return symbol(golang_helperSym.FIN_METODO, yytext()); }
{con_parametros} { return symbol(golang_helperSym.CON_PARAMETROS, yytext()); }
{function} { return symbol(golang_helperSym.FUNCTION, yytext()); }
{end_function} { return symbol(golang_helperSym.END_FUNCTION, yytext()); }
{ejecutar} { return symbol(golang_helperSym.EJECUTAR, yytext()); }
{imprimir} { return symbol(golang_helperSym.IMPRIMIR, yytext()); }
{imprimir_sin_salto} { return symbol(golang_helperSym.IMPRIMIR_SIN_SALTO, yytext()); }
{potencia_golang} { return symbol(golang_helperSym.POTENCIA, yytext()); }
{float} { return symbol(golang_helperSym.FLOAT, yytext()); }

//Variables
{variable} { return symbol(golang_helperSym.VARIABLE,yytext()); }

{whitespace} {/*SKIP WHITE SPACE*/}
[^] { return symbol(golang_helperSym.ERROR, yytext()); }