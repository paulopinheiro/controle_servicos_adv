package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.RepasseParceria;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.math.BigDecimal;
import javax.ejb.Stateless;

@Stateless
public class RepasseParceriaFacade extends AbstractFacade<RepasseParceria>{

    public RepasseParceriaFacade() {
        super(RepasseParceria.class);
    }

    @Override
    public void salvar(RepasseParceria repasse) throws LogicalException {
        if (repasse!=null) {
            if ((repasse.getParcela()==null)||repasse.getParcela().getId()==null) throw new LogicalException("Informe a parcela da qual é feita o repasse ao parceiro");
            if ((repasse.getParceria()==null)||(repasse.getParceria().getId()==null)) throw new LogicalException("Informe a parceria à qual se deverá fazer o repasse");
            if (repasse.getValor()==null) throw new LogicalException("Informe o valor do repasse");
            if (repasse.getValor().compareTo(BigDecimal.ZERO)<1) throw new LogicalException("Valor do repasse deve ser maior do que zero");

            if (repasse.getId()==null) getEntityManager().persist(repasse);
            else getEntityManager().merge(repasse);
        }
    }

    public void registrarPagamentoRepasse(RepasseParceria repasseParceria) throws LogicalException {
        if (repasseParceria!=null) {
            if (repasseParceria.getDataRepasse()==null) throw new LogicalException("Informe a data do repasse à parceria");
            this.salvar(repasseParceria);
        }
    }
    
}
