package com.unir.biblioteca.Controllers;

import com.unir.Exceptions.MiException;
import com.unir.biblioteca.Services.EstudianteService;
import com.unir.biblioteca.persistence.entity.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping("/listar")
    public ResponseEntity<List<Estudiante>> listarEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.listarEstudiantes();
        return new ResponseEntity<>(estudiantes, HttpStatus.OK);
    }

    @GetMapping("/buscarid/{id}")
    public ResponseEntity<Estudiante> buscarEstudiantePorId(@PathVariable("id") Long id) {
        Optional<Estudiante> estudiante = estudianteService.buscarEstudiantePorId(id);
        return estudiante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarpornombre")
    public ResponseEntity<List<Estudiante>> buscarEstudiantesPorNombre(@RequestParam("nombre") String nombre) {
        List<Estudiante> estudiantes = estudianteService.buscarEstudiantesPorNombre(nombre);
        if (estudiantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(estudiantes);
        }
    }
    

    @PostMapping("/crear")
    public ResponseEntity<?> crearEstudiante(@RequestBody Estudiante estudiante) {
        try {
            estudianteService.crearEstudiante(estudiante);
            return new ResponseEntity<>("Estudiante creado correctamente", HttpStatus.CREATED);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al crear estudiante: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEstudiante(@PathVariable("id") Long id,
                                                  @RequestBody Estudiante estudianteActualizado) {
        try {
            estudianteService.actualizarEstudiante(id, estudianteActualizado);
            return new ResponseEntity<>("Estudiante actualizado correctamente", HttpStatus.OK);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al actualizar estudiante: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEstudiante(@PathVariable("id") Long id) {
        try {
            estudianteService.eliminarEstudiante(id);
            return new ResponseEntity<>("Estudiante eliminado correctamente", HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>("No se puede eliminar el estudiante porque tiene registros asociados en la tabla de prestamos.", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error al intentar eliminar el estudiante: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
