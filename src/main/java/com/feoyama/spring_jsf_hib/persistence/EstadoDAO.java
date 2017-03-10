package com.feoyama.spring_jsf_hib.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.feoyama.spring_jsf_hib.model.Estado;

@Repository
public class EstadoDAO extends GenericDAO<Estado, Long> {

	public EstadoDAO() {
		super(Estado.class);
		// TODO Auto-generated constructor stub
	}

//	@PersistenceContext
//	private EntityManager em;
//	
//	public Estado salvar(Estado estado) throws PersistenceException {
//		try {
//			return em.merge(estado);
//		} catch (Exception e) {
//			throw new PersistenceException("Não foi possível salvar", e);
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Estado> list() {
//		Query query = em.createQuery("select e from Estado e order by e.descricao");
//		return query.getResultList();
//	}
//
//	public void excluir(Estado estado) throws PersistenceException {
//		try {
//			// antes da exclusao, eh necessario obter a referencia do objeto
//			estado = em.getReference(Estado.class, estado.getId());
//			em.remove(estado);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new PersistenceException("Não foi possível excluir", e);
//		}
//		
//	}
//
//	public Estado find(long id) {
//		return em.find(Estado.class, id);
//	}

}
