package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.entity.ParceriaServico;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import javax.ejb.Stateless;

@Stateless
public class ParceriaServicoFacade extends AbstractFacade<ParceriaServico> {
    public ParceriaServicoFacade() {
        super(ParceriaServico.class);
    }

    @Override
    public void salvar(ParceriaServico entity) throws LogicalException {
        if (entity!=null) {
            if (entity.getAdvogado()==null) throw new LogicalException("Informe o advogado que presta a parceria");
            if (entity.getServico()==null) throw new LogicalException("Informe o serviço para o qual será feita parceria");
            if (entity.getId()==null) super.create(entity);
            else super.edit(entity);
        }
    }
}
