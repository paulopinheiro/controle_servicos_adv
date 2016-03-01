package br.com.pereirakienast.controleservicos.mbeans.converter;

import br.com.pereirakienast.controleservicos.ejb.ClienteFacade;
import br.com.pereirakienast.controleservicos.entity.Cliente;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@ManagedBean
@RequestScoped
public class ClienteConverterMB implements Converter, Serializable {
    @EJB private ClienteFacade clienteFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Cliente) clienteFacade.find(Integer.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o cliente de id %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Cliente)||((Cliente)value).getId()==null) return null;
            return String.valueOf(((Cliente)value).getId());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o cliente de id %s", value)), ex);
        }
    }

}
