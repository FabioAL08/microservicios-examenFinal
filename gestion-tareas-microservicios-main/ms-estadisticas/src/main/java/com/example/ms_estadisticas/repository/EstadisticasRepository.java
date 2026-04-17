package com.example.ms_estadisticas.repository;

import com.example.ms_estadisticas.model.Estadisticas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstadisticasRepository extends JpaRepository<Estadisticas, Long> {

    Optional<Estadisticas> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT e FROM Estadisticas e ORDER BY e.tareasCompletadas DESC")
    List<Estadisticas> findTopUsuarios();
}