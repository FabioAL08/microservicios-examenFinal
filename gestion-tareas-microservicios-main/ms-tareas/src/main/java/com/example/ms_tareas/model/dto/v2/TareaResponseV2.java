package com.example.ms_tareas.model.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO para tareas - Versión 2 (completa)")
public class TareaResponseV2 {

    @Schema(description = "ID único de la tarea", example = "1")
    private Long id;

    @Schema(description = "Título de la tarea", example = "Implementar microservicios")
    private String titulo;

    @Schema(description = "Descripción detallada", example = "Crear los microservicios...")
    private String descripcion;

    @Schema(description = "Estado actual", example = "EN_PROGRESO")
    private String estado;

    @Schema(description = "Prioridad", example = "ALTA")
    private String prioridad;

    @Schema(description = "Nombre del usuario propietario", example = "juanperez")
    private String username;

    @Schema(description = "Fecha de creación", example = "2024-01-15T10:30:00")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Última actualización", example = "2024-01-20T14:25:00")
    private LocalDateTime fechaActualizacion;

    @Schema(description = "Fecha de vencimiento", example = "2024-12-31")
    private LocalDate fechaVencimiento;

    @Schema(description = "Porcentaje de completado", example = "75")
    private Integer porcentajeCompletado;

    @Schema(description = "Etiquetas", example = "[\"backend\", \"urgente\"]")
    private List<String> etiquetas;

    @Schema(description = "Tiempo estimado en horas", example = "8")
    private Integer tiempoEstimadoHoras;

    @Schema(description = "Tiempo real en horas", example = "6")
    private Integer tiempoRealHoras;

    @Schema(description = "¿Está archivada?", example = "false")
    private Boolean archivada;

    @Schema(description = "Comentarios adicionales")
    private List<ComentarioDTO> comentarios;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "DTO para comentarios")
    public static class ComentarioDTO {
        private Long id;
        private String texto;
        private String usuario;
        private LocalDateTime fecha;
    }
}