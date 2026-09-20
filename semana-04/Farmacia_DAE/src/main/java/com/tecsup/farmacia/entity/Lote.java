package com.tecsup.farmacia.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @Column(nullable = false, unique = true)
    private String numeroLote;

    @Column(nullable = false)
    private LocalDate fechaVencimiento; // RF-FAR-12

    private Integer cantidad;
    private Double costoUnitario;
    private String proveedor;
    private LocalDate fechaIngreso = LocalDate.now();

    public Lote() {
    }

    public Lote(Long id, Medicamento medicamento, String numeroLote, LocalDate fechaVencimiento,
                Integer cantidad, Double costoUnitario, String proveedor, LocalDate fechaIngreso) {
        this.id = id;
        this.medicamento = medicamento;
        this.numeroLote = numeroLote;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.proveedor = proveedor;
        this.fechaIngreso = fechaIngreso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(Double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}