package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.entity.ParceriaServico;
import br.com.pereirakienast.controleservicos.entity.ServicoPrestado;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import br.com.pereirakienast.controleservicos.model.CobrancaServico;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

@Stateless
public class ServicoFacade extends AbstractFacade<ServicoPrestado> {
    @EJB private ParceriaServicoFacade parceriaFacade;

    public ServicoFacade() {
        super(ServicoPrestado.class);
    }

    @Override
    public void salvar(ServicoPrestado servico) throws LogicalException {
        if (servico != null) {
            if (servico.getCliente()==null) throw new LogicalException("Informe o cliente para o qual o serviço foi ou será prestado");
            if (servico.getDataPrestacao()==null) throw new LogicalException("Informe a data da prestação efetiva do serviço, ou a data de início");
            if (servico.getAdvogado()==null) throw new LogicalException("Informe o advogado que prestou ou prestará o serviço");
            if (servico.getTipoServico()==null) throw new LogicalException("Informe o tipo de serviço prestado");
            if (servico.getParcerias() != null) {
                if (isAdvogadoParceiro(servico.getAdvogado(),servico.getParcerias())) throw new LogicalException("O advogado que presta o serviço não pode ser também parceiro do mesmo");
                ParceriaServico resp = duplicidadeParceria(servico.getParcerias());
                if (resp!=null) throw new LogicalException("O(A) advogado(a) " + resp.getAdvogado() + " aparece em duplicidade na parceria do serviço");
            }

            if (servico.getId()==null) getEntityManager().persist(servico);
            else getEntityManager().merge(servico);
            //aprimorar esta rotina para que perceba se houve exclusão de alguma
            // parceria de um serviço existente
            if (servico.getParcerias()!=null) {
                for (ParceriaServico as:servico.getParcerias()) {
                    parceriaFacade.salvar(as);
                }
            }
        }
    }

    public void salvar(ServicoPrestado servico, CobrancaServico cobranca) throws LogicalException {
        salvar(servico);
    }

    private boolean isAdvogadoParceiro(Advogado advogado, List<ParceriaServico> parcerias) {
        if ((advogado==null)||(parcerias==null)) return false;
        for (ParceriaServico as:parcerias) {
            if (as.getAdvogado().equals(advogado)) return true;
        }
        return false;
    }

    private ParceriaServico duplicidadeParceria(List<ParceriaServico> parcerias) {
        if ((parcerias==null)||(parcerias.size()<2)) return null;
        Collections.sort(parcerias);
        for (int i=1;i<parcerias.size();i++) {
            if (parcerias.get(i-1).equals(parcerias.get(i))) return parcerias.get(i);
        }
        return null;
    }

    @Override
    protected CriteriaQuery<ServicoPrestado> getCq(ServicoPrestado filtro) throws LogicalException {
        CriteriaQuery<ServicoPrestado> cq = super.getCq(filtro);

        Predicate cliente = getPredicateEqual(filtro.getCliente(),"cliente");
        Predicate advogado = getPredicateEqual(filtro.getAdvogado(),"advogado");
        Predicate dataPrestacao = getPredicateEqual(filtro.getDataPrestacao(),"dataPrestacao");
        Predicate tipoServico = getPredicateEqual(filtro.getTipoServico(),"tipoServico");

        cq.where(cliente,advogado,dataPrestacao,tipoServico);

        return cq;
    }
}
