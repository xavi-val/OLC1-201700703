package com.backend.compiladores.services.traductor;

import com.backend.compiladores.services.parserPackage.Nodo;
import java_cup.runtime.ScannerBuffer;
import java.io.*;

public abstract class Traductor {

    public String final_traduction="An easter egg";
    public String cabecera="";

    public ScannerBuffer listaTokens;

    public Traductor(ScannerBuffer buffer){
        this.listaTokens = buffer;
    }

    public Traductor(){

    }

    public void generate_file(String fileName){
        try {
            Writer fileWriter = new FileWriter((".\\src\\main\\resources\\traductions\\"+fileName),false);
            fileWriter.write(this.final_traduction);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public abstract void traducir(Nodo nodo);

    public abstract void inicio();

    public abstract void comentario(String comentario);


    public abstract String traducir_booleano(String booleano);

    public abstract String traducir_character(String character);

    public abstract void traducir_declaracion(Nodo decl_asig);

    public abstract String traducir_valor(String valor);

    public abstract void traducir_asignacion(Nodo decl_asig);

    public abstract void traducir_if(Nodo nodo);

    public abstract void traducir_else_if(Nodo nodo);

    public abstract void traducir_else(Nodo nodo);

    public abstract void traducir_select(Nodo nodo);

    public abstract void traducir_case(Nodo nodo, String variable);

    public abstract void traducir_for(Nodo nodo);

    public abstract void traducir_while(Nodo nodo);

    public abstract void traducir_do_while(Nodo nodo);

    public abstract void traducir_metodo(Nodo nodo);

    public abstract void traducir_funcion(Nodo nodo);

    public abstract String traducir_llamadas(Nodo nodo, Boolean salto_de_linea);

    public abstract void imprimir(Nodo nodo);
}
