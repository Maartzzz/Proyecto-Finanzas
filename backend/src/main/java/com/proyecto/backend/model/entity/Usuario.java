package com.proyecto.backend.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Long id;

    @Column(name = "usu_nombre")
    private String nombre;

    @Column(name = "usu_apellido")
    private String apellido;

    @Column(name = "usu_correo")
    private String correo;

    @Column(name = "usu_telefono")
    private String telefono;

    @Column(name = "usu_contrasena")
    private String contrasena;

    @Column(name = "usu_fechareg")
    private LocalDate fechaRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDate.now();
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String correo, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
}
