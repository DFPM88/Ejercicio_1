package com.unir.biblioteca.persistence.entity;




import java.util.List;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    private String  Nacionalidad;


    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros;

}