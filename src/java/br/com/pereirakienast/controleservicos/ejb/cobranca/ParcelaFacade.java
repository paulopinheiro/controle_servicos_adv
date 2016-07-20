package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import br.com.pereirakienast.controleservicos.entity.cobranca.RepasseParceria;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ParcelaFacade extends AbstractFacade<Parcela> {
   @EJB private RepasseEscritorioFacade repasseEscritorioFacade;
   @EJB private RepasseParceriaFacade repasseParceriaFacade;

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

    public void registrarPagamento(Parcela parcela, boolean propagaRepasseEscritorio, boolean propagaRepasseParcerias) throws LogicalException {
        if (parcela!=null) {
            if (parcela.getDataPagamento()==null) throw new LogicalException("Informe a data do pagamento");
            this.salvar(parcela);
            if (propagaRepasseEscritorio) {
                if (parcela.isPendenteCobrancaEscritorio()) throw new LogicalException("Não foi encontrada cobrança de repasse ao escritório para essa parcela");
                parcela.getRepasseEscritorio().setDataRepasse(parcela.getDataPagamento());
                repasseEscritorioFacade.registrarPagamentoRepasse(parcela.getRepasseEscritorio());
            }
            if (propagaRepasseParcerias) {
                if (parcela.isPendenteCobrancaParcerias()) throw new LogicalException("Não foram encontradas cobranças de repasse a parcerias para esta parcela");
                for (RepasseParceria r:parcela.getRepassesParcerias()) {
                    r.setDataRepasse(parcela.getDataPagamento());
                    repasseParceriaFacade.registrarPagamentoRepasse(r);
                }
            }
        }
    }
}
