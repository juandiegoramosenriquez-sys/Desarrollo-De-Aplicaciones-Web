package com.tecsup.farmacia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditorias")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;
    private LocalDateTime fechaHora;
    private String operacion; // CREAR, EDITAR, ELIMINAR
    private String entidadAfectada;
    private Long registroId;

    public Auditoria() {}

    public Auditoria(String usuario, String operacion, String entidadAfectada, Long registroId) {
        this.usuario = usuario;
        this.fechaHora = LocalDateTime.now();
        this.operacion = operacion;
        this.entidadAfectada = entidadAfectada;
        this.registroId = registroId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getOperacion() { return operacion; }
    public void setOperacion(String operacion) { this.operacion = operacion; }
    public String getEntidadAfectada() { return entidadAfectada; }
    public void setEntidadAfectada(String entidadAfectada) { this.entidadAfectada = entidadAfectada; }
    public Long getRegistroId() { return registroId; }
    public void setRegistroId(Long registroId) { this.registroId = registroId; }
}