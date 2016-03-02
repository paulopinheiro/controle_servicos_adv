package br.com.pereirakienast.controleservicos.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author paulopinheiro
 */
@Entity
public class Documento implements Serializable {
    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name="tipo_documento_id")
    private TipoDocumento tipo;
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
    private String numero;
    private String orgaoEmissor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocumento tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.tipo != null ? this.tipo.hashCode() : 0);
        hash = 17 * hash + (this.cliente != null ? this.cliente.hashCode() : 0);
        hash = 17 * hash + (this.numero != null ? this.numero.hashCode() : 0);
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
        final Documento other = (Documento) obj;
        if (this.tipo != other.tipo && (this.tipo == null || !this.tipo.equals(other.tipo))) {
            return false;
        }
        if (this.cliente != other.cliente && (this.cliente == null || !this.cliente.equals(other.cliente))) {
            return false;
        }
        if ((this.numero == null) ? (other.numero != null) : !this.numero.equals(other.numero)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Documento{" + "id=" + id + ", tipo=" + tipo.getNome() + ", cliente=" + cliente.getNome() + ", numero=" + numero + ", orgaoEmissor=" + orgaoEmissor + '}';
    }

}
