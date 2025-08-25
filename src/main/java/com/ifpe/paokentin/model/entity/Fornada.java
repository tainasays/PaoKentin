package com.ifpe.paokentin.model.entity;

import java.time.LocalDateTime;

public class Fornada {
    private int id;
    private int paoId;
    private LocalDateTime horaInicio;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPaoId() { return paoId; }
    public void setPaoId(int paoId) { this.paoId = paoId; }

    public LocalDateTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalDateTime horaInicio) { this.horaInicio = horaInicio; }
}