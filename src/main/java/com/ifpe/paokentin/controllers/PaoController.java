package com.ifpe.paokentin.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifpe.paokentin.domain.entities.Pao;

@RestController
@RequestMapping("paokentin/paes")
@CrossOrigin(origins = "*") 
public class PaoController extends BaseController<Pao> {

    @Override
    protected Pao buscarPorId(int id) throws Exception {
        return paoService.buscarPorId(id);
    }

    @GetMapping("")
    public List<Pao> listarPao() throws SQLException {
        return paoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pao> detalharPao(@PathVariable int id) throws Exception {
        return getById(id); // Usa o template method do BaseController
    }

    @PostMapping("")
    public ResponseEntity<?> cadastrarPao(@RequestBody Pao pao) throws SQLException {
        try {
            paoService.salvar(pao);
            return ResponseEntity.status(201).body(pao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("")
    public ResponseEntity<?> atualizarPao(@RequestBody Pao pao) throws SQLException {
        try {
            Pao existente = paoService.buscarPorId(pao.getId());
            if (existente == null) {
                return ResponseEntity.notFound().build();
            }
            paoService.atualizar(pao.getId(), pao);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}