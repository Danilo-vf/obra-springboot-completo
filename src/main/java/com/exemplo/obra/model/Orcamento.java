package com.exemplo.obra.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orcamento")
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCliente;
    private String numeroOrcamento;
    private Double volumeConcreto;
    private Integer quantidadeTijolos;
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.numeroOrcamento = "ORC-" + System.currentTimeMillis();
    }

    public Long getId() { return id; }
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public String getNumeroOrcamento() { return numeroOrcamento; }
    public void setNumeroOrcamento(String numeroOrcamento) { this.numeroOrcamento = numeroOrcamento; }
    public Double getVolumeConcreto() { return volumeConcreto; }
    public void setVolumeConcreto(Double volumeConcreto) { this.volumeConcreto = volumeConcreto; }
    public Integer getQuantidadeTijolos() { return quantidadeTijolos; }
    public void setQuantidadeTijolos(Integer quantidadeTijolos) { this.quantidadeTijolos = quantidadeTijolos; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
