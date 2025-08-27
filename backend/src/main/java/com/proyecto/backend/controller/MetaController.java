package com.proyecto.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend.model.entity.Metas;
import com.proyecto.backend.model.entity.Usuario;
import com.proyecto.backend.model.repository.IMetaRepository;
import com.proyecto.backend.model.service.IMetaService;
import com.proyecto.backend.model.service.IUsuarioService;

@RestController
@RequestMapping("/api/metas")
@CrossOrigin(origins = "http://localhost:4200")
public class MetaController {

    @Autowired private IMetaRepository metaRepository;
    @Autowired private IUsuarioService usuarioService;
    @Autowired
    private IMetaService metaService;

    @PostMapping
    public ResponseEntity<?> crearMeta(@RequestBody Metas meta) {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarCorreo(correo);
        meta.setUsuario(usuario);

        if (meta.isActivo()) {
            metaService.desactivarMetas(usuario.getId());
        }
        meta.setAcumulado(0);
        meta.setActivo(false);
        return ResponseEntity.ok(metaService.guardar(meta));
    }

    @GetMapping
    public ResponseEntity<List<Metas>> listarMisMetas() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarCorreo(correo);
        return ResponseEntity.ok(metaRepository.findByUsuarioId(usuario.getId()));
    }

    @PutMapping("/activar/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> activarMeta(@PathVariable Long id, Authentication auth) {
        String correo = auth.getName();
        Usuario usuario = usuarioService.buscarCorreo(correo);

        metaService.desactivarMetas(usuario.getId());

        Metas meta = metaService.buscarPorId(id).orElseThrow(() ->
        new RuntimeException("Meta no encontrada con ID: " +id));
        meta.setActivo(true);
        return ResponseEntity.ok(metaService.guardar(meta));
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> eliminarMeta(@PathVariable Long id, Authentication auth) {
    try {
        metaService.eliminar(id);
        return ResponseEntity.ok().build();
    } catch (DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("No se puede eliminar esta meta porque tiene aportes asociados.");
        }
    }
}
