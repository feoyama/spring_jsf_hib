package com.noriakijr.spring_jsf_hib.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.noriakijr.spring_jsf_hib.model.Cidade;
import com.noriakijr.spring_jsf_hib.service.CidadeService;


@Component
public class CidadeConverter implements Converter {
 
	@Inject
	private CidadeService cidadeService;
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		
        if (value != null) {  
        	Cidade cidade = cidadeService.find(Long.parseLong(value));
        	return cidade;
        }  
        return null;  
    }  
  
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {  
  
        if (value != null  
                && !"".equals(value)) {  
  
            Cidade cidade = (Cidade) value;  
  
            Long codigo = cidade.getId();  
            if (codigo != null) {  
                return String.valueOf(codigo);  
            }  
        }  
  
        return (String) value;  
    }  
  

}
