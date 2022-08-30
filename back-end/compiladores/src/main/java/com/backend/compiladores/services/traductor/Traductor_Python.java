package com.backend.compiladores.services.traductor;

import java_cup.runtime.ScannerBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Traductor_Python extends Traductor {

    private int tabulaciones=0;


    public Traductor_Python(){
        this.final_traduction="";
    }

    public String tabular(String texto){

        StringBuffer sb = new StringBuffer();
        sb.append(texto);

        for (int i = 0; i < this.tabulaciones; i++) {
            sb.append("\t");
        }

        return sb.toString();
    }

    public void encolar(String texto){
        this.final_traduction += "\n";
        this.final_traduction += tabular(texto);
    }

    @Override
    public void comentario(String comentario) {

        String aux = comentario.replaceAll("\\/\\/","#");
        aux = aux.replaceAll("\\/\\*","\'\'\'");
        aux = aux.replaceAll("\\*\\/","\'\'\'");
        encolar(aux);
    }

    @Override
    public void inicio(String inicio) {
        this.tabulaciones+=1;
        String aux = inicio.replaceAll("inicio","if __name__ == \'__main__\':");
        encolar(aux);
    }

    @Override
    public String booleano(String booleano) {
        String aux ="";
        aux = booleano.replaceAll("Verdadero","True");
        aux = booleano.replaceAll("Falso","False");
        return aux;
    }

    @Override
    public String character(String character) {
        Pattern pattern = Pattern.compile("\\$\\{\\d+\\}");
        Matcher matcher = pattern.matcher(character);
        boolean matchFound = matcher.find();
        if(matchFound) {
            character = character.replaceAll("\\$\\{","");
            character = character.replaceAll("\\}","");

            return  Character.toString((char) Integer.parseInt(character));


        } else {
            return character;
        }

    }

    @Override
    public void declaracion_asignacion(String decl_asig) {
        String aux ="";
        aux = decl_asig.replaceAll("con_valor","=");
    }


}
