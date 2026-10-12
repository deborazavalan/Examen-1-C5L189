package com.medtriage.repository;

import com.medtriage.model.CitaMedica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {

    List<CitaMedica> findByNivelPrioridadOrderByFechaCitaDesc(String nivelPrioridad);

    List<CitaMedica> findAllByOrderByFechaCitaDesc();
}
