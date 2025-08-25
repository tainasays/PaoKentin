package com.ifpe.paokentin.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpe.paokentin.model.entity.Pao;
import com.ifpe.paokentin.model.repository.PaoRepository;

@Service
public class PaoService {
	
	@Autowired
	private final PaoRepository repository;

	
	public PaoService(PaoRepository repository) {
		this.repository = repository;
	}

	public void salvar(Pao pao) throws SQLException {
		repository.salvar(pao);
	}

	public Pao buscarPorId(int id) throws SQLException {
		return repository.buscarPorId(id);
	}

	public List<Pao> listarTodos() throws SQLException {
		return repository.listarTodos();
	}

	public void atualizar(int id, Pao pao) throws SQLException {
		repository.atualizar(id, pao);
	}
}