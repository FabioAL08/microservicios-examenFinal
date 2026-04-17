package com.example.gateway_service.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OpenApiController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "/v3/api-docs", produces = "application/json")
    public JsonNode getAggregatedOpenApi() {
        try {
            // Obtener definiciones de cada microservicio
            JsonNode usuariosDoc = null;
            JsonNode tareasDoc = null;
            JsonNode estadisticasDoc = null;

            try {
                usuariosDoc = restTemplate.getForObject("http://localhost:8081/v3/api-docs", JsonNode.class);
                System.out.println("✅ Usuarios doc obtenido");
            } catch (Exception e) {
                System.out.println("❌ Error obteniendo usuarios: " + e.getMessage());
                usuariosDoc = mapper.createObjectNode();
            }

            try {
                tareasDoc = restTemplate.getForObject("http://localhost:8082/v3/api-docs", JsonNode.class);
                System.out.println("✅ Tareas doc obtenido");
            } catch (Exception e) {
                System.out.println("❌ Error obteniendo tareas: " + e.getMessage());
                tareasDoc = mapper.createObjectNode();
            }

            try {
                estadisticasDoc = restTemplate.getForObject("http://localhost:8083/v3/api-docs", JsonNode.class);
                System.out.println("✅ Estadisticas doc obtenido");
            } catch (Exception e) {
                System.out.println("❌ Error obteniendo estadisticas: " + e.getMessage());
                estadisticasDoc = mapper.createObjectNode();
            }

            // Crear documento combinado
            ObjectNode combined = mapper.createObjectNode();
            combined.put("openapi", "3.0.1");

            // Info
            ObjectNode info = mapper.createObjectNode();
            info.put("title", "API Gateway - Sistema de Gestión de Tareas");
            info.put("version", "1.0.0");
            info.put("description", "API Gateway unificado para los microservicios del sistema");
            combined.set("info", info);

            // Servers
            ArrayNode servers = mapper.createArrayNode();
            ObjectNode server = mapper.createObjectNode();
            server.put("url", "http://localhost:8080");
            server.put("description", "API Gateway");
            servers.add(server);
            combined.set("servers", servers);

            // COMBINAR PATHS
            ObjectNode allPaths = mapper.createObjectNode();

            if (usuariosDoc != null && usuariosDoc.has("paths")) {
                ObjectNode paths = (ObjectNode) usuariosDoc.get("paths");
                paths.fieldNames().forEachRemaining(path -> {
                    allPaths.set("/api" + path, paths.get(path));
                });
            }

            if (tareasDoc != null && tareasDoc.has("paths")) {
                ObjectNode paths = (ObjectNode) tareasDoc.get("paths");
                paths.fieldNames().forEachRemaining(path -> {
                    allPaths.set("/api" + path, paths.get(path));
                });
            }

            if (estadisticasDoc != null && estadisticasDoc.has("paths")) {
                ObjectNode paths = (ObjectNode) estadisticasDoc.get("paths");
                paths.fieldNames().forEachRemaining(path -> {
                    allPaths.set("/api" + path, paths.get(path));
                });
            }
            combined.set("paths", allPaths);

            // COMBINAR COMPONENTS
            ObjectNode allComponents = mapper.createObjectNode();
            ObjectNode allSchemas = mapper.createObjectNode();

            if (usuariosDoc != null && usuariosDoc.has("components") && usuariosDoc.get("components").has("schemas")) {
                allSchemas.setAll((ObjectNode) usuariosDoc.get("components").get("schemas"));
            }
            if (tareasDoc != null && tareasDoc.has("components") && tareasDoc.get("components").has("schemas")) {
                allSchemas.setAll((ObjectNode) tareasDoc.get("components").get("schemas"));
            }
            if (estadisticasDoc != null && estadisticasDoc.has("components") && estadisticasDoc.get("components").has("schemas")) {
                allSchemas.setAll((ObjectNode) estadisticasDoc.get("components").get("schemas"));
            }

            // Security schemes
            ObjectNode securitySchemes = mapper.createObjectNode();
            ObjectNode bearerAuth = mapper.createObjectNode();
            bearerAuth.put("type", "http");
            bearerAuth.put("scheme", "bearer");
            bearerAuth.put("bearerFormat", "JWT");
            securitySchemes.set("BearerAuth", bearerAuth);
            allComponents.set("securitySchemes", securitySchemes);
            allComponents.set("schemas", allSchemas);
            combined.set("components", allComponents);

            // Security requirements
            ArrayNode security = mapper.createArrayNode();
            ObjectNode securityReq = mapper.createObjectNode();
            securityReq.set("BearerAuth", mapper.createArrayNode());
            security.add(securityReq);
            combined.set("security", security);

            return combined;

        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode error = mapper.createObjectNode();
            error.put("error", "Error al combinar los contratos API");
            error.put("message", e.getMessage());
            return error;
        }
    }

    // ENDPOINT PARA YAML
    @GetMapping(value = "/v3/api-docs.yaml", produces = "application/yaml")
    public ResponseEntity<String> getAggregatedOpenApiYaml() {
        try {
            // Obtener el JSON primero (ahora es JsonNode, no ResponseEntity)
            JsonNode jsonDoc = getAggregatedOpenApi();

            // Convertir JSON a YAML usando Jackson
            ObjectMapper yamlMapper = new ObjectMapper(new com.fasterxml.jackson.dataformat.yaml.YAMLFactory());
            String yamlContent = yamlMapper.writeValueAsString(jsonDoc);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/yaml"))
                    .body(yamlContent);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}