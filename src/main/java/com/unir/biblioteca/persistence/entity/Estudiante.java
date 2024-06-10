package com.unir.biblioteca.persistence.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Entity
@Builder
@AllArgsConstructor
@Data

public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String NombreEstudiante;
    private String CodigoEstudiante;
    private String Facultad;
    private String Telefono;
    private String Email;

    @OneToMany(mappedBy = "estudiante")
    private List<Prestamo> prestamos;

}