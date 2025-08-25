package com.ifpe.paokentin.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpe.paokentin.model.entity.Fornada;
import com.ifpe.paokentin.model.entity.Pao;
import com.ifpe.paokentin.model.repository.FornadaRepository;

@Service
public class FornadaService {

    @Autowired
    private FornadaRepository fornadaRepository;

    public List<Fornada> listarTodas() throws SQLException {
        return fornadaRepository.listarTodas();
    }

    public Fornada buscarPorId(int id) throws SQLException {
        return fornadaRepository.buscarPorId(id);
    }

    public void salvar(Fornada fornada) throws SQLException {
        fornadaRepository.salvar(fornada);
    }

    public void atualizar(int id, Fornada fornada) throws SQLException {
        fornadaRepository.atualizar(id, fornada);
    }

    // Calcula status da fornada (Em produção ou Pronto)
    public String calcularStatus(Fornada fornada, Pao pao) {
        LocalDateTime inicio = fornada.getHoraInicio();
        LocalDateTime fim = inicio.plusMinutes(pao.getTempoPreparo());
        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(fim)) {
            return "Em produção";
        } else {
            return "Pronto";
        }
    }

    // Calcula tempo restante em minutos
    public long tempoRestante(Fornada fornada, Pao pao) {
        LocalDateTime inicio = fornada.getHoraInicio();
        LocalDateTime fim = inicio.plusMinutes(pao.getTempoPreparo());
        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(fim)) {
            return Duration.between(agora, fim).toMinutes();
        } else {
            return 0;
        }
    }
}