package com.ifpe.paokentin.domain.services;

import java.sql.SQLException;
import java.util.List;

import com.ifpe.paokentin.controllers.FornadaDTO;
import com.ifpe.paokentin.domain.entities.Fornada;
import com.ifpe.paokentin.domain.entities.Pao;


public interface FornadaService {
    List<Fornada> listarTodas() throws SQLException;
    Fornada buscarPorId(int id) throws SQLException;
    void salvar(Fornada fornada) throws SQLException;
    void atualizar(int id, Fornada fornada) throws SQLException;
    String calcularStatus(Fornada fornada, Pao pao);
    long calcularTempoRestante(Fornada fornada, Pao pao);
    FornadaDTO gerarDTO(Fornada fornada) throws SQLException;
    
}