package com.noriakijr.spring_jsf_hib.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.noriakijr.spring_jsf_hib.model.Estado;
import com.noriakijr.spring_jsf_hib.model.Produto;

@Repository
public class ProdutoDAO  extends GenericDAO<Produto, Long>{

	public ProdutoDAO() {
		super(Produto.class);
		// TODO Auto-generated constructor stub
	}
	
//	@PersistenceContext
//	private EntityManager em;
//	
//	public Produto salvar(Produto produto) throws PersistenceException {
//		try {
//			return em.merge(produto);
//		} catch (Exception e) {
//			throw new PersistenceException("Não foi possível salvar", e);
//		}
//	}
//	
//	public void excluir(Produto produto) throws PersistenceException{
//		try {
//			// antes da exclusao, eh necessario obter a referencia do objeto
//			produto = em.getReference(Produto.class, produto.getId());
//			em.remove(produto);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new PersistenceException("Não foi possível excluir", e);
//		}
//	}
	
	/* para salvar sem spring
	 * 	public Produto salvar(Produto produto) throws PersistenceException {
		try {
			em.getTransaction().begin();
			produto = em.merge(produto);
			em.getTransaction().commit();
			return produto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new PersistenceException("Não foi possível salvar", e);
		}finally{
			em.close();
		}
	}
	 * */
	
//	@SuppressWarnings("unchecked")
//	public List<Produto> list() {
//		Query query = em.createQuery("select p from Produto p order by p.descricao");
//		return query.getResultList();
//	}
//	
	// filtrar produto
	@SuppressWarnings("unchecked")
	public List<Produto> list(Produto produto) {
		Query query = em.createQuery("select p from Produto p where p.descricao like '%" + produto.getDescricao() + "%' order by p.descricao");
		return query.getResultList();
	}
	
}
