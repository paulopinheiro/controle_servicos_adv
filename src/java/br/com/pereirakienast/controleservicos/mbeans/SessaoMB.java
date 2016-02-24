package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author paulopinheiro
 */
@ManagedBean
@SessionScoped
public class SessaoMB implements Serializable {
    @EJB private AdvogadoFacade facade;
    private List<Advogado> advogadosAtivos;
    private Advogado advogadoConectado;

    public SessaoMB() {}

    public Advogado getAdvogadoConectado() {
        if (this.advogadoConectado == null) this.advogadoConectado = new Advogado();
        return advogadoConectado;
    }

    public void setAdvogadoConectado(Advogado advogadoConectado) {
        this.advogadoConectado = advogadoConectado;
    }

    public List<Advogado> getAdvogadosAtivos() {
        if (this.advogadosAtivos==null) this.advogadosAtivos = this.facade.findAtivos();
        return advogadosAtivos;
    }

    public void setAdvogadosAtivos(List<Advogado> advogadosAtivos) {
        this.advogadosAtivos = advogadosAtivos;
    }

    public String conectar () {
        if (this.getAdvogadoConectado().getId()!=null)
            return "cadastros/cadAdvogado.xhtml";
        else
            return "";
    }
}
