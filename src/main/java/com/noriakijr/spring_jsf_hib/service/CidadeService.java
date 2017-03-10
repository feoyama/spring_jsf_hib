package com.noriakijr.spring_jsf_hib.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noriakijr.spring_jsf_hib.model.Cidade;
import com.noriakijr.spring_jsf_hib.persistence.CidadeDAO;
import com.noriakijr.spring_jsf_hib.persistence.PersistenceException;

@Service
public class CidadeService {
	
	@Inject
	private CidadeDAO cidadeDAO;
	
	@Transactional
	public Cidade salvar(Cidade cidade) throws ServiceException {
		try {
			return cidadeDAO.salvar(cidade);
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível salvar", e);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Cidade> list() {	
		return cidadeDAO.list();
	}
	
	@Transactional(readOnly = true)
	public List<Cidade> listaCidadesEstado(Long id) {	
		return cidadeDAO.listaCidadesEstado(id);
	}
	
	@Transactional(readOnly = true)
	public List<Cidade> listaCidadesAutoComplete(Cidade cidade){
		return cidadeDAO.listaCidadesAutoComplete(cidade);
	}

	@Transactional(rollbackFor=Exception.class)
	public void excluir(Cidade cidade) throws ServiceException {
		try {
			cidadeDAO.excluir(cidade.getId());
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível excluir", e);
		}
	}
	@Transactional(rollbackFor=Exception.class)
	public Long excluir(List<Cidade> cidadesSelecionadas) throws ServiceException {
		try {
			Long contador = new Long(0);
			for (Cidade cidade : cidadesSelecionadas) {
				cidadeDAO.excluir(cidade.getId());
				contador++;
			}
			return contador;
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível excluir", e);
		}
		
	}
	
	public Cidade find(Long id){
		return cidadeDAO.find(id);
	}
	
}
