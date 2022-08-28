package com.backend.compiladores.services.traductor;

import java_cup.runtime.ScannerBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Traductor_Python extends Traductor {

    private int tabulaciones=0;


    public Traductor_Python(){

    }

    @Override
    public String traducir(ScannerBuffer buffer) {
        return null;
    }

    public String tabular(String texto){

        StringBuffer sb = new StringBuffer();
        sb.append(texto);

        for (int i = 0; i < this.tabulaciones; i++) {
            sb.append("\t");
        }

        return sb.toString();
    }

    @Override
    public String comentario(String comentario) {

        String aux = comentario.replaceAll("\\/\\/","#");
        aux = aux.replaceAll("\\/\\*","\'\'\'");
        aux = aux.replaceAll("\\*\\/","\'\'\'");

        return tabular(aux);
    }

    @Override
    public String inicio(String inicio) {
        this.tabulaciones+=1;
        return inicio.replaceAll("inicio","if __name__ == \'__main__\':");
    }


}
