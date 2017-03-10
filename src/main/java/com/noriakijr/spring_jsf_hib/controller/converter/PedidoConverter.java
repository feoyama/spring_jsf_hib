package com.noriakijr.spring_jsf_hib.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.noriakijr.spring_jsf_hib.model.Pedido;
import com.noriakijr.spring_jsf_hib.service.PedidoService;


@Component
public class PedidoConverter implements Converter {
 
	@Inject
	private PedidoService pedidoService;
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		
        if (value != null ) {  
        	Pedido pedido = pedidoService.find(Long.parseLong(value));
        	return pedido;
        }  
        return null;  
    }  
  
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {  
  
        if (value != null  
                && !"".equals(value)) {  
  
            Pedido pedido = (Pedido) value;  
  
            Long codigo = pedido.getId();  
            if (codigo != null) {  
                return String.valueOf(codigo);  
            }  
        }  
  
        return null;
    }  
  

}
