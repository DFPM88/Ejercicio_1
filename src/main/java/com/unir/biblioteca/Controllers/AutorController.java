package com.unir.biblioteca.Controllers;

import com.unir.Exceptions.MiException;
import com.unir.biblioteca.Services.AutorService;
import com.unir.biblioteca.persistence.entity.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseEntity<?> crearAutor(@RequestBody Autor nuevoAutor) {
        try {
            Libro libro = repoLibro.findById(nuevoAutor.getLibros().get(0).getId())
                    .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

            nuevoAutor.setLibros(Collections.singletonList(libro));

            autorService.crearAutor(nuevoAutor);

            return new ResponseEntity<>(nuevoAutor.getId(), HttpStatus.CREATED);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al crear autor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Error al crear autor: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarAutor(@PathVariable("id") Long id,@RequestBody Autor autorActualizado) {
        try {
            Autor autorExistente = autorService.buscarAutorPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));

            autorExistente.setNombreAutor(autorActualizado.getNombreAutor());
            autorExistente.setNacionalidad(autorActualizado.getNacionalidad());
            autorService.actualizarAutor(id, autorExistente);

            return new ResponseEntity<>("Autor actualizado correctamente", HttpStatus.OK);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al actualizar autor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Error al actualizar autor: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarAutorPorId(@PathVariable("id") Long id) {
    try {
        autorService.eliminarAutor(id);
        return new ResponseEntity<>("Autor eliminado correctamente", HttpStatus.OK);
    } catch (DataIntegrityViolationException ex) {
        return new ResponseEntity<>("No se puede eliminar el autor porque tiene libros asociados", HttpStatus.CONFLICT);
    } catch (IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    }
}
