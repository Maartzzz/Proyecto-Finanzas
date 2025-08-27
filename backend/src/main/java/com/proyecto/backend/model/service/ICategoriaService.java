package com.proyecto.backend.model.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.backend.model.entity.Categoria;

public interface ICategoriaService {
    Categoria guardarCategoria(Categoria categoria);
    List<Categoria> listarPorUsuario(Long usuarioId);
    void eliminar(Long id);
    Optional<Categoria> buscarPorId(Long id);
}
