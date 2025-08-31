package com.ifpe.paokentin.domain.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpe.paokentin.domain.entities.Pao;
import com.ifpe.paokentin.domain.enums.CorEnum;
import com.ifpe.paokentin.infrastructure.repositories.PaoRepository;

@Service
public class PaoServiceImpl implements PaoService {

    @Autowired
    private PaoRepository paoRepository;

    @Override
    public Pao buscarPorId(int id) throws SQLException {
        return paoRepository.buscarPorId(id);
    }

    @Override
    public List<Pao> listarTodos() throws SQLException {
        return paoRepository.listarTodos();
    }

    @Override
    public void salvar(Pao pao) throws SQLException {
        if (!CorEnumValidator.isValid(pao.getCor())) {
            throw new IllegalArgumentException("Cor inválida para o pão.");
        }
        paoRepository.salvar(pao);
    }

    @Override
    public void atualizar(int id, Pao pao) throws SQLException {
        if (!CorEnumValidator.isValid(pao.getCor())) {
            throw new IllegalArgumentException("Cor inválida para o pão.");
        }
        paoRepository.atualizar(id, pao);
    }
    
    public class CorEnumValidator {
        public static boolean isValid(CorEnum cor) {
            for (CorEnum value : CorEnum.values()) {
                if (value == cor) {
                    return true;
                }
            }
            return false;
        }
    }
}