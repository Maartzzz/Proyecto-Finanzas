package com.proyecto.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.backend.model.entity.Aporte_Meta;

public interface IAporteMetaRepository extends JpaRepository<Aporte_Meta, Long>{
    List<Aporte_Meta> findByMetasId(Long id);
}
