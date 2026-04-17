package com.example.ms_tareas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
@Data
@NoArgsConstructor  // Constructor sin argumentos
@AllArgsConstructor  // Constructor con todos los argumentos
@EntityListeners(AuditingEntityListener.class) // Para fechas automáticas
@Schema(description = "Entidad que representa una tarea en el sistema")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la tarea", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Título de la tarea", example = "Completar informe", required = true)
    private String titulo;

    @Column(length = 500)
    @Schema(description = "Descripción detallada de la tarea", example = "Completar el informe mensual para el equipo")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Estado actual de la tarea", example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "EN_PROGRESO", "COMPLETADA", "CANCELADA"})
    private String estado;

    @Column(nullable = false)
    @Schema(description = "Nombre del usuario propietario de la tarea", example = "juan.perez", required = true)
    private String username;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    @Schema(description = "Fecha de creación de la tarea", example = "2024-01-15T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    @Schema(description = "Fecha de última actualización", example = "2024-01-15T14:45:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaActualizacion;

    // Método para establecer fechas automáticas antes de persistir
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (estado == null) {
            estado = "PENDIENTE";
        }
    }

    @Column(name = "user_id", nullable = false)
    @Schema(description = "ID del usuario propietario", example = "1", required = true)
    private Long userId;

    @Column(name = "prioridad")
    private String prioridad = "MEDIA";

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "porcentaje_completado")
    private Integer porcentajeCompletado = 0;

    @Column(name = "etiquetas")
    private String etiquetas;

    @Column(name = "tiempo_estimado_horas")
    private Integer tiempoEstimadoHoras;

    @Column(name = "tiempo_real_horas")
    private Integer tiempoRealHoras;

    @Column(name = "archivada")
    private boolean archivada = false;

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}