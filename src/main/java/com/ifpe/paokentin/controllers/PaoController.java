package com.ifpe.paokentin.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifpe.paokentin.domain.entities.Pao;

@RestController
@RequestMapping("paokentin/paes")
public class PaoController extends BaseController {

	@GetMapping("")
	public List<Pao> listarPao() throws SQLException {
		return paoService.listarTodos();
	}

	@GetMapping("/{id}")
	public Pao detalhePao(@PathVariable int id) throws SQLException {
		return paoService.buscarPorId(id);
	}

	@PostMapping("")
	public ResponseEntity<Pao> cadastrarPao(@RequestBody Pao pao) throws SQLException {
		paoService.salvar(pao);
		return ResponseEntity.status(201).body(pao);
	}

	@PutMapping("")
	public ResponseEntity<Void> atualizarPao(@RequestBody Pao pao) throws SQLException {
		Pao existente = paoService.buscarPorId(pao.getId());
		if (existente == null) {
			return ResponseEntity.notFound().build();
		}
		paoService.atualizar(pao.getId(), pao);
		return ResponseEntity.noContent().build();
	}
}