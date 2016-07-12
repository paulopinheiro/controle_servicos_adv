package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named
@ViewScoped
public class ParcelaMB implements Serializable {
    private Parcela parcela;

    public ParcelaMB() {}

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

}
