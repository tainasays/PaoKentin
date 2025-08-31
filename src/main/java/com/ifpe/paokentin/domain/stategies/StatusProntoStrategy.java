package com.ifpe.paokentin.domain.stategies;

import com.ifpe.paokentin.domain.entities.Fornada;
import com.ifpe.paokentin.domain.entities.Pao;
import java.time.LocalDateTime;

public class StatusProntoStrategy implements StatusStrategy {
    @Override
    public String calcularStatus(Fornada fornada, Pao pao) {
        LocalDateTime inicio = fornada.getHoraInicio();
        LocalDateTime fim = inicio.plusMinutes(pao.getTempoPreparo());
        LocalDateTime agora = LocalDateTime.now();
        if (!agora.isBefore(fim) && !isPaoDormido(fornada, pao)) {
            return "Pronto";
        }
        return null;
    }

    private boolean isPaoDormido(Fornada fornada, Pao pao) {
        LocalDateTime inicio = fornada.getHoraInicio();
        LocalDateTime fim = inicio.plusMinutes(pao.getTempoPreparo());
        LocalDateTime agora = LocalDateTime.now();
        return !agora.isBefore(fim) && java.time.Duration.between(fim, agora).toHours() > 24;
    }
}