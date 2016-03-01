package br.com.pereirakienast.controleservicos.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author paulopinheiro
 */
@Entity
@Table(name = "documento", catalog = "controladv", schema = "public")
@NamedQueries(value = {
    @NamedQuery(name = "Documento.findByCliente", query = "select d from Documento d where d.cliente = :cliente")
})
public class Documento implements Serializable, Comparable {
    @Id
    @SequenceGenerator(name = "Documento_Gen", sequenceName = "documento_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "Documento_Gen")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="tipo_documento_id",nullable=false)
    private TipoDocumento tipo;
    @ManyToOne
    @JoinColumn(name="cliente_id",nullable=false)
    private Cliente cliente;
    @Column(nullable=false,length=60)
    private String numero;
    @Column(length=255)
    private String orgaoEmissor;

    public Documento(){}

    public Documento(Cliente cliente) {
        this.cliente = cliente;
    }

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

    @Override
    public int compareTo(Object o) {
        Documento outro = (Documento) o;
        if (o==null) return 0;
        if (!outro.getCliente().equals(this.getCliente()))
            return this.getCliente().compareTo(outro.getCliente());
        return this.getTipo().compareTo(outro.getTipo());
    }
}
