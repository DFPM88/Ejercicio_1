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
    RepoAutor repoautor;

    public List<Autor>listautor(){

        return repoautor.findAll();
    }

    public Optional<Autor>buscarId(Long id){

        return repoautor.findById(id);

    }

    public void CreateAutor(Autor autor)throws MiException{

        valited(autor);
        repoautor.save(autor);
    }

    public void ElimunarAutor(Long id){

        Optional<Autor>autor = repoautor.findById(id);
        if(autor.isPresent()){

            repoautor.deleteById(id);
        }else{
            throw new IllegalArgumentException("El autor no existe");
        }
    }

    public void ActualizarAutor(Long id, Autor UpdateAutor) throws MiException{

        Optional<Autor>respuesta = repoautor.findById(id);
        if(respuesta.isPresent()){

            Autor autor = respuesta.get();
            if (UpdateAutor.getNombreAutor()!=null) {
                autor.setNombreAutor(UpdateAutor.getNombreAutor());
                
            }
            if (UpdateAutor.getNacionalidad()!=null){
                autor.setNacionalidad(UpdateAutor.getNacionalidad());
            }
            if (UpdateAutor.getLibros()!=null){
                autor.setLibros(UpdateAutor.getLibros());
            }
        }
    }
    
    public void valited(Autor autor) throws MiException{

        if(autor.getNombreAutor()==null || autor.getNombreAutor().isEmpty()){
            throw new MiException(" El campo nombre no puede estar vacio");
        }
        if(autor.getNacionalidad()==null || autor.getNombreAutor().isEmpty()){
            throw new MiException(" La campo nacionalidad no puede estar vacio");
        }
        if(autor.getLibros()==null || autor.getLibros().isEmpty()){
            throw new MiException(" El campo libro no puede estar vacio");
        }
    }



}
