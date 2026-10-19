package com.tecsup.farmacia.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombreComercial;

    private String concentracion;
    private String formaFarmaceutica;
    private boolean estado = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratorio_id")
    private Laboratorio laboratorio;

    @JsonIgnore
    @OneToMany(mappedBy = "medicamento", cascade = CascadeType.ALL)
    private List<Presentacion> presentaciones;

    @JsonIgnore
    @OneToMany(mappedBy = "medicamento", cascade = CascadeType.ALL)
    private List<Lote> lotes;

    public Medicamento() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }

    public String getConcentracion() { return concentracion; }
    public void setConcentracion(String concentracion) { this.concentracion = concentracion; }

    public String getFormaFarmaceutica() { return formaFarmaceutica; }
    public void setFormaFarmaceutica(String formaFarmaceutica) { this.formaFarmaceutica = formaFarmaceutica; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Laboratorio getLaboratorio() { return laboratorio; }
    public void setLaboratorio(Laboratorio laboratorio) { this.laboratorio = laboratorio; }

    public List<Presentacion> getPresentaciones() { return presentaciones; }
    public void setPresentaciones(List<Presentacion> presentaciones) { this.presentaciones = presentaciones; }

    public List<Lote> getLotes() { return lotes; }
    public void setLotes(List<Lote> lotes) { this.lotes = lotes; }
}