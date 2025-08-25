package com.ifpe.paokentin.model.entity;

import java.sql.Timestamp;

public class Fornada {

	private int id;
	private int paoId;
	private Timestamp horaInicio;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPaoId() {
		return paoId;
	}
	public void setPaoId(int paoId) {
		this.paoId = paoId;
	}
	public Timestamp getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Timestamp horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	
}
