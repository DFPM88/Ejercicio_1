package com.unir.biblioteca.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.unir.Exceptions.MiException;
import com.unir.biblioteca.persistence.entity.Autor;
import com.unir.biblioteca.repository.RepoAutor;

@Service
public class AutorService {

    @Autowired
    private RepoAutor repoAutor;

    public List<Autor> listarAutores() {
        return repoAutor.findAll();
    }

    public Optional<Autor> buscarAutorPorId(Long id) {
        return repoAutor.findById(id);
    }

    public void crearAutor(Autor autor) throws MiException {
        validarAutor(autor);
        repoAutor.save(autor);
    }

    public void eliminarAutor(Long id) {
        Optional<Autor> autor = repoAutor.findById(id);
        if (autor.isPresent()) {
            repoAutor.delete(autor.get());
        } else {
            throw new IllegalArgumentException("El autor no existe con ID: " + id);
        }
    }

    public void actualizarAutor(Long id, Autor autorActualizado) throws MiException {
        Optional<Autor> respuesta = repoAutor.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            if (autorActualizado.getNombreAutor() != null) {
                autor.setNombreAutor(autorActualizado.getNombreAutor());
            }
            if (autorActualizado.getNacionalidad() != null) {
                autor.setNacionalidad(autorActualizado.getNacionalidad());
            }
            if (autorActualizado.getLibros() != null) {
                autor.setLibros(autorActualizado.getLibros());
            }
            repoAutor.save(autor);
        } else {
            throw new IllegalArgumentException("Autor no encontrado con ID: " + id);
        }
    }

    public void validarAutor(Autor autor) throws MiException {
        if (autor.getNombreAutor() == null || autor.getNombreAutor().isEmpty()) {
            throw new MiException("El campo nombre no puede estar vacío");
        }
        if (autor.getNacionalidad() == null || autor.getNacionalidad().isEmpty()) {
            throw new MiException("El campo nacionalidad no puede estar vacío");
        }
        if (autor.getLibros() == null || autor.getLibros().isEmpty()) {
            throw new MiException("El campo libros no puede estar vacío");
        }
    }

    
    public List<Autor> buscarAutoresPorNombre(String nombre) {
        return repoAutor.findByNombreAutorContainingIgnoreCase(nombre);
    }
}
