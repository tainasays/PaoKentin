package com.ifpe.paokentin.domain.stategies;

import com.ifpe.paokentin.domain.entities.Fornada;
import com.ifpe.paokentin.domain.entities.Pao;
import java.time.LocalDateTime;
import java.time.Duration;


public class StatusPaoDormidoStrategy implements StatusStrategy {
    @Override
    public String calcularStatus(Fornada fornada, Pao pao) {
        LocalDateTime inicio = fornada.getHoraInicio();
        LocalDateTime fim = inicio.plusMinutes(pao.getTempoPreparo());
        LocalDateTime agora = LocalDateTime.now();
        if (!agora.isBefore(fim)) {
            Duration duracao = Duration.between(fim, agora);
            if (duracao.toHours() > 24) {
                return "Pão dormido";
            }
        }
        return null;
    }
}