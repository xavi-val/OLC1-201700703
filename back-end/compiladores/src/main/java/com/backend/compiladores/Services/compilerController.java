package com.backend.compiladores.Services;

public class compilerController {
    public String compile(String code, String lenguage){
        
        return "Esto fue compilado a " + lenguage +  " con exito" + "\n" + code ;
    }
}
