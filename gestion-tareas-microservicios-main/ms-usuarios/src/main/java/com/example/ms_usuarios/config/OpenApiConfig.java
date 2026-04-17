package com.example.ms_usuarios.config;

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
                        .title("API de Microservicio de Usuarios")
                        .version("1.0.0")
                        .description("""
                                ## Microservicio de Usuarios para Sistema de Gestión de Tareas
                                
                                Este microservicio maneja:
                                * **Autenticación y autorización** mediante JWT
                                * **Gestión de perfiles** de usuarios
                                * **Roles** (USER, ADMIN)
                                
                                ### Características principales:
                                - Registro y login de usuarios
                                - Perfil personal
                                - Listado de usuarios (solo ADMIN)
                                - Seguridad con JWT
                                
                                ### Autenticación:
                                1. Usa el endpoint `/api/auth/login` para obtener un token JWT
                                2. Copia el token recibido
                                3. Haz clic en el botón **Authorize** y pega: `Bearer {tu-token}`
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
                        1. Ve a `POST /api/auth/login`
                        2. Usa credenciales válidas
                        3. Copia el token de la respuesta
                        """);
    }
}