package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.ParceriaServico;
import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import br.com.pereirakienast.controleservicos.entity.cobranca.RepasseParceria;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RepasseParceriaFacade extends AbstractFacade<RepasseParceria>{

    public RepasseParceriaFacade() {
        super(RepasseParceria.class);
    }

    public List<RepasseParceria> gerarRepassesParceria(Parcela parcela, BigDecimal valorRepasse) throws LogicalException {
        List<RepasseParceria> resposta = new ArrayList<RepasseParceria>();

        for (ParceriaServico parceria:parcela.getConta().getServico().getParcerias()) {
            RepasseParceria rp = new RepasseParceria(parcela,parceria,valorRepasse);
            salvar(rp);
            resposta.add(rp);
        }

        return resposta;
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
    
}
