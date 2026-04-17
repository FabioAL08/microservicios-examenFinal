package com.example.ms_tareas.repository;

import com.example.ms_tareas.model.Tarea;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Hidden
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByUsername(String username);

    List<Tarea> findByUsernameAndEstado(String username, String estado);

    List<Tarea> findByEstado(String estado);

    List<Tarea> findByUsernameOrderByFechaCreacionDesc(String username);

    long countByUsernameAndEstado(String username, String estado);

    void deleteByUsername(String username);

    Page<Tarea> findByUsernameAndArchivadaFalse(String username, Pageable pageable);

    List<Tarea> findByUsernameAndArchivadaFalse(String username);

    @Query("SELECT t FROM Tarea t WHERE t.username = :username AND t.archivada = false " +
            "AND (:estado IS NULL OR t.estado = :estado) " +
            "AND (:prioridad IS NULL OR t.prioridad = :prioridad) " +
            "AND (:fechaDesde IS NULL OR t.fechaVencimiento >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR t.fechaVencimiento <= :fechaHasta)")
    Page<Tarea> findByFilters(@Param("username") String username,
                              @Param("estado") String estado,
                              @Param("prioridad") String prioridad,
                              @Param("fechaDesde") LocalDate fechaDesde,
                              @Param("fechaHasta") LocalDate fechaHasta,
                              Pageable pageable);

    @Query("SELECT t FROM Tarea t WHERE t.username = :username AND t.archivada = false " +
            "AND (LOWER(t.titulo) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Tarea> searchByKeyword(@Param("username") String username,
                                @Param("keyword") String keyword);
}