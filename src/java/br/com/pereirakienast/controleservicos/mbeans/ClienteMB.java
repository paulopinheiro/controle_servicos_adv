package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.ejb.ClienteFacade;
import br.com.pereirakienast.controleservicos.ejb.TipoDocumentoFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.entity.Cliente;
import br.com.pereirakienast.controleservicos.entity.Documento;
import br.com.pereirakienast.controleservicos.entity.TipoDocumento;
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
    @EJB private ClienteFacade facade;
    @EJB private AdvogadoFacade advogadoFacade;
    @EJB private TipoDocumentoFacade tipoDocumentoFacade;

    private List<Advogado> listaAdvogados;
    private List<TipoDocumento> listaTiposDocumentos;

    private Documento documento;

    public ClienteMB() {}

    public Cliente getCliente() {
        return super.getElemento();
    }

    public void setCliente(Cliente cliente) {
        super.setElemento(cliente);
    }

    public List<Advogado> getListaAdvogados() {
        if (this.listaAdvogados==null) this.listaAdvogados = this.advogadoFacade.findAtivos();
        return this.listaAdvogados;
    }

    public List<TipoDocumento> getListaTiposDocumentos() {
        if (this.listaTiposDocumentos==null)
            this.listaTiposDocumentos=this.tipoDocumentoFacade.findAll();
        return this.listaTiposDocumentos;
    }

    public Documento getDocumento() {
        if (this.documento==null) return new Documento(getCliente());
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.facade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getCliente().getId()==null||this.getCliente().getId()==0;
    }

    @Override
    protected Cliente novainstanciaElemento() {
        Cliente cliente = new Cliente();
        cliente.setAdvogado(DadosSessao.getAdvogadoSessao());

        return cliente;
    }

    public void salvarDocumento(ActionEvent evt) {
        try {
            this.facade.salvarDocumento(this.getDocumento());
            setDocumento(null);
            refreshListaDocumentos();
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    private void refreshListaDocumentos() {
        if (!(this.getCliente()==null)) {
            this.getCliente().setDocumentos(this.facade.refreshListaDocumentos(this.getCliente()));
        }
    }

    private void novoDocumento(ActionEvent evt) {
        setDocumento(null);
    }

    public void excluirDocumento(ActionEvent evt) {
        try {
            this.facade.excluirDocumento(this.getDocumento());
            setDocumento(null);
            refreshListaDocumentos();
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }
}
