package com.unir.biblioteca.persistence.entity;

import java.sql.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Entity
@Builder
@AllArgsConstructor
@Data

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String NombreLibro;
    private String Tema;
    private String Editorial;
    @Temporal(TemporalType.DATE)
    private Date AñoPublicacion;

   
    @OneToOne
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "prestamo_id")
    private Prestamo prestamo;

}
