package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
            // Consistir UNIQUE para número da OAB
            Advogado pesq = this.getByOab(entity.getOab());
            if (pesq== null || pesq.getId().equals(entity.getId())) {
            } else {
                throw new LogicalException("Já existe um advogado cadastrado com essa OAB");
            }

            // Forçar tudo maiúsculo e trim para campos String
            entity.setOab(entity.getOab().trim().toUpperCase());
            entity.setNome(entity.getNome().trim().toUpperCase());

            if (entity.getId()==null) super.create(entity);
            else super.edit(entity);
        }
    }

    private Advogado getByOab(String oab) throws LogicalException {
        if (oab==null) return null;
        Query query = getEntityManager().createNamedQuery("Advogado.findByOab");
        query.setParameter("oabUpper", oab.trim().toUpperCase());
        try {
            return (Advogado) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            throw new LogicalException("Há uma inconsistência no banco de dados. Dois advogados estão cadastrados com a OAB " + oab);
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
