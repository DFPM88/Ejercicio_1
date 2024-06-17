package com.unir.biblioteca.Controllers;

import com.unir.Exceptions.MiException;
import com.unir.biblioteca.Services.LibroService;
import com.unir.biblioteca.persistence.entity.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/listar")
    public ResponseEntity<List<Libro>> listarLibros() {
        List<Libro> libros = libroService.listarLibros();
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/buscarid/{id}")
    public ResponseEntity<Libro> buscarPorId(@PathVariable Long id) {
        Optional<Libro> libro = libroService.buscarPorId(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarpornombre")
public ResponseEntity<List<Libro>> buscarPorNombre(@RequestParam String nombre) {
    List<Libro> libros = libroService.buscarPorNombre(nombre);
    return ResponseEntity.ok(libros);
}


    @PostMapping("/crear")
    public ResponseEntity<Object> crearLibro(@RequestBody Libro libro) {
        try {
            libroService.crearLibro(libro);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (MiException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Object> actualizarLibro(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            libroService.actualizarLibro(id, libro);
            return ResponseEntity.ok().build();
        } catch (MiException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
        return ResponseEntity.ok().build();
    }
}
