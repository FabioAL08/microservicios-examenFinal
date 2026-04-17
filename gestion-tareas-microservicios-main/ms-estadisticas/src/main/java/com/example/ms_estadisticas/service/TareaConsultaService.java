package com.example.ms_estadisticas.service;

import com.example.ms_estadisticas.model.dto.TareaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TareaConsultaService {

    private final RestTemplate restTemplate;

    @Value("${ms-tareas.url:http://localhost:8082}")
    private String msTareasUrl;

    public List<TareaDTO> obtenerTareasDeUsuario(String username, String token) {
        String url = msTareasUrl + "/api/v2/tareas?username=" + username;
        log.info("Consultando tareas para usuario: {}", username);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<TareaDTO>> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<TareaDTO>>() {});
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (Exception e) {
            log.error("Error al consultar tareas: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}