package com.ifpe.paokentin.controllers;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpe.paokentin.domain.entities.Fornada;

@RestController
@RequestMapping("paokentin/fornadas")
@CrossOrigin(origins = "*") 
public class FornadaController extends BaseController<Fornada> {

    @Override
    protected Fornada buscarPorId(int id) throws Exception {
        return fornadaService.buscarPorId(id);
    }

    @GetMapping("")
    public List<FornadaDTO> listarFornadas() throws SQLException {
        List<Fornada> fornadas = fornadaService.listarTodas();
        return fornadas.stream()
            .map(f -> {
                try {
                    return fornadaService.gerarDTO(f);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornada> getFornadaById(@PathVariable int id) throws Exception {
        // Usa o template method do BaseController
        return getById(id);
    }

    @PostMapping("")
    public ResponseEntity<FornadaDTO> cadastrarFornada(@RequestBody Fornada fornada) throws SQLException {
        fornada.setHoraInicio(LocalDateTime.now());
        fornadaService.salvar(fornada);
        FornadaDTO dto = fornadaService.gerarDTO(fornada);
        return ResponseEntity.status(201).body(dto);
    }
}