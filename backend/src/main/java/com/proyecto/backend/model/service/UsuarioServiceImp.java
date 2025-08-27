package com.proyecto.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.backend.model.entity.Rol;
import com.proyecto.backend.model.entity.Usuario;
import com.proyecto.backend.model.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImp implements IUsuarioService{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    UsuarioServiceImp(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuario.setRol(Rol.USER);
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public String eliminarUsuario(Long id) {
        try{
            usuarioRepository.deleteById(id);
        return "Se elimino el curso correctamente";
        } catch (Exception e)
        {
            return "Error al eliminar al usuario" + e.getMessage();
        }
    }

    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
    Usuario existente = usuarioRepository.findById(id).orElse(null);

    if (existente != null) {
        existente.setNombre(usuarioActualizado.getNombre());
        existente.setApellido(usuarioActualizado.getApellido());
        existente.setCorreo(usuarioActualizado.getCorreo());
        existente.setTelefono(usuarioActualizado.getTelefono());
        existente.setContrasena(passwordEncoder.encode(usuarioActualizado.getContrasena()));
        existente.setRol(usuarioActualizado.getRol());
        return usuarioRepository.save(existente);
    }
    return null;
    }

    @Override
    public Usuario buscarCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException(
            "Usuario no encontrado con el correo: " + correo));
    }
}
