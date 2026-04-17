package com.example.ms_estadisticas.controller;

import com.example.ms_estadisticas.model.dto.EstadisticasDTO;
import com.example.ms_estadisticas.model.dto.EstadisticasResponseDTO;
import com.example.ms_estadisticas.model.dto.GlobalStatsDTO;
import com.example.ms_estadisticas.service.EstadisticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
@Tag(name = "Estadísticas", description = "API para consultar estadísticas de tareas")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class EstadisticaController {

    private final EstadisticaService estadisticaService;

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/v1/mis-estadisticas")
    @Operation(summary = "[V1] Obtener mis estadísticas básicas")
    public ResponseEntity<EstadisticasDTO> obtenerMisEstadisticasV1() {
        return ResponseEntity.ok(estadisticaService.obtenerEstadisticasBasicas(getCurrentUsername()));
    }

    @GetMapping("/v1/{username}")
    @Operation(summary = "[V1] Obtener estadísticas básicas por username")
    public ResponseEntity<EstadisticasDTO> obtenerEstadisticasV1(@PathVariable String username) {
        return ResponseEntity.ok(estadisticaService.obtenerEstadisticasBasicas(username));
    }

    @GetMapping("/v2/mis-estadisticas")
    @Operation(summary = "[V2] Obtener mis estadísticas completas")
    public ResponseEntity<EstadisticasResponseDTO> obtenerMisEstadisticasCompletas() {
        return ResponseEntity.ok(estadisticaService.obtenerEstadisticasCompletas(getCurrentUsername()));
    }

    @GetMapping("/v2/{username}")
    @Operation(summary = "[V2] Obtener estadísticas completas por username")
    public ResponseEntity<EstadisticasResponseDTO> obtenerEstadisticasCompletas(@PathVariable String username) {
        return ResponseEntity.ok(estadisticaService.obtenerEstadisticasCompletas(username));
    }

    @GetMapping("/top/{limit}")
    @Operation(summary = "Top usuarios por tareas completadas")
    public ResponseEntity<List<EstadisticasResponseDTO>> getTopUsuarios(@PathVariable int limit) {
        return ResponseEntity.ok(estadisticaService.getTopUsuarios(limit));
    }

    @GetMapping("/global/resumen")
    @Operation(summary = "Estadísticas globales del sistema")
    public ResponseEntity<GlobalStatsDTO> getGlobalStats() {
        return ResponseEntity.ok(estadisticaService.getGlobalStats());
    }

    @GetMapping("/existe/{username}")
    @Operation(summary = "Verificar si existen estadísticas")
    public ResponseEntity<Boolean> existeEstadisticas(@PathVariable String username) {
        return ResponseEntity.ok(estadisticaService.existenEstadisticas(username));
    }
}