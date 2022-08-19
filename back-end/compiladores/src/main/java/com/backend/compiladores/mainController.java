package com.backend.compiladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class mainController {

    @GetMapping("/")
    public String saludar(){
        return "Hola mundo 😁";
    }

}
