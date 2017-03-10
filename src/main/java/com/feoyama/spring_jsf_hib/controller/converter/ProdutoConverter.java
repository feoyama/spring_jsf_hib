package com.feoyama.spring_jsf_hib.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.feoyama.spring_jsf_hib.model.Produto;
import com.feoyama.spring_jsf_hib.service.ProdutoService;


@Component
public class ProdutoConverter implements Converter {
 
	@Inject
	private ProdutoService produtoService;
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		
        if (value != null) {  
        	Produto produto = produtoService.find(Long.parseLong(value));
        	return produto;
        }  
        return null;  
    }  
  
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {  
  
        if (value != null  
                && !"".equals(value)) {  
  
        	Produto produto = (Produto) value;  
  
            Long codigo = produto.getId();  
            if (codigo != null) {  
                return String.valueOf(codigo);  
            }  
        }  
  
        return null;
    }  
  

}
