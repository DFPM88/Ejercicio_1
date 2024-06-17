package com.unir.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.biblioteca.persistence.entity.Estudiante;


public interface RepoEstudiante extends JpaRepository<Estudiante,Long>{

    Optional<Estudiante> findByNombreEstudianteIgnoreCase(String nombre);

}
