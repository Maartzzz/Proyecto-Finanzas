package com.proyecto.backend.model.service;

import java.util.List;

import com.proyecto.backend.model.entity.Usuario;

public interface IUsuarioService {

    Usuario guardarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Usuario buscarUsuario(Long id);
    String eliminarUsuario(Long id);
    Usuario actualizarUsuario(Long id, Usuario usuarioActualizado);
    Usuario buscarCorreo(String correo);
}
