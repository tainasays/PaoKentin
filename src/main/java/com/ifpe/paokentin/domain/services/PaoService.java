package com.ifpe.paokentin.domain.services;

import java.sql.SQLException;
import java.util.List;
import com.ifpe.paokentin.domain.entities.Pao;


public interface PaoService {
    Pao buscarPorId(int id) throws SQLException;
    List<Pao> listarTodos() throws SQLException;
    void salvar(Pao pao) throws SQLException;
    void atualizar(int id, Pao pao) throws SQLException;
}