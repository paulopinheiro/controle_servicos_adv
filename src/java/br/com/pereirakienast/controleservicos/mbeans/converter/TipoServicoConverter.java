package br.com.pereirakienast.controleservicos.mbeans.converter;

import br.com.pereirakienast.controleservicos.entity.TipoServico;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteTipoServico", forClass = TipoServico.class)
public class TipoServicoConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soTipoServico = (UISelectOne) component;

        return tipoServicoById(listaTiposServico(soTipoServico), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        TipoServico tipoServico = (TipoServico) value;

        return String.valueOf(tipoServico.getId());
    }

    private TipoServico tipoServicoById(List<TipoServico> listaTiposServico, int id) {
        TipoServico resposta = null;

        for (TipoServico a : listaTiposServico) {
            if (a.getId() == id) {
                resposta = a;
                break;
            }
        }
        return resposta;
    }

    private List<TipoServico> listaTiposServico(UISelectOne selectOne) {
        UISelectItems siTipoServico = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siTipoServico = (UISelectItems) ui;
                break;
            }
        }
        if (siTipoServico == null) {
            throw new RuntimeException("Problemas para validar objeto TipoServico");
        }

        return (List<TipoServico>) siTipoServico.getValue();
    }
}
