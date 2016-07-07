package br.com.pereirakienast.controleservicos.ejb.cobranca;

import br.com.pereirakienast.controleservicos.ejb.ServicoFacade;
import br.com.pereirakienast.controleservicos.entity.ParceriaServico;
import br.com.pereirakienast.controleservicos.entity.cobranca.ContaServico;
import br.com.pereirakienast.controleservicos.entity.cobranca.Parcela;
import br.com.pereirakienast.controleservicos.entity.cobranca.RepasseEscritorio;
import br.com.pereirakienast.controleservicos.entity.cobranca.RepasseParceria;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.model.CobrancaServico;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CobrancaServicoService {
    @EJB private ServicoFacade servicoFacade;
    @EJB private ContaServicoFacade contaFacade;
    @EJB private ParcelaFacade parcelaFacade;
    @EJB private RepasseParceriaFacade repasseParceriaFacade;
    @EJB private RepasseEscritorioFacade repasseEscritorioFacade;

    public void salvarServicoComCobranca(CobrancaServico cobranca) throws LogicalException {
        servicoFacade.salvar(cobranca.getServico());
        gerarCobranca(cobranca);
    }

    private void gerarCobranca(CobrancaServico cobranca) throws LogicalException {
         // Se o valor do serviço for nulo ou zero não serão gerados dados de cobrança
        if ((cobranca!=null) && (cobranca.getValorServico()!=null) && cobranca.getValorServico().compareTo(BigDecimal.ZERO)>0) {
            gerarConta(cobranca);
        }
    }

    private void gerarConta(CobrancaServico cobranca) throws LogicalException {
        if (cobranca.getServico()==null||cobranca.getServico().getId()==null) throw new LogicalException("Houve um problema ao gerar dados de cobrança do serviço");
        if (cobranca.getQuantParcelasServico()==null||cobranca.getQuantParcelasServico()<1) throw new LogicalException("Deve ser informado um número de parcelas maior ou igual a 1");
        if (cobranca.getPercentualRepasseEscritorio()<0) throw new LogicalException("Percentual de repasse ao escritório não pode ser negativo");
        if (cobranca.getPercentualRepasseParceria()<0) throw new LogicalException("Percentual de repasse de parceria não pode ser negativo");

        ContaServico conta = new ContaServico(cobranca.getServico(),cobranca.getValorServico());
        contaFacade.salvar(conta);

        conta.setParcelas(gerarParcelas(conta,cobranca));
        cobranca.getServico().setConta(conta);
    }

    private List<Parcela> gerarParcelas(ContaServico conta, CobrancaServico cobranca) throws LogicalException {
        List<Parcela> resposta = new ArrayList<Parcela>();

        for (Date dataVencimento:cobranca.getVencimentosParcelas()) {
            Parcela parcela = new Parcela(conta,cobranca.getValorParcelaServico(),dataVencimento);
            parcelaFacade.salvar(parcela);

            // Gerando repasse do escritório
            RepasseEscritorio re = new RepasseEscritorio(parcela,cobranca.getValorRepasseEscritorio());
            repasseEscritorioFacade.salvar(re);
            parcela.setRepasseEscritorio(re);

            // gerar repasses parceria e setar na parcela
            parcela.setRepassesParcerias(gerarRepassesParceria(parcela, cobranca.getValorParcelaRepasseParceriaIndividual()));

            resposta.add(parcela);
        }

        return resposta;
    }

    private List<RepasseParceria> gerarRepassesParceria(Parcela parcela, BigDecimal valorRepasse) throws LogicalException {
        List<RepasseParceria> resposta = new ArrayList<RepasseParceria>();

        if (parcela.getConta().getServico().getParcerias() != null) {
            for (ParceriaServico parceria : parcela.getConta().getServico().getParcerias()) {
                RepasseParceria rp = new RepasseParceria(parcela, parceria, valorRepasse);
                repasseParceriaFacade.salvar(rp);
                resposta.add(rp);
            }
        }
        return resposta;
    }
}
