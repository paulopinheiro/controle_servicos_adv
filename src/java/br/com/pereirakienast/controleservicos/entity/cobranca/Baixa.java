package br.com.pereirakienast.controleservicos.entity.cobranca;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "baixa", catalog = "controladv", schema = "cobranca")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="dcolumn")
public class Baixa implements Serializable {
    @Id
    @SequenceGenerator(name = "Baixa_Gen", sequenceName = "cobranca.baixa_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "Baixa_Gen")
    private Integer id;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_baixa",nullable=false)
    private Date dataBaixa;
    @Column(name="observacao",length=400)
    private String observacao;
    @OneToOne
    @JoinColumn(name="obrigacao_id",nullable=false)
    private Obrigacao obrigacao;

    public Baixa() {}

    public Baixa(Obrigacao obrigacao) {
        this.obrigacao = obrigacao;
    }

    public Baixa copia() {
        Baixa resposta = new Baixa(this.getObrigacao(),this.getDataBaixa(),this.getObservacao());

        return resposta;
    }

    public Baixa(Obrigacao obrigacao, Date dataBaixa, String observacao) {
        this.obrigacao = obrigacao;
        this.dataBaixa = dataBaixa;
        this.observacao = observacao;
    }

    public boolean isDispensa() {
        return this instanceof Dispensa;
    }

    public boolean isPagamento() {
        return this instanceof Pagamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(Date dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Obrigacao getObrigacao() {
        return obrigacao;
    }

    public void setObrigacao(Obrigacao obrigacao) {
        this.obrigacao = obrigacao;
    }

    @Override
    public String toString() {
        return "Baixa{" + "dataBaixa=" + dataBaixa + ", obrigacao=" + obrigacao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.obrigacao != null ? this.obrigacao.hashCode() : 0);
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
        final Baixa other = (Baixa) obj;
        if (this.obrigacao != other.obrigacao && (this.obrigacao == null || !this.obrigacao.equals(other.obrigacao))) {
            return false;
        }
        return true;
    }
}
