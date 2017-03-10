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

import com.noriakijr.spring_jsf_hib.model.Estado;
import com.noriakijr.spring_jsf_hib.service.EstadoService;
import com.noriakijr.spring_jsf_hib.service.ServiceException;

@Controller
@Scope("view")
public class EstadoBean {
	private Estado estado;
	

	private List<Estado> estados;
	private List<Estado> estadosSelecionados;
	@Inject
	private EstadoService estadoService;
	
	@PostConstruct
	public void init(){
		estado = new Estado();
		estados = estadoService.list();
	}
	
	public void salvar(){
		try {
			estadoService.salvar(estado);
			estado = new Estado();
			estados = estadoService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Salvo com sucesso!"));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}
	
	public void excluir(Estado estado){
		try {
			estadoService.excluir(estado);
			estado = new Estado();
			estados = estadoService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Excluído com sucesso!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}
	
	public void excluir(){
		try {
			Long linhasAfetadas = estadoService.excluir(estadosSelecionados);
			estadosSelecionados = new ArrayList<>();
			estados = estadoService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Excluído com sucesso! Registros afetados: " + linhasAfetadas));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}
	
	public void onRowSelect(SelectEvent event) {
		 estado = (Estado)event.getObject();
	 }

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	public List<Estado> getEstadosSelecionados() {
		return estadosSelecionados;
	}

	public void setEstadosSelecionados(List<Estado> estadosSelecionados) {
		this.estadosSelecionados = estadosSelecionados;
	}
	
	
}
