package com.backend.compiladores.services;


import com.backend.compiladores.services.response.traductionResponse;
import com.backend.compiladores.services.scanner.Token;
import com.backend.compiladores.services.traductor.Traductor_Python;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;

public class compilerController {

    public Traductor_Python traductor_python = new Traductor_Python();

    public static traductionResponse compile(String code, String lenguage){

        Lexer lexerHandler = new Lexer(new StringReader(code));
        Parser parser = new Parser(lexerHandler);
        traductionResponse response = new traductionResponse();

        if ("python".equals(lenguage)) {

            Traductor_Python TP = new Traductor_Python();

            try {
                parser.parse();
                parser.ast.graficar();
                TP.traducir(parser.ast.raiz);


                response.setTraduccion(TP.final_traduction);

                String image = Files.readString(new File(".\\src\\main\\resources\\trees\\arbol.svg").toPath());
                response.setImage(image);

            } catch (Exception e) {

                Symbol sym = parser.s;
                LinkedList<String> expected_tokens = parser.getExpectedTokens();

                if(sym != null){

                    String salida = "ERROR EN:  Linea " + (sym.left +1) + " Columna " + (sym.right + 1 ) + ", texto: " + (sym.value);
                    salida += "\n se esperaban los siguientes TOKENS: " + expected_tokens.toString();
                    response.setError(salida);
                }
                System.out.println(e);
            }


            return response;
        }else if ("go".equals(lenguage)){
            return null;
            //return "Esto fue compilado a " + lenguage +  " con exito" + "\n" + code ;
        }else{
            return null;
            //return "ERROR en el lenguaje de traduccion";
        }        
    }
}
