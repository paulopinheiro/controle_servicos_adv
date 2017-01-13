package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.Baixa;
import br.com.pereirakienast.controleservicos.entity.cobranca.Dispensa;
import br.com.pereirakienast.controleservicos.entity.cobranca.Obrigacao;
import br.com.pereirakienast.controleservicos.entity.cobranca.Pagamento;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.math.BigDecimal;
import javax.ejb.Stateless;

@Stateless
public class BaixaFacade extends AbstractFacade<Baixa>{

    public BaixaFacade() {
        super(Baixa.class);
    }

    private void validarBaixa(Baixa baixa) throws LogicalException {
        if (baixa.getObrigacao()==null) throw new LogicalException("Faltam dados da obrigação a baixar");
        if (baixa.isPagamento()) {
            Pagamento pagto = (Pagamento) baixa;
            if (pagto.getDataPagamento()==null) throw new LogicalException("Informe a data do pagamento");
            if (pagto.getValorPago()==null) throw new LogicalException("Informe o valor pago");
            if (pagto.getValorPago().compareTo(BigDecimal.ZERO)<1) throw new LogicalException("Valor tem que ser maior que zero (é uma dispensa de pagamento?)");
            if (pagto.getDataBaixa()==null) throw new LogicalException("Informe a data da baixa da obrigação");
        } else if (baixa.isDispensa()) {
            Dispensa dispensa = (Dispensa) baixa;
            if (dispensa.getMotivo() == null) throw new LogicalException("Informe o motivo da dispensa");
            if (dispensa.getDataBaixa() == null) throw new LogicalException("Informe a data da dispensa");
        }
    }

    @Override
    public void salvar(Baixa baixa) throws LogicalException {
        if (baixa!=null) {
            if (baixa.getId()==null) {
                registrarBaixa(baixa);
            }
            else {
                alterarBaixa(baixa);
            }
        }
    }

    private void registrarBaixa(Baixa baixa) throws LogicalException {
        if (baixa.isPagamento()) {
            Pagamento pagto = (Pagamento) baixa;
            if (pagto.getValorPago()==null) pagto.setValorPago(pagto.getObrigacao().getValor());
            if (pagto.getDataBaixa()==null) baixa.setDataBaixa(pagto.getDataPagamento());
        }
        validarBaixa(baixa);

        getEntityManager().persist(baixa);

        // Atualizando no cache a relacao OneToOne entre Baixa e Obrigacao
        baixa.getObrigacao().setBaixa(baixa);
    }

    private void alterarBaixa(Baixa baixa) throws LogicalException {
        validarBaixa(baixa);
        getEntityManager().merge(baixa);
    }
}
