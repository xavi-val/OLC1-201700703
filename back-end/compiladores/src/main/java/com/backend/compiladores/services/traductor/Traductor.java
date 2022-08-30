package com.backend.compiladores.services.traductor;

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


    public abstract void comentario(String comentario);
    public abstract void inicio(String inicio);

    public abstract String booleano(String booleano);

    public abstract String character(String character);

    public abstract void declaracion_asignacion(String decl_asig);

}
