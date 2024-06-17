package com.unir.biblioteca.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.unir.Exceptions.MiException;
import com.unir.biblioteca.Services.AutorService;
import com.unir.biblioteca.persistence.entity.Autor;
import com.unir.biblioteca.persistence.entity.Libro;
import com.unir.biblioteca.repository.RepoLibro;

import java.util.Collections;

@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorService autorService;
    
    @Autowired
    private RepoLibro repoLibro;

    @PostMapping("/crearautor")
public ResponseEntity<?> CrearAutor(@RequestParam("nombreautor") String nombreAutor,
                                    @RequestParam("nacionalidad") String nacionalidad,
                                    @RequestParam("libros") Long librosId) {
    try {
        // Busca el libro por id, si no lo encuentra lanza una excepción
        Libro libro = repoLibro.findById(librosId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));

        // Instancia el objeto Autor y configura sus propiedades
        Autor autor = new Autor();
        autor.setNombreAutor(nombreAutor);
        autor.setNacionalidad(nacionalidad);
        autor.setLibros(Collections.singletonList(libro)); // o autor.getLibros().add(libro) si quieres agregar más libros

        // Llama al servicio para crear el autor
        autorService.CreateAutor(autor);

        // Devuelve la respuesta con el id del autor creado y el estado HTTP 201 (CREATED)
        return new ResponseEntity<>(autor.getId(), HttpStatus.CREATED);
    } catch (MiException ex) {
        // Devuelve una respuesta con el mensaje de error y el estado HTTP 500 (INTERNAL_SERVER_ERROR)
        return new ResponseEntity<>("Error al crear autor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}

