package com.exemplo.obra.service;

import com.exemplo.obra.dto.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MaterialService {

    public ConcretoResponse calcularVolumeConcreto(ConcretoRequest request) {
        // para fazer: incluir o código de calculo do concreto

        return new ConcretoResponse(new BigDecimal(0),9999999,
                "Volume de concreto calculado com sucesso."
        );
    }

    public TijoloResponse calcularQuantidadeTijolos(TijoloRequest request) {
        // para fazer: incluir o código de calculo do consumo de tijolos
        return new TijoloResponse(
                new BigDecimal(0),
                new BigDecimal(0),
                new BigDecimal(0),
                0,
                0,
                "Quantidade de tijolos calculada com sucesso."
        );
    }
}
