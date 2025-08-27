package com.proyecto.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend.model.entity.Tipo;
import com.proyecto.backend.model.repository.ITipoRepository;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/tipos")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoController {

    @Autowired
    private ITipoRepository tipoRepository;

    @GetMapping
    public List<Tipo> listar() {
        return tipoRepository.findAll();
    }
    
}
