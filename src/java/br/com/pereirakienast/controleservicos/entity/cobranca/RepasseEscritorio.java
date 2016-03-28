package br.com.pereirakienast.controleservicos.entity.cobranca;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "repasse_escritorio", catalog = "controladv", schema = "public")
public class RepasseEscritorio implements Serializable, Comparable {
    @Id
    @SequenceGenerator(name = "RepasseEscritorio_Gen", sequenceName = "repasse_escritorio_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "RepasseEscritorio_Gen")
    private Integer id;
    @OneToOne
    @JoinColumn(name="parcela_id",nullable=false,unique=true)
    private Parcela parcela;
    @Column(name="valor",nullable=false,precision=8,scale=2)
    private BigDecimal valor;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_repasse")
    private Date dataRepasse;
    @Column(name="observacao",length=400)
    private String observacao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataRepasse() {
        return dataRepasse;
    }

    public void setDataRepasse(Date dataRepasse) {
        this.dataRepasse = dataRepasse;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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
    public String toString() {
        return "RepasseEscritorio{" + "parcela=" + parcela + ", valor=" + valor + '}';
    }

    @Override
    public int compareTo(Object o) {
        RepasseEscritorio other = (RepasseEscritorio) o;
        return this.getParcela().compareTo(other.getParcela());
    }
}
