package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.Baixa;
import br.com.pereirakienast.controleservicos.entity.cobranca.Pagamento;
import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import br.com.pereirakienast.controleservicos.entity.cobranca.RepasseEscritorio;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class RepasseEscritorioFacade extends AbstractFacade<RepasseEscritorio> {
   @EJB private BaixaFacade baixaFacade;

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

    public void propagarBaixaParcela(Parcela parcela) throws LogicalException {
        if (parcela.isPendenteCobrancaEscritorio()) throw new LogicalException("Não foi encontrada cobrança de repasse ao escritório para essa parcela");
        Baixa propagacao = parcela.getBaixa().copia();
        propagacao.setObrigacao(parcela.getRepasseEscritorio());
        // O campo "Valor Pago" neste momento não é importante, pois o sistema
        // ainda não prevê pagamento parcial da obrigação
        // Caso futuramente isso seja adotado tomar novas decisões (exemplo: 
        // propagar o percentual pago ou deixar a escolha para o usuário)
        // Esta rotina é apenas para evitar que o valor da parcela apareça como
        // o valor pago no repasse
        if (propagacao.isPagamento()) {
            Pagamento pagto = (Pagamento) propagacao;
            pagto.setValorPago(pagto.getObrigacao().getValor());
        }

        baixaFacade.salvar(propagacao);
    }
}
