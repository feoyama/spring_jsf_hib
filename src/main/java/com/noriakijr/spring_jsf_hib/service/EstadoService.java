package com.noriakijr.spring_jsf_hib.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noriakijr.spring_jsf_hib.model.Estado;
import com.noriakijr.spring_jsf_hib.persistence.EstadoDAO;
import com.noriakijr.spring_jsf_hib.persistence.PersistenceException;

@Service
public class EstadoService {
	
	@Inject
	private EstadoDAO estadoDAO;
	
	@Transactional
	public Estado salvar(Estado estado) throws ServiceException {
		try {
			return estadoDAO.salvar(estado);
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível salvar", e);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Estado> list() {	
		return estadoDAO.list();
	}

	@Transactional(rollbackFor=Exception.class)
	public void excluir(Estado estado) throws ServiceException {
		try {
			estadoDAO.excluir(estado.getId());
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível excluir", e);
		}
	}
	@Transactional(rollbackFor=Exception.class)
	public Long excluir(List<Estado> estadosSelecionados) throws ServiceException {
		try {
			Long contador = new Long(0);
			for (Estado estado : estadosSelecionados) {
				estadoDAO.excluir(estado.getId());
				contador++;
			}
			return contador;
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível excluir", e);
		}
		
	}

	@Transactional(readOnly = true)
	public Estado find(long id) {
		return estadoDAO.find(id);
	}
}
