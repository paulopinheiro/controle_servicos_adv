package br.com.pereirakienast.controleservicos.entity;

import br.com.pereirakienast.controleservicos.entity.cobranca.ContaServico;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "servico_prestado", catalog = "controladv", schema = "public")
public class ServicoPrestado implements Comparable, Serializable {

    @Id
    @SequenceGenerator(name = "ServicoPrestado_Gen", sequenceName = "servico_prestado_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "ServicoPrestado_Gen")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="cliente_id",nullable=false)
    private Cliente cliente;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="data_prestacao",nullable=false)
    private Date dataPrestacao;
    @ManyToOne
    @JoinColumn(name="advogado_id",nullable=false)
    private Advogado advogado;
    @Column(name="detalhes", length=800)
    private String detalhes;
    @Column(name="observacao", length=800)
    private String observacao;
    @ManyToOne
    @JoinColumn(name="tipo_servico_id",nullable=false)
    private TipoServico tipoServico;
    @OneToOne(mappedBy = "servico")
    private ContaServico conta;
    @OneToMany(mappedBy = "servico")
    private List<AssessoriaServico> assessorias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getDataPrestacao() {
        return dataPrestacao;
    }

    public void setDataPrestacao(Date dataPrestacao) {
        this.dataPrestacao = dataPrestacao;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public ContaServico getConta() {
        return conta;
    }

    public void setConta(ContaServico conta) {
        this.conta = conta;
    }

    public List<AssessoriaServico> getAssessorias() {
        return assessorias;
    }

    public void setAssessorias(List<AssessoriaServico> assessorias) {
        this.assessorias = assessorias;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.cliente != null ? this.cliente.hashCode() : 0);
        hash = 37 * hash + (this.dataPrestacao != null ? this.dataPrestacao.hashCode() : 0);
        hash = 37 * hash + (this.advogado != null ? this.advogado.hashCode() : 0);
        hash = 37 * hash + (this.detalhes != null ? this.detalhes.hashCode() : 0);
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
        final ServicoPrestado other = (ServicoPrestado) obj;
        if (this.cliente != other.cliente && (this.cliente == null || !this.cliente.equals(other.cliente))) {
            return false;
        }
        if (this.dataPrestacao != other.dataPrestacao && (this.dataPrestacao == null || !this.dataPrestacao.equals(other.dataPrestacao))) {
            return false;
        }
        if (this.advogado != other.advogado && (this.advogado == null || !this.advogado.equals(other.advogado))) {
            return false;
        }
        if ((this.detalhes == null) ? (other.detalhes != null) : !this.detalhes.equals(other.detalhes)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ServicoPrestado{" + "cliente=" + cliente + ", dataPrestacao=" + dataPrestacao + ", advogado=" + advogado + ", detalhes=" + detalhes + '}';
    }

    @Override
    public int compareTo(Object o) {
        ServicoPrestado other = (ServicoPrestado) o;
        if (this.getCliente().equals(other.getCliente())) {
            return this.getDataPrestacao().compareTo(other.getDataPrestacao());
        } else return this.getCliente().compareTo(other.getCliente());
    }
}
