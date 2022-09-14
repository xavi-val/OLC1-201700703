package com.backend.compiladores.services;


import com.backend.compiladores.services.response.traductionResponse;
import com.backend.compiladores.services.scanner.Token;
import com.backend.compiladores.services.traductor.Traductor_Go;
import com.backend.compiladores.services.traductor.Traductor_Python;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;

public class compilerController {

    public Traductor_Python traductor_python = new Traductor_Python();

    public static String searchIlligalToken(String codigo) {

        Lexer lexer = null;
        lexer = new Lexer(new StringReader(codigo));
        String respuesta="";

        while (!lexer.yyatEOF()){
            try {
                Symbol aux = lexer.next_token();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(lexer.illegalCharacter){

            respuesta+="--------------⚠ Warning--------------\n";
            respuesta+="Se encontraron errores lexicos en el archivo. \nSe recomienda corregirlos antes de continuar\n";

            for (int i = 0; i < lexer.illegalCharacters.size(); i++) {
                respuesta+="Caracter: " + lexer.illegalCharacters.get(i);
                respuesta+=" Linea: " + (lexer.illegalCharacterLine.get(i)+1);
                respuesta+=" Columna: " + (lexer.illegalCharacterColumn.get(i)+1) + "\n";
            }

            return respuesta;
        }

        return respuesta;
    }


    public static traductionResponse compile(String code, String lenguage){

        Lexer lexerHandler = new Lexer(new StringReader(code));
        Parser parser = new Parser(lexerHandler);
        traductionResponse response = new traductionResponse();

        response.error = searchIlligalToken(code);

        if ("python".equals(lenguage)) {

            Traductor_Python TP = new Traductor_Python();

            try {
                parser.parse();
                parser.ast.graficar();
                TP.traducir(parser.ast.raiz);
                response.setTraduccion(TP.final_traduction);

                for (String error: parser.error_list) {
                    response.error += "\n" + error;
                }

                String image = Files.readString(new File(".\\src\\main\\resources\\trees\\arbol.svg").toPath());
                response.setImage(image);

            } catch (Exception e) {

                Symbol sym = parser.s;
                LinkedList<String> expected_tokens = parser.getExpectedTokens();

                if(sym != null){
                    String salida = "NO SE PUDO RECUPERAR DE LOS ERRORES \n";
                    salida += "ERROR EN:  Linea " + (sym.left +1) + " Columna " + (sym.right + 1 ) + ", texto: " + (sym.value);
                    salida += "\n se esperaban los siguientes TOKENS: " + expected_tokens.toString();
                    response.error+="\n"+salida;
                }
                System.out.println(e);
            }


            return response;
        }else if ("go".equals(lenguage)){
            Traductor_Go TG = new Traductor_Go();

            try {
                parser.parse();
                parser.ast.graficar();
                TG.traducir(parser.ast.raiz);
                response.setTraduccion(TG.final_traduction);

                for (String error: parser.error_list) {
                    response.error += "\n" + error;
                }

                String image = Files.readString(new File(".\\src\\main\\resources\\trees\\arbol.svg").toPath());
                response.setImage(image);

            } catch (Exception e) {

                Symbol sym = parser.s;
                LinkedList<String> expected_tokens = parser.getExpectedTokens();

                if(sym != null){
                    String salida = "NO SE PUDO RECUPERAR DE LOS ERRORES \n";
                    salida += "ERROR EN:  Linea " + (sym.left +1) + " Columna " + (sym.right + 1 ) + ", texto: " + (sym.value);
                    salida += "\n se esperaban los siguientes TOKENS: " + expected_tokens.toString();
                    response.error+="\n" + salida;
                }
                System.out.println(e);
            }


            return response;
        }else{
            return null;
            //return "ERROR en el lenguaje de traduccion";
        }        
    }
}
