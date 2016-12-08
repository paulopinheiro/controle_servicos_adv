package br.com.pereirakienast.controleservicos.entity.cobranca;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "dispensa", catalog = "controladv", schema = "cobranca")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("D")
public class Dispensa extends Baixa implements Serializable {
    @Column(name="motivo",nullable=false,length=150)
    private String motivo;

    public Dispensa() {}

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Dispensa(" + "motivo=" + motivo + ')';
    }

}
