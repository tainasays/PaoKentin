package com.ifpe.paokentin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.ifpe.paokentin.domain.services.FornadaService;
import com.ifpe.paokentin.domain.services.PaoService;

public abstract class BaseController<T> {

	@Autowired
	protected FornadaService fornadaService;
	@Autowired
	protected PaoService paoService;

	protected abstract T buscarPorId(int id) throws Exception;

	public ResponseEntity<T> getById(int id) throws Exception {
		T entidade = buscarPorId(id);
		if (entidade == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(entidade);
	}
}
