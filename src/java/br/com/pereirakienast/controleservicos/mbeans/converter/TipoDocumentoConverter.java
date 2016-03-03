package br.com.pereirakienast.controleservicos.mbeans.converter;

import br.com.pereirakienast.controleservicos.entity.TipoDocumento;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteTipoDocumento", forClass = TipoDocumento.class)
public class TipoDocumentoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soTipoDocumento = (UISelectOne) component;

        return tipoDocumentoById(listaTiposDocumento(soTipoDocumento), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        TipoDocumento tipoDocumento = (TipoDocumento) value;

        return String.valueOf(tipoDocumento.getId());
    }

    private TipoDocumento tipoDocumentoById(List<TipoDocumento> listaTiposDocumento, int id) {
        TipoDocumento resposta = null;

        for (TipoDocumento t : listaTiposDocumento) {
            if (t.getId() == id) {
                resposta = t;
                break;
            }
        }
        return resposta;
    }

    private List<TipoDocumento> listaTiposDocumento(UISelectOne selectOne) {
        UISelectItems siTipoDocumento = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siTipoDocumento = (UISelectItems) ui;
                break;
            }
        }
        if (siTipoDocumento == null) {
            throw new RuntimeException("Problemas para validar objeto TipoDocumento");
        }

        return (List<TipoDocumento>) siTipoDocumento.getValue();
    }
}
