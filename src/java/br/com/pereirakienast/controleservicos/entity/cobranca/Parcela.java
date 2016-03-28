package br.com.pereirakienast.controleservicos.entity.cobranca;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "parcela", catalog = "controladv", schema = "public")
public class Parcela implements Serializable, Comparable {
    @Id
    @SequenceGenerator(name = "Parcela_Gen", sequenceName = "parcela_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "Parcela_Gen")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="conta_servico_id",nullable=false)
    private ContaServico conta;
    @Column(name="valor",nullable=false,precision=8,scale=2)
    private BigDecimal valor;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_vencimento",nullable=false)
    private Date dataVencimento;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_pagamento")
    private Date dataPagamento;
    @OneToOne(mappedBy = "parcela")
    private RepasseEscritorio repasseEscritorio;
    @OneToMany(mappedBy = "parcela")
    private List<RepasseAssessoria> repassesAssessorias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContaServico getConta() {
        return conta;
    }

    public void setConta(ContaServico conta) {
        this.conta = conta;
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

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public RepasseEscritorio getRepasseEscritorio() {
        return repasseEscritorio;
    }

    public void setRepasseEscritorio(RepasseEscritorio repasseEscritorio) {
        this.repasseEscritorio = repasseEscritorio;
    }

    public List<RepasseAssessoria> getRepassesAssessorias() {
        return repassesAssessorias;
    }

    public void setRepassesAssessorias(List<RepasseAssessoria> repassesAssessorias) {
        this.repassesAssessorias = repassesAssessorias;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.conta != null ? this.conta.hashCode() : 0);
        hash = 79 * hash + (this.dataVencimento != null ? this.dataVencimento.hashCode() : 0);
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
        final Parcela other = (Parcela) obj;
        if (this.conta != other.conta && (this.conta == null || !this.conta.equals(other.conta))) {
            return false;
        }
        if (this.dataVencimento != other.dataVencimento && (this.dataVencimento == null || !this.dataVencimento.equals(other.dataVencimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Parcela{" + "conta=" + conta + ", valor=" + valor + ", dataVencimento=" + dataVencimento + '}';
    }

    @Override
    public int compareTo(Object o) {
        Parcela other = (Parcela) o;
        if (this.getConta().equals(other.getConta())) {
            return this.getDataVencimento().compareTo(other.getDataVencimento());
        } else return this.getConta().getServico().compareTo(other.getConta().getServico());
    }

}
