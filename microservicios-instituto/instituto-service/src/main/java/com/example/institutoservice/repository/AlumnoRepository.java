package com.example.institutoservice.repository;

import com.example.institutoservice.model.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}