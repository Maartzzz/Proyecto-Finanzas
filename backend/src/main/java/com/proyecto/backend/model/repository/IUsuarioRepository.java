package com.proyecto.backend.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.backend.model.entity.Usuario;


public interface IUsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByCorreo(String correo);
}
