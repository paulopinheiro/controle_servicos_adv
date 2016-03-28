package br.com.pereirakienast.controleservicos.entity.cobranca;

import br.com.pereirakienast.controleservicos.entity.AssessoriaServico;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "repasse_assessoria", catalog = "controladv", schema = "public")
public class RepasseAssessoria implements Serializable, Comparable {
    @Id
    @SequenceGenerator(name = "RepasseAssessoria_Gen", sequenceName = "repasse_assessoria_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "RepasseAssessoria_Gen")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="parcela_id",nullable=false)
    private Parcela parcela;
    @ManyToOne
    @JoinColumn(name="assessoria_servico_id",nullable=false)
    private AssessoriaServico assessoria;
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

    public AssessoriaServico getAssessoria() {
        return assessoria;
    }

    public void setAssessoria(AssessoriaServico assessoria) {
        this.assessoria = assessoria;
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
        hash = 71 * hash + (this.parcela != null ? this.parcela.hashCode() : 0);
        hash = 71 * hash + (this.assessoria != null ? this.assessoria.hashCode() : 0);
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
        final RepasseAssessoria other = (RepasseAssessoria) obj;
        if (this.parcela != other.parcela && (this.parcela == null || !this.parcela.equals(other.parcela))) {
            return false;
        }
        if (this.assessoria != other.assessoria && (this.assessoria == null || !this.assessoria.equals(other.assessoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RepasseAssessoria{" + "parcela=" + parcela + ", assessoria=" + assessoria + ", valor=" + valor + '}';
    }

    @Override
    public int compareTo(Object o) {
        RepasseAssessoria other = (RepasseAssessoria) o;
        if (this.getAssessoria().equals(other.getAssessoria())) {
            return this.getParcela().compareTo(other.getParcela());
        } else return this.getAssessoria().compareTo(other.getAssessoria());
    }
}
