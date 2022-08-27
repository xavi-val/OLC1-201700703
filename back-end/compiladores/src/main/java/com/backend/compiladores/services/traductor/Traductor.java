package com.backend.compiladores.services.traductor;

public abstract class Traductor {

    public String final_traduction="";
    public String cabecera="";

    public Traductor(){

    }

    public abstract String traducir();
    public abstract String comentario();




}
