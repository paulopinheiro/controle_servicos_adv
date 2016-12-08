package br.com.pereirakienast.controleservicos.entity.cobranca;

import br.com.pereirakienast.controleservicos.entity.ParceriaServico;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "repasse_parceria", catalog = "controladv", schema = "cobranca")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("P")
public class RepasseParceria extends Obrigacao implements Serializable, Comparable {
    @ManyToOne
    @JoinColumn(name="parcela_id",nullable=false)
    private Parcela parcela;
    @ManyToOne
    @JoinColumn(name="parceria_servico_id",nullable=false)
    private ParceriaServico parceria;

    public RepasseParceria() {}

    public RepasseParceria(Parcela parcela, ParceriaServico parceria, BigDecimal valor) {
        super(valor,parcela.getDataVencimento());
        this.parcela = parcela;
        this.parceria = parceria;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public ParceriaServico getParceria() {
        return parceria;
    }

    public void setParceria(ParceriaServico parceria) {
        this.parceria = parceria;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.parcela != null ? this.parcela.hashCode() : 0);
        hash = 71 * hash + (this.parceria != null ? this.parceria.hashCode() : 0);
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
        final RepasseParceria other = (RepasseParceria) obj;
        if (this.parcela != other.parcela && (this.parcela == null || !this.parcela.equals(other.parcela))) {
            return false;
        }
        if (this.parceria != other.parceria && (this.parceria == null || !this.parceria.equals(other.parceria))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        RepasseParceria other = (RepasseParceria) o;
        if (this.getParceria().equals(other.getParceria())) {
            return this.getParcela().compareTo(other.getParcela());
        } else return this.getParceria().compareTo(other.getParceria());
    }
}
