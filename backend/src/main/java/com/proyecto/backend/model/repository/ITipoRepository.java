package com.proyecto.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.backend.model.entity.Tipo;

public interface ITipoRepository extends JpaRepository<Tipo, Long> {

}
