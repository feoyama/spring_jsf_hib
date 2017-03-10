package com.noriakijr.spring_jsf_hib.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.noriakijr.spring_jsf_hib.model.Cidade;
import com.noriakijr.spring_jsf_hib.model.Estado;
import com.noriakijr.spring_jsf_hib.model.Usuario;
import com.noriakijr.spring_jsf_hib.service.CidadeService;
import com.noriakijr.spring_jsf_hib.service.EstadoService;
import com.noriakijr.spring_jsf_hib.service.ServiceException;
import com.noriakijr.spring_jsf_hib.service.UsuarioService;

@Controller
@Scope("view")
public class UsuarioBean {

	private Usuario usuario;
	private List<Usuario> usuarios;
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private CidadeService cidadeService;
	private List<Cidade> cidadesEstado;
	private List<Cidade> cidades;
	private List<Cidade> cidadesAutoComplete;
	private Estado estado;
	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
		usuarios = usuarioService.list();
		cidades = cidadeService.list();
		estado = new Estado();
		
	}
	
	public void salvar() {
		try {
			usuarioService.salvar(usuario);
			usuario = new Usuario();
			estado = new Estado();
			usuarios = usuarioService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Salvo com sucesso!"));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: " + e.getMessage(), null));
		}
	}

	public void excluir() {
		try {
			usuarioService.excluir(usuario);
			this.usuario = new Usuario();
			usuarios = usuarioService.list();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Excluído com sucesso!"));
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: " + e.getMessage(), null));
		}
	}
	
	
	public void onEstadoChange(AjaxBehaviorEvent event){
		if(estado !=null)
			cidadesEstado = cidadeService.listaCidadesEstado(estado.getId());
        else
        	cidadesEstado = new ArrayList<>();
	}
	
	public void setUsuarioEstado(Usuario usuario){
		this.usuario = usuario;
		this.estado = usuario.getCidade().getEstado();
		cidadesEstado = cidadeService.listaCidadesEstado(estado.getId());
	}
	
	public List<Cidade> completeText(String query) {
        Cidade cidade = new Cidade();
        cidade.setEstado(estado);
        cidade.setDescricao(query);
        cidadesAutoComplete = cidadeService.listaCidadesAutoComplete(cidade);
        return cidadesAutoComplete;
       
    }
	
	
	public void setUsuarioListener(Usuario usuario){
		this.usuario = usuario;
		}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Cidade> getCidadesEstado() {
		return cidadesEstado;
	}

	public void setCidadesEstado(List<Cidade> cidadesEstado) {
		this.cidadesEstado = cidadesEstado;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
}
