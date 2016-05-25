package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.entity.AssessoriaServico;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AssessoriaServicoFacade extends AbstractFacade<AssessoriaServico> {
    @PersistenceContext(unitName = "pkServicosPU")
    private EntityManager em;

    public AssessoriaServicoFacade() {
        super(AssessoriaServico.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(AssessoriaServico entity) throws LogicalException {
        if (entity!=null) {
            if (entity.getAdvogado()==null) throw new LogicalException("Informe o advogado que presta a assessoria");
            if (entity.getServico()==null) throw new LogicalException("Informe o serviço que será assessorado");
            if (entity.getId()==null) super.create(entity);
            else super.edit(entity);
        }
    }
}
