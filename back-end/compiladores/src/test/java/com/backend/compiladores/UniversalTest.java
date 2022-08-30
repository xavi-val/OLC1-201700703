package com.backend.compiladores;

import com.backend.compiladores.services.Lexer;
import com.backend.compiladores.services.Parser;
import com.backend.compiladores.services.ParserSym;
import com.backend.compiladores.services.traductor.Traductor_Python;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.Test;

import java.io.*;

public class UniversalTest {
    String testString = """

                """ ;

    File file = new File("testing.txt");



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


        Lexer lexerHandler = null;
        try {
            lexerHandler = new Lexer(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Parser p = new Parser(lexerHandler);
        p.traductor = new Traductor_Python();
        try {
            p.parse();
            p.traductor.generate_file("Test_file.txt");
        } catch (Exception e) {

            Symbol sym = p.getS();
            System.out.println("Linea " + (sym.left +1) + " Columna " + (sym.right + 1 ) + ", texto: " + (sym.value) );

            throw new RuntimeException(e);
        }


    }

    @Test
    public void testTraductorPython(){

        Traductor_Python traductor_python = new Traductor_Python();
        System.out.println(traductor_python.character("${85}"));

    }


}