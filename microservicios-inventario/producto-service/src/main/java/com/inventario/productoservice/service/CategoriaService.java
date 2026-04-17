package com.inventario.productoservice.service;

import com.inventario.productoservice.model.entity.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> findAll();
    Categoria save(Categoria categoria);
}
