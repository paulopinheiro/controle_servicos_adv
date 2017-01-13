package br.com.pereirakienast.controleservicos.mbeans;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.ejb.cobranca.BaixaFacade;
import br.com.pereirakienast.controleservicos.ejb.cobranca.ParcelaFacade;
import br.com.pereirakienast.controleservicos.ejb.cobranca.RepasseEscritorioFacade;
import br.com.pereirakienast.controleservicos.ejb.cobranca.RepasseParceriaFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.Baixa;
import br.com.pereirakienast.controleservicos.entity.cobranca.Dispensa;
import br.com.pereirakienast.controleservicos.entity.cobranca.Obrigacao;
import br.com.pereirakienast.controleservicos.entity.cobranca.Pagamento;
import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import br.com.pereirakienast.controleservicos.entity.cobranca.RepasseParceria;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.mbeans.comum.AbBasicoMB;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ParcelaMB extends AbBasicoMB<Parcela> implements Serializable {
    @EJB private ParcelaFacade parcelaFacade;
    @EJB private RepasseEscritorioFacade repasseEscritorioFacade;
    @EJB private RepasseParceriaFacade repasseParceriaFacade;
    @EJB private BaixaFacade baixaFacade;

    private Boolean propagaRepasseEscritorio;
    private Boolean propagaRepasseParcerias;

    private RepasseParceria repasseParceriaSelecionada;

    private Integer escolhaTipoObrigacao;
    private Integer escolhaTipoBaixa;

    private Baixa baixa;
    private Pagamento pagamento;
    private Dispensa dispensa;
    private Obrigacao obrigacao;

    public ParcelaMB() {}

    public void registrarBaixa() {
        try {
            // Se a obrigacao for do tipo parcela chama metodo personalizado que
            // propaga a baixa para os repasses se desejado
            if (this.getEscolhaTipoObrigacao()==0) {
                getParcela().setBaixa(getBaixa().copia());
                this.parcelaFacade.registrarBaixaParcela(getParcela(), isPropagaRepasseEscritorio(), isPropagaRepasseParcerias());
            } else {
                this.getBaixa().setObrigacao(getObrigacao());
                this.baixaFacade.salvar(this.getBaixa().copia());
            }
            super.mensagemSucesso("Baixa registrada com sucesso");

            this.limparDados();
        } catch (LogicalException ex) {
            super.mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            super.mensagemErro(ex.getMessage());
            Logger.getLogger("ParcelaMB").log(Level.SEVERE, ex.getMessage());
        }
    }

    private void limparDados() {
        setObrigacao(null);
        setBaixa(null);
        setDispensa(null);
        setPagamento(null);
    }

    // Caixa de dialogo dinamica de registro de baixa
    // Recebe como parametros o tipo de obrigacao que se estah baixando e o tipo
    // de baixa. A partir desses valores sao decididos que campos exibir e se
    // referencia as entidades abstratas Obrigacao e Baixa
    public void dialogoBaixa(Integer escolhaTipoObrigacao, Integer escolhaTipoBaixa) {
        setEscolhaTipoObrigacao(escolhaTipoObrigacao);
        setEscolhaTipoBaixa(escolhaTipoBaixa);

        if (escolhaTipoObrigacao==0) setObrigacao(getParcela());
        else  {
            if (escolhaTipoObrigacao==1) setObrigacao(getParcela().getRepasseEscritorio());
            else setObrigacao(this.getRepasseParceriaSelecionada());
        }

        if (escolhaTipoBaixa==0) setBaixa(this.getPagamento());
        else setBaixa(this.getDispensa());
    }

    public boolean isPropagaRepasseEscritorio() {
        if (this.propagaRepasseEscritorio==null) return false;
        return propagaRepasseEscritorio;
    }

    public void setPropagaRepasseEscritorio(boolean propagaRepasseEscritorio) {
        this.propagaRepasseEscritorio = propagaRepasseEscritorio;
    }

    public boolean isPropagaRepasseParcerias() {
        if (this.propagaRepasseParcerias==null) return false;
        return propagaRepasseParcerias;
    }

    public void setPropagaRepasseParcerias(boolean propagaRepasseParcerias) {
        this.propagaRepasseParcerias = propagaRepasseParcerias;
    }

    public Parcela getParcela() {
        return super.getElemento();
    }

    public void setParcela(Parcela parcela) {
        super.setElemento(parcela);
    }

    public RepasseParceria getRepasseParceriaSelecionada() {
        if (this.repasseParceriaSelecionada==null) this.repasseParceriaSelecionada = new RepasseParceria();
        return repasseParceriaSelecionada;
    }

    public void setRepasseParceriaSelecionada(RepasseParceria repasseParceriaSelecionada) {
        this.repasseParceriaSelecionada = repasseParceriaSelecionada;
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.parcelaFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return (this.getParcela().getId()==null||this.getParcela().getId()==0);
    }

    @Override
    protected Parcela novainstanciaElemento() {
        return new Parcela();
    }

    public Pagamento getPagamento() {
        if (this.pagamento==null) pagamento = new Pagamento();
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Dispensa getDispensa() {
        if (this.dispensa==null) this.dispensa = new Dispensa();
        return dispensa;
    }

    public void setDispensa(Dispensa dispensa) {
        this.dispensa = dispensa;
    }

    public Integer getEscolhaTipoBaixa() {
        if (escolhaTipoBaixa==null) escolhaTipoBaixa=0;
        return escolhaTipoBaixa;
    }

    public void setEscolhaTipoBaixa(Integer escolhaTipoBaixa) {
        this.escolhaTipoBaixa = escolhaTipoBaixa;
    }

    public Obrigacao getObrigacao() {
        if (obrigacao==null) obrigacao = new Obrigacao() {};
        return obrigacao;
    }

    public Integer getEscolhaTipoObrigacao() {
        if (escolhaTipoObrigacao == null) escolhaTipoObrigacao = 0;
        return escolhaTipoObrigacao;
    }

    public void setEscolhaTipoObrigacao(Integer escolhaTipoObrigacao) {
        this.escolhaTipoObrigacao = escolhaTipoObrigacao;
    }

    public void setObrigacao(Obrigacao obrigacao) {
        this.obrigacao = obrigacao;
    }

    public Baixa getBaixa() {
        if (this.baixa==null) this.baixa = new Baixa();
        return baixa;
    }

    public void setBaixa(Baixa baixa) {
        this.baixa = baixa;
    }
}
