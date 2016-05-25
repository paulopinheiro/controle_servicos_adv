package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();
    public abstract void salvar(T entity) throws LogicalException;
    
    public void excluir(T entity) throws LogicalException {
        this.remove(entity);
    }

    protected void create(T entity) {
        getEntityManager().persist(entity);
    }

    protected void edit(T entity) {
        getEntityManager().merge(entity);
    }

    protected void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<T> findFiltro(T entity) throws LogicalException {
        if (entity==null) throw new LogicalException("Filtro não pode ser nulo");
        List<T> resposta;

        Query pesquisa = getEntityManager().createQuery(getCq(entity));

        resposta = pesquisa.getResultList();

        return resposta;        
    }

    protected CriteriaBuilder getCb() {
        return this.getEntityManager().getCriteriaBuilder();
    }

    protected CriteriaQuery<T> getCq(T filtro) throws LogicalException {
        CriteriaQuery<T> cq = this.getCb().createQuery(entityClass);
        Root<T> r = cq.from(entityClass);
        cq.select(r);
        return cq;
    }
}
