package com.feoyama.spring_jsf_hib.model.enumerator;

public enum Tipo {
	ENLATADO("Enlatado"),
	REFRIGERADO("Refrigerado"),
	PACOTE("Pacote");
	
	private String descricao;
	
	Tipo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}