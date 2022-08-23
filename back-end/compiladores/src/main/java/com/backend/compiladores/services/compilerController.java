package com.backend.compiladores.services;


import com.backend.compiladores.services.scanner.Lexer;
import com.backend.compiladores.services.scanner.Token;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class compilerController {
    public String compile(String code, String lenguage){

        Reader stringReader = new StringReader(code);
        Lexer lexerHandler = new Lexer(stringReader);

        String answer="";

        try {
            while (!lexerHandler.yyatEOF()){
                Token token = lexerHandler.yylex();
                answer+=token.toString() + "\n";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if ("python".equals(lenguage)) {
            return answer;
        }else if ("go".equals(lenguage)){
            return "Esto fue compilado a " + lenguage +  " con exito" + "\n" + code ;
        }else{
            return "ERROR en el lenguaje de transpilacion";
        }        
    }
}
