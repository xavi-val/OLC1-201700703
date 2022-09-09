package com.backend.compiladores.services.traductor;

import com.backend.compiladores.services.Lexer;
import com.backend.compiladores.services.ParserSym;
import com.backend.compiladores.services.parserPackage.Nodo;
import java_cup.runtime.Symbol;
import org.apache.tools.ant.types.Environment;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Traductor_Python extends Traductor {


    private int tabulacion=0;

    public Traductor_Python(){
        this.final_traduction="";
    }

    @Override
    public void traducir(Nodo nodo) {
        
        if (nodo.getHijos().size()!=0){
            for (Nodo hijo:nodo.getHijos()) {
                
                if (hijo.getNombre()=="instruccion"){
                    traducir(hijo);
                } else if (hijo.getNombre()=="inicio") {
                    inicio();
                } else if (hijo.getNombre()=="comentario") {
                    comentario(hijo.getValor());
                } else if (hijo.getNombre()=="Declaracion") {
                    traducir_declaracion(hijo);
                } else if (hijo.getNombre()=="Asignacion") {
                    traducir_asignacion(hijo);
                } else if (hijo.getNombre()=="IF") {
                    traducir_if(hijo);
                } else if (hijo.getNombre()=="SELECT") {
                    traducir_select(hijo);
                } else if (hijo.getNombre()=="FOR") {
                    traducir(hijo);
                } else if (hijo.getNombre()=="WHILE") {
                    traducir(hijo);
                } else if (hijo.getNombre()=="REPETIR") {
                    traducir(hijo);
                } else if (hijo.getNombre()=="METODO") {
                    traducir(hijo);
                } else if (hijo.getNombre()=="FUNCION") {
                    traducir(hijo);
                } else if (hijo.getNombre()=="EJECUTAR") {

                } else if (hijo.getNombre() == "IMPRIMIR") {
                    imprimir(hijo);
                }
            }
        }
    }


    public String tabular(String texto){

        String aux="";

        for (int i = 0; i < this.tabulacion; i++) {
            aux += "\t";
        }

        return (aux+texto);
    }

    public void encolar(String texto){
        this.final_traduction += "\n";
        this.final_traduction += tabular(texto);
    }


    @Override
    public void inicio() {
        String aux = "if __name__ == \'__main__\':";
        encolar(aux);
        this.tabulacion+=1;
    }

    @Override
    public void comentario(String comentario) {

        String aux = comentario.replaceAll("\\/\\/","#");
        aux = aux.replaceAll("\\/\\*","\'\'\'");
        aux = aux.replaceAll("\\*\\/","\'\'\'");
        encolar(aux);
    }

    @Override
    public String traducir_booleano(String booleano) {
        booleano = booleano.replaceAll("(?i)verdadero","True");
        booleano = booleano.replaceAll("(?i)falso","False");
        return booleano;
    }

    @Override
    public String traducir_character(String character) {
        Pattern pattern = Pattern.compile("\\$\\{\\d+\\}");
        Matcher matcher = pattern.matcher(character);
        boolean matchFound = matcher.find();
        if(matchFound) {
            character = character.replaceAll("\\$\\{","");
            character = character.replaceAll("\\}","");
            character = character.replaceAll("\\'","");

            return  "'"+Character.toString((char) Integer.parseInt(character))+"'";


        } else {
            return character;
        }

    }

    @Override
    public void traducir_declaracion(Nodo decl_asig) {
        String[] variables={};
        String[] valores={};
        String aux="";

        for (Nodo hijo : decl_asig.getHijos()) {
            if (hijo.getNombre()=="Variables"){
                variables = hijo.getValor().split(",");
                aux = hijo.getValor();
            } else if (hijo.getNombre()=="Valores") {
                valores = hijo.getValor().split(",");
                //Traducimos los valores
                for (int i = 0; i < valores.length; i++) {
                    valores[i] = traducir_valor(valores[i]);
                }


                if(variables.length == 1 &&  valores.length==1){
                    aux += " = " + valores[0];
                }else if ( variables.length > 1 && valores.length>1) {
                    aux += " = ";
                    for (int i = 0; i < valores.length; i++) {
                        if (i+1==valores.length){
                            aux += valores[i];
                        }else{
                            aux += valores[i] + ",";
                        }
                    }
                } else if ( variables.length > 1 && valores.length==1) {
                    aux += " = ";
                    for (int i = 0; i < variables.length; i++) {
                        if (i+1==variables.length){
                            aux += valores[0];
                        }else{
                            aux += valores[0] + ",";
                        }
                    }
                }
            }
        }

        encolar(aux);

    }

    @Override
    public String traducir_valor(String valor) {

        Reader stringReader = new StringReader(valor);
        Lexer lexerHandler = new Lexer(stringReader);
        String respuesta="";

        while (!lexerHandler.yyatEOF()){
            try {
                Symbol aux = lexerHandler.next_token();
                String nombre = ParserSym.terminalNames[aux.sym];
                String valor_token = aux.value.toString();

                /*Valores no traducidos*/
                if (nombre=="CADENA"){
                    if (valor_token.contains("\n")){
                        respuesta += valor_token.replaceAll("\"", "\"\"\"");
                    }else{
                        respuesta += valor_token;
                    }

                }

                /*LOS CONDICIONALES*/
                if (nombre=="MAYOR"){
                    respuesta += ">";
                }else if (nombre=="MENOR") {
                    respuesta += "<";
                }else if (nombre=="MAYORIGUAL") {
                    respuesta += ">=";
                }else if (nombre=="MENORIGUAL") {
                    respuesta += "<=";
                }else if (nombre=="IGUAL") {
                    respuesta += "==";
                }else if (nombre=="DIFERENTE") {
                    respuesta += "!=";
                }else if (nombre=="OR") {
                    respuesta += "or";
                }else if (nombre=="AND") {
                    respuesta += "and";
                }else if (nombre=="NOT") {
                    respuesta += "not";
                }else if (nombre=="BOOLEAN") {
                    respuesta += traducir_booleano(valor_token);
                }

                /*Caracter*/
                if (nombre=="CARACTER"){
                    respuesta += traducir_character(valor_token);
                }

                /*Expresiones matematicas, numeros y variables*/
                if (nombre=="SUMA"){
                    respuesta += "+";
                }else if (nombre=="RESTA") {
                    respuesta += "-";
                }else if (nombre=="MULTI") {
                    respuesta += "*";
                }else if (nombre=="DIVI") {
                    respuesta += "/";
                }else if (nombre=="MODULO") {
                    respuesta += "%";
                }else if (nombre=="POTENCIA") {
                    respuesta += "**";
                }else if (nombre=="NUMERO") {
                    respuesta += valor_token;
                }else if (nombre=="VARIABLE") {
                    respuesta += valor_token;
                }

                /*Otros simbolos*/
                if (nombre=="LPAREN"){
                    respuesta += "(";
                }else if (nombre=="RPAREN") {
                    respuesta += ")";
                }else if (nombre=="LCOR") {
                    respuesta += "(";
                }else if (nombre=="RCOR") {
                    respuesta += ")";
                }

            } catch (IOException e) {
                System.out.println(e);
            }
        }



        return respuesta;
    }

    @Override
    public void traducir_asignacion(Nodo decl_asig) {
        String[] variables={};
        String[] valores={};
        String aux="";

        for (Nodo hijo : decl_asig.getHijos()) {
            if (hijo.getNombre()=="Variables"){
                variables = hijo.getValor().split(",");
                aux = hijo.getValor();
            } else if (hijo.getNombre()=="Valores") {
                valores = hijo.getValor().split(",");
                //Traducimos los valores
                for (int i = 0; i < valores.length; i++) {
                    valores[i] = traducir_valor(valores[i]);
                }


                if(variables.length == 1 &&  valores.length==1){
                    aux += " = " + valores[0];
                }else if ( variables.length > 1 && valores.length>1) {
                    aux += " = ";
                    for (int i = 0; i < valores.length; i++) {
                        if (i+1==valores.length){
                            aux += valores[i];
                        }else{
                            aux += valores[i] + ",";
                        }
                    }
                } else if ( variables.length > 1 && valores.length==1) {
                    aux += " = ";
                    for (int i = 0; i < variables.length; i++) {
                        if (i+1==variables.length){
                            aux += valores[0];
                        }else{
                            aux += valores[0] + ",";
                        }
                    }
                }
            }
        }

        encolar(aux);

    }


    @Override
    public void traducir_if(Nodo nodo){
        String aux ="if ";
        for (Nodo hijo : nodo.getHijos()) {
            if (hijo.getNombre()=="condicion"){
                aux += traducir_valor(hijo.getValor()) + " : ";
                encolar(aux);
                this.tabulacion+=1;
            }else if (hijo.getNombre()=="instruccion") {
                traducir(hijo);
            }else if (hijo.getNombre()=="ELSE_IF") {
                this.tabulacion-=1;
                traducir_else_if(hijo);
            }else if (hijo.getNombre()=="ELSE") {
                this.tabulacion-=1;
                traducir_else(hijo);
            }else if (hijo.getNombre()=="END_IF") {
                this.tabulacion-=1;
            }

        }

    }

    @Override
    public void traducir_else_if(Nodo nodo){
        String aux ="elif ";
        for (Nodo hijo : nodo.getHijos()) {
            if (hijo.getNombre()=="condicion"){
                aux += traducir_valor(hijo.getValor()) + " : ";
                encolar(aux);
                this.tabulacion+=1;
            }else if (hijo.getNombre()=="ELSE_IF") {
                this.tabulacion-=1;
                traducir_else_if(hijo);
            }else if (hijo.getNombre()=="instruccion") {
                traducir(hijo);
            }
        }

    };

    @Override
    public void traducir_else(Nodo nodo){
        String aux ="else :";
        for (Nodo hijo : nodo.getHijos()) {
           if (hijo.getNombre()=="instruccion") {
                encolar(aux);
                this.tabulacion+=1;
                traducir(hijo);
           }
        }

    };

    @Override
    public void traducir_select(Nodo nodo){
        String aux ="if ";
        String valor="";
        for (Nodo hijo : nodo.getHijos()) {
            if (hijo.getNombre()=="<Valor>"){
                valor = traducir_valor(hijo.getValor());
                aux += valor + " == ";

            }else if (hijo.getNombre()=="case") {
                aux += hijo.getValor().replaceAll("\\¿","")
                        .replaceAll("\\?","")
                        .replaceAll("(?i)entonces","") + " : ";
                encolar(aux);
                this.tabulacion+=1;
                for (Nodo nieto : hijo.getHijos()) {
                    if (nieto.getNombre()=="instruccion"){
                        traducir(nieto);
                        this.tabulacion-=1;
                    } else if (nieto.getNombre()=="case") {
                        traducir_case(nieto,valor);
                    }
                }
            } else if (hijo.getNombre()=="default") {
                encolar("else: ");
                this.tabulacion+=1;
                if (hijo.getHijos().size()!=0){
                    traducir(hijo.getHijos().get(0));
                }
                this.tabulacion-=1;
            }else if (hijo.getNombre()=="END_SELECT") {
                this.tabulacion-=1;
            }

        }
    }

    @Override
    public void traducir_case(Nodo nodo, String variable){
        String aux ="elif " + variable + " == " +   nodo.getValor().replaceAll("\\¿","")
                                                                    .replaceAll("\\?","")
                                                                    .replaceAll("(?i)entonces","") + " : ";
        encolar(aux);
        this.tabulacion+=1;

        for (Nodo hijo : nodo.getHijos()) {
            if (hijo.getNombre()=="case") {
                traducir_case(hijo,variable);
            }else if (hijo.getNombre()=="instruccion"){
                traducir(hijo);
                this.tabulacion-=1;
            }
        }
    };

    @Override
    public void imprimir(Nodo nodo){
        String aux ="print(";
        for (Nodo hijo : nodo.getHijos()) {
            if (hijo.getNombre()=="Valor"){
                aux += traducir_valor(hijo.getValor()) + ")";
            }

        }

        encolar(aux);
    }

}
