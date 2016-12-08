package br.com.pereirakienast.controleservicos.entity.cobranca;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "obrigacao", catalog = "controladv", schema = "cobranca")
@DiscriminatorColumn(name="dcolumn")
public abstract class Obrigacao implements Serializable {
    @Id
    @SequenceGenerator(name = "Obrigacao_Gen", sequenceName = "cobranca.obrigacao_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "Obrigacao_Gen")
    private Integer id;
    @Column(name="valor",nullable=false,precision=8,scale=2)
    private BigDecimal valor;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_vencimento",nullable=false)
    private Date dataVencimento;
    @Column(name="observacao",length=400)
    private String observacao;

    @OneToOne (mappedBy="obrigacao")
    private Baixa baixa;

    public Obrigacao() {}

    public Obrigacao(BigDecimal valor, Date dataVencimento) {
        this.valor = valor;
        this.dataVencimento = dataVencimento;
    }

    public boolean isPendente() {
        return this.baixa==null;
    }

    public boolean isVencida() {
        return this.getDataVencimento().before(new Date()) && this.baixa==null;
    }

    public Baixa getBaixa() {
        return baixa;
    }

    public void setBaixa(Baixa baixa) {
        this.baixa = baixa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Obrigacao other = (Obrigacao) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Obrigação{" + "id=" + id + ", valor=" + valor + ", dataVencimento=" + dataVencimento + '}';
    }

}
