package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.ejb.ClienteFacade;
import br.com.pereirakienast.controleservicos.ejb.ServicoFacade;
import br.com.pereirakienast.controleservicos.ejb.TipoServicoFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.entity.Cliente;
import br.com.pereirakienast.controleservicos.entity.ServicoPrestado;
import br.com.pereirakienast.controleservicos.entity.TipoServico;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.util.ContextoJSF;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

@ManagedBean
@ViewScoped
public class LisServicosMB implements Serializable {
    @EJB private ServicoFacade servicoFacade;
    @EJB private AdvogadoFacade advogadoFacade;
    @EJB private ClienteFacade clienteFacade;
    @EJB private TipoServicoFacade tipoServicoFacade;
    
    private List<ServicoPrestado> lista;
    private ServicoPrestado filtro;

    private List<Cliente> listaClientes;
    private List<Advogado> listaAdvogados;
    private List<TipoServico> listaTiposServico;

    public void limpar(ActionEvent evt) {
        setFiltro(null);
    }

    public void filtrar(ActionEvent evt) {
        try {
            setLista(servicoFacade.findFiltro(getFiltro()));
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
            Logger.getLogger("LisServicosMB").log(Level.SEVERE, ex.getMessage());
        }
    }

    public ServicoPrestado getFiltro() {
        if (filtro==null) filtro = new ServicoPrestado();
        return filtro;
    }

    public void setFiltro(ServicoPrestado filtro) {
        this.filtro = filtro;
    }

    public List<ServicoPrestado> getLista() {
        if (lista==null) lista = servicoFacade.findAll();
        return lista;
    }

    public void setLista(List<ServicoPrestado> servicos) {
        this.lista = servicos;
    }

    public List<Advogado> getListaAdvogados() {
        try {
            if (listaAdvogados == null) listaAdvogados = advogadoFacade.findAtivos();
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
            Logger.getLogger("LisPartesMB").log(Level.SEVERE, ex.getMessage());
        }
        return listaAdvogados;
    }

    public void setListaAdvogados(List<Advogado> listaAdvogados) {
        this.listaAdvogados = listaAdvogados;
    }

    public List<Cliente> getListaClientes() {
        try {
            if (listaClientes == null) listaClientes = clienteFacade.findAtivos();
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
            Logger.getLogger("LisServicosMB").log(Level.SEVERE, ex.getMessage());
        }
        return listaClientes;
    }

    public List<TipoServico> getListaTiposServico() {
        try {
            if (listaTiposServico == null) listaTiposServico = tipoServicoFacade.findAll();
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
            Logger.getLogger("LisServicosMB").log(Level.SEVERE, ex.getMessage());
        }
        return listaTiposServico;
    }

    public void setListaTiposServico(List<TipoServico> listaTiposServico) {
        this.listaTiposServico = listaTiposServico;
    }

    protected void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
    }
}
