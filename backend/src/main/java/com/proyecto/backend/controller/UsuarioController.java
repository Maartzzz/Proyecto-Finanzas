package com.proyecto.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend.model.entity.Usuario;
import com.proyecto.backend.model.service.IUsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping()
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }

    @PostMapping()
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> eliminarUsuarios(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
        usuarioService.eliminarUsuario(id);
        response.put("mensaje", "Usuario eliminado");
        return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mensaje", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
    Map<String, String> response = new HashMap<>();
        try {
            usuarioService.actualizarUsuario(id, usuario);
            response.put("mensaje", "Usuario actualizado correctamente");
            return ResponseEntity.ok(response);
            } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
