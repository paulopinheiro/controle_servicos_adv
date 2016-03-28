package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.ejb.ClienteFacade;
import br.com.pereirakienast.controleservicos.ejb.TipoDocumentoFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.entity.Cliente;
import br.com.pereirakienast.controleservicos.entity.Documento;
import br.com.pereirakienast.controleservicos.entity.TipoDocumento;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.mbeans.comum.AbBasicoMB;
import br.com.pereirakienast.controleservicos.util.DadosSessao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class ClienteMB extends AbBasicoMB<Cliente> implements Serializable {
    @EJB
    private ClienteFacade facade;
    @EJB
    private AdvogadoFacade advogadoFacade;
    @EJB
    private TipoDocumentoFacade tipoDocumentoFacade;
    private List<Advogado> listaAdvogados;

    private List<TipoDocumento> listaTiposDocumentos;
    private Documento documento;

    public Cliente getCliente() {
        return super.getElemento();
    }

    public void setCliente(Cliente cliente) {
        super.setElemento(cliente);
    }

    public List<Advogado> getListaAdvogados() {
        if (this.listaAdvogados == null) {
            this.listaAdvogados = this.advogadoFacade.findAtivos();
        }
        return this.listaAdvogados;
    }

    public List<TipoDocumento> getListaTiposDocumentos() {
        if (this.listaTiposDocumentos == null) {
            this.listaTiposDocumentos = this.tipoDocumentoFacade.findAll();
        }
        return listaTiposDocumentos;
    }

    public void setListaTiposDocumentos(List<TipoDocumento> listaTiposDocumentos) {
        this.listaTiposDocumentos = listaTiposDocumentos;
    }

    public Documento getDocumento() {
        if (documento == null) {
            documento = new Documento();
            documento.setCliente(getCliente());
        }
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public void incluirDocumento(ActionEvent evt) {
        try {
            facade.salvarDocumento(getDocumento());
            setDocumento(null);
            refreshListaDocumentos();
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        }
    }

    private void refreshListaDocumentos() {
        if (this.getCliente().getId() != null) {
            getCliente().setDocumentos(facade.refreshListaDocumentos(getCliente()));
        }
    }

    public String excluirDocumento(Documento doc) {
        try {
            facade.excluirDocumento(doc);
            setDocumento(null);
            refreshListaDocumentos();
        } catch (LogicalException ex) {
            mensagemErro(ex.getMessage());
        }
        return null;
    }

    public void limparDocumento(ActionEvent evt) {
        setDocumento(null);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.facade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getCliente().getId() == null || this.getCliente().getId() == 0;
    }

    @Override
    protected Cliente novainstanciaElemento() {
        Cliente cliente = new Cliente();
        cliente.setAdvogado(DadosSessao.getAdvogadoSessao());

        return cliente;
    }
}
