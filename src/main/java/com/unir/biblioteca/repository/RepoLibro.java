package com.unir.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.biblioteca.persistence.entity.Libro;

public interface RepoLibro extends JpaRepository<Libro,Long> {

}
