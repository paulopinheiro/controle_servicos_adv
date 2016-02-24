package br.com.pereirakienast.controleservicos.entity;

import java.io.Serializable;
import java.text.Collator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author paulopinheiro
 */
@Entity
@Table(name = "tipo_servico", catalog = "controladv", schema = "public")
@NamedQueries(value = {
    @NamedQuery(name = "TipoServico.findByNome", query = "select t from TipoServico t where UPPER(t.nome) = :nomeUpper")
})
public class TipoServico implements Comparable, Serializable {
    @Id
    @SequenceGenerator(name = "TipoServico_Gen", sequenceName = "tipo_servico_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "TipoServico_Gen")
    private Integer id;
    @NotNull
    private String nome;
    @OneToMany(mappedBy="tipoServico")
    private List<ServicoPrestado> listaServicosPrestados;

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

    public List<ServicoPrestado> getListaServicosPrestados() {
        return listaServicosPrestados;
    }

    public void setListaServicosPrestados(List<ServicoPrestado> listaServicosPrestados) {
        this.listaServicosPrestados = listaServicosPrestados;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.nome != null ? this.nome.hashCode() : 0);
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
        final TipoServico other = (TipoServico) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

    @Override
    public int compareTo(Object o) {
        TipoServico other = (TipoServico) o;
        Collator c = Collator.getInstance();
        return c.compare(this.getNome(), other.getNome());
    }
}
