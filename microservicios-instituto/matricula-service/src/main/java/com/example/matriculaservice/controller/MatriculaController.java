package com.example.matriculaservice.controller;

import com.example.matriculaservice.model.entity.Matricula;
import com.example.matriculaservice.repository.MatriculaRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

    private final MatriculaRepository repository;
    private final RestTemplate restTemplate;

    public MatriculaController(MatriculaRepository repository,
                               RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(
            @RequestBody Matricula matricula,
            @RequestHeader("Authorization") String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // 🔎 Validar alumno
            String urlAlumno = "http://instituto-service:8081/instituto/alumnos/" + matricula.getAlumnoId();

            restTemplate.exchange(
                    urlAlumno,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // 🔎 Validar curso
            String urlCurso = "http://instituto-service:8081/instituto/cursos/" + matricula.getCursoId();

            restTemplate.exchange(
                    urlCurso,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Alumno o Curso no existe en instituto-service");
        }

        Matricula guardada = repository.save(matricula);

        return ResponseEntity.ok(guardada);
    }

    @GetMapping
    public List<Matricula> listar() {
        return repository.findAll();
    }
}
