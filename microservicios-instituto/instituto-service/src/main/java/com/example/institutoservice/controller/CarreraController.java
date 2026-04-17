package com.example.institutoservice.controller;

import com.example.institutoservice.model.entity.Carrera;
import com.example.institutoservice.repository.CarreraRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instituto/carreras")
public class CarreraController {

    private final CarreraRepository repository;

    public CarreraController(CarreraRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Carrera crear(@RequestBody Carrera carrera) {
        return repository.save(carrera);
    }

    @GetMapping
    public List<Carrera> listar() {
        return repository.findAll();
    }
}
