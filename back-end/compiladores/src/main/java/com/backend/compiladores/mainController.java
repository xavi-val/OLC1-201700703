package com.backend.compiladores;

import com.backend.compiladores.services.response.traductionResponse;
import org.springframework.web.bind.annotation.*;

import com.backend.compiladores.services.compilerController;

@RestController
@RequestMapping("")
@CrossOrigin
public class mainController {


    @GetMapping("")
    public String saludar(){
        return "Hola mundo 😁";
    }    

    @PostMapping("/go")
    public traductionResponse post_compile_go(@RequestBody String code){
        return compilerController.compile(code, "go");
    };

    @PostMapping("/python")
    public traductionResponse post_compile_python(@RequestBody String code){
        return compilerController.compile(code, "python");
    };
}
