package com.feoyama.spring_jsf_hib.persistence;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.feoyama.spring_jsf_hib.model.Usuario;
import com.feoyama.spring_jsf_hib.persistence.PersistenceException;
import com.feoyama.spring_jsf_hib.persistence.UsuarioDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true) - Spring < 4
@Transactional
public class TestUsuarioDAO {
	
	@Inject
	private UsuarioDAO dao;
	
	@Test
//	@Commit
//	@Rollback(true)
	public void salvarUsuario() throws PersistenceException {
		Usuario usuario = new Usuario();
		usuario.setNome("Felipe");
		usuario.setEmail("felipe@gmail.com");
		
		dao.salvar(usuario);
	}
	
}
