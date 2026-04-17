package com.example.ms_tareas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // Habilita @CreatedDate y @LastModifiedDate
public class MsTareasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsTareasApplication.class, args);
		System.out.println("=".repeat(60));
		System.out.println("MICROSERVICIO DE TAREAS corriendo en el puerto 8082");
		System.out.println("=".repeat(60));
	}
}