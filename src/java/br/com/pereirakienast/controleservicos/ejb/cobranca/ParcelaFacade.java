package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.math.BigDecimal;
import javax.ejb.Stateless;

@Stateless
public class ParcelaFacade extends AbstractFacade<Parcela> {

    public ParcelaFacade() {
        super(Parcela.class);
    }

    @Override
    public void salvar(Parcela parcela) throws LogicalException {
        if (parcela != null) {
            if (parcela.getConta()==null) throw new LogicalException("Informe a conta à qual pertence esta parcela");
            if (parcela.getValor()==null) throw new LogicalException("Informe o valor da parcela");
            if (parcela.getValor().compareTo(BigDecimal.ZERO)<1) throw new LogicalException("Valor da parcela deve ser maior do que zero");
            if (parcela.getDataVencimento()==null) throw new LogicalException("Informe a data de vencimento da parcela");

            if (parcela.getId()==null) getEntityManager().persist(parcela);
            else getEntityManager().merge(parcela);
        }
    }
}
