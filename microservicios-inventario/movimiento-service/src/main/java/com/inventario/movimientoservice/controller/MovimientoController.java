package com.inventario.movimientoservice.controller;

import com.inventario.movimientoservice.model.entity.Movimiento;
import com.inventario.movimientoservice.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/inventario/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> listar() {
        return movimientoService.findAll();
    }

    @PostMapping
    public ResponseEntity<Movimiento> registrar(@RequestBody Movimiento movimiento) {
        return new ResponseEntity<>(movimientoService.save(movimiento), HttpStatus.CREATED);
    }
}
