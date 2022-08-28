package com.backend.compiladores.services.traductor;

import java_cup.runtime.ScannerBuffer;


public abstract class Traductor {

    public String final_traduction="";
    public String cabecera="";
    public ScannerBuffer listaTokens;

    public Traductor(ScannerBuffer buffer){
        this.listaTokens = buffer;
    }

    public Traductor(){

    }

    public abstract String traducir(ScannerBuffer buffer);
    public abstract String comentario(String comentario);

    public abstract String inicio(String inicio);


}
