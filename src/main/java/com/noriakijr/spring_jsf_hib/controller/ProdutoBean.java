package com.noriakijr.spring_jsf_hib.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.noriakijr.spring_jsf_hib.model.Produto;
import com.noriakijr.spring_jsf_hib.model.enumerator.Tipo;
import com.noriakijr.spring_jsf_hib.service.ProdutoService;
import com.noriakijr.spring_jsf_hib.service.ServiceException;

@Controller
@Scope("view")
public class ProdutoBean {
	private Produto produto;
	private List<Produto> produtos;
	private Produto produtoFiltro;
	private List<Produto> produtosSelecionados;

	@Inject
	private ProdutoService produtoService;
	@PostConstruct
	private void init(){
		produto = new Produto();
		produtoFiltro = new Produto();
		produtos = produtoService.list();
		
	}

	public void salvar(){
		try {
			produtoService.salvar(produto);
			produto = new Produto();
			produtos = produtoService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Salvo com sucesso!"));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}

	public void excluir(Produto produto){
		try {
			produtoService.excluir(produto);
			produto = new Produto();
			produtos = produtoService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Excluído com sucesso!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}
	
	public void excluir(){
		try {
			Long linhasAfetadas = produtoService.excluir(produtosSelecionados);
			produtosSelecionados = new ArrayList<>();
			produtos = produtoService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Excluído com sucesso! Registros afetados: " + linhasAfetadas));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}
	
	 public void onRowSelect(SelectEvent event) {
		 produto = (Produto)event.getObject();
	 }
	
	public void filtrar(){
		produtos = produtoService.list(produtoFiltro);
	}
	
	public Produto getProdutoFiltro() {
		return produtoFiltro;
	}

	public void setProdutoFiltro(Produto produtoFiltro) {
		this.produtoFiltro = produtoFiltro;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Tipo[] getListTipo(){
		return Tipo.values();
	}
	
	public List<Produto> getProdutosSelecionados() {
		return produtosSelecionados;
	}

	public void setProdutosSelecionados(List<Produto> produtosSelecionados) {
		this.produtosSelecionados = produtosSelecionados;
	}
	
}
