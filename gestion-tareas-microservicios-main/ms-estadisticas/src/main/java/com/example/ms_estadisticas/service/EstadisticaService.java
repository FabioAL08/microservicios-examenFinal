package com.example.ms_estadisticas.service;

import com.example.ms_estadisticas.model.Estadisticas;
import com.example.ms_estadisticas.model.dto.EstadisticasDTO;
import com.example.ms_estadisticas.model.dto.EstadisticasResponseDTO;
import com.example.ms_estadisticas.model.dto.GlobalStatsDTO;
import com.example.ms_estadisticas.repository.EstadisticasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EstadisticaService {

    private final EstadisticasRepository estadisticasRepository;

    public EstadisticasResponseDTO obtenerEstadisticasCompletas(String username) {
        Estadisticas stats = estadisticasRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No hay estadísticas para: " + username));
        return convertirAResponseDTO(stats);
    }

    public EstadisticasDTO obtenerEstadisticasBasicas(String username) {
        Estadisticas stats = estadisticasRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No hay estadísticas para: " + username));
        return convertirADTO(stats);
    }

    public boolean existenEstadisticas(String username) {
        return estadisticasRepository.existsByUsername(username);
    }

    public List<EstadisticasResponseDTO> getTopUsuarios(int limit) {
        return estadisticasRepository.findTopUsuarios()
                .stream()
                .limit(limit)
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public GlobalStatsDTO getGlobalStats() {
        List<Estadisticas> allStats = estadisticasRepository.findAll();

        int totalUsuarios = allStats.size();
        int totalTareas = allStats.stream().mapToInt(Estadisticas::getTotalTareas).sum();
        int totalCompletadas = allStats.stream().mapToInt(Estadisticas::getTareasCompletadas).sum();

        double promedioEficiencia = allStats.stream()
                .mapToDouble(Estadisticas::getEficienciaTiempo)
                .average()
                .orElse(0.0);

        double promedioPorcentaje = allStats.stream()
                .mapToDouble(Estadisticas::getPorcentajeCompletadoGeneral)
                .average()
                .orElse(0.0);

        return GlobalStatsDTO.builder()
                .totalUsuarios(totalUsuarios)
                .totalTareas(totalTareas)
                .totalTareasCompletadas(totalCompletadas)
                .promedioEficienciaGlobal(promedioEficiencia)
                .promedioPorcentajeGlobal(promedioPorcentaje)
                .build();
    }

    private EstadisticasResponseDTO convertirAResponseDTO(Estadisticas stats) {
        return EstadisticasResponseDTO.builder()
                .username(stats.getUsername())
                .totalTareas(stats.getTotalTareas())
                .tareasCompletadas(stats.getTareasCompletadas())
                .tareasPendientes(stats.getTareasPendientes())
                .tareasProgreso(stats.getTareasProgreso())
                .tareasCanceladas(stats.getTareasCanceladas())
                .tareasPrioridadAlta(stats.getTareasPrioridadAlta())
                .tareasPrioridadMedia(stats.getTareasPrioridadMedia())
                .tareasPrioridadBaja(stats.getTareasPrioridadBaja())
                .tareasAtrasadas(stats.getTareasAtrasadas())
                .tareasArchivadas(stats.getTareasArchivadas())
                .promedioPorcentajeCompletado(stats.getPromedioPorcentajeCompletado())
                .promedioTiempoEstimado(stats.getPromedioTiempoEstimado())
                .promedioTiempoReal(stats.getPromedioTiempoReal())
                .eficienciaTiempo(stats.getEficienciaTiempo())
                .porcentajeCompletadoGeneral(stats.getPorcentajeCompletadoGeneral())
                .rendimientoGeneral(stats.getRendimientoGeneral())
                .fechaActualizacion(stats.getFechaActualizacion())
                .build();
    }

    private EstadisticasDTO convertirADTO(Estadisticas stats) {
        return EstadisticasDTO.builder()
                .username(stats.getUsername())
                .totalTareas(stats.getTotalTareas())
                .tareasCompletadas(stats.getTareasCompletadas())
                .tareasPendientes(stats.getTareasPendientes())
                .tareasProgreso(stats.getTareasProgreso())
                .tareasCanceladas(stats.getTareasCanceladas())
                .porcentajeCompletado(stats.getPorcentajeCompletadoGeneral())
                .fechaActualizacion(stats.getFechaActualizacion())
                .build();
    }
}