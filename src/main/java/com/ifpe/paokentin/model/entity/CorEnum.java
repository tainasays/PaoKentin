package com.ifpe.paokentin.model.entity;

public enum CorEnum {
	AMARELO("#FFFF00"), 
	AZUL("#0000FF"),
	VERDE("#008000"), 
	VERMELHO("#FF0000");

	private final String hex;

	CorEnum(String hex) {
        this.hex = hex;
    }

	public String getHex() {
		return hex;
	}
	
	
}
