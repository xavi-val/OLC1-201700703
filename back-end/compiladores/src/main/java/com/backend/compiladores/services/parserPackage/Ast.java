package com.backend.compiladores.services.parserPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ast {
    public Nodo raiz = new Nodo("ROOT");

    public void graficar(Nodo raiz){
        FileWriter archivo = null;
        PrintWriter pw = null;
        String cadena = graficarNodo(raiz);

        try{
            archivo = new FileWriter("arbol.dot");
            pw = new PrintWriter(archivo);
            pw.println("digraph G {node[shape=box, style=filled, color=blanchedalmond]; edge[color=chocolate3];rankdir=UD \n");
            pw.println(cadena);
            pw.println("\n}");
            archivo.close();
        }catch (Exception e) {
            System.out.println(e +" 1");
        }

        try {
            String cmd = "dot -Tpng arbol.dot -o arbol.png";
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ioe) {
            System.out.println(ioe +" 2");
        }

    }

    public String graficarNodo(Nodo nodo){
        String cadena = "";
        for(Nodo hijos : nodo.getHijos())
        {
            cadena += "\"" + nodo.getNumNodo() + "_" + nodo.getNombre() + " -> " + nodo.getValor() + "\"->\"" + hijos.getNumNodo() + "_" + hijos.getNombre() + " -> " + hijos.getValor() + "\"\n";
            cadena += graficarNodo(hijos);
        }
        return cadena;
    }

}
