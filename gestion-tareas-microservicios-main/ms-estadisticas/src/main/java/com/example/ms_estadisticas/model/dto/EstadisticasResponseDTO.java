package com.example.ms_estadisticas.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Estadísticas completas del usuario")
public class EstadisticasResponseDTO {

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

    @Schema(description = "Tareas con prioridad alta", example = "8")
    private Integer tareasPrioridadAlta;

    @Schema(description = "Tareas con prioridad media", example = "12")
    private Integer tareasPrioridadMedia;

    @Schema(description = "Tareas con prioridad baja", example = "5")
    private Integer tareasPrioridadBaja;

    @Schema(description = "Tareas atrasadas", example = "3")
    private Integer tareasAtrasadas;

    @Schema(description = "Tareas archivadas", example = "2")
    private Integer tareasArchivadas;

    @Schema(description = "Promedio de porcentaje completado", example = "65.5")
    private Double promedioPorcentajeCompletado;

    @Schema(description = "Promedio de tiempo estimado (horas)", example = "8.5")
    private Double promedioTiempoEstimado;

    @Schema(description = "Promedio de tiempo real (horas)", example = "7.2")
    private Double promedioTiempoReal;

    @Schema(description = "Eficiencia de tiempo", example = "0.85")
    private Double eficienciaTiempo;

    @Schema(description = "Porcentaje de completado general", example = "60.0")
    private Double porcentajeCompletadoGeneral;

    @Schema(description = "Rendimiento general", example = "0.75")
    private Double rendimientoGeneral;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime fechaActualizacion;
}