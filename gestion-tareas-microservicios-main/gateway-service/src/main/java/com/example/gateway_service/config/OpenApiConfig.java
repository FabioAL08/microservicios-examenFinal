package com.example.gateway_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.Components;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gateway - Sistema de Gestión de Tareas")
                        .version("1.0.0")
                        .description("API Gateway unificado para los microservicios del sistema")
                        .contact(new Contact()
                                .name("Soporte")
                                .email("soporte@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .name("Bearer Authentication")
                                        .type(Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(In.HEADER)))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
    }

    @Bean
    public GroupedOpenApi usuariosApi() {
        String[] paths = {"/api/auth/**", "/api/usuarios/**"};
        return GroupedOpenApi.builder()
                .group("ms-usuarios")
                .displayName("Usuarios - Autenticación y Perfiles")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi tareasApi() {
        return GroupedOpenApi.builder()
                .group("ms-tareas")
                .displayName("📝 Tareas - CRUD y Gestión")
                .pathsToMatch("/api/tareas/**")
                .build();
    }

    @Bean
    public GroupedOpenApi estadisticasApi() {
        return GroupedOpenApi.builder()
                .group("ms-estadisticas")
                .displayName("Estadísticas - Métricas y Reportes")
                .pathsToMatch("/api/estadisticas/**")
                .build();
    }
}