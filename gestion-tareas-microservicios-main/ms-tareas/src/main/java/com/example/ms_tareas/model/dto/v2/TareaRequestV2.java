package com.example.ms_tareas.model.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO para crear/actualizar tareas - Versión 2")
public class TareaRequestV2 {

    @NotBlank(message = "El título es requerido")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    @Schema(description = "Título de la tarea", example = "Implementar microservicios", required = true)
    private String titulo;

    @Schema(description = "Descripción detallada de la tarea", example = "Crear los microservicios de usuarios, tareas y estadísticas")
    private String descripcion;

    @Schema(description = "Estado de la tarea", example = "PENDIENTE", allowableValues = {"PENDIENTE", "EN_PROGRESO", "COMPLETADA", "CANCELADA"})
    private String estado = "PENDIENTE";

    @Schema(description = "Prioridad de la tarea", example = "MEDIA", allowableValues = {"BAJA", "MEDIA", "ALTA"})
    private String prioridad = "MEDIA";

    @Schema(description = "Fecha de vencimiento", example = "2024-12-31")
    private LocalDate fechaVencimiento;

    @Schema(description = "Etiquetas/categorías", example = "[\"backend\", \"spring\"]")
    private java.util.List<String> etiquetas;

    @Schema(description = "Tiempo estimado en horas", example = "8")
    private Integer tiempoEstimadoHoras;
}