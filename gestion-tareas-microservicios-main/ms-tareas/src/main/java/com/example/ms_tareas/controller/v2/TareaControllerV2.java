package com.example.ms_tareas.controller.v2;

import com.example.ms_tareas.model.dto.v2.TareaRequestV2;
import com.example.ms_tareas.model.dto.v2.TareaResponseV2;
import com.example.ms_tareas.model.dto.v2.TaskStatsDTO;
import com.example.ms_tareas.service.v2.TareaServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/tareas")
@Tag(name = "Tareas V2", description = "API de tareas - Versión 2 con funcionalidades avanzadas")
@SecurityRequirement(name = "Bearer Authentication")
public class TareaControllerV2 {

    @Autowired
    private TareaServiceV2 tareaServiceV2;

    // ✅ Método para obtener username del token automáticamente
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // ✅ Método auxiliar para obtener userId (temporal)
    private Long getCurrentUserId() {
        // TODO: Llamar a ms-usuarios para obtener el ID real
        // Por ahora retorna 1
        return 1L;
    }

    @PostMapping
    @Operation(summary = "Crear nueva tarea V2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<TareaResponseV2> createTask(@Valid @RequestBody TareaRequestV2 request) {
        // ✅ Obtener username y userId automáticamente del token
        String username = getCurrentUsername();
        Long userId = getCurrentUserId();

        TareaResponseV2 createdTask = tareaServiceV2.createTask(request, userId, username);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Obtener tarea por ID V2")
    public ResponseEntity<TareaResponseV2> getTaskById(@PathVariable Long taskId) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(tareaServiceV2.getTaskById(taskId, userId));
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "Actualizar tarea completa V2")
    public ResponseEntity<TareaResponseV2> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody TareaRequestV2 request) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(tareaServiceV2.updateTask(taskId, request, userId));
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Eliminar tarea V2 (soft delete)")
    @ApiResponse(responseCode = "204", description = "Tarea eliminada/archivada exitosamente")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        Long userId = getCurrentUserId();
        tareaServiceV2.deleteTask(taskId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar tareas V2 con paginación y ordenamiento")
    public ResponseEntity<Page<TareaResponseV2>> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Long userId = getCurrentUserId();
        Sort.Direction direction = Sort.Direction.fromString(sortDir.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(tareaServiceV2.getTasksByUser(userId, pageable));
    }

    @GetMapping("/filtros")
    @Operation(summary = "Filtrar tareas por múltiples criterios")
    public ResponseEntity<Page<TareaResponseV2>> filterTasks(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String prioridad,
            @RequestParam(required = false) LocalDate fechaDesde,
            @RequestParam(required = false) LocalDate fechaHasta,
            @RequestParam(required = false) String etiqueta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long userId = getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(tareaServiceV2.filterTasks(userId, estado, prioridad,
                fechaDesde, fechaHasta, etiqueta, pageable));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar tareas por texto")
    public ResponseEntity<List<TareaResponseV2>> searchTasks(@RequestParam String keyword) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(tareaServiceV2.searchTasks(userId, keyword));
    }

    @PatchMapping("/{taskId}/estado")
    @Operation(summary = "Actualizar solo el estado de la tarea")
    public ResponseEntity<TareaResponseV2> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam String estado) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(tareaServiceV2.updateTaskStatus(taskId, estado, userId));
    }

    @PatchMapping("/{taskId}/progreso")
    @Operation(summary = "Actualizar el porcentaje de completado")
    public ResponseEntity<TareaResponseV2> updateProgress(
            @PathVariable Long taskId,
            @RequestParam Integer porcentaje) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(tareaServiceV2.updateProgress(taskId, porcentaje, userId));
    }

    @PatchMapping("/{taskId}/tiempo-real")
    @Operation(summary = "Actualizar tiempo real empleado")
    public ResponseEntity<TareaResponseV2> updateRealTime(
            @PathVariable Long taskId,
            @RequestParam Integer horas) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(tareaServiceV2.updateRealTime(taskId, horas, userId));
    }

    @GetMapping("/estadisticas/resumen")
    @Operation(summary = "Obtener estadísticas de tareas del usuario")
    public ResponseEntity<TaskStatsDTO> getTaskStats() {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(tareaServiceV2.getTaskStats(userId));
    }

    @GetMapping("/estadisticas/por-estado")
    @Operation(summary = "Agrupar tareas por estado")
    public ResponseEntity<Map<String, Long>> getTasksGroupByStatus() {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(tareaServiceV2.getTasksGroupByStatus(userId));
    }

    @GetMapping("/exportar/csv")
    @Operation(summary = "Exportar tareas a CSV")
    public ResponseEntity<String> exportToCsv() {
        Long userId = getCurrentUserId();
        String csv = tareaServiceV2.exportToCsv(userId);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=tareas.csv")
                .body(csv);
    }

    @PatchMapping("/masivo/estado")
    @Operation(summary = "Actualizar estado de múltiples tareas")
    public ResponseEntity<Void> bulkUpdateStatus(
            @RequestParam List<Long> taskIds,
            @RequestParam String estado) {
        Long userId = getCurrentUserId();
        tareaServiceV2.bulkUpdateStatus(taskIds, estado, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/masivo/archivar")
    @Operation(summary = "Archivar múltiples tareas")
    public ResponseEntity<Void> bulkArchive(@RequestParam List<Long> taskIds) {
        Long userId = getCurrentUserId();
        tareaServiceV2.bulkArchive(taskIds, userId);
        return ResponseEntity.ok().build();
    }
}