package br.com.pereirakienast.controleservicos.mbeans.converter;

import br.com.pereirakienast.controleservicos.entity.Cliente;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteCliente", forClass = Cliente.class)
public class ClienteConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soCliente = (UISelectOne) component;

        return clienteById(listaClientes(soCliente), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Cliente cliente = (Cliente) value;

        return String.valueOf(cliente.getId());
    }

    private Cliente clienteById(List<Cliente> listaClientes, int id) {
        Cliente resposta = null;

        for (Cliente a : listaClientes) {
            if (a.getId() == id) {
                resposta = a;
                break;
            }
        }
        return resposta;
    }

    private List<Cliente> listaClientes(UISelectOne selectOne) {
        UISelectItems siCliente = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siCliente = (UISelectItems) ui;
                break;
            }
        }
        if (siCliente == null) {
            throw new RuntimeException("Problemas para validar objeto Cliente");
        }

        return (List<Cliente>) siCliente.getValue();
    }
}
