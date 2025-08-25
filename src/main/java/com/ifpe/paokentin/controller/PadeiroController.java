package com.ifpe.paokentin.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifpe.paokentin.model.entity.Fornada;
import com.ifpe.paokentin.model.entity.Pao;
import com.ifpe.paokentin.services.FornadaService;
import com.ifpe.paokentin.services.PaoService;

@RestController
@RequestMapping("paokentin/padeiro")
public class PadeiroController {

    @Autowired
    private FornadaService fornadaService;

    @Autowired
    private PaoService paoService;

    // Cadastro de fornada (padeiro clica no botão do pão)
    @PostMapping("/fornada/cadastrar")
    public ResponseEntity<FornadaDTO> cadastrarFornada(@RequestBody Fornada fornada) throws SQLException {
        fornada.setHoraInicio(LocalDateTime.now());
        fornadaService.salvar(fornada);

        Pao pao = paoService.buscarPorId(fornada.getPaoId());
        String status = fornadaService.calcularStatus(fornada, pao);
        long tempoRestante = fornadaService.tempoRestante(fornada, pao);

        FornadaDTO dto = new FornadaDTO(fornada.getId(), pao.getDescricao(), fornada.getHoraInicio(), status, tempoRestante);
        return ResponseEntity.status(201).body(dto);
    }

    // Listagem das fornadas para recuperação da informação e cálculo do status
    @GetMapping("/fornada/listar")
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

    // Buscar fornada por id
    @GetMapping("/fornada/detalhes/{id}")
    public ResponseEntity<FornadaDTO> getFornadaById(@PathVariable int id) throws SQLException {
        Fornada fornada = fornadaService.buscarPorId(id);
        if (fornada == null) {
            return ResponseEntity.notFound().build();
        }
        Pao pao = paoService.buscarPorId(fornada.getPaoId());
        String status = fornadaService.calcularStatus(fornada, pao);
        long tempoRestante = fornadaService.tempoRestante(fornada, pao);
        FornadaDTO dto = new FornadaDTO(fornada.getId(), pao.getDescricao(), fornada.getHoraInicio(), status, tempoRestante);
        return ResponseEntity.ok(dto);
    }

    // DTO para resposta
    public static class FornadaDTO {
        private int id;
        private String nomePao;
        private LocalDateTime horaInicio;
        private String status;
        private long tempoRestante;

        public FornadaDTO(int id, String nomePao, LocalDateTime horaInicio, String status, long tempoRestante) {
            this.id = id;
            this.nomePao = nomePao;
            this.horaInicio = horaInicio;
            this.status = status;
            this.tempoRestante = tempoRestante;
        }
        public int getId() { return id; }
        public String getNomePao() { return nomePao; }
        public LocalDateTime getHoraInicio() { return horaInicio; }
        public String getStatus() { return status; }
        public long getTempoRestante() { return tempoRestante; }
    }
}