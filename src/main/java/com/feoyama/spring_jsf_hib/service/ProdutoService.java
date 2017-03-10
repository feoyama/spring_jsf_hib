package com.feoyama.spring_jsf_hib.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feoyama.spring_jsf_hib.model.Produto;
import com.feoyama.spring_jsf_hib.persistence.PersistenceException;
import com.feoyama.spring_jsf_hib.persistence.ProdutoDAO;

@Service
public class ProdutoService {
	
	@Inject
	private ProdutoDAO produtoDAO;

	@Transactional
	public Produto salvar(Produto produto) throws ServiceException {
		try {
			if(produto.isNovo()){
				produto.setDataCadastro(new Date());
			}
			return produtoDAO.salvar(produto);
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível salvar", e);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Produto> list() {	
		return produtoDAO.list();
	}
	
	@Transactional(readOnly=true)
	public List<Produto> list(Produto produto){
		return produtoDAO.list(produto);
	}

	@Transactional(rollbackFor=Exception.class)
	public void excluir(Produto produto) throws ServiceException {
		try {
			produtoDAO.excluir(produto.getId());
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ServiceException("Não foi possível excluir", e);
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public Long excluir(List<Produto> produtosSelecionados) throws ServiceException {
		try {
			Long contador = new Long(0);
			for (Produto produto : produtosSelecionados) {
				produtoDAO.excluir(produto.getId());
				contador++;
			}
			return contador;
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível excluir", e);
		}
		
	}

	public Produto find(long id) {
		return produtoDAO.find(id);
	}
	
	
	
	

	

}
