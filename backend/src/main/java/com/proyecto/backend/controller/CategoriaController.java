package com.proyecto.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend.model.entity.Categoria;
import com.proyecto.backend.model.entity.Tipo;
import com.proyecto.backend.model.entity.Usuario;
import com.proyecto.backend.model.repository.ITipoRepository;
import com.proyecto.backend.model.service.ICategoriaService;
import com.proyecto.backend.model.service.IUsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ITipoRepository tipoRepository;

    @PostMapping()
    public ResponseEntity<?> crearCategoria(@RequestBody Map<String, Object> datos) {
        try {
            String nombre = (String) datos.get("nombre");
            String descripcion = (String) datos.get("descripcion");
            Long tipoId = Long.valueOf(datos.get("tipoId").toString());

            String correo = obtenerCorreoDesdeToken();
            Usuario usuario = usuarioService.buscarCorreo(correo);
            Tipo tipo = tipoRepository.findById(tipoId).orElseThrow();

            Categoria cat = new Categoria();
            cat.setNombre(nombre);
            cat.setDescripcion(descripcion);
            cat.setTipo(tipo);
            cat.setUsuario(usuario);

            return ResponseEntity.ok(categoriaService.guardarCategoria(cat));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar categoria: " + e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<Categoria>> Listar() {
        String correo = obtenerCorreoDesdeToken();
        Usuario usuario = usuarioService.buscarCorreo(correo);
        return ResponseEntity.ok(categoriaService.listarPorUsuario(usuario.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.ok().build();
    }

    private String obtenerCorreoDesdeToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody Map<String, Object> datos) {
        try {
            String nombre = (String) datos.get("nombre");
            String descripcion = (String) datos.get("descripcion");
            Long tipoId = Long.valueOf(datos.get("tipoId").toString());

            Categoria cat = categoriaService.buscarPorId(id).orElse(null);
            if (cat == null) return ResponseEntity.notFound().build();

            Tipo tipo = tipoRepository.findById(tipoId).orElseThrow();
            cat.setNombre(nombre);
            cat.setDescripcion(descripcion);
            cat.setTipo(tipo);

            return ResponseEntity.ok(categoriaService.guardarCategoria(cat));
        }catch (Exception e) {
                return ResponseEntity.badRequest().body("Error al actualizar categoria: " + e.getMessage());
        }
    }

}

