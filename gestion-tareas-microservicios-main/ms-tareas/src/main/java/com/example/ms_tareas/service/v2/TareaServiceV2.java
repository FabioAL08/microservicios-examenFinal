package com.example.ms_tareas.service.v2;

import com.example.ms_tareas.mapper.TareaMapperV2;
import com.example.ms_tareas.model.dto.v2.TareaRequestV2;
import com.example.ms_tareas.model.dto.v2.TareaResponseV2;
import com.example.ms_tareas.model.dto.v2.TaskStatsDTO;
import com.example.ms_tareas.model.Tarea;
import com.example.ms_tareas.repository.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class TareaServiceV2 {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private TareaMapperV2 tareaMapperV2;

    // ========== CRUD BÁSICO ==========

    public TareaResponseV2 createTask(TareaRequestV2 request, Long userId, String username) {
        log.info("Creando tarea para usuario: {}", username);

        Tarea tarea = tareaMapperV2.toEntity(request);
        tarea.setUserId(userId);
        tarea.setUsername(username);
        tarea.setFechaCreacion(LocalDateTime.now());
        tarea.setFechaActualizacion(LocalDateTime.now());
        tarea.setPorcentajeCompletado(0);
        tarea.setArchivada(false);

        Tarea savedTask = tareaRepository.save(tarea);
        log.info("Tarea creada con ID: {} para usuario: {}", savedTask.getId(), username);
        return tareaMapperV2.toResponse(savedTask);
    }

    public TareaResponseV2 getTaskById(Long taskId, Long userId) {
        log.debug("Obteniendo tarea ID: {} para userId: {}", taskId, userId);
        Tarea tarea = findTaskByIdAndUserId(taskId, userId);
        return tareaMapperV2.toResponse(tarea);
    }

    public TareaResponseV2 updateTask(Long taskId, TareaRequestV2 request, Long userId) {
        log.info("Actualizando tarea ID: {} para userId: {}", taskId, userId);

        Tarea tarea = findTaskByIdAndUserId(taskId, userId);
        tareaMapperV2.updateEntity(request, tarea);
        tarea.setFechaActualizacion(LocalDateTime.now());

        // Calcular porcentaje basado en estado
        if (tarea.getEstado() != null) {
            switch (tarea.getEstado()) {
                case "COMPLETADA":
                    tarea.setPorcentajeCompletado(100);
                    break;
                case "EN_PROGRESO":
                    if (tarea.getPorcentajeCompletado() == null || tarea.getPorcentajeCompletado() < 25) {
                        tarea.setPorcentajeCompletado(25);
                    }
                    break;
                case "CANCELADA":
                    tarea.setPorcentajeCompletado(0);
                    break;
                default:
                    break;
            }
        }

        Tarea updatedTask = tareaRepository.save(tarea);
        log.info("Tarea actualizada con ID: {}", updatedTask.getId());
        return tareaMapperV2.toResponse(updatedTask);
    }

    public void deleteTask(Long taskId, Long userId) {
        log.info("Eliminando (soft delete) tarea ID: {} para userId: {}", taskId, userId);

        Tarea tarea = findTaskByIdAndUserId(taskId, userId);
        tarea.setArchivada(true);
        tarea.setFechaActualizacion(LocalDateTime.now());
        tareaRepository.save(tarea);

        log.info("Tarea archivada con ID: {}", taskId);
    }

    public void hardDeleteTask(Long taskId, Long userId) {
        log.info("Eliminando físicamente tarea ID: {} para userId: {}", taskId, userId);

        Tarea tarea = findTaskByIdAndUserId(taskId, userId);
        tareaRepository.delete(tarea);

        log.info("Tarea eliminada físicamente con ID: {}", taskId);
    }

    // ========== LISTAR CON PAGINACIÓN ==========

    public Page<TareaResponseV2> getTasksByUser(Long userId, Pageable pageable) {
        String username = getUsernameByUserId(userId);
        log.debug("Listando tareas paginadas para usuario: {}", username);

        Page<Tarea> tareas = tareaRepository.findByUsernameAndArchivadaFalse(username, pageable);
        return tareas.map(tareaMapperV2::toResponse);
    }

    // ========== FILTRADO AVANZADO ==========

    public Page<TareaResponseV2> filterTasks(Long userId, String estado, String prioridad,
                                             LocalDate fechaDesde, LocalDate fechaHasta,
                                             String etiqueta, Pageable pageable) {
        String username = getUsernameByUserId(userId);
        log.debug("Filtrando tareas para usuario: {} - estado: {}, prioridad: {}", username, estado, prioridad);

        Page<Tarea> tareas = tareaRepository.findByFilters(username, estado, prioridad,
                fechaDesde, fechaHasta, pageable);
        return tareas.map(tareaMapperV2::toResponse);
    }

    // ========== BÚSQUEDA ==========

    public List<TareaResponseV2> searchTasks(Long userId, String keyword) {
        String username = getUsernameByUserId(userId);
        log.debug("Buscando tareas para usuario: {} con keyword: {}", username, keyword);

        List<Tarea> tareas = tareaRepository.searchByKeyword(username, keyword);
        return tareas.stream()
                .map(tareaMapperV2::toResponse)
                .collect(Collectors.toList());
    }

    // ========== ACTUALIZACIONES PARCIALES ==========

    public TareaResponseV2 updateTaskStatus(Long taskId, String estado, Long userId) {
        log.info("Actualizando estado de tarea ID: {} a: {} para userId: {}", taskId, estado, userId);

        Tarea tarea = findTaskByIdAndUserId(taskId, userId);
        tarea.setEstado(estado);
        tarea.setFechaActualizacion(LocalDateTime.now());

        if ("COMPLETADA".equals(estado)) {
            tarea.setPorcentajeCompletado(100);
            if (tarea.getTiempoRealHoras() == null && tarea.getTiempoEstimadoHoras() != null) {
                tarea.setTiempoRealHoras(tarea.getTiempoEstimadoHoras());
            }
        } else if ("EN_PROGRESO".equals(estado) && tarea.getPorcentajeCompletado() < 25) {
            tarea.setPorcentajeCompletado(25);
        } else if ("CANCELADA".equals(estado)) {
            tarea.setPorcentajeCompletado(0);
        }

        Tarea updatedTask = tareaRepository.save(tarea);
        return tareaMapperV2.toResponse(updatedTask);
    }

    public TareaResponseV2 updateProgress(Long taskId, Integer porcentaje, Long userId) {
        log.info("Actualizando progreso de tarea ID: {} a: {}% para userId: {}", taskId, porcentaje, userId);

        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }

        Tarea tarea = findTaskByIdAndUserId(taskId, userId);
        tarea.setPorcentajeCompletado(porcentaje);
        tarea.setFechaActualizacion(LocalDateTime.now());

        if (porcentaje == 100 && !"COMPLETADA".equals(tarea.getEstado())) {
            tarea.setEstado("COMPLETADA");
        } else if (porcentaje > 0 && porcentaje < 100 && "PENDIENTE".equals(tarea.getEstado())) {
            tarea.setEstado("EN_PROGRESO");
        }

        Tarea updatedTask = tareaRepository.save(tarea);
        return tareaMapperV2.toResponse(updatedTask);
    }

    public TareaResponseV2 updateRealTime(Long taskId, Integer horas, Long userId) {
        log.info("Actualizando tiempo real de tarea ID: {} a: {} horas para userId: {}", taskId, horas, userId);

        Tarea tarea = findTaskByIdAndUserId(taskId, userId);
        tarea.setTiempoRealHoras(horas);
        tarea.setFechaActualizacion(LocalDateTime.now());

        Tarea updatedTask = tareaRepository.save(tarea);
        return tareaMapperV2.toResponse(updatedTask);
    }

    // ========== ESTADÍSTICAS ==========

    /**
     * Obtiene estadísticas usando username directamente (RECOMENDADO)
     */
    public TaskStatsDTO getTaskStatsByUsername(String username) {
        log.info("Calculando estadísticas para usuario: {}", username);

        List<Tarea> tareas = tareaRepository.findByUsernameAndArchivadaFalse(username);

        log.info("Tareas encontradas: {}", tareas.size());

        long total = tareas.size();
        long completadas = tareas.stream().filter(t -> "COMPLETADA".equals(t.getEstado())).count();
        long pendientes = tareas.stream().filter(t -> "PENDIENTE".equals(t.getEstado())).count();
        long enProgreso = tareas.stream().filter(t -> "EN_PROGRESO".equals(t.getEstado())).count();
        long canceladas = tareas.stream().filter(t -> "CANCELADA".equals(t.getEstado())).count();

        double porcentajeCompletado = total > 0 ? (completadas * 100.0 / total) : 0;

        log.info("Estadísticas - Total: {}, Completadas: {}, Pendientes: {}, EnProgreso: {}, Canceladas: {}",
                total, completadas, pendientes, enProgreso, canceladas);

        return TaskStatsDTO.builder()
                .totalTareas(total)
                .tareasCompletadas(completadas)
                .tareasPendientes(pendientes)
                .tareasEnProgreso(enProgreso)
                .tareasCanceladas(canceladas)
                .porcentajeCompletado(Math.round(porcentajeCompletado))
                .build();
    }

    /**
     * Método para compatibilidad (usa userId)
     */
    public TaskStatsDTO getTaskStats(Long userId) {
        String username = getUsernameByUserId(userId);
        return getTaskStatsByUsername(username);
    }

    public Map<String, Long> getTasksGroupByStatus(Long userId) {
        String username = getUsernameByUserId(userId);
        log.debug("Agrupando tareas por estado para usuario: {}", username);

        List<Tarea> tareas = tareaRepository.findByUsernameAndArchivadaFalse(username);
        return tareas.stream()
                .collect(Collectors.groupingBy(Tarea::getEstado, Collectors.counting()));
    }

    // ========== EXPORTAR DATOS ==========

    public String exportToCsv(Long userId) {
        String username = getUsernameByUserId(userId);
        log.info("Exportando tareas a CSV para usuario: {}", username);

        List<Tarea> tareas = tareaRepository.findByUsernameAndArchivadaFalse(username);

        StringBuilder csv = new StringBuilder();
        csv.append("ID,Título,Descripción,Estado,Prioridad,Fecha Creación,Fecha Actualización,Fecha Vencimiento,Porcentaje Completado,Tiempo Estimado,Tiempo Real,Archivada\n");

        for (Tarea tarea : tareas) {
            csv.append(tarea.getId()).append(",")
                    .append(escapeCsv(tarea.getTitulo())).append(",")
                    .append(escapeCsv(tarea.getDescripcion())).append(",")
                    .append(tarea.getEstado()).append(",")
                    .append(tarea.getPrioridad()).append(",")
                    .append(tarea.getFechaCreacion()).append(",")
                    .append(tarea.getFechaActualizacion()).append(",")
                    .append(tarea.getFechaVencimiento()).append(",")
                    .append(tarea.getPorcentajeCompletado()).append(",")
                    .append(tarea.getTiempoEstimadoHoras()).append(",")
                    .append(tarea.getTiempoRealHoras()).append(",")
                    .append(tarea.isArchivada()).append("\n");
        }

        log.info("Exportadas {} tareas para usuario: {}", tareas.size(), username);
        return csv.toString();
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // ========== OPERACIONES EN MASA ==========

    public void bulkUpdateStatus(List<Long> taskIds, String estado, Long userId) {
        log.info("Actualizando estado de {} tareas a: {} para userId: {}", taskIds.size(), estado, userId);

        List<Tarea> tareas = tareaRepository.findAllById(taskIds);
        tareas.stream()
                .filter(t -> t.getUserId().equals(userId))
                .forEach(t -> {
                    t.setEstado(estado);
                    t.setFechaActualizacion(LocalDateTime.now());
                });
        tareaRepository.saveAll(tareas);

        log.info("Actualizadas {} tareas", tareas.size());
    }

    public void bulkArchive(List<Long> taskIds, Long userId) {
        log.info("Archivando {} tareas para userId: {}", taskIds.size(), userId);

        List<Tarea> tareas = tareaRepository.findAllById(taskIds);
        tareas.stream()
                .filter(t -> t.getUserId().equals(userId))
                .forEach(t -> t.setArchivada(true));
        tareaRepository.saveAll(tareas);

        log.info("Archivadas {} tareas", tareas.size());
    }

    // ========== MÉTODOS PRIVADOS ==========

    private Tarea findTaskByIdAndUserId(Long taskId, Long userId) {
        return tareaRepository.findById(taskId)
                .filter(t -> t.getUserId().equals(userId))
                .filter(t -> !t.isArchivada())
                .orElseThrow(() -> {
                    log.error("Tarea no encontrada - ID: {}, UserId: {}", taskId, userId);
                    return new EntityNotFoundException("Tarea no encontrada con id: " + taskId);
                });
    }

    private String getUsernameByUserId(Long userId) {
        // ⚠️ IMPORTANTE: Cambia este valor por el username REAL que usas en tus pruebas
        // Debe coincidir con el username que tienes en la base de datos
        return "reydi2huallparttupa";
    }
}