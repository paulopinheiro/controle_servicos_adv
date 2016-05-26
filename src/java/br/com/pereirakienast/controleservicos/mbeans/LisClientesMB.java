package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.ejb.ClienteFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.entity.Cliente;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.util.ContextoJSF;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class LisClientesMB implements Serializable {
    @EJB
    private ClienteFacade clienteFacade;
    @EJB
    private AdvogadoFacade advogadoFacade;
    private Cliente filtro;
    private List<Cliente> lista;
    private List<Advogado> listaAdvogados;

    public Cliente getFiltro() {
        if (filtro == null) filtro = new Cliente();
        return filtro;
    }

    public void setFiltro(Cliente filtro) {
        this.filtro = filtro;
    }

    public List<Cliente> getLista() {
        try {
            if (lista == null) lista = clienteFacade.findAtivos();
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
            Logger.getLogger("LisClientesMB").log(Level.SEVERE, ex.getMessage());
        }
        return lista;
    }

    public void setLista(List<Cliente> lista) {
        this.lista = lista;
    }

    public List<Advogado> getListaAdvogados() {
        try {
            if (listaAdvogados == null) listaAdvogados = advogadoFacade.findAtivos();
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
            Logger.getLogger("LisClientesMB").log(Level.SEVERE, ex.getMessage());
        }
        return listaAdvogados;
    }

    public void setListaAdvogados(List<Advogado> listaAdvogados) {
        this.listaAdvogados = listaAdvogados;
    }

    public void limpar(ActionEvent evt) {
        setFiltro(null);
    }

    public void filtrar(ActionEvent evt) {
        try {
            setLista(clienteFacade.findFiltro(getFiltro()));
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
            Logger.getLogger("LisClientesMB").log(Level.SEVERE, ex.getMessage());
        }
    }

    protected void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
    }
}
