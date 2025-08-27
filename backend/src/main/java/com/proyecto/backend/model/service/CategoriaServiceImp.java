package com.proyecto.backend.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.backend.model.entity.Categoria;
import com.proyecto.backend.model.repository.ICategoriaRepository;

@Service
public class CategoriaServiceImp implements ICategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> listarPorUsuario(Long usuarioId) {
        return categoriaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }
}
