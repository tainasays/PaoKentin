package com.ifpe.paokentin.controllers;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpe.paokentin.domain.entities.Fornada;
import com.ifpe.paokentin.domain.entities.Pao;

@RestController
@Controller
@RequestMapping("paokentin/fornadas")
public class FornadaController extends BaseController {

	@GetMapping("")
	public List<FornadaDTO> listarFornadas() throws SQLException {
		List<Fornada> fornadas = fornadaService.listarTodas();
		return fornadas.stream().map(f -> {
			Pao pao = null;
			try {
				pao = paoService.buscarPorId(f.getPaoId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String status = fornadaService.calcularStatus(f, pao);
			long tempoRestante = fornadaService.tempoRestante(f, pao);
			return new FornadaDTO(f.getId(), pao.getDescricao(), f.getHoraInicio(), status, tempoRestante);
		}).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<FornadaDTO> getFornadaById(@PathVariable int id) throws SQLException {
		Fornada fornada = fornadaService.buscarPorId(id);
		if (fornada == null) {
			return ResponseEntity.notFound().build();
		}
		Pao pao = paoService.buscarPorId(fornada.getPaoId());
		String status = fornadaService.calcularStatus(fornada, pao);
		long tempoRestante = fornadaService.tempoRestante(fornada, pao);
		FornadaDTO dto = new FornadaDTO(fornada.getId(), pao.getDescricao(), fornada.getHoraInicio(), status,
				tempoRestante);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("")
	public ResponseEntity<FornadaDTO> cadastrarFornada(@RequestBody Fornada fornada) throws SQLException {
		fornada.setHoraInicio(LocalDateTime.now());
		fornadaService.salvar(fornada);

		Pao pao = paoService.buscarPorId(fornada.getPaoId());
		String status = fornadaService.calcularStatus(fornada, pao);
		long tempoRestante = fornadaService.tempoRestante(fornada, pao);

		FornadaDTO dto = new FornadaDTO(fornada.getId(), pao.getDescricao(), fornada.getHoraInicio(), status,
				tempoRestante);
		return ResponseEntity.status(201).body(dto);
	}
}