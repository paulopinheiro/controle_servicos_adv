package br.com.pereirakienast.controleservicos.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Collator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author paulopinheiro
 */
@Entity
public class Advogado implements Comparable, Serializable {
    @Id
    private Integer id;
    @NotNull
    private String oab;
    private String digestPassword;
    @NotNull
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

    /**
     * 
     * @return Hash da senha escolhida pelo usuário
     */
    public String getDigestPassword() {
        return digestPassword;
    }

    /**
     * Insere hash da senha, gerado com algoritmo SHA para codificação
     * UTF-8
     * @param digestPassword a senha de onde se gerará o hash para persistir no
     * banco de dados
     */
    public void setDigestPassword(String digestPassword) {
        this.digestPassword = digestPassword(digestPassword);
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

     /**
     * Retorna hash para senha informada. 
     * @param password a senha de onde se gerará o hash
     * @return hash gerado gerado com algoritmo SHA para codificação UTF-8
     */
    public static String digestPassword(String password) {
        if ((password==null)||(password.isEmpty())) return null;
        try {
            byte[] bytesOfParameter;
            bytesOfParameter = password.getBytes("UTF-8");
            byte[] digest = MessageDigest.getInstance("SHA").digest(bytesOfParameter);
            return new String(digest,"UTF-8");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Advogado.class.getName()).log(Level.SEVERE, null, ex);
            return password;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Advogado.class.getName()).log(Level.SEVERE, null, ex);
            return password;
        }
    }

    /**
     * Gera hash da senha informada e compara com o hash anteriormente
     * persistido. O hash da senha é implementada com algoritmo SHA para
     * codificação UTF-8
     * @param password a senha informada, de onde se gerará um hash para
     * comparar com o persistido no banco de dados
     * @return True se hash da senha informada é igual ao hash persistido ou 
     * False se são diferentes. Quando ambos são nulos retorna True.
     */
    public boolean confirmaPassword(String password) {
        if ((password==null)&&(this.digestPassword==null)) return true;
        return (this.digestPassword != null) &&
                (password !=null) &&
                (!password.isEmpty()) &&
               (!this.digestPassword.isEmpty()) &&
               this.digestPassword.equals(digestPassword(password));
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
