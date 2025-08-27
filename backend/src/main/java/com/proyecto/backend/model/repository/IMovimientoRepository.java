package com.proyecto.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.backend.model.entity.Movimiento;

public interface IMovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByUsuarioId(Long id);
}
