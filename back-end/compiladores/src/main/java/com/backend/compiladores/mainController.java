package com.backend.compiladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.compiladores.services.compilerController;

@RestController
@RequestMapping("")
@CrossOrigin
public class mainController {

    
    compilerController compiler = new compilerController();

    @GetMapping("")
    public String saludar(){
        return "Hola mundo 😁";
    }    

    @PostMapping("/go")
    public String post_compile_go(@RequestBody String code){
        return this.compiler.compile(code, "go");
    };

    @PostMapping("/python")
    public String post_compile_python(@RequestBody String code){
        return this.compiler.compile(code, "python");
    };
}
