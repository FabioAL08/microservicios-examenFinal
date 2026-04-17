package com.example.ms_tareas.model.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear o actualizar una tarea")
public class TareaRequest {

    @Schema(description = "Título de la tarea", example = "Completar informe", required = true)
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String titulo;

    @Schema(description = "Descripción de la tarea", example = "Completar el informe mensual")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @Schema(description = "Estado de la tarea", example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "EN_PROGRESO", "COMPLETADA", "CANCELADA"})
    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}