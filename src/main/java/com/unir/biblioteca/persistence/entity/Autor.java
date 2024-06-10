package com.unir.biblioteca.persistence.entity;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Entity
@Builder
@AllArgsConstructor
@Data

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String NombreAutor;


    @OneToOne(mappedBy = "autor")
    private Libro libro;

}