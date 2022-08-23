package com.backend.compiladores;

import com.backend.compiladores.services.scanner.Lexer;
import com.backend.compiladores.services.scanner.Token;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class LexerTest {

    @Test
    public void matchExpresion(){
        String testString = """
                /*Probano el analizador lexico
                para ver los tokens*/
                
                10;
                10.02;
                5,6,5;
                "Esto es una cadena"
                Verdadero
                Falso
                'o'
                'P'
                '${70}'
                +
                -
                *
                /
                potencia
                mod
                ("cadena")
                inicio
                
                //comentario de una linea
                
                ingresar _variable_ como Cadena con_valor "Hola mundo"
                
                _variable_ -> "Adios mundo"
                
                
                
                fin
                 
                """ ;
        Reader stringReader = new StringReader(testString);
        Lexer lexerHandler = new Lexer(stringReader);
        try {
            while (!lexerHandler.yyatEOF()){
                Token token = lexerHandler.yylex();
                System.out.println(token);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
