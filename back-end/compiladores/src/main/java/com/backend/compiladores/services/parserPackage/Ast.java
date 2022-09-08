package com.backend.compiladores.services.parserPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ast {
    public Nodo raiz = new Nodo("ROOT");

    public void graficar(){
        FileWriter archivo ;
        PrintWriter pw ;
        String cadena = graficarNodo(raiz);

        String path = ".\\src\\main\\resources\\trees\\";

        try{
            archivo = new FileWriter(path+"arbol.dot");
            pw = new PrintWriter(archivo);
            pw.println("digraph G {node[shape=box, style=filled, color=black, fillcolor=white]; edge[color=black];rankdir=UD \n");
            pw.println(cadena);
            pw.println("\n}");
            archivo.close();
        }catch (Exception e) {
            System.out.println(e +" 1");
        }

        try {
            String cmd = "dot -Tsvg " + path + "arbol.dot -o " + path + "arbol.svg";
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ioe) {
            System.out.println(ioe +" 2");
        }

    }

    public String graficarNodo(Nodo nodo){
        String cadena = "";
        for(Nodo hijos : nodo.getHijos())
        {
            cadena += "\"" + nodo.getNumNodo() + "_" + nodo.getNombre() + " -> " + parsearComillas(nodo.getValor()) + "\"->\"" + hijos.getNumNodo() + "_" + hijos.getNombre() + " -> " + parsearComillas(hijos.getValor()) + "\"\n";
            cadena += graficarNodo(hijos);
        }
        return cadena;
    }

    /*graphviz tiene problema cuando detecta el caracter comilla "
        por lo tanto se le colca como un meta caracter usando la barra invertida
    */

    public String parsearComillas(String valor){

        if (valor != null){
            String aux = valor.replaceAll("\\\"","\\\\\\\"");
            return aux;
        }

        return null;
    }

}
