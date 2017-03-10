package com.feoyama.spring_jsf_hib.persistence;

import org.springframework.stereotype.Repository;

import com.feoyama.spring_jsf_hib.model.Pedido;

@Repository
public class PedidoDAO   extends GenericDAO<Pedido, Long>  {

	public PedidoDAO() {
		super(Pedido.class);
	}
	
}
