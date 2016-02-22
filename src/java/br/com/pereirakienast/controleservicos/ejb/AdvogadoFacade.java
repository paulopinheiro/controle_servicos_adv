package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AdvogadoFacade extends AbstractFacade<Advogado> {

    @PersistenceContext(unitName = "pkServicosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdvogadoFacade() {
        super(Advogado.class);
    }

    @Override
    public void salvar(Advogado entity) throws LogicalException {
        if (entity != null) {
            if (entity.getOab()==null||entity.getOab().trim().isEmpty())
                throw new LogicalException("Informe a OAB do advogado.");
            if (entity.getNome()==null || entity.getNome().trim().isEmpty())
                throw new LogicalException("Informe o nome do advogado");
            // TODO: consistir UNIQUE para número da OAB

            // TODO: forçar tudo maiúsculo e trim para campos String

            if (entity.getId()==null) super.create(entity);
            else super.edit(entity);
        }
    }

    @Override
    public void excluir(Advogado entity) throws LogicalException {
        //TODO: consistir se existem "filhos" nas tabelas Cliente e Serviços_Prestados
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Advogado> findFiltro(Advogado entity) throws LogicalException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
