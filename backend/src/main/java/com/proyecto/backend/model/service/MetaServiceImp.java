package com.proyecto.backend.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.backend.model.entity.Metas;
import com.proyecto.backend.model.repository.IMetaRepository;

@Service
public class MetaServiceImp implements IMetaService {

    @Autowired
    private IMetaRepository metaRepository;

    @Override
    public void desactivarMetas(Long id) {
        List<Metas> activas = metaRepository.findAllByUsuarioIdAndActivoTrue(id);
        for (Metas meta : activas) {
            meta.setActivo(false);
            metaRepository.save(meta);
        }
    }

    @Override
    public Metas guardar(Metas meta) {
        return metaRepository.save(meta);
    }

    @Override
    public Optional<Metas> buscarPorId(Long id) {
        return metaRepository.findById(id);
    }

    @Override
    public Optional<Metas> obtenerMetaActivaPorUsuario(Long id) {
        return metaRepository.findByUsuarioIdAndActivoTrue(id);
    }

    @Override
    public List<Metas> listarPorUsuario(Long id) {
        return metaRepository.findByUsuarioId(id);
    }

    @Override
    public void eliminar(Long id) {
        metaRepository.deleteById(id);
    }

}
