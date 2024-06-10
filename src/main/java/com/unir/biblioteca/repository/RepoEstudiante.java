package com.unir.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.biblioteca.persistence.entity.Estudiante;


public interface RepoEstudiante extends JpaRepository<Estudiante,Long>{

}
