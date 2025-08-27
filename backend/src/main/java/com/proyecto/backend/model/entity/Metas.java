package com.proyecto.backend.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;;

@Entity
@Table(name = "metas")
public class Metas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "met_id")
    private Long id;

    @Column(name = "met_titulo")
    private String titulo;

    @Column(name = "met_objetivo")
    private double objetivo;

    @Column(name = "met_limite")
    private LocalDate fecha_limite;

    @Column(name = "met_acumulado")
    private double acumulado = 0.0;

    @Column(name = "met_activo")
    private boolean activo = false;

    @ManyToOne
    @JoinColumn(name = "usu_id")
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(double objetivo) {
        this.objetivo = objetivo;
    }

    public LocalDate getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(LocalDate fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public double getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(double acumulado) {
        this.acumulado = acumulado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
}

