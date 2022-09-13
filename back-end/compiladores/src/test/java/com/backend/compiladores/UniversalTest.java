package com.backend.compiladores;

import com.backend.compiladores.services.Lexer;
import com.backend.compiladores.services.Parser;
import com.backend.compiladores.services.ParserSym;
import com.backend.compiladores.services.helper.golang_helperSym;
import com.backend.compiladores.services.helper.golang_helper_lexer;
import com.backend.compiladores.services.traductor.Traductor_Go;
import com.backend.compiladores.services.traductor.Traductor_Python;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import java.io.*;
import java.util.LinkedList;

public class UniversalTest {
    String testString = """

                """ ;

    File file = new File("testing.txt");



    @Test
    public void seeTokens() throws FileNotFoundException {


        Lexer lexerHandler = new Lexer(new BufferedReader(new FileReader(file)));

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


        Lexer lexerHandler;
        try {
            lexerHandler = new Lexer(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Parser parser = new Parser(lexerHandler);
        //p.traductor = new Traductor_Python();
        try {
            parser.parse();
            parser.ast.graficar();
//            parser.traductor.generate_file("Test_file.txt");
        } catch (Exception e) {

            Symbol sym = parser.s;
            LinkedList<String> expected_tokens = parser.getExpectedTokens();

            if(sym != null){
                System.out.println("ERROR EN:  Linea " + (sym.left +1) + " Columna " + (sym.right + 1 ) + ", texto: " + (sym.value) );
                System.out.println(expected_tokens);
            }

            System.out.println(e);
            throw new RuntimeException(e);
        }


    }

    @Test
    public void testCaracteristicaTraductorPython(){

        Traductor_Python traductor_python = new Traductor_Python();
        String[] entradas = {
                "(_base_ Numero, _exponenete_ Numero)"
        };
        for (String entrada:entradas) {
            //System.out.println(traductor_python.getVariablesParametros(entrada));
        }


    }


    public Boolean searchIlligalToken(Lexer lexer) {
        while (!lexer.yyatEOF()){
            try {
                Symbol aux = lexer.next_token();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(lexer.illegalCharacter){

            System.out.println("Se encontraron errores lexicos en el archivo, arreglelos antes de poder continuar");

            for (int i = 0; i < lexer.illegalCharacters.size(); i++) {
                System.out.print("Caracter: " + lexer.illegalCharacters.get(i));
                System.out.print(" Linea: " + (lexer.illegalCharacterLine.get(i)+1));
                System.out.println(" Columna: " + (lexer.illegalCharacterColumn.get(i)+1));
            }

            return true;
        }

        return false;
    }

    @Test
    public void testTraductorPython() {

        Lexer lexerHandler;
        try {
            lexerHandler = new Lexer(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(!searchIlligalToken(lexerHandler)){
            Parser parser = new Parser(lexerHandler);
            Traductor_Python TP = new Traductor_Python();

            try {
                parser.parse();
                parser.ast.graficar();
                TP.traducir(parser.ast.raiz);
                TP.generate_file("Traduction_Python.py");

            } catch (Exception e) {

                Symbol sym = parser.s;
                LinkedList<String> expected_tokens = parser.getExpectedTokens();

                if (sym != null) {
                    System.out.println("ERROR EN:  Linea " + (sym.left + 1) + " Columna " + (sym.right + 1) + ", texto: " + (sym.value));
                    System.out.print("Izquierda: " + parser.getIzquierda() + " , ");
                    System.out.print("Derecha: " + parser.getDerecha() + " , ");
                    System.out.println("Expected tokens: " + expected_tokens);
                }

                System.out.println(e);
                throw new RuntimeException(e);
            }
        }

    }


    @Test
    public void seeTokensGo() throws FileNotFoundException {


        String[] entradas = {
                "(_variable1_ es_igual 5*5+8/2)",
                "'${100}'",
                "1+(1)",
                "30 potencia [22.2-2.2] + (2)",
                "(5*8) mod (1+5+6)"
        };

//        golang_helper_lexer lexerHandler = new golang_helper_lexer(new BufferedReader(new FileReader(file)));

        for (String entrada:entradas) {
            golang_helper_lexer lexerHandler = new golang_helper_lexer(new StringReader(entrada));

            while (!lexerHandler.yyatEOF()){
                try {

                    Symbol aux = lexerHandler.next_token();

                    String nombre = golang_helperSym.terminalNames[aux.sym];
                    System.out.println( nombre + " -- " + aux.value);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }


    @Test
    public void testCaracteristicaTraductorGo(){

        Traductor_Go traductor_go = new Traductor_Go();
        String[] entradas = {
                "(_variable1_ es_igual 5*5+8/2)",
                "'${100}'",
                "1+(1)",
                "30 potencia [22.2-2.2] + (2)",
                "(5*8) mod (1+5+6)"
        };
        for (String entrada:entradas) {
            System.out.println(traductor_go.traducir_valor(entrada));
        }


    }

    @Test
    public void testTraductorGo(){

        Lexer lexerHandler;
        try {
            lexerHandler = new Lexer(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Parser parser = new Parser(lexerHandler);
        Traductor_Go TG = new Traductor_Go();

        try {
            parser.parse();
            parser.ast.graficar();
            TG.traducir(parser.ast.raiz);
            TG.generate_file("Traduction_GO.go");

        } catch (Exception e) {

            Symbol sym = parser.s;
            LinkedList<String> expected_tokens = parser.getExpectedTokens();

            if(sym != null){
                System.out.println("ERROR EN:  Linea " + (sym.left +1) + " Columna " + (sym.right + 1 ) + ", texto: " + (sym.value) );
                System.out.println(expected_tokens);
            }

            System.out.println(e);
            throw new RuntimeException(e);
        }

    }

}
