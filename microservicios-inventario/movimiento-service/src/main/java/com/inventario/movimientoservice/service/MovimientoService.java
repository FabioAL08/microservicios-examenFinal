package com.inventario.movimientoservice.service;

import com.inventario.movimientoservice.model.entity.Movimiento;

import java.util.List;

public interface MovimientoService {
    List<Movimiento> findAll();
    Movimiento save(Movimiento movimiento);
}
