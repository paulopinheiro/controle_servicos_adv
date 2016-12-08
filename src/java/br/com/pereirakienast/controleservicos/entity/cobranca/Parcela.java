package br.com.pereirakienast.controleservicos.entity.cobranca;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "parcela", catalog = "controladv", schema = "cobranca")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("S")
public class Parcela extends Obrigacao implements Serializable, Comparable {
    @ManyToOne
    @JoinColumn(name="conta_servico_id",nullable=false)
    private ContaServico conta;
    @OneToOne(mappedBy = "parcela")
    private RepasseEscritorio repasseEscritorio;
    @OneToMany(mappedBy = "parcela")
    private List<RepasseParceria> repassesParcerias;

    public Parcela() {}

    public Parcela(ContaServico conta, BigDecimal valor, Date dataVencimento) {
        super(valor,dataVencimento);
        this.conta = conta;
    }

    public boolean isPendenteCobrancaEscritorio() {
        return this.repasseEscritorio==null;
    }

    public boolean isPendenteCobrancaParcerias() {
        return this.repassesParcerias==null || this.repassesParcerias.isEmpty();
    }

    public ContaServico getConta() {
        return conta;
    }

    public void setConta(ContaServico conta) {
        this.conta = conta;
    }

    public RepasseEscritorio getRepasseEscritorio() {
        return repasseEscritorio;
    }

    public void setRepasseEscritorio(RepasseEscritorio repasseEscritorio) {
        this.repasseEscritorio = repasseEscritorio;
    }

    public List<RepasseParceria> getRepassesParcerias() {
        return repassesParcerias;
    }

    public void setRepassesParcerias(List<RepasseParceria> repassesParcerias) {
        this.repassesParcerias = repassesParcerias;
    }

    @Override
    public int compareTo(Object o) {
        Parcela other = (Parcela) o;
        if (this.getConta()==null||other.getConta()==null) return 0;
        if (this.getConta().equals(other.getConta())) {
            return this.getDataVencimento().compareTo(other.getDataVencimento());
        } else return this.getConta().getServico().compareTo(other.getConta().getServico());
    }
}
