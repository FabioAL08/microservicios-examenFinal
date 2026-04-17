package com.example.institutoservice.controller;

import com.example.institutoservice.model.entity.Alumno;
import com.example.institutoservice.repository.AlumnoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instituto/alumnos")
public class AlumnoController {

    private final AlumnoRepository repository;

    public AlumnoController(AlumnoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Alumno crear(@RequestBody Alumno alumno) {
        return repository.save(alumno);
    }

    @GetMapping
    public List<Alumno> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> buscarPorId(@PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}