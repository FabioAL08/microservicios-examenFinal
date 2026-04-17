package com.example.ms_estadisticas.model.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TareaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String estado;
    private String username;
    private String prioridad;
    private LocalDate fechaVencimiento;
    private Integer porcentajeCompletado;
    private Integer tiempoEstimadoHoras;
    private Integer tiempoRealHoras;
    private boolean archivada;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}