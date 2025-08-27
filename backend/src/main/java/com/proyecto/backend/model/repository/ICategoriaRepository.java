package com.proyecto.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.backend.model.entity.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByUsuarioId(Long id);
}
