package com.feoyama.spring_jsf_hib.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feoyama.spring_jsf_hib.model.ItemPedido;
import com.feoyama.spring_jsf_hib.model.Pedido;
import com.feoyama.spring_jsf_hib.model.Produto;
import com.feoyama.spring_jsf_hib.model.Usuario;
import com.feoyama.spring_jsf_hib.model.enumerator.Status;
import com.feoyama.spring_jsf_hib.service.ItemPedidoService;
import com.feoyama.spring_jsf_hib.service.PedidoService;
import com.feoyama.spring_jsf_hib.service.ProdutoService;
import com.feoyama.spring_jsf_hib.service.ServiceException;
import com.feoyama.spring_jsf_hib.service.UsuarioService;

@Controller
@Scope("view")
public class PedidoBean {
	private ItemPedido itemPedido;
	private List<Produto> produtos;
	private List<ItemPedido> itensPedidos;
	private Produto produto;
	@Inject
	private ProdutoService produtoService;
	@Inject
	private UsuarioService usuarioService;
	private Usuario usuario;

	private Produto produtoSelecinado;

	private List<ItemPedido> itensPedidosSelecionados;

	@Inject
	private ItemPedidoService itemPedidoService;

	@Inject
	private PedidoService pedidoService;

	private Pedido pedido;
	private List<Pedido> pedidos;

	private Boolean visivel;
	private List<Pedido> pedidosSelecionados;
	private Double valorTotal;
	
	private Boolean pedidoVisivel;
	private Boolean itemPedidoVisivel;

	@PostConstruct
	public void init() {
		itemPedido = new ItemPedido();
		produto = new Produto();
		produtos = produtoService.list();
		itensPedidos = new ArrayList<>();
		pedido = new Pedido();
		pedidos = pedidoService.list();
		visivel = new Boolean(true);
		itensPedidosSelecionados = new ArrayList<>();
		produtoSelecinado = new Produto();
		valorTotal = new Double(0);
		usuario = new Usuario();
		pedidoVisivel = new Boolean(true);
		itemPedidoVisivel = new Boolean(false);
	}

	public void salvarPedido() {
		try {
			pedido.setDataPedido(new Date());
			pedido.setValorTotal(0.0);
			pedidoService.salvar(pedido);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Salvo com sucesso!"));
		} catch (Exception e) {
			// TODO: handle exception
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}

	}

	public void salvar() {
		try {
			itemPedidoService.salvar(itemPedido);
			itemPedido = new ItemPedido();
			itensPedidos = itemPedidoService.list();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Salvo com sucesso!"));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}

	public void salvarItemPedido() {
		try {
			Double somatoria = new Double(0);
			for (ItemPedido item : itensPedidos) {
				somatoria += item.getProduto().getValor() * item.getQuantidade();
			}
			somatoria += produto.getValor() * itemPedido.getQuantidade();
			pedido.setValorTotal(somatoria);
			pedido.setStatus(Status.ORCAMENTO);
			pedido = pedidoService.salvar(pedido);
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);
			itemPedidoService.salvar(itemPedido);
			itensPedidos = itemPedidoService.listaPedido(pedido.getId());
			itemPedido = new ItemPedido();

			pedidos = pedidoService.list();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Salvo com sucesso!"));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}
	
	public void emitirPedido(Pedido pedido){
		try {
			pedido.setStatus(Status.EMITIDO);
			pedidoService.salvar(pedido);
			pedidos = pedidoService.list();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Emitido com sucesso!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
		}
	}
	
	public Double retornaValorTotal(){
		Double somatoria = new Double(0);
		for (ItemPedido item : itensPedidos) {
			somatoria += item.getProduto().getValor() * item.getQuantidade();
		}
		return somatoria;
	}

	public void trocaVisivel() {
		this.visivel = !this.visivel;
	}

	public void onRowSelect(SelectEvent event) {
		pedido = (Pedido) event.getObject();
		itensPedidos = itemPedidoService.listaPedido(pedido.getId());
		itemPedido = new ItemPedido();
		produto = new Produto();
		setItemParaVisivel();

	}

	public void onRowSelectItensPedidos(SelectEvent event) {
		itemPedido = (ItemPedido) event.getObject();
	}

	public void onProdutoChange(AjaxBehaviorEvent event) {
		produtoSelecinado = produtoService.find(produto.getId());
	}

	public void onUsuarioChange(AjaxBehaviorEvent event) {
		Usuario usuarioTemp = pedido.getUsuario();
		pedido = new Pedido();
		pedido.setUsuario(usuarioService.find(usuarioTemp.getId()));
		itensPedidos = new ArrayList<>();
	}

	public void onQuantidadeChange(AjaxBehaviorEvent event) {
		valorTotal = produto.getValor() * itemPedido.getQuantidade();
	}

	public void excluirItensSelecionados() {
		try {
			Long linhasAfetadas = itemPedidoService.excluirSelecionados(itensPedidosSelecionados);
			itensPedidos = itemPedidoService.listaPedido(pedido.getId());
			itensPedidosSelecionados = new ArrayList<>();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Excluído com sucesso! " + linhasAfetadas + " Registros excluídos"));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
			e.printStackTrace();
		}
	}

	public void excluirPedidosSelecionados() {
		try {
			Long linhasAfetadas = pedidoService.excluirSelecionados(pedidosSelecionados);
			pedidosSelecionados = new ArrayList<>();
			pedidos = pedidoService.list();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Excluído com sucesso! " + linhasAfetadas + " Registros excluídos"));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro " + e.getMessage(), null));
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean temOrcamento(){
		for (Pedido pedido : pedidos) {
			if(pedido.getStatus() == Status.ORCAMENTO)
				return true;
		}
		return false;
	}
	
	public Boolean isStatusOrcamento(Pedido pedido){
		return pedido.getStatus() == Status.ORCAMENTO;
	}
	
	
	public void setPedidoParaVisivel(){
		pedidoVisivel = true;
		itemPedidoVisivel = false;
	}
	
	public void setItemParaVisivel(){
		itemPedidoVisivel = true;
		pedidoVisivel = false;
	}
	
	public Boolean getPedidoVisivel() {
		return pedidoVisivel;
	}

	public void setPedidoVisivel(Boolean pedidoVisivel) {
		this.pedidoVisivel = pedidoVisivel;
	}

	public Boolean getItemPedidoVisivel() {
		return itemPedidoVisivel;
	}

	public void setItemPedidoVisivel(Boolean itemPedidoVisivel) {
		this.itemPedidoVisivel = itemPedidoVisivel;
	}

	public void onStatusChange(AjaxBehaviorEvent event) {
		pedido.setStatus(pedido.getStatus());
	}

	public Status[] getListStatus() {
		return Status.values();
	}

	public ItemPedido getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemPedido> getItensPedidos() {
		return itensPedidos;
	}

	public void setItensPedidos(List<ItemPedido> itensPedidos) {
		this.itensPedidos = itensPedidos;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ProdutoService getProdutoService() {
		return produtoService;
	}

	public void setProdutoService(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Boolean getVisivel() {
		return visivel;
	}

	public void setVisivel(Boolean visivel) {
		this.visivel = visivel;
	}

	public List<Pedido> getPedidosSelecionados() {
		return pedidosSelecionados;
	}

	public void setPedidosSelecionados(List<Pedido> pedidosSelecionados) {
		this.pedidosSelecionados = pedidosSelecionados;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemPedido> getItensPedidosSelecionados() {
		return itensPedidosSelecionados;
	}

	public void setItensPedidosSelecionados(List<ItemPedido> itensPedidosSelecionados) {
		this.itensPedidosSelecionados = itensPedidosSelecionados;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

}
