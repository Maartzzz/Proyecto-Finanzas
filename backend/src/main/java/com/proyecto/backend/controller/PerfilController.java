package com.proyecto.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend.model.entity.Usuario;
import com.proyecto.backend.model.repository.ITipoRepository;
import com.proyecto.backend.model.service.ICategoriaService;
import com.proyecto.backend.model.service.IMetaService;
import com.proyecto.backend.model.service.IMovimientoService;
import com.proyecto.backend.model.service.IUsuarioService;

@RestController
@RequestMapping("api/perfil")
@CrossOrigin(origins = "http://localhost:4200")
public class PerfilController {

    @Autowired private IUsuarioService usuarioService;
    @Autowired private ICategoriaService categoriaService;
    @Autowired private ITipoRepository tipoRepository;
    @Autowired private IMetaService metaService;
    @Autowired private IMovimientoService movimientoService;

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> obtenerPerfil(Authentication authentication) {
        String correo = authentication.getName();
        Usuario usuario = usuarioService.buscarCorreo(correo);

        var perfil = new java.util.HashMap<String, Object>();
        perfil.put("usuario", usuario);
        perfil.put("categorias", categoriaService.listarPorUsuario(usuario.getId()));
        perfil.put("tipos", tipoRepository.findAll());
        perfil.put("movimientos", movimientoService.listarPorUsuario(usuario.getId()));
        perfil.put("metas", metaService.listarPorUsuario(usuario.getId()));
        return ResponseEntity.ok(perfil);
    }
    
}
