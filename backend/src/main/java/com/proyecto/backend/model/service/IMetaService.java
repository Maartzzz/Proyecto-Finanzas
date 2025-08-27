package com.proyecto.backend.model.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.backend.model.entity.Metas;

public interface IMetaService {

    void desactivarMetas(Long id);
    void eliminar(Long id);
    Metas guardar(Metas meta);
    Optional<Metas> buscarPorId(Long id);
    Optional<Metas> obtenerMetaActivaPorUsuario(Long usuarioId);
    List<Metas> listarPorUsuario(Long usuarioId);
}
