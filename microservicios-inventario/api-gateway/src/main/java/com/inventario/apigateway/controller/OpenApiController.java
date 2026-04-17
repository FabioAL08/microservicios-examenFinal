package com.inventario.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OpenApiController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/api-docs-unificado")
    public Map<String, Object> getApiDocs() {

        Map<String, Object> resultado = new HashMap<>();

        Map<String, Object> paths = new HashMap<>();
        Map<String, Object> schemas = new HashMap<>();

        try {
            Map<String, Object> instituto =
                    restTemplate.getForObject("http://producto-service:8081/v3/api-docs", Map.class);

            Map<String, Object> matricula =
                    restTemplate.getForObject("http://movimiento-service:8082/v3/api-docs", Map.class);

            // unir paths
            paths.putAll((Map) instituto.get("paths"));
            paths.putAll((Map) matricula.get("paths"));

            // unir schemas
            Map comp1 = (Map) ((Map) instituto.get("components")).get("schemas");
            Map comp2 = (Map) ((Map) matricula.get("components")).get("schemas");

            schemas.putAll(comp1);
            schemas.putAll(comp2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        resultado.put("openapi", "3.0.1");

        Map<String, Object> info = new HashMap<>();
        info.put("title", "API UNIFICADA - Instituto");
        info.put("version", "2.1.0");

        resultado.put("info", info);
        resultado.put("paths", paths);

        Map<String, Object> components = new HashMap<>();
        components.put("schemas", schemas);

        resultado.put("components", components);

        return resultado;
    }
}
