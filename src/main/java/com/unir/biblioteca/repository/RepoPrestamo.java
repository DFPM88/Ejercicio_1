package com.unir.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.unir.biblioteca.persistence.entity.Prestamo;

public interface RepoPrestamo extends JpaRepository<Prestamo,Long> {

}
