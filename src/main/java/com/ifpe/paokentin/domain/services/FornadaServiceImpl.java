package com.ifpe.paokentin.domain.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpe.paokentin.controllers.FornadaDTO;
import com.ifpe.paokentin.domain.entities.Fornada;
import com.ifpe.paokentin.domain.entities.Pao;
import com.ifpe.paokentin.domain.stategies.StatusStrategy;
import com.ifpe.paokentin.domain.stategies.StatusStrategyFactory;
import com.ifpe.paokentin.infrastructure.repositories.FornadaRepository;


@Service
public class FornadaServiceImpl implements FornadaService {

    @Autowired
    private FornadaRepository fornadaRepository;
    @Autowired
    private PaoService paoService;
    
    @Override
    public List<Fornada> listarTodas() throws SQLException {
        return fornadaRepository.listarTodas();
    }

    @Override
    public Fornada buscarPorId(int id) throws SQLException {
        return fornadaRepository.buscarPorId(id);
    }

    @Override
    public void salvar(Fornada fornada) throws SQLException {
        fornadaRepository.salvar(fornada);
    }

    @Override
    public void atualizar(int id, Fornada fornada) throws SQLException {
        fornadaRepository.atualizar(id, fornada);
    }

    @Override
    public String calcularStatus(Fornada fornada, Pao pao) {
        StatusStrategy strategy = StatusStrategyFactory.getStrategy(fornada, pao);
        return strategy.calcularStatus(fornada, pao);
    }

    @Override
    public long calcularTempoRestante(Fornada fornada, Pao pao) {
        LocalDateTime inicio = fornada.getHoraInicio();
        LocalDateTime fim = inicio.plusMinutes(pao.getTempoPreparo());
        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(fim)) {
            return Duration.between(agora, fim).toMinutes();
        } else {
            return 0;
        }
    }
    
    @Override
    public FornadaDTO gerarDTO(Fornada fornada) throws SQLException {
        Pao pao = paoService.buscarPorId(fornada.getPaoId());
        String status = calcularStatus(fornada, pao);
        long tempoRestante = calcularTempoRestante(fornada, pao);
        return new FornadaDTO(fornada.getId(), pao != null ? pao.getDescricao() : "", fornada.getHoraInicio(), status, tempoRestante);
    }
}