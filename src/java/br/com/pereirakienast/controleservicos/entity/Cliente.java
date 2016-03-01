package br.com.pereirakienast.controleservicos.entity;

import java.io.Serializable;
import java.text.Collator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cliente", catalog = "controladv", schema = "public")
@NamedQueries(value = {
    @NamedQuery(name = "Cliente.findAtivos", query = "select c from Cliente c where c.ativo = true")
})
public class Cliente implements Comparable, Serializable {
    @Id
    @SequenceGenerator(name = "Cliente_Gen", sequenceName = "cliente_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "Cliente_Gen")
    private Integer id;
    @Column(name="cpf_cnpj", length=20)
    private String cpfCnpj;
    @Column(nullable=false,length=255)
    private String nome;
    @Column(length=800)
    private String qualificacao;
    @Column(length=40)
    private String telefone;
    @Column(length=60)
    private String email;
    @Column(length=800)
    private String endereco;
    @Column(name="rede_social",length=255)
    private String redeSocial;
    private boolean ativo;
    @ManyToOne
    @JoinColumn(name="advogado_id",nullable=false)
    private Advogado advogado;
    @OneToMany(mappedBy = "cliente")
    private List<Historico> historicos;
    @OneToMany(mappedBy = "cliente")
    private List<Documento> documentos;
    @OneToMany(mappedBy = "cliente")
    private List<ServicoPrestado> servicosPrestados;

    public Cliente() {
        this.ativo=true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQualificacao() {
        return qualificacao;
    }

    public void setQualificacao(String qualificacao) {
        this.qualificacao = qualificacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRedeSocial() {
        return redeSocial;
    }

    public void setRedeSocial(String redeSocial) {
        this.redeSocial = redeSocial;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public List<Historico> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<Historico> historicos) {
        this.historicos = historicos;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public List<ServicoPrestado> getServicosPrestados() {
        return servicosPrestados;
    }

    public void setServicosPrestados(List<ServicoPrestado> servicosPrestados) {
        this.servicosPrestados = servicosPrestados;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.cpfCnpj != null ? this.cpfCnpj.hashCode() : 0);
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
        final Cliente other = (Cliente) obj;
        if ((this.cpfCnpj == null) ? (other.cpfCnpj != null) : !this.cpfCnpj.equals(other.cpfCnpj)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nome=" + nome + '}';
    }

    @Override
    public int compareTo(Object o) {
        Cliente other = (Cliente) o;
        Collator c = Collator.getInstance();
        if ((this.getNome()==null)||(other.getNome()==null)) return 0;
        return c.compare(this.getNome().toUpperCase(), other.getNome().toUpperCase());
    }
}
