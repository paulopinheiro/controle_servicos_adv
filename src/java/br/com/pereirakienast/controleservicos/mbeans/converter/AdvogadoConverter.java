package br.com.pereirakienast.controleservicos.mbeans.converter;

import br.com.pereirakienast.controleservicos.entity.Advogado;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteAdvogado", forClass = Advogado.class)
public class AdvogadoConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soAdvogado = (UISelectOne) component;

        return advogadoById(listaAdvogados(soAdvogado), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Advogado advogado = (Advogado) value;

        return String.valueOf(advogado.getId());
    }

    private Advogado advogadoById(List<Advogado> listaAdvogados, int id) {
        Advogado resposta = null;

        for (Advogado a : listaAdvogados) {
            if (a.getId() == id) {
                resposta = a;
                break;
            }
        }
        return resposta;
    }

    private List<Advogado> listaAdvogados(UISelectOne selectOne) {
        UISelectItems siAdvogado = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siAdvogado = (UISelectItems) ui;
                break;
            }
        }
        if (siAdvogado == null) {
            throw new RuntimeException("Problemas para validar objeto Advogado");
        }

        return (List<Advogado>) siAdvogado.getValue();
    }
}
