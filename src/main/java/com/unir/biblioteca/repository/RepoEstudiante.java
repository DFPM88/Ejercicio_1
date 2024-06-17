package com.unir.biblioteca.repository;

import com.unir.biblioteca.persistence.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RepoEstudiante extends JpaRepository<Estudiante, Long> {

    List<Estudiante> findByNombreEstudianteContainingIgnoreCase(String nombre);
}

