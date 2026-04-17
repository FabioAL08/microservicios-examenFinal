package com.example.ms_estadisticas.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Microservicio de Estadísticas")
                        .version("2.0.0")
                        .description("""
                                ## Microservicio de Estadísticas para Sistema de Gestión de Tareas
                                
                                Este microservicio maneja:
                                * **Estadísticas de tareas** por usuario
                                * **Métricas** de productividad
                                * **Consultas** a otros microservicios
                                
                                ### Características principales:
                                - Obtener estadísticas del usuario autenticado
                                - Actualizar estadísticas manualmente
                                - Consultar tareas desde ms-tareas
                                - Seguridad con JWT (mismo token que ms-usuarios)
                                
                                ### Autenticación:
                                1. Obtén un token JWT del microservicio `ms-usuarios` en `/api/auth/login`
                                2. Copia el token recibido
                                3. Haz clic en el botón **Authorize** y pega: `Bearer {tu-token}`
                                
                                ### Dependencias:
                                - Este microservicio se comunica con `ms-tareas` (puerto 8082)
                                """)
                        .contact(new Contact()
                                .name("Soporte Técnico")
                                .email("soporte@example.com")
                                .url("https://github.com/tu-repo"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createSecurityScheme()))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name("Bearer Authentication")
                .type(Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(In.HEADER)
                .description("""
                        Ingresa tu token JWT en el siguiente formato:
                        
                        **Bearer {tu-token-jwt}**
                        
                        Ejemplo: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`
                        
                        ### Cómo obtener un token:
                        1. Ve al microservicio `ms-usuarios` en `POST /api/auth/login`
                        2. Usa credenciales válidas
                        3. Copia el token de la respuesta
                        """);
    }
}