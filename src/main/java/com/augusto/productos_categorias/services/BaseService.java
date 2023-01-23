package com.augusto.productos_categorias.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.augusto.productos_categorias.repositories.BaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public abstract class BaseService <T>{
    
    private final BaseRepository <T> baseRepository;

    // Encontrar modelo por id
    public T findById(Long id){
        Optional <T> optional = baseRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    //Listar modelos
    public List <T> list(){
        return baseRepository.findAll();
    }

    //Guardar modelo
    public T save(T object){
        return baseRepository.save(object);
    }
}
