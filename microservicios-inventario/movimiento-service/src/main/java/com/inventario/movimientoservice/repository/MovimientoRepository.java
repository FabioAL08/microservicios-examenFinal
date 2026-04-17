package com.inventario.movimientoservice.repository;

import com.inventario.movimientoservice.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
