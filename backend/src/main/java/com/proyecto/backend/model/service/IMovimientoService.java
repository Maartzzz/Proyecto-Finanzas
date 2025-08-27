package com.proyecto.backend.model.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.backend.model.entity.Movimiento;

public interface IMovimientoService {

    Movimiento guardarMovimiento(Movimiento movimiento);
    List<Movimiento> listarPorUsuario(Long usuarioId);
    void eliminar(Long id);
    Optional<Movimiento> buscarPorId(Long id);
}
