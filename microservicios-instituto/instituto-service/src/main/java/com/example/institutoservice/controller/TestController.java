package com.example.institutoservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/instituto/test")
    public String prueba() {
        return "Acceso autorizado al instituto-service";
    }
}
