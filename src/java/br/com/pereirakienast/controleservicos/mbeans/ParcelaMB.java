package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.ejb.cobranca.ParcelaFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.mbeans.comum.AbBasicoMB;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class ParcelaMB extends AbBasicoMB<Parcela> implements Serializable {
    @EJB private ParcelaFacade parcelaFacade;

    private Boolean propagaRepasseEscritorio;
    private Boolean propagaRepasseParcerias;

    public ParcelaMB() {}

    public void registrarPagamento(ActionEvent evt) {
        try {
            this.parcelaFacade.registrarPagamento(getParcela(), isPropagaRepasseEscritorio(), isPropagaRepasseParcerias());
            super.mensagemSucesso("Pagamento registrado com sucesso");
        } catch (LogicalException ex) {
            super.mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            super.mensagemErro(ex.getMessage());
            Logger.getLogger("ParcelaMB").log(Level.SEVERE, ex.getMessage());
        }
    }

    public boolean isPropagaRepasseEscritorio() {
        if (this.propagaRepasseEscritorio==null) return false;
        return propagaRepasseEscritorio;
    }

    public void setPropagaRepasseEscritorio(boolean propagaRepasseEscritorio) {
        this.propagaRepasseEscritorio = propagaRepasseEscritorio;
    }

    public boolean isPropagaRepasseParcerias() {
        if (this.propagaRepasseParcerias==null) return false;
        return propagaRepasseParcerias;
    }

    public void setPropagaRepasseParcerias(boolean propagaRepasseParcerias) {
        this.propagaRepasseParcerias = propagaRepasseParcerias;
    }

    public Parcela getParcela() {
        return super.getElemento();
    }

    public void setParcela(Parcela parcela) {
        super.setElemento(parcela);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.parcelaFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return (this.getParcela().getId()==null||this.getParcela().getId()==0);
    }

    @Override
    protected Parcela novainstanciaElemento() {
        return new Parcela();
    }

}
