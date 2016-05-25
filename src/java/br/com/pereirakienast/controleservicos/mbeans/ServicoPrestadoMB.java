package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.ejb.ClienteFacade;
import br.com.pereirakienast.controleservicos.ejb.ServicoFacade;
import br.com.pereirakienast.controleservicos.ejb.TipoServicoFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.entity.Cliente;
import br.com.pereirakienast.controleservicos.entity.ServicoPrestado;
import br.com.pereirakienast.controleservicos.entity.TipoServico;
import br.com.pereirakienast.controleservicos.mbeans.comum.AbBasicoMB;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

@ManagedBean
@ViewScoped
public class ServicoPrestadoMB extends AbBasicoMB<ServicoPrestado> implements Serializable {
    @EJB private ServicoFacade servicoFacade;
    @EJB private AdvogadoFacade advogadoFacade;
    @EJB private ClienteFacade clienteFacade;
    @EJB private TipoServicoFacade tipoServicoFacade;

    public ServicoPrestadoMB() {}

    public List<Advogado> getListaAdvogados() {
        return advogadoFacade.findAtivos();
    }

    public List<Cliente> getListaClientes() {
        return clienteFacade.findAtivos();
    }

    public List<TipoServico> getListaTiposServico() {
        return tipoServicoFacade.findAll();
    }

    public ServicoPrestado getServico() {
        return this.getElemento();
    }

    public void setServico(ServicoPrestado servico) {
        this.setElemento(servico);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.servicoFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getElemento().getId()==null || this.getElemento().getId()==0;
    }

    @Override
    protected ServicoPrestado novainstanciaElemento() {
        return new ServicoPrestado();
    }
}
