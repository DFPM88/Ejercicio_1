package com.unir.biblioteca.repository;

import com.unir.biblioteca.persistence.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RepoPrestamo extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByEstudianteNombreEstudianteIgnoreCase(String nombreEstudiante);

    
    List<Prestamo> findByFechaPrestamo(Date fechaPrestamo);
}
