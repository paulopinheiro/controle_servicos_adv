package br.com.pereirakienast.controleservicos.mbeans.converter;

import br.com.pereirakienast.controleservicos.ejb.cobranca.ParcelaFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@Model
public class ParcelaConverterMB implements Converter, Serializable {
    @EJB private ParcelaFacade parcelaFacade;
@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Parcela) parcelaFacade.find(Integer.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar a parcela de id %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Parcela)||((Parcela)value).getId()==null) return null;
            return String.valueOf(((Parcela)value).getId());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar a parcela de id %s", value)), ex);
        }
    }    
}
