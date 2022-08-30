package com.backend.compiladores.services;


import com.backend.compiladores.services.scanner.Token;
import com.backend.compiladores.services.traductor.Traductor_Python;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class compilerController {

    public Traductor_Python traductor_python = new Traductor_Python();

    public static String compile(String code, String lenguage){

        if ("python".equals(lenguage)) {
            //CREANDO UN STRINGREADER PARA LEER EL TEXTO DE ENTRADA
            Reader stringReader = new StringReader(code);
            //CREANDO EL ANALIZADOR LEXICO PARA QUE LE PASE LOS TOKENS A EL PARSER
            Lexer lexerHandler = new Lexer(stringReader);
            //PASANDO EL BUFFER AL PARSER (DENTRO DEL BUFFER ESTA EL ANALIZADOR LEXICO)
            Parser p = new Parser(lexerHandler);
            try {
                //ANALIZAMOS SINTACTICAMENTE LA ENTRADA
                p.parse();
            } catch (Exception e) {
                //MANEJO DE ERRORES, sym.left = fila, sym.right = columna
                Symbol sym = p.getS();
                System.out.println("Linea " + (sym.left +1) + " Columna " + (sym.right + 1 ) + ", texto: " + (sym.value) );
                throw new RuntimeException(e);
            }


            return "answer";
        }else if ("go".equals(lenguage)){
            return "Esto fue compilado a " + lenguage +  " con exito" + "\n" + code ;
        }else{
            return "ERROR en el lenguaje de traduccion";
        }        
    }
}
