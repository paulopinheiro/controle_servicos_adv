package br.com.pereirakienast.controleservicos.entity;

import java.io.Serializable;
import java.text.Collator;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author paulopinheiro
 */
@Entity
public class Advogado implements Comparable, Serializable {
    @Id
    private Integer id;
    private String oab;
    private String md5Password;
    private String nome;
    private boolean administrador;
    private boolean ativo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOab() {
        return oab;
    }

    public void setOab(String oab) {
        this.oab = oab;
    }

    public String getMd5Password() {
        return md5Password;
    }

    public void setMd5Password(String md5Password) {
        this.md5Password = md5Password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Advogado{" + "oab=" + oab + ", nome=" + nome + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.oab != null ? this.oab.hashCode() : 0);
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
        final Advogado other = (Advogado) obj;
        return !((this.oab == null) ? (other.oab != null) : !this.oab.equals(other.oab));
    }

    @Override
    public int compareTo(Object o) {
        Advogado other = (Advogado) o;
        Collator c = Collator.getInstance();
        return c.compare(this.getNome(), other.getNome());
    }
}
