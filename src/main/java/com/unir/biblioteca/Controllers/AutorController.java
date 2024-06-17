package com.unir.biblioteca.Controllers;

import com.unir.Exceptions.MiException;
import com.unir.biblioteca.Services.AutorService;
import com.unir.biblioteca.persistence.entity.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.unir.biblioteca.persistence.entity.Libro;
import com.unir.biblioteca.repository.RepoLibro;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @Autowired
    private RepoLibro repoLibro;

    @GetMapping("/listar")
    public ResponseEntity<List<Autor>> listarAutores() {
        List<Autor> autores = autorService.listarAutores();
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }

    @GetMapping("/buscarid/{id}")
    public ResponseEntity<?> buscarAutorPorId(@PathVariable("id") Long id) {
        try {
            Autor autor = autorService.buscarAutorPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));
            return new ResponseEntity<>(autor, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Error al buscar autor: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscarpornombre")
    public ResponseEntity<List<Autor>> buscarAutorPorNombre(@RequestParam("nombre") String nombre) {
        try {
            List<Autor> autores = autorService.buscarAutoresPorNombre(nombre);
            if (autores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(autores, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearAutor(@RequestParam("nombreautor") String nombreAutor,
                                        @RequestParam("nacionalidad") String nacionalidad,
                                        @RequestParam("libros") Long librosId) {
        try {
            // Busca el libro por id, si no lo encuentra lanza una excepción
            Libro libro = repoLibro.findById(librosId)
                    .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

            // Instancia el objeto Autor y configura sus propiedades
            Autor autor = new Autor();
            autor.setNombreAutor(nombreAutor);
            autor.setNacionalidad(nacionalidad);
            autor.setLibros(Collections.singletonList(libro));

            // Llama al servicio para crear el autor
            autorService.crearAutor(autor);

            // Devuelve la respuesta con el id del autor creado y el estado HTTP 201 (CREATED)
            return new ResponseEntity<>(autor.getId(), HttpStatus.CREATED);
        } catch (MiException ex) {
            // Devuelve una respuesta con el mensaje de error y el estado HTTP 500 (INTERNAL_SERVER_ERROR)
            return new ResponseEntity<>("Error al crear autor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            // Devuelve una respuesta con el mensaje de error y el estado HTTP 404 (NOT_FOUND)
            return new ResponseEntity<>("Error al crear autor: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarAutor(@PathVariable("id") Long id,
                                             @RequestBody Autor autorActualizado) {
        try {
            // Primero, verificamos si el autor que intentamos actualizar existe
            Autor autorExistente = autorService.buscarAutorPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));

            // Actualizamos los campos del autor existente con los valores del autor actualizado
            autorExistente.setNombreAutor(autorActualizado.getNombreAutor());
            autorExistente.setNacionalidad(autorActualizado.getNacionalidad());
            // Puedes manejar la actualización de libros según tus necesidades específicas

            // Llamamos al servicio para guardar los cambios
            autorService.actualizarAutor(id, autorExistente);

            // Devolvemos una respuesta con un mensaje y el estado HTTP 200 OK
            return new ResponseEntity<>("Autor actualizado correctamente", HttpStatus.OK);
        } catch (MiException ex) {
            // En caso de excepción de negocio, devolvemos un error interno
            return new ResponseEntity<>("Error al actualizar autor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            // Si el autor no existe, devolvemos un error 404 Not Found
            return new ResponseEntity<>("Error al actualizar autor: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarAutorPorId(@PathVariable("id") Long id) {
        try {
            autorService.eliminarAutor(id);
            return new ResponseEntity<>("Autor eliminado correctamente", HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
