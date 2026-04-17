package com.inventario.productoservice.service;

import com.inventario.productoservice.model.entity.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    void delete(Long id);
}
