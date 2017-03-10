package com.feoyama.spring_jsf_hib.model.enumerator;

public enum Status {
	ORCAMENTO("Or�amento"),
	EMITIDO("Emitido");
	
	private String descricao;
	
	Status(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
}
