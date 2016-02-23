package br.com.pereirakienast.controleservicos.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author paulopinheiro
 */
@Entity
@Table(name = "servico_prestado", catalog = "controladv", schema = "public")
public class ServicoPrestado implements Comparable, Serializable {
    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_prestacao")
    private Date dataPrestacao;
    @ManyToOne
    @JoinColumn(name="advogado_id")
    private Advogado advogado;
    private String detalhes;
    @Column(name="valor_servico")
    private double valorServico;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_pagamento")
    private Date dataPagamento;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_repasse_escritorio")
    private Date dataRepasseEscritorio;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_repasse_advogado")
    private Date dataRepasseAdvogado;
    @ManyToOne
    @JoinColumn(name="tipo_servico_id")
    private TipoServico tipoServico;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getDataPrestacao() {
        return dataPrestacao;
    }

    public void setDataPrestacao(Date dataPrestacao) {
        this.dataPrestacao = dataPrestacao;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public double getValorServico() {
        return valorServico;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataRepasseEscritorio() {
        return dataRepasseEscritorio;
    }

    public void setDataRepasseEscritorio(Date dataRepasseEscritorio) {
        this.dataRepasseEscritorio = dataRepasseEscritorio;
    }

    public Date getDataRepasseAdvogado() {
        return dataRepasseAdvogado;
    }

    public void setDataRepasseAdvogado(Date dataRepasseAdvogado) {
        this.dataRepasseAdvogado = dataRepasseAdvogado;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.cliente != null ? this.cliente.hashCode() : 0);
        hash = 37 * hash + (this.dataPrestacao != null ? this.dataPrestacao.hashCode() : 0);
        hash = 37 * hash + (this.advogado != null ? this.advogado.hashCode() : 0);
        hash = 37 * hash + (this.detalhes != null ? this.detalhes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServicoPrestado other = (ServicoPrestado) obj;
        if (this.cliente != other.cliente && (this.cliente == null || !this.cliente.equals(other.cliente))) {
            return false;
        }
        if (this.dataPrestacao != other.dataPrestacao && (this.dataPrestacao == null || !this.dataPrestacao.equals(other.dataPrestacao))) {
            return false;
        }
        if (this.advogado != other.advogado && (this.advogado == null || !this.advogado.equals(other.advogado))) {
            return false;
        }
        if ((this.detalhes == null) ? (other.detalhes != null) : !this.detalhes.equals(other.detalhes)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ServicoPrestado{" + "cliente=" + cliente + ", dataPrestacao=" + dataPrestacao + ", advogado=" + advogado + ", detalhes=" + detalhes + '}';
    }

    @Override
    public int compareTo(Object o) {
        ServicoPrestado other = (ServicoPrestado) o;
        if (this.getCliente().equals(other.getCliente())) {
            return this.getDataPrestacao().compareTo(other.getDataPrestacao());
        } else return this.getCliente().compareTo(other.getCliente());
    }
}
