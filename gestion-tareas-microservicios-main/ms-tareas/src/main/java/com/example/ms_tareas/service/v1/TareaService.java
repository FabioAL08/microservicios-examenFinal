package com.example.ms_tareas.service.v1;

import com.example.ms_tareas.model.Tarea;
import com.example.ms_tareas.model.dto.v1.TareaRequest;
import com.example.ms_tareas.repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TareaService {

    private final TareaRepository tareaRepository;

    @Transactional
    public Tarea crearTarea(TareaRequest request, String username) {
        log.info("Creando tarea para usuario: {}", username);

        Tarea tarea = new Tarea();
        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setEstado(request.getEstado() != null ? request.getEstado() : "PENDIENTE");
        tarea.setUsername(username);
        tarea.setUserId(obtenerUserIdPorUsername(username));  // ✅ Asignar userId
        tarea.setFechaCreacion(LocalDateTime.now());
        tarea.setFechaActualizacion(LocalDateTime.now());

        Tarea savedTarea = tareaRepository.save(tarea);
        log.info("Tarea creada con ID: {} para usuario: {}", savedTarea.getId(), username);
        return savedTarea;
    }

    // ✅ Método auxiliar para obtener userId
    private Long obtenerUserIdPorUsername(String username) {
        // TODO: Llamar a ms-usuarios para obtener el ID real
        // Por ahora, retorna 1 como valor por defecto
        return 1L;
    }

    public List<Tarea> listarTareasPorUsuario(String username) {
        log.debug("Listando tareas para usuario: {}", username);
        return tareaRepository.findByUsername(username);
    }

    public List<Tarea> listarTareasPorUsuarioYEstado(String username, String estado) {
        log.debug("Listando tareas para usuario: {} con estado: {}", username, estado);
        return tareaRepository.findByUsernameAndEstado(username, estado);
    }

    public List<Tarea> listarTareasOrdenadasPorFecha(String username) {
        log.debug("Listando tareas ordenadas por fecha para usuario: {}", username);
        return tareaRepository.findByUsernameOrderByFechaCreacionDesc(username);
    }

    public long contarTareasPorEstado(String username, String estado) {
        log.debug("Contando tareas para usuario: {} con estado: {}", username, estado);
        return tareaRepository.countByUsernameAndEstado(username, estado);
    }

    @Transactional
    public void eliminarTareasPorUsuario(String username) {
        log.info("Eliminando todas las tareas del usuario: {}", username);
        tareaRepository.deleteByUsername(username);
    }

    @Transactional
    public Tarea actualizarTarea(Long id, TareaRequest request, String username) {
        log.info("Actualizando tarea ID: {} para usuario: {}", id, username);

        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Tarea no encontrada con ID: {}", id);
                    return new RuntimeException("Tarea no encontrada");
                });

        if (!tarea.getUsername().equals(username)) {
            log.warn("Usuario {} intentó modificar tarea de {}", username, tarea.getUsername());
            throw new RuntimeException("No tienes permiso para modificar esta tarea");
        }

        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setEstado(request.getEstado());
        tarea.setFechaActualizacion(LocalDateTime.now());

        return tareaRepository.save(tarea);
    }

    @Transactional
    public void eliminarTarea(Long id, String username) {
        log.info("Eliminando tarea ID: {} para usuario: {}", id, username);

        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Tarea no encontrada con ID: {}", id);
                    return new RuntimeException("Tarea no encontrada");
                });

        if (!tarea.getUsername().equals(username)) {
            log.warn("Usuario {} intentó eliminar tarea de {}", username, tarea.getUsername());
            throw new RuntimeException("No tienes permiso para eliminar esta tarea");
        }

        tareaRepository.deleteById(id);
    }

    public Tarea obtenerTareaPorId(Long id, String username) {
        log.debug("Obteniendo tarea ID: {} para usuario: {}", id, username);

        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Tarea no encontrada con ID: {}", id);
                    return new RuntimeException("Tarea no encontrada");
                });

        if (!tarea.getUsername().equals(username)) {
            log.warn("Usuario {} intentó ver tarea de {}", username, tarea.getUsername());
            throw new RuntimeException("No tienes permiso para ver esta tarea");
        }

        return tarea;
    }
}