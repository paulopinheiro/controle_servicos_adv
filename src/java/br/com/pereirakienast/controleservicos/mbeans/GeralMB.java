package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AdvogadoFacade;
import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.util.DadosSessao;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GeralMB implements Serializable {
    @EJB private AdvogadoFacade facade;
    private List<Advogado> advogadosAtivos;
    private Advogado advogado;

    public GeralMB() {}

    public Advogado getAdvogado() {
        if (this.advogado == null) this.advogado = new Advogado();
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public List<Advogado> getAdvogadosAtivos() {
        if (this.advogadosAtivos==null) this.advogadosAtivos = this.facade.findAtivos();
        Collections.sort(advogadosAtivos);
        return advogadosAtivos;
    }

    public void setAdvogadosAtivos(List<Advogado> advogadosAtivos) {
        this.advogadosAtivos = advogadosAtivos;
    }

    public boolean isVazioAdvogadosAtivos() {
        return this.getAdvogadosAtivos().isEmpty();
    }

    public String conectar() {
        if (this.getAdvogado().getId()!=null) {
            setAdvogadoSessao(this.getAdvogado());
            return "/home.xhtml";
        }
        else
            return "";
    }

    private void setAdvogadoSessao(Advogado advogadoSessao) {
        DadosSessao.setAdvogadoSessao(advogadoSessao);
    }
}
