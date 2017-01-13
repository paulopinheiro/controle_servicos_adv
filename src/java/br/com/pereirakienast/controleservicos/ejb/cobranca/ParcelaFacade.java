package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.Baixa;
import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ParcelaFacade extends AbstractFacade<Parcela> {
   @EJB private RepasseEscritorioFacade repasseEscritorioFacade;
   @EJB private RepasseParceriaFacade repasseParceriaFacade;
   @EJB private BaixaFacade baixaFacade;

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

    public void registrarBaixaParcela(Parcela parcela, boolean propagaRepasseEscritorio, boolean propagaRepasseParcerias) throws LogicalException {
        if (parcela!=null) {
            if (parcela.getBaixa()==null) throw new LogicalException ("Informe os dados para baixa");

            Baixa baixa = parcela.getBaixa();
            baixa.setObrigacao(parcela);

            baixaFacade.salvar(baixa);

            if (propagaRepasseEscritorio) repasseEscritorioFacade.propagarBaixaParcela(parcela);
            if (propagaRepasseParcerias) repasseParceriaFacade.propagarBaixaParcela(parcela);
        }
    }
}
