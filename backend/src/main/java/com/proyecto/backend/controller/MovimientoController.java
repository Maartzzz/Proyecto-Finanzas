package com.proyecto.backend.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend.model.entity.Aporte_Meta;
import com.proyecto.backend.model.entity.Categoria;
import com.proyecto.backend.model.entity.Metas;
import com.proyecto.backend.model.entity.Movimiento;
import com.proyecto.backend.model.entity.Usuario;
import com.proyecto.backend.model.repository.IAporteMetaRepository;
import com.proyecto.backend.model.repository.ICategoriaRepository;
import com.proyecto.backend.model.repository.IMetaRepository;
import com.proyecto.backend.model.service.ICategoriaService;
import com.proyecto.backend.model.service.IMovimientoService;
import com.proyecto.backend.model.service.IUsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = "http://localhost:4200")
public class MovimientoController {

    @Autowired
    private IMovimientoService movimientoService;
    @Autowired
    private ICategoriaRepository categoriaRepository;
    @Autowired 
    private IUsuarioService usuarioService;
    @Autowired
    private ICategoriaService categoriaService;
    @Autowired
    private IMetaRepository metaRepository;
    @Autowired 
    private IAporteMetaRepository aporteMetaRepository;

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> crearMovimiento(@RequestBody Map<String, Object> datos, Authentication auth) {
    try {
        String correo = auth.getName();
        Usuario usuario = usuarioService.buscarCorreo(correo);

        Long categoriaId = Long.valueOf(datos.get("categoriaId").toString());
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Movimiento mov = new Movimiento();
        mov.setMonto(Double.valueOf(datos.get("monto").toString()));
        mov.setDescripcion((String) datos.get("descripcion"));
        mov.setCategoria(categoria);
        mov.setUsuario(usuario);

        // Guardar movimiento
        Movimiento guardado = movimientoService.guardarMovimiento(mov);

        // Si es ahorro, buscar meta activa
        if ("Ahorro".equalsIgnoreCase(categoria.getTipo().getNombre())) {
            Optional<Metas> metaActivo = metaRepository.findByUsuarioIdAndActivoTrue(usuario.getId());

            if (metaActivo.isPresent()) {
                Metas meta = metaActivo.get();

                // Actualizar acumulado
                meta.setAcumulado(meta.getAcumulado() + guardado.getMonto());
                metaRepository.save(meta);

                // Guardar aporte
                Aporte_Meta aporte = new Aporte_Meta();
                aporte.setMetas(meta);
                aporte.setMovimiento(guardado);
                aporteMetaRepository.save(aporte);
            }
        }
        return ResponseEntity.ok(guardado);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error al guardar movimiento: " + e.getMessage());
    }
}
    
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Movimiento>> listarMovimientos(Authentication auth) {
        String correo = auth.getName();
        Usuario usuario = usuarioService.buscarCorreo(correo);
        return ResponseEntity.ok(movimientoService.listarPorUsuario(usuario.getId()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        movimientoService.eliminar(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> actualizarMovimiento(@PathVariable Long id, @RequestBody Map<String, Object> datos) {
        try {
            Optional<Movimiento> optMov = movimientoService.buscarPorId(id);
            if (optMov.isEmpty()) return ResponseEntity.notFound().build();

            Movimiento movimiento = optMov.get();

            // Actualizar campos
            movimiento.setMonto(Double.valueOf(datos.get("monto").toString()));
            movimiento.setDescripcion((String) datos.get("descripcion"));

            Long categoriaId = Long.valueOf(datos.get("categoriaId").toString());
            Categoria categoria = categoriaService.buscarPorId(categoriaId).orElseThrow(() ->
                new RuntimeException("Categoría no encontrada"));
            movimiento.setCategoria(categoria);

            // Guardar actualización
            return ResponseEntity.ok(movimientoService.guardarMovimiento(movimiento));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar movimiento: " + e.getMessage());
        }
    }

    @GetMapping("/saldo")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Double> obtenerSaldo(Authentication auth) {
        String correo = auth.getName();
        Usuario usuario = usuarioService.buscarCorreo(correo);

        List<Movimiento> movimientos = movimientoService.listarPorUsuario(usuario.getId());

        double saldo = movimientos.stream()
                .mapToDouble(mov -> {
                    String tipoNombre = mov.getCategoria().getTipo().getNombre();
                    return tipoNombre.equalsIgnoreCase("Ingreso") ? mov.getMonto()
                        : tipoNombre.equalsIgnoreCase("Salida") ? -mov.getMonto()
                        : 0.0; // Ahorro u otros no afectan directamente el saldo
                })
                .sum();
        return ResponseEntity.ok(saldo);
    }
}
