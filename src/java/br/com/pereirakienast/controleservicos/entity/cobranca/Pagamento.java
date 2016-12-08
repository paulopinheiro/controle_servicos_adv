package br.com.pereirakienast.controleservicos.entity.cobranca;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "pagamento", catalog = "controladv", schema = "cobranca")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("P")
public class Pagamento extends Baixa implements Serializable {
    @Column(name="valor_pago",nullable=false,precision=8,scale=2)
    private BigDecimal valorPago;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_pagamento",nullable=false)
    private Date dataPagamento;

    public Pagamento() {}

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @Override
    public String toString() {
        return "Pagamento{" + "valorPago=" + valorPago + ", dataPagamento=" + dataPagamento + '}';
    }
}
