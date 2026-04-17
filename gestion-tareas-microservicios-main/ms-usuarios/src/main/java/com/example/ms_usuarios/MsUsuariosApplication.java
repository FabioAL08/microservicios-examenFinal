package com.example.ms_usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUsuariosApplication.class, args);
		System.out.println("=".repeat(60));
		System.out.println("MICROSERVICIO DE USUARIOS corriendo en el puerto 8081");
		System.out.println("=".repeat(60));
	}
}