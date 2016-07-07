package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.ContaServico;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.math.BigDecimal;
import javax.ejb.Stateless;

@Stateless
public class ContaServicoFacade extends AbstractFacade<ContaServico> {

    public ContaServicoFacade() {
        super(ContaServico.class);
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
