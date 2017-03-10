package com.feoyama.spring_jsf_hib.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feoyama.spring_jsf_hib.model.Usuario;
import com.feoyama.spring_jsf_hib.persistence.PersistenceException;
import com.feoyama.spring_jsf_hib.persistence.UsuarioDAO;

@Service
public class UsuarioService {

	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Transactional(rollbackFor = Exception.class)
	public Usuario salvar(Usuario usuario) throws ServiceException {
		try {
			return usuarioDAO.salvar(usuario);
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível salvar", e);
		}
	}

	@Transactional(readOnly = true)
	public List<Usuario> list() {
		return usuarioDAO.list();
	}

	@Transactional(rollbackFor = Exception.class)
	public void excluir(Usuario usuario) throws ServiceException {
		try {
			usuarioDAO.excluir(usuario.getId());
		} catch (PersistenceException e) {
			throw new ServiceException("Não foi possível excluir", e);
		}
	}
	
	@Transactional(readOnly = true)
	public Usuario findById(Usuario usuario) {
		if(usuario == null || usuario.getId() == null)
			return null;
		return usuarioDAO.find(usuario.getId());
	}

	public Usuario find(long id) {
		return usuarioDAO.find(id);
	}
	
}
