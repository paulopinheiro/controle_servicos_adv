package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.ejb.TipoServicoFacade;
import br.com.pereirakienast.controleservicos.entity.TipoServico;
import br.com.pereirakienast.controleservicos.mbeans.comum.AbListaMB;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class TipoServicoMB extends AbListaMB<TipoServico> implements Serializable {
    @EJB private TipoServicoFacade facade;

    public List<TipoServico> getListaTipoServico() {
        List<TipoServico> lista = super.getLista();
        Collections.sort(lista);
        return lista;
    }

    public void setListaTipoServico(List<TipoServico> listaTipoServico) {
        super.setLista(listaTipoServico);
    }

    public TipoServico getTipoServico() {
        return super.getElemento();
    }

    public void setTipoServico(TipoServico tipoServico) {
        super.setElemento(tipoServico);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.facade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getTipoServico().getId()==null || this.getTipoServico().getId()==0;
    }

    @Override
    protected TipoServico novainstanciaElemento() {
        return new TipoServico();
    }
}
