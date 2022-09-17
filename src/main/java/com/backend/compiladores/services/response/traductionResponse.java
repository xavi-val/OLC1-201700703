package com.backend.compiladores.services.response;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class traductionResponse {
    protected   String traduccion="";
    public   String error="";
    protected   String image="";


    public String getTraduccion() {
        return traduccion;
    }

    public String getError() {
        return error;
    }

    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public void setTraduccion(String traduccion) {
        this.traduccion = traduccion;
    }

    public void setError(String error) {
        this.error = error;
    }


}
