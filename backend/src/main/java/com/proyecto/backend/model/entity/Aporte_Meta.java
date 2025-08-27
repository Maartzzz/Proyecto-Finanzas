package com.proyecto.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "aportes_meta")
public class Aporte_Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aportes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "met_id")
    private Metas metas;

    @ManyToOne
    @JoinColumn(name = "mov_id")
    private Movimiento movimiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    
}
