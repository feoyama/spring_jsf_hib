package com.noriakijr.spring_jsf_hib.persistence;

import javax.persistence.Query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.noriakijr.spring_jsf_hib.model.Cidade;

@Repository
public class CidadeDAO extends GenericDAO<Cidade, Long>{

	public CidadeDAO() {
		super(Cidade.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> listaCidadesEstado(Long id){
		Query query = em.createQuery("select c from Cidade c join c.estado e where e.id=:id", Cidade.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
//	@PersistenceContext
//	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Cidade> listaCidadesAutoComplete(Cidade cidade) {
		Query query = em.createQuery("select c from Cidade c join c.estado e where e.id=:id and c.descricao like :descricao", Cidade.class);
		query.setParameter("id", cidade.getEstado().getId());
		query.setParameter("descricao", cidade.getDescricao() + "%");
		List<Cidade> cidades = query.getResultList();
		return query.getResultList();
	}


//	public Cidade salvar(Cidade cidade) throws PersistenceException {
//		try {
//			return em.merge(cidade);
//		} catch (Exception e) {
//			throw new PersistenceException("Não foi possível salvar", e);
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<Cidade> list() {
//		Query query = em.createQuery("select c from Cidade c order by c.descricao");
//		return query.getResultList();
//	}
//
//	public void excluir(Cidade cidade) throws PersistenceException {
//		try {
//			// antes da exclusao, eh necessario obter a referencia do objeto
//			cidade = em.getReference(Cidade.class, cidade.getId());
//			em.remove(cidade);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new PersistenceException("Não foi possível excluir", e);
//		}
//		
//	}
//
//	
//	public Cidade find(Long id) {
//		return em.find(Cidade.class, id);
//	}
	
	
	
}
