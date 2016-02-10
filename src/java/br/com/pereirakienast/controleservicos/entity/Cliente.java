package br.com.pereirakienast.controleservicos.entity;

import java.io.Serializable;
import java.text.Collator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author paulopinheiro
 */
@Entity
public class Cliente implements Comparable, Serializable {
    @Id
    private Integer id;
    private String cpfCnpj;
    private String nome;
    private String qualificacao;
    private String telefone;
    private String email;
    private String redeSocial;
    private boolean ativo;
    @OneToMany(mappedBy = "cliente")
    private List<Historico> historicos;
    @OneToMany(mappedBy = "cliente")
    private List<Documento> documentos;
    @OneToMany(mappedBy = "cliente")
    private List<ServicoPrestado> servicosPrestados;

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
        return c.compare(this.getNome(), other.getNome());
    }
}
