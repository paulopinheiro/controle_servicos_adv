package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.ejb.ClienteFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.entity.Cliente;
import br.com.pereirakienast.controleservicos.mbeans.comum.AbBasicoMB;
import br.com.pereirakienast.controleservicos.util.DadosSessao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ClienteMB extends AbBasicoMB<Cliente> implements Serializable {
    @EJB private ClienteFacade facade;
    @EJB private AdvogadoFacade advogadoFacade;
    private List<Advogado> listaAdvogados;

    //TODO:
    // . implementar cadastro de documentos do cliente

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
}
