package com.unir.biblioteca.Controllers;

import com.unir.Exceptions.MiException;
import com.unir.biblioteca.Services.PrestamoService;
import com.unir.biblioteca.persistence.entity.Prestamo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Prestamo>> listarPrestamos() {
        List<Prestamo> prestamos = prestamoService.listarPrestamos();
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/buscarid/{id}")
    public ResponseEntity<Prestamo> buscarPrestamoPorId(@PathVariable("id") Long id) {
        try {
            Optional<Prestamo> prestamo = prestamoService.buscarPrestamoPorId(id);
            return prestamo.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPrestamo(@RequestBody Prestamo prestamo) {
        try {
            prestamoService.crearPrestamo(prestamo);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (MiException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear préstamo: " + ex.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPrestamo(@PathVariable("id") Long id, @RequestBody Prestamo prestamoActualizado) {
        try {
            prestamoService.actualizarPrestamo(id, prestamoActualizado);
            return ResponseEntity.ok().body("Préstamo actualizado correctamente");
        } catch (MiException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar préstamo: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al actualizar préstamo: " + ex.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPrestamo(@PathVariable("id") Long id) {
        try {
            prestamoService.eliminarPrestamo(id);
            return ResponseEntity.ok().body("Préstamo eliminado correctamente");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar préstamo: " + ex.getMessage());
        }
    }
    
}
