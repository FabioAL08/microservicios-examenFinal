package com.example.ms_estadisticas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "estadisticas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Estadisticas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private Integer totalTareas = 0;
    private Integer tareasCompletadas = 0;
    private Integer tareasPendientes = 0;
    private Integer tareasProgreso = 0;
    private Integer tareasCanceladas = 0;

    @Column(name = "tareas_prioridad_alta")
    private Integer tareasPrioridadAlta = 0;

    @Column(name = "tareas_prioridad_media")
    private Integer tareasPrioridadMedia = 0;

    @Column(name = "tareas_prioridad_baja")
    private Integer tareasPrioridadBaja = 0;

    @Column(name = "tareas_atrasadas")
    private Integer tareasAtrasadas = 0;

    @Column(name = "tareas_archivadas")
    private Integer tareasArchivadas = 0;

    @Column(name = "promedio_porcentaje_completado")
    private Double promedioPorcentajeCompletado = 0.0;

    @Column(name = "promedio_tiempo_estimado")
    private Double promedioTiempoEstimado = 0.0;

    @Column(name = "promedio_tiempo_real")
    private Double promedioTiempoReal = 0.0;

    @Column(name = "eficiencia_tiempo")
    private Double eficienciaTiempo = 0.0;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public Double getPorcentajeCompletadoGeneral() {
        if (totalTareas == null || totalTareas == 0) return 0.0;
        return (tareasCompletadas * 100.0) / totalTareas;
    }

    public Double getRendimientoGeneral() {
        if (totalTareas == null || totalTareas == 0) return 0.0;
        return (double) tareasCompletadas / totalTareas;
    }
}