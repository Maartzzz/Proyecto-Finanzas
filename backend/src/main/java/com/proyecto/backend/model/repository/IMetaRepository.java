package com.proyecto.backend.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.backend.model.entity.Metas;
import java.util.List;
import java.util.Optional;


public interface IMetaRepository extends JpaRepository<Metas, Long> {
    Optional<Metas> findByUsuarioIdAndActivoTrue(Long usuarioId);
    List<Metas> findAllByUsuarioIdAndActivoTrue(Long usuarioId);
    List<Metas> findByUsuarioId(Long id);
}
