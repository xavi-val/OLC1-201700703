package com.backend.compiladores.services.traductor;

import com.backend.compiladores.services.Lexer;
import com.backend.compiladores.services.ParserSym;
import com.backend.compiladores.services.helper.golang_helper_lexer;
import com.backend.compiladores.services.parserPackage.Nodo;
import java_cup.runtime.Symbol;

//Ayuda para traducir a golang con un parser
import com.backend.compiladores.services.helper.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Traductor_Go extends Traductor {


    private int tabulacion=0;

    public Traductor_Go(){
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
                } else if (hijo.getNombre()=="final") {
                    this.tabulacion-=1;
                    agregarEncabezado();
                    encolar("}");
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
                    traducir_for(hijo);
                } else if (hijo.getNombre()=="WHILE") {
                    traducir_while(hijo);
                } else if (hijo.getNombre()=="REPETIR") {
                    traducir_do_while(hijo);
                } else if (hijo.getNombre()=="METODO") {
                    traducir_metodo(hijo);
                } else if (hijo.getNombre()=="FUNCION") {
                    traducir_funcion(hijo);
                } else if (hijo.getNombre()=="EJECUTAR") {
                    traducir_llamadas(hijo,true);
                } else if (hijo.getNombre() == "IMPRIMIR" || hijo.getNombre() == "IMPRIMIR_SS") {
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

    public void agregarEncabezado(){
        String aux = "package main \nimport(";
        for (String libreria:this.cabecera) {
            aux += "\n\t\"" + libreria + "\"";
        }
        aux+="\n)\n\n";
        this.final_traduction = aux + final_traduction;
    }

    @Override
    public void inicio() {
        String aux = "func main(){";
        encolar(aux);
        this.tabulacion+=1;
    }

    @Override
    public void comentario(String comentario) {
        encolar(comentario);
    }

    @Override
    public String traducir_booleano(String booleano) {
        booleano = booleano.replaceAll("(?i)verdadero","true");
        booleano = booleano.replaceAll("(?i)falso","false");
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


    public String obtenerTipado(String valor){
        String tipo="";


        if(valor.matches("-?[0-9]+(\\.)?[0-9]+")){
            tipo = " float64 ";
        }else if(valor.matches("-?[0-9]+")){
            tipo = " int ";
        } else if (valor.matches("\\\"([^\\\"])*\\\"")) {
            tipo = " string ";
        } else if (valor.toLowerCase().matches("(verdadero|falso)")) {
            tipo = " bool ";
        } else if (valor.matches("(\\'.\\' | \\'\\$\\{[0-9]+\\}\\')")) {
            tipo = "string";
        }


        return tipo;
    }

    @Override
    public void traducir_declaracion(Nodo decl_asig) {
        String[] variables={};
        String[] valores={};
        String aux="var ";
        String tipo="";

        for (Nodo hijo : decl_asig.getHijos()) {
            if (hijo.getNombre()=="Variables"){
                variables = hijo.getValor().split(",");
                aux += hijo.getValor();
            } else if (hijo.getNombre()=="TIPO") {



                for (Nodo hijo2 : decl_asig.getHijos()) {
                    if (hijo2.getNombre() == "Valores") {
                        valores = hijo2.getValor().split(",");
                        tipo = obtenerTipado(valores[0]);
                    }
                }


                aux+=tipo;

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

        if(valor==""){
            return "";
        } else if (valor.toLowerCase().contains("potencia")) {
            //TRADUCIMOS POR SI VIENE POTENCIA
            golang_helper gh = new golang_helper(new golang_helper_lexer(new StringReader(valor)));
            try {
                gh.parse();
                valor = gh.valor;
            } catch (Exception e) {
                Symbol sym = gh.s;
                LinkedList<String> expected_tokens = gh.getExpectedTokens();

                if(sym != null){
                    System.out.println("ERROR EN:  Linea " + (sym.left +1) + " Columna " + (sym.right + 1 ) + ", texto: " + (sym.value) );
                    System.out.println(expected_tokens);
                    System.out.println("SOY EL HELPER DE GOLANG");
                }

                System.out.println(e);
                throw new RuntimeException(e);
            }
        }

        Reader stringReader = new StringReader(valor);
        golang_helper_lexer lexerHandler = new golang_helper_lexer(stringReader);
        String respuesta="";

        while (!lexerHandler.yyatEOF()){
            try {
                Symbol aux = lexerHandler.next_token();
                String nombre = golang_helperSym.terminalNames[aux.sym];
                String valor_token = aux.value.toString();

                /*Valores no traducidos*/
                if (nombre=="CADENA"){
                    if (valor_token.contains("\n")){
                        respuesta += valor_token.replaceAll("\"", "`");
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
                    respuesta += "||";
                }else if (nombre=="AND") {
                    respuesta += "&&";
                }else if (nombre=="NOT") {
                    respuesta += "!";
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
                    respuesta += valor_token;
                    if (!this.cabecera.contains("math")){
                        this.cabecera.add("math");
                    }
                }else if (nombre=="FLOAT") {
                    respuesta += valor_token;
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
                }else if (nombre=="COMA") {
                    respuesta += valor_token;
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
            } else if (hijo.getNombre()=="Valores") {
                valores = hijo.getValor().split(",");
                //Traducimos los valores
                for (int i = 0; i < valores.length; i++) {
                    valores[i] = traducir_valor(valores[i]);
                }

                for (int i = 0; i < variables.length; i++) {
                    aux = variables[i] + " = " + valores[0];
                    encolar(aux);
                }
            }
        }


    }

    @Override
    public void traducir_if(Nodo nodo){
        String aux ="if ";
        for (Nodo hijo : nodo.getHijos()) {
            if (hijo.getNombre()=="condicion"){
                aux += traducir_valor(hijo.getValor()) + " { ";
                encolar(aux);
                this.tabulacion+=1;
            }else if (hijo.getNombre()=="instruccion") {
                traducir(hijo);
                this.tabulacion-=1;
                encolar("}");
                this.tabulacion+=1;
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
        String aux =" else if ";
        for (Nodo hijo : nodo.getHijos()) {
            if (hijo.getNombre()=="condicion"){
                aux += traducir_valor(hijo.getValor()) + " { ";
                this.final_traduction += aux;
                this.tabulacion+=1;
            }else if (hijo.getNombre()=="ELSE_IF") {
                this.tabulacion-=1;
                traducir_else_if(hijo);
            }else if (hijo.getNombre()=="instruccion") {
                traducir(hijo);
                this.tabulacion-=1;
                encolar("}");
                this.tabulacion+=1;
            }
        }

    };

    @Override
    public void traducir_else(Nodo nodo){
        String aux =" else {";
        for (Nodo hijo : nodo.getHijos()) {
            if (hijo.getNombre()=="instruccion") {
                this.final_traduction += aux;
                this.tabulacion+=1;
                traducir(hijo);
            }
        }
        this.tabulacion-=1;
        encolar("}");
        this.tabulacion+=1;
    };

    @Override
    public void traducir_select(Nodo nodo){
        String aux ="switch ";
        String valor="";
        for (Nodo hijo : nodo.getHijos()) {
            if (hijo.getNombre()=="<Valor>"){
                valor = traducir_valor(hijo.getValor());
                aux += valor+"{";
                encolar(aux);
                this.tabulacion+=1;

            }else if (hijo.getNombre()=="case") {
                aux = "case " + traducir_valor(hijo.getValor().replaceAll("\\¿","")
                                                .replaceAll("\\?","")
                                                .replaceAll("(?i)entonces","")) + " : ";
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
                encolar("default: ");
                this.tabulacion+=1;
                if (hijo.getHijos().size()!=0){
                    traducir(hijo.getHijos().get(0));
                }
                this.tabulacion-=1;
            }else if (hijo.getNombre()=="END_SELECT") {
                this.tabulacion-=1;
                encolar("}");
            }

        }
    }

    @Override
    public void traducir_case(Nodo nodo, String variable){
        String aux ="case " + traducir_valor(nodo.getValor().replaceAll("\\¿","")
                .replaceAll("\\?","")
                .replaceAll("(?i)entonces","")) + " : ";
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
    public void traducir_for(Nodo nodo){

        Boolean hayIncremental=false;
        String aux="for ";
        String Variable = "";

        for (Nodo hijo: nodo.getHijos()) {
            if (hijo.getNombre() == "Incremental") {
                hayIncremental=true;
            }
        }

        for (Nodo hijo: nodo.getHijos()) {
            if (hijo.getNombre()=="<Variable>"){
                Variable = hijo.getValor();
                aux += Variable + " :=" ;
            } else if (hijo.getNombre()=="<Valor inicial>") {
                aux+=traducir_valor(hijo.getValor()) + ";";
            } else if (hijo.getNombre()=="<Valor final>") {
                if (hayIncremental){
                    aux+= Variable + "<" + traducir_valor(hijo.getValor()) + ";";
                }else{
                    aux+=Variable + "<" + traducir_valor(hijo.getValor()) + ";" + Variable + "++" + " {";
                    encolar(aux);
                    this.tabulacion+=1;
                }
            } else if (hijo.getNombre()=="Incremental") {
                aux+=Variable + "+=" + traducir_valor(hijo.getValor()) + "{";
                encolar(aux);
                this.tabulacion+=1;
            } else if (hijo.getNombre()=="instruccion") {
                traducir(hijo);
            } else if (hijo.getNombre()=="<END_FOR>") {
                this.tabulacion-=1;
                encolar("}");
            }
        }
    };
    @Override
    public void traducir_while(Nodo nodo){
        String aux="";
        encolar("for true {");
        this.tabulacion+=1;

        for (Nodo hijo: nodo.getHijos()) {
            if (hijo.getNombre() == "<condicion>") {
                aux+=  "if !(" + traducir_valor(hijo.getValor()) +"){";
                encolar(aux);
                this.tabulacion+=1;
                encolar("break");
                this.tabulacion-=1;
                encolar("}");
            } else if (hijo.getNombre() == "instruccion") {
                traducir(hijo);
            } else if (hijo.getNombre() == "END_WHILE") {
                this.tabulacion-=1;
                encolar("}");
            }
        }
    };

    @Override
    public void traducir_do_while(Nodo nodo){
        String aux="";
        encolar("for true {");
        this.tabulacion+=1;

        for (Nodo hijo: nodo.getHijos()) {
            if (hijo.getNombre() == "<condicion>") {
                aux+=  "if !(" + traducir_valor(hijo.getValor()) +"){";
                encolar(aux);
                this.tabulacion+=1;
                encolar("break");
                this.tabulacion-=1;
                encolar("}");
            } else if (hijo.getNombre() == "instruccion") {
                traducir(hijo);
            }
        }

        this.tabulacion-=1;
        encolar("}");
    };

    @Override
    public void traducir_metodo(Nodo nodo){
        String aux="";
        Boolean hayParametros=false;

        for (Nodo hijo: nodo.getHijos()) {
            if (hijo.getNombre() == "parametros") {
                hayParametros=true;
            }
        }


        for (Nodo hijo: nodo.getHijos()) {
            if (hijo.getNombre() == "Nombre") {

                aux += hijo.getValor() + ":= func";

                if (!hayParametros){
                    aux+= "(){";
                    encolar(aux);
                    this.tabulacion+=1;
                }else{
                    aux+="(";
                }
            } else if (hijo.getNombre() == "parametros") {
                aux+=getVariablesParametros(hijo.getValor()) + "){";
                encolar(aux);
                this.tabulacion+=1;
            } else if (hijo.getNombre() == "instruccion") {
                traducir(hijo);
            } else if (hijo.getNombre() == "FIN_METODO") {
                this.tabulacion-=1;
                encolar("}");
            }
        }
    };

    public String getVariablesParametros(String valor) {
        Reader stringReader = new StringReader(valor);
        Lexer lexerHandler = new Lexer(stringReader);
        String respuesta="";

        while (!lexerHandler.yyatEOF()){
            Symbol aux = null;
            try {
                aux = lexerHandler.next_token();
                String nombre = ParserSym.terminalNames[aux.sym];
                String valor_token = aux.value.toString();


                if (nombre=="VARIABLE"){
                    respuesta+=valor_token;
                } else if (nombre=="COMA") {
                    respuesta+=valor_token;
                } else if (nombre=="TIPO") {
                    if ("numero".equalsIgnoreCase(valor_token)){
                        respuesta += " int";
                    } else if ("cadena".equalsIgnoreCase(valor_token)) {
                        respuesta += " string";
                    } else if ("boolean".equalsIgnoreCase(valor_token)) {
                        respuesta += " boolean";
                    } else if ("caracter".equalsIgnoreCase(valor_token)) {
                        respuesta += " string";
                    }
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return respuesta;
    }
    @Override
    public void traducir_funcion(Nodo nodo){
        String aux="";
        String tipoFuncion="";
        Boolean hayParametros=false;

        for (Nodo hijo: nodo.getHijos()) {
            if (hijo.getNombre() == "parametros") {
                if (hijo.getValor()!="()"){
                    hayParametros=true;
                }
            }else if (hijo.getNombre() == "Tipo") {
                if ("numero".equalsIgnoreCase(hijo.getValor())){
                    tipoFuncion += " int";
                } else if ("cadena".equalsIgnoreCase(hijo.getValor())) {
                    tipoFuncion += " string";
                } else if ("boolean".equalsIgnoreCase(hijo.getValor())) {
                    tipoFuncion += " boolean";
                } else if ("caracter".equalsIgnoreCase(hijo.getValor())) {
                    tipoFuncion += " string";
                }
            }
        }


        for (Nodo hijo: nodo.getHijos()) {

            if (hijo.getNombre() == "Nombre") {

                aux += hijo.getValor() + ":= func";

                if (!hayParametros){
                    aux+= "(){";
                    encolar(aux);
                    this.tabulacion+=1;
                }else{
                    aux+="(";
                }
            } else if (hijo.getNombre() == "parametros") {
                aux+=getVariablesParametros(hijo.getValor()) + ") " + tipoFuncion + " {";
                encolar(aux);
                this.tabulacion+=1;
            } else if (hijo.getNombre() == "instruccion") {
                traducir(hijo);
            } else if (hijo.getNombre() == "RETURN") {
                String retorno = "return " +traducir_valor(hijo.getValor().replaceAll(";",""));
                encolar(retorno);
            } else if (hijo.getNombre() == "FIN_FUNCION") {
                this.tabulacion-=1;
                encolar("}");
            }


        }
    };
    @Override
    public String traducir_llamadas(Nodo nodo, Boolean salto_de_linea){
        String aux="";
        Boolean hayParametros=false;

        for (Nodo hijo: nodo.getHijos()) {
            if (hijo.getNombre() == "parametros") {
                hayParametros=true;
            }
        }

        for (Nodo hijo: nodo.getHijos()) {
            if (hijo.getNombre() == "Nombre") {
                if (!hayParametros){
                    aux+= hijo.getValor() + "()";
                    encolar(aux);
                }else{
                    aux+= hijo.getValor() + "(";
                }
            } else if (hijo.getNombre() == "parametros") {

                if(hayParametros){
                    String[] parametros =hijo.getValor().replaceAll("\\(","").replaceAll("\\)","").split(",");

                    for(String parametro:parametros){
                        if (parametro != parametros[parametros.length-1]){
                            aux += traducir_valor(parametro) + ",";
                        }else{
                            aux += traducir_valor(parametro);
                        }
                    }


                    aux+= ")";
                    if (salto_de_linea){
                        encolar(aux);
                    }else{
                        return aux;
                    }

                }
            }
        }

        return null;
    };

    @Override
    public void imprimir(Nodo nodo){
        String aux ="";

        if(nodo.getNombre()=="IMPRIMIR"){
            aux+= "fmt.Print(";
        }else{
            aux+= "fmt.Println(";
        }


        for (Nodo hijo : nodo.getHijos()) {
            if (hijo.getNombre()=="Valor"){
                aux += traducir_valor(hijo.getValor()) + ")";
                encolar(aux);
            } else if (hijo.getNombre()=="EJECUTAR") {
                aux += traducir_llamadas(hijo,false) + ")";
                encolar(aux);
            }

        }


        if (!this.cabecera.contains("fmt")){
            this.cabecera.add("fmt");
        }

    }

}
