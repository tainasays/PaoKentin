package com.ifpe.paokentin.domain.stategies;
import com.ifpe.paokentin.domain.entities.Fornada;
import com.ifpe.paokentin.domain.entities.Pao;


public class StatusStrategyFactory {
    public static StatusStrategy getStrategy(Fornada fornada, Pao pao) {
        StatusStrategy dormido = new StatusPaoDormidoStrategy();
        if ("Pão dormido".equals(dormido.calcularStatus(fornada, pao))) {
            return dormido;
        }
        StatusStrategy emProducao = new StatusEmProducaoStrategy();
        if ("Em produção".equals(emProducao.calcularStatus(fornada, pao))) {
            return emProducao;
        }
        StatusStrategy pronto = new StatusProntoStrategy();
        if ("Pronto".equals(pronto.calcularStatus(fornada, pao))) {
            return pronto;
        }
        return pronto;
    }
}