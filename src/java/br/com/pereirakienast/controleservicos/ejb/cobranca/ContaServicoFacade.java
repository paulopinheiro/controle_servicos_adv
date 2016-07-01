package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.ContaServico;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.model.CobrancaServico;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ContaServicoFacade extends AbstractFacade<ContaServico> {
    @EJB private ParcelaFacade parcelaFacade;

    public ContaServicoFacade() {
        super(ContaServico.class);
    }

    public void gerarConta(CobrancaServico cobranca) throws LogicalException{
        // Se o valor do serviço for nulo ou zero não serão gerados dados de cobrança
        if ((cobranca!=null) && (cobranca.getValorServico()!=null) && cobranca.getValorServico().compareTo(BigDecimal.ZERO)>0) {
            if (cobranca.getServico()==null||cobranca.getServico().getId()==null) throw new LogicalException("Houve um problema ao gerar dados de cobrança do serviço");
            if (cobranca.getQuantParcelasServico()==null||cobranca.getQuantParcelasServico()<1) throw new LogicalException("Deve ser informado um número de parcelas maior ou igual a 1");
            if (cobranca.getPercentualRepasseEscritorio()<0) throw new LogicalException("Percentual de repasse ao escritório não pode ser negativo");
            if (cobranca.getPercentualRepasseParceria()<0) throw new LogicalException("Percentual de repasse de parceria não pode ser negativo");

            ContaServico conta = new ContaServico(cobranca.getServico(),cobranca.getValorServico());
            salvar(conta);

            conta.setParcelas(parcelaFacade.gerarParcelas(conta,cobranca));
        }
    }

    @Override
    public void salvar(ContaServico conta) throws LogicalException {
        if (conta!=null) {
            if ((conta.getServico()==null)||(conta.getServico().getId()==null)) throw new LogicalException("Informe o serviço que está sendo cobrado");
            if (conta.getValor().compareTo(BigDecimal.ZERO)<=0) throw new LogicalException("Valor da conta tem que ser maior que zero");

            if (conta.getId()==null) getEntityManager().persist(conta);
            else getEntityManager().merge(conta);
        }
    }

}
