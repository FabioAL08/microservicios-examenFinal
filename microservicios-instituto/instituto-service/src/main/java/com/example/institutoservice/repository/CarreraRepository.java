package com.example.institutoservice.repository;

import com.example.institutoservice.model.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
}