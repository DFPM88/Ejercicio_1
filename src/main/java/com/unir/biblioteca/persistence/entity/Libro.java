package com.unir.biblioteca.persistence.entity;

import java.sql.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    private String Isbn;
    private String Tema;
    private String Editorial;
    @Temporal(TemporalType.DATE)
    private Date AñoPublicacion;
    
    

   
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "libro_autor",
        joinColumns = @JoinColumn(name = "libro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id"))
        private List<Autor> autores;

    @ManyToOne
    @JoinColumn(name = "prestamo_id")
    private Prestamo prestamo;
}