package com.feoyama.spring_jsf_hib.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.feoyama.spring_jsf_hib.persistence.implementation.DAO;

public abstract class GenericDAO<T extends Serializable, I> implements DAO<T, I>{
	
	@PersistenceContext
	protected EntityManager em;
	
	private Class<T> classeGenerica;
	
	public GenericDAO(Class<T> classeGenerica){
		this.classeGenerica = classeGenerica;
	}
	
	public T salvar(T entity) throws PersistenceException{
		try {
			return em.merge(entity);
		} catch (Exception e) {
			throw new PersistenceException("Não foi possível salvar", e);
		}
	}
	
	
	public void excluir(Long id) throws PersistenceException{
		try {
			T entity = (T) em.getReference(classeGenerica, id);
			em.remove(entity);
		} catch (Exception e) {
			throw new PersistenceException("Não foi possível excluir", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list(){
		Query query = em.createQuery("select e from " + classeGenerica.getSimpleName() +" e");
		return query.getResultList();
	}
	
	public T find(Long id) {
		return (T) em.find(classeGenerica, id);
	}
	
	
}
