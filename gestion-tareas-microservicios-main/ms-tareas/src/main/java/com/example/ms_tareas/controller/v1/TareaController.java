package com.example.ms_tareas.controller.v1;

import com.example.ms_tareas.model.Tarea;
import com.example.ms_tareas.model.dto.v1.TareaRequest;
import com.example.ms_tareas.service.v1.TareaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@Tag(name = "Tareas", description = "API para la gestión de tareas del usuario autenticado")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@Slf4j
public class TareaController {

    private final TareaService tareaService;

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva tarea",
            description = "Crea una nueva tarea asociada al usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<Tarea> crearTarea(@Valid @RequestBody TareaRequest request) {
        String username = getCurrentUsername();
        log.info("Creando tarea para usuario: {}", username);

        Tarea tarea = tareaService.crearTarea(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarea);
    }

    @GetMapping
    @Operation(summary = "Listar todas mis tareas",
            description = "Retorna todas las tareas del usuario autenticado")
    public ResponseEntity<List<Tarea>> listarMisTareas() {
        String username = getCurrentUsername();
        List<Tarea> tareas = tareaService.listarTareasPorUsuario(username);
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar tareas por estado",
            description = "Filtra las tareas del usuario por su estado")
    public ResponseEntity<List<Tarea>> listarTareasPorEstado(
            @Parameter(description = "Estado de la tarea", example = "PENDIENTE")
            @PathVariable String estado) {
        String username = getCurrentUsername();
        List<Tarea> tareas = tareaService.listarTareasPorUsuarioYEstado(username, estado);
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/recientes")
    @Operation(summary = "Listar tareas recientes")
    public ResponseEntity<List<Tarea>> listarTareasRecientes() {
        String username = getCurrentUsername();
        List<Tarea> tareas = tareaService.listarTareasOrdenadasPorFecha(username);
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/contar/{estado}")
    @Operation(summary = "Contar tareas por estado")
    public ResponseEntity<Long> contarTareasPorEstado(@PathVariable String estado) {
        String username = getCurrentUsername();
        long cantidad = tareaService.contarTareasPorEstado(username, estado);
        return ResponseEntity.ok(cantidad);
    }

    @DeleteMapping("/eliminar-todas")
    @Operation(summary = "Eliminar todas mis tareas")
    public ResponseEntity<String> eliminarTodasMisTareas() {
        String username = getCurrentUsername();
        tareaService.eliminarTareasPorUsuario(username);
        return ResponseEntity.ok("Todas las tareas han sido eliminadas");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tarea por ID")
    public ResponseEntity<Tarea> obtenerTarea(@PathVariable Long id) {
        String username = getCurrentUsername();
        Tarea tarea = tareaService.obtenerTareaPorId(id, username);
        return ResponseEntity.ok(tarea);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tarea")
    public ResponseEntity<Tarea> actualizarTarea(
            @PathVariable Long id,
            @Valid @RequestBody TareaRequest request) {
        String username = getCurrentUsername();
        Tarea tarea = tareaService.actualizarTarea(id, request, username);
        return ResponseEntity.ok(tarea);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        String username = getCurrentUsername();
        tareaService.eliminarTarea(id, username);
        return ResponseEntity.noContent().build();
    }
}