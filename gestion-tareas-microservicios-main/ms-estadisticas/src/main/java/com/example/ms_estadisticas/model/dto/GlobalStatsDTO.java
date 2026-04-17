package com.example.ms_estadisticas.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalStatsDTO {
    private Integer totalUsuarios;
    private Integer totalTareas;
    private Integer totalTareasCompletadas;
    private Double promedioEficienciaGlobal;
    private Double promedioPorcentajeGlobal;
}