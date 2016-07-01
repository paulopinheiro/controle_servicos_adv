package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.AbstractFacade;
import br.com.pereirakienast.controleservicos.entity.cobranca.ContaServico;
import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import br.com.pereirakienast.controleservicos.entity.cobranca.RepasseEscritorio;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.model.CobrancaServico;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

public class ParcelaFacade extends AbstractFacade<Parcela> {
    @EJB private RepasseEscritorioFacade repasseEscritorioFacade;
    @EJB private RepasseParceriaFacade repasseParceriaFacade;

    public ParcelaFacade() {
        super(Parcela.class);
    }

    public List<Parcela> gerarParcelas(ContaServico conta, CobrancaServico cobranca) throws LogicalException {
        List<Parcela> resposta = new ArrayList<Parcela>();

        for (Date dataVencimento:cobranca.getVencimentosParcelas()) {
            Parcela parcela = new Parcela(conta,cobranca.getValorParcelaServico(),dataVencimento);
            salvar(parcela);

            // Gerando repasse do escritório
            RepasseEscritorio re = new RepasseEscritorio(parcela,cobranca.getValorRepasseEscritorio());
            repasseEscritorioFacade.salvar(re);
            parcela.setRepasseEscritorio(re);

            // gerar repasses parceria e setar na parcela
            parcela.setRepassesParcerias(repasseParceriaFacade.gerarRepassesParceria(parcela, cobranca.getValorParcelaRepasseParceriaIndividual()));

            resposta.add(parcela);
        }

        return resposta;
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
