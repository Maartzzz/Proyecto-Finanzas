package com.proyecto.backend.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.backend.model.entity.Movimiento;
import com.proyecto.backend.model.repository.IMovimientoRepository;

@Service
public class MovimientoServiceImp implements IMovimientoService {

    @Autowired IMovimientoRepository movimientoRepository;

    @Override
    public Movimiento guardarMovimiento(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    @Override
    public List<Movimiento> listarPorUsuario(Long usuarioId) {
        return movimientoRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public void eliminar(Long id) {
        movimientoRepository.deleteById(id);
    }

    @Override
    public Optional<Movimiento> buscarPorId(Long id) {
        return movimientoRepository.findById(id);
    }

}
