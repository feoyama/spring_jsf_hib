package com.feoyama.spring_jsf_hib.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.feoyama.spring_jsf_hib.model.Usuario;
import com.feoyama.spring_jsf_hib.service.UsuarioService;


@Component
public class UsuarioConverter implements Converter {
 
	@Inject
	private UsuarioService usuarioService;
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		
        if (value != null) {  
        	Usuario usuario = usuarioService.find(Long.parseLong(value));
        	return usuario;
        }  
        return null;  
    }  
  
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {  
  
        if (value != null  
                && !"".equals(value)) {  
  
            Usuario usuario = (Usuario) value;  
  
            Long codigo = usuario.getId();  
            if (codigo != null) {  
                return String.valueOf(codigo);  
            }  
        }  
  
        return null; 
    }  
  

}
