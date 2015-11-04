package br.com.pereirakienast.controleservicos.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author paulopinheiro
 */
@Entity
public class Historico implements Comparable, Serializable {
    @Id
    private Integer id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataHistorico;
    private String descricao;
    @ManyToOne
    private Cliente cliente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.dataHistorico != null ? this.dataHistorico.hashCode() : 0);
        hash = 37 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
        hash = 37 * hash + (this.cliente != null ? this.cliente.hashCode() : 0);
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
        final Historico other = (Historico) obj;
        if (this.dataHistorico != other.dataHistorico && (this.dataHistorico == null || !this.dataHistorico.equals(other.dataHistorico))) {
            return false;
        }
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        if (this.cliente != other.cliente && (this.cliente == null || !this.cliente.equals(other.cliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Historico{" + "dataHistorico=" + dataHistorico + ", cliente=" + cliente  + ", descricao=" + descricao + '}';
    }

    @Override
    public int compareTo(Object o) {
        Historico other = (Historico) o;
        if (this.getCliente().equals(other.getCliente())) {
            return this.getDataHistorico().compareTo(other.getDataHistorico());
        } else return this.getCliente().compareTo(other.getCliente());
    }
}
