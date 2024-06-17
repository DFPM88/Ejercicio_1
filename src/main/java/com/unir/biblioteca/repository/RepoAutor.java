package com.unir.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.biblioteca.persistence.entity.Autor;


public interface RepoAutor extends JpaRepository<Autor,Long>{
    List<Autor> findByNombreAutorContainingIgnoreCase(String nombre);

}
