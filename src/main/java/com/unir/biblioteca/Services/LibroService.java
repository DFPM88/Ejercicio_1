package com.unir.biblioteca.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.unir.Exceptions.MiException;
import com.unir.biblioteca.persistence.entity.Libro;
import com.unir.biblioteca.repository.RepoLibro;

@Service
public class LibroService {

    @Autowired
    RepoLibro repoLibro;

    public List<Libro> listarLibros() {
        return repoLibro.findAll();
    }

    public Optional<Libro> buscarPorId(Long id) {
        return repoLibro.findById(id);
    }

    public void crearLibro(Libro libro) throws MiException {
        validar(libro);
        repoLibro.save(libro);
    }

    public void eliminarLibro(Long id) {
        repoLibro.deleteById(id);
    }

    public void actualizarLibro(Long id, Libro libroActualizado) throws MiException {
        Optional<Libro> respuesta = repoLibro.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            if (libroActualizado.getNombreLibro() != null) {
                libro.setNombreLibro(libroActualizado.getNombreLibro());
            }
            if (libroActualizado.getIsbn() != null) {
                libro.setIsbn(libroActualizado.getIsbn());
            }
            if (libroActualizado.getTema() != null) {
                libro.setTema(libroActualizado.getTema());
            }
            if (libroActualizado.getEditorial() != null) {
                libro.setEditorial(libroActualizado.getEditorial());
            }
            if (libroActualizado.getAñoPublicacion() != null) {
                libro.setAñoPublicacion(libroActualizado.getAñoPublicacion());
            }
            repoLibro.save(libro);
        }
    }

    public List<Libro> buscarPorNombre(String nombre) {
        return repoLibro.findByNombreLibroContainingIgnoreCase(nombre);
    }

    public void validar(Libro libro) throws MiException {
        if (libro.getNombreLibro() == null || libro.getNombreLibro().isEmpty()) {
            throw new MiException("El campo nombre del libro no puede estar vacío");
        }
        
    }
}
