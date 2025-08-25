package com.ifpe.paokentin.controller;


import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifpe.paokentin.model.entity.Pao;
import com.ifpe.paokentin.services.PaoService;

@RestController
@RequestMapping("paokentin/pao")
public class PaoController {

    @Autowired
    private PaoService paoService;

    // Listagem de pães
    @GetMapping("/listar")
    public List<Pao> listarPao() throws SQLException {
        return paoService.listarTodos();
    }

    // Detalhes de um pão
    @GetMapping("/detalhes/{id}")
    public Pao detalhePao(@PathVariable int id) throws SQLException {
        return paoService.buscarPorId(id);
    }
    
    // Cadastro de pão
    @PostMapping("/cadastrar")
    public ResponseEntity<Pao> cadastrarPao(@RequestBody Pao pao) throws SQLException {
        paoService.salvar(pao);
        return ResponseEntity.status(201).body(pao);
    }
    
    
    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizarPao(@RequestBody Pao pao) throws SQLException {
        Pao existente = paoService.buscarPorId(pao.getId());
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        paoService.atualizar(pao.getId(), pao);
        return ResponseEntity.noContent().build();
    }
}