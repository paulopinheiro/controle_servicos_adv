package br.com.pereirakienast.controleservicos.entity.cobranca;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "repasse_escritorio", catalog = "controladv", schema = "cobranca")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("E")
public class RepasseEscritorio extends Obrigacao implements Serializable, Comparable {
    @OneToOne
    @JoinColumn(name="parcela_id",nullable=false,unique=true)
    private Parcela parcela;

    public RepasseEscritorio() {}

    public RepasseEscritorio(Parcela parcela, BigDecimal valor) {
        super(valor,parcela.getDataVencimento());
        this.parcela = parcela;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.parcela != null ? this.parcela.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RepasseEscritorio other = (RepasseEscritorio) obj;
        if (this.parcela != other.parcela && (this.parcela == null || !this.parcela.equals(other.parcela))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        RepasseEscritorio other = (RepasseEscritorio) o;
        return this.getParcela().compareTo(other.getParcela());
    }
}
