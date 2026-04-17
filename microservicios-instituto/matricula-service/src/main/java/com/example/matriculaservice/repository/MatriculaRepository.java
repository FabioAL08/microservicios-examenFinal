package com.example.matriculaservice.repository;

import com.example.matriculaservice.model.entity.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
}