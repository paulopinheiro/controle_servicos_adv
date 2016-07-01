package br.com.pereirakienast.controleservicos.entity.cobranca;

import br.com.pereirakienast.controleservicos.entity.ServicoPrestado;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "conta_servico", catalog = "controladv", schema = "public")
public class ContaServico implements Serializable {
    @Id
    @SequenceGenerator(name = "ContaServico_Gen", sequenceName = "conta_servico_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "ContaServico_Gen")
    private Integer id;
    @OneToOne
    @JoinColumn(name="servico_prestado_id",nullable=false,unique=true)
    private ServicoPrestado servico;
    @Column(name="valor",nullable=false,precision=8,scale=2)
    private BigDecimal valor;
    @OneToMany(mappedBy = "conta")
    private List<Parcela> parcelas;

    public ContaServico() {}

    public ContaServico(ServicoPrestado servico, BigDecimal valor) {
        this.servico = servico;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServicoPrestado getServico() {
        return servico;
    }

    public void setServico(ServicoPrestado servico) {
        this.servico = servico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.servico != null ? this.servico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContaServico other = (ContaServico) obj;
        if (this.servico != other.servico && (this.servico == null || !this.servico.equals(other.servico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Conta{" + "servico=" + servico + ", valor=" + valor + '}';
    }

}
