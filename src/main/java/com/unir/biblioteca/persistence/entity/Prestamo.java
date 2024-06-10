package com.unir.biblioteca.persistence.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Entity
@Builder
@AllArgsConstructor
@Data


public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date FechaPrestamo;
    @Temporal(TemporalType.DATE)
    private Date FechaDevolucion;

    
    @ManyToOne
    private Estudiante estudiante;

    @OneToMany(mappedBy = "prestamo")
    private List<Libro> libros;


}
