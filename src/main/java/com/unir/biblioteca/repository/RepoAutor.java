package com.unir.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.biblioteca.persistence.entity.Autor;


public interface RepoAutor extends JpaRepository<Autor,Long>{

}
