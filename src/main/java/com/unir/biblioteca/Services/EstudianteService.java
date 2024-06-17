package com.unir.biblioteca.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.unir.Exceptions.MiException;
import com.unir.biblioteca.persistence.entity.Estudiante;
import com.unir.biblioteca.repository.RepoEstudiante;

@Service
public class EstudianteService {

    @Autowired
    private RepoEstudiante repoEstudiante;

    public List<Estudiante> listarEstudiantes() {
        return repoEstudiante.findAll();
    }

    public Optional<Estudiante> buscarEstudiantePorId(Long id) {
        return repoEstudiante.findById(id);
    }

    public List<Estudiante> buscarEstudiantesPorNombre(String nombre) {
        return repoEstudiante.findByNombreEstudianteContainingIgnoreCase(nombre);
    }


    public void crearEstudiante(Estudiante estudiante) throws MiException {
        validarEstudiante(estudiante);
        repoEstudiante.save(estudiante);
    }

    public void eliminarEstudiante(Long id) {
        Optional<Estudiante> estudiante = repoEstudiante.findById(id);
        if (estudiante.isPresent()) {
            repoEstudiante.deleteById(id);
        } else {
            throw new IllegalArgumentException("Estudiante no encontrado con ID: " + id);
        }
    }

    public void actualizarEstudiante(Long id, Estudiante estudianteActualizado) throws MiException {
        Optional<Estudiante> respuesta = repoEstudiante.findById(id);
        if (respuesta.isPresent()) {
            Estudiante estudiante = respuesta.get();
            if (estudianteActualizado.getNombreEstudiante() != null) {
                estudiante.setNombreEstudiante(estudianteActualizado.getNombreEstudiante());
            }
            if (estudianteActualizado.getCodigoEstudiante() != null) {
                estudiante.setCodigoEstudiante(estudianteActualizado.getCodigoEstudiante());
            }
            if (estudianteActualizado.getFacultad() != null) {
                estudiante.setFacultad(estudianteActualizado.getFacultad());
            }
            if (estudianteActualizado.getTelefono() != null) {
                estudiante.setTelefono(estudianteActualizado.getTelefono());
            }
            if (estudianteActualizado.getEmail() != null) {
                estudiante.setEmail(estudianteActualizado.getEmail());
            }
            repoEstudiante.save(estudiante); 
        } else {
            throw new IllegalArgumentException("Estudiante no encontrado con ID: " + id);
        }
    }

    public void validarEstudiante(Estudiante estudiante) throws MiException {
        if (estudiante.getNombreEstudiante() == null || estudiante.getNombreEstudiante().isEmpty()) {
            throw new MiException("El campo nombre de estudiante no puede estar vacío");
        }
        
    }
}
