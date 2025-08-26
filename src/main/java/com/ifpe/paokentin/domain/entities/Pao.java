package com.ifpe.paokentin.domain.entities;

import com.ifpe.paokentin.domain.enums.CorEnum;

public class Pao {

	private int id;
	private String nome;
	private String descricao;
	private long tempoPreparo;
	private CorEnum cor;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public long getTempoPreparo() {
		return tempoPreparo;
	}
	public void setTempoPreparo(long tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

	public CorEnum getCor() {
        return cor;
    }

    public void setCor(CorEnum cor) {
        this.cor = cor;
    }
	
	
	
}
