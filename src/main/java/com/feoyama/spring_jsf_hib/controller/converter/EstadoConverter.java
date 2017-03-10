package com.feoyama.spring_jsf_hib.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.feoyama.spring_jsf_hib.model.Estado;
import com.feoyama.spring_jsf_hib.service.EstadoService;


@Component
public class EstadoConverter implements Converter {
 
	@Inject
	private EstadoService estadoService;
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		
        if (value != null) {  
        	Estado estado = estadoService.find(Long.parseLong(value));
        	return estado;
        }  
        return null;  
    }  
  
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {  
  
        if (value != null  
                && !"".equals(value)) {  
  
            Estado estado = (Estado) value;  
  
            Long codigo = estado.getId();  
            if (codigo != null) {  
                return String.valueOf(codigo);  
            }  
        }  
  
        return "";
    }  
  

}
