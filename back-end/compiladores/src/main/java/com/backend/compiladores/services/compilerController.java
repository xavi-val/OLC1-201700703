package com.backend.compiladores.services;


import com.backend.compiladores.services.scanner.Token;
import com.backend.compiladores.services.traductor.Traductor_Python;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class compilerController {

    public Traductor_Python traductor_python = new Traductor_Python();

    public static String compile(String code, String lenguage){

        if ("python".equals(lenguage)) {
            //LLAMAR AL ANALIZADOR LEXICO Y SINTACTICO

            return "answer";
        }else if ("go".equals(lenguage)){
            return "Esto fue compilado a " + lenguage +  " con exito" + "\n" + code ;
        }else{
            return "ERROR en el lenguaje de traduccion";
        }        
    }
}
