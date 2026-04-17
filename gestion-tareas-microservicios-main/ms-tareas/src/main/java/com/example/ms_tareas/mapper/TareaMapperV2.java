package com.example.ms_tareas.mapper;

import com.example.ms_tareas.model.Tarea;
import com.example.ms_tareas.model.dto.v2.TareaRequestV2;
import com.example.ms_tareas.model.dto.v2.TareaResponseV2;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TareaMapperV2 {

    public Tarea toEntity(TareaRequestV2 request) {
        if (request == null) return null;

        Tarea tarea = new Tarea();
        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setEstado(request.getEstado());
        tarea.setPrioridad(request.getPrioridad());
        tarea.setFechaVencimiento(request.getFechaVencimiento());
        tarea.setTiempoEstimadoHoras(request.getTiempoEstimadoHoras());

        // Convertir lista de etiquetas a String
        if (request.getEtiquetas() != null && !request.getEtiquetas().isEmpty()) {
            tarea.setEtiquetas(String.join(",", request.getEtiquetas()));
        }

        return tarea;
    }

    public TareaResponseV2 toResponse(Tarea tarea) {
        if (tarea == null) return null;

        // Convertir etiquetas de String a List
        List<String> etiquetas = Collections.emptyList();
        if (tarea.getEtiquetas() != null && !tarea.getEtiquetas().isEmpty()) {
            etiquetas = Arrays.asList(tarea.getEtiquetas().split(","));
        }

        return TareaResponseV2.builder()
                .id(tarea.getId())
                .titulo(tarea.getTitulo())
                .descripcion(tarea.getDescripcion())
                .estado(tarea.getEstado())
                .prioridad(tarea.getPrioridad())
                .username(tarea.getUsername())
                .fechaCreacion(tarea.getFechaCreacion())
                .fechaActualizacion(tarea.getFechaActualizacion())
                .fechaVencimiento(tarea.getFechaVencimiento())
                .porcentajeCompletado(tarea.getPorcentajeCompletado())
                .tiempoEstimadoHoras(tarea.getTiempoEstimadoHoras())
                .tiempoRealHoras(tarea.getTiempoRealHoras())
                .archivada(tarea.isArchivada())
                .etiquetas(etiquetas)
                .comentarios(Collections.emptyList())
                .build();
    }

    public void updateEntity(TareaRequestV2 request, Tarea tarea) {
        if (request.getTitulo() != null) tarea.setTitulo(request.getTitulo());
        if (request.getDescripcion() != null) tarea.setDescripcion(request.getDescripcion());
        if (request.getEstado() != null) tarea.setEstado(request.getEstado());
        if (request.getPrioridad() != null) tarea.setPrioridad(request.getPrioridad());
        if (request.getFechaVencimiento() != null) tarea.setFechaVencimiento(request.getFechaVencimiento());
        if (request.getTiempoEstimadoHoras() != null) tarea.setTiempoEstimadoHoras(request.getTiempoEstimadoHoras());
        if (request.getEtiquetas() != null && !request.getEtiquetas().isEmpty()) {
            tarea.setEtiquetas(String.join(",", request.getEtiquetas()));
        }
    }
}