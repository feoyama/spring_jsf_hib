package com.feoyama.spring_jsf_hib.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feoyama.spring_jsf_hib.model.ItemPedido;
import com.feoyama.spring_jsf_hib.model.Produto;
import com.feoyama.spring_jsf_hib.persistence.ItemPedidoDAO;
import com.feoyama.spring_jsf_hib.persistence.PersistenceException;

@Service
public class ItemPedidoService {
	
	@Inject
	private ItemPedidoDAO itemPedidoDAO;
	
	@Transactional
	public ItemPedido salvar(ItemPedido itemPedido) throws ServiceException {
		try {
			return itemPedidoDAO.salvar(itemPedido);
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível salvar", e);
		}
	}
	
	@Transactional(readOnly = true)
	public List<ItemPedido> list() {	
		return itemPedidoDAO.list();
	}

	public List<ItemPedido> listaPedido(Long id) {
		return itemPedidoDAO.listaPedido(id);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Long excluirSelecionados(List<ItemPedido> itensPedidosSelecionados) throws ServiceException {
		try {
			Long contador = new Long(0);
			for (ItemPedido itemPedido : itensPedidosSelecionados) {
				itemPedidoDAO.excluir(itemPedido.getId());
				contador++;
			}
			return contador;
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível excluir", e);
		}
		
	}
	
}
