package com.exemplo.obra.bean;

import com.exemplo.obra.model.Orcamento;
import com.exemplo.obra.repository.OrcamentoRepository;
import com.exemplo.obra.service.MaterialService;
import com.exemplo.obra.dto.ConcretoRequest;
import com.exemplo.obra.dto.TijoloRequest;
import com.exemplo.obra.dto.ArestaRequest;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class OrcamentoBean implements Serializable {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    private String nomeCliente;
    private Double alturaViga = 0.3;
    private Double larguraViga = 0.2;
    private Double comprimentoParede = 5.0;
    private Double alturaParede = 2.8;
    private Double larguraTijolo = 0.14;
    private Double alturaTijolo = 0.09;
    private Double comprimentoTijolo = 0.19;

    private Double volumeConcreto;
    private Integer quantidadeTijolos;
    private String numeroOrcamento;
    private String mensagem;

    public void calcular() {
        try {
            List<ArestaRequest> arestas = new ArrayList<>();
            ArestaRequest aresta = new ArestaRequest();
            aresta.setComprimento(comprimentoParede);
            aresta.setLargura(larguraViga);
            arestas.add(aresta);

            ConcretoRequest concretoRequest = new ConcretoRequest();
            concretoRequest.setArestas(arestas);
            concretoRequest.setAltura(alturaViga);

            TijoloRequest tijoloRequest = new TijoloRequest();
            tijoloRequest.setArestas(arestas);
            tijoloRequest.setAltura(alturaParede);
            tijoloRequest.setLargura(larguraTijolo);
            tijoloRequest.setComprimento(comprimentoTijolo);

            volumeConcreto = materialService.calcularVolumeConcreto(concretoRequest).getVolumeTotal().doubleValue();
            quantidadeTijolos = materialService.calcularQuantidadeTijolos(tijoloRequest).getQtdComPerda();

            Orcamento orcamento = new Orcamento();
            orcamento.setNomeCliente(nomeCliente);
            orcamento.setVolumeConcreto(volumeConcreto);
            orcamento.setQuantidadeTijolos(quantidadeTijolos);
            orcamentoRepository.save(orcamento);

            numeroOrcamento = orcamento.getNumeroOrcamento();
            mensagem = "Orçamento gerado com sucesso!";
        } catch (Exception e) {
            mensagem = "Erro ao calcular: " + e.getMessage();
        }
    }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public Double getAlturaViga() { return alturaViga; }
    public void setAlturaViga(Double alturaViga) { this.alturaViga = alturaViga; }
    public Double getLarguraViga() { return larguraViga; }
    public void setLarguraViga(Double larguraViga) { this.larguraViga = larguraViga; }
    public Double getComprimentoParede() { return comprimentoParede; }
    public void setComprimentoParede(Double comprimentoParede) { this.comprimentoParede = comprimentoParede; }
    public Double getAlturaParede() { return alturaParede; }
    public void setAlturaParede(Double alturaParede) { this.alturaParede = alturaParede; }
    public Double getLarguraTijolo() { return larguraTijolo; }
    public void setLarguraTijolo(Double larguraTijolo) { this.larguraTijolo = larguraTijolo; }
    public Double getAlturaTijolo() { return alturaTijolo; }
    public void setAlturaTijolo(Double alturaTijolo) { this.alturaTijolo = alturaTijolo; }
    public Double getComprimentoTijolo() { return comprimentoTijolo; }
    public void setComprimentoTijolo(Double comprimentoTijolo) { this.comprimentoTijolo = comprimentoTijolo; }
    public Double getVolumeConcreto() { return volumeConcreto; }
    public Integer getQuantidadeTijolos() { return quantidadeTijolos; }
    public String getNumeroOrcamento() { return numeroOrcamento; }
    public String getMensagem() { return mensagem; }
}
