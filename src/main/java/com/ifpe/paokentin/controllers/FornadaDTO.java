package com.ifpe.paokentin.controllers;

import java.time.LocalDateTime;

public class FornadaDTO {
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