package br.com.pereirakienast.controleservicos.entity;

import java.io.Serializable;
import java.text.Collator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author paulopinheiro
 */
@Entity
@Table(name = "tipo_documento", catalog = "controladv", schema = "public")
@NamedQueries(value = {
    @NamedQuery(name = "TipoDocumento.findByNome", query = "select t from TipoDocumento t where UPPER(t.nome) = :nomeUpper")
})
public class TipoDocumento implements Comparable, Serializable {
    @Id@SequenceGenerator(name = "TipoDocumento_Gen", sequenceName = "tipo_documento_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "TipoDocumento_Gen")
    private Integer id;
    @Column(nullable=false,length=40)
    private String nome;
    @OneToMany(mappedBy="tipo")
    private List<Documento> listaDocumentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Documento> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<Documento> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.nome != null ? this.nome.hashCode() : 0);
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
        final TipoDocumento other = (TipoDocumento) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int compareTo(Object o) {
        TipoDocumento other = (TipoDocumento) o;
        Collator c = Collator.getInstance();
        return c.compare(this.getNome(), other.getNome());
    }

}
