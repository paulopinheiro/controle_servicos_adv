package br.com.pereirakienast.controleservicos.ejb;

import br.com.pereirakienast.controleservicos.entity.Advogado;
import br.com.pereirakienast.controleservicos.exceptions.LogicalException;
import java.util.Collections;
import java.util.List;
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
        // Consistir se existem "filhos" nas tabelas Cliente e Serviços_Prestados
        if (entity != null) {
           if (!entity.getListaClientes().isEmpty())  
               throw new LogicalException("Não é possível excluir o advogado " + entity.getNome()+ ", pois existem clientes associados ao mesmo");
           if (!entity.getListaServicosPrestados().isEmpty())
               throw new LogicalException("Não é possível excluir o advogado " + entity.getNome()+ ", pois existem cadastrados serviços prestados pelo mesmo");
           super.remove(entity);
        }
    }

    @Override
    protected CriteriaQuery<Advogado> getCq(Advogado filtro) {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Advogado> cq = cb.createQuery(Advogado.class);
        Root<Advogado> root = cq.from(Advogado.class);
        cq.select(root);

        Predicate oab = cb.conjunction();
        Predicate nome = cb.conjunction();
        Predicate ativo;
        Predicate administrador;

        if ((filtro.getOab()!=null)&&(!filtro.getOab().isEmpty())) {
            Expression<String> a_oab = root.get("oab");
            oab = cb.like(cb.upper(a_oab), filtro.getOab().toUpperCase());
        }
        if ((filtro.getNome()!=null)&&(!filtro.getNome().isEmpty())) {
            Expression<String> a_nome = root.get("nome");
            nome = cb.like(cb.upper(a_nome), filtro.getNome().toUpperCase());
        }
        ativo = cb.equal(root.get("ativo"), filtro.isAtivo());
        administrador = cb.equal(root.get("administrador"), filtro.isAdministrador());

        cq.where(oab,nome,ativo,administrador);

        return cq;
    }

    public List<Advogado> findAtivos() {
        List<Advogado> resposta;

        Query pesquisa = getEntityManager().createNamedQuery("Advogado.findAtivos");
        resposta = pesquisa.getResultList();
        if (resposta==null) {
        } else {
            Collections.sort(resposta);
        }

        return resposta;
    }
}
