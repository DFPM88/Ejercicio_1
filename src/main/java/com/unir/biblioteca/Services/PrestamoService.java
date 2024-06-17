package com.unir.biblioteca.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.unir.Exceptions.MiException;
import com.unir.biblioteca.persistence.entity.Prestamo;
import com.unir.biblioteca.repository.RepoPrestamo;

import java.sql.Date;

@Service
public class PrestamoService {

    @Autowired
    private RepoPrestamo prestamoRepository;

    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    public Optional<Prestamo> buscarPrestamoPorId(Long id) {
        return prestamoRepository.findById(id);
    }

    public void crearPrestamo(Prestamo prestamo) throws MiException {
        validarPrestamo(prestamo);
        prestamoRepository.save(prestamo);
    }

    public void eliminarPrestamo(Long id) {
        Optional<Prestamo> prestamo = prestamoRepository.findById(id);
        if (prestamo.isPresent()) {
            prestamoRepository.delete(prestamo.get());
        } else {
            throw new IllegalArgumentException("El préstamo no existe con ID: " + id);
        }
    }

    public void actualizarPrestamo(Long id, Prestamo prestamoActualizado) throws MiException {
        Optional<Prestamo> respuesta = prestamoRepository.findById(id);
        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            if (prestamoActualizado.getFechaPrestamo() != null) {
                prestamo.setFechaPrestamo(prestamoActualizado.getFechaPrestamo());
            }
            if (prestamoActualizado.getFechaDevolucion() != null) {
                prestamo.setFechaDevolucion(prestamoActualizado.getFechaDevolucion());
            }
            if (prestamoActualizado.getEstudiante() != null) {
                prestamo.setEstudiante(prestamoActualizado.getEstudiante());
            }
            if (prestamoActualizado.getLibros() != null) {
                prestamo.setLibros(prestamoActualizado.getLibros());
            }
            prestamoRepository.save(prestamo);
        } else {
            throw new IllegalArgumentException("Préstamo no encontrado con ID: " + id);
        }
    }

    public void validarPrestamo(Prestamo prestamo) throws MiException {
        if (prestamo.getFechaPrestamo() == null) {
            throw new MiException("La fecha de préstamo no puede estar vacía");
        }
        // Puedes añadir más validaciones según sea necesario
    }

    public List<Prestamo> buscarPrestamosPorFecha(Date fecha) {
        return prestamoRepository.findByFechaPrestamo(fecha);
    }
}
