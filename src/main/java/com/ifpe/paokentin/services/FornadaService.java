package com.ifpe.paokentin.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.ifpe.paokentin.model.entity.Fornada;
import com.ifpe.paokentin.model.entity.Pao;

public class FornadaService {

	
	
	
	public String calcularStatus(Fornada fornada, Pao pao) {
        LocalDateTime entrada = fornada.getHoraInicio().toLocalDateTime();
        LocalDateTime horaPronto = entrada.plusMinutes(pao.getTempoPreparo());
        LocalDateTime agora = LocalDateTime.now();

        if (agora.isBefore(horaPronto)) {
            long minutosRestantes = ChronoUnit.MINUTES.between(agora, horaPronto);
            return "Em produção - faltam " + minutosRestantes + " min";
        } else {
            return "Pronto!";
        }
    }
}
