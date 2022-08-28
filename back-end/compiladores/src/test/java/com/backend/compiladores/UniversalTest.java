package com.backend.compiladores;

import com.backend.compiladores.services.Lexer;
import com.backend.compiladores.services.Parser;
import com.backend.compiladores.services.ParserSym;
import com.backend.compiladores.services.traductor.Traductor_Python;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class UniversalTest {
    String testString = """
inicio
//Archivo de prueba, comentario de una linea
/*
	Comentarios de varias lineas
	Javier Andres Valdez Gonzalez - 201700703
*/

ingresar _pi_ como numero con_valor 2.1416
ingresar _multiOperador_ como numero con_valor (((-3*(5-6)/5) potencia 2) mod 2)

_pi_ -> 3.1416

fin
                """ ;


    @Test
    public void seeTokens(){

        Reader stringReader = new StringReader(testString);
        Lexer lexerHandler = new Lexer(stringReader);

        while (!lexerHandler.yyatEOF()){
            try {

                Symbol aux = lexerHandler.next_token();

                String nombre = ParserSym.terminalNames[aux.sym];
                System.out.println( nombre + " -- " + aux.value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Test
    public void testParser(){


        Reader stringReader = new StringReader(testString);
        Lexer lexerHandler = new Lexer(stringReader);

        ScannerBuffer buffer = new ScannerBuffer(lexerHandler);
        Parser p = new Parser(buffer);
        try {
            Object result = p.parse().value;
        } catch (Exception e) {

            Symbol sym = p.getS();
            System.out.println("Linea " + (sym.left +1) + " Columna " + (sym.right + 1 ) + ", texto: " + (sym.value) );

            throw new RuntimeException(e);
        }
        System.out.println(buffer.getBuffered());

    }

    @Test
    public void testTraductorPython(){

        Traductor_Python traductor_python = new Traductor_Python();

        System.out.println(traductor_python.comentario("/*Hola mundo \t */ "));

    }


}
