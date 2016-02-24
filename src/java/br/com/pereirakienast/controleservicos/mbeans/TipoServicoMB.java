package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.TipoServicoFacade;
import br.com.pereirakienast.controleservicos.entity.TipoServico;
import br.com.pereirakienast.controleservicos.mbeans.comum.AbListaMB;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

@ManagedBean
@ViewScoped
public class TipoServicoMB extends AbListaMB<TipoServico> implements Serializable {
    @EJB private TipoServicoFacade facade;

    public TipoServicoMB() {}


    @Override
    public void salvar(ActionEvent evt) {
        System.out.println("(MB) " + this.getElemento().getId() + " - " + this.getElemento().getNome());
        super.salvar(evt);
    }

    @Override
    protected TipoServicoFacade getFacade() {
        return this.facade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getTipoServico().getId()==null||this.getTipoServico().getId()==0;
    }

    @Override
    protected TipoServico novainstanciaElemento() {
        return new TipoServico();
    }

    public TipoServico getTipoServico() {
        return super.getElemento();
    }

    public void setTipoServico(TipoServico tipoServico) {
        super.setElemento(tipoServico);
    }

    public List<TipoServico> getListaTiposServico() {
        return super.getLista();
    }

    public void setListaTiposServico(List<TipoServico> listaTiposServico) {
        super.setLista(listaTiposServico);
    }
}
