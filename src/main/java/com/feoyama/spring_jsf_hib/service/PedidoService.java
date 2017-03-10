package com.feoyama.spring_jsf_hib.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hamcrest.core.IsNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feoyama.spring_jsf_hib.model.Cidade;
import com.feoyama.spring_jsf_hib.model.ItemPedido;
import com.feoyama.spring_jsf_hib.model.Pedido;
import com.feoyama.spring_jsf_hib.persistence.ItemPedidoDAO;
import com.feoyama.spring_jsf_hib.persistence.PedidoDAO;
import com.feoyama.spring_jsf_hib.persistence.PersistenceException;

@Service
public class PedidoService {
	@Inject
	private PedidoDAO pedidoDAO;
	
	@Inject
	private ItemPedidoDAO itemPedidoDAO;
	
	@Transactional
	public Pedido salvar(Pedido pedido) throws ServiceException {
		try {
			if (pedido.isNovo()){
				pedido.setDataPedido(new Date());
			}
			return pedidoDAO.salvar(pedido);
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível salvar", e);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Pedido> list() {	
		return pedidoDAO.list();
	}


	public Pedido find(long id) {
		return pedidoDAO.find(id);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Long excluirSelecionados(List<Pedido> pedidosSelecionados) throws ServiceException{
		try {
			Long contador = new Long(0);
			for (Pedido pedido : pedidosSelecionados) {
				for (ItemPedido itemPedido : itemPedidoDAO.listaPedido(pedido.getId())) {
					itemPedidoDAO.excluir(itemPedido.getId());
				}
				pedidoDAO.excluir(pedido.getId());
				contador++;
			}
			return contador;
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível excluir", e);
		}
	}
}
