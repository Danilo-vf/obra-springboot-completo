package com.exemplo.obra.service;

import com.exemplo.obra.dto.ArestaRequest;
import com.exemplo.obra.dto.ConcretoRequest;
import com.exemplo.obra.dto.ConcretoResponse;
import com.exemplo.obra.dto.TijoloRequest;
import com.exemplo.obra.dto.TijoloResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MaterialService {

    public ConcretoResponse calcularVolumeConcreto(ConcretoRequest request) {
        BigDecimal volumeTotal = BigDecimal.ZERO;

        for (ArestaRequest aresta : request.getArestas()) {
            // Volume da viga baldrame = Comprimento x Espessura x AlturaViga
            BigDecimal volume = BigDecimal.valueOf(aresta.getComprimento())
                    .multiply(BigDecimal.valueOf(aresta.getEspessura()))
                    .multiply(BigDecimal.valueOf(request.getAlturaViga()));
            volumeTotal = volumeTotal.add(volume);
        }

        return new ConcretoResponse(
                volumeTotal.setScale(4, RoundingMode.HALF_UP),
                request.getArestas().size(),
                "Volume de concreto calculado com sucesso."
        );
    }

    public TijoloResponse calcularQuantidadeTijolos(TijoloRequest request) {
        BigDecimal areaTotalParedes = BigDecimal.ZERO;
        BigDecimal areaAberturas = BigDecimal.ZERO;

        for (ArestaRequest aresta : request.getArestas()) {
            // Área bruta da parede = Comprimento x AlturaParede
            BigDecimal areaParede = BigDecimal.valueOf(aresta.getComprimento())
                    .multiply(BigDecimal.valueOf(aresta.getAlturaParede()));
            areaTotalParedes = areaTotalParedes.add(areaParede);

            // Desconta porta se houver
            if (aresta.isPossuiPorta()) {
                BigDecimal areaPorta = BigDecimal.valueOf(aresta.getLarguraPorta())
                        .multiply(BigDecimal.valueOf(aresta.getAlturaPorta()));
                areaAberturas = areaAberturas.add(areaPorta);
            }

            // Desconta janela se houver
            if (aresta.isPossuiJanela()) {
                BigDecimal areaJanela = BigDecimal.valueOf(aresta.getLarguraJanela())
                        .multiply(BigDecimal.valueOf(aresta.getAlturaJanela()));
                areaAberturas = areaAberturas.add(areaJanela);
            }
        }

        BigDecimal areaLiquidaParedes = areaTotalParedes.subtract(areaAberturas);

        // Área de um tijolo = largura x altura
        BigDecimal areaTijolo = BigDecimal.valueOf(request.getLarguraTijolo())
                .multiply(BigDecimal.valueOf(request.getAlturaTijolo()));

        // Quantidade sem perda
        BigDecimal qtdSemPerda = areaLiquidaParedes
                .divide(areaTijolo, 4, RoundingMode.HALF_UP);

        // Quantidade com perda: aplica o percentual informado (ex: 10.0 = 10%)
        BigDecimal fator = BigDecimal.ONE.add(
                BigDecimal.valueOf(request.getPercentualPerda())
                        .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
        );
        BigDecimal qtdComPerda = qtdSemPerda.multiply(fator);

        return new TijoloResponse(
                areaTotalParedes.setScale(4, RoundingMode.HALF_UP),
                areaLiquidaParedes.setScale(4, RoundingMode.HALF_UP),
                areaAberturas.setScale(4, RoundingMode.HALF_UP),
                qtdSemPerda.setScale(0, RoundingMode.CEILING).intValue(),
                qtdComPerda.setScale(0, RoundingMode.CEILING).intValue(),
                "Quantidade de tijolos calculada com sucesso."
        );
    }
}