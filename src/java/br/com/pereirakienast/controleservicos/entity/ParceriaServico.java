package br.com.pereirakienast.controleservicos.entity;

import br.com.pereirakienast.controleservicos.entity.cobranca.RepasseParceria;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "parceria_servico", catalog = "controladv", schema = "public")
public class ParceriaServico implements Serializable, Comparable {
    @Id
    @SequenceGenerator(name = "ParceriaServico_Gen", sequenceName = "parceria_servico_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "ParceriaServico_Gen")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="servico_prestado_id",nullable=false)
    private ServicoPrestado servico;
    @ManyToOne
    @JoinColumn(name="advogado_id",nullable=false)
    private Advogado advogado;
    @Column(name="detalhes",length=400)
    private String detalhes;
    @OneToMany(mappedBy = "parceria")
    private List<RepasseParceria> repassesParcelas;

    public ParceriaServico() {}

    public ParceriaServico(ServicoPrestado servico) {
        this.servico = servico;
    }

    public ParceriaServico(ServicoPrestado servico, Advogado advogado) throws LogicalException {
        this.servico = servico;
        this.advogado = advogado;
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

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public List<RepasseParceria> getRepassesParcelas() {
        return repassesParcelas;
    }

    public void setRepassesParcelas(List<RepasseParceria> repassesParcelas) {
        this.repassesParcelas = repassesParcelas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.servico != null ? this.servico.hashCode() : 0);
        hash = 17 * hash + (this.advogado != null ? this.advogado.hashCode() : 0);
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
        final ParceriaServico other = (ParceriaServico) obj;
        if (this.servico != other.servico && (this.servico == null || !this.servico.equals(other.servico))) {
            return false;
        }
        if (this.advogado != other.advogado && (this.advogado == null || !this.advogado.equals(other.advogado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AsessoriaServico{" + "servico=" + servico + ", advogado=" + advogado + "}";
    }

    @Override
    public int compareTo(Object o) {
        ParceriaServico other = (ParceriaServico) o;
        if (!this.getServico().equals(other.getServico())) {
            return this.getServico().compareTo(other.getServico());
        } else return this.getAdvogado().compareTo(other.getAdvogado());
    }
}
