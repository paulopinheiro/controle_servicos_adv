package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.entity.Advogado;
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
    
}
