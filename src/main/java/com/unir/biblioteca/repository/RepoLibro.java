package com.unir.biblioteca.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.unir.biblioteca.persistence.entity.Libro;

public interface RepoLibro extends JpaRepository<Libro, Long> {

    List<Libro> findByNombreLibroContainingIgnoreCase(String nombre);

}
