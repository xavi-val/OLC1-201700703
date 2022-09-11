package com.backend.compiladores;

import com.backend.compiladores.services.Lexer;
import com.backend.compiladores.services.Parser;
import com.backend.compiladores.services.ParserSym;
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

    @Test
    public void testTraductorPython(){

        Lexer lexerHandler;
        try {
            lexerHandler = new Lexer(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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

            if(sym != null){
                System.out.println("ERROR EN:  Linea " + (sym.left +1) + " Columna " + (sym.right + 1 ) + ", texto: " + (sym.value) );
                System.out.println(expected_tokens);
            }

            System.out.println(e);
            throw new RuntimeException(e);
        }

    }


    @Test
    public void testCaracteristicaTraductorGo(){

        Traductor_Go traductor_go = new Traductor_Go();
        String[] entradas = {
                "(_base_ Numero, _exponenete_ Numero)"
        };
        for (String entrada:entradas) {
            System.out.println(traductor_go.getVariablesParametros(entrada));
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
