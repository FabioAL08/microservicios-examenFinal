package com.example.ms_estadisticas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // ✅ Habilita @LastModifiedDate
@EnableFeignClients  // ✅ Habilita Feign Client para llamar a otros MS
public class MsEstadisticasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEstadisticasApplication.class, args);
		System.out.println("=".repeat(60));
		System.out.println("MICROSERVICIO DE ESTADÍSTICAS corriendo en el puerto 8083");
		System.out.println("=".repeat(60));
	}
}