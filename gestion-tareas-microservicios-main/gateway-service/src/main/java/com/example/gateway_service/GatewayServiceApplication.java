package com.example.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);

        System.out.println("=".repeat(60));
        System.out.println("MICROSERVICIO DE ESTADÍSTICAS corriendo en el puerto 8080");
        System.out.println("=".repeat(60));
    }
}