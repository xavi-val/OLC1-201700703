package com.backend.compiladores.services.parserPackage;

import java_cup.runtime.Symbol;

import java.util.ArrayList;

public class Nodo {


    private String nombre;
    private ArrayList<Nodo> hijos = new ArrayList<>();
    private String valor;
    private int numNodo;


    public Nodo()
    {

    }

    public Nodo(String nombre)
    {
        this.nombre = nombre;
        this.hijos = new ArrayList<>();
        this.numNodo = 0;
    }

    public Nodo(String nombre, String valor)
    {
        this.nombre = nombre;
        this.hijos = new ArrayList<>();
        this.numNodo = 0;
        this.valor = valor;
    }



    public void addHijo(Nodo hijo)
    {
        hijos.add(hijo);
    }

    //SETTERS Y GETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public ArrayList<Nodo> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = hijos;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getNumNodo() {
        return numNodo;
    }

    public void setNumNodo(int numNodo) {
        this.numNodo = numNodo;
    }
}
