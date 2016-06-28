package br.com.pereirakienast.controleservicos.model;

import br.com.pereirakienast.controleservicos.entity.ServicoPrestado;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CobrancaServico implements Serializable {

    private ServicoPrestado servico;
    private BigDecimal valorParcelaServico;
    private Integer quantParcelasServico;
    private BigDecimal valorParcelaRepasseEscritorio;
    private BigDecimal valorParcelaRepasseParceria;
    private Integer diaCobrancaMensal;
    private Date dataPrimeiraParcela;

    public CobrancaServico() {
    }

    public CobrancaServico(ServicoPrestado servico) {
        this.servico = servico;
    }

    public ServicoPrestado getServico() {
        if (this.servico==null) this.servico = new ServicoPrestado();
        return servico;
    }

    public void setServico(ServicoPrestado servico) {
        this.servico = servico;
    }

    public BigDecimal getValorParcelaServico() {
        if (this.valorParcelaServico == null) {
            this.valorParcelaServico = new BigDecimal(0);
        }
        return valorParcelaServico;
    }

    public void setValorParcelaServico(BigDecimal valorParcelaServico) {
        this.valorParcelaServico = valorParcelaServico;
    }

    public Integer getQuantParcelasServico() {
        if (this.quantParcelasServico == null) {
            this.quantParcelasServico = 1;
        }
        return quantParcelasServico;
    }

    public void setQuantParcelasServico(Integer quantParcelasServico) {
        this.quantParcelasServico = quantParcelasServico;
    }

    public BigDecimal getValorParcelaRepasseEscritorio() {
        if (this.valorParcelaRepasseEscritorio == null) {
            this.valorParcelaRepasseEscritorio = new BigDecimal(0);
        }
        return valorParcelaRepasseEscritorio;
    }

    public void setValorParcelaRepasseEscritorio(BigDecimal valorParcelaRepasseEscritorio) {
        this.valorParcelaRepasseEscritorio = valorParcelaRepasseEscritorio;
    }

    public BigDecimal getValorParcelaRepasseParceria() {
        if (this.valorParcelaRepasseParceria == null) {
            this.valorParcelaRepasseParceria = new BigDecimal(0);
        }
        return valorParcelaRepasseParceria;
    }

    public void setValorParcelaRepasseParceria(BigDecimal valorParcelaRepasseParceria) {
        this.valorParcelaRepasseParceria = valorParcelaRepasseParceria;
    }

    public Integer getDiaCobrancaMensal() {
        if (this.diaCobrancaMensal == null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(new Date());
            this.diaCobrancaMensal = cal.get(Calendar.DAY_OF_MONTH);
        }
        return diaCobrancaMensal;
    }

    public void setDiaCobrancaMensal(Integer diaCobrancaMensal) {
        this.diaCobrancaMensal = diaCobrancaMensal;
    }

    public Date getDataPrimeiraParcela() {
        if (this.dataPrimeiraParcela==null) this.dataPrimeiraParcela = new Date();
        return dataPrimeiraParcela;
    }

    public void setDataPrimeiraParcela(Date dataPrimeiraParcela) {
        this.dataPrimeiraParcela = dataPrimeiraParcela;
    }
    
    public BigDecimal getMaximoRepasseEscritorio() {
        if (this.getValorParcelaServico().equals(0)) return new BigDecimal(0);
        return this.getValorParcelaServico();
    }

    public BigDecimal getMaximoRepasseParceria() {
        if (this.getValorParcelaServico().equals(0)) return new BigDecimal(0);
        if (quantParcerias()==0) return new BigDecimal(0);
        return (this.getValorParcelaServico().subtract(this.getValorParcelaRepasseEscritorio())).divide(new BigDecimal(quantParcerias()));
    }

    private int quantParcerias() {
        if (this.getServico().getParcerias()==null) return 0;
        return this.getServico().getParcerias().size();
    }

    public BigDecimal getTotalRepasseMensalParceria() {
        if (quantParcerias()==0) return new BigDecimal(0);
        return (this.getValorParcelaRepasseParceria().multiply(new BigDecimal(this.getServico().getParcerias().size())));
    }

    public BigDecimal getSaldoParcelaAdvogado() {
        if (this.getValorParcelaServico().equals(0)) return new BigDecimal(0);
        return ((this.getValorParcelaServico().subtract(this.getValorParcelaRepasseEscritorio()))).subtract(this.getTotalRepasseMensalParceria());
    }

    public BigDecimal getCustoTotalServico() {
        return this.getValorParcelaServico().multiply(new BigDecimal(this.getQuantParcelasServico()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.servico != null ? this.servico.hashCode() : 0);
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
        final CobrancaServico other = (CobrancaServico) obj;
        if (this.servico != other.servico && (this.servico == null || !this.servico.equals(other.servico))) {
            return false;
        }
        return true;
    }
}
