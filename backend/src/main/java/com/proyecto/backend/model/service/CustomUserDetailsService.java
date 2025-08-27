package com.proyecto.backend.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import com.proyecto.backend.model.entity.Usuario;
import com.proyecto.backend.model.repository.IUsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Correo no encontrado: " + correo));

                System.out.println("Usuario autenticado: " + correo + " | Rol: " + usuario.getRol());

            // LOG 2: mostrar las autoridades generadas
            List<SimpleGrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
            );
            System.out.println("Autoridades asignadas: " + authorities);

        return new org.springframework.security.core.userdetails.User(
            usuario.getCorreo(),
            usuario.getContrasena(),
            List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())) // rol con el prefijo
        );
    }
}
