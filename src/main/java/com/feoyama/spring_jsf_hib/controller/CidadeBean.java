package com.feoyama.spring_jsf_hib.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feoyama.spring_jsf_hib.model.Cidade;
import com.feoyama.spring_jsf_hib.model.Estado;
import com.feoyama.spring_jsf_hib.service.CidadeService;
import com.feoyama.spring_jsf_hib.service.EstadoService;
import com.feoyama.spring_jsf_hib.service.ServiceException;

@Controller
@Scope("view")
public class CidadeBean {
	private Cidade cidade;
	private List<Cidade> cidades;
	private List<Estado> estados;
	@Inject
	private CidadeService cidadeService;
	@Inject
	private EstadoService estadoService;
	
	
	private Estado estado;
	

	private List<Cidade> cidadesSelecionadas;
	

	
	@PostConstruct
	public void init(){
		cidade = new Cidade();
		cidades = cidadeService.list();
		estados = estadoService.list();
		estado = new Estado();
	}
	
	public void salvar(){
		try {
			cidadeService.salvar(cidade);
			cidade = new Cidade();
			cidades = cidadeService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Salvo com sucesso!"));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}
	
	public void excluir(Cidade cidade){
		try {
			cidadeService.excluir(cidade);
			cidade = new Cidade();
			cidades = cidadeService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Excluído com sucesso!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}
	
	public void excluir(){
		try {
			Long linhasAfetadas = cidadeService.excluir(cidadesSelecionadas);
			cidadesSelecionadas = new ArrayList<>();
			cidades = cidadeService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Excluído com sucesso! Registros afetados: " + linhasAfetadas));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}
	
	public void onRowSelect(SelectEvent event) {
		cidade = (Cidade)event.getObject();
	}
	

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Cidade> getCidadesSelecionadas() {
		return cidadesSelecionadas;
	}

	public void setCidadesSelecionadas(List<Cidade> cidadesSelecionadas) {
		this.cidadesSelecionadas = cidadesSelecionadas;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	

	
}
