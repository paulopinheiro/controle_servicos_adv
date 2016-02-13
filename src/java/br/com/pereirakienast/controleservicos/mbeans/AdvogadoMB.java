package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author paulopinheiro
 */
@Named(value = "advogadoMB")
@RequestScoped
public class AdvogadoMB {
    private Advogado advogado;
    @EJB private AdvogadoFacade facade;
    private List<Advogado> lista;

    /**
     * Creates a new instance of AdvogadoMB
     */
    public AdvogadoMB() {}

    public Advogado getAdvogado() {
        if (this.advogado==null) this.advogado = new Advogado();
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public List<Advogado> getLista() {
        if (this.lista==null) this.lista = facade.findAll();
        return lista;
    }

    public void setLista(List<Advogado> lista) {
        this.lista = lista;
    }

}
