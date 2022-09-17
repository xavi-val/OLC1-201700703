package com.backend.compiladores;

import com.backend.compiladores.services.response.traductionResponse;
import org.springframework.web.bind.annotation.*;

import com.backend.compiladores.services.compilerController;

@RestController
@RequestMapping("")
@CrossOrigin
public class mainController {


    @RequestMapping(value = "",produces = "application/json")
    public String saludar(){
        return "Hola mundo 😁 estoy en azure";
    }

    @RequestMapping(value = "/go",produces = "application/json")
    public traductionResponse post_compile_go(@RequestBody String code){
        return compilerController.compile(code, "go");
    };

    @RequestMapping(value = "/python",produces = "application/json")
    public traductionResponse post_compile_python(@RequestBody String code){
        return compilerController.compile(code, "python");
    };
}
