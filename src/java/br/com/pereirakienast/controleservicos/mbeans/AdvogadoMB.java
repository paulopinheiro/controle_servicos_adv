package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.mbeans.interfaces.AbListaMB;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author paulopinheiro
 */
@ManagedBean
@ViewScoped
public class AdvogadoMB extends AbListaMB<Advogado> implements Serializable {
    @EJB private AdvogadoFacade facade;

    public AdvogadoMB() {}

    public Advogado getAdvogado() {
        return super.getElemento();
    }

    public void setAdvogado(Advogado advogado) {
        super.setElemento(advogado);
    }

    public List<Advogado> getListaAdvogados() {
        return super.getLista();
    }

    public void setListaAdvogados(List<Advogado> listaAdvogados) {
        super.setLista(listaAdvogados);
    }

    @Override
    protected AdvogadoFacade getFacade() {
        return this.facade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getAdvogado().getId()==null||this.getAdvogado().getId()==0;
    }

    @Override
    protected Advogado novainstanciaElemento() {
        return new Advogado();
    }
}
