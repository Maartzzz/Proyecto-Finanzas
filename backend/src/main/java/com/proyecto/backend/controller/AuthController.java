package com.proyecto.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;

import com.proyecto.backend.model.entity.Usuario;
import com.proyecto.backend.model.service.IUsuarioService;
import com.proyecto.backend.security.dto.LoginRequest;
import com.proyecto.backend.security.jwt.JwtUtil;
import com.proyecto.backend.security.dto.JwtResponse;
import com.proyecto.backend.model.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena())
            );
            Usuario usuario = usuarioService.buscarCorreo(request.getCorreo());
            UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getCorreo());
            String token = jwtUtil.generateToken(usuario.getCorreo(), userDetails.getAuthorities());
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }
}
