package com.feoyama.spring_jsf_hib.persistence;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.feoyama.spring_jsf_hib.model.Cidade;
import com.feoyama.spring_jsf_hib.model.ItemPedido;

@Repository
public class ItemPedidoDAO  extends GenericDAO<ItemPedido, Long>  {
	
	public ItemPedidoDAO() {
		super(ItemPedido.class);
	}

	public List<ItemPedido> listaPedido(Long id) {
		Query query = em.createQuery("select ip from ItemPedido ip join ip.pedido p where p.id=:id", ItemPedido.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
}
