package com.ifpe.paokentin.domain.stategies;

import com.ifpe.paokentin.domain.entities.Fornada;
import com.ifpe.paokentin.domain.entities.Pao;

public interface StatusStrategy {
    String calcularStatus(Fornada fornada, Pao pao);
}