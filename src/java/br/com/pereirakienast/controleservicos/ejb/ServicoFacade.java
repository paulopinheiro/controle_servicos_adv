package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.entity.AssessoriaServico;
import br.com.pereirakienast.controleservicos.entity.ServicoPrestado;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

@Stateless
public class ServicoFacade extends AbstractFacade<ServicoPrestado> {
    @EJB private AssessoriaServicoFacade assessoriaFacade;

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
            if (servico.getAssessorias() != null) {
                if (isAdvogadoAssessor(servico.getAdvogado(),servico.getAssessorias())) throw new LogicalException("O advogado que presta o serviço não pode ser também assessor do mesmo");
                AssessoriaServico resp = duplicidadeAssessoria(servico.getAssessorias());
                if (resp!=null) throw new LogicalException("O advogado " + resp.getAdvogado() + " aparece em duplicidade na assessoria do serviço");
            }

            if (servico.getId()==null) getEntityManager().persist(servico);
            else getEntityManager().merge(servico);
            if (servico.getAssessorias()!=null) {
                for (AssessoriaServico as:servico.getAssessorias()) {
                    assessoriaFacade.salvar(as);
                }
            }
        }
    }

    private boolean isAdvogadoAssessor(Advogado advogado, List<AssessoriaServico> assessorias) {
        if ((advogado==null)||(assessorias==null)) return false;
        for (AssessoriaServico as:assessorias) {
            if (as.getAdvogado().equals(advogado)) return true;
        }
        return false;
    }

    private AssessoriaServico duplicidadeAssessoria(List<AssessoriaServico> assessorias) {
        if ((assessorias==null)||(assessorias.size()<2)) return null;
        Collections.sort(assessorias);
        for (int i=1;i<assessorias.size();i++) {
            if (assessorias.get(i-1).equals(assessorias.get(i))) return assessorias.get(i);
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
