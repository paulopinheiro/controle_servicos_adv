package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.Pagamento;
import br.com.pereirakienast.controleservicos.entity.cobranca.RepasseEscritorio;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.math.BigDecimal;
import javax.ejb.Stateless;

@Stateless
public class RepasseEscritorioFacade extends AbstractFacade<RepasseEscritorio> {

    public RepasseEscritorioFacade() {
        super(RepasseEscritorio.class);
    }

    @Override
    public void salvar(RepasseEscritorio repasse) throws LogicalException {
        if (repasse!=null) {
            if ((repasse.getParcela()==null)||repasse.getParcela().getId()==null) throw new LogicalException("Informe a parcela da qual é feita o repasse ao escritório");
            if (repasse.getValor()==null) throw new LogicalException("Informe o valor do repasse");
            if (repasse.getValor().compareTo(BigDecimal.ZERO)<1) throw new LogicalException("Valor do repasse deve ser maior do que zero");

            if (repasse.getId()==null) getEntityManager().persist(repasse);
            else getEntityManager().merge(repasse);
        }
    }

    public void registrarPagamentoRepasse(RepasseEscritorio repasseEscritorio) throws LogicalException {
        if (repasseEscritorio!=null) {
            if (repasseEscritorio.getBaixa()==null || !repasseEscritorio.getBaixa().isPagamento()) throw new LogicalException("Opção inválida para pagamento");
            Pagamento pagto = (Pagamento) repasseEscritorio.getBaixa();
            if (pagto.getDataPagamento()==null) throw new LogicalException("Informe a data do pagamento do repasse ao escritório");
            if (pagto.getValorPago()==null) pagto.setValorPago(pagto.getObrigacao().getValor());
            this.salvar(repasseEscritorio);
        }
    }
}
