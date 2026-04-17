package com.example.ms_tareas.model.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Estadísticas de tareas del usuario")
public class TaskStatsDTO {
    @Schema(description = "Total de tareas", example = "25")
    private Long totalTareas;

    @Schema(description = "Tareas completadas", example = "15")
    private Long tareasCompletadas;

    @Schema(description = "Tareas pendientes", example = "5")
    private Long tareasPendientes;

    @Schema(description = "Tareas en progreso", example = "3")
    private Long tareasEnProgreso;

    @Schema(description = "Tareas canceladas", example = "2")
    private Long tareasCanceladas;

    @Schema(description = "Porcentaje de completado", example = "60")
    private Long porcentajeCompletado;
}