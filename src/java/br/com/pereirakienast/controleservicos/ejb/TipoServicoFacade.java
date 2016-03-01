package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.entity.TipoServico;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class TipoServicoFacade extends AbstractFacade<TipoServico> {
    @PersistenceContext(unitName = "pkServicosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoServicoFacade() {
        super(TipoServico.class);
    }

    @Override
    public void salvar(TipoServico entity) throws LogicalException {
        if (entity !=null) {
            if (entity.getNome()==null||entity.getNome().isEmpty())
                throw new LogicalException("Informe o nome do tipo de serviço");

            TipoServico pesq = this.getByNome(entity.getNome());
            if (pesq==null || pesq.getId().equals(entity.getId())) {
            } else {
                throw new LogicalException("Já existe um tipo de serviço cadastrado com esse nome");
            }

            entity.setNome(entity.getNome().trim().toUpperCase());

            if (entity.getId()==null) super.create(entity);
            else super.edit(entity);
        }
    }

    private TipoServico getByNome(String nome) throws LogicalException {
        if (nome==null) return null;
        Query query = getEntityManager().createNamedQuery("TipoServico.findByNome");
        query.setParameter("nomeUpper", nome.trim().toUpperCase());
         try {
            return (TipoServico) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            throw new LogicalException("Há uma inconsistência no banco de dados. Dois tipos de serviço estão cadastrados com o nome " + nome);
        }
    }

    @Override
    public void excluir(TipoServico entity) throws LogicalException {
        if (entity !=null) {
            if (!entity.getListaServicosPrestados().isEmpty()) {
                throw new LogicalException("Não é possível excluir o tipo de serviço " + entity.getNome()+ ", pois existem prestados desse tipo cadastrados");
            }
            super.remove(entity);
        }
    }

    @Override
    protected CriteriaQuery<TipoServico> getCq(TipoServico filtro) throws LogicalException {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TipoServico> cq = cb.createQuery(TipoServico.class);
        Root<TipoServico> root = cq.from(TipoServico.class);
        cq.select(root);

        Predicate nome = cb.conjunction();

        if ((filtro.getNome()!=null)&&(!filtro.getNome().isEmpty())) {
            Expression<String> a_nome = root.get("nome");
            nome = cb.like(cb.upper(a_nome), filtro.getNome().toUpperCase());
        }

        cq.where(nome);

        return cq;
    }
}
