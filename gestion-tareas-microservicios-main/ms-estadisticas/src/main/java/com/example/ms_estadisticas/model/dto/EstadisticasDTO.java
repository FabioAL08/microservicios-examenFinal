package com.example.ms_estadisticas.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Estadísticas básicas del usuario")
public class EstadisticasDTO {

    @Schema(description = "Nombre de usuario", example = "juanperez")
    private String username;

    @Schema(description = "Total de tareas", example = "25")
    private Integer totalTareas;

    @Schema(description = "Tareas completadas", example = "15")
    private Integer tareasCompletadas;

    @Schema(description = "Tareas pendientes", example = "5")
    private Integer tareasPendientes;

    @Schema(description = "Tareas en progreso", example = "3")
    private Integer tareasProgreso;

    @Schema(description = "Tareas canceladas", example = "2")
    private Integer tareasCanceladas;

    @Schema(description = "Porcentaje de completado", example = "60.0")
    private Double porcentajeCompletado;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime fechaActualizacion;
}