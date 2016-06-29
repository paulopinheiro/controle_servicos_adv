package br.com.pereirakienast.controleservicos.model;

import br.com.pereirakienast.controleservicos.entity.ServicoPrestado;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CobrancaServico implements Serializable {
    private ServicoPrestado servico;
    private BigDecimal valorServico;
    private Integer quantParcelasServico;
    private double percentualRepasseEscritorio;
    private double percentualRepasseParceria;
    private Integer diaCobrancaMensal;
    private Date dataPrimeiraParcela;

    public CobrancaServico() {}

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

    public BigDecimal getValorServico() {
        if (this.valorServico == null) {
            this.valorServico = new BigDecimal(0);
        }
        return valorServico;
    }

    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
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

    public double getPercentualRepasseEscritorio() {
        return percentualRepasseEscritorio;
    }

    public void setPercentualRepasseEscritorio(double percentualRepasseEscritorio) {
        this.percentualRepasseEscritorio = percentualRepasseEscritorio;
    }

    public Double getPercentualRepasseParceria() {
        return percentualRepasseParceria;
    }

    public void setPercentualRepasseParceria(Double percentualRepasseParceria) {
        this.percentualRepasseParceria = percentualRepasseParceria;
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
//****************************************************************************************

    public BigDecimal getValorParcelaServico() {
        return getValorServico().divide(new BigDecimal(getQuantParcelasServico()),2,RoundingMode.HALF_DOWN);
    }

    public BigDecimal getValorRepasseEscritorio() {
        return getValorServico().multiply(new BigDecimal(getPercentualRepasseEscritorio() / 100));
    }

    public BigDecimal getValorParcelaRepasseEscritorio() {
        return getValorRepasseEscritorio().divide(new BigDecimal(this.getQuantParcelasServico()),2,RoundingMode.HALF_DOWN);
    }

    public BigDecimal getValorRepasseParceriaIndividual() {
        if (this.quantParcerias()==0) return new BigDecimal(0);
        return getValorServico().multiply(new BigDecimal(getPercentualRepasseParceria() / 100));
    }

    public BigDecimal getValorParcelaRepasseParceriaIndividual() {
        return getValorRepasseParceriaIndividual().divide(new BigDecimal(this.getQuantParcelasServico()),2,RoundingMode.HALF_DOWN);
    }

    public BigDecimal getValorRepasseParceria() {
        return getValorRepasseParceriaIndividual().multiply(new BigDecimal(quantParcerias()));
    }

    public BigDecimal getValorParcelaRepasseParceria() {
        return getValorRepasseParceria().divide(new BigDecimal(this.getQuantParcelasServico()),2,RoundingMode.HALF_DOWN);
    }

    public BigDecimal getMaximoRepasseEscritorio() {
        if (this.getValorServico().equals(0)) return new BigDecimal(0);
        return this.getValorServico();
    }

    public BigDecimal getMaximoRepasseParceria() {
        if (this.getValorServico().equals(0)) return new BigDecimal(0);
        if (quantParcerias()==0) return new BigDecimal(0);
        return (this.getValorServico().subtract(this.getValorParcelaRepasseEscritorio())).divide(new BigDecimal(quantParcerias()),2,RoundingMode.HALF_DOWN);
    }

    private int quantParcerias() {
        if (this.getServico().getParcerias()==null) return 0;
        return this.getServico().getParcerias().size();
    }

    public BigDecimal getSaldoParcelaAdvogado() {
        if (this.getValorServico().equals(0)) return new BigDecimal(0);
        return ((this.getValorParcelaServico().subtract(this.getValorParcelaRepasseEscritorio()))).subtract(this.getValorParcelaRepasseParceria());
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
